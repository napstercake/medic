/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @class This class is used to handle the emailing main GUI.
 */
public class EmailingMainController implements Initializable {
    
    /**
     * Constructor.
     */
    public EmailingMainController() {
        
    }
    
    // FXML Methods
    
    /**
     * Return to main GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    public void regresarEmailing(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();        
    }
    
    /**
     * Send email to the complete list of patients.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    public void enviarATodos(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Emailing.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();   
    }
    
    /**
     * Send email to selected patients.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void enviarPacSeleccionados(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/EmailingPacSelec.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

