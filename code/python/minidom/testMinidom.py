'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
from xml.dom import minidom
from xml.parsers import expat
import requests
from xml.parsers.expat import ExpatError

class Test(unittest.TestCase):

	def setUp(self):
		self._URL_="http://127.0.0.1:5000"
	def tearDown(self):
		pass
		
	def testDefault_noAttack(self):
		document = '../../xml_files_windows/standard.xml'
		doc = minidom.parse(document)    
		content = doc.documentElement.firstChild.nodeValue
		self.assertEqual("4", content)

	def testDOS_core(self):
		document = '../../xml_files_windows/dos/dos_core.xml'
		doc = minidom.parse(document)    
		content = doc.documentElement.firstChild.nodeValue
		expectedCount = 25
		count = content.count("dos")
		self.assertEqual(expectedCount,count)

	def testDOS_indirections(self):
		document = '../../xml_files_windows/dos/dos_indirections.xml'
		doc = minidom.parse(document)    
		content = doc.documentElement.firstChild.nodeValue
		expectedCount = 10000 
		count = content.count("dos")
		self.assertEqual(expectedCount,count)
		
	def testDOS_entitySize(self):
		document = '../../xml_files_windows/dos/dos_entitySize.xml'
		doc = minidom.parse(document)    
		content = doc.documentElement.firstChild.nodeValue		                            
		expectedCount = 3400000 
		count = content.count("dos")
		self.assertEqual(expectedCount, count)
		
	'''
	ExpatError: illegal parameter entity reference: line 4, column 14
	'''	
	def testDOS_indirections_parameterEntity(self):
		document = '../../xml_files_windows/dos/dos_indirections_parameterEntity.xml'
		with self.assertRaises(ExpatError):		
			doc = minidom.parse(document)    
		
		
	'''
	ExpatError: recursive entity reference: line 6, column 6
	'''	
	def testDOS_recursion(self):
		document = '../../xml_files_windows/dos/dos_recursion.xml'		
		with self.assertRaises(ExpatError):
			doc = minidom.parse(document)    
			content = doc.documentElement.firstChild.nodeValue		                            
		

	def testInternalSubset_ExternalPEReferenceInDTD(self):
		document = '../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()         
		self.assertEqual('<data/>',content)
		
	def testInternalSubset_PEReferenceInDTD(self):
		document = '../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()       
		self.assertEqual('<data/>',content)  	
		
	def testParameterEntity_core(self):
		document = '../../xml_files_windows/xxep/parameterEntity_core.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()
		self.assertEqual("<data/>", content)
		
	def testParameterEntity_doctype(self):           
		document = '../../xml_files_windows/xxep/parameterEntity_doctype.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()		
		self.assertEqual("<data/>", content)

	def testURLInvocation_doctype(self):
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                         
		r = requests.get(self._URL_ +"/getCounter")                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)     
		
		document = '../../xml_files_windows/ssrf/url_invocation_doctype.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()          
		
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
		
		document = '../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()      
		
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
		
		document = '../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()      
		
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
		
		document = '../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()                        
		
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
		
		document = '../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()     
		
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
		
		document = '../../xml_files_windows/ssrf/url_invocation_xinclude.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()     
		
		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)                                  
		
  	
		
	def testXInclude(self):
		document = '../../xml_files_windows/xinclude.xml'
		doc = minidom.parse(document)   		
		content = doc.documentElement.firstChild.nodeName
		self.assertEqual("xi:include", content)      
		
	def testXXE(self):
		document = '../../xml_files_windows/xxe/xxe.xml'
		doc = minidom.parse(document)    
		content = doc.documentElement.toxml()
		self.assertEqual("<data/>", content)
		
	def testXSLT(self):
		document = '../../xml_files_windows/optional/xslt.xsl'
		doc = minidom.parse(document)    
		content = doc.documentElement.nodeName
		self.assertEqual("xsl:stylesheet", content)

if __name__ == "__main__":
	unittest.main()