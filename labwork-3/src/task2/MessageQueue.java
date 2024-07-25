package task2;

public class MessageQueue {
    private int numberMessage;
    private boolean empty = true;

    public synchronized int take() {
        if (empty) {
            try {
                wait();
            } catch (InterruptedException _) {
            }
        }

        empty = true;
        notifyAll();
        return numberMessage;
    }

    public synchronized void put(int numberMessage) {
        if (!empty) {
            try {
                wait();
            } catch (InterruptedException _) {
            }
        }

        empty = false;
        this.numberMessage = numberMessage;
        notifyAll();
    }
}
