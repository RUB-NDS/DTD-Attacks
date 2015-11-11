import xml.sax as _SAX
from MyContentHandler import MyContentHandler

parser = _SAX.make_parser()
myHandler = MyContentHandler()
parser.setContentHandler(myHandler)
parser.parse('../../xml_files_windows/standard.xml')
print myHandler.getElementContent("data") 



