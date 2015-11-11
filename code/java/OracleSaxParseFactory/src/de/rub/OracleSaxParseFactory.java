package de.rub;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import oracle.xml.jaxp.JXSAXParserFactory;
import oracle.xml.parser.v2.DOMParser;
//import javax.xml.parsers.SAXParser;
import oracle.xml.parser.v2.SAXParser;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *  
 * @author dev
 * OLD implementation; Refer to MySAXParser
 * This is a collection of different implementations.
 */
public class OracleSaxParseFactory {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		
		//Collection of different implementations
		
		// Option 1: Using XMLReaderFactory
//		XMLReader parser = XMLReaderFactory.createXMLReader("oracle.xml.parser.v2.SAXParser");		
//		
//		// not recognized
////		 parser.setFeature(oracle.xml.parser.v2.SAXParser.EXPAND_ENTITYREF, false);
//		MyContentHandler myContentHandler = new MyContentHandler();
//		parser.setContentHandler(myContentHandler);		
//		// undefined
//		// parser.setAttribute("oracle.xml.parser.XMLParser.ExpandEntityRef", false);
//		// not recognized
////		parser.setProperty("oracle.xml.parser.XMLParser.ExpandEntityRef", false);
//	 	// reports true
//		System.out.println(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
//	 	// reports true
//		System.out.println(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		
//		// Feature not supported
////		parser.setFeature("http://xml.org/sax/features/external-general-entities",false);
//		// Feature not supported
////		parser.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
//		
//		parser.parse("file:///C:/Christopher_Spaeth/code/xml_files_windows/test/parameterEntity_sendftp.xml");
//		System.out.println(myContentHandler.getResult());
		
		
		
		
		// Option 2: JAXP: Oracle classes
//		 InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/parameterEntity_sendftp.xml");
////		String provider =  "oracle.xml.jaxp.JXSAXParserFactory";
//		oracle.xml.jaxp.JXSAXParserFactory factory = new JXSAXParserFactory();		
//		// not recognized
////		factory.setFeature(oracle.xml.parser.v2.SAXParser.EXPAND_ENTITYREF, false);		
//	 	// reports false
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//	 	// reports false
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//	 
//	// Feature not supported
////	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
//	// Feature not supported
////	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
//		
//	    javax.xml.parsers.SAXParser saxparser = factory.newSAXParser();
////	    saxparser.setProperty(oracle.xml.parser.v2.SAXParser.EXPAND_ENTITYREF, false);
//        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//               
//        saxparser.parse(xmlInput, myDefaultHandler);       
//        System.out.println("Ausgabe " + myDefaultHandler.getElementContent("data"));
		
		
		



		
//		Option 3: JAXP: Standard classes
//		 InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/parameterEntity_sendftp.xml");
//		 String provider =  "oracle.xml.jaxp.JXSAXParserFactory";			
//		 SAXParserFactory factory = SAXParserFactory.newInstance(provider, null);
//		 //not recognized
////		 factory.setFeature(oracle.xml.parser.v2.SAXParser.EXPAND_ENTITYREF, false);		 
//		 	// reports false
//			System.out.println(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		 	// reports false
//			System.out.println(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		 
//			
//		// Feature not supported
////		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
//		// Feature not supported
////		factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
//		 	
//			
//		 SAXParser parser = factory.newSAXParser();
//		 // undefined for SAXParser
//		 // parser.setAttribute("oracle.xml.parser.XMLParser.ExpandEntityRef", false);
//		 MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//		 parser.parse(xmlInput, myDefaultHandler);
//       System.out.println("Ausgabe " + myDefaultHandler.getElementContent("data"));
		
   
	    
	    
	    // Option 4: Use class directly
//        SAXParser parser = new SAXParser();
//        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/test/parameterEntity_sendftp.xml";
//        
//
//        //reports true
//        System.out.println(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//        //reports true
//        System.out.println(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
//        
//        // SAXNotSupportedException
////      parser.setFeature("http://xml.org/sax/features/external-general-entities",false);
//      //SAXNotSupportedException
////      parser.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
//        
//        
////        parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
//        // set validation mode
//       // ((SAXParser)parser).setValidationMode(SAXParser.DTD_VALIDATION);        
//      //  parser.setFeature(oracle.xml.parser.v2.SAXParser.EXT_GEN_ENTITY_FEATURE, false);
//        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
//        parser.setContentHandler(myDefaultHandler);        
//        parser.parse(xmlInput);        
//        System.out.println("Ausgabe " + myDefaultHandler.getElementContent("data"));
	        

	        
	      
//	      factory.setFeature(oracle.xml.parser.v2.SAXParser.EXT_GEN_ENTITY_FEATURE, false);
//	      
	      
    	//parser.setAttribute(DOMParser.EXT_PAR_ENTITY_FEATURE, true);        
		

	}

}
