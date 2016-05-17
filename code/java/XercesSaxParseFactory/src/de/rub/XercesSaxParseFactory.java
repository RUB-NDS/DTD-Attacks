package de.rub;



import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class XercesSaxParseFactory {	
	
	 public static void main (String argv []) throws ParserConfigurationException, SAXException, IOException {
		  	 
		 
		 String provider = "org.apache.xerces.jaxp.SAXParserFactoryImpl";
		 InputStream xmlInput = new FileInputStream("C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml");
	     SAXParserFactory factory = SAXParserFactory.newInstance(provider, null);	     
	     MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
	     factory.setFeature("http://apache.org/xml/features/xinclude",true);
	     factory.setFeature("http://xml.org/sax/features/namespaces",true);
	     SAXParser saxParser = factory.newSAXParser();	 
	     saxParser.parse(xmlInput, myDefaultHandler);
	     System.out.println(myDefaultHandler.getElementContent("data"));            
	 }
	
}
