package com.boot.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lipeng
 * &#064;date 2024/6/5 16:29:52
 */
public class CollectionTest {


    @Test
    public void test() {
        List<Integer> numberList = new ArrayList<>();
        numberList.add(1);
        numberList.add(2);
        numberList.add(3);
        System.out.println("number list result1：" + numberList);

        numberList.addFirst(0);
        numberList.addLast(4);
        System.out.println("number list result2：" + numberList);

        numberList.removeFirst();
        System.out.println("number list result3：" + numberList);

        List<Integer> reversedList = numberList.reversed();
        System.out.println("reversed list：" + reversedList);
    }
}
