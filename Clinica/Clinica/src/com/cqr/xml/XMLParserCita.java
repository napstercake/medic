/**
 * This is a software created by me, If you have any question about this project
 * just ask or make a pull request for this project.
 * 
 * @author Ricardo Gonzales [js.ricardo.gonzales@gmail.com]
 */

package com.cqr.xml;

import com.cqr.bean.CitaBean;
import com.cqr.constants.Constants;
import com.cqr.interfaz.IXMLParserCita;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author ricardogonzales
 */
public class XMLParserCita implements IXMLParserCita {
    
    CitaBean cita;
    int count = 0;

    @Override
    public boolean validateCita(String date, String time) {
        
        boolean result = true;
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnHora = false,
                        blnFecha = false,
                        blnCitaEnd = false,
                        blnFechaCo = false;
                        
                /**
                 * 
                 * @param uri
                 * @param localName
                 * @param qName
                 * @param attributes
                 * @throws SAXException 
                 */
                public void startElement(String uri, String localName,String qName, 
                    Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("FECHA")){blnFecha = true;}
                    if (qName.equalsIgnoreCase("HORA")){blnHora = true;}
                }
            
                /**
                 * 
                 * @param uri
                 * @param localName
                 * @param qName
                 * @throws SAXException 
                 */
                public void endElement(String uri, String localName,
                    String qName) throws SAXException {
                }
            
                /**
                 * 
                 * @param ch
                 * @param start
                 * @param length
                 * @throws SAXException 
                 */
                public void characters(char ch[], int start, int length) 
                        throws SAXException {

                        if (blnFecha) {

                            String fechaXML = new String(ch, start, length);
                            
                            if (fechaXML.equalsIgnoreCase(date)) {
                                blnFechaCo = true;
                            }
                            
                            blnFecha = false;
                        }
                        
                        if (blnHora) {
                            
                            if (blnFechaCo) {
                                
                                String horaXML = new String(ch, start, length);
                                
                                if (horaXML.equalsIgnoreCase(time)) {
                                    count++;
                                    blnFechaCo = false;
                                }
                            }
                            
                            blnHora = false;
                        }    
                }
        };
            
            saxParser.parse(Constants.XML_CITAS_PATH, handler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (count == 3) {
            result = false;
        }
        
        return result;
    }

    @Override
    public List<CitaBean> getCitasByDate(String fecha) {
        List<CitaBean> citasPorFechaXML = new ArrayList();
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnCodigo = false,
                        blnCodigoPaciente = false,
                        blnEstado = false,
                        blnHora = false,
                        blnFecha = false,
                        blnNotas = false,
                        blnCitaStart = false,
                        blnCitaEnd = false,
                        blnFechaCo = false;
                      
                /**
                 * 
                 * @param uri
                 * @param localName
                 * @param qName
                 * @param attributes
                 * @throws SAXException 
                 */
                public void startElement(String uri, String localName,String qName, 
                    Attributes attributes) throws SAXException {
                    
                    if (qName.equalsIgnoreCase("CITA")){blnCitaStart = true;}
                    
                    if (qName.equalsIgnoreCase("FECHA")){blnFecha = true;}
                    if (qName.equalsIgnoreCase("CODIGO")){blnCodigo = true;}
                    if (qName.equalsIgnoreCase("HORA")){blnHora = true;}
                    if (qName.equalsIgnoreCase("ESTADO")){blnEstado = true;}
                    if (qName.equalsIgnoreCase("NOTAS")) {blnNotas = true;}
                    if (qName.equalsIgnoreCase("CODIGOPACIENTE")){blnCodigoPaciente = true;}
                }
            
            /**
             * 
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException 
             */
            public void endElement(String uri, String localName,
		String qName) throws SAXException {
                if (qName.equalsIgnoreCase("CITA")){blnCitaEnd = true;}
            }
            
            
            /**
             * 
             * @param ch
             * @param start
             * @param length
             * @throws SAXException 
             */
            public void characters(char ch[], int start, int length) 
                    throws SAXException {
                
                    if (blnFecha) {
                        
                        String fechaXML = new String(ch, start, length);
                        
                        if (fechaXML.equalsIgnoreCase(fecha)) {
                            cita = new CitaBean();
                            cita.setFecha(fechaXML);
                            blnFechaCo = true;
                        }
                        blnFecha = false;
                    }
                    if (blnCodigo) {
                        if (blnFechaCo) {
                            cita.setCodigo(Integer.parseInt(new String(ch, start, length)));
                        }
                        blnCodigo = false;
                    }
                    if (blnHora) {
                        if (blnFechaCo) {
                            cita.setHora(new String(ch, start, length));
                        }
                        blnHora = false;
                    }    
                    if (blnEstado) {
                        if (blnFechaCo) {
                            cita.setEstado(new String(ch, start, length));
                        }
                        blnEstado = false;
                    }
                    if (blnNotas) {
                        if (blnFechaCo) {
                            cita.setNotas(new String(ch, start, length));
                        }
                        blnNotas = false;
                    }
                    if (blnCodigoPaciente) {
                        if (blnFechaCo) {
                            cita.setCodigopaciente(Integer.parseInt(new String(ch, start, length)));
                        }
                        blnCodigoPaciente = false;
                    }

                    if(blnCitaEnd){
                        if (blnFechaCo) {
                            citasPorFechaXML.add(cita);
                            blnFechaCo = false;
                        }
                        blnCitaEnd = false;
                    }
                    
            }
        };
            
        saxParser.parse(Constants.XML_CITAS_PATH, handler);    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return citasPorFechaXML;
    }

    @Override
    public List<CitaBean> readXMLCitas() {
        
        List<CitaBean> citasXML = new ArrayList();
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnCodigo = false,
                        blnCodigoPaciente = false,
                        blnEstado = false,
                        blnHora = false,
                        blnFecha = false,
                        blnNotas = false,
                        blnCitaStart = false,
                        blnCitaEnd = false;
                      
                /**
                 * 
                 * @param uri
                 * @param localName
                 * @param qName
                 * @param attributes
                 * @throws SAXException 
                 */
                public void startElement(String uri, String localName,String qName, 
                    Attributes attributes) throws SAXException {
                    
                    if (qName.equalsIgnoreCase("CITA")){blnCitaStart = true;}
                    
                    if (qName.equalsIgnoreCase("CODIGO")){blnCodigo = true;}
                    if (qName.equalsIgnoreCase("CODIGOPACIENTE")){blnCodigoPaciente = true;}
                    if (qName.equalsIgnoreCase("ESTADO")){blnEstado = true;}
                    if (qName.equalsIgnoreCase("HORA")){blnHora = true;}
                    if (qName.equalsIgnoreCase("FECHA")){blnFecha = true;}
                    if (qName.equalsIgnoreCase("NOTAS")) {blnNotas = true;}
                }
            
            /**
             * 
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException 
             */
            public void endElement(String uri, String localName,
		String qName) throws SAXException {
                if (qName.equalsIgnoreCase("CITA")){blnCitaEnd = true;}
            }
            
            
            /**
             * 
             * @param ch
             * @param start
             * @param length
             * @throws SAXException 
             */
            public void characters(char ch[], int start, int length) 
                    throws SAXException {
                
                    if (blnCitaStart) {
                        cita = new CitaBean();
                        blnCitaStart = false;
                    }
                    
                    if (blnCodigo) {
                        cita.setCodigo(Integer.parseInt(new String(ch, start, length)));
                        blnCodigo = false;
                    }

                    if (blnCodigoPaciente) {
                        cita.setCodigopaciente(Integer.parseInt(new String(ch, start, length)));
                        blnCodigoPaciente = false;
                    }

                    if (blnEstado) {
                        cita.setEstado(new String(ch, start, length));
                        blnEstado = false;
                    }

                    if (blnHora) {
                        cita.setHora(new String(ch, start, length));
                        blnHora = false;
                    }

                    if (blnFecha) {
                        cita.setFecha(new String(ch, start, length));
                        blnFecha = false;
                    }
                    if (blnNotas) {
                        cita.setNotas(new String(ch, start, length));
                        blnNotas = false;
                    }
                    if(blnCitaEnd){
                        citasXML.add(cita);
                        blnCitaEnd = false;
                    }
            }
        };
        
        saxParser.parse(Constants.XML_CITAS_PATH, handler);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return citasXML;
    }

    @Override
    public Boolean saveCita(CitaBean citaBean) {
        
        boolean blnResultado = false;
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(Constants.XML_CITAS_PATH);
            
            int codigoCita = obtenerCodigo(doc,"cita");
            
            if (codigoCita != 0) {
                
                // Get the root element // 
                Node citas = doc.getFirstChild();

                Node cita = doc.createElement("cita");
                citas.appendChild(cita);

                Element nodeFecha = doc.createElement("fecha");
                Element nodeCodigo = doc.createElement("codigo");
                Element nodeHora = doc.createElement("hora");
                Element nodeEstado = doc.createElement("estado");
                Element nodeNotas = doc.createElement("notas");
                Element nodeCodigoPaciente = doc.createElement("codigopaciente");
                
                nodeFecha.appendChild(doc.createTextNode(citaBean.getFecha()));
                cita.appendChild(nodeFecha);
                
                nodeCodigo.appendChild(doc.createTextNode(codigoCita+""));
                cita.appendChild(nodeCodigo);
                
                nodeHora.appendChild(doc.createTextNode(citaBean.getHora()));
                cita.appendChild(nodeHora);
                
                nodeEstado.appendChild(doc.createTextNode(citaBean.getEstado()));
                cita.appendChild(nodeEstado);
                
                nodeNotas.appendChild(doc.createTextNode(citaBean.getNotas()));
                cita.appendChild(nodeNotas);
                
                nodeCodigoPaciente.appendChild(doc.createTextNode(citaBean.getCodigopaciente().toString()));
                cita.appendChild(nodeCodigoPaciente);
                
                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(Constants.XML_CITAS_PATH));
                transformer.transform(source, result);

                blnResultado = true;
                
            } else {
                blnResultado = false;
            }
            
        } catch (Exception e) {
            blnResultado = false;
            e.printStackTrace();
        }
        
        return blnResultado;
    }

    @Override
    public boolean updateCita(CitaBean citaBean) {
        
        int citaCodigo = citaBean.getCodigo() - 1;
        boolean resultado = false;
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(Constants.XML_CITAS_PATH);
            
            Node cita = doc.getElementsByTagName("cita").item(citaCodigo);
            
            NodeList listCita = cita.getChildNodes();
            
            for (int i=0; i<listCita.getLength(); i++) {
                
                Node node = listCita.item(i);
                
                if ("fecha".equals(node.getNodeName())) {
                    node.setTextContent(citaBean.getFecha());
		}
                if ("hora".equals(node.getNodeName())) {
                    node.setTextContent(citaBean.getHora());
		}
                if ("estado".equals(node.getNodeName())) {
                    node.setTextContent(citaBean.getEstado());
		}
                if ("notas".equals(node.getNodeName())) {
                    node.setTextContent(citaBean.getNotas());
		}
                
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Constants.XML_CITAS_PATH));
            transformer.transform(source, result);
            
            resultado = true;
            
        } catch (Exception e) {
            resultado = false;
            e.printStackTrace();
            
        }
        
        return resultado;
    }

    @Override
    public boolean deleteCita(String codigoCita) {
        boolean blnResultado = false;
        
        if (!codigoCita.equalsIgnoreCase("")) {
        
            try {
            
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(Constants.XML_CITAS_PATH);

                Node citas = doc.getElementsByTagName("citas").item(0);

                NodeList listCitas = citas.getChildNodes();//<cita><cita><cita>

                for (int i=0; i<listCitas.getLength(); i++) {

                    NodeList listCitaContents = listCitas.item(i).getChildNodes();
                    Node citaToRemove = listCitas.item(i);

                    for (int j=0; j<listCitaContents.getLength(); j++) {

                        Node node = listCitaContents.item(j);//<fecha><codigo><hora>

                        if ("codigo".equals(node.getNodeName())) {
                            
                            if (node.getTextContent().equalsIgnoreCase(codigoCita)) {
                                citas.removeChild(citaToRemove);
                            } 
                       }
                    }
                }
                
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(Constants.XML_CITAS_PATH));
                transformer.transform(source, result);
                
                blnResultado = true;
            
            } catch (Exception e) {
                blnResultado = false;
                e.printStackTrace();
            }
            
        } else {
            blnResultado = false;
        }
        
        return blnResultado;
    }
    
    /**
     * 
     * @param doc
     * @param tagName
     * @return 
     */
    private int obtenerCodigo(Document doc, String tagName) {
        
        int codigo = 0;
        
        NodeList list = doc.getElementsByTagName(tagName);
        codigo = list.getLength()+1;
 
        return codigo;
    }

    
}
