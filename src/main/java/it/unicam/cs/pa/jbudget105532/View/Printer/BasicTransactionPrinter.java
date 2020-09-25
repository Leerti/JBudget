package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Transaction;
import it.unicam.cs.pa.jbudget105532.View.Printer.CategoryPrinter;
import it.unicam.cs.pa.jbudget105532.View.Printer.TransactionPrinter;

/**
 * Questa classe definisce un printer per una Transaction.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicTransactionPrinter implements TransactionPrinter {

    private CategoryPrinter cprinter;

    public BasicTransactionPrinter(CategoryPrinter cprinter) {
        this.cprinter = cprinter;
    }

    /**
     * Viene ritornata la stringa contentente i nomi delle Category separati da una virgola.
     * Viene utilizzato un CategoryPrintere per avere una rappresentazione a stringa della singola Category
     *
     * @param transaction la Transaction da cui vengono prese le Categoryda visualizzare
     * @return una stringa contenente i nomi delle Category separati da una virgola
     */
    private String printCategories(Transaction transaction) {
        String category = " ";
        for (int i = 0; i < transaction.getCategories().size(); i++) {
            category += cprinter.stringOf(transaction.getCategories().get(i)) + ",";
        }
        category = category.substring(0, category.length() - 1);
        return category;
    }

    @Override
    public String stringOf(Transaction transaction) {
        return "ID: " + transaction.getID() +
                "\nDate: " + transaction.getDate().toString() +
                "\nCategories: " + printCategories(transaction) +
                "\nNumbers of Movements: " + transaction.getMovements().size() +
                "\nTotal: " + transaction.getTotalAmount();

    }
}
