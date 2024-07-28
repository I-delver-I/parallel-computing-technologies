package Workers;

import Services.QueueService;
import java.util.Random;

public class QueueProducer implements Runnable {
    private final QueueService service;

    public QueueProducer(QueueService service) {
        this.service = service;
    }

    @Override
    public void run() {
        var random = new Random();
        var startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        final var producerLifeDuration = 10000;

        while (elapsedTime < producerLifeDuration) {
            service.push(random.nextInt(100));

            try {
                Thread.sleep(random.nextInt(15));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Queue producer interrupted: " + e.getMessage());
                return;
            }

            elapsedTime = System.currentTimeMillis() - startTime;
        }

        service.isQueueOpen = false;
    }
}