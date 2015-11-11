import xml.sax as _SAX

# Code from http://www.python-kurs.eu/python_XML_SAX.php
# with minimal modifications

class MyContentHandler(_SAX.ContentHandler):

   
    def __init__(self):
        self.currentContent =""
#        self.result = set()
        self.result = []

    def startElement(self, name, attrs):
#        self.currentContent = "<"+name+">"
        self.result.append(name)



    def characters(self, content):
#        self.currentContent += content.strip()
        self.result.append(content)


        
    def endElement(self, name):
#        self.currentContent += "</"+ name +">"        
#        self.result.add(self.currentContent)
        self.result.append(name)

    def getResult(self):
        return self.result

   

    ''' There will arise problems if we encounter a more complex xml structure,
        <data>
            <tmp>
                <hallo>xxxx</hallo>
            </tmp>
        </data>
   However this implementation gets the job done for a proof of concept. 
    
    '''
    def getElementContent(self, tagName):

#       position = self.result.index(tagName)
#       return self.result[position]
       startIndex = self.result.index(tagName)
       content = ""


       for i in range(startIndex+1, len(self.result)):
           if self.result[i] != tagName:
               content += self.result[i]
           else:
               # End element found
                break
       return content






#    for el in self.result:
#           #gesuchtes Element?
#            if el.find(nameOfTag) > -1:
#                # Start/EndTag entfernen
 #               tmp = el.replace("</"+nameOfTag+">", "")
 #               tmp = tmp.replace("<"+nameOfTag+">","")
 #               return tmp
                

class MySecureResolver(_SAX.handler.EntityResolver):

    def resolveEntity(self, publicId, systemId):
        print "%s, %s" % (publicId, systemId)
        raise _SAX.SAXNotSupportedException("Entities not allowed") 
        

class MyResolver(_SAX.handler.EntityResolver):

    def resolveEntity(self, publicId, systemId):
        print publicId, systemId
        return _SAX.InputSource(systemId)


#class MyDeclHandler(_SAX.sax2lib.DeclHandler):
#    pass


