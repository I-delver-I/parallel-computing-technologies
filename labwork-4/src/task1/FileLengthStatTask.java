package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveTask;

public class FileLengthStatTask extends RecursiveTask<HashMap<Integer, Integer>> {
    public final String filePath;
    private List<String> words;
    private int start;
    private int end;
    private final boolean isSplit;

    public FileLengthStatTask(String filePath, List<String> words, int start, int end) {
        this.filePath = filePath;
        this.words = words;
        this.start = start;
        this.end = end;
        isSplit = true;
    }

    public FileLengthStatTask(String filePath) {
        this.filePath = filePath;
        isSplit = false;
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        if (!isSplit) {
            addWordsFromFileToList();
        }

        final int threshold = 5000;

        if (end - start < threshold) {
            return getWordsData();
        }

        var midIndex = (end + start) / 2;
        var leftTask = new FileLengthStatTask(filePath, words, start, midIndex);
        var rightTask = new FileLengthStatTask(filePath, words, midIndex, end);

        leftTask.fork();
        var rightResult = rightTask.compute();
        var leftResult = leftTask.join();

        return mergeResults(leftResult, rightResult);
    }

    private HashMap<Integer, Integer> mergeResults(HashMap<Integer, Integer> leftResult,
                                                   HashMap<Integer, Integer> rightResult) {
        rightResult.forEach((key, value) -> leftResult.merge(key, value, Integer::sum));
        return leftResult;
    }

    private HashMap<Integer, Integer> getWordsData() {
        var lengthsMapper = new HashMap<Integer, Integer>();

        for (var word : words.subList(start, end)) {
            var wordLength = word.length();
            lengthsMapper.merge(wordLength, 1, Integer::sum);
        }

        return lengthsMapper;
    }

    private void addWordsFromFileToList() {
        try {
            var content = Files.readString(Paths.get(filePath));
            final var oneOrMoreSpacesRegex = "\\s+";
            words = List.of(content.split(oneOrMoreSpacesRegex));
            start = 0;
            end = words.size();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file" + filePath, e);
        }
    }
}
