package Services;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueService {
    private int rejectCounter;
    private int approveCounter;
    private final Queue<Integer> itemQueue;
    public boolean isQueueOpen;

    public QueueService() {
        approveCounter = rejectCounter = 0;
        isQueueOpen = true;
        itemQueue = new ArrayDeque<>();
    }

    public synchronized void push(int item) {
        final int queueSize = 3;

        if (itemQueue.size() >= queueSize) {
            rejectCounter++;
            return;
        }

        itemQueue.add(item);
        notifyAll();
    }

    public synchronized int pop() {
        while (itemQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("QueueService pop interrupted: " + e.getMessage());
                return -1;
            }
        }

        return itemQueue.poll();
    }

    public synchronized void incrementApprovedCount() {
        approveCounter++;
    }

    public double calculateRejectedPercentage() {
        return rejectCounter / (double) (rejectCounter + approveCounter);
    }

    public synchronized int getQueueLength() {
        return itemQueue.size();
    }
}