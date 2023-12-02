package com.boot.example;

import com.boot.example.entity.SingleTable;
import com.boot.example.mapper.SingleTableMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lipeng
 * &#064;date 2023/11/30 19:50:34
 */
@SpringBootTest(classes = ShardingSphereApplication.class)
public class ShardingSphereTest {

    @Autowired
    private SingleTableMapper singleTableMapper;

    @Test
    public void write() {
        SingleTable singleTable = new SingleTable();
        singleTable.setId(10000L);
        singleTable.setKey1("shardingsphere-key1");
        singleTable.setKey2(10000);
        singleTable.setKey3("shardingsphere-key3");
        singleTable.setKeyPart1("shardingsphere-keyPart1");
        singleTable.setKeyPart2("shardingsphere-keyPart2");
        singleTable.setKeyPart3("shardingsphere-keyPart3");
        singleTable.setCommonField("commen-field");
        singleTableMapper.insert(singleTable);
    }

    @Test
    public void read() {
        for (int i = 0; i < 4; i++) {
            singleTableMapper.selectList(null);
        }
    }
}
