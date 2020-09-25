package it.unicam.cs.pa.jbudget105532.Model;
/**
 * Questa classe definisce un BudgetManager.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */

public class BasicBudgetManager implements BudgetManager {


    @Override
    public BudgetReport generateReport(Ledger ledger, Budget budget) {
        return new BasicBudgetReport(budget,ledger);
    }
}
