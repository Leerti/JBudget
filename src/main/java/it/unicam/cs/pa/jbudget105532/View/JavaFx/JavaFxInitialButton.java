package it.unicam.cs.pa.jbudget105532.View.JavaFx;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaFxInitialButton {


    @FXML
    private Button home;
    @FXML
    private Button transaction;
    @FXML
    private Button budget;
    @FXML
    private Button category;
    @FXML
    private Button account;
    @FXML
    private Button exit;

    protected int accountID=-1;
    protected String dateForAccount = "";
    protected String categoryNameForAccount = "";
    protected String categoryNameForCategory = "";
    protected int budgetID=-1;
    protected int transactionID=-1;
    protected String dateForTransaction="";
    protected String categoryNameForTransaction="";
    protected int indexSelectedTransaction=-1;
    protected  int typeTransaction=-1;

    protected BasicController controller;
    protected Stage stage;

    @FXML
    public void goHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Scene home = new Scene(loader.load());
        stage.setScene(home);
        JavaFxHome homeFX = loader.<JavaFxHome>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        homeFX.set(info);
    }
    @FXML
    public void changeSceneTransaction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transaction.fxml"));
        Scene transactions = new Scene(loader.load());
        stage.setScene(transactions);
        JavaFxTransaction transactionsFX = loader.<JavaFxTransaction>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Transaction",transactionID);
        info.put("Date",dateForTransaction);
        info.put("Category",categoryNameForTransaction);
        info.put("Index selected",indexSelectedTransaction);
        info.put("Type",typeTransaction);
        transactionsFX.set(info);
    }

    @FXML
    public void changeSceneBudget() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Budget.fxml"));
        Scene budget = new Scene(loader.load());
        stage.setScene(budget);
        JavaFxBudget budgetFX = loader.<JavaFxBudget>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Budget",budgetID);
        budgetFX.set(info);

    }

    @FXML
    public void changeSceneCategory() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category.fxml"));
        Scene category = new Scene(loader.load());
        stage.setScene(category);
        JavaFxCategory categoryFX = loader.<JavaFxCategory>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Category", categoryNameForCategory);
        categoryFX.set(info);

    }

    @FXML
    public void changeSceneAccount() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Account.fxml"));
        Scene accounts = new Scene(loader.load());
        stage.setScene(accounts);
        JavaFxAccount accountFX = loader.<JavaFxAccount>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        info.put("Last Account", accountID);
        info.put("Date", dateForAccount);
        info.put("Category", categoryNameForAccount);
        accountFX.set(info);
    }

    public void save() {
        controller.save();
    }

}
