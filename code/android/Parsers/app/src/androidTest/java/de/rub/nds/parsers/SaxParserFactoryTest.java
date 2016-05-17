package de.rub.nds.parsers;

import android.util.Log;

import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class SaxParserFactoryTest {


    SAXParserFactory factory;
    SAXParser parser;


    String _URL_ = "http://127.0.0.1:5000";



    final String _DECL_HANDLER_INTERNAL_ = "Entities not allowed";

    @Test
    public void testStandard () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/standard.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");
//        Log.d("SAXParser", myDefaultHandler.getElementContent("data"));
//        Log.d("SAXParser", value);
        assertEquals("4", value);

    }


    @Test
    public void testDOS_core () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/dos_core.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");
        int expectedCount = 25;
        // we know that each word is 3 chars long
        int dosCount = value.length() / 3;
        assertEquals(expectedCount, dosCount);



    }







    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     *
     * java.lang.OutOfMemoryError: Failed to allocate a 6120016 byte allocation with 4135512 free bytes and 3MB until OOM
     */
    @Test (expected = OutOfMemoryError.class)
    public void testDOS_entitySize () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/dos_entitySize.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);
//
        value = myDefaultHandler.getElementContent("data");

    }






    @Test
    public void testDOS_indirections () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/dos_indirections.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        int expectedCount = 10000;
        // we know that each word is 3 chars long
        int dosCount = value.length() / 3;
        assertEquals(expectedCount, dosCount);

    }





    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException org.apache.harmony.xml.ExpatParser$ParseException: At line 3, column 14: illegal parameter entity reference
     */
    @Test (expected = SAXParseException.class)
    public void testDOS_indirections_parameterEntity () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/dos_indirections_parameterEntity.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);

        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

    }

    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     *
     * org.apache.harmony.xml.ExpatParser$ParseException: At line 5, column 6: recursive entity reference
     */
    @Test (expected = SAXParseException.class )
    public void testDOS_recursion () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/dos_recursion.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

//        assertEquals("4", value);

    }



    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/internalSubset_ExternalPEReferenceInDTD.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }


    @Test
    public void testInternalSubset_ExternalPEReferenceInDTD_setEntityResolver () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/internalSubset_ExternalPEReferenceInDTD.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }


    @Test
    public void testInternalSubset_PEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/internalSubset_PEReferenceInDTD.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }


    @Test
    public void testParameterEntity_core () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/parameterEntity_core.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }

    @Test
    public void testParameterEntity_core_setEntityResolver () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/parameterEntity_core.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }


    @Test
    public void testParameterEntity_doctype () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/parameterEntity_doctype.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }

    @Test
    public void testParameterEntity_doctype_setEntityResolver () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "/data/data/de.rub.nds.parsers/parameterEntity_doctype.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;



        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");

        assertEquals("", value);

    }


    @Test
    public void testURLInvocation_doctype () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_doctype.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_doctype_setEntityResolver () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_doctype.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_externalGeneralEntity () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_externalGeneralEntity.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_externalGeneralEntity_setEntityResolver () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_externalGeneralEntity.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("1", response);

    }


    @Test
    public void testURLInvocation_noNamespaceSchemaLocation () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_noNamespaceSchemaLocation.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_parameterEntity () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_parameterEntity.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_parameterEntity_setEntityResolver () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_parameterEntity.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testURLInvocation_schemaLocation () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_schemaLocation.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }

    @Test
    public void testURLInvocation_XInclude () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "/data/data/de.rub.nds.parsers/url_invocation_xinclude.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;


        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);


        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    @Test
    public void testXInclude () throws Exception {


        String xmlFile = "/data/data/de.rub.nds.parsers/xinclude.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;

        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");
        assertEquals("xi:includexi:include", value);
    }


    @Test
    public void testXXE () throws Exception {


        String xmlFile = "/data/data/de.rub.nds.parsers/xxe.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;

        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");
        assertEquals("", value);
    }


    @Test
    public void testXXE_setEntityResolver() throws Exception {


        String xmlFile = "/data/data/de.rub.nds.parsers/xxe.xml";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;

        factory = SAXParserFactory.newInstance();
        MyInsecureDefaultHandler myDefaultHandler   = new MyInsecureDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        value = myDefaultHandler.getElementContent("data");
        assertEquals("it_works", value);
    }


    @Test
    public void testXSLT () throws Exception {


        String xmlFile = "/data/data/de.rub.nds.parsers/xslt.xsl";
        InputStream xmlInput = new FileInputStream(xmlFile);
        String value;

        factory = SAXParserFactory.newInstance();
        MyDefaultHandler myDefaultHandler   = new MyDefaultHandler();
        parser = factory.newSAXParser();
        parser.parse(xmlInput, myDefaultHandler);

        // ugly coding;
        // other solutions are more complex and require to keep state of document structure
        String name = myDefaultHandler.getResult().get(0);

        assertEquals("xsl:stylesheet", name);
    }




}