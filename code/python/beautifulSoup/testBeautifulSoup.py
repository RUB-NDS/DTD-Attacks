import unittest
from bs4 import BeautifulSoup
import requests


class Test(unittest.TestCase):


	def setUp(self):
		pass 

	def tearDown(self):
		pass

	def testDefault_noAttack(self):
		file = "../../xml_files_windows/standard.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual("4",soup.data.string)
		
	def testDOS_core(self):
		file = "../../xml_files_windows/dos/dos_core.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)

	def testDOS_indirections(self):
		file = "../../xml_files_windows/dos/dos_indirections.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)

	def testDOS_indirections_parameterEntity(self):
		file = "../../xml_files_windows/dos/dos_indirections_parameterEntity.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)
		
			
	def testDOS_entitySize(self):
		file = "../../xml_files_windows/dos/dos_entitySize.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)
			
	def testDOS_recursion (self):	
		file = "../../xml_files_windows/dos/dos_recursion.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)

	def testXXE(self):
		file = "../../xml_files_windows/xxe/xxe.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)
		
		
	def testInternalSubset_ExternalPEReferenceInDTD(self):     
		file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)	

		
	def testInternalSubset_PEReferenceInDTD(self): 
		file = "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)
		
		
	def testParameterEntity_core(self):
		file = "../../xml_files_windows/xxep/parameterEntity_core.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)

			
	def testParameterEntity_doctype(self):
		file = "../../xml_files_windows/xxep/parameterEntity_doctype.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual(None, soup.data.string)
			
			
	def testURLInvocation_doctype(self):
		
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		
	def testURLInvocation_externalGeneralEntity(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)    
		
	def testURLInvocation_parameterEntity(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)     

		file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

	def testURLInvocation_schemaLocation(self):                      
		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		file = "../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)


						   
	def testURLInvocation_XInclude(self):                                       

		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		file = "../../xml_files_windows/ssrf/url_invocation_xinclude.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)	

	def testURLInvocation_noNamespaceSchemaLocation(self):                      
		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		file = "../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml"
		soup = BeautifulSoup(open(file), "xml")

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)	

	def testXInclude(self):
	
		file = "../../xml_files_windows/xinclude.xml"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual("include", soup.include.name)
		self.assertEqual(None, soup.include.string)
		
		self.assertEqual(None, soup.content)

		
	def testXSLT(self):
	
		file = "../../xml_files_windows/optional/xslt.xsl"
		soup = BeautifulSoup(open(file), "xml")
		self.assertEqual("stylesheet", soup.stylesheet.name)
		self.assertEqual(None, soup.stylesheet.string)
		
		self.assertEqual(None, soup.content)
	

		
		
		
		
if __name__ == "__main__":

	unittest.main()