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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kxml2.io.KXmlParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 *
 * @author dev
 */
public class KxmlTest {
    
    KXmlParser parser;
    String _URL_ = "http://127.0.0.1:5000";
    
    public KxmlTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        parser = new KXmlParser();
        
      assertFalse(parser.getFeature(org.kxml2.io.KXmlParser.FEATURE_PROCESS_DOCDECL));
      assertFalse(parser.getFeature(org.kxml2.io.KXmlParser.FEATURE_PROCESS_NAMESPACES));
      assertFalse(  parser.getFeature(org.kxml2.io.KXmlParser.FEATURE_REPORT_NAMESPACE_ATTRIBUTES));
      assertFalse(  parser.getFeature(org.kxml2.io.KXmlParser.FEATURE_VALIDATION));
        
        
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Kxml.
     */
    @Test
    public void testStandard() throws XmlPullParserException, IOException {
               
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
        parser.setInput(xmlInput, "UTF-8");

        // navigate to first Tag
        parser.nextTag();        
        String name = parser.getName();
        assertEquals("data", name);
        
        // navigate to text content
        parser.next();
        String content = parser.getText();
        assertEquals("4", content);
              
    }
    
    
    @Test
    public void testDOS_core() throws FileNotFoundException, IOException, XmlPullParserException  {
            
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_core.xml");
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &a2;";
            assertTrue(e.getMessage().contains(message));          
        }               
    }
    
    @Test
    public void testDOS_indirections() throws FileNotFoundException, IOException, XmlPullParserException  {
         
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_indirections.xml");
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &a4;";
           assertTrue(e.getMessage().contains(message));          
        }               
    }
    
    @Test
    public void testDOS_indirections_parameterEntity() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_indirections_parameterEntity.xml");
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &g;";

            assertTrue(e.getMessage().contains(message));            
        }               
    }
    
    @Test
    public void testDOS_entitySize() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_entitySize.xml");
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &a0;";
            assertTrue(e.getMessage().contains(message));            
        }               
    }
    
        
    @Test
    public void testDOS_recursion() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_recursion.xml");
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
                        
            String message = "unresolved: &a;";
            assertTrue(e.getMessage().contains(message));  
        }               
    }
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
            InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
        
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &intern;";
            assertTrue(e.getMessage().contains(message));  
        }               
    }
    
    @Test
    public void testInternalSubset_PEReferenceInDTD() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
        	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
            
        
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {
            String message = "unresolved: &intern;";
            assertTrue(e.getMessage().contains(message));  
        }               
    }

    
    
    @Test
    public void testParameterEntity_core() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
        	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
            
        
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {

            String message = "unresolved: &all;";
            assertTrue(e.getMessage().contains(message));  
        }               
    }
    
    
    @Test
    public void testParameterEntity_doctype() throws FileNotFoundException, IOException, XmlPullParserException  {
        
        try {
        	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
                       
        
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.next();            
        }
        catch(XmlPullParserException e) {

            String message = "unresolved: &all;";
            assertTrue(e.getMessage().contains(message));  
        }               
    }
    
    
    @Test
    public void testURLInvocation_doctype() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        parser.setInput(xmlInput, "UTF-8");   
        parser.nextTag();        
        String name = parser.getName();    
        parser.next();            
        
        
        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);  	
    }
    
    
    @Test
    public void testURLInvocation_externalGeneralEntity() throws Exception{
	
			
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        
        try {
        	
        	parser.setInput(xmlInput, "UTF-8");   
        	parser.nextTag();        
        	String name = parser.getName();    
        	parser.next();            
        	
        }
        catch(XmlPullParserException e) {

        	String message = "unresolved: &remote;";
        	assertTrue(e.getMessage().contains(message));
        }finally {
        	
        	response = http.sendGet(_URL_ + "/getCounter");
        	assertEquals("0", response);  	
        }
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity() throws Exception{
	
			
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        
       
    	parser.setInput(xmlInput, "UTF-8");   
    	parser.nextTag();        
    	String name = parser.getName();    
    	parser.next();            
    	
            	
    	response = http.sendGet(_URL_ + "/getCounter");
    	assertEquals("0", response);  	
      
        
    }
    
    @Test
    public void testURLInvocation_schemaLocation() throws Exception{
	
			
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        
       
    	parser.setInput(xmlInput, "UTF-8");   
    	parser.nextTag();        
    	String name = parser.getName();    
    	parser.next();            
    	
            	
    	response = http.sendGet(_URL_ + "/getCounter");
    	assertEquals("0", response);  	
      
        
    }
    
    @Test
    public void testURLInvocation_XInclude() throws Exception{
	
    	 InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
         
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        
       
    	parser.setInput(xmlInput, "UTF-8");   
    	parser.nextTag();        
    	String name = parser.getName();    
    	parser.next();            
    	
            	
    	response = http.sendGet(_URL_ + "/getCounter");
    	assertEquals("0", response);  	
      
        
    }
    
    
    @Test
    public void testURLInvocation_noNamespaceSchemaLocation() throws Exception{
	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_+ "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);
        
        
       
    	parser.setInput(xmlInput, "UTF-8");   
    	parser.nextTag();        
    	String name = parser.getName();    
    	parser.next();            
    	
            	
    	response = http.sendGet(_URL_ + "/getCounter");
    	assertEquals("0", response);  	
         	
    }
    
    
    @Test
    public void testXInclude() throws FileNotFoundException, IOException, XmlPullParserException  {
         
    
        	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
            
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
            parser.nextTag();   
            name = parser.getName();
            
            assertEquals("xi:include", name);        
        
    }
    
    
    @Test
    public void testXSLT() throws FileNotFoundException, IOException, XmlPullParserException  {
         
    
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/xslt.xsl");
        
            parser.setInput(xmlInput, "UTF-8");   
            parser.nextTag();        
            String name = parser.getName();    
          
            
            assertEquals("xsl:stylesheet", name);        
        
    }
    
    @Test
    public void testXXE() throws FileNotFoundException, IOException, XmlPullParserException  {
         
    
        
    	try {
    		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe.xml");
    		
    		parser.setInput(xmlInput, "UTF-8");   
    		parser.nextTag();        
    		String name = parser.getName();   
    		parser.next();
    		String content = parser.getText();
    		
    	}
    	catch(XmlPullParserException e) {
    		String message = "unresolved: &file;";
    		assertTrue(e.getMessage().contains(message));    		   
    	}
    	
            
        
    }
    
    @Test
    public void testXXE_netdoc() throws FileNotFoundException, IOException, XmlPullParserException  {
         
    
        
    	try {
    		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe_netdoc.xml");
    		
    		parser.setInput(xmlInput, "UTF-8");   
    		parser.nextTag();        
    		String name = parser.getName();   
    		parser.next();
    		String content = parser.getText();
    		
    	}
    	catch(XmlPullParserException e) {
    		String message = "unresolved: &file;";
    		assertTrue(e.getMessage().contains(message));    		   
    	}
    	
            
        
    }
    
    
    
    
    
    
}
