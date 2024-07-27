package task3;

import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String TEXTS_DIRECTORY_PATH = "src/texts";

    public static void main(String[] args) {
        try (var pool = ForkJoinPool.commonPool()) {
            var words = pool.invoke(new DirectoryWordsStatisticsTask(TEXTS_DIRECTORY_PATH));
            System.out.println("Common words for all files: " + words);
        }
    }
}