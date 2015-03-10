/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

import com.cqr.bean.PacienteBean;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.util.Util;
import com.cqr.xml.XMLParserPaciente;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import jxl.write.WriteException;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @class Search controller patients
 */
public class PacienteBuscarController implements Initializable {
    
    // FXML variables
    @FXML private TextField txtNrohistoria;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtCelular;
    @FXML private TextField txtTelefonoFijo;
    @FXML private TextField txtFechaNacimiento;
    @FXML private TextField txtEdad;
    @FXML private TextField txtPeso;
    @FXML private TextField txtTalla;
    @FXML private TextField txtBuscar;
    @FXML private Button btnGuardarPaciente;
    @FXML private TableView tableListadoPaciente2;
    @FXML private TableColumn clnNrohistoria;
    @FXML private TableColumn clnNombre;
    @FXML private TableColumn clnApellido;
    @FXML private TableColumn clnDireccion;
    @FXML private TableColumn clnCorreo;
    @FXML private TableColumn clnCelular;
    @FXML private TableColumn clnFijo;
    @FXML private TableColumn clnFecNac;
    @FXML private TableColumn clnEdad;
    @FXML private TableColumn clnPeso;
    @FXML private TableColumn clnTalla;
    
    // FXML class variables
    private ObservableList filteredData = FXCollections.observableArrayList();
    private ObservableList<PacienteBean> pacienteObservable = FXCollections.observableArrayList();
    private int posicionPacienteEnTabla;
    private int codigoPacienteModificar;
    
    /**
     * Constructor
     */
    public PacienteBuscarController() {
    }
    
    /**
     * This is method is a listener that works when the user
     * select any row on the table. 
     */
    private final ListChangeListener<PacienteBean> selectorTablePaciente =
            new ListChangeListener<PacienteBean>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends PacienteBean> c) {
            ponerPacienteSeleccionado();
        }
    };
    
    /**
     * Set selected patient data on textfields.
     */
    private void ponerPacienteSeleccionado() {
        
        final PacienteBean pacienteBean = getTablaPacienteSeleccionado();
        posicionPacienteEnTabla = pacienteObservable.indexOf(pacienteBean);
        
        if (pacienteBean != null) {
            
            codigoPacienteModificar = pacienteBean.getCodigo();
            
            // Set values into textfields.
            txtNrohistoria.setText(pacienteBean.getNrohistoria());
            txtNombre.setText(pacienteBean.getNombre());
            txtApellido.setText(pacienteBean.getApellido());
            txtDireccion.setText(pacienteBean.getDireccion());
            txtCorreo.setText(pacienteBean.getCorreo());
            txtCelular.setText(pacienteBean.getCelular());
            txtTelefonoFijo.setText(pacienteBean.getFijo());
            txtEdad.setText(pacienteBean.getEdad().toString());
            txtPeso.setText(pacienteBean.getPeso());
            txtTalla.setText(pacienteBean.getTalla());
            txtFechaNacimiento.setText(pacienteBean.getFechaNacimiento());
                    
            // Set items behavior.
            txtNombre.setDisable(false);txtApellido.setDisable(false);txtDireccion.setDisable(false);txtCorreo.setDisable(false);txtCelular.setDisable(false);txtTelefonoFijo.setDisable(false);txtFechaNacimiento.setDisable(false);txtEdad.setDisable(false);txtPeso.setDisable(false);txtTalla.setDisable(false);
            txtNrohistoria.setDisable(false);
            btnGuardarPaciente.setDisable(false);
        }
    }
    
    /**
     * Get selected patient data.
     * 
     * @return 
     */
    public PacienteBean getTablaPacienteSeleccionado() {
        
        if (tableListadoPaciente2 != null) {
            List<PacienteBean> tabla = 
                    tableListadoPaciente2.getSelectionModel().getSelectedItems();
            
            if(tabla.size() == 1) {
                final PacienteBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    /**
     * Ini patients table.
     */
    private void inicializarTablaPaciente() {
        
        clnNrohistoria.setCellValueFactory(new PropertyValueFactory("nrohistoria"));
        clnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clnApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        clnDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        clnCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        clnCelular.setCellValueFactory(new PropertyValueFactory("celular"));
        clnFijo.setCellValueFactory(new PropertyValueFactory("fijo"));
        clnFecNac.setCellValueFactory(new PropertyValueFactory("fechaNacimiento"));
        clnEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        clnPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        clnTalla.setCellValueFactory(new PropertyValueFactory("talla")); 
        
        tableListadoPaciente2.setItems(filteredData);
        tableListadoPaciente2.setPlaceholder(new Label("No hay pacientes registrados"));
        
        // Add listener to "Search" field.
        txtBuscar.textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                updateFilteredData();
            }
        });
    }
    
    // FXML Methods.
    
    /**
     * Save patients.
     * 
     * @param evento
     * @throws WriteException
     * @throws IOException 
     */
    @FXML
    private void guardarPaciente(ActionEvent evento) throws WriteException, IOException {
        
        // Validates mandatories fields.
        if ( txtNombre.getText().equalsIgnoreCase("") ||
             txtApellido.getText().equalsIgnoreCase("") ||
             txtEdad.getText().equalsIgnoreCase("") ||
             txtNrohistoria.getText().equalsIgnoreCase("") ) {
            
            Dialogs.create().owner(null)
                .title("Datos imcompletos.")
                .masthead(null)
                .message( "Debes completar al menos el Nombre, apellido, edad y Nro. de historia del paciente.")
                .showWarning();
            
        } else {
            
            // If email data is empty.
            if (txtCorreo.getText().trim().equalsIgnoreCase("")) {
                
                PacienteBean paciente = new PacienteBean();
                
                // Set values to patient' object.
                paciente.setCodigo(codigoPacienteModificar);
                paciente.setNrohistoria(txtNrohistoria.getText());
                paciente.setNombre(txtNombre.getText().trim());
                paciente.setApellido(txtApellido.getText().trim());
                paciente.setDireccion(txtDireccion.getText().trim());
                paciente.setCorreo(txtCorreo.getText().trim());
                paciente.setCelular(txtCelular.getText().trim());
                paciente.setFijo(txtTelefonoFijo.getText().trim());
                paciente.setFechaNacimiento(txtFechaNacimiento.getText().trim());
                paciente.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                paciente.setPeso(txtPeso.getText().trim());
                paciente.setTalla(txtTalla.getText().trim());

                IXMLParserPaciente xmlPPac = new XMLParserPaciente();

                // Check if the update process was succesfully.
                if (xmlPPac.updatePaciente(paciente)) {

                    Dialogs.create().owner(null)
                        .title("Datos Guardados")
                        .masthead(null)
                        .message( "Paciente modificado correctamente")
                        .showInformation();
                    
                    Node node=(Node) evento.getSource();
                    Stage stage=(Stage) node.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Paciente.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                } else {
                    Dialogs.create().owner(null)
                        .title("Error")
                        .masthead(null)
                        .message( "No se pudo guardar los datos. Intentelo nuevamente.")
                        .showError();
                }
                
            } else {
                
                // If email value is not empty.
                Util util = new Util();
                
                if (util.isValidEmailAddress(txtCorreo.getText().trim())) {
                    
                    PacienteBean paciente = new PacienteBean();
            
                    paciente.setCodigo(codigoPacienteModificar);
                    paciente.setNrohistoria(txtNrohistoria.getText());
                    paciente.setNombre(txtNombre.getText().trim());
                    paciente.setApellido(txtApellido.getText().trim());
                    paciente.setDireccion(txtDireccion.getText().trim());
                    paciente.setCorreo(txtCorreo.getText().trim());
                    paciente.setCelular(txtCelular.getText().trim());
                    paciente.setFijo(txtTelefonoFijo.getText().trim());
                    paciente.setFechaNacimiento(txtFechaNacimiento.getText().trim());
                    paciente.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                    paciente.setPeso(txtPeso.getText().trim());
                    paciente.setTalla(txtTalla.getText().trim());

                    IXMLParserPaciente xmlPPac = new XMLParserPaciente();
                    
                    // Validate update process was succesfully done.
                    if (xmlPPac.updatePaciente(paciente)) {

                        Dialogs.create().owner(null)
                            .title("Datos Guardados")
                            .masthead(null)
                            .message( "Paciente modificado correctamente")
                            .showInformation();
                        
                        Node node=(Node) evento.getSource();
                        Stage stage=(Stage) node.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();

                    } else {
                        Dialogs.create().owner(null)
                            .title("Error")
                            .masthead(null)
                            .message( "No se pudo guardar los datos. Intentelo nuevamente.")
                            .showError();
                    }
                    
                } else {
                    
                    Dialogs.create().owner(null)
                    .title("Email incorrecto.")
                    .masthead(null)
                    .message( "Debes ingresar un mail valido.")
                    .showWarning();
                }
            }
        }
    }
    
    /**
     * Return to main patient GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void backToPaciente(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Paciente.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Ini table patients.
        this.inicializarTablaPaciente();
        
        // Clean textfields values.
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtCelular.setText("");
        txtTelefonoFijo.setText("");
        txtFechaNacimiento.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtTalla.setText("");
        txtNrohistoria.setText("");
        
        // Set items behavior.
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtDireccion.setDisable(true);
        txtCorreo.setDisable(true);
        txtCelular.setDisable(true);
        txtTelefonoFijo.setDisable(true);
        txtFechaNacimiento.setDisable(true);
        txtEdad.setDisable(true);
        txtPeso.setDisable(true);
        txtTalla.setDisable(true);
        txtNrohistoria.setDisable(true);
        btnGuardarPaciente.setDisable(true);
        
        final ObservableList<PacienteBean> tablaPacienteSel = 
                tableListadoPaciente2.getSelectionModel().getSelectedItems();
        tablaPacienteSel.addListener(selectorTablePaciente);
        
        List<PacienteBean> arregloPacientes = new ArrayList();
        IXMLParserPaciente xmlParser = new XMLParserPaciente();
        
        try {
            // Get all data from patients stored in xml document.
            arregloPacientes = xmlParser.readXMLPacientes();
            
            for (int i=0; i< arregloPacientes.size(); i++) {
                
                PacienteBean pacienteTable = new PacienteBean();
                pacienteTable.setCodigo(arregloPacientes.get(i).getCodigo());
                pacienteTable.setNrohistoria(arregloPacientes.get(i).getNrohistoria());
                pacienteTable.setNombre(arregloPacientes.get(i).getNombre());
                pacienteTable.setApellido(arregloPacientes.get(i).getApellido());
                pacienteTable.setDireccion(arregloPacientes.get(i).getDireccion());
                pacienteTable.setCorreo(arregloPacientes.get(i).getCorreo());
                pacienteTable.setCelular(arregloPacientes.get(i).getCelular());
                pacienteTable.setFijo(arregloPacientes.get(i).getFijo());
                pacienteTable.setFechaNacimiento(arregloPacientes.get(i).getFechaNacimiento());
                pacienteTable.setEdad(arregloPacientes.get(i).getEdad());
                pacienteTable.setPeso(arregloPacientes.get(i).getPeso());
                pacienteTable.setTalla(arregloPacientes.get(i).getTalla());
                pacienteObservable.add(pacienteTable);
            }
            
            filteredData.addAll(pacienteObservable);
            
            // Add a listener to the patients table.
            pacienteObservable.addListener(new ListChangeListener() {

                @Override
                public void onChanged(ListChangeListener.Change c) {
                    updateFilteredData();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Update filtered data.
     */
    private void updateFilteredData() {
        filteredData.clear();
        
        for (PacienteBean p : pacienteObservable) {
            if(matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        reapplyTableSortOrder();
    }
    
    /**
     * Match filters.
     * 
     * @param person
     * @return 
     */
    private boolean matchesFilter(PacienteBean person) {
        
        String filterString = txtBuscar.getText();
        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();
        
        if (person.getNombre().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getApellido().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }
    
    /**
     * Order table.
     */
    private void reapplyTableSortOrder() {
        tableListadoPaciente2.getSortOrder().addAll(clnNombre);
    }
}
