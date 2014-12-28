/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.controller;

import com.cqr.bean.PacienteBean;
import com.cqr.constants.Constants;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.util.OSValidator;
import com.cqr.util.Util;
import com.cqr.util.WriteExcel;
import com.cqr.xml.XMLParserPaciente;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
 * @author ricardogonzales
 */
public class PacienteListadoController implements Initializable {
    
    @FXML private TableView tblPaciente;
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
    
    @FXML private TextField txtNrohistoriaApellido;
    
    @FXML private Button btnExportarExcel;
    @FXML private Button btnEliminarPacSeleccionado;
    
    private ObservableList filteredData = FXCollections.observableArrayList();
    private ObservableList<PacienteBean> pacienteObservable = FXCollections.observableArrayList();
    
    List<PacienteBean> arregloPacientes = new ArrayList();
    
    private int posicionPacienteEnTabla;
    private String codigoPacSel;
    
    /**
     * 
     */
    public PacienteListadoController() {
        
    }
    
    /**
     * 
     */
    private final ListChangeListener<PacienteBean> selectorTablePaciente =
           new ListChangeListener<PacienteBean>() {
               
                @Override
                public void onChanged(ListChangeListener.Change<? extends PacienteBean> c) {
                    ponerPacienteSeleccionado();
                }
           };
    
    /**
     * Ver backup para inverse.
     */
    private void ponerPacienteSeleccionado() {
        
        final PacienteBean pacienteBean = getTablaPacienteSeleccionado();
        posicionPacienteEnTabla = pacienteObservable.indexOf(pacienteBean);
        
        if (pacienteBean != null) {
            
            codigoPacSel = pacienteBean.getCodigo().toString().trim();
            
            btnEliminarPacSeleccionado.setDisable(false);
        }
        
    }
    
    /**
     * 
     * @return 
     */
    public PacienteBean getTablaPacienteSeleccionado() {
        
        if (tblPaciente != null) {
            
            List<PacienteBean> tabla = 
                    tblPaciente.getSelectionModel().getSelectedItems();
            
            if(tabla.size() == 1) {
                final PacienteBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    /**
     * 
     */
    private void inicializarTablaPacientes() {
        
        btnEliminarPacSeleccionado.setDisable(true);
        
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
        
        tblPaciente.setItems(filteredData);
        tblPaciente.setPlaceholder(new Label("No hay pacientes registrados"));
        
        txtNrohistoriaApellido.textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                updateFilteredData();
            }
            
        });
        
    }
    
    /**
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
    
    @FXML
    private void eliminarPacSeleccionado(ActionEvent evento) throws IOException {
        
        if (codigoPacSel.equalsIgnoreCase("") || codigoPacSel == null) {
            
            Dialogs.create().owner(null)
                    .title("Error al eliminar paciente.")
                    .masthead(null)
                    .message( "Debe seleccionar un paciente para eliminarlo.")
                    .showWarning();
            
        } else {
            
            //Eliminar desde el xml
            IXMLParserPaciente xmlPC = new XMLParserPaciente();
        
            if (xmlPC.deletePaciente(codigoPacSel)) {
            
                Dialogs.create().owner(null)
                        .title("Paciente eliminado")
                        .masthead(null)
                        .message( "Paciente eliminado correctamente.")
                        .showInformation();

                // Eliminar de la tabla.
                pacienteObservable.remove(posicionPacienteEnTabla);
                txtNrohistoriaApellido.setText("");
                btnEliminarPacSeleccionado.setDisable(true);
                
            } else {
                Dialogs.create().owner(null)
                    .title("Error.")
                    .masthead(null)
                    .message( "Error al eliminar al paciente. Vuelva a intentarlo nuevamente.")
                    .showWarning();
            }
        }
        
    }
    
    /**
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void exportarExcel(ActionEvent evento) throws IOException, WriteException {
        
        //pacientes
        WriteExcel writeExcel = new WriteExcel(arregloPacientes);
        writeExcel.setOutputFile(Constants.XLS_PACIENTES);
        
        if (writeExcel.write()) {
            
            OSValidator osValidator = new OSValidator();
            String os = osValidator.getOs().trim();
            
            Util util = new Util();
            
            File source = new File(Constants.XLS_PACIENTES);
            File dest = null;
            
            String language = Locale.getDefault().getLanguage();
            String escritorio = "Escritorio";
            
            if (language.equalsIgnoreCase("en") || language.equalsIgnoreCase("EN")) {
                    escritorio = "Desktop";
            }
            
            if (os.equalsIgnoreCase("windows")){
                
                dest = new File(System.getProperty("user.home") + File.separator + escritorio + File.separator + "Pacientes.xls");
                
            } else if (os.equalsIgnoreCase("mac")) {
                
                dest = new File(System.getProperty("user.home") + File.separator + escritorio + File.separator + "Pacientes.xls");
                
            }
            
            Path p = Paths.get(System.getProperty("user.home") + File.separator + escritorio + File.separator + "Pacientes.xls");
            
            try {
                Files.deleteIfExists(p);
                
            } catch (IOException x) {
                
            }
            
            util.copyFileUsingJava7Files(source, dest);
            
            Dialogs.create().owner(null)
                .title("El documento se creo correctamente")
                .masthead(null)
                .message( "El documento se creo correctamente y esta localizado en el escritorio.")
                .showInformation();
            
        } else {
            
            Dialogs.create().owner(null)
                .title("Error al exportar datos")
                .masthead(null)
                .message( "Hubo un error al exportar los datos. Intentalo nuevamente.")
                .showInformation();
        }
        
    }
    
    /**
     * 
     * @param source
     * @param dest
     * @throws IOException 
     */
    private static void copyFileUsingJava7Files(File source, File dest)
		throws IOException {
	Files.copy(source.toPath(), dest.toPath());
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.inicializarTablaPacientes();
        
        final ObservableList<PacienteBean> tablaPacienteSel = 
                tblPaciente.getSelectionModel().getSelectedItems();
        tablaPacienteSel.addListener(selectorTablePaciente);
        
        IXMLParserPaciente xmlParser = new XMLParserPaciente();
        
        try {
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
            
            pacienteObservable.addListener(new ListChangeListener() {

                @Override
                public void onChanged(ListChangeListener.Change c) {
                    updateFilteredData();
                }
            
            });
            
            if (pacienteObservable.isEmpty()) {
                btnExportarExcel.setDisable(true);
            } else {
                btnExportarExcel.setDisable(false);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    /**
     * 
     */
    private void updateFilteredData() {
        filteredData.clear();
        
        for (PacienteBean p: pacienteObservable) {
            if(matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        reapplyTableSortOrder();
    }
    
    /**
     * 
     * @param person
     * @return 
     */
    private boolean matchesFilter(PacienteBean person) {
        
        String filterString = txtNrohistoriaApellido.getText();
        if (filterString == null || filterString.isEmpty()) {
            return true;
        }

        String lowerCaseFilterString = filterString.toLowerCase();
        
        if (person.getNrohistoria().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getApellido().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }

        return false; // Does not match
    }
    
    private void reapplyTableSortOrder() {
        tblPaciente.getSortOrder().addAll(clnApellido);
    }
    
}

