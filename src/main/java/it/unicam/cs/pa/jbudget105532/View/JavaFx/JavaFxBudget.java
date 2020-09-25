package it.unicam.cs.pa.jbudget105532.View.JavaFx;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;


import it.unicam.cs.pa.jbudget105532.Exception.BudgetAlreadyExist;
import it.unicam.cs.pa.jbudget105532.Model.*;


import it.unicam.cs.pa.jbudget105532.View.Printer.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;

public class JavaFxBudget extends JavaFxInitialButton {
    @FXML
    private ListView<String> showBudgets;

    @FXML
    private Label budgetInformation;

    @FXML
    private Button modifyBudget;

    @FXML
    private Button showProgress;

    @FXML
    private Button deleteBudget;

    @FXML
    private Label errorModify;

    @FXML
    private Label errorDelete;

    @FXML
    private Label errorProgress;

    @FXML
    private Button newBudget;

    @FXML
    private TextArea amount_new;

    @FXML
    private ComboBox<String> category_new;

    @FXML
    private Label errorAmount_new;

    @FXML
    private Label errorCategory_new;

    @FXML
    private Label errorBudgetCreation_new;

    @FXML
    private Label errorAmount_modify;

    @FXML
    private TextArea amount_modify;

    @FXML
    private Button back_modify;

    @FXML
    private Button back_progress;

    @FXML
    private Label budgetInformation_progress;

    @FXML
    private LineChart<String, Double> progress_progress;

    @FXML
    private ComboBox<Integer> year_progress;


    public void set(Map<String, Object> info) {
        super.stage = (Stage) info.get("Stage");
        super.controller = (BasicController) info.get("Controller");
        super.budgetID = (int) info.get("Budget");
        if (showBudgets != null) {
            setHomeBudget();
        } else if (budgetInformation_progress != null) {
            setProgressBudget();
        } else {
            setModifyBudget();
        }
    }

    @FXML
    private void setHomeBudget() {
        showBudgets.setItems(observableArrayList(getBudgetRepresentation(controller.getBudgets())));
        category_new.setItems(observableArrayList(getCategoryRepresentation()));
        if (budgetID != -1) selectBudget();
    }

    @FXML
   private void setProgressBudget() {
        budgetInformation_progress.setText(new BasicBudgetPrinter(new BasicCategoryPrinter()).stringOf(controller.getBudget(budgetID)));
        year_progress.setItems(observableArrayList(controller.getYearsOfBudgetReport(budgetID)));
    }

    @FXML
    private void setModifyBudget() {
        amount_modify.setText(String.valueOf(controller.getBudgetAmount(budgetID)));
    }

    public List<String> getBudgetRepresentation(List<Budget> budgets) {
        CategoryPrinter cprinter = new BasicCategoryPrinter();
        BudgetPrinter printer = new BasicBudgetPrinter(cprinter);
        List<String> budgetsRepresentation = new ArrayList<>();
        for (Budget budget : budgets) {
            budgetsRepresentation.add(printer.stringOf(budget));
        }
        return budgetsRepresentation;
    }

    private List<String> getCategoryRepresentation() {
        List<String> categoryRepresentation = new ArrayList<>();
        List<Category> categories = controller.getCategories();
        for (Category category : categories) {
            categoryRepresentation.add(category.getName());
        }
        return categoryRepresentation;
    }

    @FXML
    public void selectBudget() {
        if (showBudgets.getSelectionModel().getSelectedItem() != null) {
            String budget = showBudgets.getSelectionModel().getSelectedItem();
            budgetID = Integer.parseInt(budget.substring(4, budget.indexOf("\n")));
        }

        budgetInformation.setText("ID :" + String.valueOf(controller.getBudget(budgetID).getID()));
    }

    @FXML
    public void newBudget() {
        HashMap<String, String> info = new HashMap<>();
        try {
            info.put("Amount", amount_new.getText());
            info.put("Category", category_new.getValue());
            controller.addBudget(info);
            showBudgets.setItems(observableArrayList(getBudgetRepresentation(controller.getBudgets())));
            errorCategory_new.setText("");
            errorAmount_new.setText("");
            errorBudgetCreation_new.setText("");
        } catch (NumberFormatException e) {
            errorAmount_new.setText("Please, insert a number");
            errorCategory_new.setText("");
            errorBudgetCreation_new.setText("");
        } catch (NullPointerException e) {
            errorCategory_new.setText("Please, chose a category");
            errorAmount_new.setText("");
            errorBudgetCreation_new.setText("");
        } catch (BudgetAlreadyExist e) {
            errorBudgetCreation_new.setText(e.getMessage());
            errorAmount_new.setText("");
            errorCategory_new.setText("");
        }
        errorModify.setText("");
        errorDelete.setText("");
        errorProgress.setText("");
    }

    @FXML
    public void removeBudget() {
        errorCategory_new.setText("");
        errorAmount_new.setText("");
        errorModify.setText("");
        errorDelete.setText("");
        errorProgress.setText("");
        if (budgetID == -1) {
            errorDelete.setText("Please,chose a budget");
        } else {
            controller.removeBudget(budgetID);
            showBudgets.setItems(observableArrayList(getBudgetRepresentation(controller.getBudgets())));
            budgetInformation.setText("");
            budgetID = -1;
        }
    }

    @FXML
    public void modifyBudget() throws IOException {
        if (budgetID == -1) {
            errorModify.setText("Please,chose a budget");
            errorCategory_new.setText("");
            errorAmount_new.setText("");
            errorDelete.setText("");
            errorProgress.setText("");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifyBudget.fxml"));
            Scene modify = new Scene(loader.load());
            stage.setScene(modify);
            JavaFxBudget modifyBudgetFX = loader.<JavaFxBudget>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Budget", budgetID);
            modifyBudgetFX.set(info);
        }
    }

    @FXML
    public void modifyBudget_modify() throws IOException {
        try {
            String info = amount_modify.getText();
            controller.modifyBudget(Double.parseDouble(info),budgetID);
            changeSceneBudget();
        } catch (NumberFormatException e) {
            errorAmount_modify.setText("Please,insert a number");
        }
    }

    @FXML
    public void showProgress() throws IOException {
        if (budgetID == -1) {
            errorProgress.setText("Please,chose a budget");
            errorCategory_new.setText("");
            errorAmount_new.setText("");
            errorModify.setText("");
            errorDelete.setText("");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BudgetProgress.fxml"));
            Scene showProgress = new Scene(loader.load());
            stage.setScene(showProgress);
            JavaFxBudget showProgressBudgetFX = loader.<JavaFxBudget>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Budget", budgetID);
            showProgressBudgetFX.set(info);
        }
    }

    @FXML
    public void setProgress() {
        progress_progress.getData().clear();
        HashMap<String, List<XYChart.Data<String, Double>>> info = controller.progressInformation(year_progress.getValue());
        XYChart.Series<String, Double> series1 = new XYChart.Series<>();
        XYChart.Series<String, Double> series2 = new XYChart.Series<>();
        XYChart.Series<String, Double> series3 = new XYChart.Series<>();
        XYChart.Series<String, Double> series4 = new XYChart.Series<>();
        series1.setData(FXCollections.observableArrayList(info.get("Income")));
        series2.setData(FXCollections.observableArrayList(info.get("Expense")));
        series3.setData(FXCollections.observableArrayList(info.get("Amount")));
        series4.setData(FXCollections.observableArrayList(info.get("Total")));
        series1.setName("Incomes");
        series2.setName("Outcomes");
        series3.setName("Budget");
        series4.setName("Total");
        progress_progress.getData().add(series1);
        progress_progress.getData().add(series2);
        progress_progress.getData().add(series3);
        progress_progress.getData().add(series4);
    }

}
