package de.rub.nds.parsers;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.Test;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class XmlPullParserFactoryTest  {

    XmlPullParserFactory factory;
    XmlPullParser parser;


    String _URL_ = "http://127.0.0.1:5000";


    @Test
    public void testStandard () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/standard.xml";
        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


//        parser.nextToken(); // element
        parser.nextTag();
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);

        parser.nextToken();  // the content of the element

        // make sure to override ALL values; otherwise we produce assertion errors!
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(4, event);
        assertEquals("4", value);

    }


    @Test
    public void testDOS_core () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_core.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        // Unfortunately, we have to traverse the xml document manually
        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);

        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);


        parser.nextToken();  // the content of the element
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event); // The event is a ENTITY_REF - a not resolved Entity Reference, hence no "value"
        assertEquals(null, value);
        assertEquals("a2", name);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event);

    }


    @Test
    public void testDOS_core_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_core.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();

        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);


        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals(2, event);
        assertEquals("data", name);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals("a2", name);
        assertEquals("", value);
        assertEquals(6, event);

        String content = "";
        int i = 0;

        // construct the entity replacement text
        while (token != XmlPullParser.END_TAG) {

            if (parser.getEventType() == 4 ) {
                content += parser.getText();
            }
            token =             parser.nextToken();
            i++;

        }

        int expectedCount = 25;
        // we know that each word is 3 chars long
        int dosCount = content.length() / 3;
        assertEquals(expectedCount, dosCount);

    }



    @Test
    public void testDOS_entitySize () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_entitySize.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);

        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();

        assertEquals("data", name);
        assertEquals(2, event);


        // the content of the element : ten references to entity a0
        for (int i=0; i<10; i++) {
            parser.nextToken();
            // make sure to override ALL values; otherwise we produce assertion errors!
            name = parser.getName();
            value = parser.getText();
            event = parser.getEventType();
            assertEquals(6, event);
            assertEquals(null, value);
            assertEquals("a0", name);
        }

        parser.nextToken();
        // make sure to override ALL values; otherwise we produce assertion errors!
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event);




    }

    @Test(expected=OutOfMemoryError.class)
    /**
     * java.lang.OutOfMemoryError: Failed to allocate a 10200016 byte allocation with 4194304 free bytes and 8MB until OOM
     */
    public void testDOS_entitySize_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_entitySize.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();

        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);




        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals(2, event);
        assertEquals("data", name);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals("a0", name);
        assertEquals("", value);
        assertEquals(6, event);

        String content = "";
        int i = 0;

        // construct the entity replacement text
        while (token != XmlPullParser.END_TAG) {

            if (parser.getEventType() == 4 ) {
                content += parser.getText();
            }
            token =             parser.nextToken();
            i++;

        }
//
//        int expectedCount = 25;
//        // we know that each word is 3 chars long
//        int dosCount = content.length() / 3;
//        assertEquals(expectedCount, dosCount);

    }


    @Test
    public void testDOS_indirections () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_indirections.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);


        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();

        assertEquals("data", name);
        assertEquals(2, event);

        parser.nextToken(); // the content of the element
        // make sure to override ALL values; otherwise we produce assertion errors!
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();


        assertEquals(6, event);
        assertEquals(null, value);
        assertEquals("a4", name);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event);

    }


    @Test
    public void testDOS_indirections_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_indirections.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();

        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);




        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals(2, event);
        assertEquals("data", name);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();

        assertEquals("a4", name);
        assertEquals("", value);
        assertEquals(6, event);

        String content = "";
        int i = 0;

        // construct the entity replacement text
        while (token != XmlPullParser.END_TAG) {

            if (parser.getEventType() == 4 ) {
                content += parser.getText();
            }
            token =             parser.nextToken();
            i++;

        }

        int expectedCount = 10000;
        // we know that each word is 3 chars long
        int dosCount = content.length() / 3;
        assertEquals(expectedCount, dosCount);

    }

    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws XmlPullParserException: This parser doesn't support parameter entities (position:DOCDECL @4:15 in java.io.InputStreamReader@ec20ff4)
     */
    @Test(expected=XmlPullParserException.class)
    public void testDOS_indirections_parameterEntity () throws FileNotFoundException, XmlPullParserException, IOException {


        String xmlFile = "data/data/de.rub.nds.parsers/dos_indirections_parameterEntity.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken(); // IGNORABLE_WHITESPACE - this call is necessary in order to trigger the exception.
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);
    }


    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws XmlPullParserException: This parser doesn't support parameter entities (position:DOCDECL @4:15 in java.io.InputStreamReader@ec20ff4)
     */
    @Test(expected=XmlPullParserException.class)
    public void testDOS_indirections_parameterEntity_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_indirections_parameterEntity.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();

        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken(); // IGNORABLE_WHITESPACE - this call is necessary in order to trigger the exception.
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);




    }



    @Test
    public void testDOS_recursion () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_recursion.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);



        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);


        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);


        parser.nextToken(); // the content of the element
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals(null, value);
        assertEquals("a", name);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event);



    }

    @Test
    public void testDOS_recursion_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/dos_recursion.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);



        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);


        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);



        // the parser resolves the first entity reference
        parser.nextToken(); // the content of the element
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals("", value);
        assertEquals("a", name);

        // the first part of the reference is the replacement text "a"
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(4, event);
        assertEquals("a", value);
        assertEquals(null, name);

        // the second part is a reference to the entity "b"
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals("", value);
        assertEquals("b", name);

        // resolve entity b: it's a reference to the entity "a"
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals("", value);
        assertEquals("a", name);

        // the first part of the reference is the replacement text "a"
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(4, event);
        assertEquals("a", value);
        assertEquals(null, name);

        // and so on...

        // use this code to verify
//        int token = parser.nextToken();
//        int i = 0;
//
//        Log.d("recursion", "Name " + parser.getName());
//        Log.d("recursion", "Text " + parser.getText());
//        Log.d("recursion", "Event " + parser.getEventType());
//        Log.d("recursion", "----------------------");
//
//            while (token != XmlPullParser.END_DOCUMENT) {
//                Log.d("recursion", Integer.toString(i));
//                Log.d("recursion", "Name " + parser.getName());
//                Log.d("recursion", "Text " + parser.getText());
//                Log.d("recursion", "Event " + parser.getEventType());
//                Log.d("recursion", "----------------------");
//                token =             parser.nextToken();
//                i++;
//            }
    }


    /**
     *  org.xmlpull.v1.XmlPullParserException: Parameter entity references are not supported (position:DOCDECL @4:1 in java.io.InputStreamReader@b5d7795)
     */
    @Test(expected=XmlPullParserException.class)
    public void testInternalSubset_ExternalPEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/internalSubset_ExternalPEReferenceInDTD.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);


    }

    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws IOException org.xmlpull.v1.XmlPullParserException: Parameter entity references are not supported (position:DOCDECL @4:1 in java.io.InputStreamReader@5ca9b4c)
     */
    @Test(expected=XmlPullParserException.class)
    public void testInternalSubset_PEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/internalSubset_PEReferenceInDTD.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);
    }


    /**
     *
     * @throws FileNotFoundException
     * @throws org.xmlpull.v1.XmlPullParserException: Parameter entity references are not supported (position:DOCDECL @7:1 in java.io.InputStreamReader@9a6ef29)
     * @throws IOException
     */

    @Test(expected=XmlPullParserException.class)
    public void testParameterEntity_core () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/parameterEntity_core.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);
    }

    @Test
    public void testParameterEntity_doctype () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/parameterEntity_doctype.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(2, event);
        assertEquals("data", name);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals("all", name);

        // make sure that the external subset has not been fetched
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event); // End Tag
    }


    @Test
    public void testParameterEntity_doctype_FEATURE_PROCESS_DOCDECL () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/parameterEntity_doctype.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(2, event);
        assertEquals("data", name);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals("all", name);

        // make sure that the external subset has not been fetched and the entity not been resolved due to the feature
        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event); // End Tag


    }


    @Test()
    public void testURLInvocation_doctype () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_doctype.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }





        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }


    @Test()
    public void testURLInvocation_doctype_FEATURE_PROCESS_DOCDECL () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_doctype.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        factory.setFeature(XmlPullParser.FEATURE_PROCESS_DOCDECL, true);
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }



    @Test()
    public void testURLInvocation_externalGeneralEntity () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_externalGeneralEntity.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }



    @Test()
    public void testURLInvocation_noNamespaceSchemaLocation () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_noNamespaceSchemaLocation.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }






    /**
     *
     * @throws org.xmlpull.v1.XmlPullParserException: Parameter entity references are not supported (position:DOCDECL @5:1 in java.io.InputStreamReader@9a6ef29)
     */
    @Test(expected = XmlPullParserException.class)
    public void testURLInvocation_parameterEntity () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_parameterEntity.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_+ "/getCounter");
        assertEquals("0", response);


    }

    @Test()
    public void testURLInvocation_schemaLocation () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_schemaLocation.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }



    @Test()
    public void testURLInvocation_XInclude () throws Exception {



        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "data/data/de.rub.nds.parsers/url_invocation_xinclude.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        int token = parser.nextToken();
        int i = 0;

//          parse the entire Document
        while (token != XmlPullParser.END_DOCUMENT) {
            token =             parser.nextToken();
            i++;
        }




        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


    }




    @Test
    public void testXInclude () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/xinclude.xml";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");

        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(2, event);
        assertEquals(null, value);
        assertEquals("xi:include", name);

    }





    @Test
    public void testXXE () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/xxe.xml";

        String name;
        String value;
        int event;

        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(10, event);


        parser.nextToken(); // IGNORABLE_WHITESPACE
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(7, event);


        parser.nextToken(); // element
        name = parser.getName();
        value = parser.getText(); // not used here
        event = parser.getEventType();
        assertEquals("data", name);
        assertEquals(2, event);

        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(6, event);
        assertEquals(null, value);
        assertEquals("file", name);


        parser.nextToken();
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(3, event);
        assertEquals(null, value);
        assertEquals("data", name);

    }


    @Test
    public void testXSLT () throws FileNotFoundException, XmlPullParserException, IOException {

        String xmlFile = "data/data/de.rub.nds.parsers/xslt.xsl";

        String name;
        String value;
        int event;


        factory = XmlPullParserFactory.newInstance();
        parser = factory.newPullParser();
        InputStream xmlInput = new FileInputStream(xmlFile);
        parser.setInput(xmlInput, "UTF-8");


        parser.nextToken(); // Document Type Declaration
        name = parser.getName();
        value = parser.getText();
        event = parser.getEventType();
        assertEquals(2, event);
        assertEquals("xsl:stylesheet", name);



    }


}