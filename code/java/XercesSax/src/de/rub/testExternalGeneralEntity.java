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

public class testExternalGeneralEntity {


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
	     * External General Entity References
	     * ***********************************
	     */
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is only declared, but never used in an internal subset
	     * @expected[c.f. specification]: -
	     * @observed : The value of the External General Entity is the fully resolved path to the file.
	     * 			   The value of the content is as @expected, the content of the file.  
	     *
	     * 
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntity_Definition_NoReference() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntity_Definition_NoReference.xml");
	        	 
	        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntity_Definition_NoReference.txt",
	        			 myDeclHandler.getEntityValue("external"));
	        	        	 
	        	 
	    	 
	    }
	    
	    
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in Content (between a start and end tag)
	     *  in an internal subset
	     * @expected[c.f. specification]: Included if validating
	     * @observed : The value of the External General Entity is the fully resolved path to the file.
	     * 			   The value of the content is as @expected, the content of the file.  
	     *
	     * 
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInContent() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInContent.xml");
	        	 
	        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInContent.txt",
	        			 myDeclHandler.getEntityValue("external"));
	        	 
	        	 
	        	 assertEquals("it_works", myContentHandler.getElementContent("data"));
	    	 
	    }
	    
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in an Attribute Value in an internal subset
	     * @expected[c.f. specification]: Forbidden
	     * @observed : The value of the External General Entity is the fully resolved path to the file.
	     * 			   The External General Entity is not expanded, but a SAXParseException is raised (Fatal error), because this 
	     * 				is not well-formed (Well-formedness constraint: No External Entity References)  
	     *
	     * 
	     */
	    
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInAttValue_startTag() throws IOException, SAXException {
	    	
	    		try {
	    			parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	           	 	parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInAttValue_startTag.xml");
	           	 
	           	 assertEquals("file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInAttValue_startTag.txt",
	           			 myDeclHandler.getEntityValue("external"));
	           		
	    		}
	    		catch(SAXParseException e) {
//	    			String meldung ="Externe Entitätsreferenz \"&external;\" ist in einem Attributwert nicht zulässig.";
	    			String meldung ="The external entity reference \"&external;\" is not permitted in an attribute value.";
	    			assertEquals(meldung, e.getMessage());
	    		}
	    }

	    /**
	     * This test is an example for an  General Entity Reference which is used as Default Value for an Attribute Value in an internal subset
	     * @expected[c.f. specification]: Included in literal
	     * @observed : The value of the External General Entity is the fully resolved path to the file.
	     * 			   The External General Entity is not expanded, but a SAXParseException is raised (Fatal error), because this 
	     * 				is not well-formed (Well-formedness constraint: No External Entity References) 
	     *
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInAttValue_DefaultValue() throws IOException, SAXException {
	    	
	    	try {
	    	parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	   	 	parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInAttValue_DefaultValue.xml");
	   	 
	   	 assertEquals("file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInAttValue_DefaultValue.txt",
	   	 	myDeclHandler.getEntityValue("external"));   	 
	   	 	
	    	}
	    	catch(SAXParseException e) {
//	    		String meldung ="Externe Entitätsreferenz \"&external;\" ist in einem Attributwert nicht zulässig.";
	    		String meldung ="The external entity reference \"&external;\" is not permitted in an attribute value.";
	    		assertEquals(meldung, e.getMessage());
	    	}   	 	
	    }
	    
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used as an Attribute Value in an internal subset
	     * @expected[c.f. specification]: Forbidden
	     * @observed :  The name of the External General Entity is left as-is as an Attribute value
	     * 				The specification makes no statement about how this should be dealt with.
	     * 				
	     *
	     *				
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceAsAttValue() throws IOException, SAXException {
	    	
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
	   	 	parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceAsAttValue.xml");
	   	 
	   	 	assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceAsAttValue.txt"
	   	 			, myDeclHandler.getEntityValue("external"));  	 	
	   	 	assertEquals("", myContentHandler.getElementContent("tree"));
	   	 	assertEquals("external", myContentHandler.getAttributeValue("sort"));
	   	 	assertNotEquals("inhalt", myContentHandler.getAttributeValue("sort"));
	    }
	    
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in Content (between a start and end tag)
	     *  in an internal subset and the source file has a Character Reference
	     * @expected[c.f. specification]: Included if validating (External Entity)
	     * @expected[c.f. specification]: Included (Character Reference)     
	     * @observed : The value of the External General Entity is the fully resolved path to the file.
	     * 
	     * 			   The value of the content is supposed to be the "replacement text [of the External Entity] is the content of the entity, 
	     * 			   but without any replacement of character references or parameter-entity references. 
	     *			   The expected result is: "hier ist 0&#x41;7", however the Character Reference is replaced.
	     *				This can be explained by the second @expected, which states that Character References are 
	     *				included within Content 
	     *
	     *@Conclusion: External General Entities References are included by inserting their Replacement Text without
	     *			   further processing. However other rules (e.g. Character References) from the specification 
	     *				may then apply.  
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInContent_withCharacterReference() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInContent_withCharacterReference.xml");
	        	 
	        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInContent_withCharacterReference.txt",
	        			 myDeclHandler.getEntityValue("external"));
	        	 
	        	 String expected = "hier ist 0A7";
	        	 assertEquals(expected, myContentHandler.getElementContent("data"));
	    	 
	    }
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in a Parameter Entity in an internal subset
	     * @expected[c.f. specification]: Bypassed
	     * @observed :  The value of the External General Entity is the fully resolved path to the file.
	     * 				The External General Entity is not expanded as @expected and the Entity Reference (&external;) 
	     * 				is the value of the Parameter Entity 
	     *				
	     *
	     * @Conclusion: The behaviour of External General Entities is consistent with Internal General Entities (Bypassed)
	     * when used in Parameter Entities. 
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInParamEntity() throws IOException, SAXException {

	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInParamEntity.xml");
	        	 
	        	 assertEquals("file:///C:/Christopher_Spaeth/code/xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInParamEntity.txt",
	        			 myDeclHandler.getEntityValue("external"));
	        	 
	        	 assertEquals("&external;", myDeclHandler.getEntityValue("%use"));
	        	        	 
	    	     	 
	    }
	  
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in an Internal General Entity 
	     * in an internal subset
	     * @expected[c.f. specification]: Bypassed
	     * @observed :  The value of the External General Entity is the fully resolved path to the file.
	     * 				The External General Entity is not expanded as @expected and the Entity Reference (&external;) 
	     * 				is the value of the Internal General Entity 
	     *				
	     *
	     * @Conclusion: The behaviour of External General Entities is consistent with Internal General Entities (Bypassed)
	     * when used in Internal General Entities. 
	     * 
	     * @Conclusion: Internal and External General Entities are subject to the same rules (Bypassed)
	     * 				in EntityValue (applies to Parameter/General Entities) 
	     * 				in an internal subset (There is no WFC!)
	     */
	    @Test 
	    public void testInternalSubset_ExternalEntityReferenceInInternalGeneralEntity() throws IOException, SAXException {
	    	
	    		 parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	        	 parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInInternalGeneralEntity.xml");
	        	         	 
	        	 assertEquals("&external;",myDeclHandler.getEntityValue("use"));         	 	 
	    }
	    
	    
	    /**
	     * This test is an example for an External General Entity Reference which is used in the DTD in an internal subset
	     * @expected[c.f. specification]: Forbidden [fatal error]
	     * @observed : The use of an External General Entity within a DTD does not constitute well-formed markup for a DTD
	     * 				A SAXParseException is raised as @expected.
	     *
	     */
	    @Test
	    public void testInternalSubset_ExternalEntityReferenceInDTD() throws IOException, SAXException {
	    	try {
	    		parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
	       	 	parser.parse("../../xml_files_windows/dtd/externalGeneralEntity/internalSubset_ExternalEntityReferenceInDTD.xml");
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
