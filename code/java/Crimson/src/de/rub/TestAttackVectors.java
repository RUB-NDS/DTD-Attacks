package de.rub;

import static org.junit.Assert.*;
import de.rub.SimpleClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

/**
 * 
 * @author dev 
 *Test cases for Crimson SAX
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestAttackVectors {

	SAXParserFactory factory;
	
	String provider ="org.apache.crimson.jaxp.SAXParserFactoryImpl";
	
	String url = "http://127.0.0.1:5000";

	//	frequently occuring messages
	final String _DOCTYPE_ = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
	final String _DECL_HANDLER_INTERNAL_ = "Entities not allowed";	
	final String _DECL_HANDLER_EXTERNAL_ = "External Entities not allowed";	
	    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
	
    	factory = SAXParserFactory.newInstance(provider, null);
    	
    	// default configuration
    	assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
    	assertFalse(factory.getFeature("http://xml.org/sax/features/validation"));
    	// not supported
//    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		// not supported
//		assertFalse(factory.getFeature("http://apache.org/xml/features/xinclude"));	
//		assertTrue(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
//		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
//		assertFalse(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
	
    }

    @After
    public void tearDown() throws Exception {
    }
    
    
    @Test 
    public void testDefault_noAttack() throws ParserConfigurationException, SAXException, IOException {
    	
			
		assertFalse(factory.isNamespaceAware());
		assertFalse(factory.isValidating());
//		assertFalse(factory.isXIncludeAware());
//		
		
		//Check that the features correspond to methods in factory
		
//		factory.setFeature("http://xml.org/sax/features/namespaces", true);
//		assertTrue(factory.isNamespaceAware());
//		factory.setFeature("http://xml.org/sax/features/namespaces", false);
//		assertFalse(factory.isNamespaceAware());
//		
//		// XSDs Schemas have no effect		
//		factory.setFeature("http://apache.org/xml/features/validation/schema",true);
//		assertFalse(factory.isValidating());
//		
//		
//		// DTD Schemas 
//		factory.setFeature("http://xml.org/sax/features/validation",true);
//		assertTrue(factory.isValidating());
//		factory.setFeature("http://xml.org/sax/features/validation",false);
//		assertFalse(factory.isValidating());
		
//		// XInclude
//		factory.setFeature("http://apache.org/xml/features/xinclude",true);
//		assertTrue(factory.isXIncludeAware());
//		factory.setFeature("http://apache.org/xml/features/xinclude",false);
//		assertFalse(factory.isXIncludeAware());
		// Watch out for namespaces; They must also be activated in order to process XIncludes
		//Use this: factory.setFeature("http://xml.org/sax/features/namespaces", true);
		
		
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
    	
    	SAXParser saxParser = factory.newSAXParser();	        
    	
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        saxParser.parse(xmlInput, myDefaultHandler);
        
        assertEquals("4", myDefaultHandler.getElementContent("data"));    	
    }
    
    
    @Test 
    public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_core.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        
	    String content =myDefaultHandler.getElementContent("data");
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
    }
    
    @Test 
    public void testDOS_core_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_core.xml");    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        
        SAXParser saxParser = factory.newSAXParser();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }  	
        finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    @Test 
    public void testDOS_indirections() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_indirections.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        
	    String content =myDefaultHandler.getElementContent("data");
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
    }
    
    @Test 
    public void testDOS_indirections_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_indirections.xml");    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        
        SAXParser saxParser = factory.newSAXParser();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }  	
        finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    @Test 
    public void testDOS_indirections_parameterEntity() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_indirections_parameterEntity.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        
        try {
    		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Only external parameter entities may use \"%a;\" in entity values.", message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);
        }       
        
		   	
    }
    
    
    @Test 
    public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_entitySize.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        
	    String content =myDefaultHandler.getElementContent("data");
		int expectedCount = 3400000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
    }
    
    @Test 
    public void testDOS_entitySize_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_entitySize.xml");    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        
        SAXParser saxParser = factory.newSAXParser();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }  	
        finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    @Test 
    public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos/dos_recursion.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        
        try {
    		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Expansion of entity \"a\" is recursive.", message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("a", content);
        }       
        
		   	
    }
    
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
		String content = myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {

    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
        
    	MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	
	        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
        }    
        catch (SAXNotSupportedException e) {
//	    	String message ="External Entities not allowed";
	    	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	String content = mySecureDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    
//    @Test
//    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws ParserConfigurationException, SAXException, IOException {
// 	
//        
//		try {
//			factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
//		    assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		    
//		} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-general-entities";
//			assertEquals(message, e.getMessage());
//		}	
//		finally {
//    	
//			InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//        
//	    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//	        MyDeclHandler myDeclHandler = new MyDeclHandler();
//	        SAXParser saxParser = factory.newSAXParser();
//	        
//	        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//	        saxParser.parse(xmlInput, myDefaultHandler);
//	        
//        	String content = myDefaultHandler.getElementContent("data");
//        	assertEquals("it_works", content);
//        }
//	
//    }
    
//    @Test
//    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws ParserConfigurationException, SAXException, IOException {
//
//		try {
//	    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//		} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-parameter-entities";
//			assertEquals(message, e.getMessage());
//		}	
//		finally {
//	    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//	        
//	    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//	        MyDeclHandler myDeclHandler = new MyDeclHandler();
//	        SAXParser saxParser = factory.newSAXParser();	        
//	        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//	        saxParser.parse(xmlInput, myDefaultHandler);
//	        
//        	String content = myDefaultHandler.getElementContent("data");
//        	assertEquals("it_works", content);
//        }
//	
//    }
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();  
        SAXParser saxParser = factory.newSAXParser();
              
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message ="External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }  
		finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
    @Test
    public void testInternalSubset_PEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
//    @Test
//    public void testInternalSubset_PEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
//    	
//    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
//        
//    	MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
//        MyDeclHandler myDeclHandler = new MyDeclHandler();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//        
//        try {
//        	
//        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
//	    }    
//	    catch (SAXNotSupportedException e) {
//	    	String message ="Entities not allowed";
//	    	assertEquals(message, e.getMessage());
//	    }  
//		finally {
//        	String content = mySecureDefaultHandler.getElementContent("data");
//        	assertEquals("it_works", content);
//        }
//    }
    
    @Test
    public void testInternalSubset_PEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message ="Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        } 
		finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
    @Test
    public void testParameterEntity_core() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();      
        
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    
    @Test
    public void testParameterEntity_core_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();      
        
        
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
	    }    
	    catch (SAXNotSupportedException e) {
	    	String message ="External Entities not allowed";
	    	assertEquals(message, e.getMessage());
	    }  
        finally {
    		String content =mySecureDefaultHandler.getElementContent("data");
    		assertEquals("", content);        	
        }
	
//		String content =myDefaultHandler.getElementContent("data");
//		assertEquals("it_works", content);
    }
    
    
//    @Test
//    public void testParameterEntity_core_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
//    	
//
//		try {
//			factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-general-entities";
//			assertEquals(message, e.getMessage());
//		}
//		finally {
//			
//			InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
//			
//			MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//			
//			SAXParser saxParser = factory.newSAXParser();
//			
//			saxParser.parse(xmlInput, myDefaultHandler);    
//			
//			
//			String content =myDefaultHandler.getElementContent("data");
//			assertEquals("it_works", content);
//		}		
//   }
    
    
//    @Test
//    public void testParameterEntity_core_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {
//    
//
//    	try {
//    		
//	    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//    	
//    	} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-parameter-entities";
//			assertEquals(message, e.getMessage());
//		}
//    	finally {
//    		
//    		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
//    		
//    		MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//    		
//    		SAXParser saxParser = factory.newSAXParser();
//    		
//    		saxParser.parse(xmlInput, myDefaultHandler);    
//    		
//    		
//    		String content =myDefaultHandler.getElementContent("data");
//    		assertEquals("it_works", content);
//    	}
//    	
//    	
//    }
    
    
    @Test
    public void testParameterEntity_core_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();      
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message ="Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);        	
        }

    }
    
    
    @Test
    public void testParameterEntity_doctype() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();      
        
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    
//    @Test
//    public void testParameterEntity_doctype_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
//    	
//
//		try {
//			factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-general-entities";
//			assertEquals(message, e.getMessage());
//		}
//		finally {
//			
//			InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
//			
//			MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//			
//			SAXParser saxParser = factory.newSAXParser();
//			
//			saxParser.parse(xmlInput, myDefaultHandler); 
//			
//			String content =myDefaultHandler.getElementContent("data");
//			assertEquals("it_works", content);
//		}		
//   }
    
    
//    @Test
//    public void testParameterEntity_doctype_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {
//    
//
//    	try {
//    		
//	    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
//    	
//    	} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-parameter-entities";
//			assertEquals(message, e.getMessage());
//		}
//    	finally {
//    		
//    		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
//    		
//    		MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//    		
//    		SAXParser saxParser = factory.newSAXParser();
//    		
//    		saxParser.parse(xmlInput, myDefaultHandler);        		
//    		
//    		String content =myDefaultHandler.getElementContent("data");
//    		assertEquals("it_works", content);
//    	}
//    }
    
    @Test
    public void testParameterEntity_doctype_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();      
        
        
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
	    }    
	    catch (SAXNotSupportedException e) {
	    	String message ="External Entities not allowed";
	    	assertEquals(message, e.getMessage());
	    }    
        finally {
    		String content =mySecureDefaultHandler.getElementContent("data");
    		assertEquals("", content);        	
        }
	

    }
    
    @Test
    public void testParameterEntity_doctype_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();      
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message ="Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }  	
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);        	
        }

    }
    
    
    
  
    
    
    @Test
    public void testURLInvocation_doctype() throws Exception{
	
        
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
                
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_doctype.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();        
        saxParser.parse(xmlInput, myDefaultHandler);
        
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
        try {    
            
        	InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_doctype.xml");            
            MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
            SAXParser saxParser = factory.newSAXParser();            
            saxParser.parse(xmlInput, mySecureDefaultHandler);
            
            

        }
        catch (SAXNotSupportedException e) {
        	String message ="External Entities not allowed";
        	assertEquals(message, e.getMessage());
        }
        finally {
        	response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
        }
        
    }
    
    
    @Test
    public void testURLinvocation_doctype_setProperty_DeclHandler() throws Exception{
	
        
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
                
        InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_doctype.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        
    	saxParser.parse(xmlInput, myDefaultHandler);
          	
    	response = http.sendGet(url + "/getCounter");
    	assertEquals("1", response);
        
    }
    
    @Test
    public void testURLInvocation_externalGeneralEntity() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
        
    }
    
    @Test
    public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();		     
        SAXParser saxParser = factory.newSAXParser();
                
        
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
	    }    
	    catch (SAXNotSupportedException e) {
	    	String message ="External Entities not allowed";
	    	assertEquals(message, e.getMessage());
	    }      
        finally {
        	
        	response = http.sendGet(url + "/getCounter");
        	assertEquals("0", response);
        }
        
        
    }
    
    
//    @Test
//    public void testURLInvocation_externalGeneralEntity_setFeature_general_general_entities() throws Exception {
//    
//       	// reset the counter
//        SimpleClient http = new SimpleClient();
//        http.sendGet(url + "/reset");
//        String response = http.sendGet(url + "/getCounter");
//        assertEquals("0", response);
//
//    	try {
//    		
//	    	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//	    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    	
//    	} catch (SAXNotSupportedException e) {
//			String message ="Feature: http://xml.org/sax/features/external-general-entities";
//			assertEquals(message, e.getMessage());
//		}
//    	finally {
//    		
//    		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
//    		
//    		MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//    		
//    		SAXParser saxParser = factory.newSAXParser();
//    		
//    		saxParser.parse(xmlInput, myDefaultHandler);    
//    		
//    		   		
//    		response = http.sendGet(url + "/getCounter");
//        	assertEquals("1", response);
//    	}
//    	
//    }
    
    
    @Test
    public void testURLInvocation_externalGeneralEntity_setProperty_DeclHandler() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);       
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	
        	response = http.sendGet(url + "/getCounter");
        	assertEquals("0", response);
        }
        
        
    }
    

    @Test
    public void testURLInvocation_parameterEntity() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url + "/getCounter");
        assertEquals("1", response);
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();		     
        SAXParser saxParser = factory.newSAXParser();
                
        
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
	    }    
	    catch (SAXNotSupportedException e) {
	    	String message ="External Entities not allowed";
	    	assertEquals(message, e.getMessage());
	    }      
        finally {
        	
        	response = http.sendGet(url + "/getCounter");
        	assertEquals("0", response);
        }
        
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_setProperty_DeclHandler() throws Exception{
	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);       
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	
        	response = http.sendGet(url + "/getCounter");
        	assertEquals("0", response);
        }
        
        
    }
    
    
    @Test
    public void testURLInvocation_noNamespaceSchemaLocation() throws Exception{
  	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
  			
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
    }
    
    
    
    @Test
    public void testURLInvocation_schemaLocation() throws Exception{
  	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
  			
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
    }
    

	
    
    
    
    @Test
    public void testURLInvocation_XInclude() throws Exception{
  	
    	// reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url + "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
  			
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);;
        
    }   
    
    

    @Test
    public void testXInclude() throws IOException, SAXException, ParserConfigurationException {   		
	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	    
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("xi:includexi:include", content);
		
	//	this will fail because XInclude is not supported	
		content = myDefaultHandler.getElementContent("content");
		assertEquals("", content);
	
    }   
    
    
    @Test
    public void testXSLT() throws IOException, SAXException, ParserConfigurationException {   		
	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/xslt.xsl");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.parse(xmlInput, myDefaultHandler);
		
      	// ugly coding;
       // other solutions are more complex and require to keep state of document structure
      	String name = myDefaultHandler.getResult().get(0);

		assertEquals("xsl:stylesheet", name);
	
    }   
    
    
   
    
    @Test
    public void testXXE() throws IOException,SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        SAXParser saxParser = factory.newSAXParser();              
        
        saxParser.parse(xmlInput, myDefaultHandler);	    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    @Test
    public void testXXE_setEntityResolver() throws IOException,SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();	
        SAXParser saxParser = factory.newSAXParser();              
        
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
	    }    
	    catch (SAXNotSupportedException e) {
	    	String message ="External Entities not allowed";
	    	assertEquals(message, e.getMessage());
	    }	
        finally {
        	String content = mySecureDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
      
    }
    
    
    
    @Test
    public void testXXE_setProperty_setProperty_DeclHandler() throws IOException,SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe.xml");        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();        
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);     
                       
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String message = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	String content = myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }       

    }
    
    
    
    @Test
    public void testXXE_netdoc() throws IOException,SAXException, ParserConfigurationException {
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe/xxe.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        SAXParser saxParser = factory.newSAXParser();              
        
        saxParser.parse(xmlInput, myDefaultHandler);	    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
   
    

    
    
    
   
    
    
    

    
    
	 

    

//  /**
//  * We developed this attack ourselves using a combination of the Parameter Entity Attack 
//  * and Inclusion of External Entities in Attribute Values.
//  * This facilitates to read out a text file (no xml!) completely, not just the first line! 
//  * This only works if the Parser processes DTDs AND is validating against an XSD Schema (should be viable quite often)
//  *  
//  *  It seems we are not the first one to think about that idea
//  *  https://media.blackhat.com/eu-13/briefings/Osipov/bh-eu-13-XML-data-osipov-slides.pdf
//  *  However Slide 38 states for Java Xerces:
//  *  Cons:  Can’t read multiline files with OOB technique
//  *  from which we presume that they did NOT try this combination!
//  * @throws ParserConfigurationException 
//  */
// @Test
// public void testParameterEntity_Attack_AttributeValue() throws Exception {
//		
//		
//		
//		assertFalse(factory.isNamespaceAware());
//		factory.setNamespaceAware(true);
//		assertTrue(factory.isNamespaceAware());
//			
//		factory.setFeature("http://xml.org/sax/features/validation",true);
//	
//
//		
//		InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/schemaEntity_noSchema.xml");
//     
//     MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//     MyDeclHandler myDeclHandler = new MyDeclHandler();
//     SAXParser saxParser = factory.newSAXParser();
//     
//     saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//     
//     
// 	SimpleClient http = new SimpleClient();
//    http.sendGet("http://localhost:9000/reset");
//    String response = http.sendGet("http://localhost:9000/getCounter");
//    assertEquals("0", response);
//    
//    saxParser.parse(xmlInput, myDefaultHandler); 
//    
//    saxParser.parse(xmlInput, myDefaultHandler);
//    response = http.sendGet("http://localhost:9000/getCounter");
//    assertEquals("1", response);
//    
//    http.sendGet("http://localhost:9000/reset");
//    response = http.sendGet("http://localhost:9000/getCounter");
//    assertEquals("0", response); 
//    
//     
//		
//		assertEquals("http://192.168.2.31/root:root sample:sample",
//				 myDefaultHandler.getAttributeValue("noNamespaceSchemaLocation"));
// }
    
    
}
