'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import xml.sax as _SAX
import requests
from MyContentHandler import MyContentHandler, MySecureResolver

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

		self.parser.parse('../../xml_files_windows/dos_core.xml')                      
		tmp = self.myHandler.getElementContent("data")
		expectedCount = 25
		count = tmp.count("dos")
		self.assertEqual(expectedCount,count)

	def testDOS_indirections(self):

		self.parser.parse('../../xml_files_windows/dos_indirections.xml')                      
		tmp = self.myHandler.getElementContent("data")
		expectedCount = 10000 
		count = tmp.count("dos")
		self.assertEqual(expectedCount,count)

	def testDOS_entitySize(self):

		self.parser.parse('../../xml_files_windows/dos_entitySize.xml')                      
		tmp = self.myHandler.getElementContent("data")
		expectedCount = 3400000 
		count = tmp.count("dos")
		self.assertEqual(expectedCount,count)
		
	'''
	SAXParseException: ../../xml_files_windows/optional/dos_indirections_parameterEntity.xml:4:14: illegal parameter entity reference
	'''
	def testDOS_indirections_parameterEntity(self):
		with self.assertRaises(_SAX.SAXParseException):
			self.parser.parse('../../xml_files_windows/optional/dos_indirections_parameterEntity.xml')                      
		
		
	'''
	SAXParseException: ../../xml_files_windows/optional/dos_recursion.xml:6:6: recursive entity reference
	'''
	def testDOS_recursion(self):
	
		with self.assertRaises(_SAX.SAXParseException):
			self.parser.parse('../../xml_files_windows/optional/dos_recursion.xml')                      
			tmp = self.myHandler.getElementContent("data")
		
		
		
	def testXXE(self):
		self.parser.parse('../../xml_files_windows/xxe.xml')                       
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("it_works",tmp)
		
	'''
	SAXNotSupportedException: Entities not allowed	
	'''
	def testXXE_setEntityResolver(self):
	                      		
		mySecureResolver = MySecureResolver()
		self.parser.setEntityResolver(mySecureResolver)
		with self.assertRaises(_SAX.SAXNotSupportedException):
			self.parser.parse('../../xml_files_windows/xxe.xml') 
			tmp = self.myHandler.getElementContent("data")
			
		
	def testXXE_setFeature_feature_external_ges(self):
		# disables External Entities
		self.parser.setFeature(_SAX.handler.feature_external_ges, False)        
		self.assertFalse(self.parser.getFeature(_SAX.handler.feature_external_ges))
		self.parser.parse('../../xml_files_windows/xxe.xml')                       
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("",tmp)

	def testInternalSubset_ExternalPEReferenceInDTD(self):                      
		self.parser.parse('../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml')
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("it_works",tmp) 	
		

	def testInternalSubset_PEReferenceInDTD(self):                              
		self.parser.parse('../../xml_files_windows/internalSubset_PEReferenceInDTD.xml')
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("it_works",tmp)                                        	
		
		
	def testParameterEntity_core(self):		
		self.parser.parse('../../xml_files_windows/parameterEntity_core.xml')
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("",tmp)

	'''
	SAXNotSupportedException: expat does not read external parameter entities
	'''
	def testParameterEntity_core_setFeature_feature_external_pes(self):
		with self.assertRaises(_SAX.SAXNotSupportedException):
			self.parser.setFeature(_SAX.handler.feature_external_pes, True)


	def testParameterEntity_doctype(self):
		self.parser.parse('../../xml_files_windows/parameterEntity_doctype.xml')
		tmp = self.myHandler.getElementContent("data")
		self.assertEqual("",tmp)
		

	def testURLInvocation_doctype(self):
	
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		self.parser.parse('../../xml_files_windows/url_invocation_doctype.xml')                  

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content) 

	'''
	SAXNotSupportedException: Entities not allowed
	'''
	def testURLInvocation_doctype_setEntityResolver(self):

		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		mySecureResolver = MySecureResolver()
		self.parser.setEntityResolver(mySecureResolver)

		with self.assertRaises(_SAX.SAXNotSupportedException):
			tmp =  self.parser.parse('../../xml_files_windows/url_invocation_doctype.xml')                      

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

		self.parser.parse('../../xml_files_windows/url_invocation_externalGeneralEntity.xml')                     

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content) 


	'''
	SAXNotSupportedException: Entities not allowed
	'''
	def testURLInvocation_externalGeneralEntity_setEntityResolver(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		mySecureResolver = MySecureResolver()
		self.parser.setEntityResolver(mySecureResolver)

		with self.assertRaises(_SAX.SAXNotSupportedException):
			self.parser.parse('../../xml_files_windows/url_invocation_externalGeneralEntity.xml')                     

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 
		
		
	def testURLInvocation_externalGeneralEntity_feature_external_ges(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   
		
		self.parser.setFeature("http://xml.org/sax/features/external-general-entities", False)
		self.assertFalse(self.parser.getFeature("http://xml.org/sax/features/external-general-entities"))
		
		self.parser.parse('../../xml_files_windows/url_invocation_externalGeneralEntity.xml')                     

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

		self.parser.parse('../../xml_files_windows/url_invocation_parameterEntity.xml')                      

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content) 


	'''
	SAXNotSupportedException: Entities not allowed
	'''
	def testURLInvocation_parameterEntity_setEntityResolver(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		mySecureResolver = MySecureResolver()
		self.parser.setEntityResolver(mySecureResolver)

		with self.assertRaises(_SAX.SAXNotSupportedException):
			self.parser.parse('../../xml_files_windows/url_invocation_parameterEntity.xml')                      

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


		self.parser.parse('../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml')                

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 
	
	'''
	SAXNotSupportedException: expat does not support validation
	'''	
	def testURLInvocation_noNamespaceSchemaLocation_setFeature_validation(self):                      

		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		with self.assertRaises(_SAX.SAXNotSupportedException): 
			self.parser.setFeature("http://xml.org/sax/features/validation",True)	

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

		self.parser.parse('../../xml_files_windows/url_invocation_schemaLocation.xml')                      

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

		self.parser.parse('../../xml_files_windows/url_invocation_xinclude.xml')                      

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
