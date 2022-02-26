package com.dfedorino.generics;

import java.util.Collections;
import java.util.List;

import static com.dfedorino.generics.Digits.*;
import static java.util.Arrays.asList;

public class GenericsPractice {
    public static void main(String[] args) {
        System.out.println("1. Can you create a list of enum type? - Yes.");
        List<Digits> digits = asList(ONE, TWO, THREE);

        System.out.println("2. Will 'method' work with List<Integer> argument? - No, because List<Integer> not is-a List<Number>");
        List<Number> numbers = asList(1, 2, 3);
        List<Integer> integers = asList(1, 2, 3);
        method(numbers);
//        method(integers); // compile time error
        System.out.println("3. What's the difference between List<T extends Number> and List<? extends Number>?");
        System.out.println("- 'T' cannot be used while instantiation, only while declaring a parametrized class member");
        System.out.println("- '?' can be used while instantiation, it represents an unknown type. Can denote type that extends or supers othere type");
        Container<?> containerWithObjects = new Container<>();
        Container<Number> containerWithNumbers = new Container<>();
        Container<? extends Number> containerWithObjectsThatExtendNumber = new Container<>();
        System.out.println("4. What's the difference between List, List<?> and List<Object>?");
        System.out.println("- List<?> can be returned but ");
        List<?> list = (List<Object>) wouldThisWork(Collections.singletonList(new Object()));
    }

    private static void method(List<Number> numbers) {
        numbers.forEach(System.out::println);
    }

    private static List<?> wouldThisWork(List<Object> list) {
        return list;
    }
}
