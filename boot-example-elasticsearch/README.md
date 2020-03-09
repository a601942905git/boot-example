## 1. 前言

在工作中你或多或少听说过搜索引擎，目前最流行的搜索引擎就是Elasticsearch，本文将从Elasticsearch的安装、实战、原理分析几个方面带你领略Elasticsearch的风采

## 2. Elasticsearch介绍

Elasticsearch是一个分布式搜索引擎，可以用来存储、分析、搜索数据。

## 3. Elasticsearch安装

### 3.1 Docker的方式安装Elasticsearch

#### 3.1.1 使用docker搜索镜像

```shell
➜  ~ docker search elasticsearch
```

#### 3.1.2从远程拉取镜像

```shel
➜  ~ docker pull elasticsearch:7.4.0
```

#### 3.1.3 启动镜像

```shell
➜  ~ docker run -d --name myes -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.4.0
```

#### 3.1.4 访问Elasticsearch

http://localhost:9200/

```json
{
  "name" : "0222e17f062d",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "OtL-k7FpR4ylPVsiKcZIkA",
  "version" : {
    "number" : "7.4.0",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "22e1767283e61a198cb4db791ea66e3f11ab9910",
    "build_date" : "2019-09-27T08:36:48.569419Z",
    "build_snapshot" : false,
    "lucene_version" : "8.2.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

当你看到如上返回数据，恭喜你es安装成功了。

### 3.2  正常安装Elasticsearch

#### 3.2.1 下载安装包

Linux：

```shell
➜  ~ curl -L -O https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.4.1-linux-x86_64.tar.gz
```

MacOS：

```shell
➜  ~ wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-7.4.1-darwin-x86_64.tar.gz
```

#### 3.2.2 解压安装包

Linux:

```shell
➜  ~ tar -xvf elasticsearch-7.4.1-linux-x86_64.tar.gz
```

macOS:

```shell
➜  ~ tar -xvf elasticsearch-7.4.1-darwin-x86_64.tar.gz
```

#### 3.2.3 运行

Linux and macOS:

```shell
➜  ~ cd elasticsearch-7.4.1/bin
➜  ~ ./elasticsearch
```

### 3.3 访问Elasticsearch

http://localhost:9200/

## 4.Elasticsearch增、删、改、查实战

本文以SpringBoot整合Elasticsearch进行实战讲解，要求对SpringBoot有一定的了解

### 4.1 新建项目

创建boot-example-elasticsearch模块

### 4.2 添加依赖

```properties
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>
    </dependencies>
```

### 4.3 配置Elasticsearch地址

```yaml
spring:
  elasticsearch:
    rest:
      uris: http://localhost:9200 # 指明es地址
```

### 4.4 创建文档实体类

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "goods", type = "_doc")
public class Goods implements Serializable {

    @Id
    private Long id;

    @Field
    private String name;

    @Field
    private String title;

    @Field
    private BigDecimal price;

    @Field
    private String publishDate;
}
```

> 需要使用@Document注解用来指明索引和文档类型，这样在操作文档数据的时候可以不需要指定索引和类型。使用@Id注解指明文档的id，@Field用于指明文档的其它属性

### 4.5 注入ElasticsearchRestTemplate

和往常操作其它中间件如redis、rabbitMQ一样，需要注入相应的xxxxTemplate

```java
@Autowired
private ElasticsearchRestTemplate elasticsearchRestTemplate;
```

### 4.6 新增文档数据

```java
@Test
public void insert() {
    Goods goods = Goods.builder()
        .id(10006L)
        .name("测试商品")
        .title("我是一个测试商品，拍了不发货，请谨慎！")
        .price(new BigDecimal(9999999))
        .publishDate("2019-11-06")
        .build();
    /**
         * index用于新增或者修改整个文档
         * 文档不存在，执行新增操作
         * 文档存在，执行修改操作
         * 此处只需要设置索引的对象即可，底层代码会从索引对象的@Document注解中获取索引、类型以及文档的id
         */
    elasticsearchRestTemplate.index(new IndexQueryBuilder().withObject(goods).build());
    log.info("index document finish");
}
```

### 4.7 批量新增文档数据

```java
@Test
public void batchInsert() {
    List<Goods> goodsList = buildGoodsList();
    List<IndexQuery> indexQueryList = new ArrayList<>();
    for (int i = 0, len = goodsList.size(); i < len; i++) {
        indexQueryList.add(new IndexQueryBuilder()
                           .withObject(goodsList.get(i)).build());
    }
    // 使用bulk方法批量索引文档
    elasticsearchRestTemplate.bulkIndex(indexQueryList);
    log.info("batch index document finish");
}
```

### 4.8 修改文档数据

```java
@Test
public void update() {
    Goods goods = Goods.builder()
        .id(10001L)
        .name("AppleiPhone 11 update")
        .title("Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待 update")
        .price(new BigDecimal(6666))
        .publishDate("2019-12-12")
        .build();
    IndexQuery indexQuery = new IndexQueryBuilder()
        .withObject(goods)
        .build();
    /**
         * 使用index方法来更新整个文档
         * 如果有字段为null，更新到es中，es文档中不会有该字段存在
         * {
         *           "_class" : "com.boot.example.Goods",
         *           "id" : 10006,
         *           "name" : "AppleiPhone 11 update",
         *           "title" : "Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待 update"
         *         }
         */
    elasticsearchRestTemplate.index(indexQuery);
    log.info("update document");
}
```

### 4.9 修改文档部分数据

```java
@Test
public void partialUpdate() {
    // 构建需要更新的字段
    Map<String, Object> map = new HashMap<>();
    map.put("price", new BigDecimal(6666));
    map.put("publishDate", "2019-09-08");
    UpdateQuery updateQuery = new UpdateQueryBuilder()
        .withId("10001")
        .withClass(Goods.class)
        .withUpdateRequest(new UpdateRequest().doc(map))
        .build();
    // 更新文档的部分内容
    elasticsearchRestTemplate.update(updateQuery);
    log.info("partial update document");
}
```

### 4.10 查询单个文档数据

```java
@Test
public void getById() {
    GetQuery getQuery = new GetQuery();
    getQuery.setId("10001");
    Goods goods = elasticsearchRestTemplate.queryForObject(getQuery, Goods.class);
    log.info("get document by id result：{}", goods);
}
```

### 4.11 查询文档列表数据

```java
@Test
public void list() {
    /**
         * 使用match进行搜索
         * GET /goods/_search
         * {
         *   "query": {
         *     "match": {
         *       "title": "apple"
         *     }
         *   }
         * }
         */
    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withQuery(new MatchQueryBuilder("title", "apple"))
        .withSort(new FieldSortBuilder("publishDate").order(SortOrder.DESC))
        .build();
    List<Goods> goodsList = elasticsearchRestTemplate.queryForList(nativeSearchQuery, Goods.class);
    log.info("list document size：{}， result ：{}", goodsList.size(), goodsList);
}
```

### 4.12 多条件查询文档列表数据

```java
@Test
public void listByMultiCondition() {
    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withQuery(new BoolQueryBuilder()
                   .must(new MatchQueryBuilder("title", "apple"))
                   .must(new RangeQueryBuilder("price").from(BigDecimal.ZERO).to(new BigDecimal(12800)))
                  )
        .build();
    List<Goods> goodsList = elasticsearchRestTemplate.queryForList(nativeSearchQuery, Goods.class);
    log.info("list document size：{}， result ：{}", goodsList.size(), goodsList);
}
```

### 4.13 分页查询文档数据

```java
@Test
public void page() {
    QueryBuilder queryBuilder = new MatchQueryBuilder("title", "apple");
    NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
        .withQuery(queryBuilder)
        .withPageable(PageRequest.of(0, 10))
        .build();
    /**
         * 查看详细的请求响应日志信息，在配置文件中设置如下：
         * logging:
         *   level:
         *     tracer: trace
         *
         *     2019-11-06 21:09:11.995 TRACE 13584 --- [/O dispatcher 1] tracer: curl -iX POST 'http://localhost:9200/goods/_doc/_search?rest_total_hits_as_int=true&typed_keys=true&ignore_unavailable=false&expand_wildcards=open&allow_no_indices=true&ignore_throttled=true&search_type=dfs_query_then_fetch&batched_reduce_size=512' -d '{"from":0,"size":10,"query":{"match":{"title":{"query":"apple","operator":"OR","prefix_length":0,"max_expansions":50,"fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"boost":1.0}}},"version":true}'
         *     # HTTP/1.1 200 OK
         *     # Warning: 299 Elasticsearch-7.4.0-22e1767283e61a198cb4db791ea66e3f11ab9910 "[types removal] Specifying types in search requests is deprecated."
         *     # content-type: application/json; charset=UTF-8
         *     # content-length: 730
         *     #
         *     # {"took":0,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":2,"max_score":0.76022595,"hits":[{"_index":"goods","_type":"_doc","_id":"10001","_version":2,"_score":0.76022595,"_source":{"_class":"com.boot.example.Goods","id":10001,"name":"AppleiPhone 11","title":"Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待","price":6666,"publishDate":"2019-09-08"}},{"_index":"goods","_type":"_doc","_id":"10002","_version":1,"_score":0.73438257,"_source":{"_class":"com.boot.example.Goods","id":10002,"name":"AppleMNYH2CH/A","title":"Apple MacBook 12 | Core m3 8G 256G SSD硬盘 银色 笔记本电脑 轻薄本 MNYH2CH/A","price":12800.0,"publishDate":"2018-09-11"}}]}}
         */
    Page<Goods> page = elasticsearchRestTemplate.queryForPage(nativeSearchQuery, Goods.class);
    List<Goods> goodsList = page.getContent();
    log.info("page document size：{}，result：{}", goodsList.size(), goodsList);
}
```

### 4.14 删除文档

```java
@Test
public void delete() {
    // 删除文档
    elasticsearchRestTemplate.delete(Goods.class, "10001");
}
```

## 5.Elasticsearch聚合实战

### 5.1 统计文档数量

```java
@Test
public void count() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder().build();
    Long count = elasticsearchRestTemplate.count(searchQuery, Goods.class);
    log.info("goods count：{}", count);
}
```

### 5.2 求平均值

```java
@Test
public void avgPrice() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .addAggregation(AggregationBuilders.avg("avg_price").field("price"))
        .withIndices("goods")
        .build();
    double avgPrice = elasticsearchRestTemplate.query(searchQuery, response -> {
        Avg avg = response.getAggregations().get("avg_price");
        return avg.getValue();
    });
    log.info("avg_price：{}", avgPrice);
}
```

### 5.3 求最大值

```java
@Test
public void maxPrice() {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .addAggregation(AggregationBuilders.max("max_price").field("price"))
        .withIndices("goods")
        .build();
    double maxPrice = elasticsearchRestTemplate.query(searchQuery, response -> {
        Max max = response.getAggregations().get("max_price");
        return max.getValue();
    });
    log.info("max_price：{}", maxPrice);
}
```

## 6. ElasticsearchRestTemplate API总结

| 序号 | 功能     | 方法名         | query对象         |
| ---- | -------- | -------------- | ----------------- |
| 1    | 新增     | index          | IndexQuery        |
| 2    | 批量新增 | bulkIndex      | IndexQuery        |
| 3    | 修改     | index          | IndexQuery        |
|      | 部分修改 | update         | UpdateQuery       |
| 5    | 查询对象 | queryForObject | GetQuery          |
| 6    | 查询列表 | queryForList   | NativeSearchQuery |
| 7    | 分页查询 | queryForPage   | NativeSearchQuery |
| 8    | 删除     | delete         | DeleteQuery       |
| 9    | 统计     | count          | NativeSearchQuery |
| 10   | 平均值   | uery           | NativeSearchQuery |
| 11   | 最大值   | query          | NativeSearchQuery |

## 7. SpringBoot整合Elasticsearch原理探秘

### 7.1 ElasticsearchDataConfiguration

![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/20200107/1579176992212.jpg)

声明我们的最重要的ElasticsearchRestTemplate，ElasticsearchRestTemplate需要RestHighLevelClient

### 7.2 RestClientConfigurations

![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/20200107/1579177125472.jpg)

声明RestHighLevelClient，RestHighLevelClient需要RestClientBuilder

![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/20200107/1579177099218.jpg)

声明RestClientBuilder，RestClientBuilder由RestClient构建得到，构建使用到的hosts从RestClientProperties的uris属性中获取，uris的属性在配置文件中指定

```yaml
spring:
  elasticsearch:
    rest:
      uris: http://localhost:9200 # 指明es地址
```

通过本文你不仅学到如何整合SpringBoot和Elasticsearch，还可以通过ElasticsearchRestTemplate对文档进行增、删、改、查操作，最后你还了解了Elasticsearch自动配置原理，希望这些知识对你有帮助。
