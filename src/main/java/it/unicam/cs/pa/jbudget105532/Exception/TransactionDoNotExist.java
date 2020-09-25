package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * L'eccezione qui definita viene sollevata quando in un oggetto Ledger
 * si cerca di utilizare o rimuovere un oggetto Transaction che non esiste.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class TransactionDoNotExist extends RuntimeException {
    private final int ID;

    /**
     * Costruttore dell'eccezione
     *
     * @param ID della Transaction che si vorrebbe utilizzare
     *           ma che è inesistente
     */
    public TransactionDoNotExist(int ID) {
        super("This transaction do not exists " + ID);
        this.ID = ID;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
