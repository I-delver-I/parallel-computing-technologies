package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String TEXTS_DIRECTORY_PATH = "src/texts";

    public static void main(String[] args) {
        measureTime(Main::parallelAnalyseDirectory, "parallel");
        measureTime(Main::sequentialAnalyseDirectory, "sequential");
    }

    private static void sequentialAnalyseDirectory() {
        try (var paths = Files.walk(Paths.get(TEXTS_DIRECTORY_PATH))) {
            var filePaths = paths.filter(Files::isRegularFile).toList();
            var wordFrequencyMap = new HashMap<Integer, Integer>();

            for (var filePath : filePaths) {
                try {
                    var content = Files.readString(filePath);
                    final var oneOrMoreSpacesRegex = "\\s+";
                    var words = content.split(oneOrMoreSpacesRegex);

                    for (var word : words) {
                        var length = word.length();
                        wordFrequencyMap.merge(length, 1, Integer::sum);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Sequential analysis completed");
            printStatistics(wordFrequencyMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void parallelAnalyseDirectory() {
        try (var pool = ForkJoinPool.commonPool()) {
            var task = new DirectoryLengthStatTask(TEXTS_DIRECTORY_PATH);
            var wordFrequencyMap = pool.invoke(task);
            System.out.println("Parallel analysis completed");
            printStatistics(wordFrequencyMap);
        }
    }

    private static void measureTime(Runnable runnable, String type) {
        var startTime = System.currentTimeMillis();
        runnable.run();
        var endTime = System.currentTimeMillis();

        System.out.println("Time taken for " + type + " analyse " + ": " + (endTime - startTime) + " ms");
        System.out.println();
    }

    public static void printStatistics(HashMap<Integer, Integer> map) {
        var totalWords = map.values().stream().mapToInt(Integer::intValue).sum();
        var totalCharacters = map.entrySet().stream().mapToDouble(e -> e.getKey() * e.getValue()).sum();
        var averageWordLength = totalCharacters / totalWords;

        var variance = map.entrySet().stream()
                .mapToDouble(e -> e.getValue() * Math.pow(e.getKey() - averageWordLength, 2))
                .sum() / totalWords;
        var standardDeviation = Math.sqrt(variance);

        System.out.println("Number of words: " + totalWords);
        System.out.println("Variance of the word length: " + Math.round(variance * 100.0) / 100.0);
        System.out.println("Average length of word: " + Math.round(averageWordLength * 100.0) / 100.0);
        System.out.println("Standard deviation: " + Math.round(standardDeviation * 100.0) / 100.0);
    }
}