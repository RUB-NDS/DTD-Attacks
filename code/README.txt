Folder structure of /code:

<DIR>          dotNet 
<DIR>          java 
<DIR>          perl
<DIR>          php
<DIR>          python
<DIR>          ruby


***********************************************
PREREQUISITES
***********************************************
1. Open a command prompt and navigate to /code/xml_files_windows/
2. Start a local webserver on port 5000 by typing: java -jar webserver.jar 5000 
3. Follow up with parser specific instructions

***********************************************
.NET Framework
***********************************************
Folder structure of /code/dotNet: 

	<DIR>          XInclude.NET-1.2 :: XInclude Support for .NET (No Parser)
	<DIR>          XMLDocument
		<DIR>          TestResults
		<DIR>          TestXMLDocument
		<DIR>          XMLDocument
		<FILE>		   XMLDocument.sln				   
	<DIR>          XMLReader
		<DIR>          TestResults
		<DIR>          TestXMLReader
		<DIR>          XMLReader
		<FILE> 		   XMLReader.sln

Steps:
1. Open the *.sln file (e.g. XMLDocument.sln) with Visual Studio 2013
2. Open the Testclass (e.g. TestXMLDocument.cs) in the Solution Explorer (View -> Solution Explorer)
3. Build the Project (Build -> Build Solution)
4. Execute all tests by using Test Explorer (Test -> Windows -> Test Explorer) and click "Run All"


***********************************************
JAVA
***********************************************
Folder structure of /code/Java: 


	<DIR>          Crimson
	<DIR>          OracleDocumentBuilderFactory
	<DIR>          OracleSaxParseFactory
	<DIR>          Piccolo		
	<DIR>          XercesDocumentBuilderFactory
	<DIR>          XercesSaxParseFactory

Steps:
1. Open Eclipse and choose workspace "C:/Christopher_Spaeth/code/java"
2. Open the Testclass (e.g. TestXercesSaxParseFactory.java)
3. Execute all tests by clicking "Run" (Menu -> Run -> Run)



***********************************************
PERL
***********************************************
Folder structure of /code/perl: 

	<DIR>          xml_libxml

Steps:
1. Open a command prompt and navigate to the directory /code/perl/libxml 
2. Execute all tests by typing "perl *.t" (e.g. testXML_libxml.t)



***********************************************
PHP
***********************************************
Folder structure of /code/php:

	<DIR>          DOMDocument
	<DIR>          SimpleXML
	<DIR>          XMLReader

Steps:
1. Open a command prompt and navigate to the directory /code/php/parserName (e.g. DOMDocument)
2. Execute all tests by typing "phpunit test*.php" (e.g. testDOMDocument.php)


***********************************************
PYTHON
***********************************************
Folder structure of /code/python:

	<DIR>          etree
    <DIR>          lxml
    <DIR>          minidom
    <DIR>          pulldom
    <DIR>          xml_sax

Steps:
1. Open a command prompt and navigate to the directory /code/python/parserName (e.g. etree)
2. Execute all tests by typing "python test*.py" (e.g. testEtree.py)
	
	
***********************************************
RUBY
***********************************************
Folder structure of /code/ruby:

    <DIR>          nokogiri
    <DIR>          rexml
	
Steps:
1. Open a command prompt and navigate to the directory /code/ruby/parserName (e.g. nokogiri)
2. Execute all tests by typing "ruby test*.rb" (e.g. testNokogiri.rb )



***********************************************
Android
***********************************************
Folder structure of /code/android:

    <DIR>          Parsers
    <DIR>          SimpleWebServer
	
Steps:
Instead of the webserver on your local machine, start the SimpleWebServer App on the device.
Place the test files into the root directory of /data/data/de.rub.nds.parsers (adb push <filename>)
1. Open Android Developer Studio and select the corresponding Android Instrumentation Test (e.g. DocumentBuilderFactoryTest)
2. Open the context menu and select "Run DocumentBuilderFactoryTest"
3. Choose a device or emulator to run the tests on and confirm

