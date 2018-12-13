# 一、下载Quick Start安装包
```
git clone https://github.com/nobodyiam/apollo-build-scripts.git
```

# 二、创建数据库
sql文件存放在刚才下载项目的sql文件夹下
1. 创建ApolloPortalDB数据库
```
source /your_local_path/sql/apolloportaldb.sql
```
2. 导入成功之后可以通过下面sql来验证
```
select `Id`, `AppId`, `Name` from ApolloPortalDB.App;
```
3. 创建ApolloConfigDB
```
source /your_local_path/sql/apolloconfigdb.sql
```
4. 导入成功后，可以通过执行以下sql语句来验证：
```
select `NamespaceId`, `Key`, `Value`, `Comment` from ApolloConfigDB.Item;
```

# 三、配置数据库连接信息
Apollo服务端需要知道如何连接到你前面创建的数据库，所以需要编辑demo.sh，修改ApolloPortalDB和ApolloConfigDB相关的数据库连接串信息。

==注意：== 填入的用户需要具备对ApolloPortalDB和ApolloConfigDB数据的读写权限。

```
#apollo config db info
apollo_config_db_url=jdbc:mysql://localhost:3306/ApolloConfigDB?characterEncoding=utf8
apollo_config_db_username=用户名
apollo_config_db_password=密码（如果没有密码，留空即可）

# apollo portal db info
apollo_portal_db_url=jdbc:mysql://localhost:3306/ApolloPortalDB?characterEncoding=utf8
apollo_portal_db_username=用户名
apollo_portal_db_password=密码（如果没有密码，留空即可）
```
==注意：== 不要修改demo.sh的其它部分

# 四、启动配置中心

## 4.1 确保端口为被占用
Quick Start脚本会在本地启动3个服务，分别使用8070, 8080, 8090端口，请确保这3个端口当前没有被使用。

## 4.2 执行启动脚本
```
./demo.sh start
```
当看到如下输出后，就说明启动成功了！
```
==== starting service ====
Service logging file is ./service/apollo-service.log
Started [1530]
Waiting for config service startup.....
Config service started. You may visit http://localhost:8080 for service status now!
Waiting for admin service startup..
Admin service started
==== starting portal ====
Portal logging file is ./portal/apollo-portal.log
Started [1580]
Waiting for portal startup....
Portal started. You can visit http://localhost:8070 now!
```

apollo配置中心启动后会启动3个端口

8070：apollo界面操作

8080：meta server地址、eureka访问地址

8081：apolloe-adminserver访问地址

访问http://localhost:8070 

用户名：apollo 密码：admin

# 五、运行客户端程序

在之前下载的apollo-build-scripts项目下找到demo.sh，执行如下命令
```
./demo.sh client
```

然后输入timeout，就以查看到该key对应的value

然后在apollo管理界面修改timeout的值，修改完成之后点击发布按钮，在客户端输入timeout就可以看到修改后的最新之

# 六、搭建自己的客户端程序
使用Spring Boot来搭建客户端程序


                                客户端搭建
                                
# 一、创建项目
在apollo配置中心控制台界面店家创建项目

![](https://raw.githubusercontent.com/nobodyiam/apollo-build-scripts/master/images/apollo-sample-home.png)

==注意：== 项目的appId一定要和配置文件中的app.id=boot-example-apollo保持一致

# 二、添加项目依赖
```
<dependencies>
    <dependency>
        <groupId>com.ctrip.framework.apollo</groupId>
        <artifactId>apollo-client</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```
# 三、添加实体类
```
@Data
@ToString
public class TestJavaConfigBean {

    @Value("${timeout:100}")
    private int timeout;

    @Value("${batch:200}")
    private int batch;
}
```
# 四、添加配置类
```
@Configuration
public class AppConfig {

    @Bean
    public TestJavaConfigBean testJavaConfigBean() {
        return new TestJavaConfigBean();
    }
}
```
# 五、添加控制器
```
@RestController
@RequestMapping("/testJavaConfigBean")
public class ApolloController {

    @Autowired
    private TestJavaConfigBean testJavaConfigBean;

    @RequestMapping("/")
    public TestJavaConfigBean testJavaConfigBean() {
        return testJavaConfigBean;
    }
}
```
# 六、添加启动类
```
@SpringBootApplication
@EnableApolloConfig
public class ApolloApplication {

    private static TestJavaConfigBean testJavaConfigBean;

    @Autowired
    public void setTestJavaConfigBean(TestJavaConfigBean testJavaConfigBean) {
        ApolloApplication.testJavaConfigBean = testJavaConfigBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApolloApplication.class, args);

        System.out.println("TestJavaConfigBean======>" + testJavaConfigBean);
    }
}
```
# 七、添加配置
在resources目录下面创建application.properties
```
app.id=boot-example-apollo
apollo.meta=http://localhost:8080
apollo.bootstrap.enabled = true
apollo.bootstrap.namespaces = application,FX.apollo
server.port=8081
```
由于apollo配置中心在启动的时候会占用8080端口，而spring boot项目启动的默认端口也是8080，所以需要修改端口

# 八、启动客户端
客户端启动之后可以在控制台看到如下结果：
```
TestJavaConfigBean======>TestJavaConfigBean(timeout=100, batch=200)
```

# 九、添加配置信息
在apollo配置中心添加配置信息，此处将timeout修改为200，batch修改为300，添加完成之后点击发布

# 十、 访问控制器
http://localhost:8081/testJavaConfigBean/

结果如下：

{"timeout":200,"batch":300}

此处我们可以看到配置已经达到了实时生效的效果
