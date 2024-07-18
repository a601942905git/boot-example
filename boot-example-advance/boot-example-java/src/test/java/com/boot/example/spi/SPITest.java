package com.boot.example.spi;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * @author lipeng
 * &#064;date 2024/6/12 16:46:03
 */
public class SPITest {

    @Test
    public void test() {
        ServiceLoader<Search> serviceLoader = ServiceLoader.load(Search.class);
        for (Search search : serviceLoader) {
            search.search();
        }
    }
}
