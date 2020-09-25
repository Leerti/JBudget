package it.unicam.cs.pa.jbudget105532.Model;

import java.time.LocalDate;

public class BasicScheduler implements Scheduler {


    @Override
    public boolean insert(ScheduledTransaction scheduled,Ledger ledger) {
           Transaction transaction = scheduled.getTransaction();
            if (check(transaction)) {
                ledger.addTransaction(transaction);

                for (int i = 0; i < ledger.getScheduledTransactions().size(); i++) {
                    if (ledger.getScheduledTransactions().get(i) == scheduled)
                        ledger.getScheduledTransactions().get(i).removeTransaction();
                    i = ledger.getScheduledTransactions().size();
                }
                return true;
            }
            return false;
    }

    @Override
    public boolean check(Transaction transaction) {
        return LocalDate.now().isAfter( transaction.getDate());
    }
}
