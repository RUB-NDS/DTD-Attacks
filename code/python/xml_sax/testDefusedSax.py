'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import defusedxml.sax as _SAX
import requests
from MyContentHandler import MyContentHandler, MySecureResolver
from defusedxml import EntitiesForbidden,ExternalReferenceForbidden 

class MyContentHandlerTest(unittest.TestCase):


	def setUp(self):
		self._URL_="http://127.0.0.1:5000"

		self.parser = _SAX.make_parser()


		# Default Configuration

		self.assertTrue(self.parser.getFeature("http://xml.org/sax/features/external-general-entities"))
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/external-parameter-entities"))
		# Not recognized      
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/is-standalone"))
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/lexical-handler/parameter-entities"))
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/namespaces"))
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/namespace-prefixes"))
		# Not recognized
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/resolve-dtd-uris"))
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/string-interning"))
		#Not recognized
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/unicode-normalization-checking"))
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/use-attributes2"))
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/use-locator2"))
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/use-entity-resolver2"))
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/validation"))
		#Not recognized
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/xmlns-uris"))
		#        self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/xmlns-1.1"))      

		self.myHandler = MyContentHandler()
		self.parser.setContentHandler(self.myHandler)

	def tearDown(self):
		pass

	def testDefault_noAttack(self):		
		self.parser.parse('../../xml_files_windows/standard.xml')                       
		tmp = self.myHandler.getElementContent("data")

		self.assertEqual('4',tmp)


	def testDOS_core(self):
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/dos/dos_core.xml')                      
			

	def testDOS_indirections(self):
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/dos/dos_indirections.xml')                      
		

	def testDOS_entitySize(self):
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/dos/dos_entitySize.xml')                      
		

	def testXXE(self):
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/xxe/xxe.xml')                       
		

	def testInternalSubset_ExternalPEReferenceInDTD(self):                      
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml')
		
		

	def testInternalSubset_PEReferenceInDTD(self):                              
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml')
		                          	
		
		
	def testParameterEntity_core(self):		
		with self.assertRaises(EntitiesForbidden):
			self.parser.parse('../../xml_files_windows/xxep/parameterEntity_core.xml')
		

	'''
	ExternalReferenceForbidden: ExternalReferenceForbidden(system_id='http://127.0.0.1:5000/parameterEntity_doctype.dtd', public_id=None)
	'''
	def testParameterEntity_doctype(self):
		with self.assertRaises(ExternalReferenceForbidden):
			self.parser.parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml')
		
		
	'''
	ExternalReferenceForbidden: ExternalReferenceForbidden(system_id='http://127.0.0.1:5000/', public_id=None)
	'''
	def testURLInvocation_doctype(self):
	
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   
		
		with self.assertRaises(ExternalReferenceForbidden):
			self.parser.parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml')                  

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testURLInvocation_externalGeneralEntity(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   
		
		with self.assertRaises(EntitiesForbidden):		
			self.parser.parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml')                     

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
		
		with self.assertRaises(EntitiesForbidden):	
			self.parser.parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml')                      

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


		self.parser.parse('../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml')                

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

		self.parser.parse('../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml')                      

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

		self.parser.parse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml')                      

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testXInclude(self):

		self.parser.parse('../../xml_files_windows/xinclude.xml')                      
		
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual('xi:includexi:include',tmp)
		
		tmp = self.myHandler.getElementContent("xi:include")
		self.assertEqual("", tmp)	
		
		
		with self.assertRaises(ValueError):
			tmp = self.myHandler.getElementContent("content")
			self.assertEqual(None, tmp)


	def testXSLT(self):
		self.parser.parse('../../xml_files_windows/optional/xslt.xsl')                      
		
		tmp = self.myHandler.getElementContent("xsl:stylesheet")
		self.assertEqual("\n   xsl:template\n       xsl:value-ofxsl:value-of\n   xsl:template\n",tmp)		
		
		
		with self.assertRaises(ValueError):
			tmp = self.myHandler.getElementContent("content")
			self.assertEqual(None, tmp)			
		



if __name__ == "__main__":

	unittest.main()
