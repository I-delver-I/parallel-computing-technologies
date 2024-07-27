package task3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DirectoryWordsStatisticsTask extends RecursiveTask<Set<String>> {
    private final List<String> filePaths = new ArrayList<>();

    public DirectoryWordsStatisticsTask(String dirPath) {
        var files = new File(dirPath).listFiles();
        if (files != null) {
            for (var file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected Set<String> compute() {
        var tasks = new ArrayList<RecursiveTask<Set<String>>>();
        var filesToResolve = new ArrayList<String>();
        final var filesCountThreshold = 2;

        for (var filePath : filePaths) {
            filesToResolve.add(filePath);
            if (filesToResolve.size() >= filesCountThreshold) {
                createAndForkTask(tasks, filesToResolve);
            }
        }

        if (!filesToResolve.isEmpty()) {
            createAndForkTask(tasks, filesToResolve);
        }

        var setsToIntersect = new ArrayList<Set<String>>();
        for (var task : tasks) {
            setsToIntersect.add(task.join());
        }

        var intersectionOfSets = new HashSet<>(setsToIntersect.getFirst());
        for (var i = 1; i < setsToIntersect.size(); i++) {
            intersectionOfSets.retainAll(setsToIntersect.get(i));
        }

        return intersectionOfSets;
    }

    private void createAndForkTask(List<RecursiveTask<Set<String>>> tasks,
                                   List<String> filesToResolve) {
        var task = new FileWordsStatisticsTask(new ArrayList<>(filesToResolve));
        tasks.add(task);
        task.fork();
        filesToResolve.clear();
    }
}