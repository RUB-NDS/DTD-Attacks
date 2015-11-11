'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import defusedxml.ElementTree as _ET
import xml.etree.ElementInclude as _ETINCLUDE
from defusedxml import EntitiesForbidden,ExternalReferenceForbidden
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
		self.assertIn("4",root.text)
	'''
	EntitiesForbidden: EntitiesForbidden(name='a0', system_id=None, public_id=None)
	'''
	def testDOS_core(self):
		with self.assertRaises(EntitiesForbidden):
			self.helperParse('../../xml_files_windows/dos_core.xml')
			
	'''
	EntitiesForbidden: EntitiesForbidden(name='a0', system_id=None, public_id=None)
	'''			
	def testDOS_indirections(self):
		with self.assertRaises(EntitiesForbidden):
			root = self.helperParse('../../xml_files_windows/dos_indirections.xml')		
	'''
	EntitiesForbidden: EntitiesForbidden(name='a0', system_id=None, public_id=None)
	'''
	def testDOS_entitySize(self):
		with self.assertRaises(EntitiesForbidden):
			root = self.helperParse('../../xml_files_windows/dos_entitySize.xml')		

	'''
	EntitiesForbidden: EntitiesForbidden(name='file', system_id=u'file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt', public_id=None)
	'''
	def testXXE(self):
		with self.assertRaises(_ET.EntitiesForbidden):
			tmp = self.helperParse('../../xml_files_windows/xxe.xml')
	'''
	EntitiesForbidden: EntitiesForbidden(name='external', system_id=u'internalSubset_ExternalPEReferenceInDTD.dtd', public_id=None)
	'''
	def testInternalSubset_ExternalPEReferenceInDTD(self):
		with self.assertRaises(EntitiesForbidden):
			tmp = self.helperParse('../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.xml')
	'''
	EntitiesForbidden: EntitiesForbidden(name='internal', system_id=None, public_id=None)
	'''	
	def testInternalSubset_PEReferenceInDTD(self):
		with self.assertRaises(EntitiesForbidden):
			tmp = self.helperParse('../../xml_files_windows/dtd/internalParameterEntity/internalSubset_PEReferenceInDTD.xml')

	'''
	EntitiesForbidden: EntitiesForbidden(name='start', system_id=None, public_id=None)
	'''
	def testParameterEntity_core(self):
		with self.assertRaises(EntitiesForbidden):
			tmp = self.helperParse('../../xml_files_windows/parameterEntity_core.xml')                                      

	'''
	ParseError: undefined entity &all;: line 3, column 6
	'''
	def testParameterEntity_doctype(self):
		with self.assertRaises(_ET.ParseError):        
			self.helperParse('../../xml_files_windows/parameterEntity_doctype.xml')

			
			
	def testURLInvocation_doctype(self):
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		#with self.assertRaises(EntitiesForbidden):
		self.helperParse('../../xml_files_windows/url_invocation_doctype.xml')

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

		
	'''
	EntitiesForbidden: EntitiesForbidden(name='remote', system_id=u'http://127.0.0.1:5000/file.xml', public_id=None)
	'''
	def testURLInvocation_externalGeneralEntity(self):     
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		with self.assertRaises(EntitiesForbidden):
			self.helperParse('../../xml_files_windows/url_invocation_externalGeneralEntity.xml')

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 
		
	'''
	EntitiesForbidden: EntitiesForbidden(name='remote', system_id=u'http://127.0.0.1:5000/url_invocation_parameterEntity.dtd', public_id=None)
	'''
	def testURLInvocation_parameterEntity(self):   
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   	
		
		with self.assertRaises(EntitiesForbidden):
			self.helperParse('../../xml_files_windows/url_invocation_parameterEntity.xml')                      

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

		self.helperParse('../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml')                      

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

		self.helperParse('../../xml_files_windows/url_invocation_schemaLocation.xml')  

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

		self.helperParse('../../xml_files_windows/url_invocation_xinclude.xml')                      

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
			root = self.helperParse('../../xml_files_windows/url_invocation_xinclude.xml')                      
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
