package com.boot.example.spi;

/**
 * @author lipeng
 * &#064;date 2024/6/12 16:46:36
 */
public class FileSearch implements Search {

    @Override
    public void search() {
        System.out.println("file search");
    }
}
