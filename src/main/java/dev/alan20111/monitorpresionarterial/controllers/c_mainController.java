package dev.alan20111.monitorpresionarterial.controllers;

import dev.alan20111.monitorpresionarterial.dao.impl.c_pacienteDAOImpl;
import dev.alan20111.monitorpresionarterial.dao.impl.c_medicionDAOImpl;
import dev.alan20111.monitorpresionarterial.models.c_paciente;
import dev.alan20111.monitorpresionarterial.models.c_medicion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class c_mainController implements Initializable {

    private c_pacienteDAOImpl pacienteDAO = new c_pacienteDAOImpl();
    private c_medicionDAOImpl medicionDAO = new c_medicionDAOImpl();

    private c_paciente pacienteEnEdicion = null;
    private c_medicion medicionEnEdicion = null;

    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private DatePicker dpFechaNac;
    @FXML private ComboBox<String> cmbGenero;
    @FXML private TextField txtPeso;
    @FXML private TextField txtAltura;
    @FXML private Button btnGuardarPaciente;

    @FXML private TableView<c_paciente> tblPacientes;
    @FXML private TableColumn<c_paciente, Integer> colPacId;
    @FXML private TableColumn<c_paciente, String> colPacNombres;
    @FXML private TableColumn<c_paciente, String> colPacApellidos;
    @FXML private TableColumn<c_paciente, LocalDate> colPacFechaNac;
    @FXML private TableColumn<c_paciente, String> colPacGenero;
    @FXML private TableColumn<c_paciente, Double> colPacPeso;
    @FXML private TableColumn<c_paciente, Double> colPacAltura;

    @FXML private ComboBox<c_paciente> cmbPacientesMedicion;
    @FXML private TextField txtSistolica;
    @FXML private TextField txtDiastolica;
    @FXML private TextField txtPulsos;
    @FXML private Button btnGuardarMedicion;

    @FXML private TableView<c_medicion> tblMediciones;
    @FXML private TableColumn<c_medicion, Integer> colMedId;
    @FXML private TableColumn<c_medicion, Integer> colMedIdPac;
    @FXML private TableColumn<c_medicion, Integer> colMedSis;
    @FXML private TableColumn<c_medicion, Integer> colMedDia;
    @FXML private TableColumn<c_medicion, Integer> colMedPulso;
    @FXML private TableColumn<c_medicion, LocalDateTime> colMedFecha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGenero.setItems(FXCollections.observableArrayList("Masculino", "Femenino", "Otro"));

        configurarColumnas();
        cargarPacientes();
        cargarTodasLasMediciones();

        cmbPacientesMedicion.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                filtrarMediciones(newVal.getId_paciente());
            }
        });

        tblPacientes.setRowFactory(tv -> {
            TableRow<c_paciente> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    c_paciente seleccionado = row.getItem();
                    cmbPacientesMedicion.setValue(seleccionado);
                    filtrarMediciones(seleccionado.getId_paciente());

                    if (event.getClickCount() == 2) {
                        cargarPacienteEnFormulario(seleccionado);
                    }
                }
            });
            return row;
        });

        tblMediciones.setRowFactory(tv -> {
            TableRow<c_medicion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    cargarMedicionEnFormulario(row.getItem());
                }
            });
            return row;
        });
    }
    @FXML private TableColumn<c_paciente, String> colPacDiagnostico;

    private void configurarColumnas() {
        colPacId.setCellValueFactory(new PropertyValueFactory<>("id_paciente"));
        colPacNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colPacApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colPacFechaNac.setCellValueFactory(new PropertyValueFactory<>("fecha_nacimiento"));
        colPacGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colPacPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colPacAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        colMedId.setCellValueFactory(new PropertyValueFactory<>("id_medicion"));
        colMedIdPac.setCellValueFactory(new PropertyValueFactory<>("id_paciente"));
        colMedSis.setCellValueFactory(new PropertyValueFactory<>("presion_sistolica"));
        colMedDia.setCellValueFactory(new PropertyValueFactory<>("presion_diastolica"));
        colMedPulso.setCellValueFactory(new PropertyValueFactory<>("pulsos"));
        colMedFecha.setCellValueFactory(new PropertyValueFactory<>("fecha_hora"));

        colPacDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnosticoPeso"));
    }

    private void cargarPacientes() {
        ObservableList<c_paciente> lista = FXCollections.observableArrayList(pacienteDAO.findAll());
        tblPacientes.setItems(lista);
        cmbPacientesMedicion.setItems(lista);
    }

    @FXML
    private void cargarTodasLasMediciones() {
        ObservableList<c_medicion> lista = FXCollections.observableArrayList(medicionDAO.findAll());
        tblMediciones.setItems(lista);

        cmbPacientesMedicion.getSelectionModel().clearSelection();
    }

    private void filtrarMediciones(int idPaciente) {
        ObservableList<c_medicion> filtradas = FXCollections.observableArrayList(medicionDAO.findByPacienteId(idPaciente));
        tblMediciones.setItems(filtradas);
    }

    private void cargarPacienteEnFormulario(c_paciente p) {
        pacienteEnEdicion = p;
        txtNombres.setText(p.getNombres());
        txtApellidos.setText(p.getApellidos());
        dpFechaNac.setValue(p.getFecha_nacimiento());
        cmbGenero.setValue(p.getGenero());
        txtPeso.setText(String.valueOf(p.getPeso()));
        txtAltura.setText(String.valueOf(p.getAltura()));

        btnGuardarPaciente.setText("Actualizar Paciente");
        btnGuardarPaciente.getStyleClass().setAll("btn", "btn-warning");
    }

    @FXML
    private void guardarPaciente() {
        try {
            if (pacienteEnEdicion == null) {
                c_paciente p = new c_paciente(
                        txtNombres.getText(), txtApellidos.getText(),
                        dpFechaNac.getValue(), cmbGenero.getValue(),
                        Double.parseDouble(txtPeso.getText()), Double.parseDouble(txtAltura.getText())
                );
                if (pacienteDAO.insert(p)) {
                    mostrarAlerta("Éxito", "Paciente guardado.", Alert.AlertType.INFORMATION);
                    limpiarFormularioPaciente();
                    cargarPacientes();
                }
            } else {
                pacienteEnEdicion.setNombres(txtNombres.getText());
                pacienteEnEdicion.setApellidos(txtApellidos.getText());
                pacienteEnEdicion.setFecha_nacimiento(dpFechaNac.getValue());
                pacienteEnEdicion.setGenero(cmbGenero.getValue());
                pacienteEnEdicion.setPeso(Double.parseDouble(txtPeso.getText()));
                pacienteEnEdicion.setAltura(Double.parseDouble(txtAltura.getText()));

                if (pacienteDAO.update(pacienteEnEdicion)) {
                    mostrarAlerta("Éxito", "Paciente actualizado.", Alert.AlertType.INFORMATION);
                    limpiarFormularioPaciente();
                    cargarPacientes();
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Verifica los datos (peso y altura deben ser números).", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarFormularioPaciente() {
        pacienteEnEdicion = null;
        txtNombres.clear(); txtApellidos.clear(); txtPeso.clear(); txtAltura.clear();
        dpFechaNac.setValue(null); cmbGenero.setValue(null);
        tblPacientes.getSelectionModel().clearSelection();

        btnGuardarPaciente.setText("Guardar Paciente");
        btnGuardarPaciente.getStyleClass().setAll("btn", "btn-primary");
    }

    private void cargarMedicionEnFormulario(c_medicion m) {
        medicionEnEdicion = m;

        for (c_paciente p : cmbPacientesMedicion.getItems()) {
            if (p.getId_paciente() == m.getId_paciente()) {
                cmbPacientesMedicion.setValue(p);
                break;
            }
        }

        txtSistolica.setText(String.valueOf(m.getPresion_sistolica()));
        txtDiastolica.setText(String.valueOf(m.getPresion_diastolica()));
        txtPulsos.setText(String.valueOf(m.getPulsos()));

        btnGuardarMedicion.setText("Actualizar Medición");
        btnGuardarMedicion.getStyleClass().setAll("btn", "btn-warning");
    }

    @FXML
    private void guardarMedicion() {
        if (cmbPacientesMedicion.getValue() == null) {
            mostrarAlerta("Advertencia", "Selecciona un paciente.", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (medicionEnEdicion == null) {
                c_medicion m = new c_medicion(
                        cmbPacientesMedicion.getValue().getId_paciente(),
                        Integer.parseInt(txtSistolica.getText()),
                        Integer.parseInt(txtDiastolica.getText()),
                        Integer.parseInt(txtPulsos.getText()),
                        LocalDateTime.now()
                );
                if (medicionDAO.insert(m)) {
                    mostrarAlerta("Éxito", "Medición registrada.", Alert.AlertType.INFORMATION);
                    limpiarFormularioMedicion();
                    filtrarMediciones(m.getId_paciente());
                }
            } else {
                medicionEnEdicion.setId_paciente(cmbPacientesMedicion.getValue().getId_paciente());
                medicionEnEdicion.setPresion_sistolica(Integer.parseInt(txtSistolica.getText()));
                medicionEnEdicion.setPresion_diastolica(Integer.parseInt(txtDiastolica.getText()));
                medicionEnEdicion.setPulsos(Integer.parseInt(txtPulsos.getText()));

                if (medicionDAO.update(medicionEnEdicion)) {
                    mostrarAlerta("Éxito", "Medición actualizada.", Alert.AlertType.INFORMATION);
                    int idRefresco = medicionEnEdicion.getId_paciente();
                    limpiarFormularioMedicion();
                    filtrarMediciones(idRefresco);
                }
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Presiones y pulsos deben ser enteros.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void limpiarFormularioMedicion() {
        medicionEnEdicion = null;
        txtSistolica.clear(); txtDiastolica.clear(); txtPulsos.clear();
        tblMediciones.getSelectionModel().clearSelection();

        btnGuardarMedicion.setText("Guardar Medición");
        btnGuardarMedicion.getStyleClass().setAll("btn", "btn-success");
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    public void eliminarPaciente() {
        c_paciente pacienteSeleccionado = tblPacientes.getSelectionModel().getSelectedItem();

        if (pacienteSeleccionado == null) {
            mostrarAlerta("Atención", "Por favor, selecciona un paciente de la tabla para eliminarlo.", Alert.AlertType.WARNING);
            return;
        }

        javafx.scene.control.Alert confirmacion = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar este paciente?");
        confirmacion.setContentText("Esta acción borrará sus datos y todas sus mediciones.");

        java.util.Optional<javafx.scene.control.ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK) {
            // CORRECCIÓN 2: Se usa getId_paciente() (como está en tu modelo c_paciente)
            if (pacienteDAO.delete(pacienteSeleccionado.getId_paciente())) {

                cargarPacientes();
                mostrarAlerta("Éxito", "Paciente eliminado correctamente.", Alert.AlertType.INFORMATION);

            } else {
                mostrarAlerta("Error", "Hubo un problema al eliminar el paciente en la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    public void eliminarMedicion() {
        c_medicion medicionSeleccionada = tblMediciones.getSelectionModel().getSelectedItem();

        if (medicionSeleccionada == null) {
            mostrarAlerta("Atención", "Por favor, selecciona una medición de la tabla para eliminarla.", Alert.AlertType.WARNING);
            return;
        }

        javafx.scene.control.Alert confirmacion = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar esta medición?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        java.util.Optional<javafx.scene.control.ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == javafx.scene.control.ButtonType.OK) {

            if (medicionDAO.delete(medicionSeleccionada.getId_medicion())) {

                if (cmbPacientesMedicion.getValue() != null) {
                    filtrarMediciones(cmbPacientesMedicion.getValue().getId_paciente());
                } else {
                    cargarTodasLasMediciones();
                }

                mostrarAlerta("Éxito", "Medición eliminada correctamente.", Alert.AlertType.INFORMATION);

            } else {
                mostrarAlerta("Error", "Hubo un problema al eliminar la medición en la base de datos.", Alert.AlertType.ERROR);
            }
        }
    }
}