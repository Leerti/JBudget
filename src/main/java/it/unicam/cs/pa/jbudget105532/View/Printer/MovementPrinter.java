package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Movement;

/**
 * Questa interfaccia definisce un printer per un Movement.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface MovementPrinter {
    /**
     * Fornisce una stringa che rappresenta un Movement
     *
     * @param movement il Movement da rappresentare
     * @return la Stringa che descrive il Movement passato come parametro
     */
    String stringOf(Movement movement);
}
