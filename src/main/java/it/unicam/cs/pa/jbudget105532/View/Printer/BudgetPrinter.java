package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Budget;

/**
 * Questa interfaccia definisce un printer per un Budget.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface BudgetPrinter {
    /**
     * Fornisce una stringa che rappresenta un Budget
     *
     * @param budget il Budget da rappresentare
     * @return la Stringa che descrive il Budget  passato come parametro
     */
    String stringOf(Budget budget);
}
