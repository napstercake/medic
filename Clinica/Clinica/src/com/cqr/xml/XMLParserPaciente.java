/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.xml;

import com.cqr.bean.PacienteBean;
import com.cqr.constants.Constants;
import com.cqr.interfaz.IXMLParserPaciente;
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
public class XMLParserPaciente implements IXMLParserPaciente {
    
    PacienteBean paciente;
    String nombrePaciente;

    @Override
    public String getNamePacienteByCode (String pacienteCodigo) {
        
        System.out.println("Paciente codigo: " + pacienteCodigo);
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
                
                boolean blnCodigo = false,
                        blnHistoria = false,
                        blnNombre = false,
                        blnApellido = false,
                        blnGetName = false;
                      
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
                    
                    if (qName.equalsIgnoreCase("CODIGO")){blnCodigo = true;}
                    if (qName.equalsIgnoreCase("NROHISTORIA")){blnHistoria = true;} //nrohistoria
                    if (qName.equalsIgnoreCase("NOMBRE")){blnNombre = true;}
                    if (qName.equalsIgnoreCase("APELLIDO")){blnApellido = true;}
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
              
                        if (blnCodigo) {
                            if ((new String(ch, start, length)).equalsIgnoreCase(pacienteCodigo)) {
                                blnGetName = true;
                            }
                            blnCodigo = false;
                        }
                        
                        if (blnHistoria) {
                            if (blnGetName) {
                                nombrePaciente = "(" + new String(ch, start, length) + ")";
                                //nombrePaciente = "(" + new String(ch, start, length) + ")" + nombrePaciente;
                                System.out.println("historia: " + nombrePaciente);
                                //blnGetName = false;
                            }
                            blnHistoria = false;
                            
                        }

                        if (blnNombre) {
                            if (blnGetName) {
                                nombrePaciente = nombrePaciente + " " + new String(ch, start, length);
                                System.out.println("historia + nombre: " + nombrePaciente);
                            }
                            blnNombre = false;
                        }
                        
                        if (blnApellido) {
                            if (blnGetName) {
                                nombrePaciente = nombrePaciente + " " + new String(ch, start, length);
                                System.out.println("historia + nombre + apellido: " + nombrePaciente);
                                blnGetName = false;
                            }
                            blnApellido = false;
                        }
                        
                        
                            
            }
        };
        
        saxParser.parse(Constants.XML_PACIENTES_PATH, handler);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return nombrePaciente;
    }

    @Override
    public List<String> getPacientesEmail() {
        
        
        // NAPSTER AGREGUE FINAL
        ArrayList<String> listaDeEmails;
        listaDeEmails = new ArrayList<String>();
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnCorreo = false;
                      
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
                    
                    if (qName.equalsIgnoreCase("CORREO")){blnCorreo = true;}
                }
            
            /**
             * 
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException 
             */
            public void endElement(String uri, String localName,
		String qName) throws SAXException {}
            
            /**
             * 
             * @param ch
             * @param start
             * @param length
             * @throws SAXException 
             */
            public void characters(char ch[], int start, int length) 
                    throws SAXException {
                
                    if (blnCorreo) {
                        listaDeEmails.add(new String(ch, start, length));
                        blnCorreo = false;
                    }
            }
        };
        
        saxParser.parse(Constants.XML_PACIENTES_PATH, handler);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listaDeEmails;
    }

    @Override
    public List<PacienteBean> readXMLPacientesWMail() {
        
        final List<PacienteBean> pacientesXML = new ArrayList();
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnNombre = false,
                        blnApellido = false,
                        blnCorreo = false,
                        blnPacienteStart = false,
                        blnPacienteEnd = false,
                        blnBean = false;
                      
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
                    
                    if (qName.equalsIgnoreCase("PACIENTE")){blnPacienteStart = true;}
                    
                    if (qName.equalsIgnoreCase("NOMBRE")){blnNombre = true;}
                    if (qName.equalsIgnoreCase("APELLIDO")){blnApellido = true;}
                    if (qName.equalsIgnoreCase("CORREO")){blnCorreo = true;}
                    
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
                if (qName.equalsIgnoreCase("PACIENTE")){blnPacienteEnd = true;}
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
                
                    if (blnCorreo) {
                        String correo = new String(ch, start, length);
                        
                        if (!correo.equalsIgnoreCase("")) {
                            paciente.setCorreo(correo);
                            blnBean = true;
                        }
                        
                        blnCorreo = false;
                    }
                    
                    if (blnPacienteStart && blnBean) {
                        paciente = new PacienteBean();
                        blnPacienteStart = false;
                    }
                    
                    if (blnNombre) {
                        if (blnBean) {
                            paciente.setNombre(new String(ch, start, length));
                        }
                        blnNombre = false;
                    }

                    if (blnApellido) {
                        if (blnBean) {
                            paciente.setApellido(new String(ch, start, length));
                        }
                        blnApellido = false;
                    }

                    if(blnPacienteEnd) {
                        if (blnBean) {
                            pacientesXML.add(paciente);
                            blnPacienteEnd = false;
                            blnBean = false;
                        }
                    }
            }
        };
        
        saxParser.parse(Constants.XML_PACIENTES_PATH, handler);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return pacientesXML;
        
    }

    @Override
    public List<PacienteBean> readXMLPacientes() {
        
        List<PacienteBean> pacientesXML = new ArrayList();
        
        try {
            
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            
            DefaultHandler handler = new DefaultHandler() {
 
                boolean blnCodigo = false,
                        blnNrohistoria = false,
                        blnNombre = false,
                        blnApellido = false,
                        blnDireccion = false,
                        blnCorreo = false,
                        blnCelular = false,
                        blnFijo = false,
                        blnFecNacimiento = false,
                        blnEdad = false,
                        blnPeso = false,
                        blnTalla = false,
                        blnPacienteStart = false,
                        blnPacienteEnd = false;
                      
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
                    
                    if (qName.equalsIgnoreCase("PACIENTE")){blnPacienteStart = true;}
                    
                    if (qName.equalsIgnoreCase("CODIGO")){blnCodigo = true;}
                    if (qName.equalsIgnoreCase("NROHISTORIA")){blnNrohistoria = true;}
                    if (qName.equalsIgnoreCase("NOMBRE")){blnNombre = true;}
                    if (qName.equalsIgnoreCase("APELLIDO")){blnApellido = true;}
                    if (qName.equalsIgnoreCase("DIRECCION")){blnDireccion = true;}
                    if (qName.equalsIgnoreCase("CORREO")){blnCorreo = true;}
                    if (qName.equalsIgnoreCase("CELULAR")) {blnCelular = true;}
                    if (qName.equalsIgnoreCase("FIJO")) {blnFijo = true;}
                    if (qName.equalsIgnoreCase("FECHANACIMIENTO")){blnFecNacimiento = true;}
                    if (qName.equalsIgnoreCase("EDAD")) {blnEdad = true;}
                    if (qName.equalsIgnoreCase("PESO")) {blnPeso = true;}
                    if (qName.equalsIgnoreCase("TALLA")) {blnTalla = true;}
                    
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
                if (qName.equalsIgnoreCase("PACIENTE")){blnPacienteEnd = true;}
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
                
                    if (blnPacienteStart) {
                        paciente = new PacienteBean();
                        blnPacienteStart = false;
                    }
                    
                    if (blnCodigo) {
                        paciente.setCodigo(Integer.parseInt(new String(ch, start, length)));
                        blnCodigo = false;
                    }
                    
                    if (blnNrohistoria) {
                        paciente.setNrohistoria(new String(ch, start, length));
                        blnNrohistoria = false;
                    }

                    if (blnNombre) {
                        paciente.setNombre(new String(ch, start, length));
                        blnNombre = false;
                    }

                    if (blnApellido) {
                        paciente.setApellido(new String(ch, start, length));
                        blnApellido = false;
                    }

                    if (blnDireccion) {
                        paciente.setDireccion(new String(ch, start, length));
                        blnDireccion = false;
                    }

                    if (blnCorreo) {
                        paciente.setCorreo(new String(ch, start, length));
                        blnCorreo = false;
                    }
                    if (blnCelular) {
                        paciente.setCelular(new String(ch, start, length));
                        blnCelular = false;
                    }

                    if (blnFijo) {
                        paciente.setFijo(new String(ch, start, length));
                        blnFijo = false;
                    }

                    if (blnFecNacimiento) {
                        paciente.setFechaNacimiento(new String(ch, start, length));
                        blnFecNacimiento = false;
                    }

                    if (blnEdad) {
                        paciente.setEdad(Integer.parseInt(new String(ch, start, length)));
                        blnEdad = false;
                    }

                    if (blnPeso) {
                        paciente.setPeso(new String(ch, start, length));
                        blnPeso = false;
                    }

                    if (blnTalla) {
                        paciente.setTalla(new String(ch, start, length));
                        blnTalla = false;
                    }
                    
                    if(blnPacienteEnd){
                        pacientesXML.add(paciente);
                        blnPacienteEnd = false;
                    }
                    
            }
        };
        
        saxParser.parse(Constants.XML_PACIENTES_PATH, handler);
        
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return pacientesXML;
    }

    @Override
    public boolean updatePaciente(PacienteBean pacienteBean) {
        
        int pacienteCodigo = pacienteBean.getCodigo() - 1;
        boolean resultado = false;
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(Constants.XML_PACIENTES_PATH);
            
            Node paciente = doc.getElementsByTagName("paciente").item(pacienteCodigo);
            
            NodeList listPaciente = paciente.getChildNodes();
            
            for (int i=0; i<listPaciente.getLength(); i++) {
                
                Node node = listPaciente.item(i);
                
                if ("nrohistoria".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getNrohistoria());
		}
                if ("nombre".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getNombre());
		}
                if ("apellido".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getApellido());
		}
                if ("direccion".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getDireccion());
		}
                if ("correo".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getCorreo());
		}
                if ("celular".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getCelular());
		}
                if ("fijo".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getFijo());
		}
                if ("fechaNacimiento".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getFechaNacimiento());
		}
                if ("edad".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getEdad().toString());
		}
                if ("peso".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getPeso().toString());
		}
                if ("talla".equals(node.getNodeName())) {
                    node.setTextContent(pacienteBean.getTalla().toString());
		}
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Constants.XML_PACIENTES_PATH));
            transformer.transform(source, result);
            
            resultado = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return resultado;
        
    }

    @Override
    public boolean savePaciente(PacienteBean pacienteBean) {
        
        boolean resultado = false;
        
        try {
            
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(Constants.XML_PACIENTES_PATH);
            
            int codigoPaciente = obtenerCodigo(doc,"paciente");
            
            if (codigoPaciente != 0) {
                
                // Get the root element // Quiza esto este fallando
                Node pacientes = doc.getFirstChild();

                Node paciente = doc.createElement("paciente");
                pacientes.appendChild(paciente);

                // Get the staff element by tag name directly
                Element nodeCodigo = doc.createElement("codigo");
                Element nodeNrohistoria = doc.createElement("nrohistoria");
                Element nodeNombre = doc.createElement("nombre");
                Element nodeApellido = doc.createElement("apellido");
                Element nodeDireccion = doc.createElement("direccion");
                Element nodeCorreo = doc.createElement("correo");
                Element nodeCelular = doc.createElement("celular");
                Element nodeFijo = doc.createElement("fijo");
                Element nodeFecNac = doc.createElement("fechaNacimiento");
                Element nodeEdad = doc.createElement("edad");
                Element nodePeso = doc.createElement("peso");
                Element nodeTalla = doc.createElement("talla");

                nodeCodigo.appendChild(doc.createTextNode(codigoPaciente+""));
                paciente.appendChild(nodeCodigo);
                
                nodeNrohistoria.appendChild(doc.createTextNode(pacienteBean.getNrohistoria()));
                paciente.appendChild(nodeNrohistoria);
                
                nodeNombre.appendChild(doc.createTextNode(pacienteBean.getNombre()));
                paciente.appendChild(nodeNombre);
                
                nodeApellido.appendChild(doc.createTextNode(pacienteBean.getApellido()));
                paciente.appendChild(nodeApellido);
                
                nodeDireccion.appendChild(doc.createTextNode(pacienteBean.getDireccion()));
                paciente.appendChild(nodeDireccion);
                
                nodeCorreo.appendChild(doc.createTextNode(pacienteBean.getCorreo()));
                paciente.appendChild(nodeCorreo);
                
                nodeCelular.appendChild(doc.createTextNode(pacienteBean.getCelular()));
                paciente.appendChild(nodeCelular);
                
                nodeFijo.appendChild(doc.createTextNode(pacienteBean.getFijo()));
                paciente.appendChild(nodeFijo);
                
                nodeFecNac.appendChild(doc.createTextNode(pacienteBean.getFechaNacimiento()));
                paciente.appendChild(nodeFecNac);
                
                nodeEdad.appendChild(doc.createTextNode(pacienteBean.getEdad().toString()));
                paciente.appendChild(nodeEdad);
                
                nodePeso.appendChild(doc.createTextNode(pacienteBean.getPeso().toString()));
                paciente.appendChild(nodePeso);
                
                nodeTalla.appendChild(doc.createTextNode(pacienteBean.getTalla().toString()));
                paciente.appendChild(nodeTalla);

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(Constants.XML_PACIENTES_PATH));
                transformer.transform(source, result);

                resultado = true;
                
            } else {
                resultado = false;
            }
            
            
            
        } catch (Exception e) {
            resultado = false;
            e.printStackTrace();
        }
        
        return resultado;
    }

    @Override
    public boolean deletePaciente(String codigoPaciente) {
        
        boolean blnResultado = false;
        
        if (!codigoPaciente.equalsIgnoreCase("")) {
        
            try {
            
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(Constants.XML_PACIENTES_PATH);

                Node pacientes = doc.getElementsByTagName("pacientes").item(0);

                NodeList listPacientes = pacientes.getChildNodes();//<cita><cita><cita>

                for (int i=0; i<listPacientes.getLength(); i++) {

                    NodeList listPacienteContents = listPacientes.item(i).getChildNodes();
                    Node pacienteToRemove = listPacientes.item(i);

                    for (int j=0; j<listPacienteContents.getLength(); j++) {

                        Node node = listPacienteContents.item(j);//<fecha><codigo><hora>

                        if ("codigo".equals(node.getNodeName())) {
                            
                            if (node.getTextContent().equalsIgnoreCase(codigoPaciente)) {
                                pacientes.removeChild(pacienteToRemove);
                            } 
                       }

                    }
                }
                
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(Constants.XML_PACIENTES_PATH));
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
