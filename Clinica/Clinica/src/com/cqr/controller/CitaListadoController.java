/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.controller;

import com.cqr.bean.CitaBean;
import com.cqr.interfaz.IXMLParserCita;
import com.cqr.interfaz.IXMLParserPaciente;
import com.cqr.xml.XMLParserCita;
import com.cqr.xml.XMLParserPaciente;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.dialog.Dialogs;

/**
 * This class work directly with the list of appointments in the
 * GUI.
 *
 * @class FXML Controller class
 */
public class CitaListadoController implements Initializable {
    
    // FXML variables from GUI
    @FXML private VBox vBoxDateHolder;
    @FXML private AnchorPane AnchorPane;
    @FXML private Label lblFecha;
    @FXML private Button btnEliminarCita;
    @FXML private Button btnImprimir;
    @FXML private TableView<CitaBean> tableListadoCitas;
    @FXML private TableColumn clnCodigo;
    @FXML private TableColumn clnNombrePaciente;
    @FXML private TableColumn clnFecha;
    @FXML private TableColumn clnHora;
    @FXML private TableColumn clnEstado;
    
    ObservableList<CitaBean> citasObservable;
    
    private int posicionCitaEnTabla;
    private String codigoCitaSeleccionada;
    
    List<CitaBean> arregloCitas = null;
    
	String[] rowsHours = {
		"-","09:00 AM","09:15 AM","09:30 AM","09:45 AM",
            "10:00 AM","10:15 AM","10:30 AM","10:45 AM",
            "11:00 AM","11:15 AM","11:30 AM","11:45 AM",
            "12:00 M","12:15 M","12:30 M","12:45 M",
            "01:00 PM","03:00 PM","03:15 PM","03:30 PM","03:45 PM",
            "04:00 PM","04:15 PM","04:30 PM","04:45 PM","05:00 PM",
            "05:15 PM","05:30 PM","05:45 PM","06:00 PM","06:15 PM",
            "06:30 PM","06:45 PM","07:00 PM","07:15 PM","07:30 PM",
            "07:45 PM","08:00 PM"
        };
	
    int POINTS_PER_INCH = 72;
    
    /**
     * Constructor
     */
    public CitaListadoController(){
    }
    
    /**
     * This is method is a listener that works when the user
     * select any row on the table. 
     */
    private final ListChangeListener<CitaBean> selectorTableCita =
            new ListChangeListener<CitaBean>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends CitaBean> c) {
            ponerCitaSeleccionada();
        }
                
    };
    
    /**
     * This method load data from an appointment.
     */
    private void ponerCitaSeleccionada() {
        
        final CitaBean citaBean = getTablaCitaSeleccionado();
        posicionCitaEnTabla = citasObservable.indexOf(citaBean);
        
        if (citaBean != null) {
            
            // Poner datos en textfields.
            codigoCitaSeleccionada = citaBean.getCodigo().toString();
            btnEliminarCita.setDisable(false);
        }
    }
    
    /**
     * 
     * @return AppointmentBean Object
     */
    public CitaBean getTablaCitaSeleccionado() {
        
        if(tableListadoCitas != null) {
            
            List<CitaBean> tabla = 
                    tableListadoCitas.getSelectionModel().getSelectedItems();
            
            if(tabla.size() == 1) {
                final CitaBean competicionSeleccionada = tabla.get(0);
                return competicionSeleccionada;
            }
        }
        return null;
    }
    
    /**
     * Ini table appointments.
     */
    private void inicializarTablaCitas() {
        
        clnCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        clnNombrePaciente.setCellValueFactory(new PropertyValueFactory("nombrepaciente"));
        clnFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        clnHora.setCellValueFactory(new PropertyValueFactory("hora"));
        clnEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        
        clnEstado.setCellFactory(new Callback<TableColumn<CitaBean, String>, TableCell<CitaBean, String>>() {
        
        @Override
        public TableCell<CitaBean, String> call(TableColumn<CitaBean, String> personStringTableColumn ) {
            
            return new TableCell<CitaBean, String>() {
                @Override
                protected void updateItem(String name, boolean empty) {
                    super.updateItem(name, empty);
                    
                    if (!empty) {
                    
                        // Setting style for each category
                        if (name.toLowerCase().equalsIgnoreCase("p. mantenimiento")) {
                            getStyleClass().add("pacMantenimiento");
                        } else if (name.toLowerCase().equalsIgnoreCase("p. nuevo")) {
                            getStyleClass().add("pacNuevo");
                        } else if (name.toLowerCase().equalsIgnoreCase("p. reporte")) {
                            getStyleClass().add("pacReporte");
                        }
                        setText(name);
                    
                    }
                }
            };
        }
    });
        
    citasObservable = FXCollections.observableArrayList();
    tableListadoCitas.setItems(citasObservable);
    tableListadoCitas.setPlaceholder(new Label("No hay citas registradas para la fecha"));
        
    }
    
    /**
     * This method get a list of Patients registered for 
     * an appointment by hour.
     * 
     * @param hour
     * @return List
     */
    private List<CitaBean> getPacientesByHour(String hour) {
        
        List<CitaBean> pacienteOnHour = new ArrayList();
        
        for (int i=0; i<arregloCitas.size(); i++) {
            
            if (arregloCitas.get(i).getHora().equalsIgnoreCase(hour)) {
                CitaBean cb = new CitaBean();
                cb.setCodigopaciente(arregloCitas.get(i).getCodigopaciente());
                cb.setEstado(arregloCitas.get(i).getEstado());
                pacienteOnHour.add(cb);
                //pacienteOnHour.add(xmlPP.getNombrePacientePorCodigo(arregloCitas.get(i).getCodigopaciente().toString()));
            }
        }
        
        return pacienteOnHour;
    }
   
    /**
     * Get boundaries for the printed paper
     * 
     * @param printJob
     * @return 
     */
    static private PageFormat getMin(PrinterJob printJob) {
        PageFormat pf0 = printJob.defaultPage();
        PageFormat pf1 = (PageFormat) pf0.clone();
        Paper p = pf0.getPaper();
        p.setImageableArea(5, 1, pf0.getWidth(), pf0.getHeight());
        pf1.setPaper(p);
        PageFormat pf2 = printJob.validatePage(pf1);
        return pf2;
    }
    
    /**
     * @class This class is used to print and set main values for 
     * the appointments list.
     */
    public class IntroPage implements Printable {
	
       @Override
	public int print(Graphics g, PageFormat pageFormat, int page) {
	    
            Graphics2D g2d = (Graphics2D) g;
            
	    g2d.translate(pageFormat.getImageableX(), pageFormat
	          .getImageableY());
            
	    g2d.setPaint(Color.black);
  
	    Font titleFont = new Font("helvetica", Font.PLAIN, 8);
	    g2d.setFont(titleFont);
	    
	    FontMetrics fontMetrics = g2d.getFontMetrics();
            g2d.setStroke(new BasicStroke(1));
            
            IXMLParserCita xmlParserCita = new XMLParserCita();
            
            // Page Width: 593.8
	      
	    String txtHeader = "";
            String txtValue = "";
	    
            int starColumn = 0;
            int starRow = 10;
            
            int columnWidth = 50;
            int rowsCount = 39;
            int colsCount = 3;
            int rowsHeigth = 16;
            
            Double doubleValue = (pageFormat.getImageableWidth() - columnWidth) / colsCount;
            int calcColumn = doubleValue.intValue();
            calcColumn = calcColumn - 5;
            
            List<CitaBean> listToPrint = null;
            IXMLParserPaciente xmlPP = new XMLParserPaciente();
                  
            // Rows.
            for (int iRow=0; iRow<rowsCount; iRow++) {
                
                if (iRow != 0) {
                    
                    // Get pacientes code for the first/second.. row.
                    // Get pacientes code for the first/second.. hour time.
                    // Remember the hour array starts with "-"
                    listToPrint = new ArrayList<CitaBean>();
                    listToPrint = getPacientesByHour(rowsHours[iRow]);
                    
                    if (listToPrint.isEmpty()){
                    }
                } 
                
                //Cols.
                for (int iCol=0; iCol<=colsCount; iCol++) {
                    
                    //Print header.
                    if (iRow==0) {
                        
                        if (iCol == 0) {
                            // Printing hour;
                            txtHeader = "Hora";
                            
                        } else if (iCol == 1) {
                            starColumn = 50;
                            
                            // Printing (history) name and lastname;
                            columnWidth = calcColumn;
                            txtHeader = "(Historia) Nombre y Apellido";
                            
                        } else {
                            
                            // Add new value for name/lastname columns.
                            columnWidth = calcColumn;
                            starColumn += calcColumn;
                        }
                        
                        // Print vertical lines.
                        Rectangle2D.Double verticalLine = new Rectangle2D.Double( 
                              starColumn, 
                              0, 
                              (columnWidth), 
                              pageFormat.getImageableHeight());
                        g2d.draw(verticalLine);
                    
                        // Print "Hora" y "Nombres y Apellido";
                        g2d.drawString(txtHeader, (starColumn+3), 10);
                    }
                    
                    // Print everything else. // iRow=1, iRow=2, iRow=3...  
                    else {
                        
                        String estado = "";
                        
                        if (iCol==0) {
                            // Print hour value
                            txtValue = rowsHours[iRow];
                            starColumn = 0;
                            
                        } else { 
                            
                            if (iCol==1) {
                                starColumn = 50;
                            } else {
                                starColumn += calcColumn;
                            }
                            
                            if (!listToPrint.isEmpty()) {
                                
                                if ((listToPrint.size() - (iCol-1)) > 0) {
                                    int intValue = listToPrint.get(iCol-1).getCodigopaciente();
                                    txtValue = xmlPP.getNamePacienteByCode(intValue+"");
                                    
                                    if (listToPrint.get(iCol-1).getEstado().equalsIgnoreCase("P. Nuevo")) {
                                        //estado = "P. Nuevo";
                                        g2d.setColor(Color.red);
                                    } else if (listToPrint.get(iCol-1).getEstado().equalsIgnoreCase("P. Reporte")) {
                                        //estado = "P. Reporte";
                                        g2d.setColor(Color.green);
                                    } else if (listToPrint.get(iCol-1).getEstado().equalsIgnoreCase("P. Mantenimiento")) {
                                        //estado = "P. Mantenimiento";
                                        g2d.setColor(Color.blue);
                                    } else {
                                        //estado = "P. Plan";
                                        g2d.setColor(Color.black);
                                    }
                                        
                                    //txtValue = listToPrint.get(iCol-1).toString();
                                } else {
                                    txtValue = "";
                                }
                                
                            } else {
                                txtValue = "";
                            }
                        }
                        
                        g2d.drawString(txtValue, (starColumn+3) ,(rowsHeigth + (rowsHeigth*iRow)) - 3 );
                        g2d.setColor(Color.black);
                        
                    }
                }
                
                // Print horizontal lines.
                g2d.drawLine(1, 
                        (rowsHeigth + ( (rowsHeigth)*iRow) ), 
                        (50 + (calcColumn*3)), //40
                        (rowsHeigth + ((rowsHeigth)*iRow)));
            }
            return (PAGE_EXISTS);
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblFecha.setText(LocalDate.now().toString());
        btnEliminarCita.setDisable(true);
        
        final ObservableList<CitaBean> tablaCitaSel = 
                tableListadoCitas.getSelectionModel().getSelectedItems();
        tablaCitaSel.addListener(selectorTableCita);
        
        // Date picker
        final DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setOnAction(event -> {
            LocalDate date = datePicker.getValue();
            
            // Populate table by date
            llenarTablaPorFecha(date);
        });
        
        // Adding the date picker
        vBoxDateHolder.getChildren().add(datePicker);
        
        // Populate table by this date
        llenarTablaPorFecha(LocalDate.now());
        
    }
    
    /**
     * This method populates the table by
     * any given date.
     * 
     * @param date 
     */
    public void llenarTablaPorFecha(LocalDate date) {
        
        // Ini appointment table.
        this.inicializarTablaCitas();
        
        arregloCitas = new ArrayList();
        IXMLParserCita xmlParser = new XMLParserCita();
        IXMLParserPaciente xmlParserPaciente = new XMLParserPaciente();
        
        try {
            
            arregloCitas = xmlParser.getCitasByDate(date.toString());
            
            for (int i=0; i<arregloCitas.size(); i++) {
                
                CitaBean citaTable = new CitaBean();
                
                citaTable.setCodigo(arregloCitas.get(i).getCodigo());
                citaTable.setNombrepaciente(xmlParserPaciente.getNamePacienteByCode(arregloCitas.get(i).getCodigopaciente().toString()));
                citaTable.setFecha(arregloCitas.get(i).getFecha());
                citaTable.setHora(arregloCitas.get(i).getHora());
                citaTable.setEstado(arregloCitas.get(i).getEstado());
                
                citasObservable.add(citaTable);
            }
            
            if(citasObservable.isEmpty()){
                btnImprimir.setDisable(true);
                btnEliminarCita.setDisable(true);
            } else {
                btnImprimir.setDisable(false);
            }
            
            tableListadoCitas.getColumnResizePolicy();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    // FXML Methods
    
    /**
     * Get back to Appointment main interface.
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void regresarCitaMain(ActionEvent evento) throws IOException {
        Node node=(Node) evento.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/cqr/fxml/CitasMain.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    /**
     * This method fired the groups of methods to start printing.
     * 
     * @param evento
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException 
     */
    @FXML
    private void imprimirCitas(ActionEvent evento) throws 
            IOException, NoSuchMethodException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException {
        
        // Check if the list is empty
        if (citasObservable.isEmpty()){
            Dialogs.create().owner(null)
                    .title("Error.")
                    .masthead(null)
                    .message( "Error al imprimir la cita. No existen registros para imprimir.")
                    .showWarning();
        } else {
        
            try {
                
                // Page format
                PrinterJob printJob = PrinterJob.getPrinterJob();
                
                Book book = new Book();
                book.append(new IntroPage(), getMin(printJob));
                
                PageFormat documentPageFormat = new PageFormat();
                documentPageFormat.setOrientation(PageFormat.PORTRAIT);
                
                printJob.setPageable(book);
                
                // Printing task
                if (printJob.printDialog()) {
                    try {
                      printJob.print();
                    } catch (Exception PrintException) {
                      PrintException.printStackTrace();
                    }
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Delete appointment
     * 
     * @param evento
     * @throws IOException 
     */
    @FXML
    private void eliminarCita(ActionEvent evento) throws IOException {
        
        if (codigoCitaSeleccionada.equalsIgnoreCase("") || codigoCitaSeleccionada == null) {
            
            Dialogs.create().owner(null)
                    .title("Error al eliminar cita.")
                    .masthead(null)
                    .message( "Debe seleccionar una cita para eliminarla.")
                    .showWarning();
            
        } else {
            
            // Delete from XML file
            IXMLParserCita xmlPC = new XMLParserCita();
        
            if (xmlPC.deleteCita(codigoCitaSeleccionada)) {
            
                Dialogs.create().owner(null)
                        .title("Cita eliminada")
                        .masthead(null)
                        .message( "Cita eliminada satisfactoriamente.")
                        .showInformation();

                // Delete from the table at the UI
                citasObservable.remove(posicionCitaEnTabla);
            } else {
                Dialogs.create().owner(null)
                    .title("Error.")
                    .masthead(null)
                    .message( "Error al eliminar la cita. Vuelva a intentarlo nuevamente.")
                    .showWarning();
            }
        }
    }
}
