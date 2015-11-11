using System;
using System.Text;
using System.Text.RegularExpressions;
using System.Xml;
using System.Net;
using System.IO;
using System.Xml.Schema;
using GotDotNet.XInclude;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace TestDOMDocument
{
    [TestClass]
    public class UnitTest1
    {
        //                         DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.
        String _DTD_NOT_ALLOWED_ = "For security reasons DTD is prohibited in this XML document. To enable DTD processing set the DtdProcessing property on XmlReaderSettings to Parse and pass the settings into XmlReader.Create method.";

        [TestMethod]
        public void testDefault_noAttack()        {
                        
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/standard.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("4", content);
        }

        [TestMethod]
        public void testDOS_core()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml");
            String content = xmlDoc.DocumentElement.InnerText;

            int expectedCount = 25;
            int realCount = Regex.Matches(content, "dos").Count;
            Assert.AreEqual(expectedCount, realCount);
        
        
        }


        [TestMethod]
        public void testDOS_core_XmlReader()
        {
            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e) {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_ , e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");       
        
        }

        [TestMethod]
        public void testDOS_indirections()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/dos_indirections.xml");
            String content = xmlDoc.DocumentElement.InnerText;

            int expectedCount = 10000;
            int realCount = Regex.Matches(content, "dos").Count;
            Assert.AreEqual(expectedCount, realCount);
        }

        [TestMethod]
        public void testDOS_indirections_XmlReader()
        {
            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_indirections.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");

        }

        [TestMethod]
        public void testDOS_indirections_parameterEntity()
        {

            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/optional/dos_indirections_parameterEntity.xml");
                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e)
            {
                Assert.AreEqual("A parameter entity reference is not allowed in internal markup. Line 4, position 16.", e.Message);

                return;
            }

            Assert.Fail("No exception was thrown.");
        }


        [TestMethod]
        public void testDOS_entitySize()
        {

            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/dos_entitySize.xml");
                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual("The input document has exceeded a limit set by MaxCharactersFromEntities.", e.Message);
                
                return;
            }

            Assert.Fail("No exception was thrown.");   
        }


        [TestMethod]
        public void testDOS_recursion()
        {

            try
            {
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/optional/dos_recursion.xml");
                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e)
            {
                Assert.AreEqual("General entity 'a' references itself. Line 4, position 16.", e.Message);

                return;
            }

            Assert.Fail("No exception was thrown.");
        }
        




        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("it_works", content);
        }


        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD_XmlReader()
        {
          
            
            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");

        }

        [TestMethod]
        public void testInternalSubset_PEReferenceInDTD()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("it_works", content);
        }

        [TestMethod]
        public void testInternalSubset_PEReferenceInDTD_XmlReader()
        {

            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
            
        }


        [TestMethod]
        public void testParameterEntity_core()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_core.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("it_works", content);
        }

        [TestMethod]
        public void testParameterEntity_core_XmlReader()
        {            

            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_core.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

                
        [TestMethod]
        public void testParameterEntity_doctype()
        {
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_doctype.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("it_works", content);
        }


        [TestMethod]
        public void testParameterEntity_doctype_XmlReader()
        {
            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_doctype.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");


        }



        [TestMethod]
        public void testURLInvocation_doctype()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();
            
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml");
            
            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("1", responseServer);
        }

        [TestMethod]
        public void testURLInvocation_doctype_XmlReader()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
               // Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }
            finally
            {
                //check the counter
                request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
                response = request.GetResponse();
                Stream dataStream = response.GetResponseStream();
                StreamReader streamReader = new StreamReader(dataStream);
                string responseServer = streamReader.ReadToEnd();
                responseServer = responseServer.Trim();

                Assert.AreEqual("0", responseServer);
            }

            Assert.Fail("No exception was thrown.");

        }


        [TestMethod]
        public void testURLInvocaton_externalGeneralEntity()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml");

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("1", responseServer);
        }

        [TestMethod]
        public void testURLInvocaton_externalGeneralEntity_XmlReader()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }
            finally
            {
                //check the counter
                request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
                response = request.GetResponse();
                Stream dataStream = response.GetResponseStream();
                StreamReader streamReader = new StreamReader(dataStream);
                string responseServer = streamReader.ReadToEnd();
                responseServer = responseServer.Trim();

                Assert.AreEqual("0", responseServer);
            }

            Assert.Fail("No exception was thrown.");           
        }



        [TestMethod]
        public void testURLInvocation_noNamespaceSchemaLocation()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("0", responseServer);
        }

        //[TestMethod]
        //public void testURLInvocation_noNamespaceSchemaLocation_XmlReader_ValidationType_Schema()
        //{

        //    // reset the counter
        //    WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
        //    WebResponse response = request.GetResponse();

        //    //for detailed tests please refer to XmlReader Project; 
        //    // We are only testing the settings which ultimately worked for XmlReader
        //    String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml";
        //    XmlReaderSettings settings = new XmlReaderSettings();
        //    settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
        //    settings.ValidationType = ValidationType.Schema;
        //    XmlReader reader = XmlReader.Create(filename, settings);
        //    XmlDocument xmlDoc = new XmlDocument();
        //    xmlDoc.Load(reader);

        //    //check the counter
        //    request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
        //    response = request.GetResponse();
        //    Stream dataStream = response.GetResponseStream();
        //    StreamReader streamReader = new StreamReader(dataStream);
        //    string responseServer = streamReader.ReadToEnd();
        //    responseServer = responseServer.Trim();

        //    Assert.AreEqual("1", responseServer);
        //}



        [TestMethod]
        public void testURLInvocation_parameterEntity()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml");

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("1", responseServer);
        }

        [TestMethod]
        public void testURLInvocation_parameterEntity_XmlReader()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();


            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml";
                XmlReaderSettings settings = new XmlReaderSettings();                
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);
            }
            catch (System.Xml.XmlException e) {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }
            finally {
                    //check the counter
                    request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
                    response = request.GetResponse();
                    Stream dataStream = response.GetResponseStream();
                    StreamReader streamReader = new StreamReader(dataStream);
                    string responseServer = streamReader.ReadToEnd();
                    responseServer = responseServer.Trim();

                    Assert.AreEqual("0", responseServer);
            }       
      }


        [TestMethod]
        public void testURLInvocation_schemaLocation()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_schemaLocation.xml");

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("0", responseServer);
        }


        //[TestMethod]
        //public void testURLInvocation_schemaLocation_XmlReader_ValidationType_Schema()
        //{

        //    // reset the counter
        //    WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
        //    WebResponse response = request.GetResponse();

        //    //for detailed tests please refer to XmlReader Project; 
        //    // We are only testing the settings which ultimately worked for XmlReader
        //    String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_schemaLocation.xml";
        //    XmlReaderSettings settings = new XmlReaderSettings();
        //    settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
        //    settings.ValidationType = ValidationType.Schema;
        //    XmlReader reader = XmlReader.Create(filename, settings);
        //    XmlDocument xmlDoc = new XmlDocument();
        //    xmlDoc.Load(reader);

        //    //check the counter
        //    request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
        //    response = request.GetResponse();
        //    Stream dataStream = response.GetResponseStream();
        //    StreamReader streamReader = new StreamReader(dataStream);
        //    string responseServer = streamReader.ReadToEnd();
        //    responseServer = responseServer.Trim();

        //    Assert.AreEqual("1", responseServer);
        //}


        [TestMethod]
        public void testURLInvocation_XInclude()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_xinclude.xml");

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("0", responseServer);
        }


        [TestMethod]
        public void testURLInvocation_XInclude_XIncludingReader()
        {

            // reset the counter
            WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
            WebResponse response = request.GetResponse();

                       

            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_xinclude.xml";
            XmlReaderSettings settings = new XmlReaderSettings();            
            XmlReader reader = XmlReader.Create(filename, settings);
            //XmlDocument xmlDoc = new XmlDocument();
            //xmlDoc.Load(reader);


            //XmlReader r = XmlReader.Create(filename);
            XIncludingReader xir = new XIncludingReader(reader);
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(xir);
            String content = xmlDoc.DocumentElement.InnerText;

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            Stream dataStream = response.GetResponseStream();
            StreamReader streamReader = new StreamReader(dataStream);
            string responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();

            Assert.AreEqual("1", responseServer);
        }


        [TestMethod]
        public void testXInclude()
        {

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml");
            //String content = xmlDoc.DocumentElement.InnerText;
            String childElement = xmlDoc.DocumentElement.FirstChild.Name;
            Assert.AreEqual("xi:include", childElement);
        }



        [TestMethod]
        public void testXInclude_XIncludingReader()
        {           

            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            XmlReader reader = XmlReader.Create(filename, settings);
            
            XIncludingReader xir = new XIncludingReader(reader);
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(xir);
            String content = xmlDoc.DocumentElement.InnerText;


            //String content = xmlDoc.DocumentElement.InnerText;
            String childElement = xmlDoc.DocumentElement.FirstChild.Name;
            Assert.AreEqual("content", childElement);
            String childContent = xmlDoc.DocumentElement.FirstChild.InnerText;
            Assert.AreEqual("it_works", childContent);


        }



        [TestMethod]
        public void testXXE()
        {

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml");
            String content = xmlDoc.DocumentElement.InnerText;
            Assert.AreEqual("it_works", content);
        }


        [TestMethod]
        public void testXXE_XmlReader()
        {

            try
            {
                String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml";
                XmlReaderSettings settings = new XmlReaderSettings();
                XmlReader reader = XmlReader.Create(filename, settings);
                XmlDocument xmlDoc = new XmlDocument();
                xmlDoc.Load(reader);

                String content = xmlDoc.DocumentElement.InnerText;
            }
            catch (System.Xml.XmlException e)
            {
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }


        [TestMethod]
        public void testXSLT()
        {

            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load("C:/Christopher_Spaeth/code/xml_files_windows/optional/xslt.xsl");
            String content = xmlDoc.DocumentElement.Name;
            Assert.AreEqual("xsl:stylesheet", content);
        }



    }


}