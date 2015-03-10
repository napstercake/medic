/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.constants;

import java.io.File;

/**
 * @class This class stores all constant data.
 */
public class Constants {
    
    // Files places
    public static final String RESOURCES = "resources" + File.separator;
    public static final String DATA = "data" + File.separator;
    
    // Files location
    public static final String XLS_PACIENTES = DATA + "Pacientes.xls";
    public static final String XLS_COPY_PATH = DATA + "output.xls";
    public static final String LOGO_PATH = RESOURCES + "logo.jpg";
    public static final String XML_PACIENTES_PATH = DATA + "pacientes.xml";
    public static final String XML_CITAS_PATH = DATA + "citas.xml";
    
    // Email data
    public static final String SENDER_EMAIL = "centroquiropracticorayinfo@gmail.com";
    public static final String SENDER_PWD = "aSwdQQ@Sf";
    public static final String SERVER_GMAIL = "smtp.gmail.com";
    public static final String PORT = "587";
    public static final String PROTOCOL = "smtp";
    public static final String CONTENT_TYPE = "text/html";
    public static final String IMAGE_ATTACH = "logo.jpg";
    public static final String EMAIL_DEST = "juanmiguelchang@gmail.com";
    public static final String EMAIL_DEST_CC = "chatomiguel_18@hotmail.com";
    public static final String EMAIL_DEST_BC = "romina.lsz@hotmail.com";
    
    // Appointment validation Quantity
    public static final int MAX_CITAS = 4;
    
}
