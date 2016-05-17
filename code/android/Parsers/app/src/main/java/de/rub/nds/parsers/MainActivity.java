package de.rub.nds.parsers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import junit.framework.Assert;

import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static junit.framework.Assert.assertEquals;

public class MainActivity extends AppCompatActivity {

    String _URL_ = "http://127.0.0.1:5000";
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


//        // sample for XmlPullParserFactory
            try {

                XmlPullParserFactory factory;
                XmlPullParser parser;

                String xmlFile = "data/data/de.rub.nds.secret/signature.xml";

                factory = XmlPullParserFactory.newInstance();

                factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
//            factory.setFeature(XmlPullParser.FEATURE_VALIDATION, true);
                parser = factory.newPullParser();

                InputStream xmlInput = new FileInputStream(xmlFile);
                parser.setInput(xmlInput, "UTF-8");

                int token = parser.nextToken();
                int i = 0;

                String name = "";
                String value = "";
                int event = 0;

                while (token != XmlPullParser.END_DOCUMENT) {
                    name = parser.getName();
                    value = parser.getText();
                    event = parser.getEventType();
                    Log.d("XMLPULL", Integer.toString(i));
                    Log.d("XMLPULL", "Name " + parser.getName());
                    Log.d("XMLPULL", "Text " + parser.getText());
                    Log.d("XMLPULL", "Event " + parser.getEventType());
                    Log.d("XMLPULL", "----------------------");
                    token = parser.nextToken();
                    i++;


                    SimpleClient http = new SimpleClient();

                    //reset the counter
//                    http.sendGet(_URL_ + "/reset");
                    http.sendGet(_URL_+ "/" + name + " " + value +  " "+event );

                }
            }catch (Exception e) {}

//
//
//        // sample for DocumentBuilderFactory
////        try {
////            String xmlFile = "file:///data/data/nds.rub.de.helloworld/standard.xml";
////            String name;
////            String value;
////            int event;
////
////
////            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
////            DocumentBuilder parser = factory.newDocumentBuilder();
////
////            org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);
////
////            name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
////            value = w3cdocument.getElementsByTagName("data").item(0).getTextContent();
////            event = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
////
////            Log.d("DocumentBuilder", name);
////            Log.d("DocumentBuilder", value);
////            Log.d("DocumentBuilder", Integer.toString(event));
////
////        } catch (ParserConfigurationException e) {
////            e.printStackTrace();
////        } catch (SAXException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//
//
//        //sample for SAX Parser
//
//        try {
////            String provider = "org.apache.xerces.jaxp.SAXParserFactoryImpl";
//            InputStream xmlInput = new FileInputStream("/data/data/de.rub.nds.parsers/xxe.xml");
//            SAXParserFactory factory = SAXParserFactory.newInstance();
//
//            MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
////            factory.setFeature("http://apache.org/xml/features/xinclude",true);
//            SAXParser saxParser = factory.newSAXParser();
//
//            saxParser.parse(xmlInput, myDefaultHandler);
//            Log.d("SAXParser", myDefaultHandler.getElementContent("data"));
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
