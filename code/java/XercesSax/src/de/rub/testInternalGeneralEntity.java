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

public class testInternalGeneralEntity {

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
	     * Internal General Entity References
	     * *********************************** 
	     */
	    
	  
	    
	    
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used in Content (between a start and end tag)
	     * in an internal subset
	     * @expected[c.f. specification]: Included
	     * @observed :  
	     *
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceInContent() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInContent.xml");
	        	 
	        	 assertEquals("it_works", myDeclHandler.getEntityValue("internal"));        	 
	        	 
	        	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	    	 
	    }
	    
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used multiple times in Content (between a start and end tag)
	     * in an internal subset
	     * @expected[c.f. specification]: Included
	     * @observed : The Internal General Entity &amp; is expanded as @expected,
	     * 				however after that a new Character Event is fired and the remaining string "internal;" is parsed 
	     * 				separately. Therefore the parser does not recognize this as a new Internal General Entity. 
	     *
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceInContent_multipleUse() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInContent_multipleUse.xml");
	        	 
	        	 assertEquals("&a;works", myDeclHandler.getEntityValue("internal"));       	 
	        	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	    	 
	    }
	    
	    
	  
	    /**
	     * This test is an example for an Internal General Entity Reference which is used as Default Value for an Attribute in an internal subset
	     * @expected[c.f. specification]: Included in literal
	     * @observed :  The Internal General Entity is expanded as @expected
	     *
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceInAttValue_DefaultValue() throws IOException, SAXException {
	    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	   	 	parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInAttValue_DefaultValue.xml");
	   	 
	   	 	assertEquals("it_works", myDeclHandler.getEntityValue("internal"));  	 	
	   	 	assertEquals("", myContentHandler.getElementContent("tree"));
	   	 	assertEquals("it_works", myContentHandler.getAttributeValue("sort"));
	    }
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used as a Default Value in an Attribute in an internal subset
	     * @expected[c.f. specification]: Included in literal
	     * @observed : The Internal General Entity is expanded as @expected 
	     *
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceInAttValue_startTag() throws IOException, SAXException {
	    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	   	 	parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInAttValue_startTag.xml");
	   	 
	   	 	assertEquals("it_works", myDeclHandler.getEntityValue("internal"));  	 	
	   	 	assertEquals("", myContentHandler.getElementContent("tree"));
	   	 	assertEquals("it_works", myContentHandler.getAttributeValue("sort"));
	    }
	    
	    
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used as an Attribute Value in an internal subset
	     * @expected[c.f. specification]: Forbidden
	     * @observed : The name of the Internal General Entity is left as-is as an Attribute value.
	     * 				The specification makes no statement about how this should be dealt with.
	     * 			   
	     *    				
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceAsAttValue() throws IOException, SAXException {
	    	
			assertFalse(parser.getFeature("http://xml.org/sax/features/validation"));
			assertTrue(parser.getFeature("http://xml.org/sax/features/external-general-entities"));
			assertTrue(parser.getFeature("http://xml.org/sax/features/external-parameter-entities"));
			assertFalse(parser.getFeature("http://apache.org/xml/features/xinclude"));
			assertTrue(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd"));
			assertFalse(parser.getFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar"));
	    	// Validation 
			//[Error] internalSubset_EntityReferenceAsAttValue.xml:7:27: ENTITY "intern" ist geparst.
//			parser.setFeature("http://xml.org/sax/features/validation", true);
			
	    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	   	 	parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceAsAttValue.xml");
	   	 
	   	 	assertEquals("it_works", myDeclHandler.getEntityValue("internal"));  	 	
	   	 	assertEquals("", myContentHandler.getElementContent("tree"));
	   	 	assertNotEquals("intern", myContentHandler.getAttributeValue("sort"));
	   	 	assertEquals("internal", myContentHandler.getAttributeValue("sort"));
	    }
	    
	    
	    
	    
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used in a Parameter Entity in an internal subset
	     * @expected[c.f. specification]: Bypassed
	     * @observed : The Internal General Entity is not expanded as @expected and the Entity Reference (&internal;) 
	     * 				is the value of the Parameter Entity 
	     *				The Internal General Entity is expanded when the it is used within content
	     */
	    @Test
	    public void testInternalSubset_EntityReferenceInParamEntity() throws IOException, SAXException {
	    	 
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInParamEntity.xml");
	        	 
	        	 assertEquals("it_works", myDeclHandler.getEntityValue("internal"));
	        	 assertEquals("&internal;", myDeclHandler.getEntityValue("%use"));
	        	 
	        	 assertEquals("", myContentHandler.getElementContent("data"));
	    	     	 
	    }
	  
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used in an Internal General Entity in an internal subset
	     * @expected[c.f. specification]: Bypassed
	     * @observed : The Internal General Entity is not expanded as @expected and the Entity Reference (&internal;) 
	     * 				is the value of the General Entity 
	     *				The Internal General Entity is expanded when the it is used within content
	     *
	     * @Conclusion: The behaviour of Internal General Entities is consistent when used in an EntityValue 
	     * 				(both Parameter Entities and General Entities)
	     */
	    @Test 
	    public void testInternalSubset_EntityReferenceInInternalGeneralEntity() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInInternalGeneralEntity.xml");
	        	 
	        	 assertEquals("it_works", myDeclHandler.getEntityValue("internal"));
	        	 assertEquals("&internal;", myDeclHandler.getEntityValue("use"));
	        	 
	        	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	    	 	 
	    }
	    
 
	    
	    
	    /**
	     * This test is an example for an Internal General Entity Reference which is used in the DTD in an internal subset
	     * @expected[c.f. specification]: Forbidden [fatal error]
	     * @observed : The use of an Internal General Entity within a DTD does not constitute well-formed markup for a DTD
	     * 				A SAXParseException is raised as @expected.
	     *
	     */
	    @Test
	    public void testInternalSubset_EntityReferenceInDTD() throws IOException, SAXException {
	    	try {
	    		parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	       	 	parser.parse("../../xml_files_windows/dtd/internalGeneralEntity/internalSubset_EntityReferenceInDTD.xml");
	    	}
	    	catch (SAXParseException e){
//	    		 String meldung = "Die Markup-Deklarationen, die in der Dokumenttypdeklaration enthalten sind bzw. auf die von der Dokumenttypdeklaration verwiesen wird, müssen ordnungsgemäß formatiert sein.";
	    		 String meldung ="The markup declarations contained or pointed to by the document type declaration must be well-formed.";
	    		 assertEquals(meldung, e.getMessage());
	    	}
	    	
	    	
	    }
	    
	    /**
	     * *****************************************************************************************************************
	     * External Subset
	     *******************************************************************************************************************
	     */
	    
	    /**
	     * Internal General Entity References
	     * External General Entity References
	     * 
	     * We have already shown that within an internal subset Internal and External General Entities are subject to the same rules when used 
	     * within Content (Included) and EntityValue (Bypassed). 
	     * There is no reason to assume why this should be different in an external subset. 
	     * Therefore further tests are postponed until there is reason to believe otherwise.   
	     *  
	     */     

}
