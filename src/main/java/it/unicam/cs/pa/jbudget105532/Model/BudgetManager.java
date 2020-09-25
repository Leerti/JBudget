package it.unicam.cs.pa.jbudget105532.Model;
/**
 *  Questa interfaccia definisce un BudgetManager e forza le classi che la implementeranno
 *  a fornire un  metodo necessario alla creazione di un BudgetReport.
 *  Un budget manager ha il compito di creare un BudgetReport
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface BudgetManager {

    /**
     *  Genera il BudgetReport associato ad un Ledger e ad un Budget
     *
     * @param ledger il Ledger da cui prendere i Movement
     * @param budget il Budget da cui prendere la Category e l'Ammontare
     * @return un BudgetReport di un determinato Budget
     */
    BudgetReport generateReport(Ledger ledger, Budget budget);
}
