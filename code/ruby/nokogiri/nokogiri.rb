require "nokogiri"

# option 1 DEFAULT_XML is set = 
# DEFAULT_XML =
#
# the default options used for parsing XML documents
#
#     RECOVER | NONET
#     http://www.rubydoc.info/github/sparklemotion/nokogiri/Nokogiri/XML/ParseOptions 
#f = File.open("../../xml_files/xxe.xml")
#doc = Nokogiri::XML(f) do |config|
#    config.strict.noent
#end

#puts doc.at_css("data").content
#f.close
#

#option 2 - libxml options freely configurable
# however Xinclude not working
=begin
doc = Nokogiri::XML::Document.read_io(open('../../xml_files_windows/url_invocation_externalGeneralEntity.xml'), 
                                      url=nil, 
                                      encoding=nil,
                                      options=Nokogiri::XML::ParseOptions::NOENT )
									  

puts doc.at_css("data").content
=end


# option 3 : use parse()

doc = Nokogiri::XML::Document.parse(open('../../xml_files_windows/standard.xml'), 
                                      url=nil, 
                                      encoding=nil,
									  options=Nokogiri::XML::ParseOptions::DTDLOAD
                                      )

puts doc.at_css("data").content


#doc  = Nokogiri::XSLT(File.read('../../xml_files_windows/optional/xslt.xsl'))

#puts doc.root
