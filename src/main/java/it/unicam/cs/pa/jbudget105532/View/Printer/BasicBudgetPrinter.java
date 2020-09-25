package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Budget;
import it.unicam.cs.pa.jbudget105532.View.Printer.BudgetPrinter;
import it.unicam.cs.pa.jbudget105532.View.Printer.CategoryPrinter;

/**
 *  Questa classe  definisce un printer per un Budget
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicBudgetPrinter implements BudgetPrinter {
    private CategoryPrinter cprinter;

    /**
     * Costruttore di un BudgetPrinter
     *
     * @param cprinter il CategoryPrinter per avere la String delle Category
     */
    public BasicBudgetPrinter(CategoryPrinter cprinter) {
        this.cprinter = cprinter;
    }

    @Override
    public String stringOf(Budget budget) {
        return "ID: " + budget.getID() + "\nCategory: " + cprinter.stringOf(budget.getCategory()) + "\nAmount: " + budget.getAmount();
    }
}
