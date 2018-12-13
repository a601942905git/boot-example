# 一、下载jar包
下载swagger-codegen-cli-2.2.3.jar到指定目录

# 二、生成测试代码
在有swagger-codegen-cli-2.2.3.jar的目录中执行如下命令：
```
java -jar swagger-codegen-cli-2.2.3.jar generate \
  -i http://localhost:8080/v2/api-docs \
  --api-package com.boot.example.petstore.client.api \
  --model-package com.boot.exampl.petstore.client.model \
  --invoker-package com.boot.exampl.petstore.client.invoker \
  --group-id com.boot.example \
  --artifact-id spring-swagger-codegen-api-client \
  --artifact-version 0.0.1-SNAPSHOT \
  -l java \
  --library resttemplate \
  -o boot-swagger-codegen-api-client
```

# 三、构建生成的测试代码项目
查看当前目录会发现有个spring-swagger-codegen-api-client项目生成，切换到该目录下面，进行构建操作
```
mvn clean package install -X
```
并将构建好的包打到本地仓库中，这样我们就可以在本地依赖spring-swagger-codegen-api-client

# 四、新建项目
1. 创建一个Spring Boot项目
2. 添加依赖
```
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>com.boot.example</groupId>
        <artifactId>spring-swagger-codegen-api-client</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```
3. 添加配置
```
@Configuration
public class SwaggerClientConfig {

    @Bean
    public UserControllerApi userControllerApi() {
        return new UserControllerApi(apiClient());
    }

    @Bean
    public ApiClient apiClient() {
        return new ApiClient().setBasePath("http://localhost:8080");
    }
}

```
4. 编写控制器
```
@RestController
public class SwaggerClientController {

    @Autowired
    private UserControllerApi userControllerApi;

    @RequestMapping("/")
    public void index() {
        System.out.println(userControllerApi.getUserUsingGET(10001));
    }
}

```

5. 编写启动类
```
@SpringBootApplication
public class SwaggerClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwaggerClientApplication.class, args);
    }
}
```
6. 该项目端口为：8081，访问localhost:8081/，控制台输出结果如下
```
class User {
    id: 10001
    name: 测试swagger api
}
```
可以看到我们不需要编写任何的测试代码，只需要注入UserControllerApi对象，就可以执行对应的方法。