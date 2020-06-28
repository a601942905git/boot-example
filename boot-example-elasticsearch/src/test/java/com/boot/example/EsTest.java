package com.boot.example;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * com.boot.example.EsTest
 *
 * @author lipeng
 * @date 2019/11/6 下午3:05
 */
@SpringBootTest(classes = EsApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class EsTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

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
        elasticsearchRestTemplate.index(new IndexQueryBuilder().withObject(goods).build(), IndexCoordinates.of("goods"));
        log.info("index document finish");
    }

    @Test
    public void batchInsert() {
        List<Goods> goodsList = buildGoodsList();
        List<IndexQuery> indexQueryList = new ArrayList<>();
        for (int i = 0, len = goodsList.size(); i < len; i++) {
            indexQueryList.add(new IndexQueryBuilder()
                    .withObject(goodsList.get(i)).build());
        }
        // 使用bulk方法批量索引文档
        elasticsearchRestTemplate.bulkIndex(indexQueryList, IndexCoordinates.of("goods"));
        log.info("batch index document finish");
    }

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
        elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("goods"));
        log.info("update document");
    }

    @Test
    public void partialUpdate() {
        // 构建需要更新的字段
        Map<String, Object> params = new HashMap<>();
        Document document = Document.create();
        document.put("price", new BigDecimal(8888));
        document.put("publishDate", "2019-09-08");
        UpdateQuery updateQuery = UpdateQuery.builder("10006")
                .withDocument(document)
                .build();
        elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("goods"));
        log.info("partial update document");
    }

    @Test
    public void delete() {
        // 删除文档
        elasticsearchRestTemplate.delete("10001", IndexCoordinates.of("goods"));
    }

    @Test
    public void getById() {
        GetQuery getQuery = GetQuery.getById("10002");
        Goods goods = elasticsearchRestTemplate.get(getQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("get document by id result：{}", goods);
    }

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
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("list document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
    }

    @Test
    public void listByMultiCondition() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(new BoolQueryBuilder()
                        .must(new MatchQueryBuilder("title", "apple"))
                        .must(new RangeQueryBuilder("price").from(BigDecimal.ZERO).to(new BigDecimal(12800)))
                )
                .build();
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("list document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
    }

    @Test
    public void page() {
        QueryBuilder queryBuilder = new MatchAllQueryBuilder();
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
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("page document size：{}，result：{}", searchHits, searchHits);
    }

    @Test
    public void pageWithHighlight() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", "apple"))
                .withHighlightBuilder(new HighlightBuilder().field("title").preTags("<font color='green'>").postTags("</font>"))
                .withPageable(PageRequest.of(0, 10))
                .build();
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("page document size：{}，result：{}", 0, searchHits);
    }

    @Test
    public void count() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder().build();
        Long count = elasticsearchRestTemplate.count(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));
        log.info("goods count：{}", count);
    }

    @Test
    public void avgPrice() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.avg("avg_price").field("price"))
                .build();
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));

        Map<String, Aggregation> aggregationMap = Objects.requireNonNull(searchHits.getAggregations()).asMap();
        Aggregation aggregation = aggregationMap.get("avg_price");
        ParsedAvg parsedAvg = (ParsedAvg) aggregation;
        log.info("avg_price：{}", parsedAvg.getValue());
    }

    @Test
    public void maxPrice() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .addAggregation(AggregationBuilders.max("max_price").field("price"))
                .build();
        SearchHits<Goods> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Goods.class, IndexCoordinates.of("goods"));

        Map<String, Aggregation> aggregationMap = Objects.requireNonNull(searchHits.getAggregations()).asMap();
        Aggregation aggregation = aggregationMap.get("max_price");
        ParsedMax parsedMax = (ParsedMax) aggregation;
        log.info("max_price：{}", parsedMax.getValue());
    }

    private List<Goods> buildGoodsList() {
        List<Goods> goodsList = new ArrayList<>();
        Goods goods1 = Goods.builder()
                .id(10001L)
                .name("AppleiPhone 11")
                .title("Apple iPhone 11 (A2223) 64GB 黑色 移动联通电信4G手机 双卡双待")
                .price(new BigDecimal(8688))
                .publishDate("2019-09-11")
                .build();

        Goods goods2 = Goods.builder()
                .id(10002L)
                .name("AppleMNYH2CH/A")
                .title("Apple MacBook 12 | Core m3 8G 256G SSD硬盘 银色 笔记本电脑 轻薄本 MNYH2CH/A")
                .price(new BigDecimal(12800))
                .publishDate("2018-09-11")
                .build();

        Goods goods3 = Goods.builder()
                .id(10003L)
                .name("华为Mate 30 5G")
                .title("华为 HUAWEI Mate 30 5G 麒麟990 4000万超感光徕卡影像双超级快充8GB+128GB亮黑色5G全网通版")
                .price(new BigDecimal(4999))
                .publishDate("2019-11-11")
                .build();

        Goods goods4 = Goods.builder()
                .id(10004L)
                .name("乐视TVY43")
                .title("乐视（Letv）超级电视 Y43 43英寸 1GB+8GB大存储 人工智能全高清LED平板液晶网络超薄电视机")
                .price(new BigDecimal(999))
                .publishDate("2016-06-08")
                .build();

        Goods goods5 = Goods.builder()
                .id(10005L)
                .name("爱仕达蒸锅")
                .title("爱仕达蒸锅 304不锈钢 不串味三层不锈钢复底蒸锅30CM ZS30E1Q")
                .price(new BigDecimal(389))
                .publishDate("2017-09-11")
                .build();
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
        goodsList.add(goods4);
        goodsList.add(goods5);
        return goodsList;
    }
}
