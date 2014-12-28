/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.interfaz;

import com.cqr.bean.PacienteBean;
import java.util.List;

/**
 *
 * @author ricardogonzales
 * 
 */
public interface IXMLParserPaciente {
    
    /**
     * 
     * @param pacienteCodigo
     * @return 
     */
    public String getNamePacienteByCode(String pacienteCodigo);
    
    /**
     * 
     * @return 
     */
    public List<String> getPacientesEmail();
    
    /**
     * 
     * @return 
     */
    public List<PacienteBean> readXMLPacientesWMail();
    
    /**
     * 
     * @return 
     */
    public List<PacienteBean> readXMLPacientes();
    
    /**
     * 
     * @param pacienteBean
     * @return 
     */
    public boolean updatePaciente(PacienteBean pacienteBean);
    
    /**
     * 
     * @param pacienteBean
     * @return 
     */
    public boolean savePaciente(PacienteBean pacienteBean);
    
    /**
     * 
     * @param codigoPaciente
     * @return 
     */
    public boolean deletePaciente(String codigoPaciente);
    
}
