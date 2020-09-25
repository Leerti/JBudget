package it.unicam.cs.pa.jbudget105532.Model;


import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class BasicScheduledTransaction implements ScheduledTransaction {

    private Transaction transaction;

    public BasicScheduledTransaction(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public Transaction getTransaction() {
        return this.transaction;
    }


    @Override
    public void removeTransaction() {
      this.transaction = null;
    }

    @Override
    public boolean isCompleted() {
        return this.transaction==null;
    }


    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            ScheduledTransaction a = (ScheduledTransaction) o;
            if(this.transaction==null&&a.getTransaction()==null) return true;
            if (a.getTransaction().equals(this.getTransaction())) return true;
        }
        return false;
    }
}
