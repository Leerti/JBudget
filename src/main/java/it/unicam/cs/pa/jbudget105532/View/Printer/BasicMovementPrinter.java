package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Movement;
import it.unicam.cs.pa.jbudget105532.View.Printer.CategoryPrinter;
import it.unicam.cs.pa.jbudget105532.View.Printer.MovementPrinter;

/**
 *  Questa classe definisce un printer per un Movement.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicMovementPrinter implements MovementPrinter {
    private CategoryPrinter cprinter;

    public BasicMovementPrinter(CategoryPrinter cprinter) {
        this.cprinter = cprinter;
    }

    /**
     * Viene ritornata la String contentente i nomi delle Category separati da una virgola.
     * Viene utilizzato un CategoryPrintere per avere una rappresentazione a stringa della singola Category
     *
     * @param movement Moveemnt da cui vengono prese le Category da visualizzare
     * @return una stringa contenente i nomi delle Category separati da una virgola
     */
    private String printCategories(Movement movement) {
        String category = "  ";
        for (int i = 0; i < movement.getCategory().size(); i++) {
            category += cprinter.stringOf(movement.getCategory().get(i)) + ", ";

        }
        category = category.substring(0, category.length() - 2);
        return category;
    }

    @Override
    public String stringOf(Movement movement) {
        return "ID Movement : \t" + movement.getID() +
                "\nDescription : \t" + movement.getDescription() +
                "\nAmount : \t" + movement.getMovement() +
                "\nType : \t" + movement.getMovementType().toString() +
                "\nCategories : \t" + printCategories(movement) +
                "\nIDAccount : \t" + movement.getAccount().getID();


    }
}
