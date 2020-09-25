package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando in un oggetto
 * Ledger si cerca di creare un oggetto Category già esistente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class CategoryAlreadyExisting extends RuntimeException {
    private final String nameCategory;

    /**
     * Costruttore dell'eccezione
     *
     * @param nameCategory nome della Category che si vorrebbe
     *                     creare ma è gia esistente
     */
    public CategoryAlreadyExisting(String nameCategory) {
        super("This category already existing : " + nameCategory);
        this.nameCategory = nameCategory;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
