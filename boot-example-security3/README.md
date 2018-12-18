上一个章节已经实现了对于不同用户拥有不同的操作权限，但是登录还存在着问题。我们可以尝试，输入不存在的用户名或者错误的密码，都会在页面展示“用户名或密码错误”这样的提示。如果我们不需要知道具体的错误原因，给一个笼统的错误提示可以接受，那么上一章节就可以满足需求。

# 登录返回具体的错误原因
1. 首先在之前登录错误路由的方法中加入如下代码
```
System.out.println(request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
```
2. 分别输入错误的账号、密码进行登录，控制台打印如下内容
```
org.springframework.security.authentication.BadCredentialsException: Bad credentials
org.springframework.security.authentication.BadCredentialsException: Bad credentials
```
3. 根据如上打印的内容我们无法区分具体的错误原因，我们在自定义的CustomerUserDetailService类的loadUserByUsername()方法中判断如果用户不存在抛出的是UsernameNotFoundException异常，为什么返回的却是BadCredentialsException？？？
4. 打开AbstractUserDetailsAuthenticationProvider类
```
if (user == null) {
	cacheWasUsed = false;

	try {
		user = retrieveUser(username,
				(UsernamePasswordAuthenticationToken) authentication);
	}
	catch (UsernameNotFoundException notFound) {
		logger.debug("User '" + username + "' not found");
        
        // 如果该变量为真，那么就会返回BadCredentialsException
		if (hideUserNotFoundExceptions) {
			throw new BadCredentialsException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.badCredentials",
					"Bad credentials"));
		}
		else {
			throw notFound;
		}
	}

	Assert.notNull(user,
			"retrieveUser returned null - a violation of the interface contract");
}
```
5.修改该变量为false即可解决我们的问题，之前的配置类修改如下
```
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailService customerUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login/error")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customerUserDetailService);
        return provider;
    }
}
```
6. 登录错误方法修改如下
```
@RequestMapping("/login")
@Controller
public class LoginController {

    private static final String ERROR_PASSWORD_MESSAGE = "Bad credentials";

    @RequestMapping("/error")
    public String error(HttpServletRequest request, Model model) {
        String errorMessage = "";
        Object exceptionObj = request.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (exceptionObj instanceof AuthenticationException) {
            errorMessage = ((AuthenticationException) exceptionObj).getMessage();
            if (errorMessage.equalsIgnoreCase(ERROR_PASSWORD_MESSAGE)) {
                errorMessage = "密码错误";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
}
```
7. 登录页面修改如下
```
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
</head>
<body>
<h1>Login page</h1>
<p th:text="${errorMessage}"></p>
<form th:action="@{/login}" method="post">
    <label for="username">Username</label>:
    <input type="text" id="username" name="username" autofocus="autofocus" /> <br />
    <label for="password">Password</label>:
    <input type="password" id="password" name="password" /> <br />
    <input type="submit" value="Login" />
</form>
</body>
</html>
```

# 信息国际化
前面对于密码有误的输出通过硬编码的方式实现的，其实Spring Security自带国际化配置文件
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E6%88%AA%E5%9B%BE_230da13e-57ed-4fb0-b64f-0be8c304a913.png)
如果我们想自定义，我们可以在项目的resource目录下面创建org/springframework/security/messages_zh_CN.properties来覆盖默认的
```
AbstractUserDetailsAuthenticationProvider.badCredentials=输入的密码有误
```
这样我们再输入错误的密码，就可以看到我们配置的错误信息了