package task2;

import java.util.Random;

public class Consumer implements Runnable {
    private final MessageQueue messageQueue;

    public Consumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void run() {
        var random = new Random();

        for (var numberMessage = messageQueue.take(); numberMessage != -1;
             numberMessage = messageQueue.take()) {
            System.out.format("Message received: %s%n", numberMessage);

            try {
                Thread.sleep(10);
            } catch (InterruptedException _) {
            }
        }
    }
}