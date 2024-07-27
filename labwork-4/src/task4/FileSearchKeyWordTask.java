package task4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;

public class FileSearchKeyWordTask extends RecursiveTask<Boolean> {
    public final String filePath;
    private final String[] keyWords;
    private final List<String> wordsList;
    private final int startIndex;
    private final int endIndex;

    public FileSearchKeyWordTask(String filePath, String[] keyWords, List<String> wordsList,
                                 int startIndex, int endIndex) {
        this.filePath = filePath;
        this.keyWords = keyWords;
        this.wordsList = wordsList;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    FileSearchKeyWordTask(String filePath, String[] keyWords) {
        this.filePath = filePath;
        this.keyWords = keyWords;
        final var inputBeginningRegex = "\\A";
        final var oneOrMoreSpacesRegex = "\\s+";

        try (var scanner = new Scanner(Paths.get(filePath), StandardCharsets.UTF_8)) {
            var content = scanner.useDelimiter(inputBeginningRegex).next();
            wordsList = List.of(content.split(oneOrMoreSpacesRegex));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        startIndex = 0;
        endIndex = wordsList.size();
    }

    @Override
    protected Boolean compute() {
        final var wordsCountThreshold = 700;

        if (endIndex - startIndex < wordsCountThreshold) {
            return wordsListContainsSearchWord();
        }

        var middleIndex = (endIndex + startIndex) / 2;
        var leftTask = new FileSearchKeyWordTask(filePath, keyWords, wordsList, startIndex, middleIndex);
        leftTask.fork();

        var rightTask = new FileSearchKeyWordTask(filePath, keyWords, wordsList, middleIndex, endIndex);
        return leftTask.join() || rightTask.compute();
    }

    private boolean wordsListContainsSearchWord() {
        final var anyPunctuationCharacterRegex = "\\p{Punct}";
        var pattern = Pattern.compile(anyPunctuationCharacterRegex);

        for (var str : wordsList) {
            var words = pattern.split(str.toLowerCase());

            for (var word : words) {
                for (var keyWord : keyWords) {
                    if (word.equals(keyWord.toLowerCase())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}