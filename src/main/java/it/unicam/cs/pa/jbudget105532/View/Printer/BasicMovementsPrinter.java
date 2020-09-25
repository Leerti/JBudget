package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Movement;

import java.util.ArrayList;
import java.util.List;

public class BasicMovementsPrinter implements MovementsPrinter {
    CategoryPrinter cprinter ;
    MovementPrinter printer ;

    public BasicMovementsPrinter(CategoryPrinter cprinter,MovementPrinter printer){
        this.cprinter=cprinter;
        this.printer=printer;
    }
    @Override
    public List<String>StringOf(List<Movement> movements){
        List<String> movementsRepresentation = new ArrayList<>();
        for (Movement movement : movements) {
            movementsRepresentation.add(printer.stringOf(movement));
        }
        return movementsRepresentation;
    }
}
