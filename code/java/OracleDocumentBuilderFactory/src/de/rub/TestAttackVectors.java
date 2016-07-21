package de.rub;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import oracle.xml.parser.v2.XMLParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestAttackVectors {

	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	
	String url = "http://127.0.0.1:5000";
	
	@Before
	public void setUp() throws Exception {
		factory = DocumentBuilderFactory.newInstance();
		
		assertTrue(factory.isExpandEntityReferences());
		assertFalse(factory.isValidating());
		assertTrue(factory.isNamespaceAware());
		
//		java.lang.UnsupportedOperationException: This parser does not support specification "null" version "null"
//		at javax.xml.parsers.DocumentBuilderFactory.isXIncludeAware(Unknown Source)
//		assertFalse(factory.isXIncludeAware());
			
	}
	
	
	@Test
	public void testDefault_noAttack() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/standard.xml"; 
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("4", content);		
			
	}
	
	@Test
	public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_core.xml";
		
	    builder = factory.newDocumentBuilder();	    
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}
	
//	obsolet
//	@Test
//	public void testDOS_core_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
//		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos/dos_core.xml"; 
//	    DocumentBuilder builder = factory.newDocumentBuilder();
//	    MyEntityResolver myEntityResolver = new MyEntityResolver();
//	    builder.setEntityResolver(myEntityResolver);
//	      
//	    		
//		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//		int expectedCount = 25;
//		// we know that each word is 3 chars long
//		int dosCount = content.length() / 3;
//		assertEquals(expectedCount, dosCount);	
//		
//	}
	
	
	@Test
	public void testDOS_core_setExpandEntityReferences () throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);		
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_core.xml";
	    
	      
	    builder = factory.newDocumentBuilder();
	    
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}	


	

	@Test
	public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_entitySize.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		int expectedCount = 3400000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount); 	
	}

	

	@Test
	public void testDOS_entitySize_setExpandEntityReferences () throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_entitySize.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount); 	
	}
	
	@Test
	public void testDOS_indirections() throws ParserConfigurationException, SAXException, IOException {
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount); 
		
		
	}
	
	@Test
	public void testDOS_indirections_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount); 
		
		
	}
	
	@Test
	public void testDOS_indirections_parameterEntity() throws ParserConfigurationException, SAXException, IOException {
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections_parameterEntity.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		
		
		 try {
	    		
			 org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Unexpected %.", message);
		}
		 	
	}
	
	
	
	@Test
	public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_recursion.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		
		
		 try {
	    		
			 org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Cyclic Entity Reference in entity 'a'.", message);
		}
		 	
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("it_works", content);		
			
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";	    
		builder = factory.newDocumentBuilder();	
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver);  	    
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	
	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }    
				
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
	    try {
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
//			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
//			assertEquals("it_works", content);		
	    }
	    catch(oracle.xml.parser.v2.XMLParseException e) {
	    	String message = e.getMessage();
//	    	String expected ="Fehlende Entity 'intern'.";
	    	String expected ="Missing entity 'intern'.";
	    	assertEquals(expected, message);
	    	
	    }		
			
	}
	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("it_works", content);		
			
	}
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml"; 
	    	      
	    		
	    builder = factory.newDocumentBuilder();		
	    try {
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
	    }
	    catch(oracle.xml.parser.v2.XMLParseException e) {
	    	String message = e.getMessage();
//	    	String expected ="Fehlende Entity 'intern'.";
	    	String expected ="Missing entity 'intern'.";
	    	assertEquals(expected, message);	    	
	    }		
	
			
	}
	
	
	
	@Test
	public void testParameterEntity_core() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();	
		assertEquals("it_works", content);		
			
	}
	
	@Test
	public void testParameterEntity_core_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml"; 
		builder = factory.newDocumentBuilder();		
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver); 
	      
	  
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    } 
	
			
	}
	
	@Test
	public void testParameterEntity_core_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
	    try {
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
	    }
	    catch(oracle.xml.parser.v2.XMLParseException e) {
	    	String message = e.getMessage();
//	    	String expected ="Fehlende Entity 'all'.";
	    	String expected ="Missing entity 'all'.";
	    	assertEquals(expected, message);	    	
	    }		
			
	}
	
	
	@Test
	public void testParameterEntity_doctype() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();	
		assertEquals("it_works", content);		
			
	}
	
	
	
	
	@Test
	public void testParameterEntity_doctype_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml";	    
		builder = factory.newDocumentBuilder();		
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver); 
	      
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }   	
			
	}
	
	
	@Test
	public void testParameterEntity_doctype_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
	    try {
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
	    }
	    catch(oracle.xml.parser.v2.XMLParseException e) {
	    	String message = e.getMessage();
	    	String expected ="Fehlende Entity 'all'.";
	    	assertEquals(expected, message);	    	
	    }		
			
	}
	
	

	
	@Test
	public void testURLInvocation_doctype() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_doctype_setEntityResolver() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml"; 
		 
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	    
        builder = factory.newDocumentBuilder();		
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver);	
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }   
	    finally {
	    	response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	    }
	    
		
				
			
        
	}
	
	@Test
	public void testURLInvocation_doctype_setExpandEntityReferences() throws Exception {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml"; 

	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        builder = factory.newDocumentBuilder();	    
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml"; 

	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml"; 

	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);

	    DocumentBuilder builder = factory.newDocumentBuilder();
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver);
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    } 
	    finally {
    	
	    	response = http.sendGet(url + "/getCounter");
	    	assertEquals("0", response);
	    }
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setExpandEntityReferences() throws Exception {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testURLInvocation_noNamespaceSchemaLocation() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml";
	  
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();
	    
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
//	 We create a DOMParser directly in this test!
	public void testURLInvocation_noNamespaceSchemaLocation_setValidationMode() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml";
	  

	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
		DOMParser parser = new DOMParser();
		 parser.setValidationMode(DOMParser.SCHEMA_VALIDATION);  
		
		parser.parse(xmlFile);
		XMLDocument doc = parser.getDocument();
		
//	    builder = factory.newDocumentBuilder();
//	    
//		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
	@Test
	public void testURLInvocation_parameterEntity() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml"; 

	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver);
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    } 
	    finally {
	    	
	    	response = http.sendGet(url + "/getCounter");
	    	assertEquals("0", response);
	    }
			
	}
	
	@Test
	public void testURLInvocation_parameterEntity_setExpandEntityReferences() throws Exception {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testURLInvocation_schemaLocation() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_schemaLocation.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	
	@Test
//	 We create a DOMParser directly in this test!
	public void testURLInvocation_schemaLocation_setValidationMode() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_schemaLocation.xml";
	  

	    
	 // reset the counter
       SimpleClient http = new SimpleClient();
       http.sendGet(url + "/reset");
       String response = http.sendGet(url + "/getCounter");
       assertEquals("0", response);
	      
       
		DOMParser parser = new DOMParser();
		 
		
		 try {
			 parser.setValidationMode(DOMParser.SCHEMA_VALIDATION);  
			 parser.parse(xmlFile);
			 XMLDocument doc = parser.getDocument();			 
		 } catch (XMLParseException e) {
//			 String expected ="Schema 'ttt' in 'http://127.0.0.1:5000/url_invocation_schemaLocation.xsd' kann nicht erstellt werden";
			 String expected ="Can not build schema 'ttt' located at 'http://127.0.0.1:5000/url_invocation_schemaLocation.xsd'";
			 assertEquals(expected, e.getMessage());
			 
		 }finally {
			 
			 
			 response = http.sendGet(url + "/getCounter");
			 assertEquals("1", response);
		 }
				
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void testURLInvocation_XInclude() throws Exception {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_xinclude.xml"; 
	    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	     
	    
        builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
  	@Test
	public void testXInclude() throws SAXException, IOException, ParserConfigurationException {
  		
  		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_xinclude.xml"; 
	    DocumentBuilder builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);
		String content = w3cDocument.getElementsByTagName("data").
				item(0).getChildNodes().item(0).getNodeName();	
		assertEquals("xi:include", content);
	}
  	
	@Test
	public void testXXE() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml"; 
		builder = factory.newDocumentBuilder();	      
	    	
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("it_works", content);		
			
	}  
	
	@Test
	public void testXXE_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml"; 
	    builder = factory.newDocumentBuilder();
	    MyEntityResolver myEntityResolver = new MyEntityResolver();
	    builder.setEntityResolver(myEntityResolver);
	    
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);	    	

	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }			
	}  
	
	@Test
	public void testXXE_setExpandEntityReferences() throws ParserConfigurationException, SAXException, IOException {
		
		factory.setExpandEntityReferences(false);
		assertFalse(factory.isExpandEntityReferences());
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml"; 
	    builder = factory.newDocumentBuilder();
	      
	    
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("", content);		
			
	}  
	
	
	@Test
	public void testXXE_netdoc() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe_netdoc.xml"; 
		builder = factory.newDocumentBuilder();	      
	    	
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getTextContent();
		assertEquals("it_works", content);		
			
	}  
	
	

	@Test
	public void testXSLT() throws ParserConfigurationException, SAXException, IOException {
		
		String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/optional/xslt.xsl"; 
	    
	      
	    builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);
		String content = w3cDocument.getFirstChild().getNodeName();
		assertEquals("xsl:stylesheet", content);		
			
	}  

	
	

}
