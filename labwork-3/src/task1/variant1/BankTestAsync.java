package task1.variant1;

public class BankTestAsync {
    public static final int ACCOUNTS_COUNT = 10;
    public static final int INITIAL_BALANCE = 10000;

    public static void main(String[] args) throws InterruptedException {
        var bank = new Bank(ACCOUNTS_COUNT, INITIAL_BALANCE);

        for (var i = 0; i < ACCOUNTS_COUNT; i++) {
            var t = new TransferThread(bank, i, INITIAL_BALANCE);
            t.setPriority(Thread.NORM_PRIORITY + i % 2);
            t.start();
        }
    }
}
