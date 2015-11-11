package de.rub;

import java.util.List;

import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;

public class MySecureDeclHandler implements DeclHandler {
	
	List<String> result;
	List<String> resultAttributes;
	
	public MySecureDeclHandler() {
		this.result  = new java.util.LinkedList<String>();
		this.resultAttributes  = new java.util.LinkedList<String>();
	}
	
	
	
  


  @Override
  public void elementDecl(String name, String model) throws SAXException {

  	
  }


  @Override
  public void externalEntityDecl(String name, String publicId, String systemId)	throws SAXException {
	  throw new SAXException("External Entities not allowed");
  	
  }


  @Override
  public void internalEntityDecl(String name, String value) throws SAXException {	
	  throw new SAXException("Entities not allowed");
		  
  }

  @Override
  public void attributeDecl(String arg0, String arg1, String arg2, String arg3,
		String arg4) throws SAXException {

	
  } 
	
}
