using System;
using System.Xml;

namespace XMLDocument
{
    class XMLDocument
    {
        static void Main(string[] args)
        {
            String filename = "C:/Christopher_Spaeth/code/xml_files_windows/standard.xml";
            XmlDocument xmlDoc = new XmlDocument();
            xmlDoc.Load(filename);
            String content = xmlDoc.DocumentElement.InnerText;
            Console.Write(content);
            Console.ReadKey();


            //String filename = "C:/Christopher_Spaeth/code/xml_files_windows/test/dos_entitySize_test.xml";
            //XmlReaderSettings settings = new XmlReaderSettings();
            //XmlReader reader = XmlReader.Create(filename, settings);
            //XmlDocument xmlDoc = new XmlDocument();
            //xmlDoc.Load(reader);
            //String content = xmlDoc.DocumentElement.InnerText;
            ////Console.Write(content);
            //Console.ReadKey();



        }
    }
}


//XmlReaderSettings settings = new XmlReaderSettings();
//settings.DtdProcessing = DtdProcessing.Parse;

// as documented in https://msdn.microsoft.com/en-us/library/system.xml.xmlreadersettings.validationtype%28v=vs.110%29.aspx
// ValidationFlag ProcessSchemaLocation has to set
//settings.ValidationFlags = XmlSchemaValidationFlags.ProcessSchemaLocation;
//settings.ValidationType = ValidationType.Schema;

//XmlReader reader = XmlReader.Create(filename, settings);
//XmlDocument xmlDoc = new XmlDocument();
////xmlDoc.Load(reader);
//xmlDoc.Load(filename);

//String content = xmlDoc.DocumentElement.InnerText;

//Console.Write(content);
//Console.ReadKey();
// MemoryStream stream = new MemoryStream(Encoding.Default.GetBytes(xml));
