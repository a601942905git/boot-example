# 一、添加依赖
```
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
    </dependency>
</dependencies>
```

# 二、编写配置类
```
@EnableSwagger2
@Configuration
public class SwaggerConfigure {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
```

# 三、编写控制器
```
@RestController
public class SwaggerController {

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }
}
```

# 四、编写启动类
```
@SpringBootApplication
public class SwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerApplication.class, args);
    }
}
```

# 五、访问
http://localhost:8080/v2/api-docs
```
{"swagger":"2.0","info":{"description":"Api Documentation","version":"1.0","title":"Api Documentation","termsOfService":"urn:tos","contact":{},"license":{"name":"Apache 2.0","url":"http://www.apache.org/licenses/LICENSE-2.0"}},"host":"localhost:8080","basePath":"/","tags":[{"name":"basic-error-controller","description":"Basic Error Controller"}],"paths":{"/error":{"get":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingGET","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"head":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingHEAD","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"post":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPOST","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"201":{"description":"Created"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"put":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPUT","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"201":{"description":"Created"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"},"404":{"description":"Not Found"}},"deprecated":false},"delete":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingDELETE","produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"options":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingOPTIONS","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false},"patch":{"tags":["basic-error-controller"],"summary":"error","operationId":"errorUsingPATCH","consumes":["application/json"],"produces":["*/*"],"responses":{"200":{"description":"OK","schema":{"type":"object","additionalProperties":{"type":"object"}}},"204":{"description":"No Content"},"401":{"description":"Unauthorized"},"403":{"description":"Forbidden"}},"deprecated":false}}},"definitions":{"ModelAndView":{"type":"object","properties":{"empty":{"type":"boolean"},"model":{"type":"object"},"modelMap":{"type":"object","additionalProperties":{"type":"object"}},"reference":{"type":"boolean"},"status":{"type":"string","enum":["100 CONTINUE","101 SWITCHING_PROTOCOLS","102 PROCESSING","103 CHECKPOINT","200 OK","201 CREATED","202 ACCEPTED","203 NON_AUTHORITATIVE_INFORMATION","204 NO_CONTENT","205 RESET_CONTENT","206 PARTIAL_CONTENT","207 MULTI_STATUS","208 ALREADY_REPORTED","226 IM_USED","300 MULTIPLE_CHOICES","301 MOVED_PERMANENTLY","302 FOUND","302 MOVED_TEMPORARILY","303 SEE_OTHER","304 NOT_MODIFIED","305 USE_PROXY","307 TEMPORARY_REDIRECT","308 PERMANENT_REDIRECT","400 BAD_REQUEST","401 UNAUTHORIZED","402 PAYMENT_REQUIRED","403 FORBIDDEN","404 NOT_FOUND","405 METHOD_NOT_ALLOWED","406 NOT_ACCEPTABLE","407 PROXY_AUTHENTICATION_REQUIRED","408 REQUEST_TIMEOUT","409 CONFLICT","410 GONE","411 LENGTH_REQUIRED","412 PRECONDITION_FAILED","413 PAYLOAD_TOO_LARGE","413 REQUEST_ENTITY_TOO_LARGE","414 URI_TOO_LONG","414 REQUEST_URI_TOO_LONG","415 UNSUPPORTED_MEDIA_TYPE","416 REQUESTED_RANGE_NOT_SATISFIABLE","417 EXPECTATION_FAILED","418 I_AM_A_TEAPOT","419 INSUFFICIENT_SPACE_ON_RESOURCE","420 METHOD_FAILURE","421 DESTINATION_LOCKED","422 UNPROCESSABLE_ENTITY","423 LOCKED","424 FAILED_DEPENDENCY","426 UPGRADE_REQUIRED","428 PRECONDITION_REQUIRED","429 TOO_MANY_REQUESTS","431 REQUEST_HEADER_FIELDS_TOO_LARGE","451 UNAVAILABLE_FOR_LEGAL_REASONS","500 INTERNAL_SERVER_ERROR","501 NOT_IMPLEMENTED","502 BAD_GATEWAY","503 SERVICE_UNAVAILABLE","504 GATEWAY_TIMEOUT","505 HTTP_VERSION_NOT_SUPPORTED","506 VARIANT_ALSO_NEGOTIATES","507 INSUFFICIENT_STORAGE","508 LOOP_DETECTED","509 BANDWIDTH_LIMIT_EXCEEDED","510 NOT_EXTENDED","511 NETWORK_AUTHENTICATION_REQUIRED"]},"view":{"$ref":"#/definitions/View"},"viewName":{"type":"string"}},"title":"ModelAndView"},"View":{"type":"object","properties":{"contentType":{"type":"string"}},"title":"View"}}}
```
如上图可以看到返回的是一串压根就看不懂的json数据

# 六、引入swagger-ui
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
</dependency>
```
重启项目之后访问：http://localhost:8080/swagger-ui.html，就可以看到可视化界面

# 七、改造控制器
将@RequestMapping改成@GetMapping
```
@RestController
public class SwaggerController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }
}
```
重新访问http://localhost:8080/swagger-ui.html，会发现swagger-controller下面只有一个get请求

# 八、完善api信息
配置类中添加ApiInfo信息
```
@EnableSwagger2
@Configuration
public class SwaggerConfigure {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.example"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "boot-example-swagger restful api",
                "the api document about boot-example-swagger",
                "1.0.0",
                "www.baidu.com",
                new Contact("pengli", "https://github.com/a601942905git/boot-example", "a601942905@163.com"),
                "123456789", "API license URL", Collections.emptyList());
    }
}
```

# 九、编写实体类
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @ApiModelProperty(value = "用户编号")
    private Integer id;

    @ApiModelProperty(value = "用户姓名")
    private String name;

}
```

# 十、编写完成的增、删、改、查
```
@RestController
@RequestMapping("/users")
public class UserController {

    @ApiOperation(value = "查询用户详情信息", notes = "根据用户编号查询用户详情信息")
    @GetMapping("/{id}")
    public User getUser(@PathVariable(name = "id") Integer id) {
        return User.builder()
                .id(id)
                .name("测试swagger api")
                .build();
    }

    @ApiOperation(value = "新增用户", notes = "新增用户信息")
    @PostMapping("/")
    public void saveUser(@RequestBody User user) {

    }

    @ApiOperation(value = "修改用户", notes = "修改用户信息")
    @PutMapping("/")
    public void modifyUser(@RequestBody User user) {

    }

    @ApiOperation(value = "删除用户", notes = "根据用户编号删除用户信息")
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable(name = "id") Integer id) {

    }
}
```

# 十一、github代码
[Spring Boot集成Swagger](https://github.com/a601942905git/boot-example/tree/master/boot-example-swagger)