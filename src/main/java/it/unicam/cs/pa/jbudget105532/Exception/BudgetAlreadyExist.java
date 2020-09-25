package it.unicam.cs.pa.jbudget105532.Exception;

import it.unicam.cs.pa.jbudget105532.Model.Category;
/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando in un oggetto Ledger
 * si cerca di creare un Budget per una Category che ha già un Budget
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BudgetAlreadyExist extends RuntimeException {
    private final Category category;

    /**
     * Costruttore del'eccezione
     *
     * @param category category di cui si vuole creare un nuovo budget
     */
    public BudgetAlreadyExist(Category category) {

        super("A budget for this category \nalready exists " + category.getName());
        this.category = category;
    }


    public String getMessage() {
        return super.getMessage();
    }
}
