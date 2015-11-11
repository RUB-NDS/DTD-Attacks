package de.rub;

import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public class MyDeclHandler implements DeclHandler {
	
	List<String> result;
	List<String> resultAttributes;
	
	public MyDeclHandler() {
		this.result  = new java.util.LinkedList<String>();
		this.resultAttributes  = new java.util.LinkedList<String>();
	}
	
	
	
//  DECLHandler
  @Override
  public void attributeDecl(String eName, String aName, String type, String mode,
  		String value) throws SAXException {
	  
	  	this.resultAttributes.add(aName);
    	this.resultAttributes.add(value);    
//    	System.out.println("Attvalue:" +eName + aName + type + mode + value);
  	
  }


  @Override
  public void elementDecl(String name, String model) throws SAXException {

  	
  }


  @Override
  public void externalEntityDecl(String name, String publicId, String systemId)
  		throws SAXException {
	  	this.result.add(name);
	  	this.result.add(systemId);
  	
  }


  @Override
  public void internalEntityDecl(String name, String value) throws SAXException {
	  this.result.add(name);
	  this.result.add(value);
//	  System.out.println("name " + name + " value " +value);
//	  throw new SAXException("Entites not allowed");
		  
  }
  
  
  public List<String> getResult(){
      return this.result;
  }
  
  public String getEntityValue(String EntityName) {
	// suche erstes Element mit tagName
      int startIndex = this.result.indexOf(EntityName);
      String content = "";
      
      if (startIndex > -1) {
    	// für jedes nachfolgende Element
//          for (int i = startIndex+1; i < this.result.size(); i++) {
//    		//falls inhalt != tagName
//    	  	if (this.result.get(i) != tagName) {
////    	  	  es handelt sich um inhalt
//    	  	    content += this.result.get(i); 
    	  content += this.result.get(startIndex+1);
    	  	}	  
//    	  	else {
//    	  		// nicht enthalten
////    	  		break;
//    	  	}
//          }
          
          content = content.replaceAll("\\n","");
  
//      }     
            	
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
  
  
  
	
}
