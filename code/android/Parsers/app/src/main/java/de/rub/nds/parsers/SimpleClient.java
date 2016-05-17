package de.rub.nds.parsers;


/**
 * Code from: 
 * http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SimpleClient {

    private final String USER_AGENT = "Mozilla/5.0";


    public SimpleClient() {

    }

    // HTTP GET request
    public String sendGet(String url) throws Exception {

//		String url = "http://localhost:9000/getCounter";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'GET' request to URL : " + url);
//		System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
//		System.out.println(response.toString());
        return response.toString();

    }

//	// HTTP POST request
//	private void sendPost() throws Exception {
// 
//		String url = "https://selfsolve.apple.com/wcResults.do";
//		URL obj = new URL(url);
//		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
// 
//		//add reuqest header
//		con.setRequestMethod("POST");
//		con.setRequestProperty("User-Agent", USER_AGENT);
//		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
// 
//		String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
// 
//		// Send post request
//		con.setDoOutput(true);
//		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//		wr.writeBytes(urlParameters);
//		wr.flush();
//		wr.close();
// 
//		int responseCode = con.getResponseCode();
//		System.out.println("\nSending 'POST' request to URL : " + url);
//		System.out.println("Post parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
// 
//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
// 
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}
//		in.close();
// 
//		//print result
//		System.out.println(response.toString());
// 
//	}	


//	public static void main(String[] args) throws Exception {
//	    
//	    SimpleClient http = new SimpleClient();   
// 
////		System.out.println("\nTesting 2 - Send Http POST request");
////		http.sendPost();
// 
//	}

}
