/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.interfaz;

import com.cqr.bean.CitaBean;
import java.util.List;

/**
 *
 * @class Interface class with access methods to handler the
 * XML File.
 */
public interface IXMLParserCita {
    
    /**
     * Validate appointment.
     * 
     * @param date
     * @param time
     * @return boolean
     */
    public boolean validateCita(String date, String time);
    
    /**
     * Get appointments by date.
     * 
     * @param fecha
     * @return List
     */
    public List<CitaBean> getCitasByDate(String fecha);
    
    /**
     * Read appointments from XML file.
     * 
     * @return List
     */
    public List<CitaBean> readXMLCitas();
    
    /**
     * Save appointment.
     * 
     * @param citaBean
     * @return boolean.
     */
    public Boolean saveCita(CitaBean citaBean);
    
    /**
     * Update appointment.
     * 
     * @param citaBean
     * @return boolean.
     */
    public boolean updateCita(CitaBean citaBean);
    
    /**
     * Delete appointment record.
     * 
     * @param codigoCita
     * @return boolean.
     */
    public boolean deleteCita(String codigoCita);
    
}
