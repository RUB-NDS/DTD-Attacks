using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Xml;
using System.Xml.Schema;
using System.Text.RegularExpressions;
using System.Net;
using System.IO;
using GotDotNet.XInclude;



namespace TestXMLReader
{
    

    [TestClass]
    public class TestXMLReader
    {
        //                         DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.
        String _DTD_NOT_ALLOWED_ = "For security reasons DTD is prohibited in this XML document. To enable DTD processing set the DtdProcessing property on XmlReaderSettings to Parse and pass the settings into XmlReader.Create method.";

        //"Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten."
        String _LIMIT_MAXCHARS_EXCEEDED_ = "The input document has exceeded a limit set by MaxCharactersFromEntities.";

        [TestMethod]        
        public void testDefault_noAttack()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";
            //XmlTextReader textReader = new XmlTextReader(filename);            
            XmlReaderSettings settings = new XmlReaderSettings();            
            
            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();
            
            content = content.Trim();
            Assert.AreEqual("4", content);            
        }


        [TestMethod]

        public void testDOS_core()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml";            
            XmlReaderSettings settings = new XmlReaderSettings();            
            XmlReader reader = XmlReader.Create(filename, settings);
            

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }


        [TestMethod]

        public void testDOS_core_DtdProcessing_Parse()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml";           
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;

            
            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();

            int expectedCount = 25;
            int realCount = Regex.Matches(content, "dos").Count;
            Assert.AreEqual(expectedCount, realCount);
        }

        [TestMethod]

        public void testDOS_core_DtdProcessing_Parse_MaxCharactersFromEntities()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_core.xml";            
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.MaxCharactersFromEntities = 1;
            
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (XmlException e)
            {
                // assert
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual(_LIMIT_MAXCHARS_EXCEEDED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

        [TestMethod]

        public void testDOS_indirections()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_indirections.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
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

        public void testDOS_indirections_DtdProcessing_Parse()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_indirections.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;


            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();

            int expectedCount = 10000;
            int realCount = Regex.Matches(content, "dos").Count;
            Assert.AreEqual(expectedCount, realCount);
        }

        [TestMethod]

        public void testDOS_indirections_DtdProcessing_Parse_MaxCharactersFromEntities()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_indirections.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.MaxCharactersFromEntities = 1;

            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (XmlException e)
            {
                
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual(_LIMIT_MAXCHARS_EXCEEDED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }


        [TestMethod]
        public void testDOS_indirections_parameterEntity()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/optional/dos_indirections_parameterEntity.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
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

        public void testDOS_entitySize()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_entitySize.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

        [TestMethod]

        public void testDOS_entitySize_DtdProcessing_Parse()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_entitySize.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;


            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();

            int expectedCount = 3400000;
            int realCount = Regex.Matches(content, "dos").Count;
            Assert.AreEqual(expectedCount, realCount);
        }

        [TestMethod]
        public void testDOS_recursion()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/optional/dos_recursion.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
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

        public void testDOS_entitySize_DtdProcessing_Parse_MaxCharactersFromEntities()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/dos_entitySize.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            // activate DTD Processing
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.MaxCharactersFromEntities = 1;

            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (XmlException e)
            {
                // assert
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual(_LIMIT_MAXCHARS_EXCEEDED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

        

        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml";
           // XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD_DtdProcessing_Parse()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml";
            // XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;            

            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();          

            content = content.Trim();
            Assert.AreEqual("it_works", content);        

        }

        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD_DtdProcessing_Parse_MaxCharactersFromEntities()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml";
            // XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.MaxCharactersFromEntities = 1;
            
            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (XmlException e)
            {
                // assert
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual("An error has occurred while opening external entity 'file:///C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.dtd': The input document has exceeded a limit set by MaxCharactersFromEntities.", e.Message);
                return;
            }



            //reader.ReadToFollowing("data");
            //String content = reader.ReadElementContentAsString();

            //content = content.Trim();
            //Assert.AreEqual("it_works", content);

        }



        [TestMethod]
        public void testInternalSubset_ExternalPEReferenceInDTD_DtdProcessing_Parse_XmlResolver()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml";
            // XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.XmlResolver = null;

            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);
            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("Verweis auf die nicht deklarierte Entität 'intern'. Zeile 6, Position 8.", e.Message);
                Assert.AreEqual("Reference to undeclared entity 'intern'. Line 7, position 8.", e.Message);
                
                return;
            }

            Assert.Fail("No exception was thrown.");
            

            //content = content.Trim();
            //Assert.AreEqual("it_works", content);

            // 

        }



        [TestMethod]
        public void testInternalSubset_PEReferenceInDTD()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml";
            //XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);
            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }

        [TestMethod]
        public void testInternalSubset_PEReferenceInDTD_DtdProcessing_Parse()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml";
            //XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;
           
            XmlReader reader = XmlReader.Create(filename, settings);

            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();

            content = content.Trim();
            Assert.AreEqual("it_works", content);
        }


        [TestMethod]
        public void testInternalSubset_PEReferenceInDTD_DtdProcessing_Parse_MaxCharactersFromEntities()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml";
            //XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;
            settings.MaxCharactersFromEntities = 1;

            XmlReader reader = XmlReader.Create(filename, settings);

            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (XmlException e)
            {
                // assert
                //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                Assert.AreEqual(_LIMIT_MAXCHARS_EXCEEDED_, e.Message);
                return;
            }



            //reader.ReadToFollowing("data");
            //String content = reader.ReadElementContentAsString();

            //content = content.Trim();
            //Assert.AreEqual("it_works", content);

        }




        // obsolet
        //[TestMethod]
        //public void testInternalSubset_PEReferenceInDTD_DtdProcessing_Parse_XmlResolver()
        //{
        //    String filename = "C:/Christopher_Spaeth/code/xml_files_windows/internalSubset_PEReferenceInDTD.xml";            
        //    XmlReaderSettings settings = new XmlReaderSettings();
        //    settings.DtdProcessing = DtdProcessing.Parse;
        //    settings.XmlResolver = null;

        //    XmlReader reader = XmlReader.Create(filename, settings);

        //    reader.ReadToFollowing("data");
        //    String content = reader.ReadElementContentAsString();

        //    content = content.Trim();
        //    Assert.AreEqual("it_works", content);
        //}


         [TestMethod]
        public void testParameterEntity_core()
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_core.xml";
            //XmlTextReader textReader = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            //XmlReader reader = XmlReader.Create(textReader, settings);
            XmlReader reader = XmlReader.Create(filename, settings);
            try
            {
                reader.ReadToFollowing("data");
                String content = reader.ReadElementContentAsString();
            }
            catch (System.Xml.XmlException e)
            {
                // assert
                //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                return;
            }

            Assert.Fail("No exception was thrown.");
        }


         [TestMethod]
         public void testParameterEntity_core_DtdProcessing_Parse()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_core.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();                          
             settings.DtdProcessing = DtdProcessing.Parse;
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             content = content.Trim();
             Assert.AreEqual("it_works", content);

         }


         [TestMethod]
         public void testParameterEntity_core_DtdProcessing_Parse_XmlResolver()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_core.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;
             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (System.Xml.XmlException e)
             {
                 // assert
                 //Assert.AreEqual("Verweis auf die nicht deklarierte Entität 'all'. Zeile 9, Position 8.", e.Message);
                 Assert.AreEqual("Reference to undeclared entity 'all'. Line 10, position 8.", e.Message);                 
                 return;
             }

             Assert.Fail("No exception was thrown.");
         }

         [TestMethod]
         public void testParameterEntity_doctype()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_doctype.xml";
            // XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();
             //XmlReader reader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (System.Xml.XmlException e)
             {
                 // assert
                 //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                 Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                 return;
             }

             Assert.Fail("No exception was thrown.");
         }


         [TestMethod]
         public void testParameterEntity_doctype_DtdProcessing_Parse()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_doctype.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             XmlReader reader = XmlReader.Create(filename, settings);

           
             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             content = content.Trim();
             Assert.AreEqual("it_works", content);
            
         }


         [TestMethod]
         public void testParameterEntity_doctype_DtdProcessing_Parse_XmlResolver()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/parameterEntity_doctype.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;
             XmlReader reader = XmlReader.Create(filename, settings);


             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (System.Xml.XmlException e)
             {
                 // assert
                 //Assert.AreEqual("Verweis auf die nicht deklarierte Entität 'all'. Zeile 3, Position 8.", e.Message);
                 Assert.AreEqual("Reference to undeclared entity 'all'. Line 3, position 8.", e.Message);
                 
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
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml";
             XmlReaderSettings settings = new XmlReaderSettings();             
             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (System.Xml.XmlException e)
             {
                 // assert
                 //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                 Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                 return;
             }
             finally
             {
                 //check the counter
                 request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
                 response = request.GetResponse();
                 dataStream = response.GetResponseStream();
                 streamReader = new StreamReader(dataStream);
                 responseServer = streamReader.ReadToEnd();
                 responseServer = responseServer.Trim();
                 Assert.AreEqual("0", responseServer);
             }

             Assert.Fail("No exception was thrown.");
         }

         [TestMethod]
         public void testURLInvocation_doctype_DtdProcessing_Parse()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();

            //check the counter
            request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
            response = request.GetResponse();
            dataStream = response.GetResponseStream();
            streamReader = new StreamReader(dataStream);
            responseServer = streamReader.ReadToEnd();
            responseServer = responseServer.Trim();
            Assert.AreEqual("1", responseServer);
             
         }


         [TestMethod]
         public void testURLInvocation_doctype_DtdProcessing_Parse_XmlResolver()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;
             
             XmlReader reader = XmlReader.Create(filename, settings);

             
             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);
             
         }

         [TestMethod]
         public void testURLInvocation_externalGeneralEntity()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
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
                 dataStream = response.GetResponseStream();
                 streamReader = new StreamReader(dataStream);
                 responseServer = streamReader.ReadToEnd();
                 responseServer = responseServer.Trim();
                 Assert.AreEqual("0", responseServer);
             }

             Assert.Fail("No exception was thrown.");
         }


         [TestMethod]
         public void testURLInvocation_externalGeneralEntity_DtdProcessing_Parse()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             // simple workaround: readInnerXml() is required, because element has child elements; 
             String content = reader.ReadInnerXml();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("1", responseServer);
         }


         [TestMethod]
         public void testURLinvocation_externalGeneralEntity_DtdProcessing_Parse_XmlResolver()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;

             XmlReader reader = XmlReader.Create(filename, settings);


             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

         }

         [TestMethod]
         public void testURLInvocation_noNamespaceSchemaLocation()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml";
             //XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();
             //XmlReader xmlReader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);
         }

        


         [TestMethod]
         public void testURLInvocation_noNamespaceSchemaLocation_ValidationType_Schema()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();
             // as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
             // ValidationFlag ProcessSchemaLocation has to set
             settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
             settings.ValidationType = ValidationType.Schema;
             
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("1", responseServer);
         }

         [TestMethod]
         public void testURLInvocation_noNamespaceSchemaLocation_ValidationType_Schema_XmlResolver()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);


             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             // as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
             // ValidationFlag ProcessSchemaLocation has to set
             settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
             settings.ValidationType = ValidationType.Schema;
             settings.XmlResolver = null;

             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);
         }



         [TestMethod]
         public void testURLInvocation_parameterEntity()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml";
             //XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();
             //XmlReader xmlReader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);
             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
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
                 dataStream = response.GetResponseStream();
                 streamReader = new StreamReader(dataStream);
                 responseServer = streamReader.ReadToEnd();
                 responseServer = responseServer.Trim();
                 Assert.AreEqual("0", responseServer);
             }

             Assert.Fail("No exception was thrown.");
         }

         [TestMethod]
         public void testURLInvocation_parameterEntity_DtdProcessing_Parse()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             
             XmlReader reader = XmlReader.Create(filename, settings);
             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("1", responseServer);             

             
         }

         [TestMethod]
         public void testURLInvocation_parameterEntity_DtdProcessing_Parse_XmlResolver()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;

             XmlReader reader = XmlReader.Create(filename, settings);
             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);


         }

         [TestMethod]
         public void testURLInvocation_schemaLocation()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_schemaLocation.xml";
             //XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();
             //XmlReader xmlReader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data", "http://test.com/attack");             
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);
         }




         [TestMethod]
         public void testURLInvocation_schemaLocation_ValidationType_Schema()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_schemaLocation.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             // as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
             // ValidationFlag ProcessSchemaLocation has to set
             settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
             settings.ValidationType = ValidationType.Schema;

             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data", "http://test.com/attack"); 
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("1", responseServer);
         }

         [TestMethod]
         public void testURLInvocation_schemaLocation_ValidationType_Schema_XmlResolver()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_schemaLocation.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             // as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
             // ValidationFlag ProcessSchemaLocation has to set
             settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
             settings.ValidationType = ValidationType.Schema;                          
             settings.XmlResolver = null;             

             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data", "http://test.com/attack");
             String content = reader.ReadElementContentAsString();


             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);
         }


         [TestMethod]
         public void testURLInvocation_XInclude()
         {

             // reset the counter
             WebRequest request = WebRequest.Create("http://127.0.0.1:5000/reset");
             WebResponse response = request.GetResponse();
             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             Stream dataStream = response.GetResponseStream();
             StreamReader streamReader = new StreamReader(dataStream);
             string responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/url_invocation_xinclude.xml";
             //XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();
             //XmlReader xmlReader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadInnerXml();

             //check the counter
             request = WebRequest.Create("http://127.0.0.1:5000/getCounter");
             response = request.GetResponse();
             dataStream = response.GetResponseStream();
             streamReader = new StreamReader(dataStream);
             responseServer = streamReader.ReadToEnd();
             responseServer = responseServer.Trim();
             Assert.AreEqual("0", responseServer);

         }


         [TestMethod]
         public void testXInclude()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml";
  //           XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();

//             XmlReader reader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");             
             reader.ReadStartElement();
             String childElement = reader.Name;             
             Assert.AreEqual("xi:include", childElement);
         }

        

        ////TODO not working as expected!
         //[TestMethod]
         //public void testXInclude_XIncludingReader()
         //{
         //    String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml";

         //    XIncludingReader xincludingReader = new XIncludingReader(filename);
         //    //XmlTextReader r = new XmlTextReader(filename);
         //    XmlReaderSettings settings = new XmlReaderSettings();
         //    //XmlReader reader = XmlReader.Create(filename, settings);
         //    //XIncludingReader xir = new XIncludingReader(r);
         //    XmlReader reader = XmlReader.Create(xincludingReader, settings);

         //    reader.ReadToFollowing("data");
         //    reader.ReadStartElement();
         //    String content = reader.ReadInnerXml();
         //    Console.Write(content);
         //    Console.ReadKey();

         //    //String childElement = reader.Name;
         //    //Assert.AreEqual("xi:include", childElement);
             
         //    //Assert.AreEqual("", childElement);
         //}       


         [TestMethod]
         public void testXSLT()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/optional/xslt.xsl";
             //           XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();

             //             XmlReader reader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("xsl:stylesheet");
             //reader.ReadStartElement();
             String name = reader.Name;
             Assert.AreEqual("xsl:stylesheet", name);
         }


         

         [TestMethod]
         public void testXXE()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml";
            // XmlTextReader textReader = new XmlTextReader(filename);
             XmlReaderSettings settings = new XmlReaderSettings();

//             XmlReader reader = XmlReader.Create(textReader, settings);
             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (System.Xml.XmlException e)
             {
                 // assert
                 //Assert.AreEqual("DTD ist in diesem XML-Dokument aus Sicherheitsgründen unzulässig. Zum Aktivieren der DTD-Verarbeitung müssen Sie die 'DtdProcessing'-Eigenschaft für 'XmlReaderSettings' auf 'Parse' festlegen und die Einstellungen an die 'XmlReader.Create'-Methode übergeben.", e.Message);
                 Assert.AreEqual(_DTD_NOT_ALLOWED_, e.Message);
                 return;
             }

             Assert.Fail("No exception was thrown.");  
             
         }

         [TestMethod]
         public void testXXE_DtdProcessing_Parse()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml";             
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             content = content.Trim();
             Assert.AreEqual("it_works", content);

         }


         [TestMethod]
         public void testXXE_DtdProcessing_Parse_MaxCharactersFromEntities()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;             
             settings.MaxCharactersFromEntities = 1;

             XmlReader reader = XmlReader.Create(filename, settings);

             try
             {
                 reader.ReadToFollowing("data");
                 String content = reader.ReadElementContentAsString();
             }
             catch (XmlException e)
             {
                 // assert
                 //Assert.AreEqual("Das Eingabedokument hat das Limit von MaxCharactersFromEntities überschritten.", e.Message);
                 Assert.AreEqual("An error has occurred while opening external entity 'file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt': The input document has exceeded a limit set by MaxCharactersFromEntities.", e.Message);
                 return;
             }

             Assert.Fail("No exception was thrown.");

                      

             //reader.ReadToFollowing("data");
             //String content = reader.ReadElementContentAsString();

             //content = content.Trim();
             //Assert.AreEqual("it_works", content);

         }


         [TestMethod]
         public void testXXE_DtdProcessing_Parse_XmlResolver()
         {
             String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xxe.xml";
             XmlReaderSettings settings = new XmlReaderSettings();
             settings.DtdProcessing = DtdProcessing.Parse;
             settings.XmlResolver = null;
             XmlReader reader = XmlReader.Create(filename, settings);

             reader.ReadToFollowing("data");
             String content = reader.ReadElementContentAsString();

             content = content.Trim();
             Assert.AreEqual("", content);

         }
    

    }
}
