package it.unicam.cs.pa.jbudget105532.View.JavaFx;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryAlreadyExisting;
import it.unicam.cs.pa.jbudget105532.Exception.StringBlankException;
import it.unicam.cs.pa.jbudget105532.View.Printer.BasicCategoriesPrinter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaFxCategory extends JavaFxInitialButton {
    @FXML
    private Button newCategory;

    @FXML
    private Button removeCategory;

    @FXML
    private Label errorRemove;

    @FXML
    private Label alertRemove;

    @FXML
    private ListView<String> showCategory;

    @FXML
    private TextArea name_new;

    @FXML
    private Label errorNew;


    public void set(Map<String, Object> info) {
        super.stage = (Stage) info.get("Stage");
        super.controller = (BasicController) info.get("Controller");
        super.categoryNameForCategory = (String) info.get("Category");
        if (showCategory != null) {
            setHomeCategory();
        } else {
            setAlerRemove();
        }
    }

    @FXML
    private void setHomeCategory() {
        showCategory.setItems(FXCollections.observableArrayList(getCategoryRepresentation()));
        if (categoryNameForCategory != "") {
            selectCategory();
        }
    }

    @FXML
    private void setAlerRemove() {
        alertRemove.setText("If you remove a category, \nit will be removed from all transactions\nand movements that contain it");
    }


    private List<String> getCategoryRepresentation() {
        return new BasicCategoriesPrinter().stringOf(controller.getCategories());
    }

    @FXML
    public void selectCategory() {
        if (showCategory.getSelectionModel().getSelectedItem() == null) {
            showCategory.getSelectionModel().select(categoryNameForCategory);
        } else {
            super.categoryNameForCategory = showCategory.getSelectionModel().getSelectedItem();
        }
    }

    @FXML
    public void removeCategory() throws IOException {
        if (categoryNameForCategory.isBlank()) {
            errorRemove.setText("Please,chose a category");
            errorNew.setText("");
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AlertRemove.fxml"));
            Scene alert = new Scene(loader.load());
            super.stage.setScene(alert);
            JavaFxCategory categoryFX = loader.<JavaFxCategory>getController();
            Map<String, Object> info = new HashMap<>();
            info.put("Controller", controller);
            info.put("Stage", stage);
            info.put("Category", categoryNameForCategory);
            categoryFX.set(info);
        }
    }

    @FXML
    public void confirmRemove() throws IOException {
        controller.removeCategory(categoryNameForCategory);
        categoryNameForCategory = "";
        changeSceneCategory();
    }

    @FXML
    public void newCategory() {
        String info = name_new.getText();
        errorRemove.setText("");
        try {
            controller.addCategory(info);
            showCategory.setItems(FXCollections.observableArrayList(getCategoryRepresentation()));

        } catch (CategoryAlreadyExisting e) {
            errorNew.setText("Category already exist");
        } catch (StringBlankException e) {
            errorNew.setText(e.getMessage());
        }
    }


}
