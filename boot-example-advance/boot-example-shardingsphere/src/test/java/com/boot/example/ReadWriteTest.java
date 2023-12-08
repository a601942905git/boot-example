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
public class ReadWriteTest {

    @Autowired
    private SingleTableMapper singleTableMapper;

    @Test
    public void write() {
        SingleTable singleTable = new SingleTable();
        singleTable.setId(10002L);
        singleTable.setKey1("shardingsphere-key2");
        singleTable.setKey2(10002);
        singleTable.setKey3("shardingsphere-key2");
        singleTable.setKeyPart1("shardingsphere-keyPart2");
        singleTable.setKeyPart2("shardingsphere-keyPart2");
        singleTable.setKeyPart3("shardingsphere-keyPart2");
        singleTable.setCommonField("commen-field2");
        singleTableMapper.insert(singleTable);
    }

    @Test
    public void read() {
        singleTableMapper.selectList(null);
    }
}
