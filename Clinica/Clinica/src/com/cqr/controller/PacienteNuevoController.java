/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author ricardogonzales
 */
public class PacienteNuevoController implements Initializable {
    
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
    
    @FXML private Button btnGuardarPaciente;

    @FXML
    private void guardarPaciente(ActionEvent evento) throws WriteException, SAXException, TransformerException {
        
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
            
            if (txtCorreo.getText().trim().equalsIgnoreCase("")) {
                
                PacienteBean pacienteBean = new PacienteBean();
                    
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
                
                Util util = new Util();
            
                if (util.isValidEmailAddress(txtCorreo.getText().trim())) {

                    PacienteBean pacienteBean = new PacienteBean();

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

                    if (xmlParser.savePaciente(pacienteBean)) {

                        Dialogs.create().owner(null)
                            .title("Registro de pacientes.")
                            .masthead(null)
                            .message( "Paciente guardado correctamente.")
                            .showInformation();

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
                    Dialogs.create().owner(null)
                    .title("Email incorrecto.")
                    .masthead(null)
                    .message( "Debes ingresar un mail valido.")
                    .showWarning();
                }
                
            }
            
            
        }
    }
    
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
