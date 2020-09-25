package it.unicam.cs.pa.jbudget105532.View.JavaFx;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;
import it.unicam.cs.pa.jbudget105532.Exception.StringBlankException;
import it.unicam.cs.pa.jbudget105532.Model.Account;
import it.unicam.cs.pa.jbudget105532.Model.Category;
import it.unicam.cs.pa.jbudget105532.Model.Movement;
import it.unicam.cs.pa.jbudget105532.View.Printer.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFxAccount extends JavaFxInitialButton {

    @FXML
    private ComboBox<String> accounts;

    @FXML
    private Button newAccount;

    @FXML
    private Button modifyAccount;

    @FXML
    private Label accountInformation;

    @FXML
    private Label errorAccount;

    @FXML
    private ListView<String> movements;

    @FXML
    private Label filteredBy;

    @FXML
    private Button filterForDate;

    @FXML
    private Button filterForCategory;

    @FXML
    private DatePicker date_filterForDate;

    @FXML
    private Button filter_filterForDate;

    @FXML
    private Label errorDate_filterForDate;

    @FXML
    private Button back_filterForDateAndFilterForCategory;

    @FXML
    private ComboBox<String> category_filterForCategory;

    @FXML
    private Label errorCategory_filterForCategory;

    @FXML
    private Button filter_filterForCategory;

    @FXML
    private TextArea name_modify;

    @FXML
    private TextArea description_modify;

    @FXML
    private TextArea initialBalance_modify;

    @FXML
    private Button modify_modify;

    @FXML
    private Button back_modify;

    @FXML
    private Label errorName_modify;

    @FXML
    private Label errorBalance_modify;

    @FXML
    private ComboBox<String> typeAccount_new;

    @FXML
    private TextArea name_new;

    @FXML
    private TextArea description_new;

    @FXML
    private TextArea initialBalance_new;

    @FXML
    private Label errorBalance_new;

    @FXML
    private Label errorName_new;

    @FXML
    private Label errorType_new;

    @FXML
    private Button back_new;

    @FXML
    private Button create_new;


    public void set(Map<String, Object> info) {
        super.stage = (Stage) info.get("Stage");
        super.controller = (BasicController) info.get("Controller");
        super.accountID = (int) info.get("Last Account");
        super.dateForAccount = (String) info.get("Date");
        super.categoryNameForAccount = (String) info.get("Category");
        if (accounts != null) {
            setHomeAccount();
        } else if (category_filterForCategory != null) {
            setFilterForCategory();
        } else if (name_modify != null) {
            setModifyAccount();
        } else if (typeAccount_new != null) {
            setNewAccount();
        }
    }
    @FXML
    private void setHomeAccount() {
        accounts.setItems(FXCollections.observableArrayList(getAccountsRepresentation()));
        if (accountID != -1) {
            accounts.getSelectionModel().select(accountID);
            selectAccount();
        }
    }

    @FXML
    private void setModifyAccount() {
        name_modify.setText(controller.getAccountName(accountID));
        description_modify.setText(controller.getAccountDescription(accountID));
        initialBalance_modify.setText(controller.getAccountInitialBalance(accountID));
    }

    @FXML
    private void setFilterForCategory() {
        category_filterForCategory.setItems(FXCollections.observableArrayList(getCategoriesRepresentation()));
    }

    @FXML
    private void setNewAccount() {
        typeAccount_new.getItems().addAll("Asset", "Liability");
    }


    public List<String> getAccountsRepresentation() {
        List<String> accountRepresentation = new ArrayList<>();
        List<Account> accounts = controller.getAccounts();
        for (Account account : accounts) {
            accountRepresentation.add(account.getID() + "." + account.getName());
        }
        return accountRepresentation;
    }

    public String getAccountRepresentation() {
        AccountPrinter printer = new BasicAccountPrinter();
        return printer.stringOf(controller.getAccount(accountID));
    }

    private List<String> getCategoriesRepresentation() {
        List<String> categoryRepresentation = new ArrayList<>();
        List<Category> categories = controller.getCategories();
        for (Category category : categories) {
            categoryRepresentation.add(category.getName());
        }
        return categoryRepresentation;
    }

    @FXML
    public void filterForDate() throws IOException {
        if (accountID == -1) {
            errorAccount.setText("Please, chose an account.");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetDateForAccount.fxml"));
            Scene getDate = new Scene(loader.load());
            super.stage.setScene(getDate);
            JavaFxAccount filterDateFX = loader.<JavaFxAccount>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Last Account", accountID);
            info.put("Date", dateForAccount);
            info.put("Category", "");
            filterDateFX.set(info);
        }
    }

    @FXML
    public void filterForCategory() throws IOException {
        if (accountID == -1) {
            errorAccount.setText("Please, chose an account.");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GetCategoryForAccount.fxml"));
            Scene getCategory = new Scene(loader.load());
            super.stage.setScene(getCategory);
            JavaFxAccount filterCategoryFX = loader.<JavaFxAccount>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Last Account", accountID);
            info.put("Category", categoryNameForAccount);
            info.put("Date", "");
            filterCategoryFX.set(info);
        }
    }


    @FXML
    public void selectAccount() {
        String account = accounts.getValue();
        String ID = account.substring(0, account.indexOf("."));
        accountID = Integer.parseInt(ID);
        errorAccount.setText("");
        accountInformation.setText(getAccountRepresentation());
        if (!super.dateForAccount.isBlank()) {
            filterMovementsByDate();
        } else if (!super.categoryNameForAccount.isBlank()) {
            filterMovementsByCategory();
        } else {
            showAllMovements();
        }
    }

    @FXML
    public void newAccount() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/NewAccount.fxml"));
        Scene home = new Scene(loader.load());
        stage.setScene(home);
        JavaFxAccount newAccountFX = loader.<JavaFxAccount>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Last Account", accountID);
        info.put("Category", "");
        info.put("Date", "");
        newAccountFX.set(info);
    }

    @FXML
    public void newAccount_new() throws IOException {
        HashMap<String, String> info = new HashMap<>();
        info.put("Type", typeAccount_new.getValue());
        info.put("Name", name_new.getText());
        info.put("Description", description_new.getText());
        info.put("InitialBalance", initialBalance_new.getText());
        try {

            super.accountID = controller.addAccount(info).getID();
            changeSceneAccount();
            errorBalance_new.setText("");
            errorName_new.setText("");
            errorType_new.setText("");
        } catch (NumberFormatException e) {
            errorBalance_new.setText("Please,insert a number.");
            errorName_new.setText("");
            errorType_new.setText("");
        } catch (StringBlankException e) {
            errorName_new.setText(e.getMessage());
            errorBalance_new.setText("");
            errorType_new.setText("");
        } catch (NullPointerException e) {
            errorType_new.setText("Please, chose a type.");
            errorBalance_new.setText("");
            errorName_new.setText("");
        }
    }

    @FXML
    public void modifyAccount() throws IOException {
        if (accountID == -1) {
            errorAccount.setText("Please, chose an account.");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyAccount.fxml"));
            Scene modify = new Scene(loader.load());
            stage.setScene(modify);
            JavaFxAccount modifyAccountFX = loader.<JavaFxAccount>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Last Account", accountID);
            info.put("Category", categoryNameForAccount);
            info.put("Date", dateForAccount);
            modifyAccountFX.set(info);
        }
    }

    @FXML
    public void modify_modify() throws IOException {
        HashMap<String, Object> info = new HashMap<>();
        info.put("Name", name_modify.getText());
        info.put("Description", description_modify.getText());
        info.put("InitialBalance", initialBalance_modify.getText());
        info.put("Account",accountID);
        try {
            controller.modifyAccount(info);
            changeSceneAccount();
        } catch (NumberFormatException e) {
            errorBalance_modify.setText("Please,insert a number");
            errorName_modify.setText("");
        } catch (StringBlankException e) {
            errorName_modify.setText(e.getMessage());
            errorBalance_modify.setText("");
        }
    }

    @FXML
    public void filter_filterForDate() throws IOException {
        try {
            super.dateForAccount = date_filterForDate.getValue().toString();
            changeSceneAccount();
        } catch (NullPointerException e) {
            errorDate_filterForDate.setText("Please, chose a date");
        }
    }

    @FXML
    public void filter_filterForCategory() throws IOException {
        super.categoryNameForAccount = category_filterForCategory.getValue();
        if (categoryNameForAccount == null) {
            errorCategory_filterForCategory.setText("Please, chose a category");
            categoryNameForAccount = "";
        } else
            changeSceneAccount();
    }


    @FXML
    public void filterMovementsByDate() {
        CategoryPrinter cprinter = new BasicCategoryPrinter();
        MovementPrinter printer = new BasicMovementPrinter(cprinter);
        List<Movement> filteredMovements = controller.filterMovementForData(
                LocalDate.parse(super.dateForAccount, DateTimeFormatter.ISO_DATE),accountID);
        movements.setItems(FXCollections.observableArrayList(
                new BasicMovementsPrinter(cprinter, printer).StringOf(filteredMovements)));
        filteredBy.setText("FILTERED BY DATE (" + super.dateForAccount + ")");
    }

    @FXML
    public void showAllMovements() {
        if (accountID == -1) {
            errorAccount.setText("Please, chose an account.");
        } else {
            CategoryPrinter cprinter = new BasicCategoryPrinter();
            MovementPrinter printer = new BasicMovementPrinter(cprinter);
            movements.setItems(FXCollections.observableArrayList(
                    new BasicMovementsPrinter(cprinter, printer).StringOf(controller.getAccount(accountID).getMovement())));
            filteredBy.setText("");
            super.dateForAccount = "";
            super.categoryNameForAccount = "";
        }
    }

    @FXML
    public void filterMovementsByCategory() {
        CategoryPrinter cprinter = new BasicCategoryPrinter();
        MovementPrinter printer = new BasicMovementPrinter(cprinter);
        List<Movement> filteredMovements = controller.filterMovementForCategory(categoryNameForAccount,accountID);
        movements.setItems(FXCollections.observableArrayList(new BasicMovementsPrinter(cprinter, printer).StringOf(filteredMovements)));
        filteredBy.setText("FILTERED BY CATEGORY (" + categoryNameForAccount + ")");
    }

}

