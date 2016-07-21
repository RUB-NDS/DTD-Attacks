package de.rub;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import oracle.xml.parser.v2.SAXParser;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import oracle.xml.parser.v2.XMLParseException;

/**
 * 
 * @author dev 
 *Test cases for Oracle SAX
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)  
public class TestSAXParser {

	SAXParser parser;
	
	
	String url = "http://127.0.0.1:5000";
	
	
	//frequently occuring messages
	final String _DECL_HANDLER_INTERNAL_ = "Entities not allowed";	
	final String _DECL_HANDLER_EXTERNAL_ = "External Entities not allowed";
	
	@Before
	public void setUp() throws Exception {
		
		 parser = new SAXParser();
		
//    	factory = SAXParserFactory.newInstance(provider, null);
		
		
		// default configuration
    	assertFalse(parser.getFeature("http://xml.org/sax/features/namespaces"));
    	// not recognized
//    	assertTrue(factory.getFeature("http://xml.org/sax/features/use-entity-resolver2"));
    	assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	// not recognized		
//		assertFalse(factory.getFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD"));
    	
		assertTrue(parser.getFeature("http://xml.org/sax/features/namespace-prefixes"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/string-interning"));
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
		
		// NullPointerException; Feature is not initialized unless explicitly set by user
//		assertEquals(null, parser.getAttribute(SAXParser.EXPAND_ENTITYREF).toString());
	
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefault_noAttack() throws SAXException, IOException, ParserConfigurationException {		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";
		
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler); 
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("4", content);
	}
	
	
	@Test
	public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_core.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);			
	}
	
	@Test
	public void testDOS_core_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_core.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);			
	}
	
	
	
	

	
	@Test
	public void testDOS_core_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_core.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
			parser.setContentHandler(myDefaultHandler);
        	parser.parse(xmlInput);
			
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
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_entitySize.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 3400000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}
	
	@Test
	public void testDOS_entitySize_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_entitySize.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);			
	}
	
	
	
	@Test
	public void testDOS_entitySize_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_entitySize.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	parser.setContentHandler(myDefaultHandler);
        	parser.parse(xmlInput);
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
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);	
		
	}
	
	
	@Test
	public void testDOS_indirections_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		int expectedCount = 0;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);			
	}
	
	
	@Test
	public void testDOS_indirections_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	parser.setContentHandler(myDefaultHandler);
        	parser.parse(xmlInput);
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
	 public void testDOS_indirections_parameterEntity() throws ParserConfigurationException, SAXException, IOException {
	    	    	    		        
	    	String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_indirections_parameterEntity.xml";
			
			
			MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
			parser.setContentHandler(myDefaultHandler);
			
	        
	        try {
	    		
	        	parser.parse(xmlInput);        
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals("Unexpected %.", message);
			}
	        finally {
	    		String content =myDefaultHandler.getElementContent("data");
	    		assertEquals("f;", content);
	        }       
	        
			   	
	    }
	
	
	
    @Test 
    public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
    	    	    		        
    	String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/dos/dos_recursion.xml";
		
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		
        
        try {
    		
        	parser.parse(xmlInput);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Cyclic Entity Reference in entity 'a'.", message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("a", content);
        }       
        
		   	
    }
	
	
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		
		try {
			parser.parse(xmlInput);
			String content = myDefaultHandler.getElementContent("data");
		}
		catch(XMLParseException e) {
			String message = e.getMessage();
//			String expected = "Fehlende Entity 'intern'.";
			String expected = "Missing entity 'intern'.";
			assertEquals(expected, message);
			
		}
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
		
		
		
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
			
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.setContentHandler(mySecureDefaultHandler);
			parser.setEntityResolver(mySecureDefaultHandler);
			parser.parse(xmlInput);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}	
        finally {
        	String content =mySecureDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
		
	}
	
	
//	@Test
//	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws ParserConfigurationException, SAXException, IOException {
//			
//		
//		
//		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
//		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//		try {
//			parser.setFeature("http://xml.org/sax/features/external-general-entities",false);
//			parser.parse(xmlInput);
//
//		}
//		catch(SAXNotSupportedException e) {
//			String message = e.getMessage();
//			String expected = "SAX-Feature 'http://xml.org/sax/features/external-general-entities' nicht unterstützt.";
//			assertEquals(expected, message);
//		}	
//        finally {
//        	String content =myDefaultHandler.getElementContent("data");
//        	assertEquals("", content);
//        }	
//		
//			
//		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
//		parser.parse(xmlInput);
//		String content = myDefaultHandler.getElementContent("data");
//		
//		assertEquals("it_works", content);
//	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		try {
			parser.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
			parser.parse(xmlInput);
		}
		catch(SAXNotSupportedException e) {
			String message = e.getMessage();
//			String expected = "SAX-Feature 'http://xml.org/sax/features/external-parameter-entities' nicht unterstützt.";
			String expected = "SAX feature 'http://xml.org/sax/features/external-parameter-entities' not supported.";
			assertEquals(expected, message);
		}
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
	}
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.setContentHandler(myDefaultHandler);
			parser.parse(xmlInput);
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
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		
		
		
		
		try {
			parser.parse(xmlInput);
			String content = myDefaultHandler.getElementContent("data");
		}
		catch(XMLParseException e) {
			String message = e.getMessage();
//			String expected = "Fehlende Entity 'intern'.";
			String expected = "Missing entity 'intern'.";
			assertEquals(expected, message);
			
		}	
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	

	}
	
	

	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml";
		
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.setContentHandler(myDefaultHandler);
			parser.parse(xmlInput);
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
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}	
	
	@Test
	public void testParameterEntity_core_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(parser.EXPAND_ENTITYREF, false);
		
		
		try {
			parser.parse(xmlInput);
			String content = myDefaultHandler.getElementContent("data");
		}
		catch(XMLParseException e) {
			String message = e.getMessage();
//			String expected = "Fehlende Entity 'all'.";
			String expected = "Missing entity 'all'.";
			assertEquals(expected, message);
			
		}
		finally {
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	

	}	
	
	
	@Test
	public void testParameterEntity_core_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml";
		
			
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.setContentHandler(mySecureDefaultHandler);
			parser.setEntityResolver(mySecureDefaultHandler);
			parser.parse(xmlInput);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}	
		finally {
			String content =mySecureDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
	}
	
	
	@Test
	public void testParameterEntity_core_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.setContentHandler(myDefaultHandler);
			parser.parse(xmlInput);
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
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	@Test
	public void testParameterEntity_doctype_setAttribute_EXPAND_ENTITYREF() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(parser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("", content);
	}
	
	
	@Test
	public void testParameterEntity_doctype_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml";
			
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		
		try {
			parser.setContentHandler(mySecureDefaultHandler);
			parser.setEntityResolver(mySecureDefaultHandler);
			parser.parse(xmlInput);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);
					
		}
		finally {
			String content =mySecureDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}	
		

		
	}
	

	@Test
	public void testParameterEntity_doctype_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml";
		
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
		
		try {
			parser.setContentHandler(myDefaultHandler);
			parser.parse(xmlInput);
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml";
			
		
		parser.parse(xmlInput);

				
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_doctype_setAttribute_EXPAND_ENTITYREF() throws Exception {
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml";
			
		parser.setAttribute(parser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);

				
			
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml";
			
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		
		
		try {
			parser.setEntityResolver(mySecureDefaultHandler);
			parser.parse(xmlInput);			
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
		      
	        
	        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_doctype.xml";
				
			
			MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
			parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
			
			parser.parse(xmlInput);
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);		

		}
	
	
	

	

	@Test
	public void testURLInvocation_externalGeneralEntity() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";
		
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setAttribute_EXPAND_ENTITYREF() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";
		
        parser.setAttribute(parser.EXPAND_ENTITYREF, false);
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	
	@Test
	public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception {
    
	    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";
    		
    	MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
    	
    	
    	try {
    		parser.setEntityResolver(mySecureDefaultHandler);
    		parser.parse(xmlInput);    		
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";
		
    		
    	
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.parse(xmlInput);
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml";
			    
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testURLInvocation_noNamespaceSchemaLocation_setValidationMode() throws Exception {
		
		assertFalse(parser.getFeature("http://xml.org/sax/features/namespaces"));
		parser.setFeature("http://xml.org/sax/features/namespaces", true);
		assertTrue(parser.getFeature("http://xml.org/sax/features/namespaces"));
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml";
			    
		
        parser.setValidationMode(parser.SCHEMA_VALIDATION);			
		parser.parse(xmlInput);
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	
    
	@Test
	public void testURLInvocation_parameterEntity() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml";
			    
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
	}
	
	@Test
	public void testURLInvocation_parameterEntity_setAttribute_EXPAND_ENTITYREF() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml";
			    
		parser.setAttribute(parser.EXPAND_ENTITYREF, false);
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	
	
	@Test
	public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml";
    		
    	MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();    	
    	
    	try {
    		parser.setEntityResolver(mySecureDefaultHandler);
    		parser.parse(xmlInput);
    		
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_parameterEntity.xml";
		
    		
    	
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.parse(xmlInput);
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
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_schemaLocation.xml";
			    
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	
	@Test
	public void testURLInvocation_schemaLocation_setValidationMode() throws Exception {
	    

		
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      
        
        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_schemaLocation.xml";
		try {
			
			parser.setValidationMode(parser.SCHEMA_VALIDATION);		
			parser.parse(xmlInput);
		}
		catch (XMLParseException e) {
//			String expected = "Schema 'ttt' in 'http://127.0.0.1:5000/url_invocation_schemaLocation.xsd' kann nicht erstellt werden";
			String expected = "Can not build schema 'ttt' located at 'http://127.0.0.1:5000/url_invocation_schemaLocation.xsd'";
			assertEquals(expected, e.getMessage());
		}
		finally{
			
			response = http.sendGet(url + "/getCounter");
			assertEquals("1", response);
		}
						
			
	}
	
	
	
	
	@Test
	public void testURLInvocation_XInclude() throws Exception {
			    
	 // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	      

        String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_xinclude.xml";
			    
		
		parser.parse(xmlInput);
						
			
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
	}
	
	@Test
	public void testXInclude() throws SAXException, IOException, ParserConfigurationException {		
		
	       String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("xi:includexi:include", content);
	}
	
	@Test
	public void testXXE() throws SAXException, IOException, ParserConfigurationException {		
		
	    String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("it_works", content);
	}
	
	
	
	@Test
	public void testXXE_setAttribute_EXPAND_ENTITYREF() throws SAXException, IOException, ParserConfigurationException {		
		
		
		
	    String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml";
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.setAttribute(SAXParser.EXPAND_ENTITYREF, false);
		
		parser.parse(xmlInput);
		
		String content = myDefaultHandler.getElementContent("data");
		
		assertEquals("", content);
	}
	
	
	@Test
	public void testXXE_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml";
			
		MySecureDefaultHandler mySecureDefaultHandler   = new MySecureDefaultHandler();
		MyEntityResolver myEntityResolver = new MyEntityResolver();
		
		try {
			parser.setContentHandler(mySecureDefaultHandler);
			parser.setEntityResolver(mySecureDefaultHandler);
			parser.parse(xmlInput);
			String content = mySecureDefaultHandler.getElementContent("data");
		}
		catch (SAXNotSupportedException e) {
			String message = e.getMessage();
			String expected = "External Entities not allowed";
			assertEquals(expected, message);					
		} finally {
        	String content =mySecureDefaultHandler.getElementContent("data");
        	assertEquals("", content);	
		}
	
	}
	
	
	@Test
	public void testXXE_setFeature_external_general_entities () throws ParserConfigurationException, SAXException, IOException {
			
		
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml";
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		try {
			parser.setFeature("http://xml.org/sax/features/external-general-entities",false);
			parser.parse(xmlInput);

		}
		catch(SAXNotSupportedException e) {
			String message = e.getMessage();
//			String expected = "SAX-Feature 'http://xml.org/sax/features/external-general-entities' nicht unterstützt.";
			String expected = "SAX feature 'http://xml.org/sax/features/external-general-entities' not supported.";
			assertEquals(expected, message);
		}	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }	
		
	}
		
	
	

	
	@Test
	public void testXXE_setProperty_DeclHandler() throws Exception {
	      
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe/xxe.xml";
		
    		
    	MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		parser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
    	
    	try {
    		parser.setContentHandler(myDefaultHandler);
    		parser.parse(xmlInput);
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
		
		String xmlInput ="file:///C:/Christopher_Spaeth/code/xml_files_windows/optional/xslt.xsl";	
			
		MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
		parser.setContentHandler(myDefaultHandler);
		parser.parse(xmlInput);
		
		// ugly coding;
	    // other solutions are more complex and require to keep state of document structure
	    String name = myDefaultHandler.getResult().get(0);

		assertEquals("xsl:stylesheet", name);
		
//		String content = myDefaultHandler.getElementContent("xsl:template");
//		content = content.trim();
//		assertEquals("xsl:value-ofxsl:value-of", content);
	}
	

}
