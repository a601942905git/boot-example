# 一、权限系统分析
权限系统的设计：
1. 用户
2. 角色
3. 权限

给用户分配相应的角色，给角色赋予相应的权限，那么用户就有了相应的角色

用户和角色的对应关系：一对多

角色和权限的对应关系：一对多

# 二、实战示例
1. 添加依赖
```
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>

    <dependency>
        <groupId>org.thymeleaf.extras</groupId>
        <artifactId>thymeleaf-extras-springsecurity5</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```
2. 创建权限实体
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemPermission {

    private Integer id;

    private String name;

    private String url;
}
```
3. 创建角色实体
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemRole {

    private Integer id;

    private String name;

    private List<SystemPermission> permissionList;
}
```
4. 创建用户实体
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {

    private Integer id;

    private String username;

    private String password;

    private List<SystemRole> roleList;
}
```
5. 创建用户业务层
```
@Service
public class UserService {

    SystemRole adminRole = SystemRole.builder()
            .id(10001)
            .name("admin")
            .build();

    SystemRole developRole = SystemRole.builder()
            .id(10002)
            .name("develop")
            .build();


    {
        SystemPermission systemPermission1 =
                SystemPermission.builder().id(10002).name("查询图书信息").url("/books").build();

        SystemPermission systemPermission2 =
                SystemPermission.builder().id(10003).name("新增图书").url("/books/add").build();

        SystemPermission systemPermission3 =
                SystemPermission.builder().id(10004).name("修改图书").url("/books/update").build();

        SystemPermission systemPermission4 =
                SystemPermission.builder().id(10005).name("删除图书").url("/books/delete").build();

        adminRole.setPermissionList(Arrays.asList(systemPermission1,
                        systemPermission2, systemPermission3, systemPermission4));

        developRole.setPermissionList(Arrays.asList(systemPermission1));
    }

    public SystemUser getSystemByUsername(String username) {
        SystemUser user = null;
        // 管理员账户
        if (Objects.equals(username, SystemUsernameConstants.ADMIN_USER_NAME)) {
            user = SystemUser.builder()
                    .id(10001)
                    .username(username)
                    .password("$2a$10$nqFV2LWYvCckYOtrgm0jKO0PYazuO6tVmGgHleDBVSUjZVSkCl/3e")
                    .build();
            user.setRoleList(Arrays.asList(adminRole, developRole));
        }
        // 开发账户
        else if (Objects.equals(username, SystemUsernameConstants.DEVELOP_USER_NAME)) {
            user = SystemUser.builder()
                    .id(10002)
                    .username(username)
                    .password("$2a$10$nqFV2LWYvCckYOtrgm0jKO0PYazuO6tVmGgHleDBVSUjZVSkCl/3e")
                    .build();
            user.setRoleList(Arrays.asList(developRole));
        }

        return user;
    }
}
```
6. 结合Spring Security加载用户信息
```
@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = userService.getSystemByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username);
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for (SystemRole role : user.getRoleList()) {
            for (SystemPermission permission : role.getPermissionList()) {
                authorityList.add(new SimpleGrantedAuthority(permission.getUrl()));
            }
        }
        return new User(username, user.getPassword(), authorityList);
    }
}
```
7. 自定义webMvc配置
```
@Configuration
public class CustomerWebMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }
}
```
8. 自定义Spring Security安全配置
```
@Configuration
@EnableWebSecurity
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
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```
9. 创建控制器
```
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
```

```
@Controller
public class BookController {

    @PreAuthorize("hasAuthority('/books')")
    @RequestMapping("/books")
    public String books(Model model) {
        Book book1 = Book.builder().id(10001).name("射雕英雄传").publishDate("2018-12-14 09:00:00").build();
        Book book2 = Book.builder().id(10002).name("天龙八部").publishDate("2018-12-15 15:20:00").build();
        Book book3 = Book.builder().id(10003).name("倚天屠龙记").publishDate("2018-12-16 18:05:00").build();
        Book book4 = Book.builder().id(10004).name("神雕侠侣").publishDate("2018-12-17 13:00:00").build();
        Book book5 = Book.builder().id(10005).name("笑傲江湖").publishDate("2018-12-18 19:00:00").build();

        List<Book> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);

        model.addAttribute("bookList", bookList);
        return "book";
    }

    @PreAuthorize("hasAuthority('/books/add')")
    @RequestMapping("/books/add")
    @ResponseBody
    public String add() {
        return "新增图书";
    }

    @PreAuthorize("hasAuthority('/books/update')")
    @RequestMapping("/books/update")
    @ResponseBody
    public String update() {
        return "修改图书";
    }

    @PreAuthorize("hasAuthority('/books/delete')")
    @RequestMapping("/books/delete")
    @ResponseBody
    public String delete() {
        return "删除图书";
    }
}
```

```
@RequestMapping("/login")
@Controller
public class LoginController {

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
```
10. 首页
```
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <a th:href="@{/books}">进入图书管理</a>
    </body>
</html>
```
11. 登录页
```
<!DOCTYPE html>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Login page</title>
</head>
<body>
<h1>Login page</h1>
<p th:if="${loginError}" class="error">用户名或密码错误</p>
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
12. 展示页
```
<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
        <link th:href="@{/bootstrap.min.css}" rel="stylesheet"/>
    </head>
    <body>
        <h1>
            Logged in user: <span sec:authentication="name"></span> |
            Roles: <span sec:authentication="principal.authorities"></span>
        </h1>
        <button sec:authorize="hasAuthority('/books/add')" class="btn-primary" th:href="@{/books/add}">新增</button>
        <table class="table table-striped table-hover table-condensed">
            <thead>
                <tr>
                    <th>编号</th>
                    <th>名称</th>
                    <th>出版时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="book : ${bookList}">
                    <td th:text="${book.id}"></td>
                    <td th:text="${book.name}"></td>
                    <td th:text="${book.publishDate}"></td>
                    <td>
                        <a sec:authorize="hasAuthority('/books/update')" th:href="@{/books/update}">修改</a>
                        <a sec:authorize="hasAuthority('/books/delete')" th:href="@{/books/delete}">删除</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
```
13. 启动项目，访问 http://localhost:8080/books

使用账号：admin，密码：123456登录，效果如下：
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/DBBD0A36-47D3-40B2-936A-B98940BA6BE7.png)

使用账号：test，密码：123456登录，效果如下：
![](https://mdsystem.oss-cn-hangzhou.aliyuncs.com/blog/7E2F2A68-45FF-4F1A-A8A7-B55B4AF143DF.png)

从上可以看出已经实现了权限功能，不用的用户分配不同的角色，看到的效果是完全不一样的

# 注意点：
在页面上使用Spring Security的属性：
1. 首先要引入
```
xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity4"
```
2. 其次需要在pom中引入
```
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>
```
针对pom的引入，需要参考版本，版本不一致也会导致页面无法展示想要的效果

关于版本选择可以参考[官网版本参考指南](https://github.com/thymeleaf/thymeleaf-extras-springsecurity)

# github代码
[Spring Boot Security进阶](https://github.com/a601942905git/boot-example/tree/master/boot-example-security2)