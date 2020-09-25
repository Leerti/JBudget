package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Exception.*;
import it.unicam.cs.pa.jbudget105532.Json.JsonSynch;
import it.unicam.cs.pa.jbudget105532.Json.Synch;
import it.unicam.cs.pa.jbudget105532.Model.*;

import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa classe definisce un Controller.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicController implements Controller {
    private Ledger ledger;
    private Scheduler scheduler;
    private BudgetReport budgetReport;
    private Synch synch;

    public BasicController() {
        this(new JsonSynch());
    }

    public BasicController(Synch synch) {
        this.synch=synch;
        try {
            ledger = this.synch.readLedger("Synch.json");
        } catch (Exception e) {
            ledger = new BasicLedger();
        }
        scheduler = new BasicScheduler();
    }


    @Override
    public int getNumbersOfAccount() {
        return this.ledger.getAccounts().size();
    }

    @Override
    public int getNumbersOfTransactions() {
        return this.ledger.getTransactions().size();
    }

    @Override
    public int getNumbersOfCategory() {
        return this.ledger.getCategories().size();
    }

    @Override
    public int getNumbersOfBudgets() {
        return this.ledger.getBudgets().size();
    }


    @Override
    public void checkScheduledTransaction() {
        for (int i = 0; i < ledger.getScheduledTransactions().size(); i++) {
            scheduler.insert(ledger.getScheduledTransactions().get(i), ledger);
            ledger.removeScheduledTransaction();
        }
    }

    @Override
    public void save() {
        try {
            synch.writeLedger(ledger, "Synch.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOn() {
        return ledger.isOn();
    }

    @Override
    public void turnOff() {
        save();
        ledger.turnOff();
    }

    @Override
    public Ledger getLedger() {
        return this.ledger;
    }

    @Override
    public Account addAccount(HashMap<String, String> info) throws StringBlankException {
        String name = info.get("Name");
        if (name.isBlank()) {
            throw new StringBlankException();
        }
        String description = info.get("Description");
        AccountType type = AccountType.valueOf(info.get("Type").toUpperCase());
        double initialBalance = Double.parseDouble(info.get("InitialBalance"));
        return ledger.addAccount(name, description, type, initialBalance);
    }

    @Override
    public List<Account> getAccounts() {
        return ledger.getAccounts();
    }

    @Override
    public Account getAccount(int account) throws AccountDoNotExist {
        return ledger.getAccount(account);
    }

    @Override
    public void modifyAccount(HashMap<String, Object> info) throws StringBlankException {
        Account account = ledger.getAccount((int) info.get("Account"));
        String name = (String) info.get("Name");
        if (name.isBlank()) {
            throw new StringBlankException();
        }
        account.setName(name);
        account.setDescription((String) info.get("Description"));
        account.setInitialBalance(Double.parseDouble((String) info.get("InitialBalance")));
    }

    @Override
    public String getAccountName(int account) {
        return ledger.getAccount(account).getName();
    }

    @Override
    public String getAccountDescription(int account) {
        return ledger.getAccount(account).getDescription();
    }

    @Override
    public String getAccountInitialBalance(int account) {
        return String.valueOf(ledger.getAccount(account).getInitialBalance());
    }


    @Override
    public Predicate<Movement> createDatePredicate(LocalDate date) {
        return movement -> movement.getDate().equals(date);
    }

    @Override
    public List<Movement> filterMovementForData(LocalDate date, int account) {
        List<Movement> movements = ledger.getAccount(account).getMovement(createDatePredicate(date));
        return movements;
    }


    @Override
    public List<Movement> filterMovementForCategory(String category, int account) throws CategoryDoNotExist, StringBlankException {
        if (category.isBlank()) throw new StringBlankException();
        List<Movement> movements = ledger.getAccount(account).getMovement(createCategoryPredicate(ledger.getCategory(category)));
        return movements;
    }

    @Override
    public Predicate<Movement> createCategoryPredicate(Category category) {
        return movement -> movement.getCategory().contains(category);
    }

    @Override
    public List<Category> getCategories() {
        return ledger.getCategories();
    }

    @Override
    public Category getCategory(String name) throws StringBlankException, CategoryDoNotExist {
        if (name.isBlank()) throw new StringBlankException();
        return ledger.getCategory(name);
    }

    @Override
    public void addCategory(String info) throws CategoryAlreadyExisting, StringBlankException {
        if (info.isBlank()) throw new StringBlankException();
        ledger.addCategory(info);
    }


    @Override
    public void removeCategory(String category) throws CategoryDoNotExist, StringBlankException {
        if (category.isBlank()) throw new StringBlankException();
        ledger.removeCategory(category);
    }

    @Override
    public List<Budget> getBudgets() {
        return ledger.getBudgets();
    }

    @Override
    public Budget getBudget(int budget) throws BudgetDoNotExist {
        return ledger.getBudget(budget);
    }

    @Override
    public void addBudget(HashMap<String, String> info) throws CategoryDoNotExist, BudgetAlreadyExist, StringBlankException {
        String nameCategory = info.get("Category");
        if (nameCategory.isBlank()) throw new StringBlankException();
        Category category = ledger.getCategory(nameCategory);
        double amount = Double.parseDouble(info.get("Amount"));
        ledger.addBudget(new BasicBudget(category, amount));
    }

    @Override
    public void modifyBudget(double newAmount, int budget) {
        ledger.getBudget(budget).setAmount(newAmount);
    }

    @Override
    public void removeBudget(int budgetID) throws BudgetDoNotExist {
        ledger.removeBudget(ledger.getBudget(budgetID));
    }

    @Override
    public BudgetReport getBudgetReport(int budget) {
        BudgetReport report = new BasicBudgetManager().generateReport(ledger, ledger.getBudget(budget));
        this.budgetReport = report;
        return this.budgetReport;
    }

    @Override
    public double getBudgetAmount(int budget) {
        return ledger.getBudget(budget).getAmount();
    }

    @Override
    public List<Integer> getYearsOfBudgetReport(int budget) {
        BudgetReport report = new BasicBudgetManager().generateReport(ledger, ledger.getBudget(budget));
        this.budgetReport = report;
        return budgetReport.years();
    }

    @Override
    public HashMap<String, List<XYChart.Data<String, Double>>> progressInformation(int year) {
        HashMap<String, List<XYChart.Data<String, Double>>> info = new HashMap<>();
        List<Movement> movements = budgetReport.selectedMovement(year);
        List<XYChart.Data<String, Double>> income = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            XYChart.Data<String, Double> dato =
                    new XYChart.Data<>(String.valueOf(i), budgetReport.calculateIncomePerMonth(i - 1, movements));
            income.add(dato);
        }
        info.put("Income", income);
        List<XYChart.Data<String, Double>> expense = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            XYChart.Data<String, Double> dato =
                    new XYChart.Data<>(String.valueOf(i), budgetReport.calculateExpensePerMonth(i - 1, movements));
            expense.add(dato);
        }
        info.put("Expense", expense);
        List<XYChart.Data<String, Double>> total = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            XYChart.Data<String, Double> dato = new XYChart.Data<>(String.valueOf(i), budgetReport.calculateTotalPerMonth(i - 1, movements));
            total.add(dato);
        }
        info.put("Total", total);
        List<XYChart.Data<String, Double>> amount = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            XYChart.Data<String, Double> dato = new XYChart.Data<>(String.valueOf(i), budgetReport.getBudget().getAmount());
            amount.add(dato);
        }
        info.put("Amount", amount);
        return info;
    }

    @Override
    public Transaction selectTransaction(int transaction) throws TransactionDoNotExist {
        Transaction transactionFound;
        try {
            transactionFound = ledger.getTransaction(transaction);
        } catch (TransactionDoNotExist e) {
            transactionFound = ledger.getScheduledTransaction(transaction).getTransaction();
        }

        return transactionFound;
    }

    @Override
    public List<Transaction> getTransactions() {
        return ledger.getTransactions();
    }

    @Override
    public List<Transaction> getScheduledTransactions() {
        List<ScheduledTransaction> scheduledTransactions = ledger.getScheduledTransactions();
        List<Transaction> transactions = new ArrayList<>();
        for (ScheduledTransaction scheduledTransaction : scheduledTransactions) {
            transactions.add(scheduledTransaction.getTransaction());
        }
        return transactions;
    }

    @Override
    public List<Movement> getTransactionMovements(int transaction) {
        Transaction transactionFound = ledger.getTransaction(transaction);
        if (transactionFound == null) transactionFound = ledger.getScheduledTransaction(transaction).getTransaction();
        return transactionFound.getMovements();
    }

    @Override
    public void modifyTransaction(HashMap<String, Object> info) throws CategoryAlreadyAdded {
        Transaction transaction = ledger.getTransaction((int) info.get("Transaction"));
        transaction.setDate((LocalDate) info.get("Date"));
        List<Category> categories = (List<Category>) info.get("Categories");
        transaction.removeAllCategories();
        categories.forEach(i -> transaction.addCategory(i));
    }

    @Override
    public Movement createNewMovement(HashMap<String, Object> info) throws AccountDoNotExist, StringBlankException {
        double amount = (double) info.get("Amount");
        String description = (String) info.get("Description");
        String typeString = (String) info.get("Type");
        MovementType type = MovementType.valueOf((typeString.toUpperCase()));
        String IdAccount = String.valueOf((int) info.get("Account"));
        if (IdAccount.isBlank()) throw new StringBlankException();
        Account account = ledger.getAccount(Integer.parseInt(IdAccount));
        List<Category> categories = (List<Category>) info.get("Categories");
        return new BasicMovement(amount, description, type, categories, account);
    }


    @Override
    public void addMovement(HashMap<String, Object> info) throws AccountDoNotExist, StringBlankException {
        try {
            ledger.addMovementToTransaction(createNewMovement(info), (int) info.get("Transaction"));
        } catch (TransactionDoNotExist e) {
            ledger.addMovementToScheduledTransaction(createNewMovement(info), (int) info.get("Transaction"));
        }
    }

    @Override
    public void removeMovement(int movementId) {
        ledger.removeMovementFromTransaction(movementId);
    }

    @Override
    public void addTransaction(HashMap<String, Object> info) throws StringBlankException {
        LocalDate date = (LocalDate) info.get("Date");
        if (date == null) throw new NullPointerException();
        List<Category> categories = (List<Category>) info.get("Categories");
        List<Movement> movements = (List<Movement>) info.get("Movements");
        Transaction transaction = new BasicTransaction(movements, categories, date);
        String type = (String) info.get("Type");
        if (type.isBlank()) throw new StringBlankException();
        if (type.equals("Normal")) {
            ledger.addTransaction(transaction);
        } else ledger.addScheduledTransaction(new BasicScheduledTransaction(transaction));
    }


    @Override
    public List<Transaction> filterTransactionForDate(LocalDate dateForTransaction) {
        return ledger.getTransactions(createDatePredicateForTransaction(dateForTransaction));
    }

    @Override
    public Predicate<Transaction> createDatePredicateForTransaction(LocalDate dateForTransaction) {
        return transaction -> transaction.getDate().equals(dateForTransaction);
    }


    @Override
    public List<Transaction> filterTransactionForCategory(String categoryNameForTransaction) throws CategoryDoNotExist {
        return ledger.getTransactions(createCategoryPredicateForTransaction(ledger.getCategory(categoryNameForTransaction)));
    }

    @Override
    public Predicate<Transaction> createCategoryPredicateForTransaction(Category category) {
        return transaction -> transaction.getCategories().contains(category);
    }


    @Override
    public void removeTransaction(int transaction) throws TransactionDoNotExist {
        ledger.removeTransaction(ledger.getTransaction(transaction));

    }


    @Override
    public LocalDate getTransactionDate(int transaction) {
        return ledger.getTransaction(transaction).getDate();
    }


    @Override
    public List<Transaction> filterScheduledTransactionForDate(LocalDate date) {
        return ledger.getScheduledTransaction(createDatePredicateForTransaction(date));
    }

    @Override
    public List<Transaction> filterScheduledTransactionForCategory(String category) throws CategoryDoNotExist {
        return ledger.getScheduledTransaction(createCategoryPredicateForTransaction(ledger.getCategory(category)));
    }
}
