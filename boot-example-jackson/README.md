# 一、编写实体类
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;

    private LocalDate localDate;

    private LocalDateTime localDateTime;
}
```

# 二、编写控制器
```
@RestController
public class UserController {

    @RequestMapping("/")
    public User index() {
        return User.builder()
                .id(10001)
                .name("test jackson")
                .localDate(LocalDate.now())
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
```

# 三、编写启动类
```
@SpringBootApplication
public class JacksonApplication {

    public static void main(String[] args) {
        SpringApplication.run(JacksonApplication.class, args);
    }
}
```

# 四、访问http://localhost:8080/
```
{"id":10001,"name":"test jackson","localDate":"2018-12-13","localDateTime":"2018-12-13T15:11:32.577"}
```
可以看到序列化后的日期格式并不是我们想要的

# 五、解决方案
1. 使用@JsonFormat(pattern = "yyyy-MM-dd")
```
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private Integer id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;
}

```
再次访问
```
{"id":10001,"name":"test jackson","localDate":"2018-12-13","localDateTime":"2018-12-13 15:14:40"}
```

2. 自定义序列化和反序列化方式
```
@JsonComponent
public class DateJsonFormat {

    public static class Serializer extends JsonSerializer<LocalDateTime> {

        private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateTimeFormatter.format(localDateTime));
        }
    }


    public static class Deserializer extends JsonDeserializer<LocalDateTime> {
        @Override
        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            return jsonParser.readValueAs(LocalDateTime.class);
        }
    }
}
```
再次访问我们可以看到日期也是经过格式化的

第二种方式的好处就是，我们只需要自定义好序列化和反序列化的方式，就不需要在每个地方添加注解，减少不必须要的工作