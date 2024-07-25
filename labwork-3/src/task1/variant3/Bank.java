package task1.variant3;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int TEST_TRANSACTIONS_AMOUNT = 10000;
    private final int[] accountBalances;
    private long nTransacts;
    private final Lock lock = new ReentrantLock();

    public Bank(int n, int initialBalance) {
        accountBalances = new int[n];
        Arrays.fill(accountBalances, initialBalance);
    }

    public void transferMoney(int sourceBalanceIndex, int destinationBalanceIndex, int amount) {
        if (sourceBalanceIndex == destinationBalanceIndex) {
            return;
        }

        lock.lock();

        try {
            accountBalances[sourceBalanceIndex] -= amount;
            accountBalances[destinationBalanceIndex] += amount;
            nTransacts++;

            if (nTransacts % TEST_TRANSACTIONS_AMOUNT == 0) {
                printBalancesSum();
            }
        } finally {
            lock.unlock();
        }
    }

    public void printBalancesSum() {
        var sum = 0;

        for (var account : accountBalances) {
            sum += account;
        }

        System.out.println("Transactions: " + nTransacts + " Sum: " + sum);
    }

    public int getAccountBalancesCount() {
        return accountBalances.length;
    }
}
