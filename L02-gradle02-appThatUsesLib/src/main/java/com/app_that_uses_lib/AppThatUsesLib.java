package com.app_that_uses_lib;

import com.google.common.collect.Lists;

import java.util.List;

import static java.util.Arrays.asList;

public class AppThatUsesLib {
    public static void main(String[] args) {
        List<Integer> numbers = asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("List " + numbers + " is reverted by a transitive guava dependency -> " + Lists.reverse(numbers));
    }
}
