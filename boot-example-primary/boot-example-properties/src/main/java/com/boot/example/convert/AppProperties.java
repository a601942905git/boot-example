package com.boot.example.convert;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * com.boot.example.convert.AppProperties
 *
 * @author lipeng
 * @date 2019/1/9 下午1:14
 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Integer id;

    private String name;

    private String version;

    private LocalDate publishDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }
}
