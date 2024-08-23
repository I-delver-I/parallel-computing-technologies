package task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class FileWordsStatisticsTask extends RecursiveTask<Set<String>> {
    private final List<String> filePaths;

    public FileWordsStatisticsTask(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    protected Set<String> compute() {
        var wordSets = filePaths.stream()
                .map(this::extractWordsFromFile)
                .toList();

        if (wordSets.isEmpty()) {
            return new HashSet<>();
        }

        var commonWords = new HashSet<>(wordSets.getFirst());
        for (var i = 1; i < wordSets.size(); i++) {
            commonWords.retainAll(wordSets.get(i));
        }

        return commonWords;
    }

    private Set<String> extractWordsFromFile(String filePath) {
        final var oneOrMoreSpacesRegex = "\\s+";
        final var nonUnicodeLetterRegex = "[^\\p{L}]";

        try (var lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .flatMap(line -> Arrays.stream(line.split(oneOrMoreSpacesRegex)))
                    .map(word -> word.toLowerCase().replaceAll(nonUnicodeLetterRegex, ""))
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}