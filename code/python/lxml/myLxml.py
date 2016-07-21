# umbenannt; wenn es lxml.py heisst, wird lxml im lokalen Verzeichnis gesucht und nicht gefunden!

from lxml.etree import XMLParser, parse

parser = XMLParser(no_network=False, resolve_entities=True) 
tree = parse('../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml',parser)
root = tree.getroot()
print root.tag
print root.text
