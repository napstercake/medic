/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
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
    
    // FXML Variables.
    @FXML private TextField txtTituloCorreo;
    @FXML private TextArea taMensajeCorreo;
    @FXML private Button btnEnviar;
    @FXML private Button btnRegresar;
    
    private static UtilEmail utilEmail = new UtilEmail();
    
    // FXML Methods.
    
    /**
     * Return to 
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void regresarClinica(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/EmailingMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   
    /**
     * Send e-mail.
     * 
     * @param evento 
     */
    @FXML
    private void enviarEmailing(ActionEvent evento) {
        
        // Set items behavior.
        btnEnviar.setDisable(true);
        btnRegresar.setDisable(true);
        
        // Validates empty fields.
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
            
            // Check if the email list is empty.
            if (!emailList.isEmpty()) {
                
                // Send email.
                BeanMail beanMail = new BeanMail();

                // Set mandatories values for emaling process.
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
                
                // Validate if email was succesfully sended.
                if (utilEmail.enviarEmailHtmlAdjunto(beanMail, true, emailList)) {
                    
                    // Show succesfully message.
                    Dialogs.create().owner(null)
                    .title("Emailing")
                    .masthead(null)
                    .message( "Enviado satisfactoriamente.")
                    .showInformation();
                    
                } else {
                    
                    // Show error message.
                    Dialogs.create().owner(null)
                    .title("Error al enviar.")
                    .masthead(null)
                    .message( "No se pudo enviar el mail. Verifique la autenticacion de la cuenta: centroquiropracticorayinfo@gmail.com entrando a su bandeja de entrada desde http://gmail.com")
                    .showWarning();
                    
                }
                
                // Cleaning up the textfields.
                txtTituloCorreo.setText("");
                taMensajeCorreo.setText(""); 

            } else {
                
                // Show for incomplete data.
                Dialogs.create().owner(null)
                    .title("Data incompleta.")
                    .masthead(null)
                    .message( "No se han encontrado registros de mails en la base de datos.")
                    .showWarning();
            }
        }
        
        // Set items behavior.
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
