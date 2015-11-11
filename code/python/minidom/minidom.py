from xml.dom import minidom

doc = minidom.parse('../../xml_files_windows/standard.xml')
print doc.documentElement.nodeName
print doc.getElementsByTagName('data')[0].nodeName
print doc.getElementsByTagName('data')[0].firstChild.nodeValue








