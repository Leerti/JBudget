package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Exception.*;
import it.unicam.cs.pa.jbudget105532.Model.*;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa interaccia definisce un Controller ed ha la responsabilita di farsi
 * passare i dati e di "immetterli" nel Ledger dopo i dovuti controllli
 * o di passare i dati dopo una richiesta.
 * <p>
 * Un controller è un oggetto che ha al suo interno un Ledger
 *
 *  @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Controller {

    /**
     * Restituisce il numero totale degli Account
     *
     * @return il numero totale degli Account
     */
    int getNumbersOfAccount();

    /**
     * Restituisce il numero totale delle Transaction
     *
     * @return il numero totale delle Transaction
     */
    int getNumbersOfTransactions();

    /**
     * Restituisce il numero totale delle Category
     *
     * @return il numero totale delle Category
     */
    int getNumbersOfCategory();

    /**
     * Restituisce il numero totale dei Budget
     *
     * @return il numero totale dei Budget
     */
    int getNumbersOfBudgets();

    /**
     * Richiama due metodi del Ledger per verificare se ci sono
     * ScheduledTransaction che devono essere rese effettive
     */
    void checkScheduledTransaction();

    /**
     * Richiama la classe per il salvataggio dei dati
     */
    void save();

    /**
     * Controlla se il Ledger è "acceso"
     *
     * @return true se è acceso
     */
    boolean isOn();

    /**
     * Spegne il Ledger
     */
    void turnOff();

    /**
     * Get del Ledger contenuto all'interno del Controller
     *
     * @return il Ledger contenuto all'interno del Controller
     */
    Ledger getLedger();

    /**
     * Preso un hashmap contenente le informazioni, crea un account
     *
     * @param info l'hashmap contenente le informazioni necessarie per la creazione di un account
     * @throws StringBlankException se il nome passato per creare un account è nullo
     * @return l'Account appena creato
     *
     */
   Account addAccount(HashMap<String, String> info) throws StringBlankException;

    /**
     * Get della List degli Account del Ledger
     *
     * @return la List degli Account del Ledger
     */
    List<Account> getAccounts();

    /**
     * Preso un intero, restituisce se esiste l'Account corrispondente all'ID inserito
     *
     * @param account l'ID dell'Account che si vuole cercare
     * @return l'Account corrispondente all'ID inserito
     * @throws AccountDoNotExist se l'ID inserito non corrisponde a nessun Account
     */
    Account getAccount(int account) throws AccountDoNotExist;

    /**
     * Preso un hashmap contenente le informazioni ( ID Account da modificare, nuovo nome,
     * nuovo saldo iniziale,..), attua le modifiche all'Account
     *
     * @param info informazioni necessarie per modificare l'Account
     * @throws StringBlankException se il nome passato per l'Account è "bianco"(senza nessun carattere)
     */
    void modifyAccount(HashMap<String, Object> info) throws StringBlankException;

    /**
     * Preso un intero che rappresenta l'ID di un Account, restituisce il nome dello stesso
     *
     * @param account l'ID dell'account da cui prendere il nome
     * @return il nome dell'Account corrispondente all'ID inserito
     */
    String getAccountName(int account);

    /**
     * Preso un intero che rappresenta l'ID di un Account, restituisce la descrizione dello stesso
     *
     * @param account l'ID dell'account da cui prendere la descrizione
     * @return la descrizione dell'Account corrispondente all'ID inserito
     */
    String getAccountDescription(int account);

    /**
     * Preso un intero che rappresenta l'ID di un Account, restituisce il saldo iniziale dello stesso
     *
     * @param account l'ID dell'account da cui prendere il saldo iniziale
     * @return il saldo iniziale dell'Account corrispondente all'ID inserito
     */
    String getAccountInitialBalance(int account);


    /**
     * Preso un oggetto LocalDate, crea un predicato per i Movement
     * questo predicato ritorna true se la data del Movement è uguale alla data inserita
     *
     * @param date data inserita
     * @return un predicato
     */
    Predicate<Movement> createDatePredicate(LocalDate date);

    /**
     * Presa una data e un ID di un account, ritorna una List di Movement associati all'Account.
     * La List è composta solo da quei Movement che hanno la data uguale a quella inserita.
     *
     * @param date    la data inserita
     * @param account l'ID dell'Account di cui si vogliono fitrare i movimenti
     * @return la List di Movement associati all'Account che hanno la data uguale a quella inserita
     */
    List<Movement> filterMovementForData(LocalDate date, int account);

    /**
     * Presa una data e un ID di un account, ritorna una List di Movement associati all'Account.
     * La List è composta solo da quei Movement che contengono la Category inserita
     *
     * @param category la category inserita
     * @param account  l'ID dell'Account di cui si vogliono fitrare i movimenti
     * @return la List di Movement associati all'Account che contengono la Category inserita
     */
    List<Movement> filterMovementForCategory(String category, int account) throws CategoryDoNotExist, StringBlankException;

    /**
     * Preso una Category, crea un predicato per i Movement
     * <p>
     * Questo predicato ritorna true il Movement contiene nella sua List di Category, la Category inserita
     *
     * @param category Category inserita
     * @return un predicato
     */
    Predicate<Movement> createCategoryPredicate(Category category);

    /**
     * Get della List di Category del Ledger
     *
     * @return la List di Category del Ledger
     */
    List<Category> getCategories();

    /**
     * Preso un nome, se esiste, ritorna la Category che ha quel nome
     *
     * @param s il nome della Category da ricercare nel ledger
     * @return la Category con lo stesso nome inserito
     * @throws StringBlankException se la Stringa inserita è "bianca"
     * @throws CategoryDoNotExist   se nessuna Category del Ledger corrisponde al nome inserito
     */
    Category getCategory(String s) throws StringBlankException, CategoryDoNotExist;

    /**
     * Aggiunge, se già non è presente, una Category al Ledger
     *
     * @param info il nome della Category che si vuole creare
     * @throws CategoryAlreadyExisting se esiste già una Category con lo stesso nome
     * @throws StringBlankException    se il nome inserito è "bianco"
     */
    void addCategory(String info) throws CategoryAlreadyExisting, StringBlankException;

    /**
     * Rimuove, se presente, una Category dal Ledger
     *
     * @param category il nome della Category che si vuole rimuovere
     * @throws CategoryDoNotExist   se il nome inserito non corrisponde a nessuna Category presente nel Ledger
     * @throws StringBlankException se il nome inserito è "bianco"
     */
    void removeCategory(String category) throws CategoryDoNotExist, StringBlankException;

    /**
     * Get della List di Budget del Ledger
     *
     * @return la List di Budget del Ledger
     */
    List<Budget> getBudgets();

    /**
     * Preso un intero, restituisce se presente il Budget corrispondente a quell'ID
     *
     * @param budget l'ID da ricercare
     * @return il Budget con l'ID inserito
     * @throws BudgetDoNotExist se l'ID inserito non corrisponde a nessun Budget
     */
    Budget getBudget(int budget) throws BudgetDoNotExist;

    /***
     *Preso un hashMap contenente le informazioni,viene creato un Budget
     * @param info l'hashMap contenente le informazioni
     * @throws CategoryDoNotExist se la Category scelta per il Budgte non esiste
     * @throws BudgetAlreadyExist se esiste già un Budget per una determinata Category
     * @throws StringBlankException se la String contenenete il nome dellaCategory desiderata è vuota
     */
    void addBudget(HashMap<String, String> info) throws CategoryDoNotExist, BudgetAlreadyExist, StringBlankException;

    /**
     * Preso un double che rappresenta il nuovo ammontare e un int che è l'ID del Budget da modificare,
     * attua le modifiche al Budget corrispondente all'ID isnerito
     *
     * @param newAmount il nuovo ammontare da dare al bUDGTE
     * @param budget    l'ID del Budgte da modificare
     */
    void modifyBudget(double newAmount, int budget);

    /**
     * Preso un int che rappresenta un ID, elimina, se esiste il Budget corrispondente all'ID inserito
     *
     * @param budget l'ID del Budget da rimuovere
     * @throws BudgetDoNotExist se l'ID inserito non corrisponde a nessun Budget
     */
    void removeBudget(int budget) throws BudgetDoNotExist;

    /**
     * Preso un int che rappresenta un ID, fornisce il BudgetReport associato al Budget
     *
     * @param budget l'ID del Budget
     * @return il BudgetReport associato al Budget
     */
    BudgetReport getBudgetReport(int budget);

    /**
     * Preso un int che rappresenta l'ID di un Budget, restituisce l'ammontare dle Budget
     *
     * @param budget l'ID del Budget da cui prendere l'ammontare
     * @return l'ammontare del Budget
     */
    double getBudgetAmount(int budget);

    /**
     * Preso un int che rappresenta l'ID di un Budget, restituisce una List di Integer
     * composta dagli anni in cui è presente almeno un Movement con la Category associata al Budget
     *
     * @param budget l'ID del Budget
     * @return la List di Integer composta dagli anni in cui è presente almeno
     * un Movement con la Category associata al Budget
     */
    List<Integer> getYearsOfBudgetReport(int budget);

    /**
     * Questo metodo, preso un anno, restituisce un hashMap contentente 4 List:
     * <p>
     * 1. una List formata dal totale delle entrate per ogni mese dell'anno
     * 2. una List formata dal totale delle uscite per ogni mese dell'anno
     * 3. una List formata dal totale dei movimenti (uscite-entrate) per ogni mese dell'anno
     * 4. una List formata dall'amontare riservato per il Budget
     *
     * @param year l'anno per cui si vogliono ricevere i dati
     * @return le 4 List per creare il garfico dell'andamento del Budget
     */
    HashMap<String, List<XYChart.Data<String, Double>>> progressInformation(int year);

    /**
     * Preso un int che rappresenta l'ID, restituisce, se esiste la Transaction corrispondente all'ID
     * se la transaction non viene trovata nelle Transaction"normali", viene ricercata tra le ScheduledTransaction
     *
     * @param transaction l'ID della Transaction
     * @return la Transaction desiderata
     * @throws TransactionDoNotExist se l'ID inserito non corrisponde a nessuna delle Transaction o ScheduledTransaction esistenti
     */
    Transaction selectTransaction(int transaction) throws TransactionDoNotExist;

    /**
     * Get della List di Transaction associate al Ledger
     *
     * @return la List di Transaction associate al Ledger
     */
    List<Transaction> getTransactions();

    /**
     * Get della List di Transaction contenute nelle  ScheduledTransaction associate al Ledger
     *
     * @return la List di Transaction contenute nelle ScheduledTransaction associate al Ledger
     */
    List<Transaction> getScheduledTransactions();

    /**
     * Preso un int che rappresenta l'ID di una Transaction, restitusice una List di Movement associati alla Transaction
     *
     * @param transaction l'Id della Transaction di cui si vogliono prendere i Movement
     * @return la List di Movement associati alla Transaction
     */
    List<Movement> getTransactionMovements(int transaction);

    /***
     * Peso un HashMap contenente le informazioni per modificare una Transaction e l'ID della Transaction da modificare,
     * applica le modifche alla Transaction desiderata
     *
     * @param info l'HashMap contenente una List di Category, una Date, e l'ID della Transaction
     * @throws CategoryAlreadyAdded se le Category che si vogliono inserire risultano già presenti nella Transaction
     */
    void modifyTransaction(HashMap<String, Object> info) throws CategoryAlreadyAdded;

    /**
     * Preso un hashMap contenente le informazioni necessarie per la creazione di un Movement, ne crea uno.
     *
     * @param addMovement l'HashMap contenente una List di Category,ammontare, tipo, descrizione e account da associare al Movement
     * @return l'oggetto Movement
     * @throws AccountDoNotExist    se l'Account a cui si vuole associare il Movement non esiste
     * @throws StringBlankException se il l'ID dell'Account a cui associare il Movemetn è bianco
     */
    Movement createNewMovement(HashMap<String, Object> addMovement) throws AccountDoNotExist, StringBlankException;

    /**
     * Preso un hashMap contenente le informazioni necessarie per la creazione di un Movement, ne crea uno.
     *
     * @param info l'HashMap contenente una List di Category,ammontare, tipo, descrizione, ID account e ID transaction  da associare al Movement
     * @throws AccountDoNotExist     se l'Account a cui si vuole associare il Movement non esiste
     * @throws TransactionDoNotExist se la Transaction a cui si vuole associare il Movement non esiste
     * @throws StringBlankException  se il l'ID dell'Account a cui associare il Movemetn è bianco
     */
    void addMovement(HashMap<String, Object> info) throws AccountDoNotExist, TransactionDoNotExist, StringBlankException;

    /**
     * Preso un int che rappresenta l'ID di un Movement, viene rimosso il Movement dal Ledger
     *
     * @param movementId l'ID del Movement da rimuovere
     */
    void removeMovement(int movementId);

    /**
     * Preso un hashMap contenente le informazioni necessarie per la creazione di una Transaction , ne crea una.
     *
     * @param info l'hasmap conenente una List di Category e una di Movement, la data e il tipo di
     *             Transaction che si vule creare (normale o Scheduled)
     */
    void addTransaction(HashMap<String, Object> info);

    /**
     * Presa una data , ritorna una List di Transaction.
     * La List è composta solo da quelle Transaction  che hanno la data uguale a quella inserita.
     *
     * @param dateForTransaction la data inserita
     * @return la List di Transactiont che hanno la data uguale a quella inserita
     */
    List<Transaction> filterTransactionForDate(LocalDate dateForTransaction);

    /**
     * Preso un oggetto LocalDate, crea un predicato per le Transaction
     * questo predicato ritorna true se la data della Transaction è uguale alla data inserita
     *
     * @param dateForTransaction data inserita
     * @return un predicato
     */
    Predicate<Transaction> createDatePredicateForTransaction(LocalDate dateForTransaction);

    /**
     * Presa una String che rapprensenta il nome di una Category , ritorna una List di Transaction.
     * La List è composta solo da quelle Transaction  che contengono la Category corrispondente al nome inserito.
     *
     * @param categoryNameForTransaction la Category inserita
     * @return la List di Transactiont che contengono la Category corrispondente al nome inserito.
     * @throws CategoryDoNotExist se la Category per cui si vuole filtrare le Transaction non esiste
     */
    List<Transaction> filterTransactionForCategory(String categoryNameForTransaction) throws CategoryDoNotExist;

    /**
     * Preso una Category, crea un predicato per le Transaction
     * <p>
     * questo predicato ritorna true se la Transaction contiene nella sua List di Category, la Category inserita
     *
     * @param category Category inserita
     * @return un predicato
     */
    Predicate<Transaction> createCategoryPredicateForTransaction(Category category);

    /**
     * Preso un int che corrisponde all'ID della Transaction, essa, se presente viene rimossa
     *
     * @param transaction l'ID della Transaction che si vuole rimuovere
     * @throws TransactionDoNotExist se l'ID inserito non corrisponde a nessuna Transaction esistente
     */
    void removeTransaction(int transaction) throws TransactionDoNotExist;

    /**
     * preso l'ID di una Transaction restituisce la data di quella Transaction
     *
     * @param transaction l'ID della Transaction di cui si vuole la da
     * @return un oggetto LocalDate che ha al suo intenro la data della Transaction
     */
    LocalDate getTransactionDate(int transaction);

    /**
     * Presa una data , ritorna una List di Transaction.
     * La List è composta solo da quelle Transaction contenute nelle ScheduledTransaction che hanno la data uguale a quella inserita.
     *
     * @param date la data inserita
     * @return la List di Transactiont ricavata dalle ScheduledTransaction che hanno la data uguale a quella inserita
     */
    List<Transaction> filterScheduledTransactionForDate(LocalDate date);

    /**
     * Presa una String che rapprensenta il nome di una Category , ritorna una List di Transaction.
     * La List è composta solo da quelle Transaction contenute nelle ScheduledTransaction che contengono la Category corrispondente al nome inserito.
     *
     * @param category la Category inserita
     * @return la List di Transaction ricavate dalle ScheduledTransaction che contengono la Category corrispondente al nome inserito.
     * @throws CategoryDoNotExist se la Category per cui si vuole filtrare le Transaction non esiste
     */
    List<Transaction> filterScheduledTransactionForCategory(String category) throws CategoryDoNotExist;


}
