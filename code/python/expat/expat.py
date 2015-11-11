import xml.parsers.expat as _EXPAT

# 3 handler functions
def start_element(name, attrs):
    print 'Start element:', name, attrs
def end_element(name):
    print 'End element:', name
def char_data(data):
    print 'Character data:', repr(data)


def element_decl_handler(name, model):
    print "%s, %s" % name, model

def entity_decl(entityName, is_parameter_entity, value, base, systemId,
        publicId, notationName):
    print 'EntityName', entityName
    print 'is_parameter_entity', is_parameter_entity
    print 'value', value
    print 'systemId', systemId

    raise IOError("Entities not allowed")

def external_entityref_handler(context, base, systemId, publicId):
    print 'context', context
    print 'base', base
    print 'systemId', systemId
    print 'publicId', publicId

p= _EXPAT.ParserCreate()

p.ElementDeclHandler = element_decl_handler
p.StartElementHandler = start_element
p.EndElementHandler = end_element
p.CharacterDataHandler = char_data
p.EntityDeclHandler = entity_decl
#p.ExternalEntityRefHandler = external_entityref_handler
#p.SetParamEntityParsing(_EXPAT.XML_PARAM_ENTITY_PARSING_ALWAYS)

p.Parse("""<?xml version="1.0"?><!DOCTYPE data [
<!ENTITY all "it_works">
<!ENTITY % test "test">
<!ENTITY file SYSTEM "file:///tmp/xxe.txt">
]>
<data>&file;</data>
""",0)

