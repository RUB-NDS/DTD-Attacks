require 'rexml/document'
include REXML

file = File.new( "../../xml_files_windows/standard.xml" )
doc = Document.new(file) 
content = doc.root.text
puts content