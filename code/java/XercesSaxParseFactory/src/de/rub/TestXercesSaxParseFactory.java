package de.rub;

import static org.junit.Assert.*;
import de.rub.SimpleClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.util.SecurityManager;
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
 * 
 
 */

/**
 * 
 * @author dev
 *
 * Test cases for Xerces DOM *
 * The test case "testDOS_entitySize" is not activated by default, because it takes too much time;
*  Uncomment it, if you like to run it 
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestXercesSaxParseFactory {

	SAXParserFactory factory;
	
	String provider =		  "org.apache.xerces.jaxp.SAXParserFactoryImpl";
	
	String url = "http://127.0.0.1:5000";
	
	//frequently occuring messages
	final String _DOCTYPE_ = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
	final String _DECL_HANDLER_INTERNAL_ = "Entities not allowed";	
	final String _DECL_HANDLER_EXTERNAL_ = "External Entities not allowed";	
	final String _SECURITYMANAGER_ = "The parser has encountered more than \"-1\" entity expansions in this document; this is the limit imposed by the application.";
    
    @Before
    public void setUp() throws Exception {
	
    	factory = SAXParserFactory.newInstance(provider, null);	
    	
    	// default configuration
    	assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
    	assertTrue(factory.getFeature("http://xml.org/sax/features/use-entity-resolver2"));
    	assertFalse(factory.getFeature("http://xml.org/sax/features/validation"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/dynamic"));

    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema-full-checking"));
    	assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema/normalized-value"));
    	assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema/element-default"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema/augment-psvi"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/generate-synthetic-annotations"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/validate-annotations"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/honour-all-schemaLocations"));


		assertTrue(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		
				
		assertTrue(factory.getFeature("http://apache.org/xml/features/validation/id-idref-checking"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/validation/identity-constraint-checking"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/validation/unparsed-entity-checking"));


		assertFalse(factory.getFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/warn-on-duplicate-entitydef"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/allow-java-encodings"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/continue-after-fatal-error"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
		assertFalse(factory.getFeature("http://apache.org/xml/features/scanner/notify-char-refs"));
		//deviates from specification
		assertTrue(factory.getFeature("http://apache.org/xml/features/scanner/notify-builtin-refs"));

		assertFalse(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		assertFalse(factory.getFeature("http://apache.org/xml/features/standard-uri-conformant"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude/fixup-base-uris"));
		assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude/fixup-language"));

		
	
					
		assertFalse(factory.getFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD"));
			
					
					
						
		//SAX2 Features	
		// deviates from specification 
		assertTrue(factory.getFeature("http://xml.org/sax/features/namespace-prefixes"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/string-interning"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/lexical-handler/parameter-entities"));
			//http://xml.org/sax/features/is-standalone
		assertTrue(factory.getFeature("http://xml.org/sax/features/resolve-dtd-uris"));
		assertFalse(factory.getFeature("http://xml.org/sax/features/unicode-normalization-checking"));
			//http://xml.org/sax/features/use-attributes2
			//http://xml.org/sax/features/use-locator2
		assertFalse(factory.getFeature("http://xml.org/sax/features/xmlns-uris"));
			// http://xml.org/sax/features/xml-1.1

		

    }

    @After
    public void tearDown() throws Exception {
    }
    
    
    @Test 
    public void testDefault_noAttack() throws ParserConfigurationException, SAXException, IOException {
    	
    	// check features of factory here
    	assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
    	assertFalse(factory.getFeature("http://xml.org/sax/features/validation"));
    	assertFalse(factory.getFeature("http://apache.org/xml/features/validation/schema"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/xinclude"));	
		assertTrue(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		assertFalse(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
				
		assertFalse(factory.isNamespaceAware());
		assertFalse(factory.isValidating());
		assertFalse(factory.isXIncludeAware());
		
		
		//Check that the features correspond to methods in factory
		
		factory.setFeature("http://xml.org/sax/features/namespaces", true);
		assertTrue(factory.isNamespaceAware());
		factory.setFeature("http://xml.org/sax/features/namespaces", false);
		assertFalse(factory.isNamespaceAware());
		
		// XSDs Schemas have no effect		
		factory.setFeature("http://apache.org/xml/features/validation/schema",true);
		assertFalse(factory.isValidating());
		
		
		// DTD Schemas 
		factory.setFeature("http://xml.org/sax/features/validation",true);
		assertTrue(factory.isValidating());
		factory.setFeature("http://xml.org/sax/features/validation",false);
		assertFalse(factory.isValidating());
		
		// XInclude
		factory.setFeature("http://apache.org/xml/features/xinclude",true);
		assertTrue(factory.isXIncludeAware());
		factory.setFeature("http://apache.org/xml/features/xinclude",false);
		assertFalse(factory.isXIncludeAware());
		// Watch out for namespaces; They must also be activated in order to process XIncludes
		//Use this: factory.setFeature("http://xml.org/sax/features/namespaces", true);
		
		
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/standard.xml");
    	
    	SAXParser saxParser = factory.newSAXParser();	        
    	MyDeclHandler myDeclHandler = new MyDeclHandler();
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
    	
    	saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
                
        saxParser.parse(xmlInput, myDefaultHandler);
        
        assertEquals("4", myDefaultHandler.getElementContent("data"));    	
    }
    
    
    @Test 
    public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
    	
	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        
        String expected = "&a1;&a1;&a1;&a1;&a1;";
	    assertEquals(expected, myDeclHandler.getEntityValue("a2"));
        
	    String content =myDefaultHandler.getElementContent("data");
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
    }
    
    @Test 
    public void testDOS_core_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}       

    }
    
    
    @Test 
    public void testDOS_core_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
        
	
    }
    
    @Test
    public void testDOS_core_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_core.xml");
        
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
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    @Test 
    public void testDOS_indirections() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        
//        String expected = "&a1;&a1;&a1;&a1;&a1;";
//	    assertEquals(expected, myDeclHandler.getEntityValue("a2"));
        
	    String content =myDefaultHandler.getElementContent("data");
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
    }
    
    
    @Test 
    public void testDOS_indirections_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
    }
    
    @Test 
    public void testDOS_indirections_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
        
	
    }
    
    @Test
    public void testDOS_indirections_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_indirections.xml");
        
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
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    
    @Test 
    public void testDOS_indirections_parameterEntity() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/dos_indirections_parameterEntity.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        
        try {
    		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("The parameter entity reference \"%a;\" cannot occur within markup in the internal subset of the DTD.", message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);
        }       
        
		   	
    }
    
    
    
    
    // TODO This test does not seem to finish;
//          prepare for long (infinite?) runtime; get a coffee in the meantime... or two
//    @Test 
//    public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
//    	
//		
//    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
//    	    		        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//        MyDeclHandler myDeclHandler = new MyDeclHandler();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//        
//        saxParser.parse(xmlInput, myDefaultHandler);      
//        
//
//        
//	    String content =myDefaultHandler.getElementContent("data");
//		int expectedCount = 3400000;
//		// we know that each word is 3 chars long
//		int dosCount = content.length() / 3;
//		assertEquals(expectedCount, dosCount);   	
//    }
    
    
    @Test 
    public void testDOS_entitySize_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
    }
    
    
    @Test 
    public void testDOS_entitySize_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
        
	
    }
    
    @Test
    public void testDOS_entitySize_setProperty_DeclHandler() throws SAXException, IOException, ParserConfigurationException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/dos_entitySize.xml");
        
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
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    
    @Test 
    public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/dos_recursion.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        
        try {
    		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals("Recursive entity reference \"a\". (Reference path: a -> b -> a),", message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("a", content);
        }       
        
		   	
    }
    
    
    
   
    
    
    
    
//    /**
//     * Attack from Source: http://lab.onsec.ru/2014/06/xxe-oob-exploitation-at-java-17.html 
//     * 
//     * 
//     */
//    @Test
//    public void testParameterEntity_sendFTP() throws IOException, SAXException, ParserConfigurationException {
//	
//		
//		InputStream xmlInput = new FileInputStream("../../xml_files_windows/paramEntity_doctype.xml");
//        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.parse(xmlInput, myDefaultHandler);    
//	
//		String content =myDefaultHandler.getElementContent("data");
//		assertEquals("xxe", content);
//    }
        

    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    @Test 
    public void testInternalSubset_ExternalPEReferenceInDTD_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);
        }
    }
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
    	
    	MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
    	
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        try {
        	
        	saxParser.parse(xmlInput, mySecureDefaultHandler);    
        	
        	
//        	assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
//        	String content =myDefaultHandler.getElementContent("data");
//        	assertEquals("it_works", content);
        	
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
    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_disallow_doctype_decl () throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);    
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
	     

    }
    
//    obsolet;
//    @Test
//    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws ParserConfigurationException, SAXException, IOException {
//    	
//    	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    	
//    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
//        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//        MyDeclHandler myDeclHandler = new MyDeclHandler();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//        
//        saxParser.parse(xmlInput, myDefaultHandler);    
//	
//        
//        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
//		String content =myDefaultHandler.getElementContent("data");
//		assertEquals("it_works", content);
//    }
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("", content);
    }
    
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities_validation() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
    	factory.setFeature("http://xml.org/sax/features/validation", true);    		
    	assertTrue(factory.getFeature("http://xml.org/sax/features/validation"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("", content);
    }
    
    
    
    
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_load_dtd_grammar() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    
    
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_load_external_dtd() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
        
        assertEquals("it_works",myDeclHandler.getEntityValue("intern"));
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
    
    
    
    
    
    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
        
    	
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();       
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);     
          
	
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
        	String meldung = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
        
        
//        assertEquals("it_works",mySecureDeclHandler.getEntityValue("intern"));
//		String content =myDefaultHandler.getElementContent("data");
//		assertEquals("it_works", content);
    }
    
    

    @Test
    public void testInternalSubset_PEReferenceInDTD() throws ParserConfigurationException, SAXException, IOException {
    	
  	   	
    
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
        
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
    
    
    @Test 
    public void testInternalSubset_PEReferenceInDTD_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);
        }
    }
    
//    obsolet;
//    @Test
//    public void testInternalSubset_PEReferenceInDTD_setEntityResolver() throws ParserConfigurationException, SAXException, IOException {
//    	
//    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
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
//        	
//        }
//        catch (SAXNotSupportedException e) {
//        	String message ="Entities not allowed";
//        	assertEquals(message, e.getMessage());
//        }
//        finally {
//        	String content =mySecureDefaultHandler.getElementContent("data");
//        	assertEquals("it_works", content);
//        }
//
//    }
    
    
    @Test
    public void testInternalSubset_PEReferenceInDTD_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);    
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
    @Test
    public void testInternalSubset_PEReferenceInDTD_setProperty_DeclHandler() throws ParserConfigurationException, SAXException, IOException {
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);        
            
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String meldung = "Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
    @Test
    public void testParameterEntity_core() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
   

    @Test 
    public void testParameterEntity_core_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
    	
		
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
    	    		        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
        //using a negative number is more effective - especially for mitigating XXE Attacks
        securityManager.setEntityExpansionLimit(-1);
        saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
        
        try {
		
        	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
        finally {
    		String content =myDefaultHandler.getElementContent("data");
    		assertEquals("", content);
        }
    }
    
    @Test
    public void testParameterEntity_core_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
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
    public void testParameterEntity_core_setFeature_disallow_doctype_decl() throws IOException, SAXException, ParserConfigurationException {
    	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);    
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
    }
    
    
    
    
   
    
//    obsolet
//    @Test
//    public void testParameterEntity_core_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
//    	
//    		
//    	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    		
//		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
//        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//        MyDeclHandler myDeclHandler = new MyDeclHandler();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//        
//        saxParser.parse(xmlInput, myDefaultHandler);    
//    	    
//    	
//    	String content =myDefaultHandler.getElementContent("data");
//    	assertEquals("it_works", content);
//   }
    
    
    @Test
    public void testParameterEntity_core_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {

		
    		
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
    	    
    	
    	String content =myDefaultHandler.getElementContent("data");
    	assertEquals("", content);
    	
    }
    
    @Test
    public void testParameterEntity_core_setFeature_load_dtd_grammar() throws IOException, SAXException, ParserConfigurationException {
	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    } 
    
    
    
    @Test
    public void testParameterEntity_core_setFeature_load_external_dtd() throws IOException, SAXException, ParserConfigurationException {
	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    } 
    
    
    @Test
    public void testParameterEntity_core_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_core.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String meldung = "Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
    
    
    
    
    
    
    
    @Test
    public void testParameterEntity_doctype() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }
    
  @Test 
  public void testParameterEntity_doctype_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
  	
		
  	InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
  	    		        
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
      //using a negative number is more effective - especially for mitigating XXE Attacks
      securityManager.setEntityExpansionLimit(-1);
      saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
      
      try {
		
      	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
      finally {
  		String content =myDefaultHandler.getElementContent("data");
  		assertEquals("", content);
      }
  }
    
    
    @Test
    public void testParameterEntity_doctype_setEntityResolver() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
            
	
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
    public void testParameterEntity_doctype_setFeature_disallow_doctype_decl() throws IOException, SAXException, ParserConfigurationException {
	
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        try {
        	
        	saxParser.parse(xmlInput, myDefaultHandler);    
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }
        
    }
    
    
//    obsolet;
//    @Test
//    public void testParameterEntity_doctype_setFeature_external_general_entities() throws IOException, SAXException, ParserConfigurationException {
//	
//    	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    	
//		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
//        
//        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//        MyDeclHandler myDeclHandler = new MyDeclHandler();
//        SAXParser saxParser = factory.newSAXParser();
//        
//        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//        
//        saxParser.parse(xmlInput, myDefaultHandler);    
//	
//		String content =myDefaultHandler.getElementContent("data");
//		assertEquals("it_works", content);
//    }
    
    @Test
    public void testParameterEntity_doctype_setFeature_external_parameter_entities() throws IOException, SAXException, ParserConfigurationException {
	
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("", content);
    }
    
    @Test
    public void testParameterEntity_doctype_setFeature_load_dtd_grammar() throws IOException, SAXException, ParserConfigurationException {
	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
    }  
    
    
    @Test
    public void testParameterEntity_doctype_setFeature_load_external_dtd() throws IOException, SAXException, ParserConfigurationException {
	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        saxParser.parse(xmlInput, myDefaultHandler);    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("", content);
    }  
    
  
    
    @Test
    public void testParameterEntity_doctype_setProperty_DeclHandler() throws IOException, SAXException, ParserConfigurationException {
	
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/parameterEntity_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        
            
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String meldung = "Entities not allowed";
        	assertEquals(_DECL_HANDLER_INTERNAL_, e.getMessage());
        }	
        finally {
        	String content =myDefaultHandler.getElementContent("data");
        	assertEquals("", content);
        }

    }
    
    
 
    
    

    
  
    

    @Test
    public void testURLInvocation_doctype() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        response = http.sendGet(url+ "/getCounter");
        assertEquals("1", response);  	
    }
    

    @Test
    public void testURLInvocation_doctype_SecurityManager () throws Exception{
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
    	
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
    	MyDeclHandler myDeclHandler = new MyDeclHandler();
    	SAXParser saxParser = factory.newSAXParser();
    	
    	saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 SimpleClient http = new SimpleClient();
    	
    	org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
    	//using a negative number is more effective - especially for mitigating XXE Attacks
    	securityManager.setEntityExpansionLimit(-1);
    	saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
    	
    	
    	
    	 //reset the counter
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
    	
           
        
    	try {
    		
    		saxParser.parse(xmlInput, myDefaultHandler);        
    	} catch (SAXParseException e) {
    		String message = e.getMessage();
    		assertEquals(_SECURITYMANAGER_,message);
    	}
    	finally {
    		response = http.sendGet(url+ "/getCounter");
            assertEquals("1", response);  	
    	}
    }
    
    
    
    
    
    
    
  
    
    @Test
    public void testURLInvocation_doctype_setEntityResolver() throws Exception {
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
        
        MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        
        SAXParser saxParser = factory.newSAXParser();

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        try {	
            saxParser.parse(xmlInput, mySecureDefaultHandler);
        }
        catch (SAXNotSupportedException e) {
        	String message ="External Entities not allowed";
        	assertEquals(message, e.getMessage());
        }
        finally {
        	response = http.sendGet(url+ "/getCounter");
            assertEquals("0", response);           
        }
        
    }
    
    @Test
    public void testURLInvocation_doctype_setFeature_disallow_doctype_decl() throws Exception{
	
			
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        
        try {        	
        	saxParser.parse(xmlInput, myDefaultHandler);   
            
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
        }
    }
    
    
    @Test
    public void testURLInvocation_doctype_setFeature_load_dtd_grammar() throws Exception{
    	
		
  		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);
  		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
  		
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
          
          MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
          MyDeclHandler myDeclHandler = new MyDeclHandler();
          SAXParser saxParser = factory.newSAXParser();
          
          saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

          //reset the counter
          SimpleClient http = new SimpleClient();
          http.sendGet(url+ "/reset");
          String response = http.sendGet(url + "/getCounter");
          assertEquals("0", response);
          
          saxParser.parse(xmlInput, myDefaultHandler);
          response = http.sendGet(url+ "/getCounter");
          assertEquals("1", response);     
    
      }
    
    
    @Test
    public void testURLInvocation_doctype_setFeature_load_external_dtd() throws Exception{
    	
		
  		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
  		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
  		
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
          
          MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
          MyDeclHandler myDeclHandler = new MyDeclHandler();
          SAXParser saxParser = factory.newSAXParser();
          
          saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

          //reset the counter
          SimpleClient http = new SimpleClient();
          http.sendGet(url+ "/reset");
          String response = http.sendGet(url + "/getCounter");
          assertEquals("0", response);
          
          saxParser.parse(xmlInput, myDefaultHandler);
          response = http.sendGet(url+ "/getCounter");
          assertEquals("0", response);     
    
      }
    
    @Test
    public void testURLInvocation_doctype_setProperty_DeclHandler() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_doctype.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        	
    	response = http.sendGet(url+ "/getCounter");
    	assertEquals("1", response);  	

        
        
    }
    
    
    @Test
    public void testURLInvocation_externalGeneralEntity() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        
        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url+ "/getCounter");
        assertEquals("1", response);   
        
    }
    
    @Test
    public void testURLInvocation_externalGeneralEntity_SecurityManager () throws Exception{
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
    	
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
    	MyDeclHandler myDeclHandler = new MyDeclHandler();
    	SAXParser saxParser = factory.newSAXParser();
    	
    	saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 SimpleClient http = new SimpleClient();
    	
    	org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
    	//using a negative number is more effective - especially for mitigating XXE Attacks
    	securityManager.setEntityExpansionLimit(-1);
    	saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
    	
    	
    	
    	 //reset the counter
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
    	
    	try {
    		
    		saxParser.parse(xmlInput, myDefaultHandler);        
    	} catch (SAXParseException e) {
    		String message = e.getMessage();
    		assertEquals(_SECURITYMANAGER_, message);
    	}
    	finally {
    		response = http.sendGet(url+ "/getCounter");
            assertEquals("1", response);  	
    	}
    }
    
    
    
    @Test
    public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        
        try {

            saxParser.parse(xmlInput, mySecureDefaultHandler);
                        
        }
        catch (SAXNotSupportedException e) {
        	String message ="External Entities not allowed";
        	assertEquals(message, e.getMessage());
        }
        finally {
        	response = http.sendGet(url+ "/getCounter");
            assertEquals("0", response);           
        }
    }
    
   
    @Test
    public void testURLInvocation_externalGeneralEntity_setFeature_disallow_doctype_decl() throws Exception{
	
			
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        
        try {        	
        	saxParser.parse(xmlInput, myDefaultHandler);   
            
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
        }
        
        
    }
    
    
    @Test
    public void testURLInvocation_externalGeneralEntity_setFeature_external_general_entities() throws Exception{
	
			
    	factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url+ "/getCounter");
        assertEquals("0", response);   
        
    }
    
    @Test
    public void testURLInvocation_externalGeneralEntity_setProperty_DeclHandler() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String meldung = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	
        	response = http.sendGet(url+ "/getCounter");
        	assertEquals("0", response);  	
        }   
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        response = http.sendGet(url+ "/getCounter");
        assertEquals("1", response);   
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_SecurityManager () throws Exception{
    	
    	InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
    	
    	MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
    	MyDeclHandler myDeclHandler = new MyDeclHandler();
    	SAXParser saxParser = factory.newSAXParser();
    	
    	saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 SimpleClient http = new SimpleClient();
    	
    	org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
    	//using a negative number is more effective - especially for mitigating XXE Attacks
    	securityManager.setEntityExpansionLimit(-1);
    	saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
    	
    	
    	
    	 //reset the counter
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
    	
        
    	try {
    		
    		saxParser.parse(xmlInput, myDefaultHandler);        
    	} catch (SAXParseException e) {
    		String message = e.getMessage();
    		assertEquals(_SECURITYMANAGER_, message);
    	}
    	finally {
    		response = http.sendGet(url+ "/getCounter");
            assertEquals("1", response);  	
    	}
    }
    
  
    @Test
    public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        //reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        
        try {

            saxParser.parse(xmlInput, mySecureDefaultHandler);
                        
        }
        catch (SAXNotSupportedException e) {
        	String message ="External Entities not allowed";
        	assertEquals(message, e.getMessage());
        }
        finally {
        	response = http.sendGet(url+ "/getCounter");
            assertEquals("0", response);           
        }
    }
    
   
    @Test
    public void testURLInvocation_parameterEntity_setFeature_disallow_doctype_decl() throws Exception{
	
			
    	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);

        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        
        try {        	
        	saxParser.parse(xmlInput, myDefaultHandler);   
            
        }
        catch(SAXParseException e) {
//        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
        finally {
        	response = http.sendGet(url + "/getCounter");
            assertEquals("0", response);
        }
        
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_setFeature_external_parameter_entities() throws Exception{
	
			
    	factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url+ "/getCounter");
        assertEquals("0", response);   
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_setFeature_load_dtd_grammar() throws Exception{
	
			
    	factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url+ "/getCounter");
        assertEquals("1", response);   
        
    }
    
    
    
    @Test
    public void testURLInvocation_parameterEntity_setFeature_load_external_dtd() throws Exception{
	
			
    	factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
    	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MyDeclHandler myDeclHandler = new MyDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        
        response = http.sendGet(url+ "/getCounter");
        assertEquals("1", response);   
        
    }
    
    
    @Test
    public void testURLInvocation_parameterEntity_setProperty_DeclHandler() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_parameterEntity.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
        MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
        SAXParser saxParser = factory.newSAXParser();
        
        
        saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        try {
        	saxParser.parse(xmlInput, myDefaultHandler);
        }
        catch (SAXException e) {
//        	String meldung = "External Entities not allowed";
        	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
        }
        finally {
        	
        	response = http.sendGet(url+ "/getCounter");
        	assertEquals("0", response);  	
        }   
        
    }
    
    @Test
    public void testURLInvocation_schemaLocation() throws Exception{
  	
  			
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_schemaLocation.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();
            
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        response = http.sendGet(url+ "/getCounter");
        assertEquals("0", response);   

  	
    }
    
    
    @Test
    public void testURLInvocation_schemaLocation_setFeature_validation_schema() throws Exception{
  	
   	
        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));	
    	        
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_schemaLocation.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();
            
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        response = http.sendGet(url+ "/getCounter");
        assertEquals("0", response);   
        
      

  	
    } 
      
    @Test
    public void testURLInvocation_schemaLocation_setFeature_validation_schema_namespaces() throws Exception{
  	
  	  // Namespaces have to be activated in order to process xml schema attributes
    	factory.setFeature("http://xml.org/sax/features/namespaces",true);
    	assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
    	
        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));	
    	        
  		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_schemaLocation.xml");
        
        MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
        SAXParser saxParser = factory.newSAXParser();
            
        // reset the counter
        SimpleClient http = new SimpleClient();
        http.sendGet(url+ "/reset");
        String response = http.sendGet(url + "/getCounter");
        assertEquals("0", response);
        
        saxParser.parse(xmlInput, myDefaultHandler);
        response = http.sendGet(url+ "/getCounter");
        assertEquals("0", response);   
        	
    } 
 

  
  @Test
  public void testURLInvocation_XInclude() throws Exception{
	
	  assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
			
	  InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
            
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      response = http.sendGet(url+ "/getCounter");
      assertEquals("0", response);   
      
      

	
  }
  
  @Test
  public void testURLInvocation_XInclude_setFeature_xinclude() throws Exception{

   	factory.setFeature("http://apache.org/xml/features/xinclude", true);
   	assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
   	
	InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      response = http.sendGet(url+ "/getCounter");
      assertEquals("0", response);   
      
       
	
  }
  
  @Test
  public void testURLInvocation_XInclude_setFeature_xinclude_namespaces() throws Exception{
	
      // now enable XIncludes
      
   // XInclude is not working unless Namespaces are also activated		
      factory.setFeature("http://xml.org/sax/features/namespaces",true);
      assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

   	factory.setFeature("http://apache.org/xml/features/xinclude", true);
   	assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
   	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      response = http.sendGet(url+ "/getCounter");
      assertEquals("1", response);   
      
       
	
  }
  
  
//  @Test
//  public void testURLInvocation_XInclude_setFeature_namespaces() throws Exception{
//
//      
//	   // XInclude is not working unless Namespaces are also activated		
//	      factory.setFeature("http://xml.org/sax/features/namespaces",true);
//	      assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
//
////	   	factory.setFeature("http://apache.org/xml/features/xinclude", true);
////	   	assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
//	   	
//			InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_xinclude.xml");
//	      
//	      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
//	      SAXParser saxParser = factory.newSAXParser();
//	      
//	      SimpleClient http = new SimpleClient();
//	        http.sendGet(url+ "/reset");
//	        String response = http.sendGet(url + "/getCounter");
//	        assertEquals("0", response);
//	        
//	        saxParser.parse(xmlInput, myDefaultHandler);
//	        response = http.sendGet(url+ "/getCounter");
//	        assertEquals("0", response);   
//
//  }
  
  @Test
  public void testURLInvocation_noNamespaceSchemaLocation() throws Exception{
	
			
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
          
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      response = http.sendGet(url+ "/getCounter");
      assertEquals("0", response);   

	
  }
  
	
  
  @Test
  public void testURLInvocation_noNamespaceSchemaLocation_setFeature_validation_schema() throws Exception{
	
 	
      factory.setFeature("http://apache.org/xml/features/validation/schema", true);
      assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));	
  	        
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
          
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      response = http.sendGet(url+ "/getCounter");
      assertEquals("0", response);   
      
    

	
  } 
    
  @Test
  public void testURLInvocation_noNamespaceSchemaLocation_setFeature_validation_schema_namespaces() throws Exception{
	
	  // Namespaces have to be activated in order to process xml schema attributes
  	factory.setFeature("http://xml.org/sax/features/namespaces",true);
  	assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
  	
      factory.setFeature("http://apache.org/xml/features/validation/schema", true);
      assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));	
  	        
      InputStream xmlInput = new FileInputStream("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();        
      SAXParser saxParser = factory.newSAXParser();
          
      // reset the counter
      SimpleClient http = new SimpleClient();
      http.sendGet(url+ "/reset");
      String response = http.sendGet(url + "/getCounter");
      assertEquals("0", response);
      
      saxParser.parse(xmlInput, myDefaultHandler);
      
      response = http.sendGet(url+ "/getCounter");
      assertEquals("1", response);   
      	
  } 

  @Test
  public void testXInclude() throws IOException, SAXException, ParserConfigurationException {   		

				
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);    
	    

		String content =myDefaultHandler.getElementContent("data");
		assertEquals("xi:includexi:include", content);
		
	//	this will fail because XInclude is not supported	
		content = myDefaultHandler.getElementContent("content");
		assertEquals("", content);
	
  }
  
  
  @Test  
  public void testXInclude_setFeature_xinclude() throws IOException, SAXException, ParserConfigurationException {   		

	  assertFalse(factory.getFeature("http://xml.org/sax/features/namespaces"));
	  
		factory.setFeature("http://apache.org/xml/features/xinclude", true);
		assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
		
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);    
	    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("xi:includexi:include", content);		
	
  }
  
  
  @Test  
  public void testXInclude_setFeature_xinclude_namespaces() throws IOException, SAXException, ParserConfigurationException {   		

	

		// XInclude is not working unless Namespaces are also activated		
		factory.setFeature("http://xml.org/sax/features/namespaces",true);
		assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

		factory.setFeature("http://apache.org/xml/features/xinclude", true);
		assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
		
		
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xinclude.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);    
	    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("contentit_workscontent", content);		
	
		content = myDefaultHandler.getElementContent("content");
		assertEquals("it_works", content);
	
  }
  
  
  @Test
  public void testXSLT() throws IOException, SAXException, ParserConfigurationException {   		

				
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/optional/xslt.xsl");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);    
	    
      	// ugly coding;
       // other solutions are more complex and require to keep state of document structure
      	String name = myDefaultHandler.getResult().get(0);

		assertEquals("xsl:stylesheet", name);
	
  }
  
  
  
    
  
  @Test
  public void testXXE() throws IOException,SAXException, ParserConfigurationException {
  	
	  
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);
	    
	
		String content =myDefaultHandler.getElementContent("data");
		assertEquals("it_works", content);
  }
  
  @Test 
  public void testXXE_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
  	
		
  	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
  	    		        
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
      //using a negative number is more effective - especially for mitigating XXE Attacks
      securityManager.setEntityExpansionLimit(-1);
      saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
      
      try {
		
      	saxParser.parse(xmlInput, myDefaultHandler);        
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}
      finally {
  		String content =myDefaultHandler.getElementContent("data");
  		assertEquals("", content);
      }
  }
  
  @Test 
  public void testXXE_SecurityManager_set_0() throws ParserConfigurationException, SAXException, IOException {
  	
		
  	InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
  	    		        
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
      //using a negative number is more effective - especially for mitigating XXE Attacks
      securityManager.setEntityExpansionLimit(0);
      saxParser.setProperty("http://apache.org/xml/properties/security-manager", securityManager);
      
      	
      saxParser.parse(xmlInput, myDefaultHandler);		
      String content =myDefaultHandler.getElementContent("data");
      assertEquals("it_works", content);
      
  }
  
  @Test
  public void testXXE_setEntityResolver() throws IOException,SAXException, ParserConfigurationException {
	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
		MySecureDefaultHandler mySecureDefaultHandler = new MySecureDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
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
  public void testXXE_setFeature_disallow_doctype_decl() throws IOException,SAXException, ParserConfigurationException {
	
  	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
  	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
  	
  	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      try {
      	saxParser.parse(xmlInput, myDefaultHandler);
      }
      catch(SAXParseException e) {
//      	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
      	assertEquals(_DOCTYPE_, e.getMessage());
      }
      finally {
      	String content =myDefaultHandler.getElementContent("data");
      	assertEquals("", content);
      	
      }
	    
	
  }
  
  
  @Test
  public void testXXE_setFeature_external_general_entities() throws SAXNotRecognizedException, SAXNotSupportedException, IOException,SAXException, ParserConfigurationException {	
	
		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
	    assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
	    
	    
	    
	    
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);
	 		
		String content = myDefaultHandler.getElementContent("data");
		assertEquals("", content);	
	    
  }
  
  
  @Test
  public void testXXE_setFeature_external_general_entities_validation() throws SAXNotRecognizedException, SAXNotSupportedException, IOException,SAXException, ParserConfigurationException {	
	
		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
	    assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
	    
	    
		factory.setFeature("http://xml.org/sax/features/validation",true);
	    assertTrue(factory.getFeature("http://xml.org/sax/features/validation"));
	    
	   
	    
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MyDeclHandler myDeclHandler = new MyDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
      
      saxParser.parse(xmlInput, myDefaultHandler);
	 		
		String content = myDefaultHandler.getElementContent("data");
		assertEquals("", content);	
	    
  }
  
  
  
  
  @Test
  public void testXXE_setProperty_setProperty_DeclHandler() throws IOException,SAXException, ParserConfigurationException {
	
		InputStream xmlInput = new FileInputStream("../../xml_files_windows/xxe.xml");
      
      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
      MySecureDeclHandler mySecureDeclHandler = new MySecureDeclHandler();
      SAXParser saxParser = factory.newSAXParser();
      
      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", mySecureDeclHandler);
      
      try {
      	saxParser.parse(xmlInput, myDefaultHandler);
      }
      catch (SAXException e) {
//      	String meldung = "External Entities not allowed";
      	assertEquals(_DECL_HANDLER_EXTERNAL_, e.getMessage());
      }	
      finally {
      	String content =myDefaultHandler.getElementContent("data");
      	assertEquals("", content);
      }
  }
  
    
    
    

//  /**
//   * We developed this attack ourselves using a combination of the Parameter Entity Attack 
//   * and Inclusion of External Entities in Attribute Values.
//   * This facilitates to read out a text file (no xml!) completely, not just the first line! 
//   * This only works if the Parser processes DTDs AND is validating against an XSD Schema (should be viable quite often)
//   *  
//   *  It seems we are not the first one to think about that idea
//   *  https://media.blackhat.com/eu-13/briefings/Osipov/bh-eu-13-XML-data-osipov-slides.pdf
//   *  However Slide 38 states for Java Xerces:
//   *  Cons:  Can’t read multiline files with OOB technique
//   *  from which we presume that they did NOT try this combination!
//   * @throws ParserConfigurationException 
//   */
//  @Test
//  public void testParameterEntity_Attack_AttributeValue() throws Exception {
//		
//		
//		
//		assertFalse(factory.isNamespaceAware());
//		factory.setNamespaceAware(true);
//		assertTrue(factory.isNamespaceAware());
//			
//		factory.setFeature("http://apache.org/xml/features/validation/schema",true);
//	
//
//		
//		InputStream xmlInput = new FileInputStream("../../xml_files_windows/test/schemaEntity_noSchema.xml");
//      
//      MyDefaultHandler myDefaultHandler = new MyDefaultHandler();
//      MyDeclHandler myDeclHandler = new MyDeclHandler();
//      SAXParser saxParser = factory.newSAXParser();
//      
//      saxParser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//      
//   	SimpleClient http = new SimpleClient();
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
//		assertEquals("http://192.168.2.31/root:root sample:sample",
//				 myDefaultHandler.getAttributeValue("noNamespaceSchemaLocation"));
//  }
  
	 

}
