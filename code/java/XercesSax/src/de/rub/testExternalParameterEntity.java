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

public class testExternalParameterEntity {

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
    
    /*****
     * *********
     * External Parameter Entities
     * *********
     */
    
    
    
    /**
     * This test is an example for an External Parameter Entity Reference which is used in Content (between a start and end tag)
     * in an internal subset
     * @expected[c.f. specification]: Not recognised
     * @observed : The Internal Parameter Entity is not processed as @expected, so the PeReference is left as-is unexpanded. 
     * 			   
     *@conclusion: There is no difference between an internal or external Parameter Entity when used in Content within an internal subset.
     */
    @Test 
    public void testInternalSubset_ExternalPEReferenceInContent() throws IOException, SAXException {
    
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInContent.xml");

   	 	assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInContent.txt",
   	 				myDeclHandler.getEntityValue("%external"));
   	 	assertEquals("%external;", myContentHandler.getElementContent("data"));
   	 	
    }    
    
    
    
    /**
     * This test is an example for an External Parameter Entity Reference which is used in an Attribute Value in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed : If an External Parameter Entity is used as an Attribute Value, it is not expanded as @expected.
     * 				External Parameter Entities are not recognized outside a DTD.  
     * 
     * @conclusion: There is no difference between an internal or external Parameter Entity when used in Attribute Value within an internal subset. 
     */
    @Test
    public void testInternalSubset_ExternalPEReferenceInAttValue_startTag() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInAttValue_startTag.xml");
   	 	
   	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInAttValue_startTag.txt",
   	 	myDeclHandler.getEntityValue("%external"));
   	 	assertEquals("", myContentHandler.getElementContent("tree"));
   	 	assertEquals("%external;", myContentHandler.getAttributeValue("sort"));
   	 	
   	 	
    }
    
    /**
     * This test is an example for an External Parameter Entity Reference which is used as an Default Value in an Attribute Declaration 
     *  in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed : If an External Parameter Entity is used as a Default Value for an Attribute, it is not expanded as @expected.
     * 				  
     * @conclusion: There is no difference between an internal or external Parameter Entity when used as a Default Value for an Attribute within an internal subset.
     */
    @Test
    public void testInternalSubset_ExternalPEReferenceInAttValue_DefaultValue() throws IOException, SAXException {
    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
   	 	parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInAttValue_DefaultValue.xml");
   	 	
   	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInAttValue_DefaultValue.txt",
   	 	myDeclHandler.getEntityValue("%external"));
   	 	assertEquals("", myContentHandler.getElementContent("tree"));
   	 	assertEquals("%external;", myContentHandler.getAttributeValue("sort"));   	 	
   	 	
    }
 

    /**
     * This test is an example for an External Parameter Entity Reference which is used as an Attribute Value in an internal subset
     * @expected[c.f. specification]: Not recognized
     * @observed :  The name of the External Parameter Entity is left as-is as an Attribute value
     * 				The specification makes no statement about how this should be dealt with.
     * 				
     *
     *				
     */
    @Test 
    public void testInternalSubset_ExternalPEReferenceAsAttValue() throws IOException, SAXException {
    	
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
   	 	parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceAsAttValue.xml");
   	 
   	 	assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceAsAttValue.txt"
   	 			, myDeclHandler.getEntityValue("%external"));  	 	
   	 	assertEquals("", myContentHandler.getElementContent("tree"));
   	 	assertEquals("external", myContentHandler.getAttributeValue("sort"));
   	 	assertNotEquals("inhalt", myContentHandler.getAttributeValue("sort"));
    }
    
    
    /**
     * This test is an example for an External Parameter Entity Reference which is used in a Parameter Entity in an internal subset
     * @expected[c.f. specification]: Included in literal
     * @observed : Within an internal subset Parameter Entities are subject to a Well-formedness constraint (WFC): 
     * due to which usage of Parameter Entities in other Entities is not well-formed and constitutes a fatal error.  
     * 
     * @Conclusion: There is no difference between Internal and External Parameter Entities when used in an EntityValue in an internal subset.
     * 				They are both subject to the WFC: PEs in Internal Subset
     */
    @Test 
    public void testInternalSubset_ExternalPEReferenceInParamEntity() throws IOException, SAXException {
   	 try {
		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInParamEntity.xml");
	 }
	 catch (SAXParseException e) {
//		 String meldung = "Parameterentitätsreferenz \"%external;\" darf nicht in Markup in der internen Teilmenge der DTD vorkommen.";
		 String meldung ="The parameter entity reference \"%external;\" cannot occur within markup in the internal subset of the DTD.";
		 assertEquals(meldung, e.getMessage());
	 }    	 
    }

    /**
     * This test is an example for an External Parameter Entity Reference which is used in a Internal General Entity in an internal subset
     * @expected[c.f. specification]: Included in literal
     * @observed : Within an internal subset Parameter Entities are subject to a Well-formedness constraint (WFC): PEs in Internal Subset
     * due to which usage of Parameter Entities in other Entities is not well-formed and constitutes a fatal error.  
     * 
     * @conclusion: There is no difference between Internal and External Parameter Entities when used in an EntityValue in an internal subset.
     * 				They are both subject to the WFC: PEs in Internal Subset
     */
    @Test 
    public void testInternalSubset_ExternalPEReferenceInInternalGeneralEntity() throws IOException, SAXException {
    	try {
   		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
       	 parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInInternalGeneralEntity.xml");
       	         	 
   	 }
   	 catch (SAXParseException e) {
//   		 String meldung = "Parameterentitätsreferenz \"%external;\" darf nicht in Markup in der internen Teilmenge der DTD vorkommen.";
   		 String meldung ="The parameter entity reference \"%external;\" cannot occur within markup in the internal subset of the DTD.";
   		 assertEquals(meldung, e.getMessage());
   	 }    	 
    }
    
       

    /**
     * This test is an example for an External Parameter Entity Reference which is used in a DTD in an internal subset
     * @expected[c.f. specification]: Included as PE (Parameter Entity in DTD)
     * @expected[c.f. specification]: Included (General Entity in Content)
     * @observed : The Parameter Entity Reference is included in the DTD as @expected.
     * 			   The included text is reparsed. A new Internal General Entity is found and resolved.
     * 				This Internal General Entity is then referenced within Content and the Replacement Text included as @expected.
     * 
     *@conclusion: There is no difference between Internal and External Parameter Entities when used in a DTD in an internal subset.       
     */
    @Test 
    public void testInternalSubset_ExternalPEReferenceInDTD() throws IOException, SAXException {
    	
		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
    	 parser.parse("../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.xml");
    	     	 
    	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.dtd",
	 				myDeclHandler.getEntityValue("%external"));
    	 assertEquals("it_works", myDeclHandler.getEntityValue("intern"));
    	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	  	 
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
     * This test is an example for an External Parameter Entity Reference which is used in a Parameter Entity in an external subset
     * @expected[c.f. specification]: Included in literal
     * @observed : The value of Parameter Entity "external" is the fully expanded path to the resource.
     * 				Within Parameter Entity "use" the PEReference "external" is resolved and the contents included as @expected  
     * 				  				
     * @Conclusion: External Parameter Entities have the fully resolved path as their value, 
     * 				which seems to be identical to External General Entities   
     */
    @Test 
    public void testExternalSubset_ExternalPEReferenceInParamEntity() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/externalSubset_ExternalPEReferenceInParamEntity.xml");
        	 
        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalSubset_ExternalPEReferenceInParamEntity.txt",
        			 myDeclHandler.getEntityValue("%external"));
        	 
        	 
        	 assertEquals("it_works", myDeclHandler.getEntityValue("%use"));
    	 
    }

    /**
     * This test is an example for an External Parameter Entity Reference which is used in an Internal General Entity
     * in an external subset
     * @expected[c.f. specification]: Included in literal
     * @observed : The value of Parameter Entity "external" is the fully expanded path to the resource.
     * 				Within Internal General Entity "use" the PEReference "external" is resolved and the contents included as @expected  
     * 				  				
     * @Conclusion: External Parameter Entities have the fully resolved path as their value, 
     * 				which seems to be identical to External General Entities   
     * 
     * @Conclusion: Internal and External Parameter Entities behave the same in EntityValue (General/Parameter Entity) 
     * 				in an external subset. 				
     * 				The "WFC: PEs in Internal Subset" does not apply in an external subset. 
     */
    @Test 
    public void testExternalSubset_ExternalPEReferenceInInternalGeneralEntity() throws IOException, SAXException {
    	
    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
        	 parser.parse("../../xml_files_windows/dtd/externalSubset_ExternalPEReferenceInInternalGeneralEntity.xml");
        	 
        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalSubset_ExternalPEReferenceInInternalGeneralEntity.txt",
        			 myDeclHandler.getEntityValue("%external"));
        	 assertEquals("it_works", myDeclHandler.getEntityValue("use"));    	 
    }

}
