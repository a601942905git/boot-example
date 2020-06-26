//package com.boot.example;
//
//import com.fasterxml.jackson.core.JsonEncoding;
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.common.document.DocumentField;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.convert.support.DefaultConversionService;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
//import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
//import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
//import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
//import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
//import org.springframework.data.mapping.PersistentPropertyAccessor;
//import org.springframework.data.mapping.model.ConvertingPropertyAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.util.Assert;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * com.boot.example.HighlightResultMapper
// *
// * @author lipeng
// * @date 2019/11/7 上午10:52
// */
//@Component
//@Slf4j
//public class HighlightResultMapper extends SearchHits {
//
//    @Autowired
//    private SimpleElasticsearchMappingContext mappingContext;
//
//    private final ConversionService conversionService = new DefaultConversionService();
//
//    @Override
//    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
//
//        long totalHits = response.getHits().getTotalHits();
//        float maxScore = response.getHits().getMaxScore();
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<T> results = new ArrayList<>();
//        for (SearchHit hit : response.getHits()) {
//            if (hit != null) {
//                T result;
//                String hitSourceAsString = hit.getSourceAsString();
//                if (!StringUtils.isEmpty(hitSourceAsString)) {
//                    result = mapEntity(hitSourceAsString, clazz);
//                } else {
//                    result = mapEntity(hit.getFields().values(), clazz);
//                }
//
//                // 设置高亮
//                highlightEntity(hit.getHighlightFields(), result);
//                setPersistentEntityId(result, hit.getId(), clazz);
//                setPersistentEntityVersion(result, hit.getVersion(), clazz);
//                setPersistentEntityScore(result, hit.getScore(), clazz);
//                results.add(result);
//            }
//        }
//
//        return new AggregatedPageImpl<T>(results, pageable, totalHits, response.getAggregations(), response.getScrollId(),
//                maxScore);
//    }
//
//    private <T> void highlightEntity(Map<String, HighlightField> highlightFields, T result) {
//        if (CollectionUtils.isEmpty(highlightFields)) {
//            return;
//        }
//
//        Field[] declaredFields = result.getClass().getDeclaredFields();
//        for (Field declaredField : declaredFields) {
//            HighlightField highlightField = highlightFields.get(declaredField.getName());
//            if (Objects.nonNull(highlightField) && declaredField.getType().isAssignableFrom(String.class)) {
//                try {
//                    declaredField.setAccessible(true);
//                    declaredField.set(result, highlightField.getFragments()[0].string());
//                } catch (IllegalAccessException e) {
//                    log.error("field：{} set error：", declaredField.getName(), e);
//                }
//            }
//        }
//    }
//
//    private <T> T mapEntity(Collection<DocumentField> values, Class<T> clazz) {
//        return mapEntity(buildJSONFromFields(values), clazz);
//    }
//
//    private String buildJSONFromFields(Collection<DocumentField> values) {
//        JsonFactory nodeFactory = new JsonFactory();
//        try {
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
//            generator.writeStartObject();
//            for (DocumentField value : values) {
//                if (value.getValues().size() > 1) {
//                    generator.writeArrayFieldStart(value.getName());
//                    for (Object val : value.getValues()) {
//                        generator.writeObject(val);
//                    }
//                    generator.writeEndArray();
//                } else {
//                    generator.writeObjectField(value.getName(), value.getValue());
//                }
//            }
//            generator.writeEndObject();
//            generator.flush();
//            return new String(stream.toByteArray(), Charset.forName("UTF-8"));
//        } catch (IOException e) {
//            return null;
//        }
//    }
//
//    private <T> void setPersistentEntityId(T result, String id, Class<T> clazz) {
//
//        if (clazz.isAnnotationPresent(Document.class)) {
//
//            ElasticsearchPersistentEntity<?> persistentEntity = mappingContext.getRequiredPersistentEntity(clazz);
//            ElasticsearchPersistentProperty idProperty = persistentEntity.getIdProperty();
//
//            PersistentPropertyAccessor<T> accessor = new ConvertingPropertyAccessor<>(
//                    persistentEntity.getPropertyAccessor(result), conversionService);
//
//            // Only deal with String because ES generated Ids are strings !
//            if (idProperty != null && idProperty.getType().isAssignableFrom(String.class)) {
//                accessor.setProperty(idProperty, id);
//            }
//        }
//    }
//
//    private <T> void setPersistentEntityVersion(T result, long version, Class<T> clazz) {
//
//        if (clazz.isAnnotationPresent(Document.class)) {
//
//            ElasticsearchPersistentEntity<?> persistentEntity = mappingContext.getPersistentEntity(clazz);
//            ElasticsearchPersistentProperty versionProperty = persistentEntity.getVersionProperty();
//
//            // Only deal with Long because ES versions are longs !
//            if (versionProperty != null && versionProperty.getType().isAssignableFrom(Long.class)) {
//                // check that a version was actually returned in the response, -1 would indicate that
//                // a search didn't request the version ids in the response, which would be an issue
//                Assert.isTrue(version != -1, "Version in response is -1");
//                persistentEntity.getPropertyAccessor(result).setProperty(versionProperty, version);
//            }
//        }
//    }
//
//    private <T> void setPersistentEntityScore(T result, float score, Class<T> clazz) {
//        if (clazz.isAnnotationPresent(Document.class)) {
//            ElasticsearchPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(clazz);
//            if (!entity.hasScoreProperty()) {
//                return;
//            }
//            entity.getPropertyAccessor(result).setProperty(entity.getScoreProperty(), score);
//        }
//    }
//
//}
