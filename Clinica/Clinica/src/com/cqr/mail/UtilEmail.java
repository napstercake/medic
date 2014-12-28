/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.mail;

import com.cqr.constants.Constants;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author ricardogonzales
 */
public class UtilEmail {
    
    private BeanMail beanMail;
    List<String> emailList;
    
    public UtilEmail() {
    }
    
    public UtilEmail(BeanMail beanMail, List<String> emailList) {
        this.beanMail = beanMail;
        this.emailList = emailList;
    }
    
    public boolean enviarEmailHtmlAdjunto(BeanMail beanMail, boolean autenticacion, List<String> emailList) {
        
        boolean blnResultado = false;
        
        if (autenticacion == true) {
            this.enviarEmailConAutenticacion(beanMail,emailList);
            blnResultado = true;
            
        } else {
            blnResultado = false;
        }
        
        return blnResultado;
    }
    
    public void enviarEmailConAutenticacion(BeanMail beanMail, List<String> emailList) {
        
        try {
            
            if (beanMail != null) {
                this.beanMail = beanMail;
                
                Properties propiedades = new Properties();
                propiedades.setProperty("mail.smtp.host", this.beanMail.getServidor());
                
                //-------------------------- MANEJO PROXY --------------------------//
                /*
                propiedades.setProperty( "proxySet",       "true" );
                propiedades.setProperty( "http.proxyHost", "14.240.1.16" );
                propiedades.setProperty( "http.proxyPort", "80"  );
                */
                //------------------------------------------------------------------//
                
                /**
                 * TLS o SSL
                 */
                propiedades.setProperty("mail.smtp.starttls.enable", "true");
                
                propiedades.setProperty("mail.smtp.port", this.beanMail.getPuerto());
                propiedades.setProperty("mail.smtp.user", this.beanMail.getCuentaEmailRemitente());
                propiedades.setProperty("mail.smtp.auth", "true");
                
                Session sesion = Session.getDefaultInstance(propiedades, null);
                sesion.setDebug(false);
                
                String mensajeHtml = this.beanMail.getCuerpoMensaje();
                
                MimeMultipart multiPartPrincipal = new MimeMultipart("mixed");
                MimeMultipart multiPartContenido = new MimeMultipart("alternative");
                
                BodyPart textoHtml = new MimeBodyPart();
                textoHtml.setContent(mensajeHtml, this.beanMail.getContentType());
                multiPartContenido.addBodyPart(textoHtml);
                
                BodyPart textoPlano = new MimeBodyPart();
                textoPlano.setContent(mensajeHtml, "text/plain");
                multiPartContenido.addBodyPart(textoPlano);
                
                BodyPart adjunto = new MimeBodyPart();
                File archivoImagen = new File(Constants.LOGO_PATH);
                //File archivoImagen = new File( "recursos\\Publicidad.jpg" );
                FileDataSource dataSourceImagen = new FileDataSource(archivoImagen);
                DataHandler manejador = new DataHandler(dataSourceImagen);
                
                adjunto.setDataHandler(manejador);
                adjunto.setFileName(this.beanMail.getNombreAdjunto());
                multiPartPrincipal.addBodyPart(adjunto);
                
                BodyPart contenidoAdjunto = new MimeBodyPart();
                contenidoAdjunto.setContent(multiPartContenido);
                
                multiPartPrincipal.addBodyPart(contenidoAdjunto);
                
                MimeMessage mensaje = new MimeMessage(sesion);
                mensaje.setFrom(new InternetAddress(this.beanMail.getCuentaEmailRemitente()));
                
                InternetAddress[] addressTo = new InternetAddress[emailList.size()];
                
                for (int i = 0; i < emailList.size(); i++) {
                    addressTo[i] = new InternetAddress(emailList.get(i));
                }
                
//                mensaje.addRecipient(Message.RecipientType.TO, 
//                            new InternetAddress(addressTo));
                
                
//              if (this.beanMail.getCuentaEmailDestinatario() != null) {
//                  mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(this.beanMail.getCuentaEmailDestinatario()));
//              }
//                if (this.beanMail.getCuentaEmailDestinatario_Cc() != null) {
//                    mensaje.addRecipient(Message.RecipientType.CC, new InternetAddress(this.beanMail.getCuentaEmailDestinatario_Cc()));
//                }
//                if (this.beanMail.getCuentaEmailDestinatario_Bcc() != null) {
//                    mensaje.addRecipient(Message.RecipientType.BCC, new InternetAddress(this.beanMail.getCuentaEmailDestinatario_Bcc()));
//                }
                
                mensaje.setSubject(this.beanMail.getAsuntoMensaje());
                mensaje.setContent(multiPartPrincipal);
                mensaje.setSentDate(new Date());
                
                for (int i=0; i<this.beanMail.getCopia(); i++) {
                    
                    Transport transportador = sesion.getTransport(this.beanMail.getProtocolo());
                    transportador.connect(this.beanMail.getUsuario(), this.beanMail.getPassword());
                    transportador.sendMessage(mensaje, addressTo);
                    transportador.close();
                    
                }
                
            }
            
        } catch (Exception ex) {
            System.out.println("Error al enviar el email.");
            ex.printStackTrace();
        }
    }
}
