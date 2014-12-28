/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.controller;

import com.cqr.constants.Constants;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.mail.BeanMail;
import com.cqr.mail.UtilEmail;
import com.cqr.xml.XMLParserPaciente;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author ricardogonzales
 */
public class EmailingController implements Initializable {
    
    @FXML private TextField txtTituloCorreo;
    @FXML private TextArea taMensajeCorreo;
    @FXML private Button btnEnviar;
    @FXML private Button btnRegresar;
    
    private static UtilEmail utilEmail = new UtilEmail();
    
    @FXML
    private void regresarClinica(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/EmailingMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   
    @FXML
    private void enviarEmailing(ActionEvent evento) {
        
        System.out.println("Enviar emailing!");
        btnEnviar.setDisable(true);
        btnRegresar.setDisable(true);
        
        if (txtTituloCorreo.getText().trim().equalsIgnoreCase("") ||
               taMensajeCorreo.getText().trim().equalsIgnoreCase("") ) {
            
            Dialogs.create().owner(null)
                .title("Datos imcompletos.")
                .masthead(null)
                .message( "Debes completar los datos.")
                .showWarning();
             
        } else {
            
            IXMLParserPaciente xmlParser = new XMLParserPaciente();
            List<String> emailList = xmlParser.getPacientesEmail();
            
            if (!emailList.isEmpty()) {
                
                /**
                * Enviar email.
                */
                BeanMail beanMail = new BeanMail();

                beanMail.setUsuario(Constants.SENDER_EMAIL);
                beanMail.setPassword(Constants.SENDER_PWD);
                beanMail.setServidor(Constants.SERVER_GMAIL);
                beanMail.setCuentaEmailRemitente(Constants.SENDER_EMAIL);

                beanMail.setCuentaEmailDestinatario(Constants.EMAIL_DEST);
                beanMail.setCuentaEmailDestinatario_Cc(Constants.EMAIL_DEST_CC);
                beanMail.setCuentaEmailDestinatario_Bcc(Constants.EMAIL_DEST_BC);

                beanMail.setPuerto(Constants.PORT);
                beanMail.setProtocolo(Constants.PROTOCOL);
                beanMail.setContentType(Constants.CONTENT_TYPE);
                beanMail.setNombreAdjunto(Constants.IMAGE_ATTACH);
                beanMail.setAsuntoMensaje(txtTituloCorreo.getText());
                beanMail.setCuerpoMensaje(taMensajeCorreo.getText());
                beanMail.setCopia(1);

                if (utilEmail.enviarEmailHtmlAdjunto(beanMail, true, emailList)) {
                    
                    Dialogs.create().owner(null)
                    .title("Emailing")
                    .masthead(null)
                    .message( "Enviado satisfactoriamente.")
                    .showInformation();
                    
                } else {
                    
                    Dialogs.create().owner(null)
                    .title("Error al enviar.")
                    .masthead(null)
                    .message( "No se pudo enviar el mail. Verifique la autenticacion de la cuenta: centroquiropracticorayinfo@gmail.com entrando a su bandeja de entrada desde http://gmail.com")
                    .showWarning();
                    
                }
                
                
                
                txtTituloCorreo.setText("");
                taMensajeCorreo.setText(""); 

            } else {
                
                Dialogs.create().owner(null)
                    .title("Data incompleta.")
                    .masthead(null)
                    .message( "No se han encontrado registros de mails en la base de datos.")
                    .showWarning();
            }
        }
        
        btnEnviar.setDisable(false);
        btnRegresar.setDisable(false);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
