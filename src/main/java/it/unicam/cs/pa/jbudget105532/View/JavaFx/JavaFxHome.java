package it.unicam.cs.pa.jbudget105532.View.JavaFx;


import it.unicam.cs.pa.jbudget105532.Controller.BasicController;


import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Map;


public class JavaFxHome extends JavaFxInitialButton{

    @FXML
    private Label nAccounts;
    @FXML
    private Label nTransactions;
    @FXML
    private Label nCategories;
    @FXML
    private Label nBudgets;

    public void set(Map<String, Object> info) {
        super.stage = (Stage) info.get("Stage");
        super.controller = (BasicController) info.get("Controller");
        controller.checkScheduledTransaction();

        nAccounts.setText(String.valueOf(controller.getNumbersOfAccount()));
        nTransactions.setText(String.valueOf(controller.getNumbersOfTransactions()));
        nCategories.setText(String.valueOf(controller.getNumbersOfCategory()));
        nBudgets.setText(String.valueOf(controller.getNumbersOfBudgets()));
    }
}
