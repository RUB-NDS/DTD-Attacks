require "rexml/document"
require "rexml/streamlistener"
require_relative "MyListener"
include REXML


file = File.new( "../../xml_files/standard.xml" ) 
listener = MyListener.new
REXML::Document.parse_stream(file, listener)
