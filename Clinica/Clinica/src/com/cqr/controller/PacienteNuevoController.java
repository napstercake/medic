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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.xml.transform.TransformerException;
import jxl.write.WriteException;
import org.controlsfx.dialog.Dialogs;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @class This class allows to the user creates a new record for patient.
 */
public class PacienteNuevoController implements Initializable {
    
    // FXML variables.
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtCelular;
    @FXML private TextField txtFijo;
    @FXML private TextField txtFecNac;
    @FXML private TextField txtEdad;
    @FXML private TextField txtPeso;
    @FXML private TextField txtTalla;
    @FXML private TextField txtNrohistoria;
    
    // FXML Methods.
    
    /**
     * Save patient.
     * 
     * @param evento
     * @throws WriteException
     * @throws SAXException
     * @throws TransformerException 
     */
    @FXML
    private void guardarPaciente(ActionEvent evento) throws WriteException, SAXException, TransformerException {
        
        // Validate if the textfields are empty.
        if ( txtNombre.getText().trim().equalsIgnoreCase("") 
                || txtApellido.getText().trim().equalsIgnoreCase("")
                || txtEdad.getText().trim().equalsIgnoreCase("")
                || txtNrohistoria.getText().trim().equalsIgnoreCase("")) {
            
            Dialogs.create().owner(null)
                .title("Datos imcompletos.")
                .masthead(null)
                .message( "Debes completar los datos principales. Nombre, apellido, edad y nro. de historia.")
                .showWarning();
            
        }  else {
            
            // Add a new patient without an email account.
            if (txtCorreo.getText().trim().equalsIgnoreCase("")) {
                
                PacienteBean pacienteBean = new PacienteBean();
                    
                // Set data on patientBean object.
                pacienteBean.setNrohistoria(txtNrohistoria.getText());
                pacienteBean.setNombre(txtNombre.getText());
                pacienteBean.setApellido(txtApellido.getText());
                pacienteBean.setDireccion(txtDireccion.getText().trim());
                pacienteBean.setCorreo(txtCorreo.getText().trim());
                pacienteBean.setCelular(txtCelular.getText().trim());
                pacienteBean.setFijo(txtFijo.getText().trim());
                pacienteBean.setFechaNacimiento(txtFecNac.getText().trim());
                pacienteBean.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                pacienteBean.setPeso(txtPeso.getText().trim());
                pacienteBean.setTalla(txtTalla.getText().trim());

                IXMLParserPaciente xmlParser = new XMLParserPaciente();

                if (xmlParser.savePaciente(pacienteBean)) {

                    Dialogs.create().owner(null)
                        .title("Registro de pacientes.")
                        .masthead(null)
                        .message( "Paciente guardado satisfactoriamente.")
                        .showInformation();

                    // Clean textfields values.
                    txtNrohistoria.setText("");
                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtDireccion.setText("");
                    txtCorreo.setText("");
                    txtCelular.setText("");
                    txtFijo.setText("");
                    txtFecNac.setText("");
                    txtEdad.setText("");
                    txtPeso.setText("");
                    txtTalla.setText("");

                } else {

                    Dialogs.create().owner(null)
                        .title("Error.")
                        .masthead(null)
                        .message( "El paciente no se ha guardado. Intentelo nuevamente.")
                        .showWarning();
                }
                
            } else {
                
                // Add a new patient with an email account.
                
                Util util = new Util();
            
                if (util.isValidEmailAddress(txtCorreo.getText().trim())) {

                    PacienteBean pacienteBean = new PacienteBean();
                    
                    // Set data on patientBean object.
                    pacienteBean.setNrohistoria(txtNrohistoria.getText().trim());
                    pacienteBean.setNombre(txtNombre.getText());
                    pacienteBean.setApellido(txtApellido.getText());
                    pacienteBean.setDireccion(txtDireccion.getText().trim());
                    pacienteBean.setCorreo(txtCorreo.getText().trim());
                    pacienteBean.setCelular(txtCelular.getText().trim());
                    pacienteBean.setFijo(txtFijo.getText().trim());
                    pacienteBean.setFechaNacimiento(txtFecNac.getText().trim());
                    pacienteBean.setEdad(Integer.parseInt(txtEdad.getText().trim()));
                    pacienteBean.setPeso(txtPeso.getText().trim());
                    pacienteBean.setTalla(txtTalla.getText().trim());

                    IXMLParserPaciente xmlParser = new XMLParserPaciente();

                    // Validates if the patient data was succesfully recorded.
                    if (xmlParser.savePaciente(pacienteBean)) {

                        Dialogs.create().owner(null)
                            .title("Registro de pacientes.")
                            .masthead(null)
                            .message( "Paciente guardado correctamente.")
                            .showInformation();

                        // Clean textfields.
                        txtNrohistoria.setText("");
                        txtNombre.setText("");
                        txtApellido.setText("");
                        txtDireccion.setText("");
                        txtCorreo.setText("");
                        txtCelular.setText("");
                        txtFijo.setText("");
                        txtFecNac.setText("");
                        txtEdad.setText("");
                        txtPeso.setText("");
                        txtTalla.setText("");

                    } else {

                        Dialogs.create().owner(null)
                            .title("Error.")
                            .masthead(null)
                            .message( "No se pudo guardar los datos. Intentelo nuevamente.")
                            .showWarning();
                    }

                } else {
                    
                    // Worng email.
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
     * Return to patient GUI.
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
        
        // Cleaning textfield.
        txtNrohistoria.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtCorreo.setText("");
        txtCelular.setText("");
        txtFijo.setText("");
        txtFecNac.setText("");
        txtEdad.setText("");
        txtPeso.setText("");
        txtTalla.setText("");
        
    }    
}
