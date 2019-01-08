# Jackson转换
在代码中我们一般都会使用实体类来进行交互，例如我们需要给前端返回数据，把
我们需要返回的对象填充数据，然后以json的格式返回出去，但是存在一个问题，
那就是返回的数据可能存在驼峰命名，例如goodsName这样的情况，如何转换成
goods_name这样的格式呢？

我们可以利用Jackson提供的自带功能来实现这样的场景

1. 编写实体类
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Output {

    private Integer outputId;

    private String outputName;
}
```
2. 编写控制器
```java
@RestController
public class OutputJsonController {

    @RequestMapping("/")
    public Output output() {
        Output output = Output.builder()
                .outputId(10001)
                .outputName("test json convert")
                .build();

        return output;
    }
}
```
3. 编写启动类
```java
@SpringBootApplication
public class OutputJsonApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutputJsonApplication.class, args);
    }
}
```
4. 访问 http://localhost:8080/ , 结果如下
```json
{"output_id":10001,"output_name":"test json convert"}
```