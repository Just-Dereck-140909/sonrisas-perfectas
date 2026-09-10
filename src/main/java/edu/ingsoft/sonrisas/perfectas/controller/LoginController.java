
package main.java.edu.ingsoft.sonrisas.perfectas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import main.java.edu.ingsoft.sonrisas.perfectas.config.DataBaseConnection;
import org.mindrot.jbcrypt.BCrypt;
import main.java.edu.ingsoft.sonrisas.perfectas.utils.SceneManager;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.RotateTransition;
import javafx.util.Duration;

public class LoginController {
    
    private boolean giroDerecha = true;
    
    private final SceneManager sceneManager;
    
    public LoginController(SceneManager sceneManager){
        this.sceneManager = sceneManager;
    }
    
    
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private ImageView imageLogin;

    
    @FXML
    private void iniciarSesion(ActionEvent event) {

        String usuario = txtUsuario.getText().trim();
        String contrasena = txtContrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("El usuario y la contraseña son obligatorios.");
            alerta.showAndWait();

            return;
        }

        String sql = "SELECT contrasena FROM usuarios WHERE nombre_usuario = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String hash = resultSet.getString("contrasena");

                    if (BCrypt.checkpw(contrasena, hash)) {

                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Inicio de sesión");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Inicio de sesión correcto.");
                        alerta.showAndWait();

                        sceneManager.mostrarDashboard(usuario);

                    } else {

                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error de autenticación");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Usuario o contraseña incorrectos.");
                        alerta.showAndWait();}

                } else {

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error de autenticación");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Usuario o contraseña incorrectos.");
                    alerta.showAndWait();
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("No se pudo realizar el inicio de sesión.");
            alerta.showAndWait();
        }
    }
    
    
    //Metodos para eventos de la Imagen
    @FXML
    private void animarImagenLogin(MouseEvent event) {

        ScaleTransition escala = new ScaleTransition(
            Duration.millis(250),
            imageLogin
        );

        escala.setToX(1.08);
        escala.setToY(1.08);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(250),
            imageLogin
        );

        rotacion.setToAngle(5);

        escala.play();
        rotacion.play();
    }
    
    @FXML
    private void restaurarImagenLogin(MouseEvent event) {

        ScaleTransition escala = new ScaleTransition(
            Duration.millis(250),
            imageLogin
        );

        escala.setToX(1.0);
        escala.setToY(1.0);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(250),
            imageLogin
        );

        rotacion.setToAngle(0);

        escala.play();
        rotacion.play();
    }
    
    @FXML
    private void animarImagenClick() {

        double angulo = giroDerecha ? 8 : -8;

        ScaleTransition escala = new ScaleTransition(
            Duration.millis(120),
            imageLogin
        );

        escala.setToX(1.12);
        escala.setToY(1.12);

        TranslateTransition salto = new TranslateTransition(
            Duration.millis(120),
            imageLogin
        );

        salto.setByY(-8);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(120),
            imageLogin
        );

        rotacion.setToAngle(angulo);

        ParallelTransition saltoAnimado = new ParallelTransition(
            escala,
            salto,
            rotacion
        );

        saltoAnimado.setOnFinished(event -> {

            ScaleTransition volverEscala = new ScaleTransition(
                Duration.millis(180),
                imageLogin
            );

            volverEscala.setToX(1.0);
            volverEscala.setToY(1.0);

            TranslateTransition volverPosicion = new TranslateTransition(
                Duration.millis(180),
                imageLogin
            );

            volverPosicion.setToY(0);

            RotateTransition volverRotacion = new RotateTransition(
                Duration.millis(180),
                imageLogin
            );

            volverRotacion.setToAngle(0);

            ParallelTransition volver = new ParallelTransition(
                volverEscala,
                volverPosicion,
                volverRotacion
            );

            volver.play();
        });

        giroDerecha = !giroDerecha;

        saltoAnimado.play();
    }
}
