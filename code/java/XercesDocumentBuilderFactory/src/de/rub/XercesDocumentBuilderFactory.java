package de.rub;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XercesDocumentBuilderFactory {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
	
//		String provider ="org.apache.xerces.impl.Version";
		String xmlFile = "../../xml_files_windows/standard.xml";
//		String xmlFile= "../../xml_files_windows/test/schemaEntity_noSchema.xml";

		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    
		    org.w3c.dom.Document w3cdocument = builder.parse(xmlFile);
		    System.out.println("Ausgabe " + w3cdocument.getElementsByTagName("data").item(0).getTextContent());

	

	}
}
