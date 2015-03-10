/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.util;

/**
 *
 * @class This class is used to validate the OS.
 */
public class OSValidator {
    
    private String OS = System.getProperty("os.name").toLowerCase();
    
    /**
     * Constructor.
     */
    public OSValidator() {
    }
    
    /**
     * Get OS.
     * 
     * @return String
     */
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
    
    /**
     * Get windows flag.
     * 
     * @return  boolean.
     */
    private boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    /**
     * Get osx flag.
     * 
     * @return boolean.
     */
    private boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    /**
     * Get unix flag.
     * 
     * @return boolean.
     */
    private boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
    }

    /**
     * Get solaris flag.
     * 
     * @return boolean.
     */
    private boolean isSolaris() {
        return (OS.indexOf("sunos") >= 0);
    }
}
