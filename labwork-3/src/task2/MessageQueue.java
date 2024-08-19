package task2;

public class MessageQueue {
    private int numberMessage;
    private boolean isEmpty = true;

    public synchronized int take() {
        if (isEmpty) {
            try {
                wait();
            } catch (InterruptedException _) {
            }
        }

        isEmpty = true;
        notifyAll();
        return numberMessage;
    }

    public synchronized void put(int numberMessage) {
        if (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException _) {
            }
        }

        isEmpty = false;
        this.numberMessage = numberMessage;
        notifyAll();
    }
}
