/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author ricardogonzales
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
     * This is not working properly
     * @param source
     * @param dest
     * @throws IOException 
     */
    public void copyFileUsingJava7Files(File source, File dest)
		throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }
    
    
    
}
