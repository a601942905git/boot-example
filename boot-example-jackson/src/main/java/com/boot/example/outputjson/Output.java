package com.boot.example.outputjson;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * com.boot.example.outputjson.Output
 *
 * @author lipeng
 * @date 2019/1/8 上午11:24
 */
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
