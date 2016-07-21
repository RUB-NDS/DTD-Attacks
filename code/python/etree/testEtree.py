'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import xml.etree.ElementTree as _ET
import xml.etree.ElementInclude as _ETINCLUDE
import requests


class Test(unittest.TestCase):


	def setUp(self):
		self._URL_="http://127.0.0.1:5000" 
		
	def tearDown(self):
		pass
		
	def helperParse(self, document):
		tree = _ET.parse(document) 
		root = tree.getroot()
		return root          
		
	def testDefault_noAttack(self):
		root = self.helperParse('../../xml_files_windows/standard.xml')
		self.assertIn("4", root.text)		
	
	def testDOS_core(self):
		root = self.helperParse('../../xml_files_windows/dos/dos_core.xml')
		expectedCount = 25
		count = root.text.count("dos")
		self.assertEqual(expectedCount, count)
		
	def testDOS_indirections(self):
		root = self.helperParse('../../xml_files_windows/dos/dos_indirections.xml')
		expectedCount = 10000 
		count = root.text.count("dos")
		self.assertEqual(expectedCount, count)
	
	'''
	ParseError: illegal parameter entity reference: line 4, column 14
	'''
	def testDOS_indirections_parameterEntity(self):
		with self.assertRaises(_ET.ParseError):		
			root = self.helperParse('../../xml_files_windows/dos/dos_indirections_parameterEntity.xml')
		
	
	def testDOS_entitySize(self):
		root = self.helperParse('../../xml_files_windows/dos/dos_entitySize.xml')
		expectedCount = 3400000 
		count = root.text.count("dos")
		self.assertEqual(expectedCount, count)
		
	'''
	ParseError: recursive entity reference: line 6, column 6
	'''
	def testDOS_entitySize(self):
		with self.assertRaises(_ET.ParseError):
			root = self.helperParse('../../xml_files_windows/dos/dos_recursion.xml')		
		
	'''
	ParseError: undefined entity &file;: line 6, column 6
	'''
	def testXXE(self):
		with self.assertRaises(_ET.ParseError):
			self.helperParse('../../xml_files_windows/xxe/xxe.xml')
			
	'''
	ParseError: undefined entity &intern;: line 6, column 6
	'''
	def testInternalSubset_ExternalPEReferenceInDTD(self):                      
		with self.assertRaises(_ET.ParseError):        
			self.helperParse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml')
			
	'''
	ParseError: undefined entity &intern;: line 6, column 6
	'''
	def testInternalSubset_PEReferenceInDTD(self):                              
		with self.assertRaises(_ET.ParseError):        
			self.helperParse('../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml')
			
	'''
	ParseError: undefined entity &all;: line 9, column 6
	'''
	def testParameterEntity_core(self):
		with self.assertRaises(_ET.ParseError):        
			self.helperParse('../../xml_files_windows/xxep/parameterEntity_core.xml')
		
	'''
	ParseError: undefined entity &all;: line 3, column 6
	'''
	def testParameterEntity_doctype(self):
		with self.assertRaises(_ET.ParseError):        
			self.helperParse('../../xml_files_windows/xxep/parameterEntity_doctype.xml')
		

			

			
	def testURLInvocation_doctype(self):
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		self.helperParse('../../xml_files_windows/ssrf/url_invocation_doctype.xml')

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	'''
	ParseError: undefined entity &remote;: line 5, column 6
	'''
	def testURLInvocation_externalGeneralEntity(self):     
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		with self.assertRaises(_ET.ParseError):
			self.helperParse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml')

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 



	def testURLInvocation_parameterEntity(self):             
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   


		self.helperParse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml')

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testURLInvocation_noNamespaceSchemaLocation(self):     
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		self.helperParse('../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml')                      

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 
		
	def testURLInvocation_schemaLocation(self):      
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		self.helperParse('../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml')  

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

		
	def testURLInvocation_XInclude(self):    
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		self.helperParse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml')                      

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 


	'''
	IOError: [Errno 22] invalid mode ('r') or filename: 'http://127.0.0.1:5000/file.xml'
	'''
	def testURLInvocation_XInclude_ETINCLUDE(self):
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		with self.assertRaises(IOError):
			root = self.helperParse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml')                      
			# Resolve xi:include references
			_ETINCLUDE.include(root)




	def testXInclude(self):
		root = self.helperParse('../../xml_files_windows/xinclude.xml')
		content = root[0]
		self.assertEqual("{http://www.w3.org/2001/XInclude}include", content.tag)

	def testXInclude_ETINCLUDE(self):
		root = self.helperParse('../../xml_files_windows/xinclude.xml')
		# Resolve xi:include references
		_ETINCLUDE.include(root)
		
		# The Inclusion of xi:include should now have happened.           
		# Therefore we can get an iterator over the newly included element
		# 'content'
		elem_data = root.iter('content')

		for elem in elem_data:
			self.assertEqual("it_works", elem.text)
			self.assertEqual("content", elem.tag)

	def testXSLT(self):
		root = self.helperParse('../../xml_files_windows/optional/xslt.xsl')
		content = root.tag
		self.assertEqual("{http://www.w3.org/1999/XSL/Transform}stylesheet", content)


if __name__ == "__main__":
		unittest.main()


