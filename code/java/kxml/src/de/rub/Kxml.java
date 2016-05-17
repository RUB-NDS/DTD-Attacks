/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rub;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class Kxml {

    public static void main(String[] args) throws XmlPullParserException, IOException {
       
        String xmlFile = "C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml";
    	
        
        XmlPullParserFactory factory = null;
        factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();
//        parser.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;
        
        while (token != XmlPullParser.END_DOCUMENT) {
        	System.out.println(i);
            System.out.println("Name " + parser.getName());
            System.out.println("Text " + parser.getText() );
            System.out.println("Event " + parser.getEventType());
            System.out.println("----------------------");
            token =             parser.nextToken();
            i++;
        }
    	
    	
    	
//       KXmlParser parser = new KXmlParser();      
//      InputStream xmlInput = new FileInputStream("C:/Christopher_Spaeth/code/xml_files_windows/standard.xml");            
//      parser.setInput(xmlInput, "UTF-8");      
//      parser.nextTag();
//        System.out.println(parser.getName());
//      parser.next();
//        System.out.println(parser.getText());        
    }
    
}
