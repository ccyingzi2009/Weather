package com.example.Tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16-8-10.
 */
public class TupleFactory<T> {
    T var;

    public TupleFactory(Class<T> kind) {
        try {
            var = kind.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Fruit{}
    static class Apple extends Fruit{}

    public static void main(String[] args) {
        List<? extends Fruit> list = new ArrayList<Apple>();
        Fruit[] fruits = new Apple[10];
        fruits[0] = new Apple();
        fruits[1] = new Fruit();

    }
}
