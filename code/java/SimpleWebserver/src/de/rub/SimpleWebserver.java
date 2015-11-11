package de.rub;
/*
 * Copyright (c) 2004 David Flanagan.  All rights reserved.
 * This code is from the book Java Examples in a Nutshell, 3nd Edition.
 * It is provided AS-IS, WITHOUT ANY WARRANTY either expressed or implied.
 * You may study, use, and modify it for any non-commercial purpose,
 * including teaching and use in open-source projects.
 * You may distribute it non-commercially as long as you retain this notice.
 * For a commercial use license, or to purchase the book, 
 * please visit http://www.davidflanagan.com/javaexamples3.
 */
//package je3.net;
// http://www.java2s.com/Code/Java/Network-Protocol/AverysimpleWebserverWhenitreceivesaHTTPrequestitsendstherequestbackasthereply.htm

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This program is a very simple Web server. When it receives a HTTP request it
 * sends the request back as the reply. This can be of interest when you want to
 * see just what a Web client is requesting, or what data is being sent when a
 * form is submitted, for example.
 */
public class SimpleWebserver {
    
  public static void main(String args[]) {
      
      int counter = 0;
      FileReader fr;
	  BufferedReader br;
      
    try {
      // Get the port to listen on
      int port = Integer.parseInt(args[0]);
//      int port = 5000;
      // Create a ServerSocket to listen on that port.
      ServerSocket ss = new ServerSocket(port);
      // Now enter an infinite loop, waiting for & handling connections.
      for (;;) {
        // Wait for a client to connect. The method will block;
        // when it returns the socket will be connected to the client
        Socket client = ss.accept();

        // Get input and output streams to talk to the client
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream());

        // Start sending our reply, using the HTTP 1.1 protocol
        out.print("HTTP/1.1 200 \r\n"); // Version & status code
        out.print("Content-Type: text/plain\r\n"); // The type of data
        out.print("Connection: close\r\n"); // Will close stream
        out.print("\r\n"); // End of headers

        // Now, read the HTTP request from the client, and send it
        // right back to the client as part of the body of our
        // response. The client doesn't disconnect, so we never get
        // an EOF. It does sends an empty line at the end of the
        // headers, though. So when we see the empty line, we stop
        // reading. This means we don't mirror the contents of POST
        // requests, for example. Note that the readLine() method
        // works with Unix, Windows, and Mac line terminators.
//        String line;
//        while ((line = in.readLine()) != null) {
//          if (line.length() == 0)
//            break;
//          
//          if (line.contains("reset")) {
//              count = 0;
//          }
//          else {
//              count +=1;
//          }
          
     // 	read first line of request (ignore the rest)
          String request = in.readLine();
          System.out.println("--------------------- REQUEST------------");
          System.out.println(request);
          System.out.println("--------------------- REQUEST------------");
          
          String req = request.substring(5, request.length()-9).trim();
          
          
                    
          
          if (req.contains("getCounter")) {
              out.print(counter  + "\r\n");
              System.out.println("Get the counter value");
          }
          else if (req.contains("reset")) {
              counter = 0;
              System.out.println("Counter has been reseted");
          }
          else {
            counter++;
            System.out.println("File " + req +" has been requested");
            try {
      		  fr = new FileReader("./" +req);
      		  br = new BufferedReader(fr);
      		  
      		  String zeile;
      		  zeile = br.readLine();
      		  while (zeile != null) {
      			  out.print(zeile);
      			  System.out.println(zeile);
      			  zeile = br.readLine();
      		  }
      		  fr.close();
      	  	}
            catch (IOException e){
              System.out.println("Error reading file - does the file exist?");
              System.out.println(e.toString());
            }
          }
          
//          else if (req.contains("parameterEntity_core.dtd")) {
//              counter++;
//              System.out.println("File parameterEntity_core.dtd has been requested");
////              out.print("<?xml version='1.0' encoding='utf-8'?><!ENTITY all '%start;%goodies;%end;'>");
//              try {
//        		  fr = new FileReader("./parameterEntity_core.dtd");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//              
//              
//          }  
//          else if (req.contains("parameterEntity_doctype.dtd")) {
//        	  counter++;
//        	  System.out.println("File parameterEntity_doctype.dtd has been requested");
////        	  out.print("<!ENTITY % start \"<![CDATA[\"> <!ENTITY % goodies SYSTEM \"file:///tmp/xxe.txt\"><!ENTITY % end \"]]>\"><!ENTITY all '%start;%goodies;%end;'>");
//        	  try {
//        		  fr = new FileReader("./parameterEntity_doctype.dtd");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//          }
//          else if (req.contains("url_invocation_parameterEntity.dtd")) {
//        	  counter++;
//        	  System.out.println("File url_invocation_parameterEntity.dtd has been requested");
//        	 // out.print("<?xml version='1.0' encoding='utf-8'?><!ENTITY intern 'it_works'>");
//        	  
//        	  try {
//        		  fr = new FileReader("./url_invocation_parameterEntity.dtd");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//        	  
//        	  
//        	  
//          }
//          else if (req.contains("url_invocation_noNamespaceSchemaLocation.xsd")) {
//        	  counter++;
//        	  System.out.println("File url_invocation_noNamespaceSchemaLocation.xsd has been requested");
//        	  try {
//        		  fr = new FileReader("./url_invocation_noNamespaceSchemaLocation.xsd");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }       
//        		  
//        
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//          }    
//          else if (req.contains("url_invocation_schemaLocation.xsd")) {
//        	  counter++;
//        	  System.out.println("File url_invocation_schemaLocation.xsd has been requested");
//        	  try {
//        		  fr = new FileReader("./url_invocation_schemaLocation.xsd");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }               		  
//        
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//          } 
//          else if (req.contains("file.xml")) {
//        	  counter++;
//        	  System.out.println("File file.xml has been requested");
//        	  //out.print("<?xml version='1.0' encoding='utf-8'?><data>it_works</data>");
//        	  try {
//        		  fr = new FileReader("./file.xml");
//        		  br = new BufferedReader(fr);
//        		  
//        		  String zeile;
//        		  zeile = br.readLine();
//        		  while (zeile != null) {
//        			  out.print(zeile);
//        			  System.out.println(zeile);
//        			  zeile = br.readLine();
//        		  }
//        		  fr.close();
//        	  }       
//        		  
//        
//              catch (IOException e){
//                  System.out.println("Fehler beim Lesen der Datei ");
//                  System.out.println(e.toString());
//              }
//        	  
//          }
          
//          else {
//              counter++;
//              System.out.println("No file has been requested");
//          }
          System.out.println(counter);

//        }

        // Close socket, breaking the connection to the client, and
        // closing the input and output streams
        out.close(); // Flush and close the output stream
        in.close(); // Close the input stream
        client.close(); // Close the socket itself
      } // Now loop again, waiting for the next connection
    }
    // If anything goes wrong, print an error message
    catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java HttpMirror <port>");
    }
  }
}
