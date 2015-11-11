<?php
$xml = new DOMDocument();
$xml->load("../../xml_files_windows/standard.xml");
$data = $xml->getElementsByTagName("data");
echo $data->item(0)->nodeValue;
?>
