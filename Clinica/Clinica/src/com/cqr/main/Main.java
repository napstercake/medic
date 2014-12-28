/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ricardogonzales
 */
public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Centro Quiropractico RAY");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        
        System.out.println("Entrega 7 - Mac. 25/12/2014");
        System.out.println("- 1. Validar solo 3 citas al dia (check)");
        System.out.println("- 2. Poner el numero de historia para impresion");
        System.out.println("- 3. Cambiar a 3 columnas la hoja de impresion.");
        
        launch(args);
    }
}
