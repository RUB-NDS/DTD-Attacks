package de.rub;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;

public class MyContentHandler implements ContentHandler {


    String currentContent;
    List<String> result;
    List<String> resultAttributes;
    
    public MyContentHandler() {
	
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
  
	
	
}
