package it.unicam.cs.pa.jbudget105532.Json;

import it.unicam.cs.pa.jbudget105532.Exception.AccountDoNotExist;
import it.unicam.cs.pa.jbudget105532.Exception.BudgetAlreadyExist;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryDoNotExist;
import it.unicam.cs.pa.jbudget105532.Model.*;


import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Questa classe  ha la responsabilita di gestire la persistenza dei dati ed utilizza
 * la tecnologia JSON.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class JsonSynch implements Synch {

    /**
     * Preso un oggetto di tipo Category, restituisce un oggetto di tipo JSONObject
     * che rappresenta la Category
     *
     * @param category la Category da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta la Category inseirita
     * come parametro
     */
    private JSONObject writeCategory(Category category) {
        JSONObject jsonCategory = new JSONObject();
        jsonCategory.put("Name", category.getName());
        return jsonCategory;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta una Category ,
     * restituisce la Category rappresentata dal JSONObject
     *
     * @param jsonCategory l'oggetto JSONObject che rappresenta la Category
     * @return un oggetto di tipo Category
     */
    private Category readCategory(JSONObject jsonCategory) {
        return new BasicCategory(jsonCategory.getString("Name"));
    }

    /**
     * Presa una List di Category, restituisce un oggetto di tipo JSONArray che rappresenta la List
     *
     * @param categories List di Category da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di Category
     */
    private JSONArray writeCategories(List<Category> categories) {
        JSONArray jsonCategoriesList = new JSONArray();
        for (Category category : categories) {
            jsonCategoriesList.put(writeCategory(category));
        }
        return jsonCategoriesList;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di Category ,
     * restituisce la List di Category rappresentata dal JSONArray
     *
     * @param jsonCategories l'oggetto JSONArray che rappresenta la List di Category
     * @return un oggetto di tipo List di Category
     */
    private List<Category> readCategories(JSONArray jsonCategories) {
        List<Category> categories = new ArrayList<>();
        for (Object category : jsonCategories) {
            categories.add(readCategory((JSONObject) category));
        }
        return categories;
    }

    /**
     * Preso un oggetto di tipo MovementType, restituisce un oggetto di tipo JSONObject
     * che rappresenta il MovementType
     *
     * @param movementType il MovementType da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta il MovementType inseirito
     * come parametro
     */
    private JSONObject writeMovementType(MovementType movementType) {
        JSONObject jsonMovementType = new JSONObject();
        jsonMovementType.put("Type", movementType.toString());
        return jsonMovementType;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta un MovementType ,
     * restituisce il MovementType rappresentato dal JSONObject
     *
     * @param jsonMovementType l'oggetto JSONObject che rappresenta il MovementType
     * @return un oggetto di tipo MovementType
     */
    private MovementType readMovementType(JSONObject jsonMovementType) {
        return MovementType.valueOf(jsonMovementType.getString("Type"));
    }

    /**
     * Preso un oggetto di tipo Movement, restituisce un oggetto di tipo JSONObject
     * che rappresenta il Movement
     *
     * @param movement il Movement da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta il Movement inseirito
     * come parametro
     */
    private JSONObject writeMovement(Movement movement) {
        JSONObject jsonMovement = new JSONObject();
        jsonMovement.put("Description", movement.getDescription());
        jsonMovement.put("Movement", movement.getMovement());
        jsonMovement.put("MovementType", writeMovementType(movement.getMovementType()));
        jsonMovement.put("Account", movement.getAccount().getID());
        jsonMovement.put("ID", movement.getID());
        int i = 0;
        for (Category category : movement.getCategory()) {
            jsonMovement.put("Category " + i++, category.getName());
        }
        return jsonMovement;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta un Movement e un
     * oggetto di tipo Ledger, restituisce il Movement rappresentato dal JSONObject
     *
     * @param jsonMovement l'oggetto JSONObject che rappresenta il Movement
     * @param ledger       il Ledger da cui prendere le Category da associare al Movement
     * @return un oggetto di tipo Movement
     */
    private Movement readMovement(JSONObject jsonMovement, Ledger ledger) {
        int ID = jsonMovement.getInt("ID");
        String description = jsonMovement.getString("Description");
        double movement = jsonMovement.getDouble("Movement");
        Account account = null;
        try {
            account = ledger.getAccount(jsonMovement.getInt("Account"));
        } catch (AccountDoNotExist e) {
        }
        MovementType movementType = readMovementType((JSONObject) jsonMovement.get("MovementType"));
        int i = 0;
        ArrayList<Category> categories = new ArrayList<>();
        while (jsonMovement.toMap().get("Category " + i) != null) {
            categories.add(ledger.getCategory((String) jsonMovement.get("Category " + i++)));
        }
        return new BasicMovement(movement, description, movementType, categories, account, ID);
    }

    /**
     * Presa una List di Movement, restituisce un oggetto di tipo JSONArray
     * che rappresenta la list di Movement
     *
     * @param movements la List di Movement da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di Movement
     * inseirita come parametro
     */
    private JSONArray writeMovements(List<Movement> movements) {
        JSONArray jsonMovements = new JSONArray();
        for (Movement movement : movements) {
            jsonMovements.put(writeMovement(movement));
        }
        return jsonMovements;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di Movement e un
     * oggetto di tipo Ledger restituisce la List di Movement rappresentata dal JSONArray
     *
     * @param jsonMovements l'oggetto JSONArray che rappresenta la List di Movement
     * @param ledger        il Ledger da cui prendere gli oggetti Category da associare al Movement
     * @return un oggetto di tipo List di Movement
     */
    private List<Movement> readMovements(JSONArray jsonMovements, Ledger ledger) {
        List<Movement> movements = new ArrayList<>();
        for (Object movement : jsonMovements) {
            movements.add(readMovement((JSONObject) movement, ledger));
        }
        return movements;
    }

    /**
     * Preso un oggetto di tipo Transaction, restituisce un oggetto di tipo JSONObject
     * che rappresenta la Transaction.
     * All'interno dell'JSONObject vengono inoltre salvati i Movement che appartengono alla
     * Transaction passata come parametro
     *
     * @param transaction la Transaction da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta la Transaction inseirita
     * come parametro
     */
    private JSONObject writeTransaction(Transaction transaction) {
        JSONObject jsonTransaction = new JSONObject();
        jsonTransaction.put("Day", transaction.getDate().getDayOfMonth());
        jsonTransaction.put("Month", transaction.getDate().getMonth().getValue());
        jsonTransaction.put("Year", transaction.getDate().getYear());
        jsonTransaction.put("ID", transaction.getID());
        int i = 0;
        for (Category category : transaction.getCategories()) {
            jsonTransaction.put("Category " + i++, category.getName());
        }
        jsonTransaction.put("Movements", writeMovements(transaction.getMovements()));
        return jsonTransaction;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta una Transaction e un
     * oggetto di tipo Ledger, restituisce la Transactio rappresentato dal JSONObject.
     * Richiamando il metodo readMovements(), la Transaction riesce a ricostruirsi la sua List di Movement
     *
     * @param jsonTransaction l'oggetto JSONObject che rappresenta la Transaction
     * @param ledger          il Ledger da cui prendere le Category da associare alla Transaction
     * @return un oggetto di tipo Transaction
     */
    private Transaction readTransaction(JSONObject jsonTransaction, Ledger ledger) {
        int ID = jsonTransaction.getInt("ID");
        LocalDate date = LocalDate.of(jsonTransaction.getInt("Year"),
                jsonTransaction.getInt("Month"), jsonTransaction.getInt("Day"));
        List<Movement> movements = new ArrayList<>();
        movements.addAll(readMovements((JSONArray) jsonTransaction.get("Movements"), ledger));
        int i = 0;
        List<Category> categories = new ArrayList<>();
        while (jsonTransaction.toMap().get("Category " + i) != null) {
            categories.add(ledger.getCategory((String) jsonTransaction.get("Category " + i++)));

        }
        return new BasicTransaction(movements, categories, date, ID);
    }

    /**
     * Presa una List di Transaction, restituisce un oggetto di tipo JSONArray
     * che rappresenta la list di Transaction
     *
     * @param transactions la List di Transaction da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di Transaction
     * inseirita come parametro
     */
    private JSONArray writeTransactions(List<Transaction> transactions) {
        JSONArray jsonTransactions = new JSONArray();
        for (Transaction transaction : transactions) {
            jsonTransactions.put(writeTransaction(transaction));
        }
        return jsonTransactions;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di Tranactions e un
     * oggetto di tipo Ledger restituisce la List di Transactions rappresentata dal JSONArray
     *
     * @param jsonTransactions l'oggetto JSONArray che rappresenta la List di Transaction
     * @param ledger           il Ledger da cui prendere gli oggetti Category da associare alle Transaction
     * @return un oggetto di tipo List di Transaction
     */
    private List<Transaction> readTransactions(JSONArray jsonTransactions, Ledger ledger) {
        List<Transaction> transactions = new ArrayList<>();
        for (Object jsonTransaction : jsonTransactions) {
            transactions.add(readTransaction((JSONObject) jsonTransaction, ledger));
        }
        return transactions;
    }

    /**
     * Preso un oggetto di tipo ScheduledTransaction, restituisce un oggetto di tipo JSONObject
     * che rappresenta la ScheduledTransaction.
     *
     * @param scheduledTransaction la ScheduledTransaction da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta la ScheduledTransaction inseirita
     * come parametro
     */
    private JSONObject writeScheduledTransaction(ScheduledTransaction scheduledTransaction) {
        JSONObject jsonTransaction = new JSONObject();
        jsonTransaction.put("Transaction", writeTransaction(scheduledTransaction.getTransaction()));
        return jsonTransaction;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta una ScheduledTransaction e un
     * oggetto di tipo Ledger, restituisce la ScheduledTransactio rappresentato dal JSONObject.
     *
     * @param jsonTransaction l'oggetto JSONObject che rappresenta la ScheduledTransaction
     * @param ledger          il Ledger da cui prendere le Category da associare alla ScheduledTransaction
     * @return un oggetto di tipo ScheduledTransaction
     */
    private ScheduledTransaction readScheduledTransaction(JSONObject jsonTransaction, Ledger ledger) {
        Transaction transaction = readTransaction((JSONObject) jsonTransaction.get("Transaction"), ledger);
        return new BasicScheduledTransaction(transaction);
    }

    /**
     * Presa una List di ScheduledTransaction, restituisce un oggetto di tipo JSONArray
     * che rappresenta la list di ScheduledTransaction
     *
     * @param scheduledTransactions la List di ScheduledTransaction da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di ScheduledTransaction
     * inseirita come parametro
     */
    private JSONArray writeScheduledTransactions(List<ScheduledTransaction> scheduledTransactions) {
        JSONArray jsonTransactions = new JSONArray();
        for (ScheduledTransaction scheduledTransaction : scheduledTransactions) {
            jsonTransactions.put(writeScheduledTransaction(scheduledTransaction));
        }
        return jsonTransactions;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di ScheduledTranactions e un
     * oggetto di tipo Ledger restituisce la List di ScheduledTransactions rappresentata dal JSONArray
     *
     * @param jsonTransactions l'oggetto JSONArray che rappresenta la List di ScheduledTransaction
     * @param ledger           il Ledger da cui prendere gli oggetti Category da associare alle ScheduledTransaction
     * @return un oggetto di tipo List di ScheduledTransaction
     */
    private List<ScheduledTransaction> readScheduledTransactions(JSONArray jsonTransactions, Ledger ledger) {
        List<ScheduledTransaction> scheduledTransactions = new ArrayList<>();
        for (Object jsonTransaction : jsonTransactions) {
            scheduledTransactions.add(readScheduledTransaction((JSONObject) jsonTransaction, ledger));
        }
        return scheduledTransactions;
    }

    /**
     * Preso un oggetto di tipo AccountType, restituisce un oggetto di tipo JSONObject
     * che rappresenta l'AccountType
     *
     * @param accountType l'AccountType da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta l'AccountType inseirito
     * come parametro
     */
    private JSONObject writeAccountType(AccountType accountType) {
        JSONObject jsonAccountType = new JSONObject();
        jsonAccountType.put("Type", accountType.toString());
        return jsonAccountType;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta un AccountType ,
     * restituisce l'AccounttType rappresentato dal JSONObject
     *
     * @param jsonAccountType l'oggetto JSONObject che rappresenta l'AccountType
     * @return un oggetto di tipo AccountType
     */
    private AccountType readAccountType(JSONObject jsonAccountType) {
        return AccountType.valueOf(jsonAccountType.getString("Type"));
    }

    /**
     * Preso un oggetto di tipo Account, restituisce un oggetto di tipo JSONObject
     * che rappresenta l'Account
     *
     * @param account l'Account da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta l'Account inseirito
     * come parametro
     */
    private JSONObject writeAccount(Account account) {
        JSONObject jsonAccount = new JSONObject();
        jsonAccount.put("ID", account.getID());
        jsonAccount.put("Type", writeAccountType(account.getAccountType()));
        jsonAccount.put("InitialBalance", account.getInitialBalance());
        jsonAccount.put("Name", account.getName());
        jsonAccount.put("Description", account.getDescription());
        int i = 0;
        for (Movement movement : account.getMovement()) {
            jsonAccount.put("Movement " + i++, movement.getID());
        }
        return jsonAccount;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta un'Account,
     * restituisce l'Account rappresentato dal JSONObject.
     *
     * @param jsonAccount l'oggetto JSONObject che rappresenta l'Account
     * @return un oggetto di tipo Account
     */
    private Account readAccount(JSONObject jsonAccount) {
        int ID = jsonAccount.getInt("ID");
        String name = jsonAccount.getString("Name");
        String description = jsonAccount.getString("Description");
        double initialBalance = jsonAccount.getDouble("InitialBalance");
        AccountType type = readAccountType(jsonAccount.getJSONObject("Type"));
        return new BasicAccount(name, description, type, initialBalance, ID);
    }

    /**
     * Presa una List di Account, restituisce un oggetto di tipo JSONArray
     * che rappresenta la list di Account
     *
     * @param accounts la List di Account da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di Account
     * inseirita come parametro
     */
    private JSONArray writeAccounts(List<Account> accounts) {
        JSONArray jsonAccounts = new JSONArray();
        for (Account account : accounts) {
            jsonAccounts.put(writeAccount(account));
        }
        return jsonAccounts;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di Account
     * restituisce la List di Account rappresentata dal JSONArray
     *
     * @param jsonAccounts l'oggetto JSONArray che rappresenta la List di Account
     * @return un oggetto di tipo List di Account
     */
    private List<Account> readAccounts(JSONArray jsonAccounts) {
        List<Account> accounts = new ArrayList<>();
        for (Object account : jsonAccounts) {
            accounts.add(readAccount((JSONObject) account));
        }
        return accounts;
    }

    /**
     * Preso un oggetto di tipo Budget, restituisce un oggetto di tipo JSONObject
     * che rappresenta il Budget
     *
     * @param budget il Budgte da trasformare in JSONObject
     * @return un oggetto di tipo JSONObject che rappresenta il Budget inseirito
     * come parametro
     */
    private JSONObject writeBudget(Budget budget) {
        JSONObject jsonBudget = new JSONObject();
        jsonBudget.put("Category", budget.getCategory().getName());
        jsonBudget.put("Amount", budget.getAmount());
        jsonBudget.put("ID", budget.getID());
        return jsonBudget;
    }

    /**
     * Preso un oggetto di tipo JSONObject che rappresenta un Budget e un oggetto
     * di tipo Ledger da cui prendere la Category da associare al Budget e
     * restituisce il Budget rappresentato dal JSONObject.
     *
     * @param jsonBudget l'oggetto JSONObject che rappresenta il Budget
     * @param ledger     l'oggetto Ledger da cui prendere l'oggetto Category da
     *                   associare al Budget
     * @return un oggetto di tipo Budget
     */
    private Budget readBudget(JSONObject jsonBudget, Ledger ledger) {
        int ID = jsonBudget.getInt("ID");
        double amount = jsonBudget.getDouble("Amount");
        Category category = null;

        category = ledger.getCategory(jsonBudget.getString("Category"));

        return new BasicBudget(category, amount, ID);
    }

    /**
     * Presa una List di Budget, restituisce un oggetto di tipo JSONArray
     * che rappresenta la list di Budget
     *
     * @param budgets la List di Budget da trasformare in JSONArray
     * @return un oggetto di tipo JSONArray che rappresenta la List di Budget
     * inseirita come parametro
     */
    private JSONArray writeBudgets(List<Budget> budgets) {
        JSONArray jsonBudgets = new JSONArray();
        for (Budget budget : budgets) {
            jsonBudgets.put(writeBudget(budget));
        }
        return jsonBudgets;
    }

    /**
     * Preso un oggetto di tipo JSONArray che rappresenta una List di Budget e un oggetto di tipo
     * Ledger da cui si prenderano le Category da associare ai singoli Budgets,
     * restituisce la List di Budget rappresentata dal JSONArray
     *
     * @param jsonBudgets l'oggetto JSONArray che rappresenta la List di Budget
     * @return un oggetto di tipo List di Budget
     */
    private List<Budget> readBudgets(JSONArray jsonBudgets, Ledger ledger) {
        List<Budget> budgets = new ArrayList<>();
        for (Object jsonBudget : jsonBudgets) {
            budgets.add(readBudget((JSONObject) jsonBudget, ledger));
        }
        return budgets;
    }

    @Override
    public void writeLedger(Ledger ledger, String path) throws IOException {
        FileWriter file = new FileWriter(path);
        JSONArray jsonLedger = new JSONArray();
        jsonLedger.put(writeAccounts(ledger.getAccounts()));
        jsonLedger.put(writeCategories(ledger.getCategories()));
        jsonLedger.put(writeTransactions(ledger.getTransactions()));
        jsonLedger.put((writeBudgets(ledger.getBudgets())));
        jsonLedger.put(writeScheduledTransactions(ledger.getScheduledTransactions()));
        file.write(jsonLedger.toString());
        file.close();

    }

    @Override
    public Ledger readLedger(String path) throws FileNotFoundException {
        File file = new File(path);
        JSONTokener tokener = new JSONTokener(new FileInputStream(file));
        JSONArray jsonLedger = new JSONArray(tokener);
        List<Account> accounts = new ArrayList<>();
        accounts.addAll(readAccounts((JSONArray) jsonLedger.get(0)));
        List<Category> categories = new ArrayList<>();
        categories.addAll(readCategories((JSONArray) jsonLedger.get(1)));
        Ledger ledger = new BasicLedger(accounts, categories);
        readTransactions((JSONArray) jsonLedger.get(2), ledger).forEach(i -> ledger.addTransaction(i));
        readBudgets((JSONArray) jsonLedger.get(3), ledger).forEach(i -> ledger.addBudget(i));
        readScheduledTransactions((JSONArray) jsonLedger.get(4), ledger).forEach(i -> ledger.addScheduledTransaction(i));
        return ledger;

    }

}
