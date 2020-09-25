package it.unicam.cs.pa.jbudget105532.View.Printer;


import it.unicam.cs.pa.jbudget105532.Model.Category;

/**
 * Questa interfaccia definisce un printer per una Category.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface CategoryPrinter {

    /**
     * Fornisce una stringa che rappresenta una Category
     *
     * @param category la Category da rappresentare
     * @return la Stringa che descrive la Category passata come parametro
     */
    String stringOf(Category category);
}
