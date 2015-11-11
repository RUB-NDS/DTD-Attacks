package de.rub;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.junit.runners.MethodSorters;

/**
 * OLD; Use tests from XercesSaxParseFactory
 * @author dev
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class testAttackVectors {

	 XMLReader parser;
	    XercesSax myContentHandler;
	    MyDeclHandler myDeclHandler;
	    
	    @BeforeClass
	    public static void setUpBeforeClass() throws Exception {
	    	
//	    	/tmp/xxe anlegen
//	    	/tmp/xinlcude_source.xml anlegen
	    }

	    @AfterClass
	    public static void tearDownAfterClass() throws Exception {
	    }

	    @Before
	    public void setUp() throws Exception {
		
		parser = XMLReaderFactory.createXMLReader();
		myContentHandler = new XercesSax();      
		parser.setContentHandler(myContentHandler);
		myDeclHandler = new MyDeclHandler();
		
		
	    }

	    @After
	    public void tearDown() throws Exception {
	    }
    
    /**
     * Security relevant tests concerning DTDs
     */
    
    @Test
    public void testStandard() throws IOException,SAXException {
	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.parse("../../xml_files/standard.xml");
	    

		String content =myContentHandler.getElementContent("data");
		assertEquals("4", content);
    }

    @Test
    // throws IOException, SaxException
    public void testDos() throws IOException,SAXException  {
	
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.parse("../../xml_files/dos.xml");
	    String expected = "&c;&c;&c;&c;&c;&c;&c;&c;&c;&c;";
	    assertEquals(myDeclHandler.getEntityValue("d"), expected);
	    	    
	    
		String content =myContentHandler.getElementContent("data");
		int expectedCount = 1000;
		// we know that each word is 3 chars long
		int dosCount = content.length() / 3;
		assertEquals(expectedCount, dosCount);
    }  
    
    
    
    @Test
    public void testXXE() throws IOException,SAXException {
	
    	assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.parse("../../xml_files/xxe.xml");
	    
	
		String content =myContentHandler.getElementContent("data");
		assertEquals("xxe", content);
    }
    
    @Test
    public void testXXE_setFeature_external_general_entity() throws SAXNotRecognizedException, SAXNotSupportedException, IOException,SAXException {	

    	//@TODO check the results if validation is turned on
		
		// external entities are resolved
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.setFeature("http://xml.org/sax/features/external-general-entities",false);
	    assertFalse(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
	    
	    parser.parse("../../xml_files/xxe.xml");

	 	
	
	
		String content = myContentHandler.getElementContent("data");
		assertEquals("", content);	
	    
    }
    
    @Test
    public void testParameterEntity() throws IOException, SAXException {
	
	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.parse("../../xml_files/paramEntity.xml");	    
	
		String content =myContentHandler.getElementContent("data");
		assertEquals("xxe", content);
    }
    
    @Test
    public void testParameterEntity_setFeature_external_general_entity() throws IOException, SAXException {
    	
    		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
    		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
    		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
    		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
    		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    		
    		parser.setFeature("http://xml.org/sax/features/external-general-entities",false);    		
    		assertFalse(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
    		
    	    parser.parse("../../xml_files/paramEntity.xml");
    	    
    	
	    	String content =myContentHandler.getElementContent("data");
	    	assertEquals("xxe", content);
   }
    
    @Test
    public void testParameterEntity_setFeature_external_parameter_entities() throws IOException, SAXException {
    
    		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
    		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
    		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
    		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
    		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    		
    		parser.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
    		assertFalse(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
    		
    	    parser.parse("../../xml_files/paramEntity.xml");
    	    
    
	    	String content =myContentHandler.getElementContent("data");
	    	assertEquals("", content);
    	
    }
    
    /**
     * We developed this attack ourselves using a combination of the Parameter Entity Attack 
     * and Inclusion of External Entities in Attribute Values.
     * This facilitates to read out a text file (no xml!) completely, not just the first line! 
     * This only works if the Parser processes DTDs AND is validating against an XSD Schema (should be viable quite often)
     *  
     *  It seems we are not the first one to think about that idea
     *  https://media.blackhat.com/eu-13/briefings/Osipov/bh-eu-13-XML-data-osipov-slides.pdf
     *  However Slide 38 states for Java Xerces:
     *  Cons:  Can’t read multiline files with OOB technique
     *  from which we presume that they did NOT try this combination!
     */
    @Test
    public void testParameterEntity_Attack_AttributeValue() throws IOException, SAXException {
    	assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
		
		assertFalse(parser.getFeature("http://apache.org/xml/features/validation/schema"));
		parser.setFeature("http://apache.org/xml/features/validation/schema",true);
		assertTrue(parser.getFeature("http://apache.org/xml/features/validation/schema"));
		
		parser.parse("../../xml_files/test/schemaEntity.xml");
		
		assertEquals("http://192.168.2.31/root:root sample:sample",
				 myContentHandler.getAttributeValue("noNamespaceSchemaLocation"));
    }
    
    
    
    @Test
    public void testXInclude() throws IOException, SAXException {   		
	

		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
	    parser.parse("../../xml_files/xinclude.xml");
	    

		String content =myContentHandler.getElementContent("data");
		assertEquals("includeinclude", content);
	//	assertNotEquals("contentsecretcontent",content);
		
	//	this will fail because XInclude is not supported	
		content = myContentHandler.getElementContent("content");
		assertEquals("", content);
	
    }
    
    
    @Test  
    public void testXInclude_setFeature_xinclude() throws IOException, SAXException {   		
	
	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
		
		
		parser.setFeature("http://apache.org/xml/features/xinclude", true);
		assertTrue(parser.getFeature("http://apache.org/xml/features/xinclude"));
	    parser.parse("../../xml_files/xinclude.xml");
	    
	
		String content =myContentHandler.getElementContent("data");
		assertEquals("contentsecretcontent", content);
		assertNotEquals("includeinclude",content);
		
	
		content = myContentHandler.getElementContent("content");
		assertEquals("secret", content);
	
    }
    
    
    
    @Test
    public void testUrlInvocation() throws IOException, SAXException{
	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));	
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
				
	    parser.parse("../../xml_files/url_invocation_doctype.xml");	    
	
		String file ="/tmp/output.txt";
		FileReader fr = new FileReader(file);
		BufferedReader textReader = new BufferedReader(fr);
		
		String content = textReader.readLine();
		assertEquals("1", content);
		
		File f = new File(file);
		
		if (f.exists()) {
			f.delete();
		}
	
	
    }
    
    
    @Test (expected = FileNotFoundException.class)
    public void testUrlInvocation_setFeature_load_external_dtd() throws IOException, SAXException{
	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));	
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
				
		parser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",false);
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		
	    parser.parse("../../xml_files/url_invocation_doctype.xml");
	    
	
		String file ="/tmp/output.txt";
		FileReader fr = new FileReader(file);
		BufferedReader textReader = new BufferedReader(fr);
		
		String content = textReader.readLine();
		assertEquals("1", content);
    }

}
