
package main.java.edu.ingsoft.sonrisas.perfectas.controller;

import java.math.BigDecimal;
import main.java.edu.ingsoft.sonrisas.perfectas.model.Tratamiento;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.edu.ingsoft.sonrisas.perfectas.config.DataBaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
public class TratamientosDashboardController {

    private boolean giroDerecha = true;
    
    private final SceneManager sceneManager;
    private final String nombreUsuario;

    public TratamientosDashboardController(SceneManager sceneManager, String nombreUsuario) {
        this.sceneManager = sceneManager;
        this.nombreUsuario = nombreUsuario;
    }
    
    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtCosto;
    
    @FXML
    private Label lblUsuario;
    
    @FXML
    private ImageView imageDashboard;
    
    @FXML
    private TableView<Tratamiento> tablaTratamientos;

    @FXML
    private TableColumn<Tratamiento, String> colCodigo;

    @FXML
    private TableColumn<Tratamiento, String> colNombre;

    @FXML
    private TableColumn<Tratamiento, String> colDescripcion;

    @FXML
    private TableColumn<Tratamiento, BigDecimal> colCosto;

    
    //Metodo para cargar la TableView de tratamientos
    private void cargarTratamientos() {
    ObservableList<Tratamiento> tratamientos = FXCollections.observableArrayList();

    String sql = "SELECT codigo_tratamiento, id_usuario, nombre_tratamiento, "
    + "descripcion, costo_estandar FROM tratamientos";

    try (Connection connection = DataBaseConnection.getConnectionDataBase();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {

            Tratamiento tratamiento = new Tratamiento(
                    resultSet.getString("codigo_tratamiento"),
                    resultSet.getString("id_usuario"),
                    resultSet.getString("nombre_tratamiento"),
                    resultSet.getString("descripcion"),
                    resultSet.getBigDecimal("costo_estandar")
            );

            tratamientos.add(tratamiento);
        }

        tablaTratamientos.setItems(tratamientos);

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    //Metodo para añadir un tratamiento a la TableView
    @FXML
    private void guardarTratamiento(ActionEvent event) {

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String costoTexto = txtCosto.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || costoTexto.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe completar todos los campos.");
            alerta.showAndWait();

            return;
        }

        try {

            BigDecimal costo = new BigDecimal(costoTexto);

            if (costo.compareTo(BigDecimal.ZERO) <= 0) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Costo inválido");
                alerta.setHeaderText(null);
                alerta.setContentText("El costo no puede ser negativo ni cero.");
                alerta.showAndWait();

                return;
            }

            String idUsuario = "US001";

            String codigo = generarCodigoTratamiento();

            String sql = "INSERT INTO tratamientos "
                    + "(codigo_tratamiento, id_usuario, nombre_tratamiento, "
                    + "descripcion, costo_estandar) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (Connection connection = DataBaseConnection.getConnectionDataBase();
                 PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, codigo);
                statement.setString(2, idUsuario);
                statement.setString(3, nombre);
                statement.setString(4, descripcion);
                statement.setBigDecimal(5, costo);
                statement.executeUpdate();
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Tratamiento guardado");
            alerta.setHeaderText(null);
            alerta.setContentText("El tratamiento " + codigo + " se guardó correctamente.");
            alerta.showAndWait();
            
            cargarTratamientos();

            txtNombre.clear();
            txtDescripcion.clear();
            txtCosto.clear();

        } catch (NumberFormatException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Costo inválido");
            alerta.setHeaderText(null);
            alerta.setContentText("El costo debe ser un número válido.");
            alerta.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("No se pudo guardar el tratamiento.");
            alerta.showAndWait();
        }
    }

    //Metodo para actualizar un tratamiento a la TableView
    @FXML
    private void actualizarTratamiento(ActionEvent event) {

        Tratamiento tratamientoSeleccionado = tablaTratamientos.getSelectionModel().getSelectedItem();

        if (tratamientoSeleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Tratamiento no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un tratamiento de la tabla para actualizarlo.");
            alerta.showAndWait();

            return;
        }

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String costoTexto = txtCosto.getText().trim();

        if (nombre.isEmpty() || descripcion.isEmpty() || costoTexto.isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Campos incompletos");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe completar todos los campos.");
            alerta.showAndWait();

            return;
        }

        try {

            BigDecimal costo = new BigDecimal(costoTexto);

            if (costo.compareTo(BigDecimal.ZERO) <= 0) {

                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Costo inválido");
                alerta.setHeaderText(null);
                alerta.setContentText("El costo no puede ser negativo ni cero.");
                alerta.showAndWait();
                return;
            }

            String sql = "UPDATE tratamientos "
                    + "SET nombre_tratamiento = ?, "
                    + "descripcion = ?, "
                    + "costo_estandar = ? "
                    + "WHERE codigo_tratamiento = ?";

            try (Connection connection = DataBaseConnection.getConnectionDataBase();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setBigDecimal(3, costo);
                statement.setString(4, tratamientoSeleccionado.getCodigoTratamiento());

                statement.executeUpdate();
            }

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Tratamiento actualizado");
            alerta.setHeaderText(null);
            alerta.setContentText("El tratamiento se actualizó correctamente.");
            alerta.showAndWait();

            cargarTratamientos();

            txtNombre.clear();
            txtDescripcion.clear();
            txtCosto.clear();

        } catch (NumberFormatException e) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Costo inválido");
            alerta.setHeaderText(null);
            alerta.setContentText("El costo debe ser un número válido.");
            alerta.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("No se pudo actualizar el tratamiento.");
            alerta.showAndWait();
        }
}

    //Metodo para eliminar un tratamiento de la TableView
    @FXML
    private void eliminarTratamiento(ActionEvent event) {

        Tratamiento tratamientoSeleccionado = tablaTratamientos.getSelectionModel().getSelectedItem();

        if (tratamientoSeleccionado == null) {

            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Tratamiento no seleccionado");
            alerta.setHeaderText(null);
            alerta.setContentText("Seleccione un tratamiento de la tabla para eliminarlo.");
            alerta.showAndWait();

            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText(
                "¿Está seguro de eliminar el tratamiento "
                + tratamientoSeleccionado.getCodigoTratamiento()
                + "?"
        );

        if (confirmacion.showAndWait().get() == javafx.scene.control.ButtonType.OK) {

            String sql = "DELETE FROM tratamientos WHERE codigo_tratamiento = ?";

            try (Connection connection = DataBaseConnection.getConnectionDataBase();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setString(
                        1,
                        tratamientoSeleccionado.getCodigoTratamiento()
                );

                statement.executeUpdate();

                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Tratamiento eliminado");
                alerta.setHeaderText(null);
                alerta.setContentText("El tratamiento se eliminó correctamente.");
                alerta.showAndWait();

                cargarTratamientos();

                txtNombre.clear();
                txtDescripcion.clear();
                txtCosto.clear();

            } catch (Exception e) {

                e.printStackTrace();

                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("No se pudo eliminar el tratamiento.");
                alerta.showAndWait();
            }
        }
    }

    //Metodo para limpiar los campos del formulario para el TableView
    @FXML
    private void limpiarCampos(ActionEvent event) {
        txtNombre.clear();
        txtDescripcion.clear();
        txtCosto.clear();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
        sceneManager.mostrarLogin();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    //Metodo para llenar la vista con los datos necesarios
    @FXML
    public void initialize() {

        lblUsuario.setText(nombreUsuario);
        
        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("codigoTratamiento")
        );

        colNombre.setCellValueFactory(
                new PropertyValueFactory<>("nombreTratamiento")
        );

        colDescripcion.setCellValueFactory(
                new PropertyValueFactory<>("descripcion")
        );

        colCosto.setCellValueFactory(
                new PropertyValueFactory<>("costoEstandar")
        );
        cargarTratamientos();
        
        tablaTratamientos.getSelectionModel()
        .selectedItemProperty()
        .addListener((observable, anterior, seleccionado) -> {

            if (seleccionado != null) {

                txtNombre.setText(seleccionado.getNombreTratamiento());
                txtDescripcion.setText(seleccionado.getDescripcion());
                txtCosto.setText(seleccionado.getCostoEstandar().toString());
            }
        });
    }
    
    
    //Metodos "Extra" para completar los eventos de los botones e imagen
    
    private String generarCodigoTratamiento() {

        String codigo = "TR001";

        String sql = "SELECT codigo_tratamiento "
                + "FROM tratamientos "
                + "ORDER BY codigo_tratamiento DESC "
                + "LIMIT 1";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                String ultimoCodigo = resultSet.getString("codigo_tratamiento");

                int numero = Integer.parseInt(ultimoCodigo.substring(2));

                numero++;

                codigo = String.format("TR%03d", numero);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return codigo;
    }
    
    @FXML
    private void animarImagenDashboard(MouseEvent event) {

        ScaleTransition escala = new ScaleTransition(
            Duration.millis(250),
            imageDashboard
        );

        escala.setToX(1.08);
        escala.setToY(1.08);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(250),
            imageDashboard
        );

        rotacion.setToAngle(5);

        escala.play();
        rotacion.play();
    }

    @FXML
    private void restaurarImagenDashboard(MouseEvent event) {

        ScaleTransition escala = new ScaleTransition(
            Duration.millis(250),
            imageDashboard
        );

        escala.setToX(1.0);
        escala.setToY(1.0);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(250),
            imageDashboard
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
            imageDashboard
        );

        escala.setToX(1.12);
        escala.setToY(1.12);

        TranslateTransition salto = new TranslateTransition(
            Duration.millis(120),
            imageDashboard
        );

        salto.setByY(-8);

        RotateTransition rotacion = new RotateTransition(
            Duration.millis(120),
            imageDashboard
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
                imageDashboard
            );

            volverEscala.setToX(1.0);
            volverEscala.setToY(1.0);

            TranslateTransition volverPosicion = new TranslateTransition(
                Duration.millis(180),
                imageDashboard
            );

            volverPosicion.setToY(0);

            RotateTransition volverRotacion = new RotateTransition(
                Duration.millis(180),
                imageDashboard
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
