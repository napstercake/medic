/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

import com.cqr.util.Util;
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
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * 
 * @class Controller with main options.
 */
public class ClinicaController implements Initializable {
    
    // FXML Variables.
    @FXML private Button btnCitas;
    @FXML private Button btnPacientes;
    @FXML private Button btnEmailing;
    
    // FXML Methods.
    
    /**
     * Show appointments.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void showCitas(ActionEvent evento) throws IOException {
        
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/CitasMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Show patients list.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void showPacientes(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Paciente.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * Show emailing UI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void showEmailing(ActionEvent evento) throws IOException {
        
        // Check internet connection.
        if (Util.isInternetReachable()) {
            
            Node node=(Node) evento.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/EmailingMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        
        } else {
            
            Dialogs.create().owner(null)
                .title("Sin internet.")
                .masthead(null)
                .message( "No dispones de una conexion a internet.")
                .showWarning();
        }
    }
    
    /**
     * Ini
     * 
     * @param url
     * @param resources 
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // TODO
    }    
    
}
