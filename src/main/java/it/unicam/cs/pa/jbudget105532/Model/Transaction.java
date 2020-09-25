package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interfaccia definisce una Transaction e forza le classi che la implementeranno
 * a fornire i metod necessari alla gestione  di essa.
 * <p>
 * Una Transaction è un inseme di uno o più Movement.
 * Una Transaction contiente una List di Category e i metodi per aggiungere o togliere Category
 * dalla sua List.
 * N.B. se una Category viene aggiunta (o rimossa) dalla List, questa viene automaticaemnte aggiunta (o rimossa)
 * da tutti i Movement che compongono la Transaction.
 * La Transaction ha inoltre una data e un metodo per cambiarla e uno per farne il get
 * Il saldo della Transaction viene ricavato sommando (o sottraendo) gli ammontare dei singoli Movement che la compongono
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Transaction {
    /**
     * Get dell'ID della Transaction
     *
     * @return l'ID della Transaction
     */
    int getID();

    /**
     * Get della List di Movement associati alla Transaction
     *
     * @return la List di Movement associati alla Transaction
     */
    List<Movement> getMovements();

    /**
     * Get della List di Category associate alla Transaction
     *
     * @return la List di Category associate alla Transaction
     */
    List<Category> getCategories();

    /**
     * Aggiunge, se non è gia presente, una Category alla List delle Category della Transaction.
     * Quando si aggiunge una Category alla Transaction, viene aggiunta la stessa Category a tutti i Movement della Transaction
     *
     * @param category la Category da inserire
     * @throws CategoryAlreadyAdded se la Category che si vuole inserire è gia presente nella List delle Category della Transaction
     */
    void addCategory(Category category) throws CategoryAlreadyAdded;

    /**
     * Rimuove, se presente, la Category passata come parametro dalla List delle Category associata alla Transaction.
     * Quando si rimuove una Category dalla Transaction, viene rimossa da tutti i Movement della Transaction
     *
     * @param categoryName il nome della Category da rimuovere
     * @throws CategoryNotAdded se la Category che si vuole rimuovere non è presente nella List delle Category della Transaction
     */
    void removeCategory(String categoryName) throws CategoryNotAdded;

    /**
     * Rimuove tutte le Category della Transaction
     */
    void removeAllCategories();

    /**
     * Get della data della Transaction
     *
     * @return la data della Transaction
     */
    LocalDate getDate();

    /**
     * Cambia la data della Transaction
     *
     * @param date la nuova data della Transaction
     */
    void setDate(LocalDate date);

    /**
     * Get del saldo totale della Transaction
     *
     * @return il saldo totale della Transaction
     */
    double getTotalAmount();

    /**
     * Preso un Movement, lo aggiunge alla List dei Movement
     *
     * @param movement il Movement da inseirire
     */
    void addMovement(Movement movement);

    /**
     * Preso un Movemetn, lo rimuove dalla List dei Movement
     *
     * @param movement il Movement da rimuovere
     */
    void removeMovement(Movement movement);

    /**
     * Get della List dei Movement che soddifano un certo predicato
     * @param predicate il predicato che i Movement devono soddisfare
     * @return la List dei Movement che soddifano un certo predicato
     */
    List<Movement> getMovements(Predicate<Movement> predicate);
}
