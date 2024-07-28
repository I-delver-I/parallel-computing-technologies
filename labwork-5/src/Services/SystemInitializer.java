package Services;

import Workers.QueueConsumer;
import Workers.QueueProducer;
import Workers.QueueMonitor;
import Workers.QueueStatistic;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SystemInitializer implements Callable<double[]> {
    private final boolean isMonitored;

    public SystemInitializer(boolean isMonitored) {
        this.isMonitored = isMonitored;
    }

    @Override
    public double[] call() {
        try (var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            var service = new QueueService();

            var statistic = new QueueStatistic(service);
            executor.execute(new QueueConsumer(service));
            QueueMonitor monitor = null;

            if (isMonitored) {
                monitor = new QueueMonitor(service);
                executor.execute(monitor);
            }

            executor.execute(new QueueProducer(service));
            executor.execute(statistic);
            executor.shutdown();
            System.out.println("System has started");

            try {
                var terminated = executor.awaitTermination(30, TimeUnit.SECONDS);

                if (!terminated) {
                    System.err.println("Executor did not terminate in the specified time.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("SystemInitializer interrupted: " + e.getMessage());
            }

            if (monitor != null) {
                monitor.stopMonitoring();
            }

            return new double[] { service.calculateRejectedPercentage(), statistic.getAverageQueueLength() };
        }
    }
}