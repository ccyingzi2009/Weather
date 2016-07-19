package com.example.struct;

/**
 * Created by user on 16-7-4.
 */

import java.util.HashMap;

/** 数组中任意两个数的和是否能得出目标数字 */
public class TwoSum {

    public static void main(String[] args) {

        HashMap<String, String> hash1 = new HashMap<>();
        hash1.put("11", "11");

        HashMap<String, String> hash2 = (HashMap<String, String>) hash1.clone();
        System.out.println(hash2.get("11"));
        hash2.put("11", "12");

        System.out.println(hash1.get("11"));
    }

}
