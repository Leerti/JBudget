package it.unicam.cs.pa.jbudget105532.Exception;
/**
 * Questa classe che estende RuntimeException definisce un eccezione.
 * <p>
 * L'eccezione qui definita viene sollevata quando si cerca di inserire
 * in un oggetto Transaction o in un oggetto Movement un oggetto Categopry gia presente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class CategoryAlreadyAdded extends RuntimeException {
    private final String  nameCategory;

    /**
     * Costruttore dell'eccezione
     *
     * @param nameCategory nome della Category che si vorrebbe
     *                     aggiungere ma che gia' è stata inserita
     */
    public CategoryAlreadyAdded(String nameCategory) {
        super("This category  is already added : "+nameCategory);
        this.nameCategory= nameCategory;
    }
    public String getMessage(){
        return super.getMessage();
    }
}
