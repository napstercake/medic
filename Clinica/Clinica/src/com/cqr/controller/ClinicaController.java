/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author ricardogonzales
 */
public class ClinicaController implements Initializable {
    
    @FXML private Button btnCitas;
    @FXML private Button btnPacientes;
    @FXML private Button btnEmailing;
    
    @FXML
    private void showCitas(ActionEvent evento) throws IOException {
        
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/CitasMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
       
    }
    @FXML
    private void showPacientes(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Paciente.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    @FXML
    private void showEmailing(ActionEvent evento) throws IOException {
        
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
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        // TODO
    }    
    
}
