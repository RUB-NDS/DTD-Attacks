package de.rub.nds.parsers;

import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
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

import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class DocumentBuilderFactoryTest {


    DocumentBuilderFactory factory;
    DocumentBuilder parser;


    String _URL_ = "http://127.0.0.1:5000";

    @Test
    public void testStandard () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/standard.xml";
        String name;
        String value;
        int event;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getTextContent();


        assertEquals("data", name);
        assertEquals("6", value);

    }


    @Test
    public void testDOS_core () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/dos_core.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType =  w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("a2", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }

    @Test
    public void testDOS_entitySize () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/dos_entitySize.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType =  w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("a0", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }

    @Test
    public void testDOS_indirections () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/dos_indirections.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType =  w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("a4", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }

    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws SAXParseException: This parser doesn't support parameter entities (position:DOCDECL @4:15 in java.io.InputStreamReader@ec20ff4)
     */
    @Test(expected=SAXParseException.class)
    public void testDOS_indirections_parameterEntity () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/dos_indirections_parameterEntity.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

//        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
//        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
//        assertEquals("data", name);
//        assertEquals(Node.ELEMENT_NODE, nodeType);
//
//        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
//        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//        nodeType = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();
//
//        assertEquals("a4", name);
//        assertEquals(null, value);
//        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }

    @Test
    public void testDOS_recursion () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/dos_recursion.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("a", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }


    /**
     *  org.xml.sax.SAXParseException: Parameter entity references are not supported (position:DOCDECL @4:1 in java.io.InputStreamReader@5ca9b4c)
     */
    @Test(expected=SAXParseException.class)
    public void testInternalSubset_ExternalPEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/internalSubset_ExternalPEReferenceInDTD.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);
    }
//
//    /**
//     *
//     * @throws FileNotFoundException
//     * @throws XmlPullParserException
//     * @throws org.xml.sax.SAXParseException: Parameter entity references are not supported (position:DOCDECL @4:1 in java.io.InputStreamReader@5ca9b4c)
//     */
    @Test(expected=SAXParseException.class)
    public void testInternalSubset_PEReferenceInDTD () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/internalSubset_PEReferenceInDTD.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

    }


    /**
     *
     * @throws FileNotFoundException
     * @throws XmlPullParserException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException org.xml.sax.SAXParseException: Parameter entity references are not supported (position:DOCDECL @7:1 in java.io.InputStreamReader@ba667b8)
     */
    @Test(expected=SAXParseException.class)
    public void testParameterEntity_core () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/parameterEntity_core.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

//        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
//        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
//        assertEquals("data", name);
//        assertEquals(Node.ELEMENT_NODE, nodeType);

//        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
//        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
//        nodeType =  w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();
//
//        assertEquals("all", name);
//        assertEquals(null, value);
//        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }


    @Test
    public void testParameterEntity_doctype () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/parameterEntity_doctype.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType =  w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("all", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);

    }


    @Test
    public void testURLInvocation_doctype () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_doctype.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

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


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_externalGeneralEntity.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }

    @Test
    public void testURLInvocation_noNamespaceSchemaLocation () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_noNamespaceSchemaLocation.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }


    /**
     *
     * @throws Exception org.xml.sax.SAXParseException: Parameter entity references are not supported (position:DOCDECL @5:1 in java.io.InputStreamReader@9c46991
     */
    @Test(expected=SAXParseException.class)
    public void testURLInvocation_parameterEntity () throws Exception {

        SimpleClient http = new SimpleClient();

        //reset the counter
        http.sendGet(_URL_ + "/reset");
        String response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_parameterEntity.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

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


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_schemaLocation.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

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


        String xmlFile = "file:///data/data/de.rub.nds.parsers/url_invocation_xinclude.xml";
        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);

        response = http.sendGet(_URL_ + "/getCounter");
        assertEquals("0", response);

    }




    @Test
    public void testXInclude () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/xinclude.xml";

        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);


        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("xi:include", name);
        assertEquals(null, value);
        assertEquals(Node.ELEMENT_NODE, nodeType);


    }


    @Test
    public void testXXE () throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/xxe.xml";

        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);


        name = w3cdocument.getElementsByTagName("data").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getNodeType();
        assertEquals("data", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);

        name = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeName();
        value = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeValue();
        nodeType = w3cdocument.getElementsByTagName("data").item(0).getFirstChild().getNodeType();

        assertEquals("file", name);
        assertEquals(null, value);
        assertEquals(Node.ENTITY_REFERENCE_NODE, nodeType);


    }


    @Test
    public void testXSLT() throws FileNotFoundException, XmlPullParserException, IOException, ParserConfigurationException, SAXException {

        String xmlFile = "file:///data/data/de.rub.nds.parsers/xslt.xsl";

        String name;
        String value;
        int nodeType;


        factory = DocumentBuilderFactory.newInstance();
        parser = factory.newDocumentBuilder();

        org.w3c.dom.Document w3cdocument = parser.parse(xmlFile);


        name = w3cdocument.getElementsByTagName("xsl:stylesheet").item(0).getNodeName();
        nodeType = w3cdocument.getElementsByTagName("xsl:stylesheet").item(0).getNodeType();
        assertEquals("xsl:stylesheet", name);
        assertEquals(Node.ELEMENT_NODE, nodeType);
    }



}