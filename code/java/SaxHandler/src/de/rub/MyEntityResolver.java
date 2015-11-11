package de.rub;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;

public class MyEntityResolver implements EntityResolver {

	@Override
	public InputSource resolveEntity(String arg0, String arg1)
			throws SAXException, IOException {
		
		throw(new SAXNotSupportedException("External Entities not allowed"));
		
	}

}
