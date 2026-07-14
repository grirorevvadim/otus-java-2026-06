package org.example;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class HelloOtus {
    static void main() {
        List<String> list = new ArrayList<>(List.of("one", "two", "three"));
        System.out.println(list);
        ImmutableList<List<String>> reverse = ImmutableList.of(list).reverse();
        System.out.println(reverse);

    }
}
