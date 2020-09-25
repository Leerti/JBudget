package it.unicam.cs.pa.jbudget105532.View.Printer;


import it.unicam.cs.pa.jbudget105532.Model.Transaction;

/**
 * Questa interfaccia definisce un printer per una Transaction.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface TransactionPrinter {

    /**
     * Fornisce una stringa che rappresenta una Transaction
     *
     * @param transaction la Transaction da rappresentare
     * @return la Stringa che descrive la Transaction passata come parametro
     */
    String stringOf(Transaction transaction);
}
