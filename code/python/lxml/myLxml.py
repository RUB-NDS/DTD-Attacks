# umbenannt; wenn es lxml.py heisst, wird lxml im lokalen Verzeichnis gesucht und nicht gefunden!

from lxml.etree import XMLParser, parse

parser = XMLParser(no_network=False)
tree = parse("../../xml_files_windows/test/parameterEntity_sendftp.xml", parser)
root = tree.getroot()
print root.tag
print root.text
