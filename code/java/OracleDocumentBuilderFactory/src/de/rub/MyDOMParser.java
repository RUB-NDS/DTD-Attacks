package de.rub;

import java.io.IOException;
import oracle.xml.parser.v2.XMLParseException;
import org.xml.sax.SAXException;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;

public class MyDOMParser
{
   static public void main(String[] argv) throws XMLParseException, SAXException, IOException
   {
	   	String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";
         DOMParser parser = new DOMParser();         
         // Parse the document.
         parser.parse(xmlFile);
         // Obtain the document.
         XMLDocument doc = parser.getDocument();       	      
	     System.out.println("Ausgabe " + doc.getElementsByTagName("data").item(0).getTextContent());
   }
}
