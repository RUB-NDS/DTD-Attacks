#!/usr/bin/perl
use XML::Twig;

my $t= XML::Twig->new();
$t->parsefile("../../xml_files_windows/standard.xml");
my $root = $t->root;
print $root->tag;
print $root->first_child->text;
