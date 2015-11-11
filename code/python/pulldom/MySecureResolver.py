import xml.sax as _SAX

class MySecureResolver(_SAX.handler.EntityResolver):

        def resolveEntity(self, publicId, systemId):
            print "%s, %s" % (publicId, systemId)
            raise _SAX.SAXNotSupportedException('SystemID/PublicID not allowed') 
