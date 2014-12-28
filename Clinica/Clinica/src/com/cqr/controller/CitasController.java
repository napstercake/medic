/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.controller;

import com.cqr.bean.CitaBean;
import com.cqr.bean.PacienteBean;
import com.cqr.interfaz.IXMLParserCita;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.xml.XMLParserCita;
import com.cqr.xml.XMLParserPaciente;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author ricardogonzales
 */
public class CitasController implements Initializable {
    
    @FXML private Button btnGuardarCita;
    @FXML private Button btnRegresar;
    
    @FXML private VBox dateHolder;
    @FXML private VBox vBoxToggle;
    @FXML private Label lblPaciente;
    
    @FXML private Label lblReservarCita;
    @FXML private Label lblFecha;
    @FXML private Label lblHora;
    @FXML private Label lblNotas;
    
    @FXML private ComboBox cbxHora;
    @FXML private TextArea taNotas;
    
    @FXML private TableView<CitaBean> tblCitas;
    @FXML private TableColumn clnNombrePaciente;
    @FXML private TableColumn clnHora;
    @FXML private TableColumn clnEstado;
    @FXML private TableColumn clnNotas;
    ObservableList<CitaBean> citasObservable;
    
    PacienteBean pacienteBean;
    
    @FXML private Label lblNombreApellido;
    @FXML private Label lblEdad;
    
    String toggleSelected;
    LocalDate date;
    
    public CitasController() {
    }
    
    private final ListChangeListener<CitaBean> selectorTableCita =
           new ListChangeListener<CitaBean>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends CitaBean> change) {
        }
    };
    
    public CitaBean getTablaCitaSeleccionada() {
        
        if (tblCitas != null) {
            
            List<CitaBean> tabla = 
                    tblCitas.getSelectionModel().getSelectedItems();
            
            if(tabla.size() == 1) {
                final CitaBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    private void inicializarTablaCitas(){
       
        clnNombrePaciente.setCellValueFactory(new PropertyValueFactory("nombrepaciente"));
        clnHora.setCellValueFactory(new PropertyValueFactory("hora"));
        clnEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        clnNotas.setCellValueFactory(new PropertyValueFactory("notas"));
        
        citasObservable = FXCollections.observableArrayList();
        tblCitas.setItems(citasObservable);
        tblCitas.setPlaceholder(new Label("No hay citas registradas para hoy"));
    }
    
    @FXML
    private void guardarCita(ActionEvent evento) throws IOException {
        
        if (cbxHora.getValue().toString().equalsIgnoreCase("Seleccionar hora")) {
        
            Dialogs.create().owner(null)
                .title("Datos incompletos.")
                .masthead(null)
                .message( "Debe seleccionar una hora para la cita.")
                .showWarning();
            
        } else {
            
            IXMLParserCita xmlPC = new XMLParserCita();
            
            if (date == null){
                date = LocalDate.now();   
            }
            
            if (xmlPC.validateCita(date.toString(), cbxHora.getValue().toString())) {
                
                CitaBean citaToSave = new CitaBean();
                
                citaToSave.setFecha(date.toString());
                citaToSave.setHora(cbxHora.getValue().toString());
                citaToSave.setEstado(toggleSelected);
                citaToSave.setNotas(taNotas.getText());
                citaToSave.setCodigopaciente(pacienteBean.getCodigo());
                
                if (xmlPC.saveCita(citaToSave)) {
                
                    Dialogs.create().owner(null)
                        .title("Cita registrada")
                        .masthead(null)
                        .message( "Cita registrada satisfactoriamente.")
                        .showInformation();

                    Node node=(Node) evento.getSource();
                    Stage stage=(Stage) node.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/CitasMain.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    Dialogs.create().owner(null)
                        .title("Error al guardar cita.")
                        .masthead(null)
                        .message( "No se pudo registrar. Intentalo nuevamente.")
                        .showWarning();
                }
                
                
                
            } else {
                
                Dialogs.create().owner(null)
                    .title("Error al guardar cita.")
                    .masthead(null)
                    .message( "Ya se han registrado 3 citas en el horario seleccionado.")
                    .showWarning();
                
            }

        }
    }
    
    @FXML
    private void regresarClinica(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/ReservarCita.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void initData(PacienteBean pacienteBeanX) {
        
        if (pacienteBeanX != null) {
            lblNombreApellido.setText(pacienteBeanX.getNombre() + " " + pacienteBeanX.getApellido());
            lblEdad.setText(pacienteBeanX.getEdad().toString());
            
            pacienteBean = pacienteBeanX;
            
        } else {
            
            
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cbxHora.setDisable(true);
        taNotas.setDisable(true);
        btnGuardarCita.setDisable(true);
        
        lblReservarCita.setDisable(true);
        lblFecha.setDisable(true);
        lblHora.setDisable(true);
        lblNotas.setDisable(true);
        
        tblCitas.setDisable(true);
        
        /**
         * Datepicker.
         */
        // NAPSTER
        final DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(event -> {
            date = datePicker.getValue();
        });
        dateHolder.getChildren().add(datePicker); 
        
        datePicker.setDisable(true); 
        
        /* vBoxToggle implementation. */
        
        final ToggleButton tb1 = new ToggleButton("P. Nuevo");
        final ToggleButton tb2 = new ToggleButton("P. Reporte");
        final ToggleButton tb3 = new ToggleButton("P. Plan");
        final ToggleButton tb4 = new ToggleButton("P. Mantenimiento");
        
        tb1.setStyle("-fx-base: red;");
        tb2.setStyle("-fx-base: green;");
        tb3.setStyle("-fx-base: black");
        tb4.setStyle("-fx-base: blue");
        
        ToggleGroup group = new ToggleGroup();
        
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        tb3.setToggleGroup(group);
        tb4.setToggleGroup(group);
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override public void changed(ObservableValue<? extends Toggle> 
                    observable, Toggle oldValue, Toggle selectedToggle) {
                
                if(selectedToggle!=null) {
                    
                    toggleSelected = ((ToggleButton) selectedToggle).getText();
                    
                    if (toggleSelected.equalsIgnoreCase("P. Nuevo")) {
                        lblPaciente.setTextFill(Color.RED);
                    } else if(toggleSelected.equalsIgnoreCase("P. Reporte")) {
                        lblPaciente.setTextFill(Color.GREEN);
                    } else if(toggleSelected.equalsIgnoreCase("P. Plan")) {
                        lblPaciente.setTextFill(Color.BLACK);
                    } else if(toggleSelected.equalsIgnoreCase("P. Mantenimiento")) {
                        lblPaciente.setTextFill(Color.BLUE);
                    } 
                    
                    lblPaciente.setText(((ToggleButton) selectedToggle).getText());
                    cbxHora.setDisable(false);
                    taNotas.setDisable(false);
                    btnGuardarCita.setDisable(false);
                    datePicker.setDisable(false);
                    
                    lblReservarCita.setDisable(false);
                    lblFecha.setDisable(false);
                    lblHora.setDisable(false);
                    lblNotas.setDisable(false);
                    
                    tblCitas.setDisable(false);
                    
                }
                else {
                    lblPaciente.setText("...");
                }
            }
        });
        
        //group.selectToggle(tb1);
        
        GridPane.setConstraints(tb1,0,0);
        GridPane.setConstraints(tb2,1,0);
        GridPane.setConstraints(tb3,2,0);
        GridPane.setConstraints(tb4,3,0);
        
        GridPane grid = new GridPane();
        grid.setVgap(20);
        grid.setHgap(10);
        
        vBoxToggle.getChildren().add(grid); 
        grid.getChildren().addAll(tb1, tb2, tb3, tb4);
        
        
        /* Tabla */
        this.inicializarTablaCitas();
        
        final ObservableList<CitaBean> tablaCitaSel = 
                tblCitas.getSelectionModel().getSelectedItems();
        tablaCitaSel.addListener(selectorTableCita);
        
        List<CitaBean> arregloCitas = new ArrayList();
        IXMLParserCita xmlParser = new XMLParserCita();
        IXMLParserPaciente xmlParserPaciente = new XMLParserPaciente();
        
        try {
            
            arregloCitas = xmlParser.getCitasByDate(datePicker.getValue().toString());
            
            for (int i=0; i<arregloCitas.size(); i++) {
                
                CitaBean citaTable = new CitaBean();
                citaTable.setNombrepaciente(xmlParserPaciente.getNamePacienteByCode(arregloCitas.get(i).getCodigopaciente().toString()));
                citaTable.setHora(arregloCitas.get(i).getHora());
                citaTable.setEstado(arregloCitas.get(i).getEstado());
                citaTable.setNotas(arregloCitas.get(i).getNotas());
                
                citasObservable.add(citaTable);
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        /* ComboBox.*/
        cbxHora.getItems().addAll(
            "09:00 AM","09:15 AM","09:30 AM","09:45 AM",
            "10:00 AM","10:15 AM","10:30 AM","10:45 AM",
            "11:00 AM","11:15 AM","11:30 AM","11:45 AM",
            "12:00 M","12:15 M","12:30 M","12:45 M",
            "01:00 PM","03:00 PM","03:15 PM","03:30 PM","03:45 PM",
            "04:00 PM","04:15 PM","04:30 PM","04:45 PM","05:00 PM",
            "05:15 PM","05:30 PM","05:45 PM","06:00 PM","06:15 PM",
            "06:30 PM","06:45 PM","07:00 PM","07:15 PM","07:30 PM",
            "07:45 PM","08:00 PM"
        );
       
        cbxHora.setValue("Seleccionar hora");
        
    }
}

