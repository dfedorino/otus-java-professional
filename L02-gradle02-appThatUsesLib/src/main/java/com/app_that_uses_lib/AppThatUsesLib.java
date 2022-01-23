package com.app_that_uses_lib;

import com.google.common.collect.Lists; // <- this dependency is marked as 'api' so it's available in this module

import java.util.List;

import static java.util.Arrays.asList;

// import static org.assertj.core.api.Assertions.assertThat; // <- this dependency is marked as 'implementation' so it's not available in this module

public class AppThatUsesLib {
    public static void main(String[] args) {
        List<Integer> numbers = asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> reversedByGuava = Lists.reverse(numbers);
        System.out.println("List " + numbers + " is reverted by a transitive guava dependency -> " + reversedByGuava);
//        assertThat(reversedByGuava).isEqualTo(asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
    }
}
