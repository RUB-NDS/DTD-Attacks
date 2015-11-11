import defusedxml.lxml as _LXML

tree = _LXML.parse("../../xml_files_windows/standard.xml")
root = tree.getroot()
print root.tag
print root.text
