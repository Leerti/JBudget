package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Questa classe definisce un Ledger e contiene i metodi necessari per crearlo e gestirlo.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicLedger implements Ledger {
    private List<Transaction> transactions;
    private List<Category> categories;
    private List<ScheduledTransaction> scheduledTransaction;
    private List<Account> accounts;
    private List<Budget> budgets;
    private boolean on;

    /**
     * Costruttore basico di un Ledger
     * Crea un nuovo ledger con tutte List vuote per i suoi parametri:
     * transactions, categories, sheduled Transaction, accounts e budgets.
     * Imposta il parametro on a true.
     */
    public BasicLedger() {
        this.transactions = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.scheduledTransaction = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.on = true;
    }

    /**
     * Costruttore di un Ledger che viene usato dal sistema di caricamento dei dati.
     *
     * @param accounts   la List di Account da aggiungere al Ledger
     * @param categories la List di Category da aggiungere al Ledger
     */
    public BasicLedger(List<Account> accounts, List<Category> categories) {
        this.accounts = accounts;
        this.categories = categories;
        this.transactions = new ArrayList<>();
        this.scheduledTransaction = new ArrayList<>();
        this.budgets = new ArrayList<>();
        this.on = true;

    }

    @Override
    public void removeScheduledTransaction() {
        int i = 0;
        while (!scheduledTransaction.isEmpty()&&scheduledTransaction.size()>i) {
            if (scheduledTransaction.get(i).isCompleted()) {
                scheduledTransaction.remove(scheduledTransaction.get(i));
            } else i++;
        }
    }


    @Override
    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public Transaction getTransaction(int ID) throws TransactionDoNotExist {
        for (int i = 0; i < getTransactions().size(); i++) {
            if (getTransactions().get(i).getID() == ID) {
                return getTransactions().get(i);
            }
        }return getScheduledTransaction(ID).getTransaction();
    }

    @Override
    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        for (int i = 0; i < transaction.getMovements().size(); i++) {
            transaction.getMovements().get(i).getAccount().addMovement(transaction.getMovements().get(i));
        }
    }

    @Override
    public void addMovementToTransaction(Movement movement, int transaction) throws TransactionDoNotExist {
        getTransaction(transaction).addMovement(movement);
        movement.getAccount().addMovement(movement);
    }

    @Override
    public void removeMovementFromTransaction(int movement)  {
        Movement movementRemove = getMovement(movement);
        movementRemove.getAccount().deleteMovement(getMovement(movement));
        movementRemove.getTransaction().removeMovement(movementRemove);
    }

    @Override
    public void removeTransaction(Transaction transaction) throws TransactionDoNotExist {
        if (transactions.contains(transaction)) {
            for (int i = 0; i < transaction.getMovements().size(); i++) {
                transaction.getMovements().get(i).getAccount().deleteMovement(transaction.getMovements().get(i));
            }
            this.transactions.remove(transaction);
        } else throw new TransactionDoNotExist(transaction.getID());
    }

    @Override
    public List<Transaction> getTransactions(Predicate<Transaction> predicate) {
        return this.transactions.stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public Movement getMovement(int ID) {
        for (Transaction transaction : transactions) {
            for (Movement movement : transaction.getMovements()) {
                if (movement.getID() == ID) return movement;
            }
        }
        return null;
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public Category getCategory(String name) throws CategoryDoNotExist {
        Category category = new BasicCategory(name);
        for (int i = 0; i < this.getCategories().size(); i++) {
            if (this.getCategories().get(i).equals(category)) {
                return this.getCategories().get(i);
            }
        }
        throw new CategoryDoNotExist(name);
    }

    @Override
    public boolean addCategory(String name) throws CategoryAlreadyExisting {
        Category category = new BasicCategory(name.toUpperCase());
        for (int i = 0; i < this.getCategories().size(); i++) {
            if (this.getCategories().get(i).equals(category)) {
                throw new CategoryAlreadyExisting(name);
            }
        }
        return categories.add(new BasicCategory(name));
    }

    @Override
    public void removeCategory(String name) throws CategoryDoNotExist {
        Category category = getCategory(name);
        if (category != null) {
            for (int i = 0; i < transactions.size(); i++) {
                if (transactions.get(i).getCategories().contains(category)) {
                    transactions.get(i).removeCategory(category.getName());
                }
            }
            for (int i = 0; i < budgets.size(); i++) {
                if (budgets.get(i).getCategory().equals(category)) {
                    removeBudget(budgets.get(i));
                }
            }
            categories.remove(category);
        }
    }

    @Override
    public void addScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        this.scheduledTransaction.add(scheduledTransaction);
    }

    @Override
    public List<ScheduledTransaction> getScheduledTransactions() {
        return this.scheduledTransaction;
    }


    @Override
    public ScheduledTransaction getScheduledTransaction(int ID) throws TransactionDoNotExist {
        for (int i = 0; i < scheduledTransaction.size(); i++) {
            if (scheduledTransaction.get(i).getTransaction().getID() == ID) {
                return scheduledTransaction.get(i);
            }
        }
        new TransactionDoNotExist(ID);
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        return this.accounts;
    }

    @Override
    public Account getAccount(int ID) throws AccountDoNotExist {
        for (int i = 0; i < getAccounts().size(); i++) {
            if (getAccounts().get(i).getID() == ID) {
                return getAccounts().get(i);
            }
        }
        throw new AccountDoNotExist(ID);
    }

    @Override
    public Account addAccount(String name, String description, AccountType accountType, double initialBalance) {
        Account account = new BasicAccount(name, description, accountType, initialBalance);
        this.accounts.add(account);
        return account;
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOff() {
        this.on = false;
    }

    @Override
    public void addBudget(Budget budget) throws BudgetAlreadyExist {
        Category category = budget.getCategory();
        for (Budget singlebudget : budgets) {
            if (singlebudget.getCategory().equals(category)) {
                throw new BudgetAlreadyExist(category);
            }
        }
        this.budgets.add(budget);
    }

    @Override
    public Budget getBudget(int ID) throws BudgetDoNotExist {
        for (int i = 0; i < getBudgets().size(); i++) {
            if (getBudgets().get(i).getID() == ID) {
                return getBudgets().get(i);
            }
        }
        throw new BudgetDoNotExist(ID);
    }

    @Override
    public List<Budget> getBudgets() {
        return this.budgets;
    }

    @Override
    public void removeBudget(Budget budget) throws BudgetDoNotExist {
        if (this.budgets.contains(budget)) {
            this.budgets.remove(budget);
        } else throw new BudgetDoNotExist(budget.getID());
    }

    @Override
    public void addMovementToScheduledTransaction(Movement movement, int idTransaction) {
        for (ScheduledTransaction scheduled : scheduledTransaction) {
            if (scheduled.getTransaction().getID() == idTransaction) {
                scheduled.getTransaction().addMovement(movement);
            }
        }

    }

    @Override
    public List<Transaction> getScheduledTransaction(Predicate predicate) {
        List<Transaction> scheduledTransactions = new ArrayList<>();
        for (ScheduledTransaction scheduled : getScheduledTransactions()) {
            scheduledTransactions.add(scheduled.getTransaction());
        }
        return (List<Transaction>) scheduledTransactions.stream().filter(predicate).collect(Collectors.toList());
    }

}
