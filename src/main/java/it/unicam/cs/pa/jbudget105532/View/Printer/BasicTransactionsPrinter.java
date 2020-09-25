package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BasicTransactionsPrinter implements TransactionsPrinter {
    CategoryPrinter cprinter ;
    TransactionPrinter printer ;
    public BasicTransactionsPrinter( CategoryPrinter cprinter,TransactionPrinter printer){
        this.cprinter=cprinter;
        this.printer=printer;
    }

    @Override
    public List<String> StringOf(List<Transaction> transactions) {
        List<String> transactionsRepresentation = new ArrayList<>();


        for (Transaction transaction : transactions) {
            transactionsRepresentation.add(printer.stringOf(transaction));
        }
        return transactionsRepresentation;
    }
}
