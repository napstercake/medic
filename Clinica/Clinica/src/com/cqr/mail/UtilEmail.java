/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
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
 * @class This class contains all main methods for the send email process.
 */
public class UtilEmail {
    
    // Class variables.
    private BeanMail beanMail;
    List<String> emailList;
    
    /**
     * Constructor.
     */
    public UtilEmail() {
    }
    
    /**
     * Set the values for constructor.
     * 
     * @param beanMail
     * @param emailList 
     */
    public UtilEmail(BeanMail beanMail, List<String> emailList) {
        this.beanMail = beanMail;
        this.emailList = emailList;
    }
    
    /**
     * This methods is used to sent an email with some file attached.
     * 
     * @param beanMail
     * @param autenticacion
     * @param emailList
     * @return boolean.
     */
    public boolean enviarEmailHtmlAdjunto(BeanMail beanMail, boolean autenticacion, List<String> emailList) {
        
        boolean blnResultado = false;
        
        // Validate authenticationl.
        if (autenticacion == true) {
            this.enviarEmailConAutenticacion(beanMail,emailList);
            blnResultado = true;
            
        } else {
            blnResultado = false;
        }
        
        return blnResultado;
    }
    
    /**
     * This method is used to send a simple email.
     * 
     * @param beanMail
     * @param emailList 
     */
    public void enviarEmailConAutenticacion(BeanMail beanMail, List<String> emailList) {
        
        try {
            
            // Validate if the object is not null.
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
