package it.unicam.cs.pa.jbudget105532.Exception;

/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando si cerca
 * di utilizzare o rimuovere un oggetto Category inesistente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class CategoryDoNotExist extends RuntimeException {
    private final String nameCategory;

    /**
     * Costruttore dell'eccezione
     *
     * @param nameCategory nome della Category che si vorrebbe
     *                    rimuovere ma che è inesistente
     */
    public CategoryDoNotExist(String nameCategory) {
        super("this category do not exist : " + nameCategory);
        this.nameCategory = nameCategory;
    }

    public String getMessage() {
        return super.getMessage();
    }
}
