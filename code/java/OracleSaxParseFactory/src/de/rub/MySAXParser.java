package de.rub;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import oracle.xml.parser.v2.SAXParser;

public class MySAXParser {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
	  String xmlFile ="file:///C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";      
      SAXParser parser = new SAXParser();      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      parser.setContentHandler(myDefaultHandler);        
      parser.parse(xmlFile);        
      System.out.println("Ausgabe " + myDefaultHandler.getElementContent("data"));	
		
	}	
}
