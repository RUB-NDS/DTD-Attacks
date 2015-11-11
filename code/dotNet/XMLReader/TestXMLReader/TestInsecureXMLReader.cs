//using System;
//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using System.Xml;
//using System.Text.RegularExpressions;
//using System.Net;
//using System.IO;

//namespace TestXMLReader
//{
//    [TestClass]
//    public class TestInsecureXMLReader_
//    {
//        [TestMethod]
//        public void testStandard()
//        {
//            String filename = "D:/xml_files/standard.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);
////            XmlReaderSettings settings = new XmlReaderSettings();

//            //XmlReader reader = XmlReader.Create(TextReader);

//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();            
//            Assert.AreEqual("4", content);
//        }


//        [TestMethod]

//        public void testDOS()
//        {
//            String filename = "D:/xml_files/dos.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);
            
//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            int expectedCount = 1000;
//            int realCount = Regex.Matches(content, "dos").Count;
//            Assert.AreEqual(expectedCount, realCount);
//        }

//        [TestMethod]

//        public void testDOS_DtdProcessing_Prohibit()
//        {
//            String filename = "D:/xml_files/dos.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);
//            textReader.DtdProcessing = DtdProcessing.Prohibit;
            

//            try
//            {
//                textReader.ReadToFollowing("data");
//                String content = textReader.ReadElementContentAsString();
//            }
//            catch (System.Xml.XmlException e)
//            {

//                Assert.AreEqual("DTD ist in diesem XML-Dokument nicht zulässig.", e.Message);
//                // DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.

//                return;
//            }

//            Assert.Fail("No exception was thrown.");
//        }



//        [TestMethod]
//        public void testInternalSubset_ExternalPEReferenceInDTD()
//        {
//            String filename = "D:/xml_files/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.xml";
//            XmlTextReader textReader = new XmlTextReader(filename); 

//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();
//            Assert.AreEqual("it_works", content);

//        }



//        [TestMethod]
//        public void testInternalSubset_PEReferenceInDTD()
//        {
//            String filename = "D:/xml_files/dtd/internalParameterEntity/internalSubset_PEReferenceInDTD.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();
//            Assert.AreEqual("it_works", content);


//        }

//        [TestMethod]
//        public void testParameterEntity()
//        {
//            String filename = "D:/xml_files/parameterEntity.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();
//            Assert.AreEqual("it_works", content);
//        }

//        //TODO Webserver problem
//        [TestMethod]
//        public void testParameterEntity_doctype()
//        {
//            String filename = "D:/xml_files/parameterEntity_doctype.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);


//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();
//            Assert.AreEqual("it_works", content);

//        }

//        [TestMethod]
//        public void testURLinvocation_doctype()
//        {

//            // reset the counter
//            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
//            WebResponse response = request.GetResponse();

//            String filename = "D:/xml_files/url_invocation_doctype.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            //String content = textReader.ReadElementContentAsString();

           
//            //check the counter
//            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
//            response = request.GetResponse();
//            Stream dataStream = response.GetResponseStream();
//            StreamReader reader = new StreamReader(dataStream);
//            string responseServer = reader.ReadToEnd();
//            responseServer = responseServer.Trim();
//            Assert.AreEqual("1", responseServer);


//        }

//        [TestMethod]
//        public void testURLInvocation_noNamespaceSchemaLocation()
//        {

//            // reset the counter
//            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
//            WebResponse response = request.GetResponse();

//            String filename = "D:/xml_files/url_invocation_noNamespaceSchemaLocation.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//           // String content = textReader.ReadElementContentAsString();

//            //check the counter
//            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
//            response = request.GetResponse();
//            Stream dataStream = response.GetResponseStream();
//            StreamReader reader = new StreamReader(dataStream);
//            string responseServer = reader.ReadToEnd();
//            responseServer = responseServer.Trim();
//            Assert.AreEqual("0", responseServer);

//        }


//        [TestMethod]
//        public void testURLInvocation_parameterEntity()
//        {

//            // reset the counter
//            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
//            WebResponse response = request.GetResponse();

//            String filename = "D:/xml_files/url_invocation_parameterEntity.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            //String content = textReader.ReadElementContentAsString();

//            //check the counter
//            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
//            response = request.GetResponse();
//            Stream dataStream = response.GetResponseStream();
//            StreamReader reader = new StreamReader(dataStream);
//            string responseServer = reader.ReadToEnd();
//            responseServer = responseServer.Trim();
//            Assert.AreEqual("1", responseServer);

//        }


//        [TestMethod]
//        public void testURLInvocation_XInclude()
//        {

//            // reset the counter
//            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
//            WebResponse response = request.GetResponse();

//            String filename = "D:/xml_files/url_invocation_xinclude.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            //String content = textReader.ReadElementContentAsString();


//            //check the counter
//            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
//            response = request.GetResponse();
//            Stream dataStream = response.GetResponseStream();
//            StreamReader reader = new StreamReader(dataStream);
//            string responseServer = reader.ReadToEnd();
//            responseServer = responseServer.Trim();
//            Assert.AreEqual("0", responseServer);

//        }


//        [TestMethod]
//        public void testXInclude()
//        {
//            String filename = "D:/xml_files/xinclude.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);
//            XmlReaderSettings settings = new XmlReaderSettings();

//            XmlReader reader = XmlReader.Create(textReader, settings);

//            reader.ReadToFollowing("data");
//            reader.ReadStartElement();
//            String childElement = reader.Name;
//            Assert.AreEqual("xi:include", childElement);
//        }

//        [TestMethod]
//        public void testXXE()
//        {
//            String filename = "D:/xml_files/xxe.xml";
//            XmlTextReader textReader = new XmlTextReader(filename);

//            textReader.ReadToFollowing("data");
//            String content = textReader.ReadElementContentAsString();

//            content = content.Trim();
//            Assert.AreEqual("it_works", content);

//        }


//    }
//}
