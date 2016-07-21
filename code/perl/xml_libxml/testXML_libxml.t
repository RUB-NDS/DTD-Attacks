use diagnostics; # this gives you more debugging information                    
use warnings;    # this warns you of bad practices                              
use strict;      # this prevents silly errors 
use XML::LibXML; 

require LWP::UserAgent;

use Test::More tests => 93;                                                      
use Test::Exception;


our $URL = "http://127.0.0.1:5000";

# variables used in test are declared here
my $file ="";
my $parser ="";
my $dom ="";
my $content ="";

my $expected_count = 0;                                                        
my $real_count =0;

my $ua = "";
my $request ="";
my $response = "";
		
######################
## testDefault_noAttack
####
$file = "../../xml_files_windows/standard.xml";

$parser = XML::LibXML->new();
# check default parser options:
is($parser->get_option("expand_entities"), "1", "default value of expand_entities");
is($parser->get_option("load_ext_dtd"), "1", "default value of load_ext_dtd");
is($parser->get_option("complete_attributes"), "0", "default value of complete_attributes");
is($parser->get_option("validation"), "0", "default value of validation");
is($parser->get_option("expand_xinclude"), "0", "default value of expand_xinclude");
is($parser->get_option("no_xinclude_nodes"), "0", "default value of no_xinclude_nodes");
is($parser->get_option("no_network"), "0", "default value of no_network");
is($parser->get_option("huge"), "0", "default value of huge");

# From now on we use the DOM Parser, which internally creates an instance of XML::LibXML 
# and applies the parser options, if provided
$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;								                                

is($content, "4", "testDefault_noAttack");  

#####################
#testDOS_core                                                    
#####
$file = "../../xml_files_windows/dos/dos_core.xml";
$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );

$content = $dom->documentElement->textContent;                                           
$expected_count = 25;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_core");   

#####################
#testDOS_core_expand_entities                                                   
#####
$file = "../../xml_files_windows/dos/dos_core.xml";
$dom = XML::LibXML->load_xml(
          location => $file,
          expand_entities => 0
                );

$content = $dom->documentElement->textContent;                                           
$expected_count = 25;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_core_expand_entities");   



######################
## testDOS_entitySize
####
$file = "../../xml_files_windows/dos/dos_entitySize.xml";                                
$dom = XML::LibXML->load_xml(
          location => $file,
          # parser options ...
                );

$content = $dom->documentElement->textContent;                                           
$expected_count = 3400000;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_entitySize");   




				
				
#####################
#testDOS_entitySize_huge                                                   
#####
#$file = "../../xml_files_windows/dos/dos_entitySize.xml";
#$dom = XML::LibXML->load_xml(
#          location => $file,
#                huge => 1
#                );
#$content = $dom->documentElement->textContent;                                           
#$expected_count = 3400000;                                                        
#$real_count = 0;                                                             
#while ($content =~ /dos/g) { $real_count++ }                                    
#                                                                                
#is($real_count, $expected_count, "testDOS_entitySize_huge"); 


				

# Exception: Entity: line 1: parser error : Detected an entity reference loop
# &a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;
######################
## testDOS_indirections
####
$file = "../../xml_files_windows/dos/dos_indirections.xml";

throws_ok { $dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                ); } qr/Detected an entity reference loop/, 'testDOS_indirections Exception';

				
			
#####################
#testDOS_indirections_huge                                                   
#####
$file = "../../xml_files_windows/dos/dos_indirections.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
                huge => 1
                );
$content = $dom->documentElement->textContent;                                           
$expected_count = 10000;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_indirections_huge");  
				


#parser error : PEReferences forbidden in internal subset
######################
## testDOS_indirections_parameterEntity
####
$file = "../../xml_files_windows/dos/dos_indirections_parameterEntity.xml";

throws_ok { $dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                ); } qr/PEReferences forbidden in internal subset/, 'testDOS_indirections_parameterEntity Exception';
				
				
				
# Exception: Entity: line 1: parser error : Detected an entity reference loop
# &a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;&a1;
######################
## testDOS_recursion
####
$file = "../../xml_files_windows/dos/dos_recursion.xml";

throws_ok { $dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                ); } qr/Detected an entity reference loop/, 'testDOS_recursion Exception';

				
				
######################
## testInternalSubset_ExternalPEReferenceInDTD
####	                            
$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;
                                               
is($content, "it_works", "testInternalSubset_ExternalPEReferenceInDTD");  				



######################
## testInternalSubset_ExternalPEReferenceInDTD_expand_entities
####	                            
$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
                expand_entities => 0
                );
$content = $dom->documentElement->textContent;
                                               
is($content, "it_works", "testInternalSubset_ExternalPEReferenceInDTD_expand_entities");  	


# Exception: XML::LibXML::Error (../../xml_files_windows/internalSubset_ExternalPERe
#ferenceInDTD.xml:6: parser error : Entity 'intern' not defined
# <data>&intern;</data>parser error : Entity 'intern' not defined <data>&intern;</data>
######################
## testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd
####	                            
$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";

throws_ok {$dom = XML::LibXML->load_xml(
          location => $file,
                load_ext_dtd => 0
                );} qr/Entity 'intern' not defined/, 'testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd';


				
				
				
				
				
				
				
# Exception: XML::LibXML::Error (../../xml_files_windows/internalSubset_ExternalPERe
#ferenceInDTD.xml:6: parser error : Entity 'intern' not defined
# <data>&intern;</data>parser error : Entity 'intern' not defined <data>&intern;</data>
######################
## testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_complete_attributes
####	                            
$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";

throws_ok {$dom = XML::LibXML->load_xml(
          location => $file,
                load_ext_dtd => 0,
				complete_attributes => 1
                );} qr/Entity 'intern' not defined/, 'testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_complete_attributes';

				
		

# Exception: XML::LibXML::Error (../../xml_files_windows/internalSubset_ExternalPERe
#ferenceInDTD.xml:6: parser error : Entity 'intern' not defined
# <data>&intern;</data>parser error : Entity 'intern' not defined <data>&intern;</data>
######################
## testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_validation
####	                            
$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";

throws_ok {$dom = XML::LibXML->load_xml(
          location => $file,
                load_ext_dtd => 0,
				validation => 1
                );} qr/Entity 'intern' not defined/, 'testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_validation';

		
# obsolete; does not contribute any new insights
######################
## testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_expand_entities
####	                            
#$file = "../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml";
#
#throws_ok {$dom = XML::LibXML->load_xml(
#          location => $file,
#                load_ext_dtd => 0,
#expand_entities => 0
#                );} qr/Entity 'intern' not defined/, 'testInternalSubset_ExternalPEReferenceInDTD_load_ext_dtd_expand_entities'; 	



######################
## testInternalSubset_PEReferenceInDTD
####	
$file = "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;
                                                                                 
is($content, "it_works", "testInternalSubset_PEReferenceInDTD");  


######################
## testInternalSubset_PEReferenceInDTD_expand_entities
####	
$file = "../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
                expand_entities => 0
                );
$content = $dom->documentElement->textContent;
                                                                                 
is($content, "it_works", "testInternalSubset_PEReferenceInDTD_expand_entities");  


######################
## testParameterEntity_core
####	

$file = "../../xml_files_windows/xxep/parameterEntity_core.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;                                  
                                                                                
is($content, "it_works", "testParameterEntity_core");


######################
## testParameterEntity_core_expand_entities
####	

#$file = "../../xml_files_windows/xxep/parameterEntity_core.xml";
#
#$dom = XML::LibXML->load_xml(
#          location => $file,
#           expand_entities => 0
#                );
#$content = $dom->documentElement->textContent;                                  
#                                                                                
#is($content, "it_works", "testParameterEntity_core_expand_entities");



# Exception: ../../xml_files_windows/xxep/parameterEntity_core.xml:9: parser error : Entity 'all' not defined        <data>&all;</data>
######################
## testParameterEntity_core_load_ext_dtd
####	

$file = "../../xml_files_windows/xxep/parameterEntity_core.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
           load_ext_dtd => 0
			);} qr/Entity 'all' not defined/, 'testParameterEntity_core_load_ext_dtd'; 	


# Exception: I/O error : Attempt to load network entity http://127.0.0.1:5000/combine.dtd
# ../../xml_files_windows/xxep/parameterEntity_core.xml:9: parser error : Entity 'all' not defined    <data>&all;</data>                   
######################
## testParameterEntity_core_no_network
####	

$file = "../../xml_files_windows/xxep/parameterEntity_core.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
           no_network => 1
                );} qr/Entity 'all' not defined/, 'testParameterEntity_core_no_network'; 	

		

######################
## testParameterEntity_doctype
####	
$file = "../../xml_files_windows/xxep/parameterEntity_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;                                          
                                                                                
is($content, "it_works", "testParameterEntity_doctype");                                                  

######################
## testParameterEntity_doctype_expand_entities
####	
#$file = "../../xml_files_windows/xxep/parameterEntity_doctype.xml";#
#
#$dom = XML::LibXML->load_xml(
#          location => $file,
#                expand_entities => 0
#                );
#$content = $dom->documentElement->textContent;                                          
#                                                                                
#is($content, "it_works", "testParameterEntity_doctype_expand_entities");  





# Exception:  ../../xml_files_windows/xxep/parameterEntity_doctype.xml:3: parser error : Entity 'all' not defined    <data>&all;</data>
######################
## testParameterEntity_doctype_load_ext_dtd
####	
$file = "../../xml_files_windows/xxep/parameterEntity_doctype.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
                load_ext_dtd => 0
                );} qr/Entity 'all' not defined/, 'testParameterEntity_doctype_load_ext_dtd'; 	


# Exception:  I/O error : Attempt to load network entity http://127.0.0.1:5000/parameterEntity_doctype.dtd
 # ../../xml_files_windows/xxep/parameterEntity_doctype.xml:3: parser error : Entity 'all' not defined        <data>&all;</data>
######################
## testParameterEntity_doctype_no_network
####	
$file = "../../xml_files_windows/xxep/parameterEntity_doctype.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
                no_network => 1
                );} qr/Entity 'all' not defined/, 'testParameterEntity_doctype_load_ext_dtd'; 	

				
######################
## testURLInvocation_doctype
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;                                        

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_doctype");

######################
## testURLInvocation_doctype_expand_entities
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
          expand_entities => 0
                );
$content = $dom->documentElement->textContent;                                        

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_doctype_expand_entities");





######################
## testURLInvocation_doctype_load_ext_dtd
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
		  load_ext_dtd => 0                
                );
$content = $dom->documentElement->textContent;                                        

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype_load_ext_dtd");



######################
## testURLInvocation_doctype_load_ext_dtd_complete_attributes
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
		  load_ext_dtd => 0,
		  complete_attributes => 1
                );
$content = $dom->documentElement->textContent;                                        

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype_load_ext_dtd_complete_attributes");



######################
## testURLInvocation_doctype_load_ext_dtd_validation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
		  load_ext_dtd => 0,
		  validation => 1
                );
$content = $dom->documentElement->textContent;                                        

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype_load_ext_dtd_validation");













# Exception: I/O error : Attempt to load network entity http://127.0.0.1:5000/
######################
## testURLInvocation_doctype_no_network
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_doctype.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
		  no_network => 1
                );} qr/Attempt to load network entity/, 'testURLInvocation_doctype_no_network Exception'; 	


$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype_no_network");



######################
## testURLInvocation_externalGeneralEntity
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocaton_externalGeneralEntity"); 


######################
## testURLInvocaton_externalGeneralEntity_expand_entities
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           expand_entities => 0
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocaton_externalGeneralEntity_expand_entities"); 


######################
## testURLInvocation_externalGeneralEntity_expand_entities_complete_attributes
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           expand_entities => 0,
		   complete_attributes => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_externalGeneralEntity_expand_entities_complete_attributes"); 


######################
## testURLInvocation_externalGeneralEntity_expand_entities_validation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           expand_entities => 0,
		   validation => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_externalGeneralEntity_expand_entities_validation"); 


######################
## testURLInvocaton_externalGeneralEntity_load_ext_dtd
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           load_ext_dtd => 0
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocaton_externalGeneralEntity_load_ext_dtd"); 


######################
## testURLInvocation_externalGeneralEntity_load_ext_dtd_complete_attributes
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           load_ext_dtd => 0,
		   complete_attributes => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_externalGeneralEntity_load_ext_dtd_complete_attributes"); 


######################
## testURLInvocation_externalGeneralEntity_load_ext_dtd_validation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
           load_ext_dtd => 0,
		   validation => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_externalGeneralEntity_load_ext_dtd_validation");




#Exception:  I/O error : Attempt to load network entity http://127.0.0.1:5000/test.xml
 # ../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml:5: parser error : Failure to process entity remote <data>&remote;</data>                      ^
######################
## testURLInvocaton_externalGeneralEntity_no_network
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
           no_network => 1
                );} qr/Attempt to load network entity/, "testURLInvocaton_externalGeneralEntity_no_network";
				
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocaton_externalGeneralEntity_no_network"); 



######################
## testURLInvocation_parameterEntity
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_parameterEntity");  


######################
## testURLInvocation_parameterEntity_expand_entities
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
               expand_entities => 0 
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_parameterEntity_expand_entities");  


######################
## testURLInvocation_parameterEntity_load_ext_dtd
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
               load_ext_dtd => 0 
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_load_ext_dtd");  



######################
## testURLInvocation_parameterEntity_load_ext_dtd_complete_attributes
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
               load_ext_dtd => 0,
			   complete_attributes => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_load_ext_dtd_complete_attributes"); 




######################
## testURLInvocation_parameterEntity_load_ext_dtd_validation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
               load_ext_dtd => 0,
			   validation => 1
                );
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_load_ext_dtd_validation"); 








# Exception:     I/O error : Attempt to load network entity http://127.0.0.1:5000/test.dtd
######################
## testURLInvocation_parameterEntity_no_network
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
               no_network => 1
                );} qr/Attempt to load network entity/ , 'testURLInvocation_parameterEntity_no_network Exception'; 	
$content = $dom->documentElement->textContent;  

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_no_network");  


######################
## testURLInvocation_noNamespaceSchemaLocation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;                                      

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_noNamespaceSchemaLocation");



######################
## testURLInvocation_schemaLocation
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;  
                                    

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_schemaLocation");  



######################
## testURLInvocation_XInclude
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_xinclude.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;  
                                 

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_XInclude"); 


######################
## testURLInvocation_XInclude_xinclude
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_xinclude.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
                xinclude => 1
                );
$content = $dom->documentElement->textContent;  
                                 

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_XInclude_xinclude"); 


# Exception:  I/O error : Attempt to load network entity http://127.0.0.1:5000/test.xml
# ../../xml_files_windows/ssrf/url_invocation_xinclude.xml:0: XInclude error :could not load http://127.0.0.1:5000/test.xml, and no fallback was found
######################
## testURLInvocation_XInclude_xinclude_no_network
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$file = "../../xml_files_windows/ssrf/url_invocation_xinclude.xml";

throws_ok{$dom = XML::LibXML->load_xml(
          location => $file,
                xinclude => 1,
				no_network => 1
                );} qr/Attempt to load network entity/, "testURLInvocation_XInclude_xinclude_no_network Exception";
$content = $dom->documentElement->textContent;  
                                 

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_XInclude_xinclude_no_network"); 





######################
## testXInclude
####	
$file = "../../xml_files_windows/xinclude.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->firstChild->nodeName; 
                                                                              
is($content, "xi:include", "testXInclude");     


######################
## testXInclude_xinclude
####	
$file = "../../xml_files_windows/xinclude.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
                xinclude => 1
                );
				
$content = $dom->getElementsByLocalName("content");                             
                                                                              
is($content, "it_works", "testXInclude_xinclude");  

# Output: no_xinclude_nodes => 0
#<?xml version="1.0" encoding="utf-8"?>
#<data xmlns:xi="http://www.w3.org/2001/XInclude"><content xml:base="C:/Christoph
#er_Spaeth/code/xml_files_windows/xinclude_source.xml">it_works</content></data>

# Output: no_xinclude_nodes => 1
#<?xml version="1.0" encoding="utf-8"?>
#<data xmlns:xi="http://www.w3.org/2001/XInclude"><content xml:base="C:/Christoph
#er_Spaeth/code/xml_files_windows/xinclude_source.xml">it_works</content></data>

######################
## testXInclude_xinclude_no_xinclude_nodes
####	
#$file = "../../xml_files_windows/xinclude.xml";
#
#$dom = XML::LibXML->load_xml(
 #         location => $file,
 #               xinclude => 1,
#				no_xinclude_nodes => 1
 #               );
#				
#$content = $dom->getElementsByLocalName("content");                             
#is($content, "it_works", "testXInclude_xinclude_no_xinclude_nodes");  



######################
## testXSLT
####	
$file = "../../xml_files_windows/optional/xslt.xsl";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->nodeName; 
                                                                              
is($content, "xsl:stylesheet", "testXSLT");  


######################
## testXXE
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file
                # parser options ...
                );
$content = $dom->documentElement->textContent;

is($content, "it_works", "testXXE");                                                                        


######################
## testXXE_expand_entities
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            expand_entities => 0
                );
$content = $dom->documentElement->textContent;

is($content, "", "testXXE_expand_entities");  


######################
## testXXE_expand_entities_complete_attributes
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            expand_entities => 0,
			complete_attributes => 1
                );
$content = $dom->documentElement->textContent;

is($content, "", "testXXE_expand_entities_complete_attributes");  

######################
## testXXE_expand_entities_validation
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            expand_entities => 0,
			validation => 1
                );
$content = $dom->documentElement->textContent;

is($content, "it_works", "testXXE_expand_entities_validation");  



######################
## testXXE_load_ext_dtd
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            load_ext_dtd => 0
                );
$content = $dom->documentElement->textContent;

is($content, "", "testXXE_load_ext_dtd"); 

######################
## testXXE_load_ext_dtd_complete_attributes
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            load_ext_dtd => 0,
			complete_attributes => 1
                );
$content = $dom->documentElement->textContent;

is($content, "", "testXXE_load_ext_dtd_complete_attributes"); 


######################
## testXXE_load_ext_dtd_validation
####	
$file = "../../xml_files_windows/xxe/xxe.xml";

$dom = XML::LibXML->load_xml(
          location => $file,
            load_ext_dtd => 0,
			validation => 1
                );
$content = $dom->documentElement->textContent;

is($content, "", "testXXE_load_ext_dtd_validation"); 

