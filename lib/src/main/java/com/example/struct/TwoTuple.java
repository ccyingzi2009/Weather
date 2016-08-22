package com.example.struct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16-8-4.
 */
public class TwoTuple<A, B> {
    private final A a;
    private final B b;

    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    //泛型方法的使用
    public static <T> List<T> makeList(T t) {
        return new ArrayList<T>();
    }
}
