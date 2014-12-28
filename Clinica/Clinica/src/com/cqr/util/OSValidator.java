/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.util;

/**
 *
 * @author ricardogonzales
 */
public class OSValidator {
    
    private String OS = System.getProperty("os.name").toLowerCase();
    
    public OSValidator() {
    }
    
    public String getOs() {
        String os = "undefined";
        
        if (isWindows()) {
            os = "windows";
        } else if (isMac()) {
            os = "mac";
        } else if (isUnix()) {
            os = "linux";
        } else if (isSolaris()) {
            os = "solaris";
        } else {
            os = "not supported";
        }
        
        return os;
    }
    
    private boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    private boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    private boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }
}
