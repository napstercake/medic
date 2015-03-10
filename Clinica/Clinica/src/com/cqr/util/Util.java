/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.util;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Util Class.
 * 
 * @class This class contains general methods to help the application in some
 * necessary validations.
 */
public class Util {
    
    /**
     * Validate if there any internet connection.
     * 
     * @return boolean.
     */
    public static boolean isInternetReachable() {
        try {
            
            URL url = new URL("http://www.java.sun.com");
            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.getContent();
            
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    /**
     * Check is the email address is corrected
     * 
     * @param email
     * @return boolean.
     */
    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    
    /**
     * 
     * This is not working properly TODO
     * 
     * @param source
     * @param dest
     * @throws IOException 
     */
    public void copyFileUsingJava7Files(File source, File dest)
		throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
}
