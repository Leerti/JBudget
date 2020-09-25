package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.CategoryAlreadyAdded;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryNotAdded;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa interfaccia definisce un Movement e forza le classi che la implementeranno
 * a fornire i metod necessari alla gestione  di esso.
 * <p>
 * Un movement è formato da una somma di denaro, da una desrizione, da una o piu Category
 * ed ha un Type (INCOME o EXPENSE) ed è associato ad un Account e ad una Transaction.
 * La data del Movement è la data della Transaction.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Movement {

    /**
     * Se la Transaction a cui è associato il Movement non è presente, questo metodo
     * la inizializza con il valore della Transaction passata come parametro.
     * Questo metodo viene chiamato dalla Transaction che contiene i Movement, non appena verrà creata.
     * Dopo aver inizializzato la Transaction, vengono inserite all'nterno del Movement,
     * se non presenti, tutte le Category della Transactio
     *
     * @param transaction la Transaction a cui associare il Movement
     */
    void initializeTransaction(Transaction transaction);

    /**
     * Get della descrizione del Movement
     *
     * @return la descrizione del Movement
     */
    String getDescription();

    /**
     * Cambia la descrizione del Movement
     *
     * @param description la nuova descrizione
     */
    void setDescription(String description);

    /**
     * Get del Type del Movement
     *
     * @return il Type del Movement
     */
    MovementType getMovementType();

    /**
     * Get dell'ammontare del Movement
     *
     * @return l'ammontare del Movement
     */
    double getMovement();

    /**
     * Get della Transaction a cui è associato il Movement
     *
     * @return la Transaction a cui è associato il Movement
     */
    Transaction getTransaction();

    /**
     * Get dell'Account a cui è associato il Movement
     *
     * @return l'Account a cui è associato il movimento
     */
    Account getAccount();

    /**
     * Get dell'ID del Movement
     *
     * @return l'ID del Movement
     */
    int getID();

    /**
     * Get della data del Movement (ritorna la data della Transaction a cui è associato il Movement)
     *
     * @return la data del Novement
     */
    LocalDate getDate();

    /**
     * Get della List delle Category asociate al Movement
     *
     * @return la List delle Category asociate al Movement
     */
    List<Category> getCategory();

    /**
     * Aggiunge, se non è gia presente, una Category al Movement
     *
     * @param category la nuova Category per il Movement
     * @throws CategoryAlreadyAdded se la Category che si vuole aggiungere è gia presente nel Movement
     */
    void addCategory(Category category) throws CategoryAlreadyAdded;

    /**
     * Rimuove, se presente nella List di Category del Movement, la Category passata come parametro
     *
     * @param category la Category da rimuovere
     * @throws CategoryNotAdded se la Category che si vuole rimuovere non è presente nella List delle Category del Movement
     */
    void removeCategory(Category category) throws CategoryNotAdded;

}
