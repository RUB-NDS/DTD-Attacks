<?php

$xml = new XMLReader();

$xml->open("../../xml_files_windows/standard.xml",			
		$encoding = "UTF-8",
		$options=LIBXML_NOENT);

while($xml->read()) {
  if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
     print $xml->name;
     print $xml->readString();
   }
}

/*
while($xml->read()) {
	if ($xml->nodeType == XMLReader::ELEMENT) {
		print $xml->name;
		print $xml->readString();
	}
}
*/


?>	