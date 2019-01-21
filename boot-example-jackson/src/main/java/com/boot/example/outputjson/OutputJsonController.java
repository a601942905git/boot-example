package com.boot.example.outputjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.example.outputjson.OutputJsonController
 *
 * @author lipeng
 * @date 2019/1/8 上午11:28
 */
@RestController
public class OutputJsonController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/json")
    public Output output() {
        Output output = Output.builder()
                .outputId(10001)
                .outputName("test json convert")
                .build();
        restTemplate.postForEntity("http://localhost:8080/output/save", output, void.class);
        return output;
    }

    @PostMapping("/output/save")
    public void save(@RequestBody Output output) throws JsonProcessingException {
        System.out.println(new ObjectMapper().writeValueAsString(output));
    }
}
