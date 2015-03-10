/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class.
 * 
 * @class Main class for the whole application.
 */
public class Main extends Application{

    /**
     * Prepare stage.
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/Clinica.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Centro Quiropractico RAY");
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * Main.
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        System.out.println("Entrega 7 - Mac. 25/12/2014");
        System.out.println("- 1. Validar solo 3 citas al dia (check)");
        System.out.println("- 2. Poner el numero de historia para impresion (check)");
        System.out.println("- 3. Cambiar a 3 columnas la hoja de impresion. (check)");
        
        System.out.println("Posible enhacements - Marzo, 07-2015 [Pending]");
        System.out.println("- 1. Add properties files");
        System.out.println("- 2. Make jUnit Test");
        System.out.println("- 3. Cleaning up the code to avoid redoundant methods.");
        
        launch(args);
    }
}
