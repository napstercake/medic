/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.mail;

import java.io.Serializable;
import java.util.Vector;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ricardogonzales
 */
public class BeanMail implements Serializable {
    
    private int id = 0;
    private String usuario = "";
    private String password = "";
    private String cuentaEmailRemitente = "";
    private String cuentaEmailDestinatario = "";
    private String cuentaEmailDestinatario_Cc = "";
    private String cuentaEmailDestinatario_Bcc = "";	
    private String direccion = "";
    private String servidor = "";
    private String protocolo = "";
    private String puerto = "";
    private String asuntoMensaje = "";
    private String cuerpoMensaje = "";
    private String nombreAdjunto = "";
    private int    copia = 0;
    private String contentType = "";
    private boolean     requiereAuthenticacion = false;
    private boolean 	requiereConexionSegura = false;
    private Vector<String> vectorEmailsReceptores = null;
    private Vector<String> vectorEmailsReceptores_CC = null;
    private Vector<String> vectorEmailsReceptores_BCC = null;
    private MimeMultipart  objMultiParte = null; 
    
    /**
     * Constructor
     */
    public BeanMail() {
        
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getCuentaEmailRemitente() {
        return cuentaEmailRemitente;
    }

    public String getCuentaEmailDestinatario() {
        return cuentaEmailDestinatario;
    }

    public String getCuentaEmailDestinatario_Cc() {
        return cuentaEmailDestinatario_Cc;
    }

    public String getCuentaEmailDestinatario_Bcc() {
        return cuentaEmailDestinatario_Bcc;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getServidor() {
        return servidor;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public String getPuerto() {
        return puerto;
    }

    public String getAsuntoMensaje() {
        return asuntoMensaje;
    }

    public String getCuerpoMensaje() {
        return cuerpoMensaje;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public int getCopia() {
        return copia;
    }

    public String getContentType() {
        return contentType;
    }

    public boolean isRequiereAuthenticacion() {
        return requiereAuthenticacion;
    }

    public boolean isRequiereConexionSegura() {
        return requiereConexionSegura;
    }

    public Vector<String> getVectorEmailsReceptores() {
        return vectorEmailsReceptores;
    }

    public Vector<String> getVectorEmailsReceptores_CC() {
        return vectorEmailsReceptores_CC;
    }

    public Vector<String> getVectorEmailsReceptores_BCC() {
        return vectorEmailsReceptores_BCC;
    }

    public MimeMultipart getObjMultiParte() {
        return objMultiParte;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCuentaEmailRemitente(String cuentaEmailRemitente) {
        this.cuentaEmailRemitente = cuentaEmailRemitente;
    }

    public void setCuentaEmailDestinatario(String cuentaEmailDestinatario) {
        this.cuentaEmailDestinatario = cuentaEmailDestinatario;
    }

    public void setCuentaEmailDestinatario_Cc(String cuentaEmailDestinatario_Cc) {
        this.cuentaEmailDestinatario_Cc = cuentaEmailDestinatario_Cc;
    }

    public void setCuentaEmailDestinatario_Bcc(String cuentaEmailDestinatario_Bcc) {
        this.cuentaEmailDestinatario_Bcc = cuentaEmailDestinatario_Bcc;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public void setAsuntoMensaje(String asuntoMensaje) {
        this.asuntoMensaje = asuntoMensaje;
    }

    public void setCuerpoMensaje(String cuerpoMensaje) {
        this.cuerpoMensaje = cuerpoMensaje;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public void setCopia(int copia) {
        this.copia = copia;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setRequiereAuthenticacion(boolean requiereAuthenticacion) {
        this.requiereAuthenticacion = requiereAuthenticacion;
    }

    public void setRequiereConexionSegura(boolean requiereConexionSegura) {
        this.requiereConexionSegura = requiereConexionSegura;
    }

    public void setVectorEmailsReceptores(Vector<String> vectorEmailsReceptores) {
        this.vectorEmailsReceptores = vectorEmailsReceptores;
    }

    public void setVectorEmailsReceptores_CC(Vector<String> vectorEmailsReceptores_CC) {
        this.vectorEmailsReceptores_CC = vectorEmailsReceptores_CC;
    }

    public void setVectorEmailsReceptores_BCC(Vector<String> vectorEmailsReceptores_BCC) {
        this.vectorEmailsReceptores_BCC = vectorEmailsReceptores_BCC;
    }

    public void setObjMultiParte(MimeMultipart objMultiParte) {
        this.objMultiParte = objMultiParte;
    }
    
}
