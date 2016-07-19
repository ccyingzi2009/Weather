package com.example;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Created by user on 16-6-14.
 */

/**
 * ArrayList: 相当于一个大小可以改变的数组,当空间不足时,以50%的的当前大小动态增长,
 * linkList :是一个双向链表,添加和删除操作比arrayList性能更好,get、set较弱,
 * Vector: 属于强同步类,如果是线程安全的,则最好选择arrayList。基本方法都是synchronized。了解s
 * Vector和ArrayList在添加的时候每次需要更大的空间,Vector需要双倍空间,ArrayList需要50%的额外空间。
 */

public class SList {
    public static void main(String args[]) {
        List<String> arrayList = new ArrayList<>();
        List<String> linkList = new LinkedList<>();
        Vector<String> vercor = new Vector<>();
        System.out.println(100 >> 1); //左移动。
        Set<String> set = new LinkedHashSet<>();
    }
}
