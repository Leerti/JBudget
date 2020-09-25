package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando si cerca
 * di rimuovere da un oggetto Transaction o da un oggetto Movement
 * un oggetto Category che non è presente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class CategoryNotAdded extends RuntimeException {
    private final String nameCategory;

    /**
     * Costruttore dell'eccezione
     *
     * @param nameCategory nome della Category che si vorrebbe rimuovere
     *                     ma non è presente nella Transaction o nel Movement
     */
    public CategoryNotAdded(String nameCategory) {
        super("This category was not added : " + nameCategory);
        this.nameCategory = nameCategory;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
