package com.example.struct;

/**
 * Created by user on 16-7-4.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 数组中任意两个数的和是否能得出目标数字
 */
public class TwoSum {

    public static void main(String[] args) {

        int a = 20;
        int b = 30;
        System.out.println(a / b);
        System.out.println(a / (float)b);
        System.out.println(a / ((float)b-5));

        Class cl1 = new ArrayList<String>().getClass();
        Class cl2 = new ArrayList<Integer>().getClass();
        System.out.println(cl1 == cl2);
        System.out.println(Arrays.toString(HashMap.class.getTypeParameters()));

    }


}
