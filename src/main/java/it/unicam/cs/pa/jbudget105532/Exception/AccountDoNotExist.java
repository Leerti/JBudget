package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando in un oggetto Ledger
 * si cerca di utilizare un oggetto Account che non esiste.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class AccountDoNotExist extends RuntimeException {
    private final int ID;

    /**
     * Costruttore del'eccezione
     *
     * @param ID ID dell'Account che si vorrebbe utilizzare
     *           ma che è inesistente
     */
    public AccountDoNotExist(int ID) {
        super("This account do not exists " + ID);
        this.ID = ID;
    }


    public String getMessage() {
        return super.getMessage();
    }
}
