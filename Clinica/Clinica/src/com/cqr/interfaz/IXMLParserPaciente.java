/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.interfaz;

import com.cqr.bean.PacienteBean;
import java.util.List;

/**
 *
 * @class Interface class with access methods to handler the
 * XML File.
 * 
 */
public interface IXMLParserPaciente {
    
    /**
     * Get patient's name by code.
     * 
     * @param pacienteCodigo
     * @return String
     */
    public String getNamePacienteByCode(String pacienteCodigo);
    
    /**
     * Get patient's email.
     * 
     * @return List
     */
    public List<String> getPacientesEmail();
    
    /**
     * Parse XML Patient with email.
     * 
     * @return List
     */
    public List<PacienteBean> readXMLPacientesWMail();
    
    /**
     * Read patient's records from XML File.
     * 
     * @return List
     */
    public List<PacienteBean> readXMLPacientes();
    
    /**
     * Update patient.
     * 
     * @param pacienteBean
     * @return boolean
     */
    public boolean updatePaciente(PacienteBean pacienteBean);
    
    /**
     * Save patient record on XML file and table.
     * 
     * @param pacienteBean
     * @return boolean
     */
    public boolean savePaciente(PacienteBean pacienteBean);
    
    /**
     * Delete patient from XML file and table.
     * 
     * @param codigoPaciente
     * @return 
     */
    public boolean deletePaciente(String codigoPaciente);
    
}
