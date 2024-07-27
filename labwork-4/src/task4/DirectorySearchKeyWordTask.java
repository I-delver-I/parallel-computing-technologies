package task4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectorySearchKeyWordTask extends RecursiveTask<List<String>> {
    private final List<String> filePaths = new ArrayList<>();
    private final String[] keyWords;

    public DirectorySearchKeyWordTask(String dirPath, String[] keyWords) {
        this.keyWords = keyWords;
        var files = new File(dirPath).listFiles();

        if (files != null) {
            for (var file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected List<String> compute() {
        var filesTasks = new ArrayList<FileSearchKeyWordTask>();

        for (var filePath : filePaths) {
            var task = new FileSearchKeyWordTask(filePath, keyWords);
            filesTasks.add(task);
            task.fork();
        }

        var results = new ArrayList<String>();

        for (var task : filesTasks) {
            if (task.join()) {
                results.add(task.filePath);
            }
        }

        return results;
    }
}