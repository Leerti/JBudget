package it.unicam.cs.pa.jbudget105532;


import it.unicam.cs.pa.jbudget105532.Controller.*;
import it.unicam.cs.pa.jbudget105532.Controller.BasicCommandProcessor;
import it.unicam.cs.pa.jbudget105532.Controller.CommandProcessor;
import it.unicam.cs.pa.jbudget105532.View.ConsoleView.ConsoleView;
import it.unicam.cs.pa.jbudget105532.View.View;
import javafx.application.Application;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Questa classe fa effettivamente paartire l'applicazione,
 * mette insieme view, model e controller e richiama il metodo della view open per
 * far iniziare la comunicazione con l'utente.
 * Crea inoltre l'hashmap con i comandi da passare al CommandProcessor;
 * Saranno questi gli unici comandi che il controller riuscirà a eseguire e quindi gli unici comanid che l'utente potrà scegliere
 */
public class App {

    private final View view;
    private final CommandProcessor commandProcessor;

    /**
     * Costruttore per l'applicazione
     *
     * @param view             la View da utilizzare nell'applicazione
     * @param commandProcessor il processatore di comandi da utilizzare nell'aplicazione
     */
    public App(View view, CommandProcessor commandProcessor) {
        this.view = view;
        this.commandProcessor = commandProcessor;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            launchGui();
        } else {
            App a = createBasicAppConsoleView();
            a.start();
        }
    }


    /**
     * Questo metodo viene richiamato dal main e richiama a sua volta due metodi della View
     * per far partire la comunicazione con l'utente e per chiuderla
     */
    private void start() {
        view.open(commandProcessor);
        view.close();
    }

    private static void launchGui() {
        Application.launch(JavaFxJbudget.class);
    }

    /**
     * Aggiunge i comandi ad un hashmap passato come parametro.
     * I comandi sono metodi della classe Command e ogni comando è associato ad una stringa utilizzata per riconoscerlo
     *
     * @param commands l'hahsmap dove inserire comandi
     * @return l'hahsmap con all'interno tutti i comndi inseriti
     */
    private static Map<String, Consumer<? super Command>> addCommands(Map<String, Consumer<? super Command>> commands) {
        commands.put("ADD ACCOUNT", Command::addAccount);
        commands.put("SHOW ALL ACCOUNTS", Command::showAllAccount);
        commands.put("SELECT ONE ACCOUNT", Command::selectAccount);
        commands.put("SHOW ALL MOVEMENTS OF ACCOUNT", Command::showAllMovement);
        commands.put("FILTER MOVEMENTS OF ACCOUNT BY DATE", Command::filterMovementForData);
        commands.put("FILTER MOVEMENTS OF ACCOUNT BY CATEGORY", Command::filterMovementForCategory);
        commands.put("MODIFY ACCOUNT", Command::modifyAccount);
        commands.put("CHANGE NAME ACCOUNT", Command::modifyNameAccount);
        commands.put("CHANGE DESCRIPTION ACCOUNT", Command::modifyDescriptionAccount);
        commands.put("CHANGE INITIAL BALANCE", Command::modifyInitialBalance);

        commands.put("ADD TRANSACTION", Command::addTransaction);
        commands.put("SHOW ALL TRANSACTIONS", Command::showAllTransaction);
        commands.put("CHECK SCHEDULED TRANSACTION", Command::checkScheduledTransaction);
        commands.put("SELECT ONE TRANSACTION", Command::selectTransaction);
        commands.put("FILTER TRANSACTIONS BY DATE", Command::filterTransactionForDate);
        commands.put("FILTER TRANSACTIONS BY CATEGORY", Command::filterTransactionForCategory);
        commands.put("SHOW ALL MOVEMENTS OF TRANSACTION", Command::showAllMovementsOfTransaction);
        commands.put("CHANGE DATE", Command::modifyDate);
        commands.put("MODIFY TRANSACTION", Command::modifyTransaction);
        commands.put("ADD CATEGORY TO TRANSACTION", Command::addCategoryForTransaction);
        commands.put("REMOVE CATEGORY FROM TRANSACTION", Command::removeCategoryForTransaction);
        commands.put("REMOVE TRANSACTION", Command::removeTransaction);

        commands.put("ADD CATEGORY", Command::addCategory);
        commands.put("SHOW ALL CATEGORIES", Command::showAllCategories);
        commands.put("REMOVE CATEGORY", Command::removeCategory);

        commands.put("ADD BUDGET", Command::addBudget);
        commands.put("REMOVE BUDGET", Command::removeBudget);
        commands.put("SELECT ONE BUDGET", Command::selectOneBudget);
        commands.put("SHOW ALL BUDGET", Command::showAllBudget);
        commands.put("SHOW PROGRESS", Command::showProgress);
        commands.put("CHANGE AMOUNT", Command::changeAmount);


        commands.put("EXIT", Command::turnOff);
        commands.put("ACCOUNT", Command::account);
        commands.put("TRANSACTION", Command::transaction);
        commands.put("CATEGORY", Command::category);
        commands.put("BUDGET", Command::budget);
        commands.put("BACK", Command::back);
        return commands;
    }

    /**
     * Crea una nuova App con interfaccia a console
     *
     * @return un oggetto di tipo App
     */
    public static App createBasicAppConsoleView() {
        Controller controller = new BasicController();
        View view = new ConsoleView(controller);
        Command command = new BasicCommand(view, controller);
        CommandProcessor commandProcessor = new BasicCommandProcessor(addCommands(new HashMap<>()), command);
        return new App(view, commandProcessor);
    }


}
