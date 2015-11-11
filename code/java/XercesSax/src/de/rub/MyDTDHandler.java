package de.rub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;

public class MyDTDHandler implements DTDHandler {

	String prog;
	@Override
	public void notationDecl(String name, String publicId, String systemId)
			throws SAXException {

		System.out.println(name + " "+publicId +" "+ systemId);
		
		String s = null;

        try {
            
	    // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(systemId);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
		
		
	}

	@Override
	public void unparsedEntityDecl(String name, String publicId,
			String systemId, String notationName) throws SAXException {
		// 
		System.out.println(name + " "+publicId +" "+ systemId + " " + notationName);
			
		
		
//		try {
//			Process p = Runtime.getRuntime().exec(systemId);
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
		
		
	}
	

}
