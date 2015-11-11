package de.rub;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class testUnparsedEntity {

	XMLReader parser;
    XercesSax myContentHandler;
    MyDeclHandler myDeclHandler;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	
//    	/tmp/xxe anlegen
//    	/tmp/xinlcude_source.xml anlegen
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
     * ***********************************
     * Unparsed Entities
     * ***********************************
     */
    
    /**
     * This test is an example for an Unparsed Entity which is used as Content
     *  in an internal subset
     * @expected[c.f. specification]: Forbidden
     * @observed :  The Unparsed Entity is not expanded, nothing happens.
     * 				This is NOT as @expected. It should constitute a fatal error.				
     *				Receive notification of an unparsed entity declaration.

					The Docs of DefaultHandler: By default, do nothing. Application writers may override this method in a subclass to keep track of the unparsed entities declared in a document.
					[https://xerces.apache.org/xerces2-j/javadocs/api/org/xml/sax/helpers/DefaultHandler.html]
     */
    @Test 
    public void testInternalSubset_UnparsedEntityInContent() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/unparsed/internalSubset_UnparsedEntityInContent.xml");        	         	 
        	 assertEquals("turing", myContentHandler.getElementContent("data"));
    }
    
    /**
     * This test is an example for an Unparsed Entity which is used in an Attribute Value 
     * in an internal subset
     * @expected[c.f. specification]: Forbidden
     * @observed :  The Unparsed Entity is not expanded, nothing happens.
     * 				This is NOT as @expected. It should constitute a fatal error.
     * 				
     * 				The Docs of DefaultHandler: By default, do nothing. Application writers may override this method in a subclass to keep track of the unparsed entities declared in a document.
					[https://xerces.apache.org/xerces2-j/javadocs/api/org/xml/sax/helpers/DefaultHandler.html]				
     *
     */
    @Test 
    public void testInternalSubset_UnparsedEntityInAttValue_startTag() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/unparsed/internalSubset_UnparsedEntityInAttValue_startTag.xml");        	         	 
        	 assertEquals("turing", myContentHandler.getAttributeValue("source"));
    }

    

    /**
     * This test is an example for an Unparsed Entity which is used as an Attribute Value in an internal subset
     * @expected[c.f. specification]: Notify
     * @observed :  Nothing happens;  
     * 
     * 				The Docs of DefaultHandler: By default, do nothing. Application writers may override this method in a subclass to keep track of the unparsed entities declared in a document.
					[https://xerces.apache.org/xerces2-j/javadocs/api/org/xml/sax/helpers/DefaultHandler.html]
     * 				
     * 				
     *
     *				
     */
    @Test 
    public void testInternalSubset_UnparsedEntityAsAttValue() throws IOException, SAXException {
    	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	// Validation 
		//[Error] internalSubset_EntityReferenceAsAttValue.xml:7:27: ENTITY "intern" ist geparst.
		parser.setFeature("http://xml.org/sax/features/validation", true);
		
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/unparsed/internalSubset_UnparsedEntityAsAttValue.xml");
   	 
   	 	assertEquals("", myContentHandler.getElementContent("tree"));
   	 	assertEquals("turing", myContentHandler.getAttributeValue("source"));
   	 	
    }
}
