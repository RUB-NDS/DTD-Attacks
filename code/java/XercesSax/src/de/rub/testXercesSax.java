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
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * OLD
 * @author dev
 *
 */

public class testXercesSax {

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
     * ++++++++++++++++++++++++++++++++++++++
     * Advanced tests
     * ++++++++++++++++++++++++++++++++++++++++ 
     */
    
    
    
    /**
     * This test is an example for a complex set of interactions.
     * There are 4 Parameter Entities
     * 
     * Internal Parameter Entity: "start" and "end"
     * External Parameter Entity: "goodies" and "dtd" 
     * 
     * Parameter Entity "dtd" is expanded in the DTD and includes an Internal General Entity "all"
     * 
     * @expected[c.f. specification]: Included in literal ("start", "end", "goodies" within "all")
     * @expected[c.f. specification]: Included as PE (dtd)
     * @expected[c.f. specification]: Included (all)
     * 
     * 
     * @observed : The value of Parameter Entity "goodies" is the fully expanded path to the resource.
     *       	   The value of Parameter Entity "dtd" is the fully expanded path to the resource.
     *       	   When %dtd is called, Internal General Entity "all" is included
     *             The literal value of Internal General Entity "all" is "%start;%goodies;%end;".
     *             The Replacement Text of Internal General Entity "all" is "<!CDATA[secret]]>". 
     *             The Replacement Text of Internal General Entity "all" is included in element "data"
     *             without the CDATA. 				  				
     * 
     */
    @Test
    public void testInternalSubset_PEReferenceIn_ExternalSubsetInternalGeneralEntity() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files/dtd/internalSubset_PEReferenceIn_ExternalSubsetInternalGeneralEntity.xml");
   	 	
   	 	assertEquals("file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files/dtd/internalSubset_PEReferenceIn_ExternalSubsetInternalGeneralEntity_source.txt",
   	 			myDeclHandler.getEntityValue("%goodies"));
   	 	
   	 	assertEquals("file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/internalSubset_PEReferenceIn_ExternalSubsetInternalGeneralEntity.dtd", 
   			 			myDeclHandler.getEntityValue("%dtd")); 	
   	 	
   	 	
   	 	assertEquals("<![CDATA[secret]]>", myDeclHandler.getEntityValue("all"));
   	 	assertEquals("secret", myContentHandler.getElementContent("data"));
   	 
    }
    

    /**
     * This tests aims at showing the different contexts in which external entities retrieve their content 
     * This is demonstrated by creating two source files with identical names but different content in different directories.
     * Show what happens if the attack of Morgan is completely executed in a external DTD.
     * 
     * 
     * @Conclusion: The attack can be executed completely in an external DTD, which is referenced in the DOCTYPE
     */
    @Test
    public void testExternalSubset_ExternalPEReferenceIn_ExternalSubset_InternalGeneralEntity() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files/dtd/externalSubset_ExternalPEReferenceIn_ExternalSubset_InternalGeneralEntity.xml");
   	 	
   	 	
   	 	
   	 	assertEquals("file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/"   	 					
   	 					+"externalSubset_ExternalPEReferenceIn_ExternalSubset_InternalGeneralEntity_source.txt",
   	 					myDeclHandler.getEntityValue("%goodies"));
   	 	
   	 	 	 	
   	 	assertEquals("<!ENTITY all '<![CDATA[hier sind die goodies]]>'>", myDeclHandler.getEntityValue("%dtd"));  	 	
   	 	
   	 	assertEquals("<![CDATA[hier sind die goodies]]>", myDeclHandler.getEntityValue("all"));
   	 	
   	 	assertEquals("hier sind die goodies", myContentHandler.getElementContent("data"));
   	 
    }
    
    
  
    
   
	 	

    
  
    
  
    
    
    
    
    
    
}
    
