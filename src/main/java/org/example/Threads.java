package org.example;

import java.util.ArrayList;

public class Threads extends Thread {

    private ArrayList<String> lines;
    private int start;
    private int end;
    private String keyWord;
    private String subKeyWord;

    private int totalWords = 0;
    private int targetCount = 0;
    private int subkeyCount = 0;

    public Threads(ArrayList<String> lines, int start, int end, String keyWord, String subKeyWord) {
        this.lines = lines;
        this.start = start;
        this.end = end;
        this.keyWord = keyWord.toLowerCase();
        this.subKeyWord = subKeyWord.toLowerCase();
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) {
                continue;
            }

            // Split line by spaces and punctuation
            String[] tokens = line.split("[ ,.;:¿?¡!\"'()\\-\\[\\]]+");

            for (int j = 0; j < tokens.length; j++) {
                String tok = tokens[j];
                if (tok.length() > 0) {
                    totalWords++;
                    String cleanWord = tok.toLowerCase();

                    if (cleanWord.equals(keyWord)) {
                        targetCount++;
                    }
                    if (cleanWord.contains(subKeyWord)) {
                        subkeyCount++;
                    }
                }
            }
        }
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getTargetCount() {
        return targetCount;
    }

    public int getSubkeyCount() {
        return subkeyCount;
    }
}