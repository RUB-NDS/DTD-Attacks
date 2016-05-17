package de.rub.nds.simplewebserver;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        int counter = 0;
        FileReader fr;
        BufferedReader br;

        try {
            // Get the port to listen on
//            int port = Integer.parseInt(args[0]);
            int port = 5000;
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
                System.out.println(counter);



                // Close socket, breaking the connection to the client, and
                // closing the input and output streams
                out.close(); // Flush and close the output stream
                in.close(); // Close the input stream
                client.close(); // Close the socket itself
            } // Now loop again, waiting for the next connection
        }
        // If anything goes wrong, print an error message
        catch (Exception e) {
//            System.err.println(e);
            e.printStackTrace();
            Log.d("SimpleWebServer", "fehler:" + e.getMessage());
//            System.err.println("Usage: java HttpMirror <port>");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
