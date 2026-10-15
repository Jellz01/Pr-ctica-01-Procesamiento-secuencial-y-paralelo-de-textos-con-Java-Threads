package org.example;

import java.util.ArrayList;

public class Main {

    private static final String KEY_WORD = "quijote";
    private static final String SUBKEY_WORD = "ción";
    private static final int TIMES_REPETITION = 5;

    public static void main(String[] args) {
        ReadFile source = new ReadFile();
        String path = "/Users/drex/IdeaProjects/Practica1/src/Txtx/pg2000-images-3.txt";

        System.out.println("Document going to memory ...");
        ArrayList<String> originalFile = source.loadLines(path);

        if (originalFile.isEmpty()) {
            System.out.println("no file");
            return;
        }

        System.out.println("lines found: " + originalFile.size());
        System.out.println("Making it bigger x50..");
        ArrayList<String> expandedFile = source.duplicateLines(originalFile, 50);

        System.out.println("\n===== original file ===========");
        evaluateData(originalFile);

        System.out.println("\n========== expanded text ===============");
        evaluateData(expandedFile);
    }

    private static void evaluateData(ArrayList<String> lines) {
        double tSec = executionResults(lines, 1);
        double t2t = executionResults(lines, 2);
        double t4t = executionResults(lines, 4);

        System.out.println("\n--- Acceleration (Speedup: Ts / Tp) ---");
        System.out.printf("1 Thread (Sequential) : %.2f ms | Speedup: 1.00\n", tSec);
        System.out.printf("2 Threads             : %.2f ms | Speedup: %.2f\n", t2t, (tSec / t2t));
        System.out.printf("4 Threads             : %.2f ms | Speedup: %.2f\n", t4t, (tSec / t4t));
    }

    private static double executionResults(ArrayList<String> lines, int numbThreads) {
        long totalTime = 0;
        int totalWords = 0;
        int targetFound = 0;
        int subkeyFound = 0;

        for (int rep = 0; rep < TIMES_REPETITION; rep++) {
            long start = System.nanoTime();

            if (numbThreads == 1) {
                // Sequential: just call run() on main thread
                Threads single = new Threads(lines, 0, lines.size(), KEY_WORD, SUBKEY_WORD);
                single.run();
                totalWords = single.getTotalWords();
                targetFound = single.getTargetCount();
                subkeyFound = single.getSubkeyCount();
            } else {
                // Parallel: start worker threads and join()
                Threads[] threads = new Threads[numbThreads];
                int totalLines = lines.size();
                int chunkSize = totalLines / numbThreads;

                for (int i = 0; i < numbThreads; i++) {
                    int blockStart = i * chunkSize;
                    int blockEnd = (i == numbThreads - 1) ? totalLines : (i + 1) * chunkSize;
                    threads[i] = new Threads(lines, blockStart, blockEnd, KEY_WORD, SUBKEY_WORD);
                    threads[i].start();
                }

                totalWords = 0;
                targetFound = 0;
                subkeyFound = 0;

                for (int i = 0; i < numbThreads; i++) {
                    try {
                        threads[i].join();
                        totalWords += threads[i].getTotalWords();
                        targetFound += threads[i].getTargetCount();
                        subkeyFound += threads[i].getSubkeyCount();
                    } catch (InterruptedException e) {
                        System.out.println("Thread error: " + e.getMessage());
                    }
                }
            }

            long end = System.nanoTime();
            totalTime += (end - start);
        }

        double avgMs = (totalTime / (double) TIMES_REPETITION) / 1_000_000.0;
        System.out.printf("[%d Thread(s)] Avg: %.2f ms | Words: %d, '%s': %d, '%s': %d\n",
                numbThreads, avgMs, totalWords, KEY_WORD, targetFound, SUBKEY_WORD, subkeyFound);

        return avgMs;
    }
}