package it.unicam.cs.pa.jbudget105532.Exception;

import javafx.scene.control.RadioButton;
/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando l'utente inserisce
 * una Stringa vuota.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class StringBlankException extends RuntimeException {
    /**
     * Costruttore dell'eccezione
     */
    public StringBlankException() {
        super("Please, insert something.");

    }

    public String getMessage() {
        return super.getMessage();
    }
}

