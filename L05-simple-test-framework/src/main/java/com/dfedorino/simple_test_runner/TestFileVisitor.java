package com.dfedorino.simple_test_runner;

import com.dfedorino.simple_test_runner.simple_annotation.SimpleTest;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class TestFileVisitor implements FileVisitor<Path> {
    private final List<String> testFileNames = new ArrayList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        try {
            boolean fileHasAtLeastOneTest = Files.lines(file)
                    .anyMatch(line -> line.contains("@" + SimpleTest.class.getSimpleName()));
            if (fileHasAtLeastOneTest) {
                StringBuilder fullClassName = new StringBuilder();
                fullClassName.append(file.getName(5));
                fullClassName.append(".");
                fullClassName.append(file.getName(6));
                fullClassName.append(".");
                fullClassName.append(file.getName(7));
                fullClassName.append(".");
                String fileName = file.getName(8).toString();
                fullClassName.append(fileName, 0, fileName.indexOf("."));
                testFileNames.add(fullClassName.toString());
            }
        } catch (Exception ignored) {}
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.out.println("Failed to access file: " + file.toString());
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return CONTINUE;
    }

    public List<String> getTestFileNames() {
        return testFileNames;
    }
}
