package main.java.edu.ingsoft.sonrisas.perfectas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import main.java.edu.ingsoft.sonrisas.perfectas.controller.LoginController;
import main.java.edu.ingsoft.sonrisas.perfectas.utils.SceneManager;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        SceneManager sceneManager = new SceneManager(stage);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/login-view.fxml")
        );

        loader.setControllerFactory(clase -> {

            if (clase == LoginController.class) {
                return new LoginController(sceneManager);
            }

            return null;
        });

        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Sonrisas Perfectas");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}