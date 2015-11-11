import xml.dom.pulldom as _PULLDOM 
import  xml.sax as _SAX


doc = _PULLDOM.parse('../../xml_files_windows/standard.xml')

'''
parser = _SAX.make_parser()
doc = _PULLDOM.parse('../../xml_files_windows/standard.xml')
'''


for event, node in doc:
	if event == _PULLDOM.START_ELEMENT and node.tagName == "data":				
		doc.expandNode(node)
		print node.nodeName
		#access the first child elements name
		print node.firstChild.nodeName		
		#access the first child elements value
		print node.firstChild.data		
		# print out the node including child elements
		print node.toxml()         
