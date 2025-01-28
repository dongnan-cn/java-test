package com.nan.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NioTest {
    public static void main(String[] args) {
        Path directory = Paths.get("newyear/src/main/resources");
        Path filePath = directory.resolve("jobs.txt");
        try {
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            } else {
                if (!Files.exists(filePath)) {
                    Files.createFile(filePath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (!Files.exists(filePath)) {
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try (var writer = Files.newBufferedWriter(filePath)) {
            writer.write("abc");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
