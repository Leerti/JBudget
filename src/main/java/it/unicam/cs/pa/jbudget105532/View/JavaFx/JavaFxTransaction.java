package it.unicam.cs.pa.jbudget105532.View.JavaFx;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;
import it.unicam.cs.pa.jbudget105532.Exception.AccountDoNotExist;
import it.unicam.cs.pa.jbudget105532.Model.Account;
import it.unicam.cs.pa.jbudget105532.Model.Category;
import it.unicam.cs.pa.jbudget105532.Model.Movement;
import it.unicam.cs.pa.jbudget105532.Model.Transaction;
import it.unicam.cs.pa.jbudget105532.View.Printer.*;

import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;

public class JavaFxTransaction extends JavaFxInitialButton {


    @FXML
    private ComboBox<String> typeTransaction;

    @FXML
    private ListView<String> transactions;

    @FXML
    private ListView<String> movements;

    @FXML
    private Button showAllTransactions;

    @FXML
    private Button removeMovement;

    @FXML
    private Button addMovement;

    @FXML
    private Label errorTransaction;

    @FXML
    private Label errorMovement;

    @FXML
    private Label errorType;

    @FXML
    private Button newTransaction;

    @FXML
    private Button removeTransaction;

    @FXML
    private Button modifyTransaction;

    @FXML
    private Button filterByCategory;

    @FXML
    private Button filterByDate;

    @FXML
    private Label filteredBy;

    @FXML
    private CheckComboBox<String> category_newMovement;

    @FXML
    private ChoiceBox<String> type_newMovement;

    @FXML
    private ChoiceBox<String> account_newMovement;

    @FXML
    private TextArea description_newMovement;

    @FXML
    private TextArea amount_newMovement;

    @FXML
    private Label errorAmount_newMovement;

    @FXML
    private Label errorType_newMovement;

    @FXML
    private Label errorAccount_newMovement;

    @FXML
    private Button create_newMovement;

    @FXML
    private Button filter_filterForCategory;

    @FXML
    private ComboBox<String> category_filterForCategory;

    @FXML
    private Label errorCategory_filterForCategory;

    @FXML
    private DatePicker date_filterForDate;

    @FXML
    private Label errorDate_filterForDate;

    @FXML
    public Button filter_filterForDate;

    @FXML
    private DatePicker data_modifyTransaction;

    @FXML
    private CheckComboBox<String> categories_modifyTransaction;

    @FXML
    private Button modifyTransaction_modifyTransaction;

    @FXML
    private Label errorDate_modifyTransaction;

    @FXML
    private CheckComboBox<String> categories_newTransaction;

    @FXML
    private Label errorDate_newTransaction;

    @FXML
    private DatePicker date_newTransaction;

    @FXML
    private RadioButton isScheduled_newTransaction;


    public void set(Map<String, Object> info) {
        super.stage = (Stage) info.get("Stage");
        super.controller = (BasicController) info.get("Controller");
        super.transactionID = (int) info.get("Transaction");
        super.dateForTransaction = (String) info.get("Date");
        super.categoryNameForTransaction = (String) info.get("Category");
        super.indexSelectedTransaction = (int) info.get("Index selected");
        super.typeTransaction = (int) info.get("Type");
        if (showAllTransactions != null) {
            setHomeTransaction();
        } else if (type_newMovement != null) {
            setNewMovement();
        } else if (category_filterForCategory != null) {
            setFilterByCategory();
        } else if (data_modifyTransaction != null) {
            setModifyTransaction();
        } else if (date_newTransaction != null)
            setNewTransaction();
    }

    private void setHomeTransaction() {
        typeTransaction.setItems(FXCollections.observableArrayList("Scheduled", "Normal"));
        setTransactionsType();
        if (super.transactionID!=-1) {
            selectTransaction();
        }if(typeTransaction.getSelectionModel().getSelectedItem()==null){
            typeTransaction.getSelectionModel().select(super.typeTransaction);
        }
    }

    private void setNewMovement() {
        type_newMovement.setItems(FXCollections.observableArrayList("Income", "Expense"));
        category_newMovement.getItems().addAll(new BasicCategoriesPrinter().stringOf(controller.getCategories()));
        account_newMovement.setItems(FXCollections.observableArrayList(getAccountsRepresentation()));
    }

    private void setFilterByCategory() {
        category_filterForCategory.setItems(FXCollections.observableArrayList(
                new BasicCategoriesPrinter().stringOf(controller.getCategories())));
    }

    private void setModifyTransaction() {
        data_modifyTransaction.setValue(controller.getTransactionDate(transactionID));
        categories_modifyTransaction.getItems().addAll(new BasicCategoriesPrinter().stringOf(controller.getCategories()));
        getIndexOfCategoryOfTransaction().forEach(i -> categories_modifyTransaction.getCheckModel().checkIndices(i));
    }
    private List<Integer> getIndexOfCategoryOfTransaction(){
        List<Integer> index = new ArrayList<>();
        Transaction transactions = controller.selectTransaction(transactionID);
        for (int i = 0; i < controller.getCategories().size(); i++) {
            if (transactions.getCategories().contains(controller.getCategories().get(i))) {
                index.add(i);
            }
        }
        return index;}

    private void setNewTransaction() {
        categories_newTransaction.getItems().addAll(new BasicCategoriesPrinter().stringOf(controller.getCategories()));
    }

    public List<String> getTransactionsRepresentation(List<Transaction> transactions) {
        CategoryPrinter cprinter = new BasicCategoryPrinter();
        TransactionPrinter printer = new BasicTransactionPrinter(cprinter);
        return new BasicTransactionsPrinter(cprinter, printer).StringOf(transactions);
    }

    public void setTransactionsType() {
        if (super.typeTransaction == -1 && typeTransaction.getSelectionModel().getSelectedItem() == null) {
        } else {
            if (typeTransaction.getSelectionModel().getSelectedItem() != null) {
                super.typeTransaction = typeTransaction.getSelectionModel().getSelectedIndex();
            } else {
                typeTransaction.getSelectionModel().select(super.typeTransaction);
            }
            if (!super.dateForTransaction.isBlank()) {
                showFilterTransactionByDate();
            } else if (!super.categoryNameForTransaction.isBlank()) {
                showFilterTransactionByCategory();
            } else {
                showAllTransactions();
            }
        }

    }


    public void selectTransaction() {
        if (transactions.getSelectionModel().getSelectedItem() == null && indexSelectedTransaction == -1) {
        } else {
            if (transactions.getSelectionModel().getSelectedItem() != null) {
                String ID = transactions.getSelectionModel().getSelectedItem();
                ID = ID.substring(4, ID.indexOf("\n"));
                super.transactionID = Integer.parseInt(ID);
                controller.selectTransaction(Integer.parseInt(ID));
                indexSelectedTransaction = transactions.getSelectionModel().getSelectedIndex();
            } else {
                transactions.getSelectionModel().select(indexSelectedTransaction);
            }
            controller.selectTransaction(transactionID);
            CategoryPrinter cprinter = new BasicCategoryPrinter();
            MovementPrinter printer = new BasicMovementPrinter(cprinter);
            movements.setItems(FXCollections.observableArrayList(new BasicMovementsPrinter(cprinter, printer)
                    .StringOf(controller.getTransactionMovements(super.transactionID))));
            addMovement.setVisible(true);
            removeMovement.setVisible(true);
        }
    }


    public List<String> getAccountsRepresentation() {
        List<String> accountRepresentation = new ArrayList<>();
        List<Account> accounts = controller.getAccounts();
        for (Account account : accounts) {
            accountRepresentation.add(account.getID() + "." + account.getName());
        }
        return accountRepresentation;
    }

    public void addMovement() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewMovement.fxml"));
        Scene home = new Scene(loader.load());
        stage.setScene(home);
        JavaFxTransaction newMovementFX = loader.<JavaFxTransaction>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Date", "");
        info.put("Category", "");
        info.put("Transaction", transactionID);
        info.put("Index selected", indexSelectedTransaction);
        info.put("Type", super.typeTransaction);
        newMovementFX.set(info);
    }

    public void removeMovement() {
        errorType.setText("");
        errorTransaction.setText("");
        if (movements.getSelectionModel().getSelectedItem() == null) {
            errorMovement.setText("Please, select a movement");
        } else {
            String movementId = movements.getSelectionModel().getSelectedItem();
            movementId = movementId.substring(movementId.indexOf(":") + 1, movementId.indexOf("\n")).trim();
            controller.removeMovement(Integer.parseInt(movementId));
            CategoryPrinter cprinter = new BasicCategoryPrinter();
            MovementPrinter printer = new BasicMovementPrinter(cprinter);
            movements.setItems(FXCollections.observableArrayList(new BasicMovementsPrinter(cprinter, printer).StringOf(controller.getTransactionMovements(transactionID))));
            errorMovement.setText("");
        }
    }

    public void newMovement_newMovement() throws IOException, AccountDoNotExist {
        HashMap<String, Object> info = new HashMap<>();

        info.put("Description", description_newMovement.getText());
        info.put("Type", type_newMovement.getValue());
        if (category_newMovement.getCheckModel().getCheckedIndices() != null) {
            List<Integer> categoriesName = category_newMovement.getCheckModel().getCheckedIndices();
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < controller.getCategories().size(); i++) {
                if (categoriesName.contains(i)) categories.add(controller.getCategories().get(i));
            }
            info.put("Categories", categories);
        } else {
            info.put("Categories", new ArrayList<String>());
        }
        try {
            if(!amount_newMovement.getText().isBlank()) {
                info.put("Amount", Double.parseDouble(amount_newMovement.getText()));
            }else errorAmount_newMovement.setText("Please, insert something");
            String account = account_newMovement.getValue().substring(0, account_newMovement.getValue().indexOf("."));
            int idAccount=Integer.parseInt(account);
            info.put("Account", idAccount);
            info.put("Transaction", transactionID);
            controller.addMovement(info);
            changeSceneTransaction();
        } catch (NumberFormatException e) {
            errorAmount_newMovement.setText("Please,insert a number");
            errorAccount_newMovement.setText("");
            errorType_newMovement.setText("");
        } catch (NullPointerException e) {
            if (type_newMovement.getValue() == null) {
                errorType_newMovement.setText("Please, chose a type");
                errorAccount_newMovement.setText("");
                errorAmount_newMovement.setText("");
            } else if (account_newMovement.getValue() == null) {
                errorAccount_newMovement.setText("Please, chose an account");
                errorType_newMovement.setText("");
                errorAmount_newMovement.setText("");

            }
        }
    }

    public void filterTransactionForDate() throws IOException {
        if (super.typeTransaction == -1) {
            errorType.setText("Please,chose a type \nof Transaction");
            errorMovement.setText("");
            errorTransaction.setText("");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetDateForTransaction.fxml"));
            Scene home = new Scene(loader.load());
            stage.setScene(home);
            JavaFxTransaction getDateFX = loader.<JavaFxTransaction>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Transaction", -1);
            info.put("Date", dateForTransaction);
            info.put("Category", "");
            info.put("Index selected", indexSelectedTransaction);
            info.put("Type", super.typeTransaction);
            getDateFX.set(info);
        }
    }

    public void filter_filterForDate() throws IOException {
        try {
            dateForTransaction = date_filterForDate.getValue().toString();
            changeSceneTransaction();
        } catch (NullPointerException e) {
            errorDate_filterForDate.setText("Please,chose a date");
        }
    }


    public void showFilterTransactionByDate() {
        if (super.typeTransaction == 1) {
            transactions.setItems(FXCollections.observableArrayList(getTransactionsRepresentation(
                    controller.filterTransactionForDate(LocalDate.parse(dateForTransaction, DateTimeFormatter.ISO_DATE)))));
        } else {
            transactions.setItems(FXCollections.observableArrayList(getTransactionsRepresentation(
                    controller.filterScheduledTransactionForDate(LocalDate.parse(dateForTransaction, DateTimeFormatter.ISO_DATE)))));
        }
        filteredBy.setText("FILTERED BY DATE (" + dateForTransaction + ")");
        movements.setItems(FXCollections.observableArrayList());
    }


    public void filterTransactionByCategory() throws IOException {
        errorMovement.setText("");
        errorType.setText("");
        errorTransaction.setText("");
        if (super.typeTransaction == -1) {
            errorType.setText("Please,chose a type \nof Transaction");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetCategoryForTransaction.fxml"));
            Scene home = new Scene(loader.load());
            stage.setScene(home);
            JavaFxTransaction getCategoryFX = loader.<JavaFxTransaction>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Transaction", -1);
            info.put("Date", "");
            info.put("Category", super.categoryNameForTransaction);
            info.put("Index selected", indexSelectedTransaction);
            info.put("Type", super.typeTransaction);
            getCategoryFX.set(info);
        }
    }

    public void filter_filterForCategory() throws IOException {


        categoryNameForTransaction = category_filterForCategory.getSelectionModel().getSelectedItem();
        if (categoryNameForTransaction == null) errorCategory_filterForCategory.setText("Please, chose a category");
        else {
            changeSceneTransaction();
        }
    }

    public void showFilterTransactionByCategory() {

        if (super.typeTransaction == 1) transactions.setItems(FXCollections.observableArrayList(
                getTransactionsRepresentation(controller.filterTransactionForCategory(categoryNameForTransaction))));
        else {
            transactions.setItems(FXCollections.observableArrayList(
                    getTransactionsRepresentation(controller.filterScheduledTransactionForCategory(categoryNameForTransaction))));
        }
        filteredBy.setText("FILTERED BY CATEGORY (" + categoryNameForTransaction + ")");
        movements.setItems(FXCollections.observableArrayList());

    }

    public void showAllTransactions() {
        errorMovement.setText("");
        errorType.setText("");
        errorTransaction.setText("");
        filteredBy.setText("");
        if (super.typeTransaction == 0) {
            transactions.setItems(FXCollections.observableArrayList(getTransactionsRepresentation(controller.getScheduledTransactions())));
        } else if (super.typeTransaction == 1) {
            transactions.setItems(FXCollections.observableArrayList(getTransactionsRepresentation(controller.getTransactions())));
        } else errorType.setText("Please,chose a type \nof Transaction");
        movements.setItems(FXCollections.observableArrayList());
        super.dateForTransaction = "";
        super.categoryNameForTransaction = "";

    }


    public void removeTransaction() {
        errorMovement.setText("");
        errorType.setText("");
        errorTransaction.setText("");
        if(transactionID==-1 )errorTransaction.setText("Please, chose a transaction");
      else {
            controller.removeTransaction(transactionID);
            showAllTransactions();
            errorTransaction.setText("");
            transactionID = -1;
        }
    }

    public void modifyTransaction() throws IOException {
        if (transactionID==-1) {
            errorTransaction.setText("Please, chose a transaction");
            errorType.setText("");
            errorMovement.setText("");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyTransaction.fxml"));
            Scene modifyTransaction = new Scene(loader.load());
            stage.setScene(modifyTransaction);
            JavaFxTransaction modifyFX = loader.<JavaFxTransaction>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Transaction", transactionID);
            info.put("Date", "");
            info.put("Category", "");
            info.put("Index selected", indexSelectedTransaction);
            info.put("Type", super.typeTransaction);
            modifyFX.set(info);
        }
    }

    public void modifyTransaction_modifyTransaction() throws IOException {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Transaction",transactionID);
        info.put("Date", data_modifyTransaction.getValue());
        List<Category> categories = new ArrayList<>();
        if (categories_modifyTransaction.getCheckModel().getCheckedIndices() != null) {
            for (int i = 0; i < controller.getCategories().size(); i++) {
                if (categories_modifyTransaction.getCheckModel().getCheckedIndices().contains(i))
                    categories.add(controller.getCategories().get(i));
            }
        }
        info.put("Categories", categories);
        controller.modifyTransaction(info);
        changeSceneTransaction();
    }


    public void newTransaction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewTransaction.fxml"));
        Scene newTransaction = new Scene(loader.load());
        stage.setScene(newTransaction);
        JavaFxTransaction newTransactionFX = loader.<JavaFxTransaction>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Transaction", -1);
        info.put("Date", "");
        info.put("Category", "");
        info.put("Index selected", indexSelectedTransaction);
        info.put("Type", super.typeTransaction);
        newTransactionFX.set(info);
    }

    public void newTransaction_newTransaction() throws IOException {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Date", date_newTransaction.getValue());
        if (categories_newTransaction.getCheckModel().getCheckedIndices() != null) {
            List<Integer> categoriesName = categories_newTransaction.getCheckModel().getCheckedIndices();
            List<Category> categories = new ArrayList<>();
            for (int i = 0; i < controller.getCategories().size(); i++) {
                if (categoriesName.contains(i)) categories.add(controller.getCategories().get(i));
            }
            info.put("Categories", categories);
        } else {
            info.put("Categories", new ArrayList<>());
        }
        String type;
        if (isScheduled_newTransaction.isSelected() == true)
            type = "Scheduled";
        else {
            type = "Normal";
        }
        info.put("Type", type);
        info.put("Movements",new ArrayList<Movement>());
        try {
            controller.addTransaction(info);
            changeSceneTransaction();
        } catch (NullPointerException e) {
            errorDate_newTransaction.setText("Please, chose a date");
        }
    }

    public void checkType() {
        if (date_newTransaction.getValue().isAfter(LocalDate.now())) {
            isScheduled_newTransaction.setVisible(true);
        } else
            isScheduled_newTransaction.setVisible(false);
    }
}
