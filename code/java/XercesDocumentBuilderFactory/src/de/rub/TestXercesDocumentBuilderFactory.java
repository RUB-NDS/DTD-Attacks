package de.rub;

import de.rub.SimpleClient;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xerces.util.SecurityManager;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.DOMBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


/**
 * 
 * @author dev 
 *Test cases for Xerces DOM *

 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class TestXercesDocumentBuilderFactory {

	
	DocumentBuilderFactory factory;
	DocumentBuilder builder;
	// integrate jdom and dom4j into this test set
	DOMBuilder jdomBuilder;
	org.dom4j.io.DOMReader dom4jDOMReader;
	
	String url = "http://127.0.0.1:5000";
	
	//frequently occuring messages
	final String _DOCTYPE_ = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
	final String _SECURITYMANAGER_ = "The parser has encountered more than \"-1\" entity expansions in this document; this is the limit imposed by the application.";
    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		    factory = DocumentBuilderFactory.newInstance();
		    jdomBuilder = new DOMBuilder();
		    dom4jDOMReader=  new org.dom4j.io.DOMReader();

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
			assertFalse(factory.getFeature("http://apache.org/xml/features/scanner/notify-builtin-refs"));

			assertFalse(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
			
			assertFalse(factory.getFeature("http://apache.org/xml/features/standard-uri-conformant"));
			assertFalse(factory.getFeature("http://apache.org/xml/features/xinclude"));
			assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude/fixup-base-uris"));
			assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude/fixup-language"));

			
		
			//XInclude Feature			
			assertTrue(factory.getFeature("http://xml.org/sax/features/allow-dtd-events-after-endDTD"));

			
			//DOM Feature			
			assertTrue(factory.getFeature("http://apache.org/xml/features/dom/defer-node-expansion"));
			//deviates from specification
			assertFalse(factory.getFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes"));
			assertTrue(factory.getFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace"));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDefault_noAttack() throws ParserConfigurationException, SAXException, IOException {
		
		
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
		
		
		builder = factory.newDocumentBuilder();		
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/standard.xml");		
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();	
		assertEquals("4", content);		
		
		
		Document jdomDocument = jdomBuilder.build(w3cDocument); 
		String jdomContent = jdomDocument.getRootElement().getText();
		assertEquals("4", jdomContent);
		
		dom4jDOMReader=  new org.dom4j.io.DOMReader();
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		assertEquals("4", dom4jContent);
		
		
	}
	
	@Test
	public void testDOS_core() throws ParserConfigurationException, SAXException, IOException {
				
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_core.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		int expectedCount = 25;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		dosCount = jdomContent.length() / 3;
		assertEquals(expectedCount, dosCount);
		
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dosCount = dom4jContent.length() / 3;
		assertEquals(expectedCount, dosCount);
		
	}
	
	
	@Test
	public void testDOS_core_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_core.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	

    
	
	
	@Test
	public void testDOS_core_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {


		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_core.xml");
			
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
			
        }
        catch(SAXParseException e) {
        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(message, e.getMessage());
        }	

	}
	
	@Test
	public void testDOS_indirections() throws ParserConfigurationException, SAXException, IOException {
				
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_indirections.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		int expectedCount = 10000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   	
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		dosCount = jdomContent.length() / 3;
		assertEquals(expectedCount, dosCount);
		
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dosCount = dom4jContent.length() / 3;
		assertEquals(expectedCount, dosCount);
		
	}
	
	@Test
	public void testDOS_indirections_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_indirections.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	@Test
	public void testDOS_indirections_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {


		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_indirections.xml");
			
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
			
        }
        catch(SAXParseException e) {
        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(message, e.getMessage());
        }	

	}
	
	
	@Test
	public void testDOS_indirections_parameterEntity() throws ParserConfigurationException, SAXException, IOException {
				
		
		builder = factory.newDocumentBuilder();
		
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_indirections_parameterEntity.xml");
			
			// nothing to do for jdom / dom4j			
        }
        catch(SAXParseException e) {
        	String message = e.getMessage();
        	assertEquals("The parameter entity reference \"%a;\" cannot occur within markup in the internal subset of the DTD.", message);
        }
		
		
	}
	
	
	@Test
	public void testDOS_entitySize() throws ParserConfigurationException, SAXException, IOException {
				
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_entitySize.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		int expectedCount = 3400000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);   
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		dosCount = jdomContent.length() / 3;
		assertEquals(expectedCount, dosCount);
		
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dosCount = dom4jContent.length() / 3;
		assertEquals(expectedCount, dosCount);
	}
	
	@Test
	public void testDOS_entitySize_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_entitySize.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	@Test
	public void testDOS_entitySize_setFeature_disallow_doctype_decl() throws ParserConfigurationException, SAXException, IOException {


		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_entitySize.xml");
			
			// nothing to do for jdom / dom4j 
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
			
        }
        catch(SAXParseException e) {
        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
        	assertEquals(message, e.getMessage());
        }	

	}
	
	
	@Test
	public void testDOS_recursion() throws ParserConfigurationException, SAXException, IOException {
				
		
		builder = factory.newDocumentBuilder();
		
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/dos/dos_recursion.xml");
			
			// nothing to do for jdom / dom4j			
        }
        catch(SAXParseException e) {
        	String message = "Recursive entity reference \"a\". (Reference path: a -> b -> a),";
        	assertEquals(message, e.getMessage());
        }
		
		
	}
	
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD() throws SAXException, IOException, ParserConfigurationException {
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
		
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {
		
		builder = factory.newDocumentBuilder();
		
		MyEntityResolver myEntityResolver = new MyEntityResolver();		    
	    builder.setEntityResolver(myEntityResolver);
		    
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
	    	
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }   
		
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_disallow_doctype_decl() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		builder = factory.newDocumentBuilder();
		
		 try {
	        	
			 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
			 
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
	}
	
	
//	obsolet;
//	@Test
//	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_general_entities() throws SAXException, IOException, ParserConfigurationException {
//		
//		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    	
//		builder = factory.newDocumentBuilder();
//		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//		content = content.replaceAll("\\n","");
//		assertEquals("it_works", content);				
//		
//		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//		String jdomContent = jdomDocument.getRootElement().getText();
//		jdomContent = jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", jdomContent);	
//		
//		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//		String dom4jContent = dom4jDocument.getRootElement().getText();
//		dom4jContent= jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", dom4jContent);
//	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities() throws SAXException, IOException, ParserConfigurationException {
    	
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("", dom4jContent);
	}
	
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_external_parameter_entities_validation() throws SAXException, IOException, ParserConfigurationException {
    	
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
    	factory.setFeature("http://xml.org/sax/features/validation", true);    		
    	assertTrue(factory.getFeature("http://xml.org/sax/features/validation"));
    	
    	
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("", dom4jContent);
	}
	
	
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_load_dtd_grammar() throws SAXException, IOException, ParserConfigurationException {
    	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	
  	
    	
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	@Test
	public void testInternalSubset_ExternalPEReferenceInDTD_setFeature_load_external_dtd() throws SAXException, IOException, ParserConfigurationException {
    	
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
    	
  	
    	
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	
	
	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD() throws SAXException, IOException, ParserConfigurationException {
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
		
	}
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
    
//	obsolet;
//	@Test
//	public void testInternalSubset_PEReferenceInDTD_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {
//		
//		builder = factory.newDocumentBuilder();
//		
//		
//		MyEntityResolver myEntityResolver = new MyEntityResolver();		    
//	    builder.setEntityResolver(myEntityResolver);
//		
//	    try {
//	    	
//	    	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
//	    	
//			// nothing to do for jdom/dom4j
////			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
////			String jdomContent = jdomDocument.getRootElement().getText();			
////			
////			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
////			String dom4jContent = dom4jDocument.getRootElement().getText();
//	    }
//	    catch (SAXException e) {
//	    	String message = "Entities not allowed";
//	    
//	    	assertEquals(message, e.getMessage());		
//	    }  
//
//	}
	
	
	@Test
	public void testInternalSubset_PEReferenceInDTD_setFeature_disallow_doctype_decl() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
		
		builder = factory.newDocumentBuilder();
		
		 try {
	        	
			 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
			 
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }	
	}
	
	@Test
	public void testParameterEntity_core() throws SAXException, IOException, ParserConfigurationException {
				
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	@Test
	public void testParameterEntity_core_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	
	@Test
	public void testParameterEntity_core_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {
				
		builder = factory.newDocumentBuilder();
		MyEntityResolver myEntityResolver = new MyEntityResolver();		    
	    builder.setEntityResolver(myEntityResolver);
	    
	    try {
	    	
	    	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }  
		
		
	}
	
	@Test
	public void testParameterEntity_core_setFeature_disallow_doctype_decl() throws SAXException, IOException, ParserConfigurationException {
				
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	    	
		builder = factory.newDocumentBuilder();
		
		 try {
	        	
			 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");   
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }		
			
	}
	
//	obsolet;
//	@Test
//	public void testParameterEntity_core_setFeature_external_general_entities() throws SAXException, IOException, ParserConfigurationException {
//
//		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//		
//		builder = factory.newDocumentBuilder();
//		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
//		content = content.replaceAll("\\n","");
//		assertEquals("it_works", content);
//		
//		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//		String jdomContent = jdomDocument.getRootElement().getText();
//		jdomContent = jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", jdomContent);	
//		
//		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//		String dom4jContent = dom4jDocument.getRootElement().getText();
//		dom4jContent= jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", dom4jContent);
//		
////    	
//	}
	
	@Test
	public void testParameterEntity_core_setFeature_external_parameter_entities() throws SAXException, IOException, ParserConfigurationException {
				
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();	
		assertEquals("", content);
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("", dom4jContent);
	}
	
	@Test
	public void testParameterEntity_core_setFeature_load_dtd_grammar() throws SAXException, IOException, ParserConfigurationException {
				
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();	
		assertEquals("it_works", content);
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	
	
	@Test
	public void testParameterEntity_core_setFeature_load_external_dtd() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_core.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	
	
	
	
	@Test
	public void testParameterEntity_doctype() throws SAXException, IOException, ParserConfigurationException {
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);	
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	
	@Test
	public void testParameterEntity_doctype_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}
	
	@Test
	public void testParameterEntity_doctype_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {
		
		builder = factory.newDocumentBuilder();
		
		MyEntityResolver myEntityResolver = new MyEntityResolver();		    
	    builder.setEntityResolver(myEntityResolver);
	    
	    try {
	    	
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
		
		// nothing to do for jdom/dom4j
//		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//		String jdomContent = jdomDocument.getRootElement().getText();			
//		
//		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//		String dom4jContent = dom4jDocument.getRootElement().getText();
		
	    }
	    catch (SAXException e) {
	    	String message = "External Entities not allowed";
	    
	    	assertEquals(message, e.getMessage());		
	    }
	}
	
	
	@Test
	public void testParameterEntity_doctype_setFeature_disallow_doctype_decl() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
		builder = factory.newDocumentBuilder();
		
		 try {
	        	
			 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");    
			 
			 
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
        }
        catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }		
	}
	
//	obsolet;
//	@Test
//	public void testParameterEntity_doctype_setFeature_external_general_entities() throws SAXException, IOException, ParserConfigurationException {
//		
//		factory.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
//    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
//    	
//    	
//		builder = factory.newDocumentBuilder();
//		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//		content = content.replaceAll("\\n","");
//		assertEquals("it_works", content);	
//		
//		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//		String jdomContent = jdomDocument.getRootElement().getText();
//		jdomContent = jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", jdomContent);	
//		
//		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//		String dom4jContent = dom4jDocument.getRootElement().getText();
//		dom4jContent= jdomContent.replaceAll("\\n","");
//		assertEquals("it_works", dom4jContent);
//	}
	
	
	@Test
	public void testParameterEntity_doctype_setFeature_external_parameter_entities() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);    		
    	assertFalse(factory.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    	
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("", dom4jContent);
	}
	
	@Test
	public void testParameterEntity_doctype_setFeature_load_dtd_grammar() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",false);    		
    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("it_works", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("it_works", dom4jContent);
	}
	
	
	
	@Test
	public void testParameterEntity_doctype_setFeature_load_external_dtd() throws SAXException, IOException, ParserConfigurationException {
		
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
		assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		content = content.replaceAll("\\n","");
		assertEquals("", content);		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
		String jdomContent = jdomDocument.getRootElement().getText();
		jdomContent = jdomContent.replaceAll("\\n","");
		assertEquals("", jdomContent);	
		
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		dom4jContent= jdomContent.replaceAll("\\n","");
		assertEquals("", dom4jContent);
	}
	
	
	
	
	
	

	  @Test
	  public void testURLInvocation_doctype() throws Exception{
		
	  		builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	        
	  }
	  
	  
	  @Test
	  public void testURLInvocation_doctype_SecurityManager() throws Exception{
		
		  
			org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
			//using a negative number is more effective - especially for mitigating XXE Attacks
			securityManager.setEntityExpansionLimit(-1);
			factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
				
			try {
				 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
			        
				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
				String jdomContent = jdomDocument.getRootElement().getText();
				
				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
				String dom4jContent = dom4jDocument.getRootElement().getText();
				
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals(_SECURITYMANAGER_,message);
			} finally {
			       
		        response = http.sendGet(url + "/getCounter");
		        assertEquals("1", response);
			}
	        
	  }
	  
	  
	  
	  @Test
	  public void testURLInvocation_doctype_setEntityResolver() throws Exception{
		
	  		builder = factory.newDocumentBuilder();
	  		
			MyEntityResolver myEntityResolver = new MyEntityResolver();		    
		    builder.setEntityResolver(myEntityResolver);
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
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
	  public void testURLInvocation_doctype_setFeature_disallow_doctype_decl() throws Exception{
		
		  	factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
	    	
	  		builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
	        }
	        catch(SAXParseException e) {
//	        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
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
		
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	        
	}
	  
	  
	  @Test
	  public void testURLInvocation_doctype_setFeature_load_external_dtd() throws Exception{
		
			
			factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
			assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	}
	  
	  
	  @Test
	  public void testURLInvocation_externalGeneralEntity() throws Exception{
		
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	 }
	  
	  @Test
	  public void testURLInvocation_externalGeneralEntity_SecurityManager() throws Exception{
		
		  
			org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
			//using a negative number is more effective - especially for mitigating XXE Attacks
			securityManager.setEntityExpansionLimit(-1);
			factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
				
			try {
				 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
			        
				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
				String jdomContent = jdomDocument.getRootElement().getText();
				
				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
				String dom4jContent = dom4jDocument.getRootElement().getText();
				
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals(_SECURITYMANAGER_,message);
			} finally {
			       
		        response = http.sendGet(url + "/getCounter");
		        assertEquals("1", response);
			}
	        
	  }
	  
	  
	  
	  @Test
	  public void testURLInvocation_externalGeneralEntity_setEntityResolver() throws Exception{
		
	  
			
			builder = factory.newDocumentBuilder();
			MyEntityResolver myEntityResolver = new MyEntityResolver();		    
		    builder.setEntityResolver(myEntityResolver);
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
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
	  public void testURLInvocation_externalGeneralEntity_setFeature_disallow_doctype_decl() throws Exception{
		
		  factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
	    	
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
	        }
	        catch(SAXParseException e) {
	        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
	        	assertEquals(message, e.getMessage());
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
			
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	 }
	  
	  
	  @Test
	  	public void testURLInvocation_noNamespaceSchemaLocation() throws Exception{
		
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");
			
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();			
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  

	  	
	  	@Test
	  	public void testURLInvocation_noNamespaceSchemaLocation_setFeature_validation_schema() throws Exception{
		
	        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
	        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));
	        
//	        factory.setFeature("http://xml.org/sax/features/namespaces",true);
//	        assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  
	  	@Test
	  	public void testURLInvocation_noNamespaceSchemaLocation_setFeature_validation_schema_namespaces() throws Exception{
		
	        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
	        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));
	        
	        factory.setFeature("http://xml.org/sax/features/namespaces",true);
	        assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");
			
	        org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	        
	        
	 }
	  

	  @Test
	  public void testURLInvocation_parameterEntity() throws Exception{
		
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
			
	        org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	 }
	  
	  @Test
	  public void testURLInvocation_parameterEntity_SecurityManager() throws Exception{
		
		  
			org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
			//using a negative number is more effective - especially for mitigating XXE Attacks
			securityManager.setEntityExpansionLimit(-1);
			factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
				
			try {
				 org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
			        
				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
				String jdomContent = jdomDocument.getRootElement().getText();
				
				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
				String dom4jContent = dom4jDocument.getRootElement().getText();
				
			} catch (SAXParseException e) {
				String message = e.getMessage();
				assertEquals(_SECURITYMANAGER_,message);
			} finally {
			       
		        response = http.sendGet(url + "/getCounter");
		        assertEquals("1", response);
			}
	        
	  }
	  
	  
	  
	  @Test
	  public void testURLInvocation_parameterEntity_setEntityResolver() throws Exception{
		
	  
			
			builder = factory.newDocumentBuilder();
			MyEntityResolver myEntityResolver = new MyEntityResolver();		    
		    builder.setEntityResolver(myEntityResolver);
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
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
	  public void testURLInvocation_parameterEntity_setFeature_disallow_doctype_decl() throws Exception{
		
		  factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
	    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
	    	
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        try {
	        	
	        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
				// nothing to do for jdom/dom4j
//				org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//				String jdomContent = jdomDocument.getRootElement().getText();			
//				
//				org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//				String dom4jContent = dom4jDocument.getRootElement().getText();
	        }
	        catch(SAXParseException e) {
//	        	String message = "DOCTYPE is disallowed when the feature \"http://apache.org/xml/features/disallow-doctype-decl\" set to true.";
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
			
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	 }
	  
	  
	  @Test
	  public void testURLInvocation_parameterEntity_setFeature_load_dtd_grammar() throws Exception{
		
		  factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);    		
	    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
			
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	 }
	  
	  @Test
	  public void testURLInvocation_parameterEntity_setFeature_load_external_dtd() throws Exception{
		
		  factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);    		
	    	assertFalse(factory.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
			
			
			builder = factory.newDocumentBuilder();
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	 }
	  
	  
	  
	  
	  @Test
	  	public void testURLInvocation_schemaLocation() throws Exception{
		
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  

	  	
	  	@Test
	  	public void testURLInvocation_schemaLocation_setFeature_validation_schema() throws Exception{
		
	        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
	        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));
	        
//	        factory.setFeature("http://xml.org/sax/features/namespaces",true);
//	        assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");
	        	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
			
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  
	  	@Test
	  	public void testURLInvocation_schemaLocation_setFeature_validation_schema_namespaces() throws Exception{
		
	        factory.setFeature("http://apache.org/xml/features/validation/schema", true);
	        assertTrue(factory.getFeature("http://apache.org/xml/features/validation/schema"));
	        
	        factory.setFeature("http://xml.org/sax/features/namespaces",true);
	        assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));
	 		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  
	  

	  	@Test
	  	public void testURLInvocation_XInclude() throws Exception{
		
			
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
	     
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	     
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  

	  	@Test
	  	public void testURLInvocation_XInclude_setFeature_xinclude() throws Exception{
		
	  		 // XInclude is not working unless Namespaces are also activated		
//	        factory.setFeature("http://xml.org/sax/features/namespaces",true);
//	        assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

	     	factory.setFeature("http://apache.org/xml/features/xinclude", true);
	     	assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));

	  		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        
	 }
	  
	  	@Test
	  	public void testURLInvocation_XInclude_setFeature_xinclude_namespaces() throws Exception{
		
	 		 // XInclude is not working unless Namespaces are also activated		
	       factory.setFeature("http://xml.org/sax/features/namespaces",true);
	       assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

	    	factory.setFeature("http://apache.org/xml/features/xinclude", true);
	    	assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
	  		
			builder = factory.newDocumentBuilder();
			
			
			// reset the counter
	        SimpleClient http = new SimpleClient();
	        http.sendGet(url + "/reset");
	        String response = http.sendGet(url + "/getCounter");
	        assertEquals("0", response);
	        
	        org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
	        
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			String jdomContent = jdomDocument.getRootElement().getText();
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getText();
	        
	        response = http.sendGet(url + "/getCounter");
	        assertEquals("1", response);
	        
	        
	 }
	  
	  	@Test
		public void testXInclude() throws SAXException, IOException, ParserConfigurationException {
		
			builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xinclude.xml");
			String content = w3cDocument.getElementsByTagName("data").
					item(0).getChildNodes().item(0).getNodeName();	
			assertEquals("xi:include", content);
			
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			Namespace namespace = Namespace.getNamespace("xi", "http://www.w3.org/2001/XInclude");
			String jdomContent = jdomDocument.getRootElement().getName();
			assertEquals("data", jdomContent);
			List <Element> childrenOfRoot =jdomDocument.getRootElement().getChildren();
			Element childOfRoot = childrenOfRoot.get(0);
			
//			System.out.println(childOfRoot.getName() + childOfRoot.getNamespace().getPrefix());
			assertEquals("xi", childOfRoot.getNamespace().getPrefix());
			assertEquals("include", childOfRoot.getName());
			
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			org.dom4j.Element root = dom4jDocument.getRootElement();
			org.dom4j.Element include = (org.dom4j.Element) root.elements().get(0);	
			assertEquals("xi:include",include.getName());
			
			
			
			
		}
		
		@Test
		public void testXInclude_setFeature_xinclude() throws SAXException, IOException, ParserConfigurationException {
			
				
			// XInclude is not working unless Namespaces are also activated		
//			factory.setFeature("http://xml.org/sax/features/namespaces",true);
//			assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

			factory.setFeature("http://apache.org/xml/features/xinclude", true);
			assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
			
			
			builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xinclude.xml");
			String content = w3cDocument.getElementsByTagName("data").
					item(0).getChildNodes().item(0).getNodeName();	
			assertEquals("xi:include", content);
			content = w3cDocument.getElementsByTagName("data").
					item(0).getChildNodes().item(0).getTextContent();
			assertEquals("", content);
			
			
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
			Namespace namespace = Namespace.getNamespace("xi", "http://www.w3.org/2001/XInclude");
			String jdomContent = jdomDocument.getRootElement().getName();
			assertEquals("data", jdomContent);
			List <Element> childrenOfRoot =jdomDocument.getRootElement().getChildren();
			Element childOfRoot = childrenOfRoot.get(0);
			
//			System.out.println(childOfRoot.getName() + childOfRoot.getNamespace().getPrefix());
			assertEquals("xi", childOfRoot.getNamespace().getPrefix());
			assertEquals("include", childOfRoot.getName());
			
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			org.dom4j.Element root = dom4jDocument.getRootElement();
			org.dom4j.Element include = (org.dom4j.Element) root.elements().get(0);	
			assertEquals("xi:include",include.getName());
			
			
			
		}
		
		@Test
		public void testXInclude_setFeature_xinclude_namespaces() throws SAXException, IOException, ParserConfigurationException {
			
				
			// XInclude is not working unless Namespaces are also activated		
			factory.setFeature("http://xml.org/sax/features/namespaces",true);
			assertTrue(factory.getFeature("http://xml.org/sax/features/namespaces"));

			factory.setFeature("http://apache.org/xml/features/xinclude", true);
			assertTrue(factory.getFeature("http://apache.org/xml/features/xinclude"));
			
			
			builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xinclude.xml");
			String content = w3cDocument.getElementsByTagName("data").
					item(0).getChildNodes().item(0).getNodeName();	
			assertEquals("content", content);
			content = w3cDocument.getElementsByTagName("data").
					item(0).getChildNodes().item(0).getTextContent();
			assertEquals("it_works", content);
			
			
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);			
			String jdomContent = jdomDocument.getRootElement().getName();
			assertEquals("data", jdomContent);
			List <Element> childrenOfRoot =jdomDocument.getRootElement().getChildren();
			Element childOfRoot = childrenOfRoot.get(0);
			
//			System.out.println(childOfRoot.getName() + childOfRoot.getNamespace().getPrefix());
//			assertEquals("xi", childOfRoot.getNamespace().getPrefix());
			assertEquals("content", childOfRoot.getName());
			assertEquals("it_works", childOfRoot.getText());
			
			
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			org.dom4j.Element root = dom4jDocument.getRootElement();
			org.dom4j.Element include = (org.dom4j.Element) root.elements().get(0);
			assertEquals("content", include.getName());
			assertEquals("it_works", include.getText());
			
			
		}

		@Test
		public void testXSLT() throws ParserConfigurationException, SAXException, IOException {
			
			String xmlFile = "file:///C:/Christopher_Spaeth/code/xml_files_windows/optional/xslt.xsl"; 
		    DocumentBuilder parser = factory.newDocumentBuilder();
		      
		    builder = factory.newDocumentBuilder();		
			org.w3c.dom.Document w3cDocument = builder.parse(xmlFile);		
			String content = w3cDocument.getDocumentElement().getNodeName();	
			assertEquals("xsl:stylesheet", content);		
			
			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument); 
			String jdomContent = jdomDocument.getRootElement().getName();
			assertEquals("stylesheet", jdomContent);
			
			dom4jDOMReader=  new org.dom4j.io.DOMReader();
			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
			String dom4jContent = dom4jDocument.getRootElement().getName();
			assertEquals("xsl:stylesheet", dom4jContent);
				
		}  
	
	
	@Test
	public void testXXE() throws SAXException, IOException, ParserConfigurationException {		

		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument); 
		String jdomContent = jdomDocument.getRootElement().getText();
		assertEquals("it_works", jdomContent);
		
		dom4jDOMReader=  new org.dom4j.io.DOMReader();
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		assertEquals("it_works", dom4jContent);
		
	}
	
	@Test
	public void testXXE_SecurityManager() throws ParserConfigurationException, SAXException, IOException {
		
		
		org.apache.xerces.util.SecurityManager securityManager = new SecurityManager();
	    //using a negative number is more effective - especially for mitigating XXE Attacks
	    securityManager.setEntityExpansionLimit(-1);
	    factory.setAttribute("http://apache.org/xml/properties/security-manager", securityManager);
		
		builder = factory.newDocumentBuilder();
		
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
			String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		} catch (SAXParseException e) {
			String message = e.getMessage();
			assertEquals(_SECURITYMANAGER_,message);
		}	
	}	
	
	

	@Test
	public void testXXE_setEntityResolver() throws SAXException, IOException, ParserConfigurationException {		
	
		
		builder = factory.newDocumentBuilder();
		MyEntityResolver myEntityResolver = new MyEntityResolver();		    
	    builder.setEntityResolver(myEntityResolver);
	    

        try {
        	
        	org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe.xml");
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
        }
        catch (SAXException e) {
        	String message = "External Entities not allowed";
    	    
	    	assertEquals(message, e.getMessage());		
        }   
		
		
	}
	
	@Test
	public void testXXE_setFeature_disallow_doctype_decl() throws SAXException, IOException, ParserConfigurationException {		

		factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
    	assertTrue(factory.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
    	
    	
    	
		builder = factory.newDocumentBuilder();
		try {
			org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe.xml");
			// nothing to do for jdom/dom4j
//			org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument);
//			String jdomContent = jdomDocument.getRootElement().getText();			
//			
//			org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
//			String dom4jContent = dom4jDocument.getRootElement().getText();
		}
		catch(SAXParseException e) {
        	assertEquals(_DOCTYPE_, e.getMessage());
        }
//        finally {	        	
//        	String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//    		content = content.replaceAll("\\n","");
//    		assertEquals("it_works", content);		
//        }
		
	}
	
	@Test
	public void testXXE_setFeature_external_general_entities() throws SAXException, IOException, ParserConfigurationException {		

		
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
	    
	    builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		assertEquals("", content);	
		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument); 
		String jdomContent = jdomDocument.getRootElement().getText();
		assertEquals("", jdomContent);
		
		dom4jDOMReader=  new org.dom4j.io.DOMReader();
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		assertEquals("", dom4jContent);
		
	}
	
	@Test
	public void testXXE_setFeature_external_general_entities_validation() throws SAXException, IOException, ParserConfigurationException {		

		
		factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		assertFalse(factory.getFeature("http://xml.org/sax/features/external-general-entities"));
	    
		
		factory.setFeature("http://xml.org/sax/features/validation", true);
		assertTrue(factory.getFeature("http://xml.org/sax/features/validation"));
		
		
		
	    builder = factory.newDocumentBuilder();
		
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe.xml");
//		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		String content = w3cDocument.getElementsByTagName("data").item(0).getTextContent();
		assertEquals("", content);	
		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument); 
		String jdomContent = jdomDocument.getRootElement().getText();
		assertEquals("", jdomContent);
		
		dom4jDOMReader=  new org.dom4j.io.DOMReader();
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		assertEquals("", dom4jContent);
		
	}
	
	
	@Test
	public void testXXE_netdoc() throws SAXException, IOException, ParserConfigurationException {		

		builder = factory.newDocumentBuilder();
		org.w3c.dom.Document w3cDocument = builder.parse("../../xml_files_windows/xxe/xxe_netdoc.xml");
		String content = w3cDocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
		content = content.replaceAll("\\n","");
		assertEquals("it_works", content);		
		
		
		org.jdom2.Document jdomDocument = jdomBuilder.build(w3cDocument); 
		String jdomContent = jdomDocument.getRootElement().getText();
		assertEquals("it_works", jdomContent);
		
		dom4jDOMReader=  new org.dom4j.io.DOMReader();
		org.dom4j.Document dom4jDocument = dom4jDOMReader.read(w3cDocument);
		String dom4jContent = dom4jDocument.getRootElement().getText();
		assertEquals("it_works", dom4jContent);
		
	}
	
	

	


 
  

}
