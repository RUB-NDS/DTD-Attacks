'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import defusedxml.lxml as _LXML
from lxml.etree import XMLParser, parse,XMLSyntaxError,XInclude, XIncludeError
from defusedxml import EntitiesForbidden,ExternalReferenceForbidden
from lxml.etree import XMLSchema
import requests


class Test(unittest.TestCase):


	def setUp(self):
		pass 

	def tearDown(self):
		pass

	def testDefault_noAttack(self):		
		tree = _LXML.parse('../../xml_files_windows/standard.xml')
		root = tree.getroot()
		self.assertIn("4",root.text)

	'''
	EntitiesForbidden: EntitiesForbidden(name='a0', system_id=None, public_id=None)
	'''
	def testDOS_core(self):	
		with self.assertRaises(EntitiesForbidden):	
			tree = _LXML.parse('../../xml_files_windows/dos/dos_core.xml')
			
		
	'''
	XMLSyntaxError: Detected an entity reference loop, line 1, column 5
	'''
	def testDOS_indirections(self):
		with self.assertRaises(XMLSyntaxError):			
			tree = _LXML.parse('../../xml_files_windows/dos/dos_indirections.xml')

	'''
	EntitiesForbidden: EntitiesForbidden(name='a0', system_id=None, public_id=None)
	'''
	def testDOS_entitySize(self):
		#with self.assertRaises(XMLSyntaxError):
		with self.assertRaises(EntitiesForbidden):
			tree = _LXML.parse('../../xml_files_windows/dos/dos_entitySize.xml')
		
	'''
	EntitiesForbidden: EntitiesForbidden(name='file', system_id=None, public_id=None)
	'''
	def testXXE(self):
		with self.assertRaises(EntitiesForbidden):		
			tree = _LXML.parse('../../xml_files_windows/xxe/xxe.xml')
		

	'''
	EntitiesForbidden: EntitiesForbidden(name='external', system_id=None, public_id=None)	
	'''
	def testInternalSubset_ExternalPEReferenceInDTD(self):
		with self.assertRaises(EntitiesForbidden):
			tree = _LXML.parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml')



	'''
	EntitiesForbidden: EntitiesForbidden(name='internal', system_id=None, public_id=None)
	'''
	def testInternalSubset_PEReferenceInDTD(self):                              
		with self.assertRaises(EntitiesForbidden):
			tree = _LXML.parse('../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml')
		


	'''
	EntitiesForbidden: EntitiesForbidden(name='start', system_id=None, public_id=None)d
	'''
	def testParameterEntity_core(self):
		with self.assertRaises(EntitiesForbidden):						
			tree = _LXML.parse('../../xml_files_windows/xxep/parameterEntity_core.xml')
	
	def testParameterEntity_doctype(self):				
		tree = _LXML.parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml')
		root = tree.getroot()
		self.assertEquals(None, root.text)
	

	
	def testURLInvocation_doctype(self):
		
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml')

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


		
		with self.assertRaises(EntitiesForbidden):
			tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml')

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

		with self.assertRaises(EntitiesForbidden):
			tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml')

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

		
		tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml')		

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

		parser = XMLParser() 


		tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml')                      


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
		
		tree = _LXML.parse('../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml')                      

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)


	def testXInclude(self):
		XINCLUDE = "{http://www.w3.org/2001/XInclude}" 
		tree = _LXML.parse('../../xml_files_windows/xinclude.xml')
		root = tree.getroot()
		self.assertEquals("data", root.tag)
		child = root[0]
		self.assertEquals(XINCLUDE + "include", child.tag)


		
if __name__ == "__main__":

	unittest.main()


