require "test/unit"
require 'net/http'
require "nokogiri"



class TestNokogiri < Test::Unit::TestCase

    def testDefault_noAttack

        f = File.open("../../xml_files_windows/standard.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
                                               
        content = doc.at_css("data").content 
        assert_equal("4", content)
    end


    def testDOS_core
        f = File.open("../../xml_files_windows/dos_core.xml")
   
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
                                               
    
        content = doc.at_css("data").content 
        expectedCount = 25 
        realCount = (content.count "dos") / 3 
        assert_equal(expectedCount,realCount)

 
    end
=begin
    def testDOS_core_ParseOptions_HUGE 
        f = File.open("../../xml_files_windows/dos_core.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::HUGE)
 

        content = doc.at_css("data").content 
        content = content.strip()
        expected_count = 25
        real_count = content.count("dos") / 3
        assert_equal(expected_count, real_count)
 
    end
=end

=begin
IF options=Nokogiri::XML::ParseOptions::STRICT THEN
Nokogiri::XML::SyntaxError: Detected an entity reference loop
=end
    def testDOS_indirections
        f = File.open("../../xml_files_windows/dos_indirections.xml")
   
        #assert_raise(Nokogiri::XML::SyntaxError ) {
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
				content = doc.at_css("data").content 
        expectedCount = 0 
        realCount = (content.count "dos") / 3 
        assert_equal(expectedCount,realCount)
                                             
        #}
 
    end

    def testDOS_indirections_ParseOptions_HUGE 
        f = File.open("../../xml_files_windows/dos_indirections.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::HUGE)
 

        content = doc.at_css("data").content 
        content = content.strip()
        expected_count = 10000 
        real_count = content.count("dos") / 3
        assert_equal(expected_count, real_count)
 
    end
	
	def testDOS_indirections_parameterEntity
        f = File.open("../../xml_files_windows/optional/dos_indirections_parameterEntity.xml")
   
        #assert_raise(Nokogiri::XML::SyntaxError ) {
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
				content = doc.at_css("data").content 
        expectedCount = 0 
        realCount = (content.count "dos") / 3 
        assert_equal(expectedCount,realCount)
                                             
        #}
 
    end


    def testDOS_entitySize
        f = File.open("../../xml_files_windows/dos_entitySize.xml")

          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
											   
		content = doc.at_css("data").content 
        expectedCount = 3400000 
        realCount = (content.count "dos") / 3 
        assert_equal(expectedCount,realCount)
 
    end


=begin
    def testDOS_entitySize_ParseOptions_HUGE 
        f = File.open("../../xml_files_windows/dos_entitySize.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::HUGE)
 

        content = doc.at_css("data").content 
        content = content.strip()
        expected_count = 3400000 
        real_count = content.count("dos") / 3
        assert_equal(expected_count, real_count)
 
    end
=end

	def testDOS_recursion
        f = File.open("../../xml_files_windows/optional/dos_recursion.xml")

          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
											   
		content = doc.at_css("data").content 
        expectedCount = 0 
        realCount = (content.count "dos") / 3 
        assert_equal(expectedCount,realCount)
 
    end
	
	



	


    def    testInternalSubset_ExternalPEReferenceInDTD
        f = File.open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml")

         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
                                           

        content = doc.at_css("data").content 
        assert_equal("", content)
 
    end
	
	def testInternalSubset_ExternalPEReferenceInDTD_ParseOptions_DTDATTR
        f = File.open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml")

         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDATTR)
                                           

        content = doc.at_css("data").content 
        assert_equal("it_works", content)
 
    end
	
	def testInternalSubset_ExternalPEReferenceInDTD_ParseOptions_DTDLOAD
        f = File.open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml")

         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDLOAD)
                                           

        content = doc.at_css("data").content 
        assert_equal("it_works", content)
 
    end
	
	def testInternalSubset_ExternalPEReferenceInDTD_ParseOptions_DTDVALID
        f = File.open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml")

         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDVALID)
                                           

        content = doc.at_css("data").content 
        assert_equal("it_works", content)
 
    end
	

    def   testInternalSubset_ExternalPEReferenceInDTD_ParseOptions_NOENT 
        f = File.open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
 
        content = doc.at_css("data").content 
        assert_equal("it_works", content)
 
    end

   def testInternalSubset_PEReferenceInDTD
 
       f = File.open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml")
       doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
 
        content = doc.at_css("data").content 
        assert_equal("it_works", content)
 

    end
	
	def testParameterEntity_core

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        content = doc.at_css("data").content 
        assert_equal("", content)
 

    end 
	
	def testParameterEntity_core_ParseOptions_DTDATTR

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDATTR)
 

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)

    end 
	
	def   testParameterEntity_core_ParseOptions_DTDATTR_NONET    
        f = File.open("../../xml_files_windows/parameterEntity_core.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDATTR| 
                                                   Nokogiri::XML::ParseOptions::NONET)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)
 
    end
	
	
	
	def testParameterEntity_core_ParseOptions_DTDLOAD

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD)
 

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)

    end 

	def testParameterEntity_core_ParseOptions_DTDLOAD_NONET

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD |
														Nokogiri::XML::ParseOptions::NONET)

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)

    end	


    def testParameterEntity_core_ParseOptions_DTDVALID

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID)
 

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)

    end 
	
	def testParameterEntity_core_ParseOptions_DTDVALID_NONET

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID |
														Nokogiri::XML::ParseOptions::NONET)

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)

    end 

    def testParameterEntity_core_ParseOptions_NOENT

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
 

        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)

    end 

    def testParameterEntity_core_ParseOptions_NOENT_NONET

        f = File.open("../../xml_files_windows/parameterEntity_core.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                            url=nil,                                  
                                            encoding=nil,                              
                                            options=Nokogiri::XML::ParseOptions::NOENT |
                                                    Nokogiri::XML::ParseOptions::NONET )
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)

    end 



    def   testParameterEntity_doctype    
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        content = doc.at_css("data").content 
        assert_equal("", content)
 
    end
	
	def   testParameterEntity_doctype_ParseOptions_DTDATTR    
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDATTR)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)
 
    end
	
	def   testParameterEntity_doctype_ParseOptions_DTDATTR_NONET    
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDATTR| 
                                                   Nokogiri::XML::ParseOptions::NONET)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)
 
    end

    def   testParameterEntity_doctype_ParseOptions_DTDLOAD    
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDLOAD)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)
 
    end

    def   testParameterEntity_doctype_ParseOptions_DTDLOAD_NONET
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDLOAD | 
                                                   Nokogiri::XML::ParseOptions::NONET)
 
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)
 
    end
	
	def   testParameterEntity_doctype_ParseOptions_DTDVALID
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDVALID)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)
 
    end
	
	def   testParameterEntity_doctype_ParseOptions_DTDVALID_NONET
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml") 
       doc = Nokogiri::XML::Document.parse(f,  
                                           url=nil,                                  
                                           encoding=nil,                              
                                           options=Nokogiri::XML::ParseOptions::DTDVALID |
													Nokogiri::XML::ParseOptions::NONET)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)
 
    end


    def   testParameterEntity_doctype_ParseOptions_NOENT
        f = File.open("../../xml_files_windows/parameterEntity_doctype.xml")  
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
 
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)
 
    end

    




    def testURLInvocation_doctype

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	def testURLInvocation_doctype_ParseOptions_DTDATTR 

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDATTR)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        

    end
	
	def testURLInvocation_doctype_ParseOptions_DTDATTR_NONET        

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                            url=nil,                                  
                                            encoding=nil,                              
                                            options=Nokogiri::XML::ParseOptions::DTDATTR |
                                                    Nokogiri::XML::ParseOptions::NONET)


        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	

    def testURLInvocation_doctype_ParseOptions_DTDLOAD 

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        

    end

    def testURLInvocation_doctype_ParseOptions_DTDLOAD_NONET        

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                            url=nil,                                  
                                            encoding=nil,                              
                                            options=Nokogiri::XML::ParseOptions::DTDLOAD |
                                                    Nokogiri::XML::ParseOptions::NONET)


        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	def testURLInvocation_doctype_ParseOptions_DTDVALID 

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        

    end
	
	def testURLInvocation_doctype_ParseOptions_DTDVALID_NONET

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID |
														Nokogiri::XML::ParseOptions::NONET)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	def testURLInvocation_doctype_ParseOptions_NOENT

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_doctype.xml")
          doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	

    def testURLInvocation_externalGeneralEntity

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)       

    end
	
	def testURLInvocation_externalGeneralEntity_ParseOptions_DTDATTR

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDATTR)
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	
	def testURLInvocation_externalGeneralEntity_ParseOptions_DTDLOAD

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDLOAD)
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	
	
	def testURLInvocation_externalGeneralEntity_ParseOptions_DTDVALID

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDVALID)
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        

    end
	
=begin
Nokogiri::XML::SyntaxError: Entity 'remote' not defined
=end
	def testURLInvocation_externalGeneralEntity_ParseOptions_DTDVALID_NONET

       url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)
		
		assert_raise(Nokogiri::XML::SyntaxError ) {
		
			f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
			doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,
											   options=Nokogiri::XML::ParseOptions::DTDVALID |
														Nokogiri::XML::ParseOptions::NONET )
														
		}
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        

    end
	
	
	    def testURLInvocation_externalGeneralEntity_ParseOptions_NOENT
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
#        doc = Nokogiri::XML(f) do |config|
#           config.noent
#        end 

 
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end

=begin
Nokogiri::XML::SyntaxError: Entity 'remote' not defined
=end
    def testURLInvocation_externalGeneralEntity_ParseOptions_NOENT_NONET
        
        url_addr = "http://127.0.0.1:5000" 
        
		# reset the counter
		Net::HTTP.get(URI.parse(url_addr +'/reset'))
		result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
		result = result.strip()
		assert_equal("0", result)
	   
		assert_raise(Nokogiri::XML::SyntaxError ) {
		
			f = File.open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml")
			doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT|
                                                       Nokogiri::XML::ParseOptions::NONET)
    
        }
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end

	def    testURLInvocation_noNamespaceSchemaLocation
        url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)


        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip() 
        assert_equal("0", result)
    end
	

   
    def    testURLInvocation_parameterEntity 
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)

       
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDATTR
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
		
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDATTR)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDATTR_NONET
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml") 
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDATTR|
														Nokogiri::XML::ParseOptions::NONET)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
	
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDLOAD
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
		
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDLOAD_NONET
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml") 
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD|
														Nokogiri::XML::ParseOptions::NONET)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
	
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDVALID
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
#        doc = Nokogiri::XML(f) do |config|
#           config.noent
#        end 

 
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end
	
	def testURLInvocation_parameterEntity_ParseOptions_DTDVALID_NONET
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml") 
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID|
														Nokogiri::XML::ParseOptions::NONET)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
	
	

    def testURLInvocation_parameterEntity_ParseOptions_NOENT
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
#        doc = Nokogiri::XML(f) do |config|
#           config.noent
#        end 

 
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
 

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end


    def testURLInvocation_parameterEntity_ParseOptions_NOENT_NONET
        
        url_addr = "http://127.0.0.1:5000" 
        
        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_parameterEntity.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT|
                                                       Nokogiri::XML::ParseOptions::NONET)
    
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
	
	

 
    def    testURLInvocation_schemaLocation
        url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_schemaLocation.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)

        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip() 
        assert_equal("0", result)
    end
 
    def    testURLInvocation_XInclude
        url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_xinclude.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
 
    def testURLInvocation_XInclude_ParseOptions_XINCLUDE
        url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_xinclude.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::XINCLUDE)
        
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("1", result)
        
    end
    
    def testURLInvocation_XInclude_ParseOptions_XINCLUDE_NONET
        url_addr = "http://127.0.0.1:5000"

        # reset the counter
       Net::HTTP.get(URI.parse(url_addr +'/reset'))
       result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
       result = result.strip()
       assert_equal("0", result)

        f = File.open("../../xml_files_windows/url_invocation_xinclude.xml")

        assert_raises(Nokogiri::XML::SyntaxError) {

         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::XINCLUDE |
                                                       Nokogiri::XML::ParseOptions::NONET)

        }
        #check if a request has been made 
        result = Net::HTTP.get(URI.parse(url_addr +'/getCounter'))
        result = result.strip()
        assert_equal("0", result)
        
    end
    

    def    testXInclude
        f = File.open("../../xml_files_windows/xinclude.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        
        
        content = doc.at_css("data").child.name
        ns = doc.at_css("data").child.namespace.prefix()
        assert_equal("include", content)
        assert_equal("xi", ns)


    end 
 
    def    testXInclude_ParseOptions_XINCLUDE
        f = File.open("../../xml_files_windows/xinclude.xml")
         doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::XINCLUDE)
 
        # Also mention this possibility!       
         # Document.read_io(f, url =nil, encoding = nil, options =
         # Nokogiri::XML::ParseOptions::Strict)
        #doc.do_xinclude() 
#       content = doc.at_css("data").do_xinclude(options=Nokogiri::XML::ParseOptions::STRICT) 

        content = doc.at_css("data").children()
        content = content[1]
        assert_equal("content", content.name)
        assert_equal("it_works", content.content)



    end 
	
	def    testXSLT
        f = File.open("../../xml_files_windows/optional/xslt.xsl")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)        
        
        content = doc.root.name
        assert_equal("stylesheet", content)
    end
  
    def    testXXE
        f = File.open("../../xml_files_windows/xxe.xml")
        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil)
        
        
        content = doc.at_css("data").content 
        assert_equal("", content)
    end
	
	def testXXE_ParseOptions_DTDLOAD
        f = File.open("../../xml_files_windows/xxe.xml")

        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDATTR)
											   
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)       

    end
	
	def testXXE_ParseOptions_DTDLOAD
        f = File.open("../../xml_files_windows/xxe.xml")

        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDLOAD)
											   
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("", content)       

    end
	
	def testXXE_ParseOptions_DTDVALID
        f = File.open("../../xml_files_windows/xxe.xml")

        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::DTDVALID)
											   
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)       

    end
    
    def testXXE_ParseOptions_NOENT
        f = File.open("../../xml_files_windows/xxe.xml")

        doc = Nokogiri::XML::Document.parse(f,  
                                               url=nil,                                  
                                               encoding=nil,                              
                                               options=Nokogiri::XML::ParseOptions::NOENT)
											   
        content = doc.at_css("data").content 
        content = content.strip()
        assert_equal("it_works", content)       

    end
	
	


end
