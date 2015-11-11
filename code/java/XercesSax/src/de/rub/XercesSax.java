package de.rub;


import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import org.apache.xerces.parsers.*;

import java.util.*;



/**
 * OLD
 * @author dev
 *
 */

// code from http://www.informit.com/articles/article.aspx?p=31349&seqNum=6
public class XercesSax implements ContentHandler
{
    String currentContent;
    List<String> result;
    List<String> resultAttributes;
    
    public XercesSax()  {
	this.currentContent = "";
	this.result  = new java.util.LinkedList<String>();
	this.resultAttributes  = new java.util.LinkedList<String>();
		
    }      
  
  
  public void startElement (String namespace, String localName, 
          String qualifiedName, Attributes attribs)
  {            	
      	this.result.add(localName);
      	this.resultAttributes.add(attribs.getLocalName(0));
      	this.resultAttributes.add(attribs.getValue(0));      	
  }

  
  
  public void characters (char[] ch, int start, int length)
  {   
    String tmp = String.valueOf(ch);
    tmp = tmp.substring(start, start+length);       
    
    this.result.add(tmp);
  }
  
  
  
  public void endElement (String namespaceURI, String localName,
                          String qualifiedName)
  {
      this.currentContent = "</" + localName+ ">";      
      this.result.add(localName);      
      
  }
  
  
//  currently not required
  public void endPrefixMapping (String prefix)
  {
      
  }
  
//currently not required
  public void ignorableWhitespace (char[] ch, int start, 
                                   int length)
  {
    System.out.print("Ignorable Whitespace: ");
    for (int i = 0; i < length; i++)
      System.out.print(ch[start + i]);
    System.out.print("\n");
  }
  
//currently not required
  public void processingInstruction (String target, String data)
  {  
  }
//currently not required
  public void setDocumentLocator (Locator l)
  {  
  }
//currently not required
  public void skippedEntity (String name)
  {
    System.out.println("Skipped entity:  Name = " + name);
  }
//currently not required
  public void startDocument ()
  {  
  }
//currently not required
  public void endDocument ()
  {    
  }
//currently not required
  public void startPrefixMapping (String prefix, String uri)
  {
    System.out.println("Start Prefix Mapping:  Prefix = " +
        prefix + ", URI = " + uri);
  }
  
  
  
  
  
  
  public List<String> getResult(){
      return this.result;
  }
  
  public List<String> getResultAttributes(){
      return this.resultAttributes;
  }
  
  public String getElementContent(String tagName) {
      
      // suche erstes Element mit tagName
      int startIndex = this.result.indexOf(tagName);
      String content = "";
      
      if (startIndex > -1) {
    	// für jedes nachfolgende Element
          for (int i = startIndex+1; i < this.result.size(); i++) {
    		//falls inhalt != tagName
    	  	if (this.result.get(i) != tagName) {
//    	  	  es handelt sich um inhalt
    	  	    content += this.result.get(i); 
    	  	}	  
    	  	else {
    	  		// EndTag wurde gefunden
    	  		break;
    	  	}
          }
          
          content = content.replaceAll("\\n","");
  
      }     
            	
      return content;
  }
  
  // This is a very simple implementation; 
  // This assumes that the same attribute is defined only once within the document
  // Therefore no trace is kept, to which element the attribute belongs. 
  public String getAttributeValue(String attributeName) {
	// suche erstes Element mit attributeName
      int startIndex = this.resultAttributes.indexOf(attributeName);
      String content = "";
      
      if (startIndex > -1) {
    	  content = this.resultAttributes.get(startIndex+1);          
      }          
      content = content.replaceAll("\\n","");          
            	
      return content;
  }
  
  
  
  public static void main (String[] args)
  {
	 try
	 {
		 SAXParser parser = new org.apache.xerces.parsers.SAXParser();
		 XercesSax myContentHandler = new XercesSax();
		 
		 parser.setContentHandler(myContentHandler);
		 
		 MyDeclHandler myDeclHandler = new MyDeclHandler();
	     parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
		 
		 parser.setFeature("http://xml.org/sax/features/external-general-entities",true);
		 parser.setFeature("http://xml.org/sax/features/external-parameter-entities",true);
		 parser.parse("../../xml_files/test/dos_parameterEntities.xml");
		 
		 System.out.println(myContentHandler.getResult());
		 System.out.println(myDeclHandler.getResult());
		 
		 
		 
		 
//	      XMLReader parser;
//	      parser = XMLReaderFactory.createXMLReader();
//	      XercesSax myContentHandler = new XercesSax();
//	      MyDeclHandler myDeclHandler = new MyDeclHandler();
//	      MyDTDHandler myDTDHandler = new MyDTDHandler();
//	      
//	      parser.setContentHandler(myContentHandler);
//	      parser.setProperty("http://xml.org/sax/properties/declaration-handler", myDeclHandler);
//	      parser.setDTDHandler(myDTDHandler);
//	      
//			parser.setFeature("http://xml.org/sax/features/external-general-entities",true);
//			parser.setFeature("http://xml.org/sax/features/external-parameter-entities",true);
//			
//
//			
////			parser.setFeature("http://apache.org/xml/features/validation/schema",true);
////			parser.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
////			parser.setFeature("http://apache.org/xml/features/honour-all-schemaLocations",true);
//				
////		this is not working - MalformedURLException!
//	      parser.parse("../../xml_files/test/paramEntity_sendftp.xml");
//	      
//	      
////	      System.out.println(myContentHandler.getResultAttributes());
////	      System.out.println(myDeclHandler.getEntityValue("a"));
//	      System.out.println(myDeclHandler.getEntityValue("internal"));
//	      
//	      System.out.println(myContentHandler.getResult());
	      
	    } catch	(SAXNotSupportedException e) {
			e.printStackTrace();
	    }
	 	catch (SAXNotRecognizedException e) {
			
			e.printStackTrace();
	 	}
	 	
	 	catch(SAXParseException e) {
	 		
	 		e.printStackTrace();
	 		
	 	}	
	    catch (Exception e)
	    {
	    	
	    	e.printStackTrace();
	    }
	  }




}


