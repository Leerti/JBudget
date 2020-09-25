package it.unicam.cs.pa.jbudget105532.Model;

import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interfaccia definisce un BudgetReport e forza le classi che la implementeranno
 * a fornire tutti i metodi necessari alla gestione di un BudgetReport.
 * <p>
 * Un BudgetReport contiene un Budget, da cui ricava la Category e un Ledger da cui ricava
 * i Movement associati alla Category del Budget.
 * il BudgetReport riesce a calcolare lo scostamento di Income/Expense attuali per una determinata
 * Category rispetto al Budgtet prefissato per la stessa.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface BudgetReport {
    /**
     * Get del Budget a cui è associato il BudgetReport
     *
     * @return il Budget a cui è associato il BudgetReport
     */
    Budget getBudget();

    /**
     * Get della Category a cui è associato il Budget del BudgetReport
     *
     * @return la Category del Budget
     */
    Category getCategory();

    /**
     * Crea il Predicate per selezionare i Movement associati alla Category del Budget a cui è associato il BudgetReport
     *
     * @return il Predicate per selezionare i Movement associati alla Category del Budget
     */
    Predicate<Movement> Predicate();

    /**
     * Calcola quanto attualmente si è speso/guadagnato per la Category associata al Budget
     *
     * @return l'ammontare di spese/guadagno associato alla Category
     */
    double calculateActual();

    /**
     * Calcola lo scostamento del totale attualmente speso/guadagnato dal totale associato al Budget
     *
     * @return lo scostamento del totale attualmente speso/guadagnato dal totale associato al Budget
     */
    double calculateDeviation();

    /**
     * Restituisce la lista deli anni in cui è presente almeno un Movement che contiene la Category del Budget
     *
     * @return lista degli anni in cui sono stat effettuati spostamenti di denaro inerenti alla Category del Budget
     */
    List<Integer> years();

    /**
     * Dato un mese e una lista di Movement, calcola per quel determianto mese quante sono state le entrate
     *
     * @param month il mese per cui si vuole sapere le entrate
     * @param movements la List di Movement da controllare
     * @return il totale delle entrate per il mese inserito
     */
    double calculateIncomePerMonth(int month, List<Movement> movements);

    /**
     * Dato un mese e una lista di Movement, calcola per quel determianto mese quante sono state le uscite
     *
     * @param month il mese per cui si vuole sapere le uscite
     * @param movements la List di Movement da controllare
     * @return il totale delle uscite per il mese inserito
     */
    double calculateExpensePerMonth(int month, List<Movement> movements);

    /**
     * Dato un mese e una lista di Movement, calcola per quel determianto mese quanto è il
     * totale degli spostamenti di denaro (usicte-entrate)
     *
     * @param month il mese per cui si vuole sapere il totale
     * @param movements la List di Movement da controllare
     * @return il totale  per il mese inserito
     */
    double calculateTotalPerMonth(int month, List<Movement> movements);

    /**
     * Preso un anno, restituisce una  List di Movemente di tutto il Ledger.
     * i Movement contenuti in questa List soddisfano due requisiti: il loro anno è
     * uguale all'anno inserito e contengono la Category del Budget
     *
     * @param year l'anno per cui si vogliono selezionare i movimenti
     * @return la List di Movement che soddifano i due reqisiti
     */
    List<Movement> selectedMovement(Integer year);
}
