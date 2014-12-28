/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.interfaz;

import com.cqr.bean.CitaBean;
import java.util.List;

/**
 *
 * @author ricardogonzales
 */
public interface IXMLParserCita {
    
    /**
     * 
     * @param date
     * @param time
     * @return 
     */
    public boolean validateCita(String date, String time);
    
    /**
     * 
     * @param fecha
     * @return 
     */
    public List<CitaBean> getCitasByDate(String fecha);
    
    /**
     * 
     * @return 
     */
    public List<CitaBean> readXMLCitas();
    
    /**
     * 
     * @param citaBean
     * @return 
     */
    public Boolean saveCita(CitaBean citaBean);
    
    /**
     * 
     * @param citaBean
     * @return 
     */
    public boolean updateCita(CitaBean citaBean);
    
    /**
     * 
     * @param codigoCita
     * @return 
     */
    public boolean deleteCita(String codigoCita);
    
}
