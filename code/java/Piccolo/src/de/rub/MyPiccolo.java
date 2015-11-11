package de.rub;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.SAXException;

import java.io.InputStream;
import java.io.FileInputStream;

import com.bluecast.xml.Piccolo;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class MyPiccolo {

	 public static void main (String argv []) throws ParserConfigurationException, SAXException, IOException {
		
	
		
//		1. This is a minimal example of using Piccolo		
//		 InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
//		 SAXParserFactory factory = SAXParserFactory.newInstance("com.bluecast.xml.JAXPSAXParserFactory", null);
////		 SAXParserFactory factory = SAXParserFactory.newInstance();	     
//	     MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//	     SAXParser saxParser = factory.newSAXParser();	 
//	     saxParser.parse(xmlInput, myDefaultHandler);
//	     System.out.println(myDefaultHandler.getElementContent("data"));	     
		
		
		
	     //2. HOwever setting features using a factory does not work:
//		 InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
////	     SAXParserFactory factory = SAXParserFactory.newInstance("com.bluecast.xml.JAXPSAXParserFactory", null);
//	     SAXParserFactory factory = SAXParserFactory.newInstance();
//		// reports true
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//		// reports true
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));	
//		
//		// reports true
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
//		// reports true
//		System.out.println(factory.getFeature("http://xml.org/sax/features/external-general-entities"));	
//		
//	     
//	     MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//	     SAXParser saxParser = factory.newSAXParser();	 
//	     saxParser.parse(xmlInput, myDefaultHandler);
//	     System.out.println(myDefaultHandler.getElementContent("data"));
	     
	     
	     
	     //3. Therefore: we use the parser directly; this does also not solve the problem!		 
//	     SAXParserFactory factory = SAXParserFactory.newInstance("com.bluecast.xml.JAXPSAXParserFactory", null);
//	     SAXParserFactory factory = SAXParserFactory.newInstance();
//	     MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//	     
//	     SAXParser saxParser = factory.newSAXParser();	 
//	     XMLReader xmlreader = saxParser.getXMLReader();
//	     // reports true
//		System.out.println("external-general-entities " + xmlreader.getFeature("http://xml.org/sax/features/external-general-entities"));
//		xmlreader.setFeature("http://xml.org/sax/features/external-general-entities", false);
//	     // reports false
//		System.out.println("external-general-entities "+ xmlreader.getFeature("http://xml.org/sax/features/external-general-entities"));	
//		
//	     // reports false		
//		System.out.println("external-parameter-entities "+  xmlreader.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		xmlreader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//	     // reports false
//		System.out.println("external-parameter-entities " + xmlreader.getFeature("http://xml.org/sax/features/external-parameter-entities"));	
//	     
//	     saxParser.parse("../../xml_files_windows/xxe.xml", myDefaultHandler);
//	     System.out.println("Ausgabe: " + myDefaultHandler.getElementContent("data"));
	     
	     
	     // 4. Finally, this works, however external-parameter-entities is not reported correctly 
		 // the value is identical to external-general-entities
		 Piccolo myPiccolo = new Piccolo();
		 MyDefaultHandler myDefaultHandler = new MyDefaultHandler();	      
		 myPiccolo.setContentHandler(myDefaultHandler);
	     // reports true
//		System.out.println("external-general-entities " + myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));
//		myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities", false);
//	     // reports false
//		System.out.println("external-general-entities "+ myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));	
//		
//	     // reports false		
//		System.out.println("external-parameter-entities "+  myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//	     // reports false
//		System.out.println("external-parameter-entities " + myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));	
//	     
		 myPiccolo.parse("../../xml_files_windows/standard.xml");
		 System.out.println("Ausgabe: " +myDefaultHandler.getElementContent("data"));
		
		

	
	}
		
	
}
