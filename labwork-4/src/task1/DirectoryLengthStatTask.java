package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class DirectoryLengthStatTask extends RecursiveTask<HashMap<Integer, Integer>> {
    private final List<String> filePaths;

    public DirectoryLengthStatTask(String directoryPath) {
        try (var fileStream = Files.walk(Paths.get(directoryPath))) {
            filePaths = fileStream.filter(Files::isRegularFile)
                    .map(Path::toString).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error while reading files from directory: " + directoryPath, e);
        }
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        var fileLengthStatTasks = filePaths.stream()
                .map(FileLengthStatTask::new)
                .peek(FileLengthStatTask::fork).toList();

        var wordsFrequencyMap = new HashMap<Integer, Integer>();

        for (var fileLengthStatTask : fileLengthStatTasks) {
            var taskResult = fileLengthStatTask.join();
            taskResult.forEach((key, value) -> wordsFrequencyMap.merge(key, value, Integer::sum));
        }

        return wordsFrequencyMap;
    }
}
