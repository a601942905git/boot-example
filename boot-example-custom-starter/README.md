# 一、Spring Boot Starter原理
Spring Boot本身定义了各种各样的Starter，当我们需要的时候，我们只需要引入对应的Starter，然后在application.properties中配置下属性就可以快速使用了，我们之所以可以快速使用，是因为Sping Boot的spring-boot-autoconfigure中的spring.factories定义了很多自动配置的类
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudServiceConnectorsAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoReactiveRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
```
以上我们可以看到elasticsearch、redis等自动配置的类，这里我们打开RedisAutoConfiguration类
```
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

	@Bean
	@ConditionalOnMissingBean
	public StringRedisTemplate stringRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}

}
```
可以看到该类上有很多的注解
@Configuration：代表这是一个配置类
@ConditionalOnClass(RedisOperations.class)：代表当RedisOperations存在的时候该类才自动执行自动配置
@EnableConfigurationProperties(RedisProperties.class)：表示启用RedisProperties
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })：表示引入这两个类

接下来我们打开RedisProperties这个类
```
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

	/**
	 * Database index used by the connection factory.
	 */
	private int database = 0;

	/**
	 * Connection URL. Overrides host, port, and password. User is ignored. Example:
	 * redis://user:password@example.com:6379
	 */
	private String url;

	/**
	 * Redis server host.
	 */
	private String host = "localhost";

	/**
	 * Login password of the redis server.
	 */
	private String password;

	/**
	 * Redis server port.
	 */
	private int port = 6379;

	/**
	 * Whether to enable SSL support.
	 */
	private boolean ssl;

	/**
	 * Connection timeout.
	 */
	private Duration timeout;

	private Sentinel sentinel;

	private Cluster cluster;

	private final Jedis jedis = new Jedis();

	private final Lettuce lettuce = new Lettuce();

	public int getDatabase() {
		return this.database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean isSsl() {
		return this.ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}

	public Duration getTimeout() {
		return this.timeout;
	}

	public Sentinel getSentinel() {
		return this.sentinel;
	}

	public void setSentinel(Sentinel sentinel) {
		this.sentinel = sentinel;
	}

	public Cluster getCluster() {
		return this.cluster;
	}

	public void setCluster(Cluster cluster) {
		this.cluster = cluster;
	}

	public Jedis getJedis() {
		return this.jedis;
	}

	public Lettuce getLettuce() {
		return this.lettuce;
	}
```
删减了部分代码，我们可以看到这个类上有一个@ConfigurationProperties(prefix = "spring.redis")注解，说明这是一个配置类，可以通过application.properties来进行配置属性，该类的属性都是默认的

总结：定义Starter要点
1. 需要有一个自动配置的类
2. 需要一个配合自动类的配置类

# 二、示例
## 定义boot-example-greeter项目
1. Greeter
```
public class Greeter {

    private GreeterConfig greeterConfig;

    public Greeter(GreeterConfig greetingConfig) {
        this.greeterConfig = greetingConfig;
    }

    public String greet(LocalDateTime localDateTime) {

        String name = greeterConfig.getUserName();
        int hourOfDay = localDateTime.getHour();

        if (hourOfDay >= 5 && hourOfDay < 12) {
            return String.format("Hello %s, %s", name, greeterConfig.getMorningMessage());
        } else if (hourOfDay >= 12 && hourOfDay < 17) {
            return String.format("Hello %s, %s", name, greeterConfig.getAfternoonMessage());
        } else if (hourOfDay >= 17 && hourOfDay < 20) {
            return String.format("Hello %s, %s", name, greeterConfig.getEveningMessage());
        } else {
            return String.format("Hello %s, %s", name, greeterConfig.getNightMessage());
        }
    }

    public String greet() {
        return greet(LocalDateTime.now());
    }
}
```
2. GreeterConfig
```
public class GreeterConfig {

    private String userName;

    private String morningMessage;

    private String afternoonMessage;

    private String eveningMessage;

    private String nightMessage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMorningMessage() {
        return morningMessage;
    }

    public void setMorningMessage(String morningMessage) {
        this.morningMessage = morningMessage;
    }

    public String getAfternoonMessage() {
        return afternoonMessage;
    }

    public void setAfternoonMessage(String afternoonMessage) {
        this.afternoonMessage = afternoonMessage;
    }

    public String getEveningMessage() {
        return eveningMessage;
    }

    public void setEveningMessage(String eveningMessage) {
        this.eveningMessage = eveningMessage;
    }

    public String getNightMessage() {
        return nightMessage;
    }

    public void setNightMessage(String nightMessage) {
        this.nightMessage = nightMessage;
    }
}
```
主要功能就是根据GreeterConfig中的配置属性来打招呼功能

## 定义greeter-spring-boot-autoconfigure项目
1. GreeterAutoConfiguration
```
@Configuration
@EnableConfigurationProperties(GreeterProperties.class)
@ConditionalOnClass(Greeter.class)
public class GreeterAutoConfiguration {

    @Autowired
    private GreeterProperties greeterProperties;

    @Bean
    @ConditionalOnMissingBean
    public GreeterConfig greeterConfig() {
        GreeterConfig greeterConfig = new GreeterConfig();

        String userName = greeterProperties.getUserName();
        String morningMessage = greeterProperties.getMorningMessage();
        String afternoonMessage = greeterProperties.getAfternoonMessage();
        String eveningMessage = greeterProperties.getEveningMessage();
        String nightMessage = greeterProperties.getNightMessage();

        greeterConfig.setUserName(userName);
        greeterConfig.setMorningMessage(morningMessage);
        greeterConfig.setAfternoonMessage(afternoonMessage);
        greeterConfig.setEveningMessage(eveningMessage);
        greeterConfig.setNightMessage(nightMessage);
        return greeterConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public Greeter greeter() {
        Greeter greeter = new Greeter(greeterConfig());
        return greeter;
    }
}
```
2. GreeterProperties
```
@ConfigurationProperties(prefix = "com.boot.example")
public class GreeterProperties {

    private String userName = "本机用户";

    private String morningMessage = "早上好";

    private String afternoonMessage = "中午好";

    private String eveningMessage = "下午好";

    private String nightMessage = "晚上好";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMorningMessage() {
        return morningMessage;
    }

    public void setMorningMessage(String morningMessage) {
        this.morningMessage = morningMessage;
    }

    public String getAfternoonMessage() {
        return afternoonMessage;
    }

    public void setAfternoonMessage(String afternoonMessage) {
        this.afternoonMessage = afternoonMessage;
    }

    public String getEveningMessage() {
        return eveningMessage;
    }

    public void setEveningMessage(String eveningMessage) {
        this.eveningMessage = eveningMessage;
    }

    public String getNightMessage() {
        return nightMessage;
    }

    public void setNightMessage(String nightMessage) {
        this.nightMessage = nightMessage;
    }
}
```
以上代码可以看到GreeterProperties类属于配置类，定义了默认的属性，也可以通过application.properties来指定

## 定义greeting-spring-boot-starter项目
1. 添加依赖
```
<dependencies>
        <dependency>
            <groupId>com.boot.example</groupId>
            <artifactId>greeter-spring-boot-autoconfigure</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

# 三、创建测试项目
1. 添加依赖
```
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>com.boot.example</groupId>
            <artifactId>greeting-spring-boot-starter</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
    </dependencies>
```
2. 编写启动类
```
@SpringBootApplication
public class CustomerStarterTestApplication implements CommandLineRunner {

    @Autowired
    private Greeter greeter;

    public static void main(String[] args) {
        SpringApplication.run(CustomerStarterTestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("【自定义Starter】：" + greeter.greet());
    }
}
```
3. 启动项目，可以看到如下输出结果
```
【自定义Starter】：Hello 本机用户, 晚上好
```
4. 编写配置属性来覆盖默认的配置
```
com.boot.example.userName=wids by properties
com.boot.example.morningMessage=good morning by properties
com.boot.example.afternoonMessage=good afternoon by properties
com.boot.example.eveningMessage=good evening by properties
com.boot.example.nightMessage=good night by properties
```
5. 启动项目，可以看到如下输出结果
```
【自定义Starter】：Hello wids by properties, good night by properties
```
6. 通过配置类的方式来覆盖默认属性
```
@Configuration
public class GreeterConfigure {

    @Bean
    public GreeterConfig greeterConfig() {
        GreeterConfig greeterConfig = new GreeterConfig();

        greeterConfig.setUserName("smile");
        greeterConfig.setMorningMessage("good morning");
        greeterConfig.setAfternoonMessage("good afternoon");
        greeterConfig.setEveningMessage("good evening");
        greeterConfig.setNightMessage("good night");
        return greeterConfig;
    }
}
```
7. 启动项目，可以看到如下输出结果
```
【自定义Starter】：Hello smile, good night
```
可以看到自定义Starter已经达到效果，和我们使用spring boot自带的Starter是一样的