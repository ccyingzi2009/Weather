package com.example;

/**
 * Created by user on 16-6-12.
 */

public class 最长字符串 {
    public static void main(String[] list) throws Exception {

        String s = "wlrbbmqbhcdarzowkkyhiddqscdxrjmowfrxsjybldbefsarcbynecdyggxxpklorellnmpapqfwkhopkmco";
        System.out.println(getLongestSubstring(s));
        System.out.println('A' - ' ');

    }

    static int[] last = new int[128];

    private static String getLongestSubstring(String s) {
        int start = 0;
        int len = 0;
        char[] w = s.toCharArray();
        for (int i = 0; i < 128; i++)
            last[i] = -1;//last数组用于保存新出现的字符的下标，一开始全部初始化为-1
        for (int i = 0; i < s.length(); ++i) {
            if (last[w[i] - ' '] >= start) { //当前这个字符出现过
                if (i - start > len)
                    len = i - start;
                start = last[w[i] - ' '] + 1; //从这个字符首次出现的位置+1，重新扫描，相当于把前面抛开前面的字符串不谈
            }
            last[w[i] - ' '] = i;//更新当前字符的下标
        }
        if (len > s.length() - start)//针对没有重复字符的字符串
            return s.substring(start - len, start);
        else
            return s.substring(start - len, len - 1);
    }


    static int[] results = new int[128];

    private static int getLongestsSubstring(String s) {
        int start = 0;
        int len = 0;
        char w[] = s.toCharArray();
        for (int result : results) {
            result = -1;
        }
        for (int i = 0; i < w.length; i++) {
            if (results[w[i] - ' '] >= start){
                len = i - start;
                start = results[w[i] - ' '] + 1;
            }
            results[w[i] - ' '] = i;

        }

        return 0;
    }
}
