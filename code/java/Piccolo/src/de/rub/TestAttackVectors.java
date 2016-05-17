package de.rub;

import static org.junit.Assert.*;

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
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

import com.bluecast.xml.Piccolo;

/**
 * 
 * @author dev 
 *Test cases for Piccolo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestAttackVectors {
//	this does not work all the way, i.e. when setting features for XXE.
//	  String provider =
//    		  "com.bluecast.xml.JAXPSAXParserFactory";
//	  
	 
//	  SAXParserFactory factory;
	
	  Piccolo myPiccolo;
	  MyDefaultHandler myDefaultHandler;
	  
	  String url = "http://127.0.0.1:5000";
	  
	  
	//frequently occuring messages		
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
		
		
		 myPiccolo = new Piccolo();
		 myDefaultHandler   = new MyDefaultHandler(); 
		 myPiccolo.setContentHandler(myDefaultHandler);

		
//		factory = SAXParserFactory.newInstance(provider, null);	
    	
    	// default configuration		
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/is-standalone"));
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/lexical-handler/parameter-entities"));
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/namespaces"));
		assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/namespace-prefixes"));
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/resolve-dtd-uris"));
		assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/string-interning"));
//		Not supported
//		assertFalse(factory.getFeature("http://xml.org/sax/features/unicode-normalization-checking"));
		
		assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/use-attributes2"));
		assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/use-locator2"));
//		Not supported
//		assertFalse(factory.getFeature("http://xml.org/sax/features/use-entity-resolver2"));
		
		assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/validation"));    	
//		Not supported
//		assertFalse(factory.getFeature("http://xml.org/sax/features/xmlns-uris"));		
//		assertFalse(factory.getFeature("http://xml.org/sax/features/xml-1.1"));

		
		
	}
	

	@After
	public void tearDown() throws Exception {
	}

	
	@Test 
	public void testDefault_noAttack() throws ParserConfigurationException, SAXException, IOException {
	    	    	
		 			 
//	    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
//	    	
//	    	SAXParser saxParser = factory.newSAXParser();	        
//	    	MyDeclHandler myDeclHandler = new MyDeclHandler();
//	    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//	    	
//	    	saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//	                
//	        saxParser.parse(xmlInput, myDefaultHandler);
//	        
//	        assertEquals("4", myDefaultHandler.getElementContent("data"));
		
		


    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        myPiccolo.parse("../../xml_files_windows/standard.xml");
        
        assertEquals("4", myDefaultHandler.getElementContent("data"));  
		
	    }
	    
	    
	    @Test 
	    public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        myPiccolo.parse("../../xml_files_windows/dos_core.xml");   
	        
	        String expected = "&a1;&a1;&a1;&a1;&a1;";
		    assertEquals( expected, myDeclHandler.getEntityValue("a2"));
	        
		    String content =myDefaultHandler.getElementContent("data");
			int expectedCount = 25;
			// we know that each word is 3 chars long
			int dosCount = content.length() / 3;
			assertEquals(expectedCount, dosCount); 
	    	
	    }    
	    
	    
	    @Test
	    public void testDOS_core_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
	    		        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	        myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	        
	        try {
	        	myPiccolo.parse("../../xml_files_windows/dos_core.xml");
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
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        myPiccolo.parse("../../xml_files_windows/dos_indirections.xml");   
 
	        
		    String content =myDefaultHandler.getElementContent("data");
			int expectedCount = 10000;
			// we know that each word is 3 chars long
			int dosCount = content.length() / 3;
			assertEquals(expectedCount, dosCount); 
	    	
	    }   
	    
	    @Test
	    public void testDOS_indirections_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
	    		        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	        myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	        
	        try {
	        	myPiccolo.parse("../../xml_files_windows/dos_indirections.xml");
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
	    	
			
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);          
	        
	        
	        try {
	    		myPiccolo.parse("../../xml_files_windows/optional/dos_indirections_parameterEntity.xml");	
	        	
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals("Parameter entities may not appear in the internal subset", message);
			}
	        finally {
	    		String content =myDefaultHandler.getElementContent("data");
	    		assertEquals("", content);
	        }       
	        
			   	
	    }
	    
	    
	    @Test 
	    public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        myPiccolo.parse("../../xml_files_windows/dos_entitySize.xml");   
 
	        
		    String content =myDefaultHandler.getElementContent("data");
			int expectedCount = 3400000;
			// we know that each word is 3 chars long
			int dosCount = content.length() / 3;
			assertEquals(expectedCount, dosCount); 
	    	
	    }   
	    
	    @Test
	    public void testDOS_entitySize_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
	    		        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	        myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	        
	        try {
	        	myPiccolo.parse("../../xml_files_windows/dos_entitySize.xml");
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
	    public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
	    	
			
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();    	
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);          
	        
	        
	        try {
	    		myPiccolo.parse("../../xml_files_windows/optional/dos_recursion.xml");	
	        	
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals("Recursive reference to entity 'null'", message);
			}
	        finally {
	    		String content =myDefaultHandler.getElementContent("data");
	    		assertEquals("a", content);
	        }       
	        
			   	
	    }
	    
	    
	    
	    
	    
	    
	    @Test
		public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
			MyDeclHandler myDeclHandler = new MyDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	    	
	    	myPiccolo.parse("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
			
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
		}
	    

	    @Test
		public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
			MyDeclHandler myDeclHandler = new MyDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	    	MyEntityResolver myEntityResolver = new MyEntityResolver();
	    	myPiccolo.setEntityResolver(myEntityResolver);
	    	
	    	try {
	    		
	    		myPiccolo.parse("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
	    	}
	    	catch (SAXNotSupportedException e) {
	    		String message = "External Entities not allowed";
	    		assertEquals(message, e.getMessage());
	    	}
	    	finally {
				String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}
			
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
//	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));

		}
	    
	    
	    @Test
		public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws ParserConfigurationException, SAXException, IOException {
			
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	    	
	    	myPiccolo.parse("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
			
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
		}
	    
	    
	    @Test
		public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws ParserConfigurationException, SAXException, IOException {
			
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
	    	// This is a problem in the implementation of the getFeature() method.
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
	    	// The value of feature external-general-entities is reported! 
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);
	    	// Verify this assumption:
	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
	    	
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	    	
	    	myPiccolo.parse("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
			
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
	        assertEquals("",myDeclHandler.getEntityValue("intern"));
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("", content);
		}
	    
	    
	    @Test
		public void testInternalSubset_ExternalPEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
			MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	    	
	    	try {
	    		myPiccolo.parse("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
	    	}
	    	catch (SAXException e ){
//	    		String message ="External Entities not allowed";
	    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
	    	}
	    	finally {
	    		String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}
	    	
			
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
//	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
			
		}
	    
	    @Test
	    public void testInternalSubset_PEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
	    	
	    	MyDeclHandler myDeclHandler = new MyDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	    	
	    	myPiccolo.parse("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
			
	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
	    	
	    }
	    
//	    obsolet
//	    @Test
//	    public void testInternalSubset_PEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
//	    	
//	    	MyDeclHandler myDeclHandler = new MyDeclHandler();
//	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//	    	MyEntityResolver myEntityResolver = new MyEntityResolver();
//	    	myPiccolo.setEntityResolver(myEntityResolver);
//	    		    		
//	    	myPiccolo.parse("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
//	    		    	
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
//	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
//			String content =myDefaultHandler.getElementContent("data");
//			assertEquals("it_works", content);	    	
//	    }
	    
	    @Test
	    public void testInternalSubset_PEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
	    	
	    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	    	
	    	try {
	    		myPiccolo.parse("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
	    	}
	    	catch (SAXException e ){
//	    		String message ="Entities not allowed";
	    		assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
	    	}
	    	finally {
	    		String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}
//	        assertEquals("<!ENTITY intern 'it_works'>",myDeclHandler.getEntityValue("%internal"));
//	        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
//			String content =myDefaultHandler.getElementContent("data");
//			assertEquals("it_works", content);
	    	
	    }
	    
	    

	    
	    @Test
	    public void testParameterEntity_core() throws IOException, SAXException, ParserConfigurationException {

	        myPiccolo.parse("../../xml_files_windows/parameterEntity_core.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);			
	    }
	    
	    @Test
	    public void testParameterEntity_core_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {

	    	MyEntityResolver myEntityResolver = new MyEntityResolver();
	    	myPiccolo.setEntityResolver(myEntityResolver);
	    	
	    	try {	    		
	    		myPiccolo.parse("../../xml_files_windows/parameterEntity_core.xml");
	    	}
	    	catch (SAXNotSupportedException e) {
	    		String message = "External Entities not allowed";
	    		assertEquals(message, e.getMessage());
	    	}
	    	finally {
				String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}		
	    }
	    
//	    obsolet
//	    @Test
//	    public void testParameterEntity_core_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
//
//	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));	   		
//
//	        myPiccolo.parse("../../xml_files_windows/parameterEntity_core.xml");    	    
//	    	
//	    	String content =myDefaultHandler.getElementContent("data");
//	    	assertEquals("it_works", content);  	
//	    	
//	   }
	    
	    
	    @Test
	    public void testParameterEntity_core_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {
	    	
	    	    	
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
	    		
	        myPiccolo.parse("../../xml_files_windows/parameterEntity_core.xml");    
	    	
	    	String content =myDefaultHandler.getElementContent("data");
	    	assertEquals("", content);	    	
	    }
	    
	    @Test
	    public void testParameterEntity_core_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {

	    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	    	
	    	try {
	    		myPiccolo.parse("../../xml_files_windows/parameterEntity_core.xml");	    		
	    	}
	    	catch (SAXException e ){
//	    		String message ="Entities not allowed";
	    		assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
	    	}
	    	finally {
	    		String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}	
		
	    }
	    
	    
	    
	    @Test
	    public void testParameterEntity_doctype() throws IOException, SAXException, ParserConfigurationException {
	    	
	        myPiccolo.parse("../../xml_files_windows/parameterEntity_doctype.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
			
	    }
	    
	    @Test
	    public void testParameterEntity_doctype_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {
	    	
	    	MyEntityResolver myEntityResolver = new MyEntityResolver();
	    	myPiccolo.setEntityResolver(myEntityResolver);
	    	
	    	try {
	    		
	    		myPiccolo.parse("../../xml_files_windows/parameterEntity_doctype.xml");
	    	}
	    	catch (SAXNotSupportedException e) {
	    		String message = "External Entities not allowed";
	    		assertEquals(message, e.getMessage());
	    	}
	    	finally {
				String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}			
	    }
	    
	    @Test
	    public void testParameterEntity_doctype_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
	    	
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));
	    	
	    	try {
	    		
	    		myPiccolo.parse("../../xml_files_windows/parameterEntity_doctype.xml");
	    	}
	    	catch (SAXParseException e){
	    		String message ="Reference to undefined entity: all";
	    		assertEquals(message, e.getMessage());
	    		
	    	}
	    	finally {
	    		
	    		String content =myDefaultHandler.getElementContent("data");
	    		assertEquals("", content);
	    	}
		
			
	    }
	    
	    
	    @Test
	    public void testParameterEntity_doctype_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {
	    	
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));
	    	
	        myPiccolo.parse("../../xml_files_windows/parameterEntity_doctype.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("%goodies;", content);
			
	    }
	    
	    
	    
	    @Test
	    public void testParameterEntity_doctype_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {
	    	
	    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	    	
	    	try {	    		
	    		myPiccolo.parse("../../xml_files_windows/parameterEntity_doctype.xml");
	    	}
	    	catch (SAXException e ){
//	    		String message ="Entities not allowed";
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
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_doctype.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	    }
	    
	    @Test
	     public void testURLInvocation_doctype_setEntityResolver() throws Exception {
	 
	    	// 	reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response); 
    	
	        MyEntityResolver myEntityResolver = new MyEntityResolver();
	        myPiccolo.setEntityResolver(myEntityResolver);
	        
	    	 try {
	    		 myPiccolo.parse("../../xml_files_windows/url_invocation_doctype.xml");
	    	 }
	    	 catch(SAXNotSupportedException e) {
	    		 String message ="External Entities not allowed";
	    		 assertEquals(message, e.getMessage());
	    		 
	    	 }	    
	    	 finally {
	 	        response = http.sendGet(url + "/getCounter");
		        assertEquals("0", response);
	    	 }
	     }
	    
	    
	    @Test
	    public void testURLInvocation_doctype_setFeature_external_general_entities() throws Exception{
	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	
	        
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_doctype.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	    }
	    
	    @Test
	    public void testURLInvocation_doctype_setFeature_external_parameter_entities() throws Exception{
	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	
	        
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
	    	// This is a problem in the implementation of the getFeature() method.
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));	    	
	    	
	        myPiccolo.parse("../../xml_files_windows/url_invocation_doctype.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	    }
	    
	    
	    
	    @Test
	    public void testURLInvocation_doctype_setProperty_DeclHandler() throws Exception{
		    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);

	        	
	        myPiccolo.parse("../../xml_files_windows/url_invocation_doctype.xml");

	        
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
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	        
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
	        
	        MyEntityResolver myEntityResolver = new MyEntityResolver();
   		 	myPiccolo.setEntityResolver(myEntityResolver);
	        
   		 	try {
   		 		
   		 		myPiccolo.parse("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
   		 	}
   		 	catch(SAXNotSupportedException e) {
    		 String message ="External Entities not allowed";
    		 assertEquals(message, e.getMessage());    		 
   		 	}
   		 	finally {
   		 		
   		 		response = http.sendGet(url + "/getCounter");
   		 		assertEquals("0", response);	    		
   		 	}
	        
	    }
	    
	    @Test
	    public void testURLInvocation_externalGeneralEntity_setFeature_external_general_entities() throws Exception{
		
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);
		    assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));		    
	    		    		
	    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);	    		
	    }
	    
	    
	    @Test
	    public void testURLInvocation_externalGeneralEntity_setProperty_DeclHandler() throws Exception{
		
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
	    	try {	    		
	    		myPiccolo.parse("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	    	}
	    	catch (SAXException e ){
//	    		String message ="External Entities not allowed";
	    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
	    	}
	    	finally {	    		
	    		response = http.sendGet(url + "/getCounter");
	    		assertEquals("0", response);	    		
	    	}	
	        
	    }
	    
	    @Test
	    public void testURLInvocation_noNamespaceSchemaLocation() throws Exception{
		
	    	
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/namespaces"));
	    		    		    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	    }
	    
	    @Test
	    public void testURLInvocation_parameterEntity() throws Exception{
		
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_parameterEntity.xml");
	        
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
	        
	        MyEntityResolver myEntityResolver = new MyEntityResolver();
   		 	myPiccolo.setEntityResolver(myEntityResolver);
	        
   		 	try {
   		 		
   		 		myPiccolo.parse("../../xml_files_windows/url_invocation_parameterEntity.xml");
   		 	}
   		 	catch(SAXNotSupportedException e) {
    		 String message ="External Entities not allowed";
    		 assertEquals(message, e.getMessage());    		 
   		 	}
   		 	finally {
   		 		
   		 		response = http.sendGet(url + "/getCounter");
   		 		assertEquals("0", response);	    		
   		 	}
	        
	    }
	    
	    @Test
	    public void testURLInvocation_parameterEntity_setFeature_external_general_entities() throws Exception{
		
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
	    	assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));	    		
	    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_parameterEntity.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);	    		
	    }
	    
	    
	    
	    @Test
	    public void testURLInvocation_parameterEntity_setFeature_external_parameter_entities() throws Exception{
		
	    	myPiccolo.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/external-parameter-entities"));	    		
	    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_parameterEntity.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);	    		
	    }
	    
	    
	    @Test
	    public void testURLInvocation_parameterEntity_setProperty_DeclHandler() throws Exception{
		
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
	    	myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
	    	try {	    		
	    		myPiccolo.parse("../../xml_files_windows/url_invocation_parameterEntity.xml");
	    	}
	    	catch (SAXException e ){
//	    		String message ="External Entities not allowed";
	    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
	    	}
	    	finally {	    		
	    		response = http.sendGet(url + "/getCounter");
	    		assertEquals("0", response);	    		
	    	}	
	        
	    }
	    
	    @Test
	    public void testURLInvocation_schemaLocation() throws Exception{
		
	    	
	    	assertTrue(myPiccolo.getFeature("http://xml.org/sax/features/namespaces"));
	    		    		    	
	    	// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_schemaLocation.xml");
	        
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
	        
	        myPiccolo.parse("../../xml_files_windows/url_invocation_xinclude.xml");
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	    		
	    }
	    
	    @Test
	    public void testXInclude() throws IOException, SAXException, ParserConfigurationException {   		
	    	
	        myPiccolo.parse("../../xml_files_windows/xinclude.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			
			assertEquals("xi:includexi:include", content);
		//	assertNotEquals("contentsecretcontent",content);
			
		//	this will fail because XInclude is not supported	
			content = myDefaultHandler.getElementContent("content");
			assertEquals("", content);
		
	    }
	    
	    
	    @Test
	    public void testXSLT() throws IOException, SAXException, ParserConfigurationException {   		
	    	
	        myPiccolo.parse("../../xml_files_windows/optional/xslt.xsl");
	        
	    	// ugly coding;
	        // other solutions are more complex and require to keep state of document structure
	       	String name = myDefaultHandler.getResult().get(0);
	       	assertEquals("xsl:stylesheet", name);	
		
	    }
	    
	    
	    @Test
	    public void testXXE() throws IOException,SAXException, ParserConfigurationException {	    	

	        myPiccolo.parse("../../xml_files_windows/xxe.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
			
	    }
	    
	    @Test
	    public void testXXE_setEntityResolver() throws IOException,SAXException, ParserConfigurationException {	    	

	    	MyEntityResolver myEntityResolver = new MyEntityResolver();
	    	myPiccolo.setEntityResolver(myEntityResolver);
	    	
	    	try {    		
	    		myPiccolo.parse("../../xml_files_windows/xxe.xml");
	    	}
	    	catch (SAXNotSupportedException e) {
	    		String message ="External Entities not allowed";
	    		 assertEquals(message, e.getMessage());    	
	    	}
	    	finally {
	    		String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content);
	    	}
	    }
	    
	    
	    @Test
	    public void testXXE_setFeature_external_general_entities() throws SAXNotRecognizedException, SAXNotSupportedException, IOException,SAXException, ParserConfigurationException {	
					
			myPiccolo.setFeature("http://xml.org/sax/features/external-general-entities",false);
		    assertFalse(myPiccolo.getFeature("http://xml.org/sax/features/external-general-entities"));		    
	    	

	        myPiccolo.parse("../../xml_files_windows/xxe.xml");
		
			String content =myDefaultHandler.getElementContent("data");			
			assertEquals("", content);
			
		    
	    }
	    
	    
	    @Test
	    public void testXXE_setProperty_setProperty_DeclHandler() throws IOException,SAXException, ParserConfigurationException {	    	

	    	MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
		    myPiccolo.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
	        
			try {
				
				myPiccolo.parse("../../xml_files_windows/xxe.xml");
			}		
			catch (SAXException e ){
//	    		String message ="External Entities not allowed";
	    		assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
	    	}
	    	finally {	    		
				String content =myDefaultHandler.getElementContent("data");
				assertEquals("", content); 		
	    	}	
			
	    }
	    
	    
	    @Test
	    public void testXXE_netdoc() throws IOException,SAXException, ParserConfigurationException {	    	

	        myPiccolo.parse("../../xml_files_windows/xxe/xxe_netdoc.xml");
		
			String content =myDefaultHandler.getElementContent("data");
			assertEquals("it_works", content);
			
	    }
	    
	    
	    

////	    /**
////	     * We developed this attack ourselves using a combination of the Parameter Entity Attack 
////	     * and Inclusion of External Entities in Attribute Values.
////	     * This facilitates to read out a text file (no xml!) completely, not just the first line! 
////	     * This only works if the Parser processes DTDs AND is validating against an XSD Schema (should be viable quite often)
////	     *  
////	     *  It seems we are not the first one to think about that idea
////	     *  https://media.blackhat.com/eu-13/briefings/Osipov/bh-eu-13-XML-data-osipov-slides.pdf
////	     *  However Slide 38 states for Java Xerces:
////	     *  Cons:  Can’t read multiline files with OOB technique
////	     *  from which we presume that they did NOT try this combination!
////	     * @throws ParserConfigurationException 
////	     */
////	    @Test
////	    public void testParameterEntity_Attack_AttributeValue() throws IOException, SAXException, ParserConfigurationException {
//	//
////	    	// check features of factory here
////	    	assertFalse(factory.getFeature("http://xml.org/sax/features/validation"));
////	    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema"));
////			assertTrue(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
////			assertTrue(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
////			assertFalse(factory.getFeature("http://apache.org/xml/features/xinclude"));	
////			assertTrue(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
////			assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
////			assertFalse(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
////			assertFalse(factory.isNamespaceAware());
////			
////			
////			
////			assertFalse(factory.isNamespaceAware());
////			factory.setNamespaceAware(true);
////			assertTrue(factory.isNamespaceAware());
////				
////			factory.setFeature("http://apache.org/xml/features/validation/schema",true);
//	//	
//	//
////			
////			InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/schemaEntity_utf16.xml");
////	        
////	        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
////	        MyDeclHandler myDeclHandler = new MyDeclHandler();
////	        SAXParser saxParser = factory.newSAXParser();
////	        
////	        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
////	        
////	        saxParser.parse(xmlInput, myDefaultHandler);    
////			
////			assertEquals("http://192.168.2.31/root:root sample:sample",
////					 myDefaultHandler.getAttributeValue("noNamespaceSchemaLocation"));
////	    }
//	    
//	    
}
