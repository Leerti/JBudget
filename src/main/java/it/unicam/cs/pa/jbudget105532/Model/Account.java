package it.unicam.cs.pa.jbudget105532.Model;

import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interfaccia definisce un oggetto di tipo Account e forza le classi che la implementeranno
 * a fornire tutti i metodi necessari alla gestione di un account.
 * <p>
 * Un Account conterrà un Nome, una Dscrizione, un Saldo iniziale e una List di Movement da cui puo ricavarsi il saldo attuale.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Account {
    /**
     * Get del nome dell'Account
     *
     * @return  il Nome dell'Account
     */
    String getName();

    /**
     * Cambia il Nome dell'Account
     *
     * @param name il nuovo Nome dell'Account
     */
    void setName(String name);

    /**
     * Get della Descrizione dell'Account
     *
     * @return String la Descrizione dell'Account
     */
    String getDescription();

    /**
     * Cambia la Descrizione dell'Account
     *
     * @param description la nuova Descrizione dell'Account
     */
    void setDescription(String description);

    /**
     * Get dell'ID dell'Account
     *
     * @return l'ID dell'Account
     */
    int getID();

    /**
     * Cambia il Saldo iniziale dell'Account
     *
     * @param initialBalance il nuovo Saldo iniziale
     */
    void setInitialBalance(double initialBalance);

    /**
     * get del saldo iniziale dell'Account
     *
     * @return il Saldo inizile dell'Account
     */
    double getInitialBalance();

    /**
     * Get del Saldo attuale dell'Account
     *
     * @return il Saldo attuale dell'Account
     */
    double getBalance();

    /**
     * Get della List di Movement associata all'Account
     *
     * @return la List di Movement associati all'Account
     */
    List<Movement> getMovement();

    /**
     * Get della List di Movement associati all'Account che soddisfano un certo Predicate
     *
     * @param p il "criterio di selezione" dei movimenti
     * @return la List di Movement associati all'Account che soddisfano un certo Predicate
     */
    List<Movement> getMovement(Predicate<Movement> p);

    /**
     * Get del Type dell'Account  (ASSET o LIABILITY)
     *
     * @return il Type dell'Account
     */
    AccountType getAccountType();

    /**
     * Aggiunge un Movement alla List dei Movement associati all'Account e aggiorna il Saldo attuale
     *
     * @param movement il Movement da aggiungere alla List
     */
    void addMovement(Movement movement);

    /**
     * Rimuove un Movement dalla List deiMovement associati all'Account e aggiorna il Saldo attuale
     *
     * @param movement il Movement da rimuovere
     */
    void deleteMovement(Movement movement);
}
