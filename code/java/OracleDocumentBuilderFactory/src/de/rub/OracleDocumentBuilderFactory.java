package de.rub;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;




public class OracleDocumentBuilderFactory {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
	
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";	
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();	    
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    org.w3c.dom.Document w3cdocument = builder.parse(xmlFile);
	    System.out.println("Ausgabe " + w3cdocument.getElementsByTagName("data").item(0).getTextContent());	        
		    
//		String documentBuilderFactory = System.getProperty("javax.xml.parsers.DocumentBuilderFactory");
//		System.out.println(documentBuilderFactory);
//		System.setProperty("javax.xml.parsers.DocumentBuilderFactory","oracle.xml.jaxp.JXDocumentBuilderFactory");
//		System.out.println(System.getProperty("javax.xml.parsers.DocumentBuilderFactory"));		
		
	      
//		 System.setProperty("javax.xml.parsers.DocumentBuilderFactory",documentBuilderFactory);
	}

}
