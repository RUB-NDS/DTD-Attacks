<?php

class TestXMLReader extends PHPUnit_Framework_TestCase {
	
	public function setUp() {
		libxml_disable_entity_loader(false);
	}
	
	public function tearDown() {
	
	}
	
	public function testDefault_noAttack() {
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/standard.xml");
		
		
		$this->assertFalse($xml->getParserProperty(XMLReader::DEFAULTATTRS));
		$this->assertFalse($xml->getParserProperty(XMLReader::LOADDTD));
		$this->assertFalse($xml->getParserProperty(XMLReader::VALIDATE));
		$this->assertFalse($xml->getParserProperty(XMLReader::SUBST_ENTITIES));
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$this->assertEquals("4", $content);
	}
	
	  
	
	
	public function testDOS_core() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_core.xml");
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}		
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);

	}
	

	public function testDOS_core_LIBXML_NOENT() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_core.xml",
					$encoding="UTF-8",
					$options = LIBXML_NOENT);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
				
			}
		}
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);

	}

 
	public function testDOS_core_setParserProperty_SUBST_ENTITIES () {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/dos_core.xml");				
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
		
			}
		}
		
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
		
		
	}
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): Entity: line 1: parser error : Detected an entity reference loop
	 */
	public function testDOS_indirections() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_indirections.xml");
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	/**
	@expectedException PHPUnit_Framework_Error
	XMLReader::read(): Entity: line 1: parser error : Detected an entity reference loop
	*/

	public function testDOS_indirections_LIBXML_NOENT() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_indirections.xml",
				$encoding="UTF-8",
				$options = LIBXML_NOENT);
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	public function testDOS_indirections_LIBXML_NOENT_PARSEHUGE() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_indirections.xml",
				$encoding="UTF-8",
				$options = LIBXML_NOENT|LIBXML_PARSEHUGE);
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
		$expectedCount = 10000;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
		
	public function testDOS_indirections_LIBXML_PARSEHUGE() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_indirections.xml",
				$encoding="UTF-8",
				$options = LIBXML_PARSEHUGE);
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): Entity: line 1: parser error : Detected an entity reference loop
	 */
	public function testDOS_indirections_setParserProperty_SUBST_ENTITIES () {
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/dos_indirections.xml");
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
	
	}	
	

	public function testDOS_indirections_setParserProperty_SUBST_ENTITIES_LIBXML_PARSEHUGE () {
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/dos_indirections.xml",
				$encoding="UTF-8",
				$options = LIBXML_PARSEHUGE);
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
	
		$expectedCount = 10000;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	}
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	parser error : PEReferences forbidden in internal subset
	*/	
	public function testDOS_indirections_parameterEntity() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/optional/dos_indirections_parameterEntity.xml");
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	
		
	
	
	
	public function testDOS_entitySize() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_entitySize.xml");
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	
	
	public function testDOS_entitySize_LIBXML_NOENT() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/dos_entitySize.xml",
				$encoding="UTF-8",
				$options = LIBXML_NOENT);
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
		
	
	public function testDOS_entitySize_setParserProperty_SUBST_ENTITIES () {
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/dos_entitySize.xml");
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
	
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	
	}
	
	/*
	public function testDOS_entitySize_setParserProperty_SUBST_ENTITIES_LIBXML_PARSEHUGE () {
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/dos_entitySize.xml",
				$encoding="UTF-8",
				$options = LIBXML_PARSEHUGE);
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
	
			}
		}
	
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	}
	*/
	
	
	/**
	* @expectedException PHPUnit_Framework_Error
	*XMLReader::read(): Entity: line 1: parser error : Detected an entity reference loop
	*/

	public function testDOS_recursion() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/optional/dos_recursion.xml");
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	
		$expectedCount = 0;
		$realCount = substr_count($content, 'dos');
		$this->assertEquals($expectedCount, $realCount);
	
	}
	
	
	

	
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error 
	 * XMLReader::read(): /xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.xml:6: parser error : Entity 'intern' not defined
	 */
	public function testInternalSubset_ExternalPEReferenceInDTD() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);		
	}
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDATTR() {

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDATTR);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);	
		
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDLOAD() {

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);	
		
	}
	
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDVALID() {

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);	
		
	}
	
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_NOENT() {

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);	
		
	}

	
	
	
	

	/**
	*@expectedException PHPUnit_Framework_Error 
	* I/O warning : failed to load external entity "file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.dtd"	
	*/
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_NOENT_disable_entity_loader() {
		
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
	}
	
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	Entity 'intern' not defined
	*/
	public function testInternalSubset_ExternalPEReferenceInDTD_setParserProperty_LOADDTD() {
		
		$xml = new XMLReader();		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	
	/**
	* @expectedException PHPUnit_Framework_Error
	Entity 'intern' not defined
	*/
	public function testInternalSubset_ExternalPEReferenceInDTD_setParserProperty_DEFAULTATTRS() {
		
		$xml = new XMLReader();		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("i_works", $content);
		
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_setParserProperty_VALIDATE() {
		
		$xml = new XMLReader();		
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
		
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_setParserProperty_SUBST_ENTITIES() {
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}

	/**
	*@expectedException PHPUnit_Framework_Error 
	*XMLReader::read(): internalSubset_ExternalPEReferenceInDTD.xml:4: 
	*I/O warning : failed to load external entity "/xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.dtd"
	**/
	public function testInternalSubset_ExternalPEReferenceInDTD_setParserProperty_SUBST_ENTITIES_disable_entity_loader() {
	
	$xml = new XMLReader();
	$xml->open("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml");
	
	libxml_disable_entity_loader(true);
	$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
	while($xml->read()) {
		if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
			$node = $xml->name;
			$content = $xml->readString();
		}
	}

	
}
	
	
	

	public function testInternalSubset_PEReferenceInDTD() {
		$xml = new XMLReader();			
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testInternalSubset_PEReferenceInDTD_LIBXML_DTDATTR() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDATTR);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	
	public function testInternalSubset_PEReferenceInDTD_LIBXML_DTDLOAD() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testInternalSubset_PEReferenceInDTD_LIBXML_DTDVALID() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testInternalSubset_PEReferenceInDTD_LIBXML_NOENT() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	

	

	
	
	public function testInternalSubset_PEReferenceInDTD_setParserProperty_LOADDTD() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	public function testInternalSubset_PEReferenceInDTD_setParserProperty_DEFAULTATTRS() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	public function testInternalSubset_PEReferenceInDTD_setParserProperty_VALIDATE() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	public function testInternalSubset_PEReferenceInDTD_setParserProperty_SUBST_ENTITIES() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml");
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
	}
	
	
	
	
			/**
	 * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity.xml:9: parser error : Entity 'all' not defined
	 */
	public function testParameterEntity_core() {
		
		$xml = new XMLReader();		

		$xml->open("../../xml_files_windows/parameterEntity_core.xml");	
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}

		
	}
	
	public function testParameterEntity_core_LIBXML_DTDATTR() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDATTR);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	
	public function testParameterEntity_core_LIBXML_DTDLOAD() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	public function testParameterEntity_core_LIBXML_DTDVALID() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	public function testParameterEntity_core_LIBXML_NOENT() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
	}

    /**
     * @expectedException PHPUnit_Framework_Error
     * XMLReader::read(): Attempt to load network entity 
     * http://127.0.0.1:5000/combine.dtd/home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity.xml:9: 
     * parser error : Entity 'all' not defined
     */
    public function testParameterEntity_core_LIBXML_NOENT_NONET() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT|LIBXML_NONET);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	}


	/**
	 * @expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/parameterEntity_core.dtd"
	*/	
	public function testParameterEntity_core_LIBXML_NOENT_disable_entity_loader() {
	
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	}
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	parser error : Entity 'all' not defined
	*/
	public function testParameterEntity_core_setParserProperty_LOADDTD(){
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_core.xml");
		//use setParserProperty
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		
	}
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	parser error : Entity 'all' not defined
	*/
	public function testParameterEntity_core_setParserProperty_DEFAULTATTRS(){
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_core.xml");
		//use setParserProperty
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
	}
	
	public function testParameterEntity_core_setParserProperty_VALIDATE(){
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_core.xml");
		//use setParserProperty
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	
	public function testParameterEntity_core_setParserProperty_SUBST_ENTITIES(){
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_core.xml");
		//use setParserProperty
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}

	/**
	 * @expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/parameterEntity_core.dtd"
	*/	
	public function testParameterEntity_core_setParserProperty_SUBST_ENTITIES_disable_entity_loader(){
	
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_core.xml");

		libxml_disable_entity_loader(true);
		//use setParserProperty
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}

    /**
 *@expectedException PHPUnit_Framework_Error
 *XMLReader::read(): Attempt to load network entity http://127.0.0.1:5000/combine.dtd/home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity.xml:9: parser error : Entity 'all' not defined
 */
    public function testParameterEntity_core_setParserProperty_SUBST_ENTITIES_LIBXML_NONET(){
		
		$xml = new XMLReader();

		$xml->open("../../xml_files_windows/parameterEntity_core.xml",
					$encoding="UTF-8",
					$options=LIBXML_NONET);
	
		//use setParserProperty
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}

		
	}
	
	
	
	
/**
	 * @expectedException PHPUnit_Framework_Error 
	 * XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity_doctype.xml:3: parser error : Entity 'all' not defined
	 */
	public function testParameterEntity_doctype() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml");	
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testParameterEntity_doctype_LIBXML_DTDATTR() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDATTR);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	
	public function testParameterEntity_doctype_LIBXML_DTDATTR_NOENT() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDATTR|LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error 
	 Entity 'all' not defined
	 */
	public function testParameterEntity_doctype_LIBXML_DTDATTR_NOENT_NONET() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDATTR|LIBXML_NOENT|LIBXML_NONET);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}

/**
	* @expectedException PHPUnit_Framework_Error 
	I/O warning : failed to load external entity "http://127.0.0.1:5000/parameterEntity_doctype.dtd"
	**/
	public function testParameterEntity_doctype_LIBXML_DTDATTR_NOENT_disable_entity_loader() {

	$xml = new XMLReader();			

	$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
					$encoding="UTF-8",
				$options=LIBXML_DTDATTR|LIBXML_NOENT);
	
	libxml_disable_entity_loader(true);

	while($xml->read()) {
		if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
			$node = $xml->name;
			$content = $xml->readString();
		}
	}

	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	public function testParameterEntity_doctype_LIBXML_DTDLOAD() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}

	public function testParameterEntity_doctype_LIBXML_DTDLOAD_NOENT() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDLOAD|LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error 
	 Entity 'all' not defined
	 */
	public function testParameterEntity_doctype_LIBXML_DTDLOAD_NOENT_NONET() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDLOAD|LIBXML_NOENT|LIBXML_NONET);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}

/**
	* @expectedException PHPUnit_Framework_Error 
	I/O warning : failed to load external entity "http://127.0.0.1:5000/parameterEntity_doctype.dtd"
	**/
	public function testParameterEntity_doctype_LIBXML_DTDLOAD_NOENT_disable_entity_loader() {

	$xml = new XMLReader();			

	$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
					$encoding="UTF-8",
				$options=LIBXML_DTDLOAD|LIBXML_NOENT);
	
	libxml_disable_entity_loader(true);

	while($xml->read()) {
		if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
			$node = $xml->name;
			$content = $xml->readString();
		}
	}
	// $content = preg_replace('/\s+/', '', $content);
	
	// $this->assertEquals("it_works", $content);
	
	}
	
	
	public function testParameterEntity_doctype_LIBXML_DTDVALID() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDVALID);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testParameterEntity_doctype_LIBXML_DTDVALID_NOENT() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDVALID|LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	/**
	 @expectedException PHPUnit_Framework_Error 
	Validation failed: no DTD found !
	*/
	public function testParameterEntity_doctype_LIBXML_DTDVALID_NOENT_NONET() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDVALID|LIBXML_NOENT|LIBXML_NONET);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	
	/**
	* @expectedException PHPUnit_Framework_Error 
	I/O error : failed to load external entity "http://127.0.0.1:5000/parameterEntity_doctype.dtd"
	*/
	public function testParameterEntity_doctype_LIBXML_DTDVALID_NOENT_disable_entity_loader() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_DTDVALID|LIBXML_NOENT);
		libxml_disable_entity_loader(true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		
	}
	
	
	

 /**
     * XMLReader::read(): 
     * /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity_doctype.xml:3: 
     * parser error : Entity 'all' not defined
     */
    /**
	 * @expectedException PHPUnit_Framework_Error 
	 */
    public function testParameterEntity_doctype_LIBXML_NOENT() {
		$xml = new XMLReader();			

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
    }		


    /**
	 * @expectedException PHPUnit_Framework_Error 
	 *XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity_doctype.xml:3: 
 	* parser error : Entity 'all' not defined
	 */        
	public function testParameterEntity_doctype_setParserProperty_SUBST_ENTITIES(){
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml");
		//use setParserProperty
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		
	}
	



	



    public function testParameterEntity_doctype_setParserProperty_LOADDTD() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml");
		//use setParserProperty
        $xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
    }


    public function testParameterEntity_doctype_setParserProperty_LOADDTD_LIBXML_NOENT() {

        $xml = new XMLReader();

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
	

		//use setParserProperty
        $xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
	
    }
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	parser error : Entity 'all' not defined
	*/
	 public function testParameterEntity_doctype_setParserProperty_LOADDTD_LIBXML_NOENT_NONET() {

        $xml = new XMLReader();

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT|LIBXML_NONET);
	

		//use setParserProperty
        $xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
	
    }

   


	/**
	* @expectedException PHPUnit_Framework_Error 
	*XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity_doctype.xml:2: I/O warning : failed to load external entity "file:///home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/paramEntity_doctype.dtd
	**/
 	public function testParameterEntity_doctype_setParserProperty_LOADDTD_LIBXML_NOENT_disable_entity_loader() {

        $xml = new XMLReader();

		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
	

		libxml_disable_entity_loader(true);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}	
    }
	
	public function testParameterEntity_doctype_setParserProperty_DEFAULTATTRS() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml");
		//use setParserProperty
        $xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
    }
	
	public function testParameterEntity_doctype_setParserProperty_DEFAULTATTRS_LIBXML_NOENT() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }
	
	/**
	* @expectedException PHPUnit_Framework_Error 
	 parser error : Entity 'all' not defined
	 */
	public function testParameterEntity_doctype_setParserProperty_DEFAULTATTRS_LIBXML_NOENT_NONET() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT|LIBXML_NONET);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }
	
	/**
		* @expectedException PHPUnit_Framework_Error 
	 failed to load external entity "http://127.0.0.1:5000/parameterEntity_doctype.dtd"
	*/
	public function testParameterEntity_doctype_setParserProperty_DEFAULTATTRS_LIBXML_NOENT_disable_entity_loader() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		libxml_disable_entity_loader(true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }
	
	

	
	
	
		 
	 public function testParameterEntity_doctype_setParserProperty_VALIDATE() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml");
		//use setParserProperty
        $xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
    }
	
	public function testParameterEntity_doctype_setParserProperty_VALIDATE_LIBXML_NOENT() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }
	
	/**
	* @expectedException PHPUnit_Framework_Error 
	Validation failed: no DTD found !
	*/
	public function testParameterEntity_doctype_setParserProperty_VALIDATE_LIBXML_NOENT_NONET() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT|LIBXML_NONET);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }

	
	/**
	* @expectedException PHPUnit_Framework_Error 
	 I/O error : failed to load external entity "http://127.0.0.1:5000/parameterEntity_doctype.dtd"
	 */
	public function testParameterEntity_doctype_setParserProperty_VALIDATE_LIBXML_NOENT_disable_entity_loader() {
       
        $xml = new XMLReader();
		$xml->open("../../xml_files_windows/parameterEntity_doctype.xml",
						$encoding="UTF-8",
					$options=LIBXML_NOENT);
		//use setParserProperty
        $xml->setParserProperty(XMLReader::VALIDATE, true);
		
		libxml_disable_entity_loader(true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
    }
	
	
	
	
	
	
	
	
	



	
	

 	public function testURLInvocation_doctype() {

 		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
 		
 		$xml = new XMLReader();
 		
 		$xml->open("../../xml_files_windows/url_invocation_doctype.xml"); 				
 		
 		while($xml->read()) {
 			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
 				$node = $xml->name;
 				$content = $xml->readString();
 			}
 		}
 		
 		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
 		$r = preg_replace('/\s+/', '', $r);
 		$this->assertEquals("0", $r);
	 }
	 
	 
	public function testURLInvocation_doctype_LIBXML_DTDATTR() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDATTR);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}	


	public function testURLInvocation_doctype_LIBXML_DTDATTR_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
		
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	 I/O warning : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_LIBXML_DTDATTR_disable_entity_loader() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDATTR);

		libxml_disable_entity_loader(true);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
	}	
	
 
	public function testURLInvocation_doctype_LIBXML_DTDLOAD() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}	
	
	public function testURLInvocation_doctype_LIBXML_DTDLOAD_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
		
	}
	

	/**
	*@expectedException PHPUnit_Framework_Error
	*	XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml:2: I/O warning : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_LIBXML_DTDLOAD_disable_entity_loader() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDLOAD);

		libxml_disable_entity_loader(true);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
	}	
	
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}	
	
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID_NONET() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}	
	
	
	/**
	*@expectedException PHPUnit_Framework_Error
	I/O error : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_LIBXML_DTDVALID_disable_entity_loader() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_DTDVALID);

		libxml_disable_entity_loader(true);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
	}	
	
	
	

	
	
	public function testURLInvocation_doctype_LIBXML_NOENT() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
					$encoding="UTF-8",
					$options=LIBXML_NOENT);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	public function testURLInvocation_doctype_setParserProperty_LOADDTD() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		//set LOADDTD
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::LOADDTD));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}

	/**
	*@expectedException PHPUnit_Framework_Error
	*XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml:2: I/O warning : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_setParserProperty_LOADDTD_disable_entity_loader() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		libxml_disable_entity_loader(true);

		//set LOADDTD
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::LOADDTD));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
		
	}
	
	
	
	

	
	public function testURLInvocation_doctype_setParserProperty_LOADDTD_LIBXML_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();

		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);
		
		//set LOADDTD		
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::LOADDTD));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	
	public function testURLInvocation_doctype_setParserProperty_DEFAULTATTRS() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::DEFAULTATTRS));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_doctype_setParserProperty_DEFAULTATTRS_LIBXML_NONET() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);

		
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::DEFAULTATTRS));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	

	/**
	*@expectedException PHPUnit_Framework_Error
	*XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml:2: I/O warning : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_setParserProperty_DEFAULTATTRS_disable_entity_loader() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		libxml_disable_entity_loader(true);

		//set LOADDTD
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::DEFAULTATTRS));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
		
	}	
	
	
	
	
	
	
	
	public function testURLInvocation_doctype_setParserProperty_VALIDATE() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::VALIDATE));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	
	public function testURLInvocation_doctype_setParserProperty_VALIDATE_LIBXML_NONET() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);

		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::VALIDATE));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	

	/**
	*@expectedException PHPUnit_Framework_Error
	*XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_doctype.xml:2: I/O warning : failed to load external entity "http://127.0.0.1:5000/"
	*/
	public function testURLInvocation_doctype_setParserProperty_VALIDATE_disable_entity_loader() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		libxml_disable_entity_loader(true);

		//set LOADDTD
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::VALIDATE));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		/* this check is not working!
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		*/
		
	}
	
	public function testURLInvocation_doctype_setParserProperty_SUBST_ENTITIES() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_doctype.xml");

		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		$this->assertTrue($xml->getParserProperty(XMLReader::SUBST_ENTITIES));
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	
	
	
	
	
	
	public function testURLInvocation_externalGeneralEntity() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
			
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDATTR() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDLOAD() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	Failure to process entity remote
	*/
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	
	/**
	*@expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/file.xml"
	*/
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID);
			
		libxml_disable_entity_loader(true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	    */
	}
	
	
	
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): Attempt to load network entity http://127.0.0.1:5000/test.xml/home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml:5: parser error : Failure to process entity remote
	 */
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	
	/**
	 *@expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): Attempt to load network entity http://127.0.0.1:5000/Attempt to load network entity http://127.0.0.1:5000//home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml:4: I/O warning : failed to load external entity "http://127.0.0.1:5000/test.dtd"
	 */
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
			
		libxml_disable_entity_loader(true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}
	
	public function testURLInvocation_externalGeneralEntity_setParserProperty_LOADDTD() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		$xml->setParserProperty(XMLReader::LOADDTD, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	
	public function testURLInvocation_externalGeneralEntity_setParserProperty_DEFAULTATTRS() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_externalGeneralEntity_setParserProperty_VALIDATE() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		$xml->setParserProperty(XMLReader::VALIDATE, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}	
	/**
		 *@expectedException PHPUnit_Framework_Error
	 parser error : Failure to process entity remote
	 */
	public function testURLInvocation_externalGeneralEntity_setParserProperty_VALIDATE_LIBXML_NONET() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);
	
	
		$xml->setParserProperty(XMLReader::VALIDATE, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	/**
	 *@expectedException PHPUnit_Framework_Error
	 I/O warning : failed to load external entity "http://127.0.0.1:5000/file.xml"
	*/
	public function testURLInvocation_externalGeneralEntity_setParserProperty_VALIDATE_disable_entity_loader() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		libxml_disable_entity_loader(true);
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	
	
	
	
	
	public function testURLInvocation_externalGeneralEntity_setParserProperty_SUBST_ENTITIES() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	/**
	 *@expectedException PHPUnit_Framework_Error
	 *	XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml:4: I/O warning : failed to load external entity "http://127.0.0.1:5000/test.dtd"
	 */
	public function testURLInvocation_externalGeneralEntity_setParserProperty_SUBST_ENTITIES_disable_entity_loader() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml");
	
		libxml_disable_entity_loader(true);
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	
	
	
	/**
	 *  @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): Attempt to load network entity http://127.0.0.1:5000/test.xml/home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_externalGeneralEntity.xml:5: parser error : Failure to process entity remote
	 */
	public function testURLInvocaton_externalGeneralEntity_setParserProperty_SUBST_ENTITIES_LIBXML_NONET() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);
	
	
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_parameterEntity() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
			
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/url_invocation_parameterEntity.dtd"
	*/
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR);
			
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/url_invocation_parameterEntity.dtd"
	*/
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD);
			
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/url_invocation_parameterEntity.dtd"
	*/
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID);
			
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT|LIBXML_NONET);
			
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	

	/**
	*@expectedException PHPUnit_Framework_Error
	* XMLReader::read(): Attempt to load network entity http://127.0.0.1:5000/Attempt to load network entity http://127.0.0.1:5000//home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml:4: I/O warning : failed to load external entity "http://127.0.0.1:5000/test.dtd"
	*/
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$xml = new XMLReader();
			
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
			
		libxml_disable_entity_loader(true);

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_parameterEntity_setParserProperty_LOADDTD() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}

	public function testURLInvocation_parameterEntity_setParserProperty_DEFAULTATTRS() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_parameterEntity_setParserProperty_VALIDATE() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testURLInvocation_parameterEntity_setParserProperty_VALIDATE_LIBXML_NONET() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);
		
		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	 I/O error : failed to load external entity "http://127.0.0.1:5000/url_invocation_parameterEntity.dtd"
	*/
	public function testURLInvocation_parameterEntity_setParserProperty_VALIDATE_disable_entity_loader() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");

		libxml_disable_entity_loader(true);
		
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	
	public function testURLInvocation_parameterEntity_setParserProperty_SUBST_ENTITIES() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}

	public function testURLInvocation_parameterEntity_setParserProperty_SUBST_ENTITIES_LIBXML_NONET() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$encoding="UTF-8",
				$options=LIBXML_NONET);
		
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	*	XMLReader::read(): /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/url_invocation_parameterEntity.xml:4: I/O warning : failed to load external entity "http://127.0.0.1:5000/test.dtd"
	*/
	public function testURLInvocation_parameterEntity_setParserProperty_SUBST_ENTITIES_disable_entity_loader() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		//use substituteEntitities
		$xml->open("../../xml_files_windows/url_invocation_parameterEntity.xml");

		libxml_disable_entity_loader(true);
		
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		/*
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		*/
	}
	
	
	

	

	
	
	
	
	public function testURLInvocation_schemaLocation() {
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/url_invocation_schemaLocation.xml");
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
	
		//$xml->open("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
	
		// 		$xml->setParserProperty(XMLReader::VALIDATE, true);
	
		// 		while($xml->read()) {
		// 			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
		// 				$node = $xml->name;
		// 				$content = $xml->readString();
		// 			}
		// 		}
	
		// 		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		// 		$r = preg_replace('/\s+/', '', $r);
		// 		$this->assertEquals("1", $r);
	}
	
	
	public function testURLInvocation_XInclude() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_xinclude.xml");				
		
		
		while($xml->read()) {			
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
				
		}
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
	}
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_xinclude.xml",
				$encoding="UTF-8",
				$options=LIBXML_XINCLUDE);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
				
		}
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	/**
	 * 
	 * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read():
	 * Attempt to load network entity http://127.0.0.1:5000/
	 * Attempt to load network entity http://127.0.0.1:5000/
	 * Attempt to load network entity http://127.0.0.1:5000/test.dtd
	 * Attempt to load network entity http://127.0.0.1:5000/test.dtd
	 * Attempt to load network entity http://127.0.0.1:5000/test.xml could not load http://127.0.0.1:5000/test.xml, 
	 * and no fallback was found
	 */
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml = new XMLReader();
	
		$xml->open("../../xml_files_windows/url_invocation_xinclude.xml",
				$encoding="UTF-8",
				$options=LIBXML_XINCLUDE|LIBXML_NONET);
	
	
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
	
		}
	
// 		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
// 		$r = preg_replace('/\s+/', '', $r);
// 		$this->assertEquals("0", $r);
	
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "http://127.0.0.1:5000/file.xml"
	*/
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE_disable_entity_loader() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_xinclude.xml",
				$encoding="UTF-8",
				$options=LIBXML_XINCLUDE);
		
		libxml_disable_entity_loader(true);
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
				
		}
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	
	
	
	public function testURLInvocation_noNamespaceSchemaLocation() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");				
		

		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		
		//$xml->open("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml");
		
// 		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
// 		while($xml->read()) {
// 			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
// 				$node = $xml->name;
// 				$content = $xml->readString();
// 			}
// 		}
		
// 		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
// 		$r = preg_replace('/\s+/', '', $r);
// 		$this->assertEquals("1", $r);
	}

    //TODO Validation??
 
		


	public function testXInclude() {
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/xinclude.xml");
				
		
		while($xml->read()) {
// 			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
// 				$node = $xml->name;
// 				$content = $xml->readString();
// 			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			} 
		}
// 		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("xi:include", $node_include);
		
		
	}
	
	public function testXInclude_LIBXML_XINCLUDE() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/xinclude.xml",
				$encoding="UTF-8",
				$options=LIBXML_XINCLUDE);
		
		
		while($xml->read()) {
			
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			
		}		
		$this->assertEquals("content", $node_include);
		$this->assertEquals("it_works", $content_include);
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error
	I/O warning : failed to load external entity "C:/Christopher_Spaeth/code/xml_files_windows/xinclude_source.xml"
	*/
	public function testXInclude_LIBXML_XINCLUDE_disable_entity_loader() {
		
		$xml = new XMLReader();
		
		$xml->open("../../xml_files_windows/xinclude.xml",
				$encoding="UTF-8",
				$options=LIBXML_XINCLUDE);
		
		libxml_disable_entity_loader(true);
		while($xml->read()) {
			
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xi:include') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'content') {
				$node_include= $xml->name;
				$content_include = $xml->readString();
			}
			
		}		
		$this->assertEquals("content", $node_include);
		$this->assertEquals("it_works", $content_include);
	}
	
	
	public function testXSLT() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/optional/xslt.xsl");
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'xsl:stylesheet') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		$this->assertEquals("xsl:stylesheet", $node);
		
	}
	
	
	
	
	
	public function testXXE() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml");
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		
		$this->assertEquals("", $content);
		
	}
	
	public function testXXE_LIBXML_DTDATTR() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDATTR);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);		
	}
	
	
	
	public function testXXE_LIBXML_DTDLOAD() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDLOAD);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);		
	}
	
	
	public function testXXE_LIBXML_DTDVALID() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml",
				$encoding="UTF-8",
				$options=LIBXML_DTDVALID);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);		
	}
	
	public function testXXE_LIBXML_NOENT() {
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);		
	}
	
	

	
	
	/**	
	* @expectedException PHPUnit_Framework_Error
	XMLReader::read(): I/O warning : failed to load external entity "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt"
	*/
	public function testXXE_LIBXML_NOENT_disable_entity_loader() {		
		
		$xml = new XMLReader();
		$xml->open("../../xml_files_windows/xxe.xml",
				$encoding="UTF-8",
				$options=LIBXML_NOENT);
		
		libxml_disable_entity_loader(true);	
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
		
	}
	
	public function testXXE_setParserProperty_LOADDTD() {
		
		$xml = new XMLReader();
		// use setParserProperty
				
		$xml->open("../../xml_files_windows/xxe.xml");
		$xml->setParserProperty(XMLReader::LOADDTD, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	public function testXXE_setParserProperty_DEFAULTATTRS() {
		
		$xml = new XMLReader();
		// use setParserProperty
				
		$xml->open("../../xml_files_windows/xxe.xml");
		$xml->setParserProperty(XMLReader::DEFAULTATTRS, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	public function testXXE_setParserProperty_VALIDATE() {
		
		$xml = new XMLReader();
		// use setParserProperty
				
		$xml->open("../../xml_files_windows/xxe.xml");
		$xml->setParserProperty(XMLReader::VALIDATE, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
	}
	
	
	
	
	
	
	public function testXXE_setParserProperty_SUBST_ENTITIES() {
		
		$xml = new XMLReader();
		// use setParserProperty
				
		$xml->open("../../xml_files_windows/xxe.xml");
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
	}
	
	
	
		/**
	 * * @expectedException PHPUnit_Framework_Error
	 * XMLReader::read(): I/O warning : failed to load
external entity "file:///C:/Christopher_Spaeth/code/xml_files_windows/xxe.txt"
	 */
	public function testXXE_setParserProperty_SUBST_ENTITIES_disable_entity_loader() {

		
		
		$xml = new XMLReader();
			
		// use setParserProperty
		$xml->open("../../xml_files_windows/xxe.xml");
		$xml->setParserProperty(XMLReader::SUBST_ENTITIES, true);
		
		libxml_disable_entity_loader(true);
		
		while($xml->read()) {
			if ($xml->nodeType == XMLReader::ELEMENT && $xml->name == 'data') {
				$node = $xml->name;
				$content = $xml->readString();
			}
		}
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("", $content);
				
	}
	
}
