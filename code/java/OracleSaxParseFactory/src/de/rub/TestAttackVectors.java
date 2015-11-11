package de.rub;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
 

/**
 *  
 * @author dev
 * OLD implementation; Refer to TestSAXParser
 * The feature EXPAND_ENTITYREF is not accessible;
 * Test cases are left as is; but may be incomplete compared to TestSAXParser
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestAttackVectors {

	SAXParserFactory factory;
	
	String provider =
	  		  "oracle.xml.jaxp.JXSAXParserFactory";
	
	
	String url = "http://127.0.0.1:5000";
	
	
	//frequently occuring messages
	final String _DECL_HANDLER_INTERNAL_ = "Entities not allowed";	
	final String _DECL_HANDLER_EXTERNAL_ = "External Entities not allowed";
	
	@Before
	public void setUp() throws Exception {
		
    	factory = SAXParserFactory.newInstance(provider, null);
		
		
		// default configuration
    	assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
    	// not recognized
//    	assertTrue(factory.getFeature("http://xml.org/sax/features/use-entity-resolver2"));
    	assertFalse(factory.getFeature("http://xml.org/sax/features/validation"));
		
		assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	// not recognized		
//		assertFalse(factory.getFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD"));
    	
		assertFalse(factory.getFeature("http://xml.org/sax/features/namespace-prefixes"));
		assertFalse(factory.getFeature("http://xml.org/sax/features/string-interning"));
		// not recognized
//		assertTrue(factory.getFeature("http://xml.org/sax/features/lexical-handler/parameter-entities"));
			//http://xml.org/sax/features/is-standalone
		// not recognized
//		assertTrue(factory.getFeature("http://xml.org/sax/features/resolve-dtd-uris"));
		// not recognized
//		assertFalse(factory.getFeature("http://xml.org/sax/features/unicode-normalization-checking"));
			//http://xml.org/sax/features/use-attributes2
			//http://xml.org/sax/features/use-locator2
		// not recognized
//		assertFalse(factory.getFeature("http://xml.org/sax/features/xmlns-uris"));
			// http://xml.org/sax/features/xml-1.1
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefault_noAttack() throws SAXException, IOException, ParserConfigurationException {		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("4", content);
	}
	
	
	@Test
	public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);			
	}
	
	@Test
	public void testDOS_core_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	parser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }		
	}
	
	

	
	
	@Test
	public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 3400000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}
	
	
	@Test
	public void testDOS_entitySize_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	parser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
		
	}
	
	@Test
	public void testDOS_indirections() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}
	
	
	@Test
	public void testDOS_indirections_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	parser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
		
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.parse(xmlInput, mySecureDefaultHandler);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}	
		
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws ParserConfigurationException, SAXException, IOException {
			
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		try {
			factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
		}
		catch(SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "SAX-Feature 'http://xml.org/sax/features/external-general-entities' nicht unterstützt.";
			assertEquals(expected, message);
		}
		
//		SAXParser parser = factory.newSAXParser();	
//		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//		parser.parse(xmlInput, myDefaultHandler);
//		String content = myDefaultHandler.getElementContent("data");
//		
//		assertEquals("it_works", content);
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		
		try {
			factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
		}
		catch(SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "SAX-Feature 'http://xml.org/sax/features/external-parameter-entities' nicht unterstützt.";
			assertEquals(expected, message);
		}
//		
//		SAXParser parser = factory.newSAXParser();	
//		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//		parser.parse(xmlInput, myDefaultHandler);
//		String content = myDefaultHandler.getElementContent("data");
//		
//		assertEquals("it_works", content);
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.parse(xmlInput, myDefaultHandler);
		}
		catch (SAXException e) {
			assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
		}	
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
		
		
	}
	
	
	
	
	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.parse(xmlInput, myDefaultHandler);
		}
		catch (SAXException e) {
			assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
		}	
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
		
		
	}
	

	@Test
	public void testParameterEntity_core() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}	
	
	
	@Test
	public void testParameterEntity_core_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.parse(xmlInput, mySecureDefaultHandler);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}	
	}
	
	
	@Test
	public void testParameterEntity_core_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.parse(xmlInput, myDefaultHandler);
		}
		catch (SAXException e) {
			assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
		}	
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
	}	
	
	
	

	

	@Test
	public void testParameterEntity_doctype() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	@Test
	public void testParameterEntity_doctype_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.parse(xmlInput, mySecureDefaultHandler);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}
	}
	

	@Test
	public void testParameterEntity_doctype_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.parse(xmlInput, myDefaultHandler);
		}
		catch (SAXException e) {
			assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
		}	
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
	}
	
	
	
	@Test
	public void testURLInvocation_doctype() throws Exception {
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");
				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_doctype_setEntityResolver() throws Exception {
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.parse(xmlInput, mySecureDefaultHandler);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}finally {			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
		}
				

	}
	
	@Test
	public void testURLInvocation_doctype_setProperty_DeclHandler() throws Exception {
	    
		 // reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
		      
	        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
			
			SAXParser parser = factory.newSAXParser();	
			MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
			MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
			parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
			
			try {
				parser.parse(xmlInput, myDefaultHandler);
			}
			catch (SAXException e) {
				assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
			}	
			finally {				
		        response = http.sendGet(url + "/getCounter");
		        assertEquals("1", response);
			}
					

		}
	
	
	

	

	@Test
	public void testURLInvocation_externalGeneralEntity() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");

    	SAXParser parser = factory.newSAXParser();	
    	MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
    	MyEntityResolver myEntityResolver = new MyEntityResolver();
    	
    	try {
    		parser.parse(xmlInput, mySecureDefaultHandler);
    		String content = mySecureDefaultHandler.getElementContent("data");
    	}
    	catch (SAXNotSupportedException e) {
    		String message = e.getMessage();
    		String expected = "External Entities not allowed";
    		assertEquals(expected, message);
    				
    	}finally {			
            response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
    	}
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setProperty_DeclHandler() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");

		
    	SAXParser parser = factory.newSAXParser();	
    	MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.parse(xmlInput, myDefaultHandler);
    	}
    	catch (SAXException e) {
    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
    	}	
    	finally {				
            response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
    	}   				
		
	}
	

	
	

	
	
	@Test
	public void testURLInvocation_noNamespaceSchemaLocation() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
		
		SAXParser parser = factory.newSAXParser();	    
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	
	@Test
	public void testURLInvocation_parameterEntity() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
		SAXParser parser = factory.newSAXParser();	    
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
	@Test
	public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
    	SAXParser parser = factory.newSAXParser();	
    	MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
    	MyEntityResolver myEntityResolver = new MyEntityResolver();
    	
    	try {
    		parser.parse(xmlInput, mySecureDefaultHandler);
    		String content = mySecureDefaultHandler.getElementContent("data");
    	}
    	catch (SAXNotSupportedException e) {
    		String message = e.getMessage();
    		String expected = "External Entities not allowed";
    		assertEquals(expected, message);
    				
    	}finally {			
            response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
    	}
    	
	}
	
	
	@Test
	public void testURLInvocation_parameterEntity_setProperty_DeclHandler() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");

		
    	SAXParser parser = factory.newSAXParser();	
    	MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.parse(xmlInput, myDefaultHandler);
    	}
    	catch (SAXException e) {
    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
    	}	
    	finally {				
            response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
    	}   				
		
	}
	
	

	
	@Test
	public void testURLInvocation_schemaLocation() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_schemaLocation.xml");
		
		SAXParser parser = factory.newSAXParser();	    
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testURLInvocation_XInclude() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_xinclude.xml");
		
		SAXParser parser = factory.newSAXParser();	    
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		String content = myDefaultHandler.getElementContent("data");				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testXInclude() throws SAXException, IOException, ParserConfigurationException {		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("xi:includexi:include", content);
	}
	
	@Test
	public void testXXE() throws SAXException, IOException, ParserConfigurationException {		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	@Test
	public void testXXE_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
		
		SAXParser parser = factory.newSAXParser();	
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.parse(xmlInput, mySecureDefaultHandler);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}
	}

	
	@Test
	public void testXXE_setProperty_setProperty_DeclHandler() throws Exception {
	      
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");

		
    	SAXParser parser = factory.newSAXParser();	
    	MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.parse(xmlInput, myDefaultHandler);
    	}
    	catch (SAXException e) {
    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
    	}	
    	finally {				
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);	
    	}   				
		
	}

	
	
	@Test
	public void testXSLT() throws SAXException, IOException, ParserConfigurationException {		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/xslt.xsl");
		
		SAXParser parser = factory.newSAXParser();	
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.parse(xmlInput, myDefaultHandler);
		
		String content = myDefaultHandler.getElementContent("xsl:template");
		content = content.trim();
		assertEquals("xsl:value-ofxsl:value-of", content);
	}
	

}
