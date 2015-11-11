import xml.sax as _SAX

class myContentHandler(_SAX.ContentHandler):

    def characters(self, content):
       print content

handler = myContentHandler()
parser = _SAX.make_parser()
parser.setContentHandler(handler)
parser.parse('../../xml_files_windows/standard.xml')
