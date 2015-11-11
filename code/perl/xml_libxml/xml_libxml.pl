#!/usr/bin/perl

use diagnostics; # this gives you more debugging information   
use strict;      # this prevents silly errors 
use warnings;    # this warns you of bad practices  
use XML::LibXML; 

my $file = "../../xml_files_windows/standard.xml";
my $parser = XML::LibXML->new();
my $dom = XML::LibXML->load_xml(location => $file);
print $dom->documentElement->tagName;
print $dom->documentElement->textContent;


# Set a feature
#my $file = "../../xml_files_windows/dos_indirections.xml";
#my $parser = XML::LibXML->new();
#my $dom = XML::LibXML->load_xml(location => $file, huge => 1);
#print $dom->documentElement->tagName;
#print $dom->documentElement->textContent;


# Set a feature - this is not working
#my $file = "../../xml_files_windows/dos_indirections.xml";
#my $parser = XML::LibXML->new(huge => 1);
#print "Huge " . $parser->get_option("huge"); 
#my $dom = XML::LibXML->load_xml(location => $file);
#print $dom->documentElement->tagName;
#print $dom->documentElement->textContent;

