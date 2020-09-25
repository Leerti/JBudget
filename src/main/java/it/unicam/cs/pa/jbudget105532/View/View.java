package it.unicam.cs.pa.jbudget105532.View;

import it.unicam.cs.pa.jbudget105532.Controller.CommandProcessor;
import it.unicam.cs.pa.jbudget105532.Model.*;

import java.util.HashMap;
import java.util.List;

/**
 * Questa interfaccia definisce una View e forza le classi che la implementeranno ad avere tutti i metodi necessari per
 * l'interazione con l'utente
 * (sia per ricevere dati, sia per ricevere comandi, sia per visualizzare i dati richiesti).
 * <p>
 * Una View ha al suo interno un Controller da cui solitamente prende le infomrazioni sullo stato del Ledger per farle vedere all'utente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface View {


    /**
     * "Chiude" la comunicazione con l'utente
     */
    void close();

    /**
     * "Apre" la comunicazione con l'utente richiamando i metodi necessari
     * per fornire il menu iniziale all'utente ed essere in grado di ricevere comandi
     *
     * @param commandProcessor controller che processerà i comandi inseriti
     */
    void open(CommandProcessor commandProcessor);

    /**
     * Richiama il metodo per stampare il menu iniziale e apre effettivamente la comunicazione con l'utente.
     * Resta in attesa del comando dell'utente che, una volta inseirto passa al Controller per farlo processare
     *
     * @param commandProcessor controller che processerà i comandi inseriti
     */
    void printInitialMenu(CommandProcessor commandProcessor);

    /**
     * Mostra un messaggio d'errore all'utente
     *
     * @param s il messagio d'errore da mostrare
     */
    void showErrorMessage(String s);

    /**
     * Fornisce il menu iniziale e un conteggio degli Accounts, Budgets, Transactions e Categories presenti nel Ledger
     */
    void printInitialCommands();

    /**
     * Fornisce il menu degli Accounts dove vengono visualizzate le opzioni che si possono eseguire sugli Accounts
     */
    void getAccountsMenu();

    void getChoiceTransactionMenu();

    /**
     * Fornisce il menu delle Transactions dove vengono visualizzate le opzioni che si possono eseguire sulle Transactions
     */
    void getTransactionsMenu();

    void getScheduleTransactionsMenu();

    /**
     * Fornisce il menu delle Categories dove vengono visualizzate le opzioni che si possono eseguire sulle Categories
     */
    void getCategoriesMenu();

    /**
     * Fornisce il menu dei Budgets dove vengono visualizzate le opzioni che si possono eseguire sui Budgets
     */
    void getBudgetMenu();

    /**
     * Fornisce il menu di un Account dove vengono visualizzate le opzioni che si possono eseguire su un Account
     */
    void getMenuOneAccount();

    /**
     * Fornisce il menu della modifica di un Account dove vengono visualizzate le opzioni che si
     * possono eseguire per modificare un Account
     */
    void getMenuModifyAccount();

    /**
     * Fornisce il menu di una Transaction dove vengono visualizzate le opzioni che si possono eseguire su una Transaction
     */
    void getMenuOneTransaction();

    /**
     * Fornisce il menu della modifica di una Transacion dove vengono visualizzate le opzioni
     * che si possono eseguire per modificare una Transaction
     */
    void getMenuModifyTransaction();

    /**
     * Fornisce il menu di un Budget dove vengono visualizzate le opzioni che si possono eseguire su un Budget
     */
    void getMenuOneBudget();

    /**
     * Viene richiesto all'utente di iserire un ID
     *
     * @return l'Id inserito dall'utente
     */
    int getID();

    int getAccountID();

    /**
     * Viene richiesto all'utente di inserire un nome
     *
     * @return il nome inserito dall'utente
     */
    String getName();

    /**
     * Viene richiesto all'utente di iserire una descritzione
     *
     * @return la descrizione inserita dall'utente
     */
    String getDescription();

    /**
     * Viene richiesto all'utente di iserire un saldo iniziale
     *
     * @return il saldo iniziale  inserito dall'utente
     */
    double getInitialBalance();

    /**
     * Viene richiesto all'utente di iserire il tipo di un Account
     *
     * @return il tipo inserito dall'utente
     */
    String getAccountType();

    /**
     * Viene richiesto all'utente di iserire un ammontare
     *
     * @return l'ammontare inserito dall'utente
     */
    double getAmount();

    /**
     * Viene richiesto all'utente di iserire un tipo di un Movement
     *
     * @return il tipo  inserito dall'utente
     */
    String getMovementType();

    /**
     * Viene chiesto all'utente di inserire giorno, mese e anno di una certa data
     *
     * @return l'hashMap contente le informazioni inserite dall'utente
     */
    HashMap<String, String> getDate();

    /**
     * Viene chiesto all'utente di inserire il nome di una Category
     *
     * @return una stringa contenente il nome di una Category
     */
    String getCategory();

    /**
     * Viene richiesto all'utente se vuole inserire un'altra Category
     *
     * @return la risposta dell'utente
     */
    String anotherCategory();

    /**
     * Viene visualizzata la scritta "NEW MOVEMENT"
     */
    void newMovement();

    /**
     * Fornisce il modo di visualizzare tutti gli Account
     */
    void showAllAccount();

    /**
     * vengono visualizzate tutte le Category presenti nel Ledger
     */
    void showAllCategories();

    /**
     * Viene visualizzata la List di Category inserita come parametro.
     * Se non sono presenti Category, viene visualizzato un messaggio per informare l'utente
     *
     * @param categories la List delle Category da visualizzare
     */
    void showCategories(List<Category> categories);

    /**
     * Viene visualizzata la stringa per ricordare che se viene eliminata una Category,
     * essa verra eliminata anche da tutte le Transaction o Movement in cui era presente.
     * Viene chiesto all'utente se è sicuro di voler proseguire
     *
     * @return la stringa contenente la risposta dell'utente
     */
    String showAlertCategory();

    /**
     * Vengono visualizzati tutti i Movement presenti dentro un Account
     *
     * @param account l'Account da cui prendere la List di Movement da stampare
     */
    void showAllMovements(Account account);

    /**
     * Viene visualizzata la List di Movement passata come parametro.
     * Viene usato un MovementPrinter per stampare uno ad uno tutti i Movement.
     * Se la List passata è vuota viene visualizzato un messaggio per informare l'utente
     *
     * @param movements la List di Movement da visualizzare
     */
    void showMovements(List<Movement> movements);

    /**
     * viene richiesto all'utente se desidera inserire un altro Movemnt
     *
     * @return la risposta dell'utente
     */
    String anotherMovements();

    /**
     * Vengono visualizzate tutte le Transaction esistenti in un Ledger
     */
    void showAllTransaction();

    /**
     * Viene visualizzata la List delle Transaction inserita come parametro.
     * Se non sono presenti Transaction, viene visualizzato un messaggio per informare l'utente
     *
     * @param transactions la List di Transaction da visualizzare
     */
    void showTransactions(List<Transaction> transactions);

    /**
     * Viene Chiesto all'utenete se vuole inserire questa Transaction come "effettiva" o come schedulata
     *
     * @return la string di risposta dell'utente
     */
    String askScheduled();


    /**
     * Vengono visualizzati tutti i Budget esistenti in un Ledger
     */
    void showAllBudget();

    /**
     * Mostra un Report di un Budget:
     * viene visualizzato il Budget stesso e successivamente l'andamento,
     * cioè quanto si è speso/guadagnato per quella determinata Category e
     * di quanto ci si è discostati dall'amontare prefissato del Budget
     *
     * @param budgetReport il BudgetReport che calcola l'andamento per il Budget
     */
    void showProgress(BudgetReport budgetReport);


}
