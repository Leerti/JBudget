package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Model.Movement;

/**
 * In questa interfaccia vengono definiti tutti i metodi per eseguire effettivametne i comandi inseriti dall'utente.
 * Un oggetto Command fa da intermediario fra la View e il Controller effettivo.
 * Chiede i dati alla View per passarli al Controller.
 * Un Command ha al suo interno un Account, un Budget e una Transaction per salvarne i riferimenti.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Command {

    /**
     * Richiama il metodo del Controller per controllare le ScheduledTransaction
     */
    void checkScheduledTransaction();

    /**
     * Get del Controller
     *
     * @return il Controller
     */
    Controller getController();

    /**
     * Richiama il metodo del Controller per "spengere" il Ledger
     */
    void turnOff();

    /**
     * Chiama i metodi della View (getName, getDescription, getAccountType e getInitialBalance) per creare
     * un hashMap con i valori ritornati da questi ultimi.
     * L'HashMap creato verrà passato poi al metodo addAccount del Controller
     */
    void addAccount();

    /**
     * Chiama il metodo della View showllAccount
     */
    void showAllAccount();

    /**
     * Chiama il metodo della View per prendere dall'utente l'ID dell'Account che si vuole selezionare
     * e dopo aver richiamato il metodo del Controller per prendere effettivamente un Account, mette
     * il riferimento dentro alla sua variabile account
     */
    void selectAccount();

    /**
     * Questo metodo chiama il metodo getDescription della View e mette il valore
     * di ritorno in un hashmap insieme al nome , all'initiaBalance e all'ID dell'Account
     * che ha il Command come parametro (questo Account vinene inizializzato
     * nel momento in cui dal menuAccount si sceglie di selezionarne uno, a quel punto
     * è possibile effettuare tutte le operazioni su un singolo Account senza dover
     * reinserire ogni volta l'ID che si vuole andare ad utilizzare) e queste informazioni
     * vengono passate al Controller, richiamando il metodo modifyAccount e passandogli in input l'hashmap
     */
    void modifyDescriptionAccount();

    /**
     * Chiama il metodo della View per ricevere dall'utente il nuovo nome dell'Account
     * e lo passa al Controller per effettuare la modifca
     */
    void modifyNameAccount();

    /**
     * Viene richiamato il metodo getInitialBalance della View e viene messo il valore di
     * ritorno in un hashmap insieme al nome , alla descrizione e all'ID dell'Account
     * che ha il Command come parametro queste informazioni vengono passate al Controller,
     * richiamando il metodo modifyAccount e passandogli in input l'hashmap
     */
    void modifyInitialBalance();

    /**
     * richiama un metodo della View per mostrare tutti i Movement
     */
    void showAllMovement();

    /**
     * Viene prima chiesto all'utente di inserire una data per cui vuole filtrare i Movement,
     * questa data viene messa in un hashmap e quest'ultimo vine passato al Controller.
     * Il controller filtra i Movement e restituisce la List di Movement corretta.
     * A questo punto vengono passati i Movement alla View per visualizzarli
     */
    void filterMovementForData();

    /**
     * Viene prima chiesto all'utente di inserire una Category per cui vuole filtrare i Movement,
     * questa Category viene messa in una Stringa  e quest'ultima vine passata al Controller.
     * Il controller filtra i Movement e restituisce la List di Movement corretta.
     * A questo punto vengono passati i Movement alla View per visualizzarli
     */
    void filterMovementForCategory();

    /**
     * Chiama il metodo getName della View e presa la Stringa ched come valore di ritorno,
     * la passa al Controller per far aggiungere la Category
     */
    void addCategory();

    /**
     * Viene visualizzato inizialmente un Alert dove vi è scritto che ogni categoria rimossa
     * verrà rimossa anche da ogni Movement e da ogni Transaction e verrà eliminato se esite,
     * il Budget associato a quella determinata Category. Viene chiesto ll'utente se è sicuro
     * di voler continuare e, in caso affermativo viene chiesto di inserire il nome della Category
     * che si vuole rimuovere.
     * La stringa viene passata a Controller che si occuperà di rimuovere effettivameente la Category
     */
    void removeCategory();

    /**
     * Viene chiamato il metodo showCategory della View per visualizzare tutte le Category presenti nel Ledger
     */
    void showAllCategories();

    /**
     * Questo metodo controlla se nel Ledger è presente almeno una Category
     *
     * @return true se è presente, false altrimenti
     */
    boolean checkCategory();

    /**
     * Questo metodo controlla se è presente almeno un Account nel Ledger
     *
     * @return true se presete, false altrimenti
     */
    boolean checkAccount();

    /**
     * Questo metodo chiama la View per ricevere dall'utente le informazioni necessarie
     * alla creazione di una Transaction.
     * Una volta inserito il primo Movement viene richiamato un metodo della View per
     * chiedere all'utente se nu veuole inserire un altro, in caso di risposta affermativa viene
     * richiamato il metodo newMovement.
     * Se la data inserita per una determinata Transaction risulta più avanti del giorno corrente,
     * viene chiesto se si vuole inserire la Transaction come Scheduled.
     * Nel momento in cui si finisce ad inserire Movement, tutte le infomrazioni raccolte
     * vengono passate al Controller che creerà effettivamente la nuova Transaction.
     */
    void addTransaction();

    /**
     * Questo metodo chiama la View per ricevere dall'utente le informazioni necessarie
     * alla creazione di un Movement.
     * Una volta completato l'inserimento vengono passate al Controller che restiusci
     * il Movement appena creato e quest'ultimo è il valore di ritorno del metodo
     *
     * @return il Movement creato
     */
    Movement newMovement();

    /**
     * Viene richiamato un metodo della View per visualizzare tutte le Transaction
     */
    void showAllTransaction();

    /**
     * Dopo aver fatto visualizzare tutte le Transaction dalla View, viene richiamato un metodo
     * per far inserire all'utente l'ID della Transaction che decide di selezionare.
     * L'ID ricevuto dalla View viene passato al controller che restiuisce la Transaction corrispondente
     * e quest'ultima viene messa come Transaction nel parametro dell'oggetto Command
     */
    void selectTransaction();

    /**
     * Viene prima chiesto all'utente di inserire una data per cui vuole filtrare le Transaction,
     * questa data viene messa in un hashmap e quest'ultimo vine passato al Controller.
     * Il controller filtra le Transaction e restituisce la List Transaction corretta.
     * A questo punto vengono passati le Transaction alla View per visualizzarli
     */
    void filterTransactionForDate();

    /**
     * Viene prima chiesto all'utente di inserire una Category per cui vuole filtrare le Transaction,
     * questa Category viene messa in una Stringa  e quest'ultima vine passata al Controller.
     * Il controller filtra le Transaction e restituisce la List di Transaction corretta.
     * A questo punto vengono passati le Transaction alla View per visualizzarli
     */
    void filterTransactionForCategory();

    /**
     * Viene dato al metodo della view "showMovement" la List di Movement della Transaction salvata come parametro.
     * Questa List viene recuperata dal Controller.
     */
    void showAllMovementsOfTransaction();

    /**
     * Questo metodo chiama il metodo getDate della View e mette il valore
     * di ritorno in un hashmap insieme alle Category e all'ID della Trasaction
     * che ha il Command come parametro (questa Transaction vinene inizializzata
     * nel momento in cui dal menuTransaction si sceglie di selezionarne una, a quel punto
     * è possibile effettuare tutte le operazioni su una singola Transaction senza dover
     * reinserire ogni volta l'ID che si vuole andare ad utilizzare) e queste informazioni
     * vengono passate al Controller, richiamando il metodo modifyTransaction e passandogli in input l'hashmap
     */
    void modifyDate();

    /**
     * Questo metodo chiama il metodo getCategory della View  e passa il valore di ritorno al controller per ricevere
     * la Category corrispondente al nome inserito.
     * Succesivamente aggiunge la Category appena ricevuta dal Controller in una List di Category composta dalle Category stesse della
     * Transaction che si vuole andare a modificare.
     * La List viene messa poi in un hashmap insieme alla data e all'ID della Trasaction
     * che ha il Command come parametro e queste informazioni
     * vengono passate al Controller, richiamando il metodo modifyTransaction e passandogli in input l'hashmap
     */
    void addCategoryForTransaction();

    /**
     * Questo metodo chiama il metodo getCategory della View  e passa il valore di ritorno al controller per ricevere
     * la Category corrispondente al nome inserito.
     * Succesivamente rimuove la Category appena ricevuta dal Controller da una List di Category composta dalle Category stesse della
     * Transaction che si vuole andare a modificare.
     * La List viene messa poi in un hashmap insieme alla data e all'ID della Trasaction
     * che ha il Command come parametro e queste informazioni
     * vengono passate al Controller, richiamando il metodo modifyTransaction e passandogli in input l'hashmap
     */
    void removeCategoryForTransaction();

    /**
     * Viene richiamato il metodo del controller removeTransaction dandogli in input la Transaction salvata all'interno del Command
     */
    void removeTransaction();

    /**
     * Viene richiamato il metodo del controller removeBudget dandogli in input il Budget salvato all'interno del Command
     */
    void removeBudget();

    /**
     * Dopo avere chiamato un metodo della View per far vedere tutti i Budget, viene richiesto, sempre dalla View, di inserire
     * l'ID del Budget che si vuole selezionare.
     * Questo ID viene passato al Controller che restituisce il Budget corrispondente.
     * Quest'ultimo verra messo come paramentro al Command
     */
    void selectOneBudget();

    /**
     * Viene richiamato un metodo della View per mostrare tutti i budget
     */
    void showAllBudget();

    /**
     * Tramite due metodi della View, viene richiesto all'utente di inserire una Category
     * ed un ammontare per la creazione di un Budgte.
     * queste infomrazioni vengono messe in un hahsmap e passate al controller
     */
    void addBudget();

    /**
     * Dopo aver selezionato un Budget, è possibile visualizzarne i progressi:
     * viene chiesto al Controller di costruire un Budget report per un determinato Budget.
     * Questo Budget report contine infomrazioni riguardanti il totale scelto per il Budget,
     * il totale attualmente speso-il totale attualmente guadagnato per una determinata Category
     * e quanto si discosta questo totale dal budget prefissato.
     */
    void showProgress();

    /**
     * Viene richiamato un metodo della View per prendere in Input il nuovo ammontare deciso per il budget.
     * viene passato al controller inseime all'ID del Budget da modificare, tramite il metodo  modifyBudget.
     * il Controlller applica le modfiche
     */
    void changeAmount();

    void account();

    void transaction();

    void budget();

    void category();

    void initial();

    void oneAccount();

    void modifyAccount();

    void oneTransaction();

    void modifyTransaction();

    void oneBudget();

    /**
     * Questo metodo viene utilizzato per tornare indietro tra i vari menu dell'App.
     * In base a quale menu è stato l'utimo visitato, viene richiamata la View per stampare un detemrinato Menu.
     */
    void back();
}
