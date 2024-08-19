package task2;

public class Producer implements Runnable {
    private final MessageQueue messageQueue;

    public Producer(MessageQueue messageQueue) {
        this.messageQueue = messageQueue;
    }

    public void run() {
        final var numbersArraySize = 500;
        var numbers = new int[numbersArraySize];

        for (var i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        for (int number : numbers) {
            messageQueue.put(number);

            try {
                Thread.sleep(10);
            } catch (InterruptedException _) {
            }
        }

        messageQueue.put(-1);
    }
}
