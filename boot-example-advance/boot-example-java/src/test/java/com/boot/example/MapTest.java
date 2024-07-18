package com.boot.example;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @author lipeng
 * &#064;date 2024/6/5 16:54:49
 */
public class MapTest {

    @Test
    public void test() {
        LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("b", 1);
        linkedHashMap.put("c", 2);
        linkedHashMap.put("d", 3);
        System.out.println("linkedHashMap result1：" + linkedHashMap);

        linkedHashMap.putFirst("a", 0);
        System.out.println("linkedHashMap result2：" + linkedHashMap);

        linkedHashMap.putLast("e", 4);
        System.out.println("linkedHashMap result3：" + linkedHashMap);

        Map.Entry<String, Integer> firstEntry = linkedHashMap.firstEntry();
        System.out.println("first entry：" + firstEntry);

        Map.Entry<String, Integer> lastEntry = linkedHashMap.lastEntry();
        System.out.println("last entry：" + lastEntry);
    }
}
