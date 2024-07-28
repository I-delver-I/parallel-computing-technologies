package Workers;

import Services.QueueService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueueMonitor implements Runnable {
    private final QueueService service;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public QueueMonitor(QueueService service) {
        this.service = service;
    }

    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!service.isQueueOpen) {
                stopMonitoring();
                return;
            }

            var queueSize = service.getQueueLength();
            var failProbability = Math.round(service.calculateRejectedPercentage() * 100.0) / 100.0;

            System.out.println("Queue size: " + queueSize);
            System.out.println("Fail probability: " + failProbability);
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void stopMonitoring() {
        scheduler.shutdown();
    }
}