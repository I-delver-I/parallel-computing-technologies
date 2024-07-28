package Workers;

import Services.QueueService;
import java.util.Random;

public class QueueConsumer implements Runnable {
    private final QueueService queueService;

    public QueueConsumer(QueueService queueService) {
        this.queueService = queueService;
    }

    @Override
    public void run() {
        var random = new Random();

        while (queueService.isQueueOpen) {
            queueService.pop();

            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Queue consumer interrupted: " + e.getMessage());
                return;
            }

            queueService.incrementApprovedCount();
        }
    }
}