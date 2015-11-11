package de.rub;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class testCharacters {


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
     * ***********************************
     * Character References
     * ***********************************
     */
    
    /**
     * This test is an example for an Character Reference which is used as Content in an internal subset
     * @expected[c.f. specification]: Included
     * @observed :  The Character Reference is expanded as @expected 				
     *
     */
    @Test 
    public void testInternalSubset_CharacterInContent() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInContent.xml");        	         	 
        	 assertEquals("AT&T", myContentHandler.getElementContent("data"));
    }
    
    
    /**
     * This test is an example for an Character Reference which is used in a Parameter Entity in an internal subset
     * @expected[c.f. specification]: Included
     * @observed :  The Character Reference is expanded as @expected, however the text is not reparsed.
     * 				So the Character Reference (&) is expanded and the remaining text (amp;) are left as-is, 
     * 				and are not interpreted as another Entity Reference (&amp;) 
     *
     *@conclusion: Character References are expanded immediately within Content, however the text is not parsed again
     *				after the Replacement Text is inserted. 
     */
    @Test 
    public void testInternalSubset_CharacterInContent_NoRecursion() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInContent_NoRecursion.xml");        	         	 
        	 assertEquals("AT&amp;T", myContentHandler.getElementContent("data"));
    }
    
    
    /**
     * This test is an example for an Character Reference which is used in a Parameter Entity in an internal subset
     * @expected[c.f. specification]: Included
     * @observed :  The Character References in an Attribute Value are immediately expanded and included as @expected. 
     *
     *
     */
    @Test 
    public void testInternalSubset_CharacterInAttValue_startTag() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInAttValue_startTag.xml");
        	 
        	 assertEquals("AT&T", myContentHandler.getAttributeValue("sort"));
    }
    
    
    
    
    /**
     * This test is an example for a Character Reference which is used as Default Value for an Attribute Value in an internal subset
     * @expected[c.f. specification]: Included
     * @observed : The Character References if provided as an Default Value for an Attribute Value 
     * 				are immediately expanded and included as @expected.
     *
     */
    @Test 
    public void testInternalSubset_CharacterInAttValue_DefaultValue() throws IOException, SAXException {
    	

    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInAttValue_DefaultValue.xml");
   	 	assertEquals("AT&T", myDeclHandler.getAttributeValue("sort"));
   	 	assertEquals("AT&T", myContentHandler.getAttributeValue("sort"));
   	 
   	 	
	 	
    }
    
    
    /**
     * This test is an example for an Character Reference which is used in a Parameter Entity in an internal subset
     * @expected[c.f. specification]: Included
     * @observed :  The Character Reference is expanded as @expected 				
     *
     */
    @Test 
    public void testInternalSubset_CharacterInParamEntity() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInParamEntity.xml");        	         	 
        	 assertEquals("intern", myDeclHandler.getEntityValue("%internal"));
    }
    
    /**
     * This test is an example for an Character Reference which is used in a DTD in an internal subset
     * @expected[c.f. specification]: Forbidden
     * @observed :  A Character Reference within a DTD does not constitute allowed markup, even if it encodes
     * 				allowed markup (<!ENTITY internal "intern"> ), so a fatal error is raised. 				
     *
     * 
     */
    @Test 
    public void testInternalSubset_CharacterInDTD() throws IOException, SAXException {
    	try {
    		
    		parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 	parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInDTD.xml");       	         	 
       	 	
    	}catch (SAXParseException e) {
//    		String meldung ="Die Markup-Deklarationen, die in der Dokumenttypdeklaration enthalten sind bzw. auf die von der Dokumenttypdeklaration verwiesen wird, müssen ordnungsgemäß formatiert sein.";
    		String meldung ="The markup declarations contained or pointed to by the document type declaration must be well-formed.";
    		assertEquals(meldung, e.getMessage());
    	}	
    		 
    }
    
    
    /**
     * This test is an example for an single Character Reference which is used in a DTD in an internal subset
     * @expected[c.f. specification]: Forbidden
     * @observed :  A Character Reference within a DTD does not constitute allowed markup, even if it encodes
     * 				allowed markup (<!ENTITY internal "intern"> ), so a fatal error is raised. 				
     *
     * 
     */
    @Test 
    public void testInternalSubset_CharacterInDTD_singleCharRefInName() throws IOException, SAXException {
    	try {
    		
    		parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 	parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInDTD_singleCharRefInName.xml");       	         	 
       	 	
    	}catch (SAXParseException e) {
//    		String meldung ="Name der Entität ist in der Entitätsdeklaration erforderlich.";
    		String meldung ="The name of the entity is required in the entity declaration.";
    		assertEquals(meldung, e.getMessage());
    	}	
    		 
    }
    
    
    
    /**
     * This test is an example for an Character Reference which is used in an Internal General Entity 
     * in an internal subset
     * @expected[c.f. specification]: Included
     * @observed :  The Character Reference is expanded as @expected 				
     *
     * @Conclusion: Character References are immediately included (resolved) within an EntityValue. 
     * 				There is no difference between Internal General Entities and Parameter Entities.
     */
    @Test 
    public void testInternalSubset_CharacterInInternalGeneralEntity() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/character/internalSubset_CharacterInInternalGeneralEntity.xml");        	         	 
        	 assertEquals("intern", myDeclHandler.getEntityValue("internal"));
    }
    

}
