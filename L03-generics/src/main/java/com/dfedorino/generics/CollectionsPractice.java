package com.dfedorino.generics;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CollectionsPractice {
    public static void main(String[] args) {
        System.out.println("1. How to get an element from a set by index?");
        int index = 7;
        Set<Integer> integers = IntStream.range(0, 10).boxed().collect(Collectors.toSet());
    }
}
