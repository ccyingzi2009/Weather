package com.example.PatternTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 16-8-24.
 */
public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("href=/\"(.+)/\"");
        Matcher matcher = pattern.matcher("<a href=/\"index.html/\">主页</a>");
        if (matcher.find())
            System.out.println(matcher.group(1));

        String xx = "----aa--3----bb--cc";
        String[] result = xx.split("x");
        System.out.println(result.length);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}
