package com.boot.example;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;

/**
 * com.boot.example.EsConfiguration
 *
 * @author lipeng
 * @date 2019/11/6 下午2:50
 */
@Configuration
public class EsConfiguration extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();
        return RestClients.create(clientConfiguration).rest();
    }

    @Bean
    @Override
    public EntityMapper entityMapper() {
        ElasticsearchEntityMapper entityMapper =
                new ElasticsearchEntityMapper(elasticsearchMappingContext(), new DefaultConversionService());
        entityMapper.setConversions(elasticsearchCustomConversions());
        return entityMapper;
    }
}
