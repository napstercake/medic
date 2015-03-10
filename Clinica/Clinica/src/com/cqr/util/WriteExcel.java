/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.util;

import com.cqr.bean.PacienteBean;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Write Excel class.
 * 
 * @class This class is used to write and excel document.
 */
public class WriteExcel {
    
    // Class variables.
    private WritableCellFormat timesBoldUnderline;
    private WritableCellFormat times;
    private String inputFile;
    List<PacienteBean> arregloPacientes = new ArrayList();
    
    /**
     * Write excel.
     * 
     * @param arregloPacientes 
     */
    public WriteExcel(List<PacienteBean> arregloPacientes) {
        this.arregloPacientes = arregloPacientes;
    }
    
    /**
     * Set output file.
     * 
     * @param inputFile 
     */
    public void setOutputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Write Excel file.
     * 
     * @return boolean
     * @throws IOException
     * @throws WriteException 
     */
    public boolean write() throws IOException, WriteException {
        
        boolean blnResultado = false;
        
        try {
            
            File file = new File(inputFile);
            WorkbookSettings wbSettings = new WorkbookSettings();

            wbSettings.setLocale(new Locale("en", "EN"));

            WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
            workbook.createSheet("Report", 0);
            WritableSheet excelSheet = workbook.getSheet(0);
            createLabel(excelSheet);
            createContent(excelSheet);

            workbook.write();
            workbook.close();
            
            blnResultado = true;
            
        } catch (Exception e){
            e.printStackTrace();
            blnResultado = false;
        }
        
        return blnResultado;
        
    }

    /**
     * Create label.
     * 
     * @param sheet
     * @throws WriteException 
     */
    private void createLabel(WritableSheet sheet)
      throws WriteException {
        
        // Lets create a times font
        WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
        // Define the cell format
        times = new WritableCellFormat(times10pt);
        // Lets automatically wrap the cells
        times.setWrap(true);

        // Create create a bold font with unterlines
        WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
            UnderlineStyle.SINGLE);
        timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
        // Lets automatically wrap the cells
        timesBoldUnderline.setWrap(true);

        CellView cv = new CellView();
        cv.setFormat(times);
        cv.setFormat(timesBoldUnderline);
        //cv.setAutosize(true);

        // Write headers
        addCaption(sheet, 0, 0, "CODIGO");
        addCaption(sheet, 1, 0, "NRO-HISTORIA");
        addCaption(sheet, 2, 0, "NOMBRES");
        addCaption(sheet, 3, 0, "APELLIDOS");
        addCaption(sheet, 4, 0, "DIRECCION");
        addCaption(sheet, 5, 0, "CORREO");
        addCaption(sheet, 6, 0, "CELULAR");
        addCaption(sheet, 7, 0, "TELF. FIJO");
        addCaption(sheet, 8, 0, "FECHA DE NACIMIENTO");
        addCaption(sheet, 9, 0, "EDAD");
        addCaption(sheet, 10, 0, "PESO");
        addCaption(sheet, 11, 0, "TALLA");
       
    }

    /**
     * Create content.
     * 
     * @param sheet
     * @throws WriteException
     * @throws RowsExceededException 
     */
    private void createContent(WritableSheet sheet) throws WriteException,
      RowsExceededException {
        
        for (int i=0; i<arregloPacientes.size(); i++) {
            
            addLabel(sheet, 0, i+1, arregloPacientes.get(i).getCodigo().toString());
            addLabel(sheet, 1, i+1, arregloPacientes.get(i).getNrohistoria());
            addLabel(sheet, 2, i+1, arregloPacientes.get(i).getNombre());
            addLabel(sheet, 3, i+1, arregloPacientes.get(i).getApellido());
            addLabel(sheet, 4, i+1, arregloPacientes.get(i).getDireccion());
            addLabel(sheet, 5, i+1, arregloPacientes.get(i).getCorreo());
            addLabel(sheet, 6, i+1, arregloPacientes.get(i).getCelular());
            addLabel(sheet, 7, i+1, arregloPacientes.get(i).getFijo());
            addLabel(sheet, 8, i+1, arregloPacientes.get(i).getFechaNacimiento());
            addLabel(sheet, 9, i+1, arregloPacientes.get(i).getEdad().toString());
            addLabel(sheet, 10, i+1, arregloPacientes.get(i).getPeso());
            addLabel(sheet, 11, i+1, arregloPacientes.get(i).getTalla());
        }
    }

    /**
     * Add a caption.
     * 
     * @param sheet
     * @param column
     * @param row
     * @param s
     * @throws RowsExceededException
     * @throws WriteException 
     */
    private void addCaption(WritableSheet sheet, int column, int row, String s)
        throws RowsExceededException, WriteException {
        
        Label label;
        label = new Label(column, row, s, timesBoldUnderline);
        sheet.addCell(label);
    }

    /**
     * Add a number.
     * 
     * @param sheet
     * @param column
     * @param row
     * @param integer
     * @throws WriteException
     * @throws RowsExceededException 
     */
    private void addNumber(WritableSheet sheet, int column, int row,
        Integer integer) throws WriteException, RowsExceededException {
        
        Number number;
        number = new Number(column, row, integer, times);
        sheet.addCell(number);
    }

    /**
     * Add label.
     * 
     * @param sheet
     * @param column
     * @param row
     * @param s
     * @throws WriteException
     * @throws RowsExceededException 
     */
    private void addLabel(WritableSheet sheet, int column, int row, String s)
        throws WriteException, RowsExceededException {
        
        Label label;
        label = new Label(column, row, s, times);
        sheet.addCell(label);
    }
    
}
