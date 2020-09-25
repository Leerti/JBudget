package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Exception.*;
import it.unicam.cs.pa.jbudget105532.Model.*;
import it.unicam.cs.pa.jbudget105532.View.View;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BasicCommand implements Command {


    private View view;
    private Account account;
    private Transaction transaction;
    private Budget budget;
    private Controller controller;

    private final int INITIALMENU = 0;
    private final int ACCOUNTMENU = 1;
    private final int CHOICEETRANSACTIONMENU = 2;
    private final int TRANSACTIONMENU = 3;
    private final int CATEGORYMENU = 4;
    private final int BUDGETMENU = 5;
    private final int ONEACCOUNTMENU = 6;
    private final int ONETRANSACTIONMENU = 7;
    private final int ONEBUDGETMENU = 8;
    private final int MODIFYACCOUNTMENU = 9;
    private final int MODIFYTRANSCTIONMENU = 10;

    private int menu;

    private final String ERRORINITIALBALANCE = "Initial balance is not correct, please, insert again.";
    private final String ERRORNAME = "The name is not correct, please, insert again.";
    private final String ERRORTYPE = "The type is not correct, please, insert again.";
    private final String ERRORDATE = "The date is not correct, please, insert again.";


    public BasicCommand(View view, Controller controller) {
        this.account = null;
        this.budget = null;
        this.transaction = null;
        this.view = view;
        this.controller = controller;
        menu = INITIALMENU;
    }

    @Override
    public void checkScheduledTransaction() {
        controller.checkScheduledTransaction();
    }

    @Override
    public Controller getController() {
        return this.controller;
    }

    @Override
    public void turnOff() {
        controller.turnOff();
    }

    @Override
    public void addAccount() {
        HashMap<String, String> info = new HashMap<>();
        String name = view.getName();
        String description = view.getDescription();
        double initialBalance = view.getInitialBalance();
        String type = view.getAccountType();
        boolean completed = false;
        while (!completed) {
            info.put("Name", name);
            info.put("Description", description);
            info.put("InitialBalance", String.valueOf(initialBalance));
            info.put("Type", type);
            try {
                controller.addAccount(info);
                completed = true;
                account();
            } catch (StringBlankException e) {
                view.showErrorMessage(ERRORNAME);
                name = view.getName();
            } catch (IllegalArgumentException e) {
                view.showErrorMessage(ERRORTYPE);
                type = view.getAccountType();
            }
        }
    }

    @Override
    public void showAllAccount() {
        view.showAllAccount();
        account();
    }

    @Override
    public void selectAccount() {
        view.showAllAccount();
        boolean completed = false;
        while (!completed) {
            int ID = view.getID();
            try {
                Account account = controller.getAccount(ID);
                completed = true;
                this.account = account;
                oneAccount();
            } catch (AccountDoNotExist e) {
                view.showErrorMessage(e.getMessage());
            }
        }

    }

    @Override
    public void modifyNameAccount() {
        String name = view.getName();
        HashMap<String, Object> info = new HashMap<>();
        boolean completed = false;
        while (!completed) {
            info.put("Account", account.getID());
            info.put("Name", name);
            info.put("Description", controller.getAccountDescription(account.getID()));
            info.put("InitialBalance", controller.getAccountInitialBalance(account.getID()));
            try {
                controller.modifyAccount(info);
                completed = true;
                modifyAccount();
            } catch (StringBlankException e) {
                view.showErrorMessage(ERRORNAME);
                name = view.getName();
            }
        }
    }

    @Override
    public void modifyDescriptionAccount() {
        HashMap<String, Object> info = new HashMap<>();
        String description = view.getDescription();
        info.put("Account", account.getID());
        info.put("Name", controller.getAccountName(account.getID()));
        info.put("Description", description);
        info.put("InitialBalance", controller.getAccountInitialBalance(account.getID()));
        controller.modifyAccount(info);
        modifyAccount();
    }

    @Override
    public void modifyInitialBalance() {
        HashMap<String, Object> info = new HashMap<>();
        double initialBalance = view.getInitialBalance();
        boolean completed = false;
        while (!completed) {
            info.put("Account", account.getID());
            info.put("Name", controller.getAccountName(account.getID()));
            info.put("Description", controller.getAccountDescription(account.getID()));
            info.put("InitialBalance", String.valueOf(initialBalance));
            try {
                controller.modifyAccount(info);
                completed = true;
                modifyAccount();
            } catch (NumberFormatException e) {
                view.showErrorMessage(ERRORINITIALBALANCE);
                initialBalance = view.getInitialBalance();
            }
        }
    }

    @Override
    public void showAllMovement() {
        view.showAllMovements(account);
        oneAccount();
    }

    @Override
    public void filterMovementForData() {
        HashMap info = view.getDate();
        boolean completed = false;
        while (!completed) {
            try {
                view.showMovements(controller.filterMovementForData(LocalDate.of((int) info.get("Year"),
                        (int) info.get("Month"), (int) info.get("Day")), account.getID()));
                completed = true;
                oneAccount();
            } catch (DateTimeException e) {
                view.showErrorMessage(ERRORDATE);
                info = view.getDate();
            }
        }
    }

    @Override
    public void filterMovementForCategory() {
        boolean completed = false;
        while (!completed) {
            view.showAllCategories();
            String category = view.getCategory();
            try {
               view.showMovements(controller.filterMovementForCategory(category, account.getID()));
                completed = true;
                oneAccount();
            } catch (CategoryDoNotExist | StringBlankException e) {
                view.showErrorMessage(e.getMessage());
            }

        }
    }


    @Override
    public void addCategory() {
        boolean completed = false;
        while (!completed) {
            String category = view.getName();
            try {
                controller.addCategory(category);
                completed = true;
                category();
            } catch (CategoryAlreadyExisting | StringBlankException e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }

    @Override
    public void removeCategory() {
        if (view.showAlertCategory().toUpperCase().equals("Y")) {

            boolean completed = false;
            while (!completed) {
                String category = view.getCategory().toUpperCase();
                try {
                    controller.removeCategory(category);
                    completed = true;
                    category();
                } catch (CategoryDoNotExist | CategoryNotAdded e) {
                    view.showErrorMessage(e.getMessage());
                }
            }
        }
    }

    @Override
    public void showAllCategories() {
        view.showAllCategories();
        category();
    }

    @Override
    public boolean checkCategory() {
        if (controller.getNumbersOfCategory() == 0) {
            view.showErrorMessage("There are no categories ,please insert one before creating transaction");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkAccount() {
        if (controller.getNumbersOfAccount() == 0) {
            view.showErrorMessage("There are no accounts,please insert one before creating transaction");
            return false;
        }
        return true;
    }

    @Override
    public void addTransaction() {
        if (checkAccount()) {
            if (checkCategory()) {
                HashMap<String, Object> info = new HashMap<>();
                List<Category> categories = new ArrayList<>();
                String resp = null;
                do {
                    try {
                        view.showAllCategories();
                        String nameCategory = view.getCategory();
                        categories.add(controller.getCategory(nameCategory));
                    } catch (CategoryDoNotExist | StringBlankException | CategoryAlreadyAdded e) {
                        view.showErrorMessage(e.getMessage());
                    }
                    resp = view.anotherCategory();
                } while (resp.equals("Y"));

                info.put("Categories", categories);
                LocalDate date = null;
                HashMap<String, String> dateInfo;
                boolean completed = false;
                while (!completed) {
                    try {
                        dateInfo = view.getDate();
                        date = LocalDate.of(Integer.parseInt(dateInfo.get("Year")),
                                Integer.parseInt(dateInfo.get("Month")), Integer.parseInt(dateInfo.get("Day")));
                        info.put("Date", date);
                        completed = true;
                    } catch (DateTimeException e) {
                        view.showErrorMessage(ERRORDATE);
                    }
                }
                if (date.compareTo(LocalDate.now()) > 0) {
                    if (view.askScheduled().equals("Y")) {
                        info.put("Type", "Scheduled");
                    } else {
                        info.put("Type", "Normal");
                    }
                } else {
                    info.put("Type", "Normal");
                }
                ArrayList<Movement> movements = new ArrayList<>();
                String response;
                do {
                    movements.add(newMovement());
                    response = view.anotherMovements();
                } while (response.equals("Y"));
                info.put("Movements", movements);
                controller.addTransaction(info);
                transaction();
            }
        }
    }


    @Override
    public Movement newMovement() {
        HashMap<String, Object> infoForController = new HashMap<>();
        view.newMovement();
        String description = view.getDescription();
        double amount = view.getAmount();
        view.showAllAccount();
        int account = view.getAccountID();
        String type = view.getMovementType();
        List<Category> categories = new ArrayList<>();
        String resp = null;
        do {
            try {
                view.showAllCategories();
                String nameCategory = view.getCategory();
                categories.add(controller.getCategory(nameCategory));
            } catch (CategoryDoNotExist | StringBlankException | CategoryAlreadyAdded e) {
                view.showErrorMessage(e.getMessage());
            }
            resp = view.anotherCategory();
        } while (resp.equals("Y"));
        Movement movement = null;
        boolean completed = false;
        while (!completed) {
            try {
                infoForController.put("Categories", categories);
                infoForController.put("Account", account);
                infoForController.put("Type", type);
                infoForController.put("Amount", amount);
                infoForController.put("Description", description);
                movement = controller.createNewMovement(infoForController);
                completed = true;
            } catch (IllegalArgumentException e) {
                view.showErrorMessage(ERRORTYPE);
                type = view.getMovementType();
            }
        }
        return movement;
    }

    @Override
    public void showAllTransaction() {
        view.showAllTransaction();
        transaction();
    }

    @Override
    public void selectTransaction() {
        Transaction transaction = null;
        while (transaction == null) {
            try {
                view.showAllTransaction();
                int ID = view.getID();
                transaction = controller.selectTransaction(ID);
            } catch (TransactionDoNotExist e) {
                view.showErrorMessage(e.getMessage());
            }
        }
        this.transaction = transaction;
        oneTransaction();
    }

    @Override
    public void filterTransactionForDate() {
        boolean completed = false;
        while (!completed) {
            try {
                HashMap<String, String> info = view.getDate();
                LocalDate date = LocalDate.of(Integer.parseInt(info.get("Year")),
                        Integer.parseInt(info.get("Month")), Integer.parseInt(info.get("Day")));
                view.showTransactions(controller.filterTransactionForDate(date));
                completed = true;
                transaction();
            } catch (DateTimeException e) {
                view.showErrorMessage(ERRORDATE);
            }
        }
    }

    @Override
    public void filterTransactionForCategory() {
        boolean completed = false;
        while (!completed) {
            try {
                String category = view.getCategory();
                view.showTransactions(controller.filterTransactionForCategory(category));
                completed = true;
                transaction();
            } catch (StringBlankException | CategoryDoNotExist e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }

    @Override
    public void showAllMovementsOfTransaction() {
        view.showMovements(controller.getTransactionMovements(transaction.getID()));
        oneTransaction();
    }

    @Override
    public void modifyDate() {
        HashMap<String, String> dataInfo = view.getDate();
        HashMap<String, Object> info = new HashMap<>();
        info.put("Transaction", transaction.getID());
        LocalDate date = null;
        boolean completed = false;
        while (!completed) {
            try {
                date = LocalDate.of(Integer.parseInt(dataInfo.get("Year")),
                        Integer.parseInt(dataInfo.get("Month")), Integer.parseInt(dataInfo.get("Day")));
                completed = true;
            } catch (DateTimeException e) {
                view.showErrorMessage(ERRORDATE);
            }
        }
        info.put("Data", date);
        info.put("Categories", transaction.getCategories());
        controller.modifyTransaction(info);
    }

    @Override
    public void addCategoryForTransaction() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Transaction", transaction.getID());
        info.put("Date", transaction.getDate());
        String categoryName = "";
        Category category = null;
        while (category == null) {
            try {
                view.showAllCategories();
                categoryName = view.getCategory();
                List<Category> categories = transaction.getCategories();
                categories.add(controller.getCategory(categoryName));
                info.put("Categories", categories);
                controller.modifyTransaction(info);
                modifyTransaction();
            } catch (CategoryDoNotExist | CategoryAlreadyAdded e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }

    @Override
    public void removeCategoryForTransaction() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Transaction", transaction.getID());
        info.put("Date", transaction.getDate());
        String categoryName = "";
        Category category = null;
        while (category == null) {
            try {
                view.showAllCategories();
                categoryName = view.getCategory();
                List<Category> categories = transaction.getCategories();
                categories.remove(controller.getCategory(categoryName));
                info.put("Categories", categories);
                controller.modifyTransaction(info);
                modifyTransaction();
            } catch (CategoryDoNotExist e) {
                view.showErrorMessage(e.getMessage());
            } catch (CategoryNotAdded e) {
                view.showErrorMessage(e.getMessage());
            }
        }
    }

    @Override
    public void removeTransaction() {
        controller.removeTransaction(transaction.getID());
        transaction();
    }

    @Override
    public void removeBudget() {
        controller.removeBudget(budget.getID());
        budget();
    }

    @Override
    public void selectOneBudget() {
        int ID;
        Budget budget = null;
        while (budget == null) {
            try {
                view.showAllBudget();
                ID = view.getID();
                budget = controller.getBudget(ID);
            } catch (BudgetDoNotExist e) {
                view.showErrorMessage(e.getMessage());
            }
        }
        this.budget = budget;
        oneBudget();
    }

    @Override
    public void showAllBudget() {
        view.showAllBudget();
        budget();
    }

    @Override
    public void addBudget() {
        HashMap<String, String> info = new HashMap<>();
        view.showAllCategories();
        String categoryName = view.getCategory();
        double amount = view.getAmount();
        boolean completed = false;
        while (!completed) {
            try {
                info.put("Category", categoryName);
                info.put("Amount", String.valueOf(amount));
                controller.addBudget(info);
                completed = true;
                budget();
            } catch (CategoryDoNotExist e) {
                view.showErrorMessage(e.getMessage());
                view.showAllCategories();
                categoryName = view.getCategory();
            } catch (BudgetAlreadyExist e) {
                view.showErrorMessage(e.getMessage());
                budget();
                break;
            }
        }
    }

    @Override
    public void showProgress() {
        BudgetReport budgetReport = controller.getBudgetReport(budget.getID());
        view.showProgress(budgetReport);
        oneBudget();
    }

    @Override
    public void changeAmount() {
       controller.modifyBudget(view.getAmount(),budget.getID());
        oneBudget();
    }



    @Override
    public void account() {
        this.menu = ACCOUNTMENU;
        view.getAccountsMenu();
        this.account = null;
    }


    @Override
    public void transaction() {
        this.menu = TRANSACTIONMENU;
        view.getTransactionsMenu();
        this.transaction = null;
    }



    @Override
    public void budget() {
        this.menu = BUDGETMENU;
        view.getBudgetMenu();
        this.budget = null;
    }



    @Override
    public void category() {
        this.menu = CATEGORYMENU;
        view.getCategoriesMenu();
    }



    @Override
    public void initial() {
        this.menu = INITIALMENU;
        view.printInitialCommands();
    }



    @Override
    public void oneAccount() {
        this.menu = ONEACCOUNTMENU;
        view.getMenuOneAccount();
    }



    @Override
    public void modifyAccount() {
        this.menu = MODIFYACCOUNTMENU;
        view.getMenuModifyAccount();
    }


    @Override
    public void oneTransaction() {
        this.menu = ONETRANSACTIONMENU;
        view.getMenuOneTransaction();
    }



    @Override
    public void modifyTransaction() {
        this.menu = MODIFYTRANSCTIONMENU;
        view.getMenuModifyTransaction();
    }


    @Override
    public void oneBudget() {
        this.menu = ONEBUDGETMENU;
        view.getMenuOneBudget();
    }


    @Override
    public void back() {
        switch (menu) {
            case (TRANSACTIONMENU):
            case (ACCOUNTMENU):
            case (CATEGORYMENU):
            case (BUDGETMENU): {
                initial();
                break;
            }
            case (ONEACCOUNTMENU): {
                account();
                break;
            }
            case (ONETRANSACTIONMENU): {
                transaction();
                break;
            }
            case (ONEBUDGETMENU): {
                budget();
                break;
            }
            case (MODIFYACCOUNTMENU): {
                oneAccount();
                break;
            }
            case (MODIFYTRANSCTIONMENU): {
                oneTransaction();
                break;
            }

        }
    }


}
