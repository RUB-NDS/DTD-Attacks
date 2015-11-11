package de.rub;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import org.xml.sax.SAXException;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CrimsonSax {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {      
    	
    	String provider ="org.apache.crimson.jaxp.SAXParserFactoryImpl";               
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");    	
    	SAXParserFactory factory = SAXParserFactory.newInstance(provider, null);
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
    	SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        System.out.println(myDefaultHandler.getElementContent("data"));
	}

}

//String provider ="org.apache.crimson.jaxp.SAXParserFactoryImpl";
//
//InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/schemaEntity_noSchema.xml");
//SAXParserFactory factory = SAXParserFactory.newInstance(provider, null);
//
//
//
//        
//SAXParser parser =factory.newSAXParser();
////XMLReader reader = parser.getXMLReader();
////reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
//MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
////saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//     
//parser.parse(xmlInput, myDefaultHandler);
//
//System.out.println(myDefaultHandler.getElementContent("data"));