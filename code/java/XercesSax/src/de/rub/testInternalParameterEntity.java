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

public class testInternalParameterEntity {

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
     * Parameter Entities References 
     * ***********************************
     */
    
    
    
    /**
     * This test is an example for an Internal Parameter Entity Reference which is used in Content (between a start and end tag)
     * in an internal subset
     * @expected[c.f. specification]: Not recognised
     * @observed : The Internal Parameter Entity is not processed as @expected, so the PeReference is left as-is unexpanded. 
     * 			   
     *
     */
    @Test 
    public void testInternalSubset_PEReferenceInContent() throws IOException, SAXException {
    
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInContent.xml");
   	 	
   	 	assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
   	 	assertEquals("%internal;", myContentHandler.getElementContent("data"));
   	 	
    }    
    
    
    /**
     * This test is an example for a Parameter Entity Reference which is used in an Attribute Value in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed : If an internal Parameter Entity is used as an Attribute Value, it is not expanded as @expected.
     * 				Internal Parameter Entities are not recognized outside a DTD.  
     * 
     */
    @Test
    public void testInternalSubset_PEReferenceInAttValue_startTag() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInAttValue_startTag.xml");
   	 	
   	 	assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
   	 	assertEquals("", myContentHandler.getElementContent("data"));
   	 	assertEquals("%internal;", myContentHandler.getAttributeValue("sort"));
   	 	
   	 	
    }

    
    /**
     * This test is an example for a Parameter Entity Reference which is used as an Default Value in an Attribute Declaration 
     *  in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed : If an internal Parameter Entity is used as a Default Value for an Attribute, it is not expanded as @expected.
     * 				  
     * 
     */
    @Test
    public void testInternalSubset_PEReferenceInAttValue_DefaultValue() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInAttValue_DefaultValue.xml");
   	 	
   	 	assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
   	 	assertEquals("", myContentHandler.getElementContent("data"));
   	 	assertEquals("%internal;", myContentHandler.getAttributeValue("sort"));   	 	
   	 	
    }
    
    
    /**
     * This test is an example for an Internal Parameter Entity Reference which is used as an Attribute Value in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed :  The name of the Internal Parameter Entity is left as-is as an Attribute value
     * 				The specification makes no statement about how this should be dealt with.
     * 				
     *
     *				
     */
    @Test 
    public void testInternalSubset_PEReferenceAsAttValue() throws IOException, SAXException {
    	
		assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
		assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
		assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
		assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
    	// Validation 
		//[Error] internalSubset_EntityReferenceAsAttValue.xml:7:27: ENTITY "intern" ist geparst.
//		parser.setFeature("http://xml.org/sax/features/validation", true);
		
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceAsAttValue.xml");
   	 
   	 	assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
   	 	assertEquals("", myContentHandler.getElementContent("data"));
   	 	assertNotEquals("intern", myContentHandler.getAttributeValue("sort"));
   	 	assertEquals("internal", myContentHandler.getAttributeValue("sort"));
   	 	
    }
    
    /**
     * This test is an example for a Parameter Entity Reference which is used in a Parameter Entity in an internal subset
     * @expected[c.f. specification]: Included in literal
     * @observed : Within an internal subset Parameter Entities are subject to a Well-formedness constraint (WFC): PEs in Internal Subset
     * due to which usage of Parameter Entities in other Entities is not well-formed and constitutes a fatal error.  
     * 
     */    
    @Test 
    public void testInternalSubset_PEReferenceInParamEntity() throws IOException, SAXException {
    	 try {
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInParamEntity.xml");
    	 }
    	 catch (SAXParseException e) {
//    		 String meldung = "Parameterentitätsreferenz \"%internal;\" darf nicht in Markup in der internen Teilmenge der DTD vorkommen.";
    		 String meldung ="The parameter entity reference \"%internal;\" cannot occur within markup in the internal subset of the DTD.";
    		 assertEquals(meldung, e.getMessage());
    	 }    	 
    }    

    
    
    /**
     * This test is an example for a Parameter Entity Reference which is used in a Internal General Entity in an internal subset
     * @expected[c.f. specification]: Included in literal
     * @observed : Within an internal subset Parameter Entities are subject to a Well-formedness constraint (WFC): PEs in Internal Subset
     * due to which usage of Parameter Entities in other Entities is not well-formed and constitutes a fatal error.  
     * 
     */
    
    @Test  
    public void testInternalSubset_PEReferenceInInternalGeneralEntity() throws IOException, SAXException {
    	 try {
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInInternalGeneralEntity.xml");
        	         	 
    	 }
    	 catch (SAXParseException e) {
//    		 String meldung = "Parameterentitätsreferenz \"%internal;\" darf nicht in Markup in der internen Teilmenge der DTD vorkommen.";
    		 String meldung ="The parameter entity reference \"%internal;\" cannot occur within markup in the internal subset of the DTD.";
    		 assertEquals(meldung, e.getMessage());
    	 }    	 
    }
    
    
    
    /**
     * This test is an example for a Parameter Entity Reference which is used in a DTD in an internal subset
     * @expected[c.f. specification]: Included as PE (Parameter Entity in DTD)
     * @expected[c.f. specification]: Included (General Entity in Content)
     * @observed : The Parameter Entity Reference is included in the DTD as @expected.
     * 			   The included text is reparsed. A new Internal General Entity is found and resolved.
     * 				This Internal General Entity is then referenced within Content and the Replacement Text included as @expected.
     * 
     *      
     */
    @Test 
    public void testInternalSubset_PEReferenceInDTD() throws IOException, SAXException {
    	
		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInDTD.xml");
    	 
    	 
    	 assertEquals("<!ENTITY intern 'it_works'>", myDeclHandler.getEntityValue("%internal"));
    	 assertEquals("it_works", myDeclHandler.getEntityValue("intern"));
    	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	  	 
    }
    
    
    /**
     * This test is an example for a Parameter Entity Reference which is used in a DTD in an internal subset
     * @expected[c.f. specification]: Included as PE (Parameter Entity in DTD)     
     * @observed : The Parameter Entity Reference is called before it was declared. 
     * 				Calling a Parameter Entity before it is declared does not constitute a fatal error. 
     * 				Processing is resumed, however the values (Entity intern) are not available for further use.
     * 				
     * @conclusion: Parameter Entities which are used before they are declared are skipped.
     *    
     */
    @Test 
    public void testInternalSubset_PEReferenceInDTD_UseFirstDeclareLater() throws IOException, SAXException {
    	
		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 parser.parse("../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInDTD_UseFirstDeclareLater.xml");    	 
    	 
    	 assertEquals("<!ENTITY intern 'HI'>", myDeclHandler.getEntityValue("%internal"));     	 	  	 
    }
    
    

    /**
    * *****************************************************************************************************************
    * External Subset
    *******************************************************************************************************************
    */
   
   
   
   /**
    * Parameter Entities References 
    */
   
   
   
   /**
    * This test is an example for a Parameter Entity Reference which is used in a Parameter Entity in an external subset
    * @expected[c.f. specification]: Included in literal
    * @observed :  The value of the Parameter Entity "internal" is expanded within use
    * 				This is as @expected  				
    * 
    * @Conclusion: Parameter Entities behave differently in internal and external subsets.
    * 				The "WFC: PEs in Internal Subset" does not apply in an external subset.
    */
   @Test 
   public void testExternalSubset_PEReferenceInParamEntity() throws IOException, SAXException {
   	
   		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 parser.parse("../../xml_files_windows/dtd/externalSubset_PEReferenceInParamEntity.xml");
      //
       	 assertEquals("<!ENTITY intern 'it_works'>", myDeclHandler.getEntityValue("%internal"));
       	 assertEquals("<!ENTITY intern 'it_works'>", myDeclHandler.getEntityValue("%use"));
   	 
   }
   
   /**
    * This test is an example for a Parameter Entity Reference which is used in an Internal General Entity in an external subset
    * @expected[c.f. specification]: Included in literal
    * @observed :  The value of the Parameter Entity "internal" is expanded within use
    * 				This is as @expected  				
    * 
    * @Conclusion: Parameter Entities behave differently in internal and external subsets.
    * 				Parameter Entities behave the same in EntityValue in an external subset.
    * 				The "WFC: PEs in Internal Subset" does not apply in an external subset.
    */
   @Test 
   public void testExternalSubset_PEReferenceInInternalGeneralEntity() throws IOException, SAXException {
   	
   		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 parser.parse("../../xml_files_windows/dtd/externalSubset_PEReferenceInInternalGeneralEntity.xml");
       	 
       	 assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
       	 assertEquals("it_works", myDeclHandler.getEntityValue("use"));
   	 
   }
   
   
   /**
    * This test is an example for multiple Parameter Entity References which are used in an Internal General Entity in an external subset
    * @expected[c.f. specification]: Included in literal
    * @observed :  The value of the Parameter Entity "internal" is expanded within use
    * 				This is as @expected  				
    * 
    */
   @Test 
   public void testExternalSubset_PEReferenceInInternalGeneralEntity_Reparsing() throws IOException, SAXException {
   	
   		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 parser.parse("../../xml_files_windows/dtd/externalSubset_PEReferenceInInternalGeneralEntity_Reparsing.xml");
       	 
       	 assertEquals("it_works", myDeclHandler.getEntityValue("%internal"));
       	 assertNotEquals("%internal;", myDeclHandler.getEntityValue("%tmp"));
       	 assertEquals("it_works", myDeclHandler.getEntityValue("%tmp"));
       	 assertEquals("it_works", myDeclHandler.getEntityValue("use"));
   	 
   }   
   
	
}
