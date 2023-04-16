## 1. 前言

在工作中你或多或少听说过搜索引擎，目前最流行的搜索引擎就是Elasticsearch，本文将从Elasticsearch的安装、实战、原理分析几个方面带你领略Elasticsearch的风采

## 2. Elasticsearch介绍

Elasticsearch是一个分布式搜索引擎，可以用来存储、分析、搜索数据。

## 3. Elasticsearch安装

### 3.1 Docker安装Elasticsearch
```shell
docker network create elastic
docker pull docker.elastic.co/elasticsearch/elasticsearch:8.7.0
docker run --name es-node01 --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -t docker.elastic.co/elasticsearch/elasticsearch:8.7.0
```

### 3.2 拷贝证书
```shell
docker cp es-node01:/usr/share/elasticsearch/config/certs/http_ca.crt .
```

### 3.3 访问Elasticsearch
```shell
curl --cacert http_ca.crt -u elastic https://localhost:9200
```

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
    uris: https://localhost:9200 # 指明es地址
    username: elastic
    password: wZNVA4SytwA8qqq-C*EE
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

### 4.5 注入ElasticsearchTemplate

和往常操作其它中间件如redis、rabbitMQ一样，需要注入相应的xxxxTemplate

```java
@Autowired
private ElasticsearchTemplate elasticsearchTemplate;
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
    /*
      index用于新增或者修改整个文档
      文档不存在，执行新增操作
      文档存在，执行修改操作
      此处只需要设置索引的对象即可，底层代码会从索引对象的@Document注解中获取索引、类型以及文档的id
     */
    elasticsearchTemplate.index(new IndexQueryBuilder().withObject(goods).build(), IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("index document finish");
}
```

### 4.7 批量新增文档数据

```java
@Test
public void batchInsert() {
    List<Goods> goodsList = buildGoodsList();
    List<IndexQuery> indexQueryList = new ArrayList<>();
    for (Goods goods : goodsList) {
    indexQueryList.add(new IndexQueryBuilder()
    .withObject(goods).build());
    }
    // 使用bulk方法批量索引文档
    elasticsearchTemplate.bulkIndex(indexQueryList, IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("batch index document finish");
}
```

### 4.8 修改文档数据

```java
@Test
public void update() {
    Goods goods = Goods.builder()
    .id(10006L)
    .name("AppleiPhone 11 update")
    .title("Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待 update")
    .price(new BigDecimal(6666))
    .publishDate("2019-12-12")
    .build();
    IndexQuery indexQuery = new IndexQueryBuilder()
    .withObject(goods)
    .build();
    /*
      使用index方法来更新整个文档
      如果有字段为null，更新到es中，es文档中不会有该字段存在
      {
        "_class" : "com.boot.example.Goods",
        "id" : 10006,
        "name" : "AppleiPhone 11 update",
        "title" : "Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待 update"
      }
     */
    elasticsearchTemplate.index(indexQuery, IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("update document");
}
```

### 4.9 修改文档部分数据

```java
@Test
public void partialUpdate() {
    // 构建需要更新的字段
    Document document = Document.create();
    document.put("price", new BigDecimal(8888));
    document.put("publishDate", "2019-09-08");
    UpdateQuery updateQuery = UpdateQuery.builder("10006")
    .withDocument(document)
    .build();
    elasticsearchTemplate.update(updateQuery, IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("partial update document");
}
```

### 4.10 查询单个文档数据

```java
@Test
public void getById() {
    Goods goods = elasticsearchTemplate.get("10006", Goods.class);
    log.info("get document by id result：{}", goods);
}
```

### 4.11 查询文档列表数据

```java
@Test
public void list() {
    /*
      使用match进行搜索
      GET /goods/_search
      {
        "query": {
          "match": {
            "title": "apple"
          }
        }
      }
     */
    NativeQuery nativeQuery = new NativeQueryBuilder()
    .withQuery(new MatchQuery.Builder().field("title").query("apple").build()._toQuery())
    .withSort(sb -> sb.field(fb -> fb.field("publishDate").order(SortOrder.Desc)))
    .build();
    SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("list document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
}
```

### 4.12 多条件查询文档列表数据

```java
@Test
public void listByMultiCondition() {
    NativeQuery nativeQuery = new NativeQueryBuilder()
    .withQuery(new MatchQuery.Builder().field("title").query("apple").build()._toQuery())
    .withQuery(new RangeQuery.Builder().from("0").to("12800").build()._toQuery())
    .build();
    SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("list document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
}
```

### 4.13 分页查询文档数据

```java
@Test
public void page() {
    NativeQuery nativeSearchQuery = new NativeQueryBuilder()
    .withQuery(new MatchAllQuery.Builder().build()._toQuery())
    .withPageable(PageRequest.of(0, 10))
    .build();
    /*
      查看详细的请求响应日志信息，在配置文件中设置如下：
      logging:
        level:
          tracer: trace

          2019-11-06 21:09:11.995 TRACE 13584 --- [/O dispatcher 1] tracer: curl -iX POST 'http://localhost:9200/goods/_doc/_search?rest_total_hits_as_int=true&typed_keys=true&ignore_unavailable=false&expand_wildcards=open&allow_no_indices=true&ignore_throttled=true&search_type=dfs_query_then_fetch&batched_reduce_size=512' -d '{"from":0,"size":10,"query":{"match":{"title":{"query":"apple","operator":"OR","prefix_length":0,"max_expansions":50,"fuzzy_transpositions":true,"lenient":false,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"boost":1.0}}},"version":true}'
          # HTTP/1.1 200 OK
          # Warning: 299 Elasticsearch-7.4.0-22e1767283e61a198cb4db791ea66e3f11ab9910 "[types removal] Specifying types in search requests is deprecated."
          # content-type: application/json; charset=UTF-8
          # content-length: 730
          #
          # {"took":0,"timed_out":false,"_shards":{"total":1,"successful":1,"skipped":0,"failed":0},"hits":{"total":2,"max_score":0.76022595,"hits":[{"_index":IndexConstant.GOODS_INDEX,"_type":"_doc","_id":"10001","_version":2,"_score":0.76022595,"_source":{"_class":"com.boot.example.Goods","id":10001,"name":"AppleiPhone 11","title":"Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待","price":6666,"publishDate":"2019-09-08"}},{"_index":"goods","_type":"_doc","_id":"10002","_version":1,"_score":0.73438257,"_source":{"_class":"com.boot.example.Goods","id":10002,"name":"AppleMNYH2CH/A","title":"Apple MacBook 12 | Core m3 8G 256G SSD硬盘 银色 笔记本电脑 轻薄本 MNYH2CH/A","price":12800.0,"publishDate":"2018-09-11"}}]}}
     */
    SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeSearchQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("page document size：{}，result：{}", searchHits, searchHits);
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
    NativeQuery nativeQuery = new NativeQueryBuilder().build();
    Long count = elasticsearchTemplate.count(nativeQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    log.info("goods count：{}", count);
}
```

### 5.2 求平均值

```java
@Test
public void avgPrice() {
    NativeQuery nativeQuery = new NativeQueryBuilder()
    .withAggregation("avg_price", AggregationBuilders.avg().field("price").build()._toAggregation())
    .build();
    SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    ElasticsearchAggregations elasticsearchAggregations = (ElasticsearchAggregations) searchHits.getAggregations();
    if (Objects.isNull(elasticsearchAggregations)) {
    return;
    }

    ElasticsearchAggregation elasticsearchAggregation = elasticsearchAggregations.get("avg_price");
    if (Objects.isNull(elasticsearchAggregation)) {
    return;
    }

    double value = elasticsearchAggregation.aggregation().getAggregate().avg().value();
    log.info("avg_price：{}", value);
}
```

### 5.3 求最大值

```java
@Test
public void maxPrice() {
    NativeQuery nativeQuery = new NativeQueryBuilder()
    .withAggregation("max_price", AggregationBuilders.max().field("price").build()._toAggregation())
    .build();
    SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
    IndexCoordinates.of(IndexConstant.GOODS_INDEX));
    ElasticsearchAggregations elasticsearchAggregations = (ElasticsearchAggregations) searchHits.getAggregations();
    if (Objects.isNull(elasticsearchAggregations)) {
    return;
    }

    ElasticsearchAggregation elasticsearchAggregation = elasticsearchAggregations.get("max_price");
    if (Objects.isNull(elasticsearchAggregation)) {
    return;
    }

    double value = elasticsearchAggregation.aggregation().getAggregate().max().value();
    log.info("max_price：{}", value);
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
| 6    | 查询列表 | queryForList   | NativeQuery |
| 7    | 分页查询 | queryForPage   | NativeQuery |
| 8    | 删除     | delete         | DeleteQuery       |
| 9    | 统计     | count          | NativeQuery |
| 10   | 平均值   | uery           | NativeQuery |
| 11   | 最大值   | query          | NativeQuery |