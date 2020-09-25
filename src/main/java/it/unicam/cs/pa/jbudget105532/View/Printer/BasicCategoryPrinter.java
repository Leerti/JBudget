package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Category;
import it.unicam.cs.pa.jbudget105532.View.Printer.CategoryPrinter;

/**
 *  Questa classe definisce un printer per una Category.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicCategoryPrinter implements CategoryPrinter {
    @Override
    public String stringOf(Category category) {
        return category.getName();
    }
}
