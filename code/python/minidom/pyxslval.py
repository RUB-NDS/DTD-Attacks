
#from minixsv import pyxsval
from xml.dom import minidom

doc = minidom.parse('../../xml_files/standard.xml')
#pyxsval.parseAndValidate('../../xml_files/standard.xml')

#print doc.toxml()
print doc.getElementsByTagName('data')[0].nodeName
#liste = doc.getElementsByTagName('data')




for child in doc.childNodes:
    if child.nodeType == child.DOCUMENT_TYPE_NODE:
    
        print child.toxml()
    else:
        print child.toxml()





