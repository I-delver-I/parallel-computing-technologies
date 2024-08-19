package task2;

public class Consumer implements Runnable {
    private final MessageQueue messageQueue;

    public Consumer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void run() {
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
