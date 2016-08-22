package com.example.synchnorize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 16-8-10.
 */
public class ConCurrent {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("1", "1");
        data.put("2", "2");
        data.put("3", "3");
        data.put("4", "4");
        data.put("5", "5");

        Map<String, String> result1 = new HashMap<>();
        result1.putAll(data);
        Map<String, String> result2 = (Map<String, String>) data.clone();
        data.remove("1");
        System.out.println(result1.entrySet().size());
        System.out.println(result2.entrySet().size());
        System.out.println(result1.get("1"));
        System.out.println(result2.get("1"));


        System.out.println(result1);
    }
}
