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
 * @class ricardogonzales
 */
public class PacienteController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    // FXML Methods.
    
    /**
     * Show "Add new patient" GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void showNuevoPaciente(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/PacienteNuevo.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Show "Patient search" GUI
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML 
    private void showBuscarPaciente(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/PacienteBuscar.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Show list of patients.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML 
    private void showListadoPacientes(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/PacienteListado.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Return to Main menu.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML 
    private void backToClinica(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

