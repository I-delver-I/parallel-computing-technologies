package task2;

public class Main {
    public static void main(String[] args) {
        var messageQueue = new MessageQueue();

        var producerThread = new Thread(new Producer(messageQueue));
        producerThread.start();
        var consumerThread = new Thread(new Consumer(messageQueue));
        consumerThread.start();
    }
}
