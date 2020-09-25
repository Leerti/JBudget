package it.unicam.cs.pa.jbudget105532;

import it.unicam.cs.pa.jbudget105532.Controller.BasicController;
import it.unicam.cs.pa.jbudget105532.Controller.Controller;
import it.unicam.cs.pa.jbudget105532.View.JavaFx.JavaFxHome;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.C;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JavaFxJbudget extends Application {

    private Stage stage;
    private Scene initialScene;
    private Controller controller = new BasicController();


    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Home.fxml"));
        initialScene = new Scene(loader.load());
        JavaFxHome initialFX = loader.<JavaFxHome>getController();
        Map<String, Object> info = new HashMap<>();
        info.put("Controller", controller);
        info.put("Stage", stage);
        initialFX.set(info);
        stage.setScene(initialScene);
        stage.setTitle("JBudget");
        stage.show();
        stage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            controller.save();
            System.exit(0);
        });
    }
}
