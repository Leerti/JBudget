package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando l'utente inserisce
 * un comando che non è compreso nei possibili comandi dell'applicazione.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class UnknownCommandException extends Exception {

    private final String command;

    /**
     * Costruttore dell'eccezione
     *
     * @param command nome del comando inesistente
     */
    public UnknownCommandException(String command) {
        super("Unknown command: " + command);
        this.command = command;
    }

    public String getMessage() {
        return command;
    }

}


