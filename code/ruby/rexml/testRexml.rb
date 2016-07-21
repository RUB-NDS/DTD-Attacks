require "rexml/document"
include REXML
require "test/unit"
require 'net/http'

class TestRexml < Test::Unit::TestCase

    def setup
		# restore the default value
         REXML::Security.entity_expansion_limit = 10000
         assert_equal(10000, REXML::Security.entity_expansion_limit)
		 REXML::Security.entity_expansion_text_limit = 10240
         assert_equal(10240, REXML::Security.entity_expansion_text_limit)
    end

    def testDefault_noAttack

        file = File.new( "../../xml_files_windows/standard.xml" )
        doc = REXML::Document.new file

        content = doc.root.text
        assert_equal("4",content)
    end

    def testDOS_core

        file = File.new( "../../xml_files_windows/dos/dos_core.xml" )
        doc = REXML::Document.new file

        content = doc.root.text

        expectedCount = 25
        realCount = (content.count "dos") / 3
        
        assert_equal(expectedCount,realCount)
       
    end

=begin 
    RuntimeError: number of entity expansions exceeded, processing aborted.
        testRexml.rb:43:in testDOS_entity_expansion_limit
=end
    def testDOS_core_entity_expansion_limit

        file = File.new( "../../xml_files_windows/dos/dos_core.xml" )
        
		assert_raise( RuntimeError ) {
		    doc = REXML::Document.new file
			REXML::Security.entity_expansion_limit = 30
			content = doc.root.text
		}	       
	end
=begin	
TestRexml#testDOS_core_entity_expansion_text_limit:
RuntimeError: entity expansion has grown too large
=end
    def testDOS_core_entity_expansion_text_limit

        file = File.new( "../../xml_files_windows/dos/dos_core.xml" )
        
		assert_raise( RuntimeError ) {
		    doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 74
			content = doc.root.text
		}	       
	end	
	


	


=begin
RuntimeError: entity expansion has grown too large
    testRexml.rb:76:in `testDOS_entitySize'
=end
    def testDOS_entitySize

        file = File.new( "../../xml_files_windows/dos/dos_entitySize.xml" )

         assert_raise( RuntimeError ) {
            doc = REXML::Document.new file			
            content = doc.root.text
        }       
    end

=begin
TestRexml#testDOS_entitySize_entity_expansion_limit:
RuntimeError: number of entity expansions exceeded, processing aborted.
=end
    def testDOS_entitySize_entity_expansion_limit

        file = File.new( "../../xml_files_windows/dos/dos_entitySize.xml" )
        
        assert_raise( RuntimeError ) {
            doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 100000000
            REXML::Security.entity_expansion_limit = 0
            content = doc.root.text
        }

    end

=begin
TestRexml#testDOS_entitySize_entity_expansion_text_
RuntimeError: entity expansion has grown too large
=end
	def testDOS_entitySize_entity_expansion_text_limit

        file = File.new( "../../xml_files_windows/dos/dos_entitySize.xml" )
        
        assert_raise( RuntimeError ) {
            doc = REXML::Document.new file
            REXML::Security.entity_expansion_text_limit = 0
            content = doc.root.text
        }

    end
	
	
=begin
RuntimeError: entity expansion has grown too large
    testRexml.rb:59:in `testDOS_indirections'
=end
    def testDOS_indirections

        file = File.new( "../../xml_files_windows/dos/dos_indirections.xml" )

         assert_raise( RuntimeError ) {
            doc = REXML::Document.new file
			#REXML::Security.entity_expansion_text_limit = 30000
			#REXML::Security.entity_expansion_limit = 11111
            content = doc.root.text
        }

       
    end

	
=begin
TestRexml#testDOS_indirections_entity_expansion_limit:
RuntimeError: number of entity expansions exceeded, processing aborted.
=end
    def testDOS_indirections_entity_expansion_limit

        file = File.new( "../../xml_files_windows/dos/dos_indirections.xml" )
        
        assert_raise( RuntimeError ) {
            doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 100000000
            REXML::Security.entity_expansion_limit = 0
            content = doc.root.text
        }


    end
=begin
TestRexml#testDOS_indirections_entity_expansion_text_
RuntimeError: entity expansion has grown too large
=end
	def testDOS_indirections_entity_expansion_text_limit

        file = File.new( "../../xml_files_windows/dos/dos_indirections.xml" )
        
        assert_raise( RuntimeError ) {
            doc = REXML::Document.new file
            REXML::Security.entity_expansion_text_limit = 0
            content = doc.root.text
        }
	end
	
=begin
REXML::ParseException: #<RuntimeError: entity expansion has grown too large>
=end
	def testDOS_indirections_parameterEntity

        file = File.new( "../../xml_files_windows/dos/dos_indirections_parameterEntity.xml" )

         assert_raise( ParseException ) {
            doc = REXML::Document.new file		
            content = doc.root.text
        }

       
    end
	
	
=begin
SystemStackError: stack level too deep
    C:/Ruby21-x64/lib/ruby/2.1.0/rexml/entity.rb:139
=end
	def testDOS_recursion

        file = File.new( "../../xml_files_windows/dos/dos_recursion.xml" )        	
		
		assert_raise( SystemStackError ) {
            doc = REXML::Document.new file            
            content = doc.root.text
        }
	end
	
	
	
	
    def testInternalSubset_ExternalPEReferenceInDTD
        file = File.new( "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml" )
        doc = REXML::Document.new file        
        content = doc.root.text        
        assert_equal("&intern;", content)
    end
	
=begin
setting has no effect
=end
	def testInternalSubset_ExternalPEReferenceInDTD_entity_expansion_limit
        file = File.new( "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml" )
        
		
		doc = REXML::Document.new file
		REXML::Security.entity_expansion_limit = 0			
		content = doc.root.text
		assert_equal("&intern;", content)        
		
    end
	
=begin
RuntimeError: entity expansion has grown too large
=end	
	def testInternalSubset_ExternalPEReferenceInDTD_entity_expansion_text_limit
        file = File.new( "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml" )
        
		assert_raise( RuntimeError ) {		
			doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 0			
			content = doc.root.text
        }     		
		
    end

    def testInternalSubset_PEReferenceInDTD 
        file = File.new( "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml" )
        doc = REXML::Document.new file
        
        content = doc.root.text
        
        assert_equal("it_works", content)
    end
	
	
=begin
TestRexml#testInternalSubset_PEReferenceInDTD_entity_expansion_limit:
RuntimeError: number of entity expansions exceeded, processing aborted.
=end
	def testInternalSubset_PEReferenceInDTD_entity_expansion_limit
        file = File.new( "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml" )
		
		assert_raise( RuntimeError ) {
		
			doc = REXML::Document.new file
			REXML::Security.entity_expansion_limit = 0
			content = doc.root.text
		}	
        
    end
	
=begin
TestRexml#testInternalSubset_PEReferenceInDTD_entity
RuntimeError: entity expansion has grown too large
=end	
	def testInternalSubset_PEReferenceInDTD_entity_expansion_text_limit
        file = File.new( "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml" )
		
		assert_raise( RuntimeError ) {
		
			doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 0
			
			content = doc.root.text
        }        
    end
	

	

    def testParameterEntity_core
        file = File.new( "../../xml_files_windows/xxep/parameterEntity_core.xml" )
        doc = REXML::Document.new file
        
        content = doc.root.text
        
        assert_equal("&all;", content)

    end 


    def testParameterEntity_doctype
        file = File.new( "../../xml_files_windows/xxep/parameterEntity_doctype.xml" )
        doc = REXML::Document.new file
        
        content = doc.root.text
        
        assert_equal("&all;", content)
    end




    def testURLInvocation_doctype

        url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_doctype.xml" )
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
    end 

    def testURLInvocation_externalGeneralEntity

        url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml" )
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
    end 


    def testURLInvocation_parameterEntity 
        url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml" )
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
    end


    def testURLInvocation_XInclude
        url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_xinclude.xml" )
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

    end


    def testURLInvocation_noNamespaceSchemaLocation
         url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml")
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
    end

    def testURLInvocation_schemaLocation
         url = "http://127.0.0.1:5000"

        # reset the counter
        Net::HTTP.get(URI.parse(url +'/reset'))
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)

        file = File.new( "../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml")
        doc = REXML::Document.new file
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
    end
	
	def testXInclude
        file = File.new( "../../xml_files_windows/xinclude.xml" )
        doc = REXML::Document.new file
        
        content = doc.root.text
        data = doc.root.elements[1].name
       
        assert_equal("include",data)

    end
	
	
	def testXXE
        file = File.new( "../../xml_files_windows/xxe/xxe.xml" )
        doc = REXML::Document.new file
        
        content = doc.root.text
        
        assert_equal("&file;", content)
    end
	
=begin
RuntimeError: entity expansion has grown too large
=end		
	def testXXE_entity_expansion_limit
        file = File.new( "../../xml_files_windows/xxe/xxe.xml" )
		
		assert_raise( RuntimeError ) {		
			doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 0			
			content = doc.root.text
        }     	
        
    end
	
=begin
RuntimeError: entity expansion has grown too large
=end		
	def testXXE_entity_expansion_text_limit
        file = File.new( "../../xml_files_windows/xxe/xxe.xml" )
		
		assert_raise( RuntimeError ) {		
			doc = REXML::Document.new file
			REXML::Security.entity_expansion_text_limit = 0			
			content = doc.root.text
        }     
		
        
    end
	
	
	def testXSLT
		file = File.new( "../../xml_files_windows/optional/xslt.xsl" )        
        doc = REXML::Document.new file        
        content = doc.root.name
		content = content.strip()		
        assert_equal("stylesheet", content)
	end

end
