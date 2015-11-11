import defusedxml.ElementTree as _ET
import xml.etree.ElementInclude as _ETINCLUDE

tree = _ET.parse('../../xml_files_windows/standard.xml')
root = tree.getroot()
print root.tag
print root.text


'''
# for using XInclude
# This code helped a lot with troubeshooting ElementInclude.include:
# http://stackoverflow.com/questions/23684996/using-python-elementtree-elementinclude-and-xpointer-to-access-included-xml-file

import xml.etree.ElementTree as _ET
import xml.etree.ElementInclude as _ETINCLUDE

tree = _ET.parse('../../xml_files_windows/xinclude.xml')
root = tree.getroot()

_ETINCLUDE.include(root)
elem_data = root.iter('content')
for elem in elem_data:
	print elem.tag
	print elem.text
'''
