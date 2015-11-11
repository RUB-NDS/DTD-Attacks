use diagnostics; # this gives you more debugging information                    
use warnings;    # this warns you of bad practices                              
use strict;      # this prevents silly errors 
use XML::Twig; 

require LWP::UserAgent;

use Test::More tests => 50;                                                      
use Test::Exception;


our $URL = "http://127.0.0.1:5000";

# variables used in test are declared here
my $t = "";
my $root ="";
 my $content ="";

my $expected_count = 0;
my $real_count = 0; 
 
my $ua = "";
my $request = "";
my $response = "";


# XML::Twig (Expat?) does neither handle file:/// protocol nor http://
# Most exceptions probably stem from this fact
# Workaround for file:/// - remove it and only provide the path to the resource

 
######################
## testDefault_noAttack
####
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/standard.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "4", "testDefault_noAttack");                                      
    
#print $XML::Parser::VERSION;
#print $XML::Twig::VERSION;

#####################
#testDOS_core                                                    
#####
$t= XML::Twig->new(); 
$t->parsefile('../../xml_files_windows/dos_core.xml');                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 25;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_core");   


#####################
#testDOS_core_NoExpand                                                
#####
$t= XML::Twig->new();    
$t->parsefile('../../xml_files_windows/dos_core.xml', NoExpand => 1);                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 0;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_core_NoExpand");   


######################
## testDOS_entitySize
####
$t= XML::Twig->new();    
$t->parsefile('../../xml_files_windows/dos_entitySize.xml');                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 3400000;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_entitySize");         


######################
## testDOS_entitySize_NoExpand
####
$t= XML::Twig->new(); 
$t->parsefile('../../xml_files_windows/dos_entitySize.xml', NoExpand => 1);                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 0;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_entitySize_NoExpand");  


######################
## testDOS_indirections
####
$t= XML::Twig->new(); 
$t->parsefile('../../xml_files_windows/dos_indirections.xml');                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 10000;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_indirections");   
    
	
######################
## testDOS_indirections_NoExpand
####
$t= XML::Twig->new(); 
$t->parsefile('../../xml_files_windows/dos_indirections.xml', NoExpand => 1);                                  
$root = $t->root;                                                               
$content = $root->first_child->text;                                            
$expected_count = 0;                                                        
$real_count = 0;                                                             
while ($content =~ /dos/g) { $real_count++ }                                    
                                                                                
is($real_count, $expected_count, "testDOS_indirections_NoExpand"); 
	
	

######################
## testDOS_indirections_parameterEntity
####
$t= XML::Twig->new();

throws_ok{$t->parsefile('../../xml_files_windows/optional/dos_indirections_parameterEntity.xml');}
				qr/illegal parameter entity reference/, "testDOS_indirections_parameterEntity";
				


######################
## testDOS_recursion
####
$t= XML::Twig->new(); 


$t= XML::Twig->new();                                                        
throws_ok{$t->parsefile('../../xml_files_windows/optional/dos_recursion.xml');}
				qr/recursive entity reference/, "testDOS_recursion";


######################
## testInternalSubset_ExternalPEReferenceInDTD
####	
$t= XML::Twig->new();                                                   
$t->parsefile('../../xml_files_windows/twig/internalSubset_ExternalPEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&intern;", "testInternalSubset_ExternalPEReferenceInDTD");  


######################
## testInternalSubset_ExternalPEReferenceInDTD_load_DTD
####	
$t= XML::Twig->new(load_DTD => 1);                                                   
$t->parsefile('../../xml_files_windows/twig/internalSubset_ExternalPEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&intern;", "testInternalSubset_ExternalPEReferenceInDTD_load_DTD"); 



#Exception is raised with file:/// protocol: 
#cannot expand %external; - cannot load 'file:///C:/Christopher_Spaeth/co
#de/xml_files_windows/internalSubset_ExternalPEReferenceInDTD.dtd' at C:/Strawber
#ry/perl/vendor/lib/XML/Parser/Expat.pm line 474.
######################
## testInternalSubset_ExternalPEReferenceInDTD_ParseParamEnt
####	
$t= XML::Twig->new(ParseParamEnt => 1);                                         			
$t->parsefile('../../xml_files_windows/twig/internalSubset_ExternalPEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                            
is($content, "it_works", "testInternalSubset_ExternalPEReferenceInDTD_ParseParamEnt");  

######################
## testInternalSubset_ExternalPEReferenceInDTD_ParseParamEnt_NoExpand
####	
$t= XML::Twig->new(ParseParamEnt => 1, NoExpand => 1);                                         			
$t->parsefile('../../xml_files_windows/twig/internalSubset_ExternalPEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                            
is($content, "&intern;", "testInternalSubset_ExternalPEReferenceInDTD_ParseParamEnt");  

			
######################
## testInternalSubset_PEReferenceInDTD
####	
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/internalSubset_PEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&intern;", "testInternalSubset_PEReferenceInDTD");  


######################
## testInternalSubset_PEReferenceInDTD_load_DTD
####	
$t= XML::Twig->new(load_DTD => 1);                                                        
$t->parsefile('../../xml_files_windows/internalSubset_PEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&intern;", "testInternalSubset_PEReferenceInDTD_load_DTD");  


######################
## testInternalSubset_PEReferenceInDTD_ParseParamEnt
####	
$t= XML::Twig->new(ParseParamEnt => 1);                                                        
$t->parsefile('../../xml_files_windows/internalSubset_PEReferenceInDTD.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "it_works", "testInternalSubset_PEReferenceInDTD_ParseParamEnt");  


######################
## testInternalSubset_PEReferenceInDTD_ParseParamEnt_NoExpand
####	
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/internalSubset_PEReferenceInDTD.xml', ParseParamEnt => 1, NoExpand => 1);                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&intern;", "testInternalSubset_PEReferenceInDTD_ParseParamEnt_NoExpand");  


######################
## testParameterEntity_core
####	
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/parameterEntity_core.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&all;", "testParameterEntity_core"); 


# Exception:  cannot expand %dtd; - cannot load 'http://127.0.0.1:5000/combine.dtd' at
#at C:/Strawberry/perl/vendor/lib/XML/Parser/Expat.pm line 474.
######################
## testParameterEntity_core_ParseParamEnt
####	
$t= XML::Twig->new();                                                        
throws_ok{$t->parsefile('../../xml_files_windows/parameterEntity_core.xml', 
				ParseParamEnt => 1 );} qr/cannot expand %dtd/, "testParameterEntity_core_ParseParamEnt";

# tried every option; still not working;
#$t= XML::Twig->new(load_DTD=>1);                                                        
#$t->parsefile('../../xml_files_windows/parameterEntity_core.xml', ParseParamEnt => 1, load_DTD =>1, NoExpand => 0); 
#$root = $t->root;                                                            
#$content = $root->first_child->text;                                                                                                                         
#is($content, "&all;", "testParameterEntity_core"); 


######################
## testParameterEntity_doctype
####	
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/parameterEntity_doctype.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         
                                                                                
is($content, "&all;", "testParameterEntity_doctype");


#Uncaught exception from user code:
#
#        illegal parameter entity reference at line 1, column 194, byte 194:
#        <!DOCTYPE data [<!ELEMENT data (#PCDATA)><!ENTITY % start "<![CDATA["><!ENTITY % goodies SYSTEM "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt"><!ENTITY % end "]]>"><!ENTITY all '%start;%goodies;%end;'>]><data/>
#        =================================================================================================================================================================================================^
 #        at C:/Strawberry/perl/vendor/lib/XML/Parser.pm line 187.
  #       at C:/Strawberry/perl/vendor/lib/XML/Parser/Expat.pm line 474.
 #        at testXml_twig.t line 262.
 #        at testXml_twig.t line 262.
 #       XML::Twig::_croak("\x{a}illegal parameter entity reference at line 1, column 194, by"..., 0) called at #C:/Strawberry/perl/vendor/lib/XML/Twig.pm line 795
#        XML::Twig::_checked_parse_result(undef, "\x{a}illegal parameter entity reference at line 1, column 194, by"...) called at C:/Strawberry/perl/vendor/lib/XML/Twig.pm line 783
#        XML::Twig::parsefile(XML::Twig=HASH(0x3a1b4f0), "../../xml_files_windows/parameterEntity_doctype.xml") called at testXml_twig.t line 262
# Looks like you planned 28 tests but ran 18.
# Looks like your test exited with 255 just after 18.

######################
## testParameterEntity_doctype_load_DTD
####	
$t= XML::Twig->new(load_DTD => 1);   

throws_ok{$t->parsefile('../../xml_files_windows/parameterEntity_doctype.xml');}
			qr/illegal parameter entity reference/, "testParameterEntity_doctype_load_DTD";

			
#        illegal parameter entity reference at line 1, column 194, byte 194:
#        <!DOCTYPE data [<!ELEMENT data (#PCDATA)><!ENTITY % start "<![CDATA["><!ENTITY % goodies SYSTEM "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt"><!ENTITY % end "]]>"><!ENTITY all '%start;%goodies;%end;'>]><data/>	
######################
## testParameterEntity_doctype_load_DTD_ParseParamEnt
####	
$t= XML::Twig->new(load_DTD => 1, ParseParamEnt => 1);   

throws_ok{$t->parsefile('../../xml_files_windows/parameterEntity_doctype.xml');}
			qr/illegal parameter entity reference/, "testParameterEntity_doctype_load_DTD_ParseParamEnt";

######################
## testParameterEntity_doctype_ParseParamEnt
####	
$t= XML::Twig->new(ParseParamEnt => 1);          
throws_ok{$t->parsefile('../../xml_files_windows/parameterEntity_doctype.xml');}
			qr/cannot expand >/, "testParameterEntity_doctype_ParseParamEnt";
                                             
# tried every option; still not working; load_DTD =>1, NoExpand => 0
#$t= XML::Twig->new(load_DTD=>1);                                                        
#$t->parsefile('../../xml_files_windows/parameterEntity_core.xml', ParseParamEnt => 1); 
#$root = $t->root;                                                            
#$content = $root->first_child->text;                                                                                                                         
#is($content, "&all;", "testParameterEntity_core");                                                                           
														   
																		   
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

$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/url_invocation_doctype.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype");                                                                               




######################
## testURLInvocation_doctype_load_DTD
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$t= XML::Twig->new(load_DTD => 1);                                                        
$t->parsefile('../../xml_files_windows/url_invocation_doctype.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "1", "testURLInvocation_doctype_load_DTD");    


######################
## testURLInvocation_doctype_ParseParamEnt
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$t= XML::Twig->new(ParseParamEnt => 1);                                    
throws_ok { $t->parsefile('../../xml_files_windows/url_invocation_externalGeneralEntity.xml') } qr/cannot expand &remote;/, 'testURLInvocation_doctype_ParseParamEnt Exception';
                                

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_doctype_ParseParamEnt");




# Exception: cannot expand &remote; - cannot load 'http://127.0.0.1:5000/test.xml'
######################
## testURLInvocaton_externalGeneralEntity
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$t= XML::Twig->new();                                                        
throws_ok { $t->parsefile('../../xml_files_windows/url_invocation_externalGeneralEntity.xml') } qr/cannot expand &remote;/, 'testURLInvocaton_externalGeneralEntity Exception';

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocaton_externalGeneralEntity"); 
                                                                    




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

$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/url_invocation_parameterEntity.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity");  


######################
## testURLInvocation_parameterEntity_load_DTD
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$t= XML::Twig->new(load_DTD => 1);                                                        
$t->parsefile('../../xml_files_windows/url_invocation_parameterEntity.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text; 


$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_load_DTD"); 







# Exception:         cannot expand %remote; - cannot load 'http://127.0.0.1:5000/test.dtd' at
 # C:/Strawberry/perl/vendor/lib/XML/Parser/Expat.pm line 474.
######################
## testURLInvocation_parameterEntity_ParseParamEnt
####	

#reset the counter
$ua = LWP::UserAgent->new;
$request = $ua->get($URL.'/reset');
$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "counter reset");

$t= XML::Twig->new(ParseParamEnt => 1);                                                        
throws_ok{$t->parsefile('../../xml_files_windows/url_invocation_parameterEntity.xml');} qr/cannot expand %remote/, "testURLInvocation_parameterEntity_ParseParamEnt Exception";

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_parameterEntity_ParseParamEnt"); 







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

$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/url_invocation_xinclude.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_XInclude"); 


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

$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

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

$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/url_invocation_schemaLocation.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                         

$request = $ua->get($URL.'/getCounter');
$response = $request->decoded_content;
#remove whitespaces
$response =~ s/^\s*(.*?)\s*$/$1/;
is($response, "0", "testURLInvocation_schemaLocation");  












######################
## testXInclude
####	
$t= XML::Twig->new();                                                        
$t->parsefile('../../xml_files_windows/xinclude.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->name;                                       
                                                                              
is($content, "xi:include", "testXInclude");                                         
     
	 
# File protocol file:/// not supported
# Uncaught exception from user code:
       #cannot expand &file; - cannot load 'file:///C:/Christopher_Spaeth/code/x
#ml_files_windows/xxe.txt' at C:/Strawberry/perl/vendor/lib/XML/Parser/Expat.pm l
#ine 474.
#         at testXml_twig.t line 496.
#         at testXml_twig.t line 496.
 #       XML::Twig::_croak("cannot expand \x{26}file; - cannot load 'file:///C:/C
#hristopher_Sp"..., 0) called at C:/Strawberry/perl/vendor/lib/XML/Twig.pm line 7
#95
#        XML::Twig::_checked_parse_result(undef, "cannot expand \x{26}file; - can
#not load 'file:///C:/Christopher_Sp"...) called at C:/Strawberry/perl/vendor/lib
#/XML/Twig.pm line 783
#        XML::Twig::parsefile(XML::Twig=HASH(0x4062990), "../../xml_files_windows
#/xxe.xml") called at testXml_twig.t line 496

######################
## testXXE
####
$t= XML::Twig->new();                                                        

$t->parsefile('../../xml_files_windows/twig/xxe.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                       
                                                                              
is($content, "it_works", "testXXE");          



######################
## testXXE_load_DTD
####
$t= XML::Twig->new(load_DTD => 0);                                                        
$t->parsefile('../../xml_files_windows/twig/xxe.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                       
                                                                              
is($content, "it_works", "testXXE_load_DTD");          




######################
## testXXE_NoExpand
####	
$t= XML::Twig->new(NoExpand => 1);                                                        
$t->parsefile('../../xml_files_windows/twig/xxe.xml');                                  
$root = $t->root;                                                            
$content = $root->first_child->text;                                       
                                                                              
is($content, "&file;", "testXXE_NoExpand");   	   


######################
## testXXE_expand_external_ents
####	
#$t= XML::Twig->new();                                                        
#$t->parsefile('../../xml_files_windows/twig/xxe.xml', expand_external_ents => 0);                                  
#$root = $t->root;                                                            
#$content = $root->first_child->text;                                       
#                                                                              
#is($content, "it_works", "testXXE_expand_external_ents");  

