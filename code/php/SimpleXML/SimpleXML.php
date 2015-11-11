<?php						
$xml = simplexml_load_file("../../xml_files_windows/standard.xml");
$name = $xml->getName();
$content = $xml->__toString();
echo $name;
echo $content;                                  

?>
