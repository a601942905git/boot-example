package com.boot.example.convert;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * com.boot.example.convert.LocalDateConvert
 *
 * @author lipeng
 * @date 2019/1/9 下午1:20
 */
@Component
@ConfigurationPropertiesBinding
public class LocalDateConvert implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String source) {
        Objects.requireNonNull(source);
        return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
