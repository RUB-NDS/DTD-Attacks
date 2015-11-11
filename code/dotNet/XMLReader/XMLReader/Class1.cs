//using System;
//using System.Xml;


using System;
using System.Xml;
using System.Xml.Schema;
using System.Text.RegularExpressions;
using System.Net;
using System.IO;
using GotDotNet.XInclude;


namespace helloWorld
{
    class Program
    {
        public static void Main()   
        {
            //String filename = "C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";        
            //XmlReader reader = XmlReader.Create(filename);            
            //reader.ReadToFollowing("data");
            //String content = reader.ReadElementContentAsString();
            //Console.Write(content);
            //Console.ReadKey();



            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/xinclude.xml";

          
            //XmlTextReader r = new XmlTextReader(filename);
            XmlReaderSettings settings = new XmlReaderSettings();
            //XmlReader reader = XmlReader.Create(filename, settings);
            //XIncludingReader xir = new XIncludingReader(r);
            XmlReader reader = XmlReader.Create(filename, settings);

            XIncludingReader xincludingReader = new XIncludingReader(reader);

            XmlReader reader2 = XmlReader.Create(xincludingReader, settings);

            reader2.ReadToFollowing("data");

            String content = reader2.ReadInnerXml();
            Console.Write(content);
            Console.ReadKey();









            //XmlReaderSettings settings = new XmlReaderSettings();
            //settings.DtdProcessing = DtdProcessing.Parse;
            // as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
            // ValidationFlag ProcessSchemaLocation has to set
            //settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
            //settings.ValidationType = ValidationType.Schema;
            //XmlReader reader = XmlReader.Create(filename, settings);
            //reader.ReadToFollowing("data", "http://test.com/attack");

            // XInclude works conveniently with XmlDocument, but not with XMLReader
            //XmlTextReader r = new XmlTextReader(filename);
            //XIncludingReader xir = new XIncludingReader(r);
            ////XmlDocument doc = new XmlDocument();
            ////doc.Load(xir);
            ////String content = doc.DocumentElement.InnerText;

            //xir.ReadToFollowing("data");
            
            
            //Console.Write(xir.ReadInnerXml());
            //Console.ReadKey();
            


//            string markup =
//@"<!DOCTYPE Root [
//  <!ENTITY anEntity ""Expands to more than 30 characters"">
//  <!ELEMENT Root (#PCDATA)>
//]>
//<Root>Content &anEntity;</Root>";

            //XmlReaderSettings settings = new XmlReaderSettings();
            //settings.DtdProcessing = DtdProcessing.Parse;
            //settings.ValidationType = ValidationType.DTD;
            //settings.MaxCharactersFromEntities = 30;

//            try
//            {
//                XmlReader reader = XmlReader.Create(new StringReader(markup), settings);
//                while (reader.Read()) { }
//            }
//            catch (XmlException ex)
//            {
//                Console.WriteLine(ex.Message);
//                Console.ReadKey();
//            }


            //XmlTextReader textReader = new XmlTextReader(filename);
            //XmlReaderSettings settings = new XmlReaderSettings();
            //settings.ProhibitDtd = false;
            //XmlReader reader = XmlReader.Create(filename, settings);
           // XmlReader reader = XmlReader.Create(filename);

            //reader.ReadToFollowing("data");
            //String content = reader.ReadElementContentAsString();

            //Console.WriteLine(content);
            //Console.ReadKey();
            
        }
    }
}
