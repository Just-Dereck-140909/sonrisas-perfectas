package main.java.edu.ingsoft.sonrisas.perfectas.utils;

import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import main.java.edu.ingsoft.sonrisas.perfectas.controller.LoginController;
import main.java.edu.ingsoft.sonrisas.perfectas.controller.TratamientosDashboardController;
public class SceneManager {

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }
    
    public void mostrarDashboard(String nombreUsuario) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/tratamientos-dashboard-view.fxml"));

        loader.setControllerFactory(clase -> {

            if (clase == TratamientosDashboardController.class) {
                return new TratamientosDashboardController(this, nombreUsuario);
            }

            return null;
        });
        
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.show();
    }
    
    public void mostrarLogin() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/main/resources/view/login-view.fxml"));

        loader.setControllerFactory(clase -> {

            if (clase == LoginController.class) {
                return new LoginController(this);}

            return null;
        });

        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setWidth(650);
        stage.setHeight(680);
        stage.show();
    }
}