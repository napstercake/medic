/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

import com.cqr.bean.PacienteBean;
import com.cqr.interfaz.IXMLParserPaciente;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @class This class is used to store a record on XML from any reserved appointment.
 */
public class ReservarCitaController implements Initializable {
    
    
    // FXML variables.
    @FXML private TextField txtBuscarPaciente;
    @FXML private TableView pacienteTable;
    @FXML private TableColumn clnNrohistoria;
    @FXML private TableColumn clnNombre;
    @FXML private TableColumn clnApellido;
    @FXML private TableColumn clnEdad;
    @FXML private Button btnRegistrarCita;
    
    // Class variables
    private ObservableList filteredData = FXCollections.observableArrayList();
    private ObservableList<PacienteBean> masterData = FXCollections.observableArrayList();
    private int posicionPacienteEnTabla;
    private PacienteBean paciente; 
    public static String parameters = "hola";
    
    /**
     * Save a new appointment.
     * This method will modify the XML file too.
     * 
     */
    public ReservarCitaController() {
        
        List<PacienteBean> arregloPacientes = new ArrayList();
        IXMLParserPaciente xmlParser = new XMLParserPaciente();
        
        try {
            arregloPacientes = xmlParser.readXMLPacientes();
            
            for (int i=0; i< arregloPacientes.size(); i++) {
                
                PacienteBean pacienteTable = new PacienteBean();
                
                pacienteTable.setCodigo(arregloPacientes.get(i).getCodigo());
                pacienteTable.setNrohistoria(arregloPacientes.get(i).getNrohistoria());
                pacienteTable.setNombre(arregloPacientes.get(i).getNombre());
                pacienteTable.setApellido(arregloPacientes.get(i).getApellido());
                pacienteTable.setPeso(arregloPacientes.get(i).getPeso());
                pacienteTable.setTalla(arregloPacientes.get(i).getTalla());
                pacienteTable.setEdad(arregloPacientes.get(i).getEdad());
                masterData.add(pacienteTable);
            }
            
            filteredData.addAll(masterData);
            
            // Add a listener to the table.
            masterData.addListener(new ListChangeListener() {
                
                @Override
                public void onChanged(ListChangeListener.Change change) {
                    updateFilteredData();
                }
                
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Ini patient's table.
     */
    private void inicializarTablaPacientes() {
        
        // Set value for columns in the table.
        clnNrohistoria.setCellValueFactory(new PropertyValueFactory("nrohistoria"));
        clnNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        clnApellido.setCellValueFactory(new PropertyValueFactory("apellido"));
        clnEdad.setCellValueFactory(new PropertyValueFactory("edad"));
        
        pacienteTable.setItems(filteredData);
        
        // Add a listener to the textfield that is used to search patients.
        txtBuscarPaciente.textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
                updateFilteredData();
            }
        });
    }
    
    /**
     * Ini
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set item behavior.
        btnRegistrarCita.setDisable(true);
        
        // Ini table patients.
        this.inicializarTablaPacientes();
        
        // Add listener to the table.
        final ObservableList<PacienteBean> tablePacienteSel = pacienteTable.getSelectionModel().getSelectedItems();
        tablePacienteSel.addListener(selectorTablePaciente);
        
    } 
    
    /**
     * Filter results.
     */
    private void updateFilteredData() {
        filteredData.clear();

        for (PacienteBean p : masterData) {
            if (matchesFilter(p)) {
                filteredData.add(p);
            }
        }

        reapplyTableSortOrder();
    }
    
    /**
     * Match input value.
     * 
     * @param person
     * @return 
     */
    private boolean matchesFilter(PacienteBean person) {
        String filterString = txtBuscarPaciente.getText();
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
     * Reapply table sort order
     */
    private void reapplyTableSortOrder() {
        pacienteTable.getSortOrder().addAll(clnNombre);
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
     * Get selected patient data.
     * 
     * @return 
     */
    public PacienteBean getTablaPacienteSeleccionado() {
        
        if (pacienteTable != null) {
            
            List<PacienteBean> tabla = 
                    pacienteTable.getSelectionModel().getSelectedItems();
            
            if(tabla.size() == 1) {
                final PacienteBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    /**
     * Set selected patient data on textfields.
     */
    private void ponerPacienteSeleccionado() {
        
        paciente = getTablaPacienteSeleccionado();
        posicionPacienteEnTabla = masterData.indexOf(paciente);

        if (paciente != null) {

            btnRegistrarCita.setDisable(false);
        }
    }
    
    // FXML methods.
    
    /**
     * Go to add new appointment GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void goToRegistrarCita(ActionEvent evento) throws IOException {
        
        // Check if there any selected patient.
        if (paciente.getCodigo() != 0) {
            
            Node node=(Node) evento.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/cqr/fxml/Citas.fxml"));     
            Parent root = (Parent)fxmlLoader.load();        
            
            CitasController citasController = fxmlLoader.<CitasController>getController();
            citasController.initData(paciente);
            
            Scene scene = new Scene(root); 
            stage.setScene(scene);    
            stage.show(); 

        } else {
            Dialogs.create().owner(null)
                .title("Registro de citas.")
                .masthead(null)
                .message( "Debes seleccionar un paciente para registrar la cita.")
                .showWarning();
        }
    }
    
    /**
     * Return to main menu GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void goToRegresar(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

