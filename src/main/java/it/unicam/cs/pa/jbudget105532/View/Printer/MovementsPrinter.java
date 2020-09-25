package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Movement;

import java.util.List;

public interface MovementsPrinter {
    List<String> StringOf(List<Movement> movements);
}
