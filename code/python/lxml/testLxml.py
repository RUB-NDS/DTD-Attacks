'''
Created on 02.06.2014

@author: phoenix
'''
import unittest
from lxml.etree import XMLParser, parse,XMLSyntaxError,XInclude, XIncludeError
from lxml.etree import XMLSchema
import requests


class Test(unittest.TestCase):


	def setUp(self):
		pass 

	def tearDown(self):
		pass

	def testDefault_noAttack(self):
		parser = XMLParser()
		tree = parse('../../xml_files_windows/standard.xml',parser)
		root = tree.getroot()
		self.assertIn("4",root.text)

	def testDOS_core(self):
		parser = XMLParser()
		tree = parse('../../xml_files_windows/dos/dos_core.xml',parser)
		root = tree.getroot()
		count = root.text.count("dos")
		expectedCount = 25
		self.assertEqual(expectedCount, count)

	def testDOS_core_resolve_entities (self):
		parser = XMLParser(resolve_entities=False)
		tree = parse('../../xml_files_windows/dos/dos_core.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)

	'''
	XMLSyntaxError: Detected an entity reference loop, line 1, column 5
	'''
	def testDOS_indirections(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser()
			tree = parse('../../xml_files_windows/dos/dos_indirections.xml',parser)


	def testDOS_indirections_huge_tree(self):
		parser = XMLParser(huge_tree=True)
		tree = parse('../../xml_files_windows/dos/dos_indirections.xml',parser)
		root = tree.getroot()
		count = root.text.count("dos")
		expectedCount = 10000 
		self.assertEqual(expectedCount, count)


	def testDOS_indirections_huge_tree_resolve_entities (self):
		parser = XMLParser(huge_tree=True, resolve_entities=False)
		tree = parse('../../xml_files_windows/dos/dos_indirections.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)


	def testDOS_entitySize(self):
			parser = XMLParser()
			tree = parse('../../xml_files_windows/dos/dos_entitySize.xml',parser)
			root = tree.getroot()
			count = root.text.count("dos")
			expectedCount = 3400000 
			self.assertEqual(expectedCount, count)

	def testDOS_entitySize_resolve_entities (self):
		parser = XMLParser(resolve_entities=False)
		tree = parse('../../xml_files_windows/dos/dos_entitySize.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
	def testDOS_indirections_parameterEntity(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser()
			tree = parse('../../xml_files_windows/dos/dos_indirections_parameterEntity.xml',parser)
		
	'''
	XMLSyntaxError: Detected an entity reference loop, line 1, column 4
	'''
	def testDOS_recursion (self):	
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser()	
			tree = parse('../../xml_files_windows/dos/dos_recursion.xml',parser)
		

	def testXXE(self):
		parser = XMLParser()
		tree = parse('../../xml_files_windows/xxe/xxe.xml',parser)
		root = tree.getroot()
		self.assertEquals('it_works', root.text)

	def testXXE_resolve_entities(self):
		parser = XMLParser(resolve_entities=False)
		tree = parse('../../xml_files_windows/xxe/xxe.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
	def testXXE_attribute_defaults_resolve_entities(self):
		parser = XMLParser(attribute_defaults=True, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxe/xxe.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)	

	def testXXE_dtd_validation_resolve_entities(self):
		parser = XMLParser(dtd_validation=True, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxe/xxe.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
	
	def testXXE_load_dtd_resolve_entities(self):
		parser = XMLParser(load_dtd=True, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxe/xxe.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
								 
	def testInternalSubset_ExternalPEReferenceInDTD(self):                      
		parser = XMLParser()
		tree = parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)
		
	def testInternalSubset_ExternalPEReferenceInDTD_attribute_defaults_resolve_entities(self):                      
		parser = XMLParser(attribute_defaults=True, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)

	def testInternalSubset_ExternalPEReferenceInDTD_dtd_validation_resolve_entities(self):                      
		parser = XMLParser(dtd_validation=True,resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
	def testInternalSubset_ExternalPEReferenceInDTD_load_dtd_resolve_entities(self):                      
		parser = XMLParser(load_dtd=True,resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)


	def testInternalSubset_ExternalPEReferenceInDTD_resolve_entities(self):                      
		parser = XMLParser(resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)

		
	def testInternalSubset_PEReferenceInDTD(self):                              
		parser = XMLParser()
		tree = parse('../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)

	def testInternalSubset_PEReferenceInDTD_resolve_entities(self):                              
		parser = XMLParser(resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)


	'''
	XMLSyntaxError: Entity 'all' not defined, line 10, column 12
	'''
	def testParameterEntity_core(self):
		with self.assertRaises(XMLSyntaxError):
		#gives an XMLSyntaxError when trying to access resource
			parser = XMLParser()
			tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)
	'''
	XMLSyntaxError: Entity 'all' not defined, line 10, column 12	
	'''
	def testParameterEntity_core_attribute_defaults(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(attribute_defaults=True)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)

	
	'''
	no_network=False ist fuer die Schwachstelle verantwortlich
	def testParameterEntity_core_attribute_defaults_no_network(self):
		#with self.assertRaises(XMLSyntaxError):
		parser = XMLParser(attribute_defaults=True, no_network=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)	
		root = tree.getroot()
		self.assertEquals("it_works", root.text)
	'''
	
	'''
	XMLSyntaxError: Entity 'all' not defined, line 10, column 12
	'''
	def testParameterEntity_core_dtd_validation(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(dtd_validation=True)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)

	def testParameterEntity_core_no_network(self):
		parser = XMLParser(no_network=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)


	def testParameterEntity_core_no_network_resolve_entities(self):
		parser = XMLParser(no_network=False, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_core.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)

	'''
	XMLSyntaxError: Entity 'all' not defined, line 3, column 12
	'''
	def testParameterEntity_doctype(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser()
			tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)

	'''
	XMLSyntaxError: Entity 'all' not defined, line 3, column 12
	'''
	def testParameterEntity_doctype_attribute_defaults(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(attribute_defaults=True)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)

		
	def testParameterEntity_doctype_attribute_defaults_no_network(self):		
		parser = XMLParser(attribute_defaults=True, no_network=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)
		
		
	def testParameterEntity_doctype_attribute_defaults_no_network_resolve_entities(self):
		parser = XMLParser(attribute_defaults=True, no_network=False, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
	'''
	XMLSyntaxError: Entity 'all' not defined, line 10, column 12
	'''	
	def testParameterEntity_doctype_dtd_validation(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(dtd_validation=True)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)

	def testParameterEntity_doctype_dtd_validation_no_network(self):		
		parser = XMLParser(dtd_validation=True, no_network=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)
	
	def testParameterEntity_doctype_dtd_validation_no_network_resolve_entities(self):
		parser = XMLParser(dtd_validation=True, no_network=False, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
	
	''' 
	XMLSyntaxError: Entity 'all' not defined, line 3, column 12
	'''
	def testParameterEntity_doctype_load_dtd(self):
		# no_network=true is default; 
		# this is only inserted to make this setting clear
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(load_dtd=True, no_network=True)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)

		
	def testParameterEntity_doctype_load_dtd_no_network(self):		
		parser = XMLParser(load_dtd=True, no_network=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals("it_works", root.text)
		
	def testParameterEntity_doctype_load_dtd_no_network_resolve_entities(self):
		parser = XMLParser(load_dtd=True, no_network=False, resolve_entities=False)
		tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)
		root = tree.getroot()
		self.assertEquals(None, root.text)
		
		
		
	'''
	XMLSyntaxError: Entity 'all' not defined, line 3, column 12
	'''
	def testParameterEntity_doctype_no_network(self):
		with self.assertRaises(XMLSyntaxError):
			parser = XMLParser(no_network=False)
			tree = parse('../../xml_files_windows/xxep/parameterEntity_doctype.xml',parser)


	def testURLInvocation_doctype(self):
		parser = XMLParser()
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   

	def testURLInvocation_doctype_attribute_defaults(self):
		parser = XMLParser(attribute_defaults=True)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testURLInvocation_doctype_attribute_defaults_no_network(self):
		parser = XMLParser(attribute_defaults=True, no_network=False)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content) 


	def testURLInvocation_doctype_dtd_validation(self):
		parser = XMLParser(dtd_validation=True)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)  	

	def testURLInvocation_doctype_dtd_validation_no_network(self):
		parser = XMLParser(dtd_validation=True, no_network=False)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content) 		


	def testURLInvocation_doctype_load_dtd(self):
		parser = XMLParser(load_dtd=True)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)                                  


	def testURLInvocation_doctype_load_dtd_no_network(self):

		# only setting both load_dtd=True and no_network=False
		# makes the parser make the request

		parser = XMLParser(load_dtd=True,no_network=False)

		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content)        

		
	def testURLInvocation_doctype_no_network(self):
		parser = XMLParser(no_network=False)

		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)       		

	def testURLInvocation_doctype_no_network(self):
		parser = XMLParser(no_network=False)
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		root = parse('../../xml_files_windows/ssrf/url_invocation_doctype.xml', parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	''' 
	XMLSyntaxError: Attempt to load network entity http://127.0.0.1:5000/test.xml
	'''
	def testURLInvocation_externalGeneralEntity(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      


		parser = XMLParser() 
		with self.assertRaises(XMLSyntaxError):
			root = parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)    

	'''
	XMLSyntaxError: Failure to process entity remote, line 6, column 15
	'''
	def testURLInvocation_externalGeneralEntity_attribute_defaults(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)  

		parser = XMLParser(attribute_defaults=True) 
		with self.assertRaises(XMLSyntaxError):
			root = parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)   		

	'''
	XMLSyntaxError: Failure to process entity remote, line 6, column 15
	'''
	def testURLInvocation_externalGeneralEntity_dtd_validation(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)  

		parser = XMLParser(dtd_validation=True) 
		with self.assertRaises(XMLSyntaxError):
			root = parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)       		


	def testURLInvocation_externalGeneralEntity_no_network(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		parser = XMLParser(no_network=False) 

		root = parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content)                                  

	def testURLInvocation_externalGeneralEntity_no_network_resolve_entities(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      


		parser = XMLParser(no_network=False, resolve_entities=False) 

		root = parse('../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml',parser)

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

		parser = XMLParser() 
		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)                                  

	def testURLInvocation_parameterEntity_attribute_defaults(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		parser = XMLParser(attribute_defaults=True) 
		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testURLInvocation_parameterEntity_dtd_validation(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		parser = XMLParser(dtd_validation=True) 
		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content) 

	def testURLInvocation_parameterEntity_load_dtd(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)     

		parser = XMLParser(load_dtd=True) 
		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)  

	def testURLInvocation_parameterEntity_no_network(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      

		parser = XMLParser(no_network=False) 

		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

		#Check if a request has been made                                       
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("1", request_content)                                  

	def testURLInvocation_parameterEntity_no_network_resolve_entities(self):                                    
		#Reset the server back to "0"                                           
		r = requests.get("http://127.0.0.1:5000/reset")                         
		url_counter = "http://127.0.0.1:5000/getCounter"                        
		r = requests.get(url_counter)                                           
		request_content = r.text.replace("\r\n","")                             
		self.assertEqual("0", request_content)      


		parser = XMLParser(no_network=False, resolve_entities=False) 

		root = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)

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

		parser = XMLParser()
		parse('../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml',
		parser)                      

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


		root= parse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml', parser)                      


		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

	'''
	XIncludeError: Attempt to load network entity http://127.0.0.1:5000/test.xml
	'''
	def testURLInvocation_XInclude_xinclude(self):                                       

		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		with self.assertRaises(XIncludeError):
			parser = XMLParser() 
			tree = parse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml',parser)
			#http://stackoverflow.com/questions/8827782/python-lxml-using-xiinclude-with-multiple-xml-fragments
			tree.xinclude()
			root = tree.getroot()

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

	def testURLInvocation_XInclude_xinclude_no_network(self):                                       

		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		parser = XMLParser(no_network=False) 

		tree = parse('../../xml_files_windows/ssrf/url_invocation_xinclude.xml', parser)

		#http://stackoverflow.com/questions/8827782/python-lxml-using-xiinclude-with-multiple-xml-fragments
		tree.xinclude()
		root = tree.getroot()


		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("1", request_content)

						  
	def testURLInvocation_noNamespaceSchemaLocation(self):                      
		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		parser = XMLParser()
		parse('../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml',
		parser)                      

		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

	'''
	def testURLInvocation_noNamespaceSchemaLocation_validate(self):
		#Reset the server back to "0"
		r = requests.get("http://127.0.0.1:5000/reset")
		url_counter = "http://127.0.0.1:5000/getCounter"
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)

		xmlschema_doc = parse("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xsd")
		xmlschema = XMLSchema(xmlschema_doc)
		#        parser = XMLParser(schema=XMLSchema(file="../../xml_files_windows/test/xxe.xsd"))
		parser = XMLParser(schema = xmlschema)
		doc = parse('../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml',
		parser)                      
		#xmlschema.validate(doc) 
		#Check if a request has been made
		r = requests.get(url_counter)
		request_content = r.text.replace("\r\n","")
		self.assertEqual("0", request_content)
	'''

	def testXInclude(self):
		parser = XMLParser()
		XINCLUDE = "{http://www.w3.org/2001/XInclude}" 
		tree = parse('../../xml_files_windows/xinclude.xml', parser)
		root = tree.getroot()
		self.assertEquals("data", root.tag)
		child = root[0]
		self.assertEquals(XINCLUDE + "include", child.tag)


	def testXInclude_xinclude(self):
		parser = XMLParser()
		tree = parse('../../xml_files_windows/xinclude.xml', parser)
		#http://stackoverflow.com/questions/8827782/python-lxml-using-xiinclude-with-multiple-xml-fragments
		tree.xinclude()
		root = tree.getroot()
		self.assertEquals("data", root.tag)
		child = root[0]
		self.assertEquals("content", child.tag)
		self.assertEquals("it_works", child.text)
		
	def testXSLT(self):
		parser = XMLParser()
		XSLT = "{http://www.w3.org/1999/XSL/Transform}" 
		tree = parse('../../xml_files_windows/optional/xslt.xsl', parser)
		root = tree.getroot()		
		self.assertEquals(XSLT + "stylesheet", root.tag)


if __name__ == "__main__":

	unittest.main()


