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


            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";
            XmlReaderSettings settings = new XmlReaderSettings();
            settings.DtdProcessing = DtdProcessing.Parse;
            //settings.ValidationType = ValidationType.Schema;
            //settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;

            XmlReader reader = XmlReader.Create(filename, settings);
            reader.ReadToFollowing("data");
            String content = reader.ReadElementContentAsString();
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
            

            
        }
    }
}
