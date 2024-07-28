import Services.SystemInitializer;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws Exception {
//        executeTask1();
//        executeTask2(5);
        executeTask3();
    }

    public static void executeTask1() {
        var initializer = new SystemInitializer(false);
        var results = initializer.call();
        printStatistics(results[0], results[1]);
    }

    public static void executeTask2(int systemInstancesCount) throws Exception {
        try (var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            var tasks = new ArrayList<Callable<double[]>>();

            for (var i = 0; i < systemInstancesCount; i++) {
                tasks.add(new SystemInitializer(false));
            }

            var resultList = executor.invokeAll(tasks);
            executor.shutdown();

            double totalAverageQueueSize = 0;
            double totalFailProbability = 0;

            for (var result : resultList) {
                var info = result.get();
                totalAverageQueueSize += info[1];
                totalFailProbability += info[0];
            }

            printStatistics(totalFailProbability / resultList.size(),
                    totalAverageQueueSize / resultList.size());
        }
    }

    public static void executeTask3() {
        var initializer = new SystemInitializer(true);
        var results = initializer.call();
        System.out.println();
        printStatistics(results[0], results[1]);
    }

    private static void printStatistics(double failProbability, double averageQueueSize) {
        System.out.printf("Fail probability: %.2f%%%n", failProbability * 100);
        System.out.printf("Avg queue size: %.2f%n", averageQueueSize);
    }
}