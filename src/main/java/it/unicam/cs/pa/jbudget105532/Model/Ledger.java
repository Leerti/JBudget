package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interfaccia definisce un Ledger e forza le classi che la implementeranno
 * a fornire tutti i  metodi necessari alla creazione e gestione  di esso.
 * <p>
 * Un Ledger contiene i metodi per aggiungere o rimuovere una Transaction dalla sua List di Transaction,
 * una volta aggiunta o rimossa una Transaction viene richiamato per ogni Account
 * associato a ogni Movement all'interno della Transaction, il metodo add/remove movement
 * (inserendo come parametro il movimento stesso da cui si ricava l'account).
 * Un Ledger contiene anche i metodi per aggiungere Account dalla sua List di Account.
 * Contiene i metodi per aggiungere  o rimuovere Category dalla sua List di Category.
 * N.B. se una Category viene rimossa dal Ledger, viene rimossa anche da tutte le Transaction e/o Movement in cui è presente.
 * Un Ledger ha al suo interno metodi per aggiungere o rimuovere Budget.
 * Un Ledger contiene infine una List di Sheduled Transaction e ha i metodi per aggiungerne di nuove alla sua List
 * o di "trasformarle" in Transaction "effettive".
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Ledger {
    void removeScheduledTransaction();

    /**
     * Get della List di tutte le Transaction associate al Ledger
     *
     * @return la List di Transaction associate al Ledger
     */
    List<Transaction> getTransactions();

    /**
     * Restituisce, se presente la Transaction corrispondente all'ID inserito
     * La Transaction viene cercata sia tra le Normali Transaction che tra quelle Scheduled
     *
     * @param ID l'ID della Transaction da restituire
     * @return la Transaction corrispondente all'ID inserito
     * @throws TransactionDoNotExist se l'ID inserito non corrisponde a nessuna Transaction
     */
    Transaction getTransaction(int ID) throws TransactionDoNotExist;

    /**
     * Aggiunge una Transaction alla List delle Transaction
     *
     * @param transaction la Transaction da inserire
     */
    void addTransaction(Transaction transaction);

    /**
     * Preso un Movement e un ID di una Transaction, aggiunge il Movement alla Transaction e all'Account a cui è associato
     * @param movement il Moviemnto da inserire nel Ledger
     * @param transaction la Transaction a cui associarlo
     */
    void addMovementToTransaction(Movement movement, int transaction) ;

    /**
     * Preso l'ID di un Movement vien rimosso il Movement sia dalla Transaction che dall'Account a cui è associato.
     *
     * @param movement l'ID del Movement t da rimuovere
     */
    void removeMovementFromTransaction(int movement) ;

    /**
     * Rimuove dal Ledger ,se esite la Transaction passata come parametro.
     * Rimuove anche tutti i Movement collegati alla Transaction
     *
     * @param transaction la Transaction da rimuovere
     * @throws TransactionDoNotExist se la Transaction da rimuovere non esiste
     */
    void removeTransaction(Transaction transaction) throws TransactionDoNotExist;

    /**
     * Restituisce una List di Transaction che soddisfano un certo Predicate
     *
     * @param predicate il Predicate che devono soddisfare le Transaction
     * @return la List di Transaction che soddisfano un certo Predicate
     */
    List<Transaction> getTransactions(Predicate<Transaction> predicate);

    /**
     * Get del Movement corrispondente all'ID passato come parametro
     *
     * @param ID l'ID del Movement da ricercare
     * @return il Movement con l'ID inserito
     */
    Movement getMovement(int ID);

    /**
     * Get della List di tutte le Category presenti nel Ledger
     *
     * @return la List di tutte le Category presenti nel Ledger
     */
    List<Category> getCategories();

    /**
     * Restituisce, se esiste una Category avente il nome uguale alla stringa passata come parametro
     *
     * @param name il nome della Category da restituire
     * @return la Category con lo stesso nome inserito
     * @throws CategoryDoNotExist se la Category non esiste
     */
    Category getCategory(String name) throws CategoryDoNotExist;

    /**
     * aggiunge, se gia non è presente una categoria con lo stesso nome, una categoria
     *
     * @param name nome della categoria da inserire
     * @return true se la categoria è stata aggiunta
     * @throws CategoryAlreadyExisting se la categoria che si vuole creare è gia presente nel ledger
     */
    boolean addCategory(String name) throws CategoryAlreadyExisting;

    /**
     * Rimuove, se presente, una Category dal Ledger e da tutte le Transaction e i Movement in cui è presente
     *
     * @param name il nome della Category da rimuovere
     * @throws CategoryNotAdded   se la Category non è presente in una Transaction o in un Movement
     * @throws CategoryDoNotExist se la Category non esiste nel Ledger
     */
    void removeCategory(String name) throws CategoryNotAdded, CategoryDoNotExist, BudgetDoNotExist;

    /**
     *
     * Aggiunge una ScheduledTransaction alla List delle ScheduledTransaction
     *
     * @param scheduledTransaction transa
     */
    void addScheduledTransaction(ScheduledTransaction scheduledTransaction);

    /**
     * Get della List delle SheduledTransaction
     *
     * @return la List delle SheduledTransaction
     */
    List<ScheduledTransaction> getScheduledTransactions();

    ScheduledTransaction getScheduledTransaction(int ID)throws TransactionDoNotExist;


    /**
     * Get della List degli Account presenti nel Ledger
     *
     * @return la List degli Account presenti nel Ledger
     */
    List<Account> getAccounts();

    /**
     * Restituisce, se esiste, un Account corrispondente all'ID inserito
     *
     * @param ID l'ID da ricercare
     * @return l'Account corripondente all'ID inserito
     * @throws AccountDoNotExist se l'ID inserito non corrisponde a nessuno degli
     *                           Account presenti nel Ledger
     */
    Account getAccount(int ID) throws AccountDoNotExist;

    /**
     * Inserisce un nuovo account nel Ledger
     *
     * @param name           il nome del nuovo Account
     * @param description    la descrizione del nuovo Account
     * @param accountType    il Type del nuovo Account
     * @param initialBalance il saldo inizioale del nuovo Account
     * @return l'Account appena creato
     */
    Account addAccount(String name, String description, AccountType accountType, double initialBalance);

    /**
     * Controlla se il Ledger è "acceso"
     *
     * @return true se il Ledger è "acceso", false se è "spento"
     */
    boolean isOn();

    /**
     * "spegne il Ledger impostando a false il parametro  on
     */
    void turnOff();

    /**
     * Inserisce un nuovo Budget per una determinata Category
     *
     * @param budget il nuovo Budget da inserire
     */
    void addBudget(Budget budget) throws BudgetAlreadyExist;

    /**
     * Restituisce, se presente, il Budget corrispondente all'ID inserito
     *
     * @param ID l'ID del Budget da ricercare
     * @return il Budget corrispondente all'ID inserito
     * @throws BudgetDoNotExist se l'ID inserito non corrisponde a nessun Budget
     */
    Budget getBudget(int ID) throws BudgetDoNotExist;

    /**
     * Get della List di tutti i Budget presenti all'interno del Ledger
     *
     * @return la List di tutti i Budget presenti all'interno del Ledger
     */
    List<Budget> getBudgets();

    /**
     * Elimina dalla List dei Budget, se presente, il Budget passato come parametro
     *
     * @param budget il Budget da eliminare
     * @throws BudgetDoNotExist se il Budget passato come parametro non è presente
     *                          all'interno della List dei Budget
     */
    void removeBudget(Budget budget) throws BudgetDoNotExist;

    /**
     * Preso un Movement e un ID di una Transaction,
     * aggiunge il Movement alla Transaction presente nella ScheduledTransaction che contiene l'ID della Transaction inseritp
     * @param movement il Moviemnto da inserire nel Ledger
     * @param idTransaction la ScheduledTransaction a cui associarlo
     */
    void addMovementToScheduledTransaction(Movement movement,int idTransaction);
    /**
     * Restituisce una List di Transaction appartenenti alle ScheduledTransaction che soddisfano un certo Predicate
     *
     * @param predicate il Predicate che devono soddisfare le Transaction
     * @return la List di Transaction che soddisfano un certo Predicate
     */
    List<Transaction> getScheduledTransaction(Predicate predicate);
}
