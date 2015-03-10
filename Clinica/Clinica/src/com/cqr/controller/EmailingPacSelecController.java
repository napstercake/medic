/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

import com.cqr.bean.PacienteBean;
import com.cqr.constants.Constants;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.mail.BeanMail;
import com.cqr.mail.UtilEmail;
import com.cqr.xml.XMLParserPaciente;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @class 
 */
public class EmailingPacSelecController implements Initializable {
    
    // FXML variables.
    @FXML private TextField txtEnviar;
    @FXML private TextField txtAsunto;
    @FXML private TextArea taMensaje;
    @FXML private Button btnEnviarEmail;
    @FXML private TableView tblCorreos;
    @FXML private TableColumn clnPaciente;
    @FXML private TableColumn clnCorreo;
    @FXML private TextField txtPara;
    
    // Class variables.
    private ObservableList filteredData = FXCollections.observableArrayList();
    private ObservableList<PacienteBean> pacienteObservable = FXCollections.observableArrayList();
    private String listTextField = "";
    private boolean blnEmailAction;
    
    /**
     * Constructor
     */
    public EmailingPacSelecController() {
        blnEmailAction = false;
    }
    
    /**
     * This is method is a listener that works when the user
     * select any row on the table. 
     */
    private final ListChangeListener<PacienteBean> selectorTablePaciente =
            new ListChangeListener<PacienteBean>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends PacienteBean> c) {
            ponerPacienteSeleccionado();
        }
    };
    
    /**
     * Set selected patient data on textfields.
     */
    private void ponerPacienteSeleccionado() {
        
        final PacienteBean pacienteBean = getTablaPacienteSeleccionado();
        
        if (pacienteBean != null && (!pacienteBean.getCorreo().equalsIgnoreCase(""))) {
            
            listTextField += pacienteBean.getCorreo() + ",";
            txtEnviar.setText(listTextField);
            btnEnviarEmail.setDisable(false);
        }
    }
    
    /**
     * Get selected patient data.
     * 
     * @return 
     */
    public PacienteBean getTablaPacienteSeleccionado() {
        
        if (tblCorreos != null) {
            List<PacienteBean> tabla =
                    tblCorreos.getSelectionModel().getSelectedItems();
            
            if (tabla.size() == 1) {
                final PacienteBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    // FXML Methods.
    
    /**
     * Return to main emailing GUI.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    public void regresarMain(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/EmailingMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Send emailing method.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    public void enviarEmailing(ActionEvent evento) throws IOException {
        
        // Validate data to send email.
        if (txtEnviar.getText().trim().equalsIgnoreCase("") ||
           taMensaje.getText().equalsIgnoreCase("") ) {

            Dialogs.create().owner(null)
                .title("Datos imcompletos.")
                .masthead(null)
                .message( "Debes completar los datos.")
                .showWarning();

        } else {

            List<String> emailList = getArrayEmail();

            if (!emailList.isEmpty()) {

                UtilEmail utilEmail = new UtilEmail();

                // Send email
                BeanMail beanMail = new BeanMail();
                
                // Set mandatories values for emaling process.
                beanMail.setUsuario(Constants.SENDER_EMAIL);
                beanMail.setPassword(Constants.SENDER_PWD);
                beanMail.setServidor(Constants.SERVER_GMAIL);
                beanMail.setCuentaEmailRemitente(Constants.SENDER_EMAIL);

                beanMail.setCuentaEmailDestinatario(Constants.EMAIL_DEST);
                beanMail.setCuentaEmailDestinatario_Cc(Constants.EMAIL_DEST_CC);
                beanMail.setCuentaEmailDestinatario_Bcc(Constants.EMAIL_DEST_BC);

                beanMail.setPuerto(Constants.PORT);
                beanMail.setProtocolo(Constants.PROTOCOL);
                beanMail.setContentType(Constants.CONTENT_TYPE);
                beanMail.setNombreAdjunto(Constants.IMAGE_ATTACH);
                beanMail.setAsuntoMensaje(txtAsunto.getText());
                beanMail.setCuerpoMensaje(taMensaje.getText());
                beanMail.setCopia(1);

                // Validate if email was succesfully sended.
                if (utilEmail.enviarEmailHtmlAdjunto(beanMail, true, emailList)) {

                    // Show succesfully message.
                    Dialogs.create().owner(null)
                    .title("Emailing")
                    .masthead(null)
                    .message( "Enviado satisfactoriamente.")
                    .showInformation();

                    btnEnviarEmail.setDisable(true);

                } else {

                    // Show error message.
                    Dialogs.create().owner(null)
                    .title("Error al enviar.")
                    .masthead(null)
                    .message( "No se pudo enviar el mail. Verifique la autenticacion de la cuenta: centroquiropracticorayinfo@gmail.com entrando a su bandeja de entrada desde http://gmail.com")
                    .showWarning();

                }

                // Cleaning up the textfields.
                txtAsunto.setText("");
                taMensaje.setText(""); 
                txtEnviar.setText("");
                txtPara.setText("");
                
                // Set items behavior.
                btnEnviarEmail.setDisable(true);

            } else {

                // Show for incomplete data.
                Dialogs.create().owner(null)
                    .title("Data incompleta.")
                    .masthead(null)
                    .message( "No se han encontrado registros de mails en la base de datos.")
                    .showWarning();
            }
        }
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Ini
        this.inicializarTablaPacientes();
        
        final ObservableList<PacienteBean> tablaPacienteSel =
                tblCorreos.getSelectionModel().getSelectedItems();
        
        tablaPacienteSel.addListener(selectorTablePaciente);
        
        IXMLParserPaciente xmlParser = new XMLParserPaciente();
        
        try {
            
            List<PacienteBean> arregloPacientes = new ArrayList();
            arregloPacientes = xmlParser.readXMLPacientes();
            
            for (int i=0; i<arregloPacientes.size(); i++) {
                
                if (!arregloPacientes.get(i).getCorreo().trim().equalsIgnoreCase("")) {
                    PacienteBean pacienteTable = new PacienteBean();
                    pacienteTable.setNombre(arregloPacientes.get(i).getNombre() + " " + arregloPacientes.get(i).getApellido());
                    pacienteTable.setCorreo(arregloPacientes.get(i).getCorreo());
                    pacienteObservable.add(pacienteTable);
                }
            }
            
            filteredData.addAll(pacienteObservable);
            
            // Add listener to list.
            pacienteObservable.addListener(new ListChangeListener() {

                @Override
                public void onChanged(ListChangeListener.Change c) {
                    updateFilteredData();
                }
            });
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Set items behavior.
        btnEnviarEmail.setDisable(true);
    }    
    
    /**
     * Format the list of emails. 
     * Example: "someperson@mail.com, anotherperson@mail.com"
     * 
     * @return items.
     */
    private List<String> getArrayEmail() {
        
        List<String> items = Arrays.asList(
                txtEnviar.getText().trim().
                        substring(0,txtEnviar.getText().trim().length()-1).
                        split("\\s*,\\s*"));
        return items;
    }
    
    /**
     * Ini patients table.
     */
    private void inicializarTablaPacientes() {
        
        clnPaciente.setCellValueFactory(new PropertyValueFactory("nombre"));
        clnCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        
        tblCorreos.setItems(filteredData);
        tblCorreos.setPlaceholder(new Label("No hay pacientes registrados"));
        
        txtPara.textProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                updateFilteredData();
            }
        });
    }
    
    /**
     * Filtered data
     */
    private void updateFilteredData() {
        
        filteredData.clear();
        
        for (PacienteBean p: pacienteObservable) {
            if(matchesFilter(p)) {
                filteredData.add(p);
            }
        }
        reapplyTableSortOrder();
        
    }
    
    /**
     * 
     * @param person
     * @return 
     */
    private boolean matchesFilter(PacienteBean person) {
        
        String filterString = txtPara.getText();
        
        if (filterString == null || filterString.isEmpty()) {
            return true;
        }
        
        String lowerCaseFilterString = filterString.toLowerCase();
        
        if (person.getCorreo().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        } else if (person.getNombre().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Sort rows on table by email.
     */
    private void reapplyTableSortOrder() {
        tblCorreos.getSortOrder().addAll(clnCorreo);
    }
}

