package task1.variant1;

import java.util.Arrays;

public class Bank {
    public static final int TEST_TRANSACTIONS_AMOUNT = 10000;
    private final int[] accountBalances;
    private long nTransacts;

    public Bank(int balancesCount, int initialBalance) {
        accountBalances = new int[balancesCount];
        Arrays.fill(accountBalances, initialBalance);
    }

    public synchronized void transferMoney(int sourceBalanceIndex, int destinationBalanceIndex, int amount) {
        if (sourceBalanceIndex == destinationBalanceIndex) {
            return;
        }

        if (accountBalances[sourceBalanceIndex] < amount) {
            return;
        }

        accountBalances[sourceBalanceIndex] -= amount;
        accountBalances[destinationBalanceIndex] += amount;
        nTransacts++;

        if (nTransacts % TEST_TRANSACTIONS_AMOUNT == 0) {
            printBalancesSum();
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
