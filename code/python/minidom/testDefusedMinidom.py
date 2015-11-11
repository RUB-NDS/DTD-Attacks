'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
from defusedxml import minidom, EntitiesForbidden, ExternalReferenceForbidden
import requests

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

	def testDOS(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/dos_core.xml'
			doc = minidom.parse(document)    

	def testDOS_indirections(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/dos_indirections.xml'
			doc = minidom.parse(document)    			
			
	def testDOS_entitySize(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/dos_entitySize.xml'
			doc = minidom.parse(document)    			
			
	def testInternalSubset_ExternalPEReferenceInDTD(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml'
			doc = minidom.parse(document)   
			
	def testInternalSubset_PEReferenceInDTD(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/internalSubset_PEReferenceInDTD.xml'
			doc = minidom.parse(document)   

	def testParameterEntity_core(self):
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/parameterEntity_core.xml'
			doc = minidom.parse(document)   		
		
	def testParameterEntity_doctype(self):        		
		document = '../../xml_files_windows/parameterEntity_doctype.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()		
		self.assertEqual("<data/>", content) 
	
	
	def testURLInvocation_doctype(self):
		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                         
		r = requests.get(self._URL_ +"/getCounter")                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)     
		
		document = '../../xml_files_windows/url_invocation_doctype.xml'
		doc = minidom.parse(document)   
		content = doc.documentElement.toxml()          
		
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
			document = '../../xml_files_windows/url_invocation_externalGeneralEntity.xml'
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
		
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/url_invocation_parameterEntity.xml'
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
		
		document = '../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml'
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
		
		document = '../../xml_files_windows/url_invocation_schemaLocation.xml'
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
		
		document = '../../xml_files_windows/url_invocation_xinclude.xml'
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
		with self.assertRaises(EntitiesForbidden):
			document = '../../xml_files_windows/xxe.xml'
			doc = minidom.parse(document)    

		
	def testXSLT(self):
		document = '../../xml_files_windows/optional/xslt.xsl'
		doc = minidom.parse(document)    
		content = doc.documentElement.nodeName
		self.assertEqual("xsl:stylesheet", content)
		
		

if __name__ == "__main__":
	unittest.main()
