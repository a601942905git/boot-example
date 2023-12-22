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
        singleTable.setId(20001L);
        singleTable.setKey1("shardingsphere-key20001");
        singleTable.setKey2(20001);
        singleTable.setKey3("shardingsphere-key20001");
        singleTable.setKeyPart1("shardingsphere-keyPart20001");
        singleTable.setKeyPart2("shardingsphere-keyPart20001");
        singleTable.setKeyPart3("shardingsphere-keyPart20001");
        singleTable.setCommonField("commen-field20001");
        singleTableMapper.insert(singleTable);
    }

    @Test
    public void read() {
        singleTableMapper.selectList(null);
    }
}
