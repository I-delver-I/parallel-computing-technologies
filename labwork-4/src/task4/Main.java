package task4;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String TEXTS_DIRECTORY_PATH = "src/texts";

    public static void main(String[] args) {
        var keyWords = new String[]{"form"};

        try (var pool = ForkJoinPool.commonPool()) {
            var filePaths = pool.invoke(new DirectorySearchKeyWordTask(TEXTS_DIRECTORY_PATH,
                    keyWords));
            System.out.println("Keywords " + Arrays.toString(keyWords) + " found in files: ");
            filePaths.forEach(file -> System.out.println("- " + file));
        }
    }
}