#!/usr/bin/perl -w
use strict;
use XML::Parser;

my $parser = new XML::Parser();
$parser->parsefile("../../xml_files_windows/parameterEntity_doctype.xml");