package it.unicam.cs.pa.jbudget105532.View.ConsoleView;

import it.unicam.cs.pa.jbudget105532.Controller.CommandProcessor;
import it.unicam.cs.pa.jbudget105532.Controller.Controller;
import it.unicam.cs.pa.jbudget105532.Exception.UnknownCommandException;
import it.unicam.cs.pa.jbudget105532.Model.*;
import it.unicam.cs.pa.jbudget105532.View.Printer.*;
import it.unicam.cs.pa.jbudget105532.View.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class ConsoleView implements View {
    private final BufferedReader in;
    private final PrintStream out;
    private final PrintStream err;
    private Controller controller;

    /**
     * Costruttore di una View
     * @param controller controller da cui prendere alcuni dati
     */
    public ConsoleView(Controller controller) {
        this(new BufferedReader(new InputStreamReader(System.in)), System.out, System.err, controller);
    }

    public ConsoleView(BufferedReader in, PrintStream out, PrintStream err, Controller controller) {
        this.out = out;
        this.in = in;
        this.err = err;
        this.controller = controller;
    }

    @Override
    public void open(CommandProcessor commandProcessor) {
        hello();
        printInitialMenu(commandProcessor);
    }


    @Override
    public void printInitialMenu(CommandProcessor commandProcessor) {
        printInitialCommands();
        while (controller.isOn()) {
            try {
                String command = readString();
                commandProcessor.processCommand(command.toUpperCase());
            } catch (UnknownCommandException e) {
                this.showErrorMessage("Unknown command " + e.getMessage());
                printInitialCommands();
            }
        }
        printGoodbye();
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Viene visualizzato a console il messaggio di benvenuto
     */
    private void hello() {
        out.println("                               ******************************");
        out.println("                               *     Welcome in jBudget     *");
        out.println("                               ******************************");
        out.println();
    }

    /**
     * Viene visualizzato a console il messaggio dell'arrivederci
     */
    private void printGoodbye() {
        out.println("\n\nThank you for having used our application!");
        out.println("          See you next time!\n\n");
    }


    /**
     * Viene utilizzato il metodo readString per prendere informazioni e viene convertita la stringa inserita in un double.
     * Se non vine inserito un double, viene richiesto l'inserimento
     *
     * @return il double inserito
     */
    private double readDouble() {
        while (true) {
            double number = 0.0;
            try {
                number = Double.parseDouble(readString());
                return number;
            } catch (NumberFormatException e) {
                err.print("Error, insert a double\n");
            }
        }
    }

    /**
     * Viene utilizzato il metodo readString per prendere informazioni e viene convertita la stringa inserita in un int.
     * Se non vine inserito un int, viene richiesto l'inserimento
     *
     * @return l'int inserito
     */
    private int readInt() {
        while (true) {
            int number = 0;
            try {
                number = Integer.parseInt(readString());
                return number;
            } catch (NumberFormatException e) {
                err.print("Error, insert an int\n");
            }
        }
    }

    private String readString() {
        while (true) {
            out.println(" > ");
            out.flush();
            String string = " ";
            try {
                    string = in.readLine().trim();
                return string;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void showErrorMessage(String s) {
        this.err.println(s);
    }

    //MENU
    @Override
    public void printInitialCommands() {
        out.println("    ACCOUNT      -      TRANSACTION     -     CATEGORY      -     BUDGET    -    EXIT");
        out.println("|--------------|" + "   |------------------|" + "   |---------------|" + "   |-------------|" +
                "\n| accounts: " + controller.getNumbersOfAccount() + "  |" + "   | transactions: " + controller.getNumbersOfTransactions() + "  |" +
                "   | categories: " + controller.getNumbersOfCategory() + " |" + "   | budgets: " + controller.getNumbersOfBudgets() + "  |" +
                "\n|--------------|" + "   |------------------|" + "   |---------------|" + "   |-------------|");
        out.println();
    }

    @Override
    public void getAccountsMenu() {
        out.println("|-------------------------------------------------------------|");
        out.println("| ADD ACCOUNT - SHOW ALL ACCOUNTS - SELECT ONE ACCOUNT - BACK |");
        out.println("|-------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getChoiceTransactionMenu() {
        out.println("|---------------------------------------------------------------|");
        out.println("| VIEW TRANSACTION MENU - VIEW SCHEDULE TRANSACTION MENU - BACK |");
        out.println("|---------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getTransactionsMenu() {
        out.println("|-------------------------------------------------------------------------|");
        out.println("| ADD TRANSACTION - SHOW ALL TRANSACTIONS - FILTER TRANSACTIONS BY DATE - |" +
                "\n|    FILTER TRANSACTIONS BY CATEGORY - SELECT ONE TRANSACTION - BACK      |");
        out.println("|-------------------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getScheduleTransactionsMenu() {
        out.println("|----------------------------------------------------------------|");
        out.println("| SHOW ALL SCHEDULED TRANSACTIONS - CHECK SCHEDULE TRANSACTION - |" +
                "\n|         - SELECT ONE SCHEDULED TRANSACTION - BACK              |");
        out.println("|----------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getCategoriesMenu() {
        out.println("|-------------------------------------------------------------|");
        out.println("| ADD CATEGORY - REMOVE CATEGORY - SHOW ALL CATEGORIES - BACK |");
        out.println("|-------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getBudgetMenu() {
        out.println("|----------------------------------------------------------|");
        out.println("| SHOW ALL BUDGETS - ADD BUDGET - SELECT ONE BUDGET - BACK |");
        out.println("|----------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getMenuOneAccount() {
        out.println("|------------------------------------------------------------------------|");
        out.println("| SHOW ALL MOVEMENTS OF ACCOUNT  - FILTER MOVEMENTS OF ACCOUNT BY DATE - |" +
                "\n|     FILTER MOVEMENTS OF ACCOUNT BY CATEGORY - MODIFY ACCOUNT - BACK    |");
        out.println("|------------------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getMenuModifyAccount() {
        out.println("|----------------------------------------------------------------------------------|");
        out.println("| CHANGE NAME ACCOUNT - CHANGE DESCRIPTION ACCOUNT - CHANGE INITIAL BALANCE - BACK |");
        out.println("|----------------------------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getMenuOneTransaction() {
        out.println("|------------------------------------------------------------------------------------|");
        out.println("| SHOW ALL MOVEMENTS OF TRANSACTION - MODIFY TRANSACTION - REMOVE TRANSACTION - BACK |");
        out.println("|------------------------------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getMenuModifyTransaction() {
        out.println("|-------------------------------------------------------------------------------------|");
        out.println("| CHANGE DATE - ADD CATEGORY TO TRANSACTION - REMOVE CATEGORY FROM TRANSACTION - BACK |");
        out.println("|-------------------------------------------------------------------------------------|");
        out.println();
    }

    @Override
    public void getMenuOneBudget() {
        out.println("|------------------------------------------------------|");
        out.println("| CHANGE AMOUNT - REMOVE BUDGET - SHOW PROGRESS - BACK |");
        out.println("|------------------------------------------------------|");
        out.println();
    }

    @Override
    public int getID() {
        out.println("Insert ID");
        return readInt();
    }

    @Override
    public int getAccountID(){
        out.println("Chose an Account ID");
        return readInt();
    }

    @Override
    public String getName() {
        out.println("Insert name");
        return readString();
    }

    @Override
    public String getDescription() {
        out.println("Insert description");
        return readString();
    }

    @Override
    public double getInitialBalance() {
        out.println("Insert initial balance");
        return readDouble();
    }

    @Override
    public String getAccountType() {
        out.println("Chose type: ");
        out.println("Asset - Liability");
        return readString();
    }

    @Override
    public double getAmount() {
        out.println("Insert amount");
        return readDouble();
    }

    @Override
    public String getMovementType() {
        out.println("Insert new movement");
        out.println("Income - Expense ");
        return readString();
    }

    @Override
    public HashMap<String, String> getDate() {
        HashMap<String, String> date = new HashMap<>();
        out.println("Insert date");
        out.println("Day:");
        date.put("Day", String.valueOf(readInt()));
        out.println("Month:");
        date.put("Month", String.valueOf(readInt()));
        out.println("Year:");
        date.put("Year", String.valueOf(readInt()));
        return date;
    }

    @Override
    public String getCategory() {
        out.println("Insert category :");
        return readString().toUpperCase();
    }

    @Override
    public String anotherCategory() {
        out.println("Another category?");
        out.println("Y-N");
        return readString().toUpperCase();
    }

    @Override
    public void newMovement() {
        out.println("New Movement");
    }

    @Override
    public void showAllAccount() {
        AccountPrinter printer = new BasicAccountPrinter();
        if (controller.getNumbersOfAccount() == 0) {
            out.println("There are no accounts");
        } else {
            controller.getAccounts().forEach(i -> out.println(printer.stringOf(i) + "\n"));
        }
    }

    @Override
    public void showAllCategories() {
        out.print("All Categories: ");
        showCategories(controller.getCategories());
    }

    @Override
    public void showCategories(List<Category> categories) {
        if (categories.isEmpty()) {
            out.println("There are no categories");
        } else {
            CategoryPrinter printer = new BasicCategoryPrinter();
            String category = "  ";
            for (int i = 0; i < categories.size(); i++) {
                category += printer.stringOf(categories.get(i)) + ", ";
            }
            category = category.substring(0, category.length() - 2);
            out.println(category);
        }
    }

    @Override
    public String showAlertCategory() {
        out.println("If you remove a category, it will be removed from all transactions and movements that contain it");
        out.println("Continue anyway?");
        out.println("Y-N");
        return readString();
    }

    @Override
    public void showAllMovements(Account account) {
        showMovements(account.getMovement());
    }

    @Override
    public void showMovements(List<Movement> movements) {
        if (movements.isEmpty()) {
            out.println("There are no movements ");
        } else {
            BasicCategoryPrinter cprinter = new BasicCategoryPrinter();
            BasicMovementPrinter printer = new BasicMovementPrinter(cprinter);
            movements.forEach(i -> out.println(printer.stringOf(i) + "\n"));
        }
    }

    @Override
    public String anotherMovements() {
        out.println("Another movement? ");
        out.println("Y | N");
        return readString().toUpperCase();
    }

    @Override
    public void showAllTransaction() {
        showTransactions(controller.getTransactions());
    }

    @Override
    public void showTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            out.println("There are no transaction");
        } else {
            BasicCategoryPrinter cprinter = new BasicCategoryPrinter();
            BasicTransactionPrinter printer = new BasicTransactionPrinter(cprinter);
            transactions.forEach(i -> out.println(printer.stringOf(i) + "\n"));
        }
    }

    @Override
    public String askScheduled() {
        out.println(
                "The date entered is ahead of today's date. Do you want to insert this Transaction as a Scheduled Transaction?");
        return readString().toUpperCase();
    }


    @Override
    public void showAllBudget() {
        if (controller.getNumbersOfBudgets() == 0) {
            out.println("There are no budgets");
        } else {
            CategoryPrinter cprinter = new BasicCategoryPrinter();
            BudgetPrinter printer = new BasicBudgetPrinter(cprinter);
            controller.getBudgets().forEach(i -> out.println(printer.stringOf(i)));
        }
    }

    @Override
    public void showProgress(BudgetReport budgetReport) {
        CategoryPrinter cprinter = new BasicCategoryPrinter();
        BudgetPrinter bprinter = new BasicBudgetPrinter(cprinter);
        BudgetReportPrinter printer = new BasicBudgetReportPrinter(bprinter);
        out.println(printer.stringOf(budgetReport));
    }

}

