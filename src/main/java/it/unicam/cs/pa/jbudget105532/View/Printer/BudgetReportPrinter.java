package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.BudgetReport;

/**
 * Questa interfaccia definisce un printer per un BudgetReport.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface BudgetReportPrinter {

    /**
     * Fornisce una stringa che rappresenta un BudgetReport
     *
     * @param budgetReport il BudgetReport  da rappresentare
     * @return la Stringa che descrive il BudgetReport passato come parametro
     */
    String stringOf(BudgetReport budgetReport);
}
