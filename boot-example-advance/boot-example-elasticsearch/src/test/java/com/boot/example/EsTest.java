package com.boot.example;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.*;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.example.EsTest
 *
 * @author lipeng
 * @date 2019/11/6 下午3:05
 */
@SpringBootTest(classes = EsApplication.class)
@Slf4j
public class EsTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

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

    @Test
    public void getById() {
        Goods goods = elasticsearchTemplate.get("10006", Goods.class);
        log.info("get document by id result：{}", goods);
    }

    @Test
    public void filter() {
        Query matchQuery = QueryBuilders.match()
                .field("title")
                .query("apple")
                .build()
                ._toQuery();
        Query boolQuery = QueryBuilders.bool()
                .must(matchQuery)
                .filter(QueryBuilders.term().field("price").value(12800).build()._toQuery())
                .build()
                ._toQuery();
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(boolQuery)
                .build();
        SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class, IndexCoordinates.of(IndexConstant.GOODS_INDEX));
        log.info("filter document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
    }

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

    @Test
    public void listByMultiCondition() {
        Query matchQuery = QueryBuilders.match()
                .field("title")
                .query("apple")
                .build().
                _toQuery();
        Query rangeQuery = QueryBuilders.range()
                .field("price")
                .from("0")
                .to("12800")
                .build()
                ._toQuery();
        Query boolQuery = QueryBuilders.bool()
                .must(Lists.newArrayList(matchQuery, rangeQuery))
                .build()
                ._toQuery();
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(boolQuery)
                .build();
        SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
                IndexCoordinates.of(IndexConstant.GOODS_INDEX));
        log.info("list document size：{}， result ：{}", searchHits.getTotalHits(), searchHits);
    }

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

    @Test
    public void pageWithHighlight() {
        HighlightParameters highlightParameters = HighlightParameters.builder()
                .withPreTags("<span style='color:red'>")
                .withPostTags("</span>")
                .build();
        HighlightField highlightField = new HighlightField("title");
        Highlight highlight = new Highlight(highlightParameters, Lists.newArrayList(highlightField));
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withQuery(new MatchQuery.Builder().field("title").query("apple").build()._toQuery())
                .withHighlightQuery(new HighlightQuery(highlight, Goods.class))
                .withPageable(PageRequest.of(0, 10))
                .build();
        SearchHits<Goods> searchHits = elasticsearchTemplate.search(nativeQuery, Goods.class,
                IndexCoordinates.of(IndexConstant.GOODS_INDEX));
        log.info("page document size：{}，result：{}", 0, searchHits);
    }

    @Test
    public void count() {
        NativeQuery nativeQuery = new NativeQueryBuilder().build();
        Long count = elasticsearchTemplate.count(nativeQuery, Goods.class,
                IndexCoordinates.of(IndexConstant.GOODS_INDEX));
        log.info("goods count：{}", count);
    }

    @Test
    public void avgPrice() {
        Aggregation aggregation = AggregationBuilders.avg().field("price").build()._toAggregation();
        NativeQuery nativeQuery = new NativeQueryBuilder()
                .withAggregation("avg_price", aggregation)
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

    @Test
    public void delete() {
        // 删除文档
        elasticsearchTemplate.delete("10001", IndexCoordinates.of(IndexConstant.GOODS_INDEX));
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
