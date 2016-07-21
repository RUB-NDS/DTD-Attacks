'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
import xml.dom.pulldom as _PULLDOM
import requests
from MySecureResolver import MySecureResolver
import defusedxml.pulldom as _DEFUSED
from defusedxml import EntitiesForbidden,ExternalReferenceForbidden

class Test(unittest.TestCase):



	def setUp(self):
		self._URL_="http://127.0.0.1:5000" 

	def tearDown(self):
		pass

	def testDefault_noAttack(self):          		
		file = '../../xml_files_windows/standard.xml'
		tagName = "data"

		doc = _DEFUSED.parse(file)        
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)                                   
				self.assertEqual("data",node.nodeName)
				self.assertEqual("4",node.firstChild.data)


	def testDOS_core(self):

		file = '../../xml_files_windows/dos/dos_core.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)  

	def testDOS_indirections(self):

		file = '../../xml_files_windows/dos/dos_indirections.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)  

	def testDOS_entitySize(self):

		file = '../../xml_files_windows/dos/dos_entitySize.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)  
							


	def testXXE(self):
		file = '../../xml_files_windows/xxe/xxe.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)                                   					



	def testInternalSubset_ExternalPEReferenceInDTD(self): 

		file = '../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)   


	def testInternalSubset_PEReferenceInDTD(self): 

		file = '../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node) 


	def testParameterEntity_core(self):                                                
		file = '../../xml_files_windows/xxep/parameterEntity_core.xml'
		tagName = "data"
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)        	

			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)    				

	def testParameterEntity_doctype(self):  

		file = '../../xml_files_windows/xxep/parameterEntity_doctype.xml'
		tagName = "data"
		with self.assertRaises(ExternalReferenceForbidden):
			doc = _DEFUSED.parse(file)        
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)    






	def testURLInvocation_doctype(self):

		#Reset the server back to "0"                                           
		r = requests.get(self._URL_+"/reset")                                   
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

		file = '../../xml_files_windows/ssrf/url_invocation_doctype.xml'
		tagName = "data"		
		with self.assertRaises(ExternalReferenceForbidden):
			doc = _DEFUSED.parse(file)
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)                                   

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

		file = '../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml'
		tagName = "data"	
		with self.assertRaises(EntitiesForbidden):		
			doc = _DEFUSED.parse(file)
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)                                   					
								 
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

		file = '../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml'
		tagName = "data"		
		with self.assertRaises(EntitiesForbidden):
			doc = _DEFUSED.parse(file)
			for event, node in doc:                                                         
				if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
					doc.expandNode(node)                                   				
							 
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

		file = '../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml'
		tagName = "data"		
		doc = _DEFUSED.parse(file)
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)                                   				                    

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

		file = '../../xml_files_windows/ssrf/url_invocation_xinclude.xml'
		tagName = "data"		
		doc = _DEFUSED.parse(file)
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)    

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

		file = '../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml'
		tagName = "data"		
		doc = _DEFUSED.parse(file)
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)  

		#Check if a request has been made                                       
		r = requests.get(self._URL_ +"/getCounter")                             
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

									 

	def testXInclude(self):
		file = '../../xml_files_windows/xinclude.xml'
		tagName = "data"

		doc = _DEFUSED.parse(file)        
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)                                   
				self.assertEqual("xi:include", node.firstChild.nodeName	)		


	def testXSLT(self):
		file = '../../xml_files_windows/optional/xslt.xsl'
		tagName = "xsl:stylesheet"

		doc = _DEFUSED.parse(file)        
		for event, node in doc:                                                         
			if event == _PULLDOM.START_ELEMENT and node.tagName == tagName:    				
				doc.expandNode(node)                                   
				self.assertEqual("xsl:stylesheet", node.nodeName)



if __name__ == "__main__":
	unittest.main()
