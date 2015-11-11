from defusedxml.sax import make_parser, parse
from MyContentHandler import MyContentHandler

parser = make_parser()
myHandler = MyContentHandler()
parser.setContentHandler(myHandler)
parser.parse('../../xml_files_windows/standard.xml')
print myHandler.getElementContent("data") 


