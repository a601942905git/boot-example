package com.boot.example.complex;

import com.boot.example.properties.StudentProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * com.boot.example.complex.ComplexStudentProperties
 *
 * @author lipeng
 * @date 2019/1/9 上午11:05
 */
@ConfigurationProperties(prefix="complex")
public class ComplexStudentProperties {

    private List<StudentProperties> list;

    private Map<String, StudentProperties> map;

    public List<StudentProperties> getList() {
        return list;
    }

    public void setList(List<StudentProperties> list) {
        this.list = list;
    }

    public Map<String, StudentProperties> getMap() {
        return map;
    }

    public void setMap(Map<String, StudentProperties> map) {
        this.map = map;
    }
}
