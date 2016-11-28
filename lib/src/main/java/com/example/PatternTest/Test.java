package com.example.PatternTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 16-8-24.
 */
public class Test {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("#img\\s*src=\"/img/ubb/([^#>]+)\"\\s*style=([^#>]+)>");
        Matcher matcher = pattern.matcher("不早 #img src=\"/img/ubb/emc/5.gif\" style=\"display:inline;border-style:none\"> #img src=\"/img/ubb/emc/5.gif\" style=\"display:inline;border-style:none\"> " +
                "#img src=\"/img/ubb/emc/5.gif\" style=\"display:inline;border-style:none\"> ");
        while (matcher.find())

        {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group());
        }

//        String xx = "----aa--3----bb--cc";
//        String[] result = xx.split("x");
//        System.out.println(result.length);
//        for (int i = 0; i < result.length; i++) {
//            System.out.println(result[i]);
//        }
    }
}
