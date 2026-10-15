package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

    public ArrayList<String> loadLines(String path) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Your file does not exist or error reading: " + e.getMessage());
        }
        return lines;
    }

    public ArrayList<String> duplicateLines(ArrayList<String> original, int times) {
        ArrayList<String> expanded = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            expanded.addAll(original);
        }
        return expanded;
    }
}