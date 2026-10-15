package org.example;

import java.util.ArrayList;

public class WordFunctions {

    public static int[] countWords(ArrayList<String> lines, int start, int end,
                                   String targetWord, String subkey) {
        int totalWords = 0;
        int targetCount = 0;
        int subkeyCount = 0;

        targetWord = targetWord.toLowerCase();
        subkey = subkey.toLowerCase();

        for (int i = start; i < end; i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;

            // split by punctuation and spaces
            String[] tokens = line.split("[ ,.;:¿?¡!\"'()\\-\\[\\]]+");

            for (String tok : tokens) {
                if (!tok.isEmpty()) {
                    totalWords++;
                    String cleanWord = tok.toLowerCase();

                    if (cleanWord.equals(targetWord)) {
                        targetCount++;
                    }
                    if (cleanWord.contains(subkey)) {
                        subkeyCount++;
                    }
                }
            }
        }

        return new int[]{totalWords, targetCount, subkeyCount};
    }
}