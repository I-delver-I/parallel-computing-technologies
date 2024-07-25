package task1.variant3;

public class TransferThread extends Thread {
    private final Bank bank;
    private final int sourceAccountBalanceIndex;
    private final int initialBalance;
    private static final int REPS = 1000;

    public TransferThread(Bank bank, int sourceAccountBalanceIndex, int initialBalance) {
        this.bank = bank;
        this.sourceAccountBalanceIndex = sourceAccountBalanceIndex;
        this.initialBalance = initialBalance;
    }

    @Override
    public void run() {
        while (true) {
            for (var i = 0; i < REPS; i++) {
                var destinationAccountBalanceIndex = (int) (bank.getAccountBalancesCount() * Math.random());
                var transferAmount = (int) (initialBalance * Math.random() / REPS);
                bank.transferMoney(sourceAccountBalanceIndex, destinationAccountBalanceIndex, transferAmount);
            }
        }
    }
}
