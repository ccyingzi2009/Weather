package com.example;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class MyClass {

    /* snippet */
    @Root
    public static class Example {

        @Element
        private String html;

        public Example(@Element(name = "html") String title) {
            this.html = title;
        }
    }
   /* snippet */

    public static void main(String[] list) throws Exception {
        Persister persister = new Persister();
        File file = new File("/Users/user/git/Weather/lib/src/main/resource/example1.xml");
        Example example = persister.read(Example.class, file);

        System.out.println(example.html);
    }
}
