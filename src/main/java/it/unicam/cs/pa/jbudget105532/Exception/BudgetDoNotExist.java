package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando in un oggetto Ledger
 * si cerca di utilizare o rimuovere un oggetto Budget che non esiste.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BudgetDoNotExist extends RuntimeException {
    private final int ID;

    /**
     * Costruttore dell'eccezione
     *
     * @param ID ID del Budget che si vorrebbe utilizzare
     *           ma che è inesistente
     */
    public BudgetDoNotExist(int ID) {
        super("This budget already existing : " + ID);
        this.ID = ID;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
