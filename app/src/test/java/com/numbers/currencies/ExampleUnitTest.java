package com.numbers.currencies;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("1", 4);
        hashMap.put("1", 5);
        System.out.println(hashMap.get("1"));

    }
}