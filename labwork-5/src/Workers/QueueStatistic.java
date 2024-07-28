package Workers;

import Services.QueueService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QueueStatistic implements Runnable {
    private final QueueService service;
    private int queueLengthsSum;
    private int scheduledTasksCount;
    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public QueueStatistic(QueueService service) {
        this.service = service;
        this.queueLengthsSum = 0;
        this.scheduledTasksCount = 0;
    }

    @Override
    public void run() {
        scheduler.scheduleAtFixedRate(() -> {
            if (!service.isQueueOpen) {
                stop();
                return;
            }

            queueLengthsSum += service.getQueueLength();
            scheduledTasksCount++;
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public double getAverageQueueLength() {
        return scheduledTasksCount == 0 ? 0 : queueLengthsSum / (double) scheduledTasksCount;
    }

    public void stop() {
        scheduler.shutdown();
    }
}