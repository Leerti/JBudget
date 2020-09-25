package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Movement;
import it.unicam.cs.pa.jbudget105532.Model.Transaction;

import java.util.List;

public interface TransactionsPrinter {
    List<String> StringOf(List<Transaction> transactions);
}
