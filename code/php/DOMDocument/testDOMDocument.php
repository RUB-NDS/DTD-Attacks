<?php



class TestDOMDocument extends PHPUnit_Framework_TestCase {

	
	public function setUp() {
		libxml_disable_entity_loader(false);
	}
	
	public function tearDown() {
	}

	public function testDefault_noAttack() {
		$xml = new DOMDocument();
		
		$xml->load("../../xml_files_windows/standard.xml");
		
		$data = $xml->getElementsByTagName("data");
		$content = $data->item(0)->nodeValue;
		$this->assertEquals("4", $content); 	
	}
	
	
	public function testDOS_core() {
		$xml = new DOMDocument();

		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);

		$xml->load("../../xml_files_windows/dos/dos_core.xml");
		
		$data = $xml->getElementsByTagName("data");
		$content = $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);	
	}

	/**
	*@expectedException PHPUnit_Framework_Error
	*DOMDocument::load(): Detected an entity reference loop in Entity, line: 1
	*/
	public function testDOS_indirections() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);

		$xml->load("../../xml_files_windows/dos/dos_indirections.xml");
		
		$data = $xml->getElementsByTagName("data");
// 		$content = $data->item(0)->nodeValue;
// 		$content = preg_replace('/\s+/', '', $content);
// 		$expectedCount = 50000;
// 		$realCount = substr_count($content, 'dos');
			
// 		$this->assertEquals($expectedCount,$realCount);	
	}

	public function testDOS_indirections_LIBXML_PARSEHUGE() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		$xml->load("../../xml_files_windows/dos/dos_indirections.xml",
				$options=LIBXML_PARSEHUGE);
	
		$data = $xml->getElementsByTagName("data");
		$content = $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 10000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	DOMDocument::load(): PEReferences forbidden in internal subset
	*/
	public function testDOS_indirections_parameterEntity() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);

		$xml->load("../../xml_files_windows/dos/dos_indirections_parameterEntity.xml");
		
		$data = $xml->getElementsByTagName("data");
// 		$content = $data->item(0)->nodeValue;
// 		$content = preg_replace('/\s+/', '', $content);
// 		$expectedCount = 50000;
// 		$realCount = substr_count($content, 'dos');
			
// 		$this->assertEquals($expectedCount,$realCount);	
	}
	

	public function testDOS_entitySize() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);

		$xml->load("../../xml_files_windows/dos/dos_entitySize.xml");
		
		$data = $xml->getElementsByTagName("data");
 		$content = $data->item(0)->nodeValue;
 		$content = preg_replace('/\s+/', '', $content);
 		$expectedCount = 3400000;
 		$realCount = substr_count($content, 'dos');
			
 		$this->assertEquals($expectedCount,$realCount);	
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error
	DOMDocument::load(): Detected an entity reference loop in Entity, line: 1
	*/
	public function testDOS_recursion() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);

		$xml->load("../../xml_files_windows/dos/dos_recursion.xml");

	}
	
	
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * DOMDocument::load(): Entity 'intern' not defined in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/dtd/externalParameterEntity/xxep/internalSubset_ExternalPEReferenceInDTD.xml, line: 6
	 */
	public function testInternalSubset_ExternalPEReferenceInDTD() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
	
	}
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDATTR() {
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml",
				$options=LIBXML_DTDATTR);
		
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDLOAD() {
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml",
				$options=LIBXML_DTDLOAD);
		
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDVALID() {
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml",
				$options=LIBXML_DTDVALID);
		
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_NOENT() {
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml",
				$options=LIBXML_NOENT);
		
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_resolveExternals() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_substituteEntities() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_ExternalPEReferenceInDTD.xml");
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	
	}	
	
	public function testInternalSubset_PEReferenceInDTD() {
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxep/internalSubset_PEReferenceInDTD.xml");
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	  
    /**
     * @expectedException PHPUnit_Framework_Error
     * DOMDocument::load(): Entity 'all' not defined in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml, line: 9
     */
    public function testParameterEntity_core() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml");
    
//     	$data = $xml->getElementsByTagName("data");
//     	$content= $data->item(0)->nodeValue;
//     	$content = preg_replace('/\s+/', '', $content);
    
    }
     
	public function testParameterEntity_core_LIBXML_DTDATTR() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDATTR);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	 public function testParameterEntity_core_LIBXML_DTDATTR_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDATTR|LIBXML_NONET);

    }
	
	
	public function testParameterEntity_core_LIBXML_DTDLOAD() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDLOAD);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	 public function testParameterEntity_core_LIBXML_DTDLOAD_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDLOAD|LIBXML_NONET);

    }
	
	
	public function testParameterEntity_core_LIBXML_DTDVALID() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDVALID);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	public function testParameterEntity_core_LIBXML_DTDVALID_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_DTDVALID|LIBXML_NONET);

    }
   
    
    
    public function testParameterEntity_core_LIBXML_NOENT() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_NOENT);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }

    /**
     * @expectedException PHPUnit_Framework_Error
     * DOMDocument::load(): Attempt to load network entity 
     * http://127.0.0.1:5000/combine.dtdEntity 'all' not defined in 
     * /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml, 
     * line: 9
     */
    public function testParameterEntity_core_LIBXML_NOENT_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_NOENT|LIBXML_NONET);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
    
   /*
    public function testParameterEntity_core_LIBXML_NOENT_disable_entity_loader    () {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	libxml_disable_entity_loader(true);
    	 
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_NOENT);
    
    	//     	$data = $xml->getElementsByTagName("data");
    	//     	$content= $data->item(0)->nodeValue;
    	//     	$content = preg_replace('/\s+/', '', $content);
    
    	//     	$this->assertEquals("it_works", $content);
    }
*/


    public function testParameterEntity_core_resolveExternals() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	// with property substitute_entities
    	$xml->resolveExternals = true;
    	$this->assertTrue($xml->resolveExternals);
    
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml");
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    
    }
	
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	public function testParameterEntity_resolveExternals_LIBXML_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	// with property substitute_entities
    	$xml->resolveExternals = true;
    	$this->assertTrue($xml->resolveExternals);
    
    
     	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_NONET);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    
    }
	

    
    public function testParameterEntity_core_substituteEntities() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	// with property substitute_entities
    	$xml->substituteEntities = true;
    	$this->assertTrue($xml->substituteEntities);
    
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml");
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    
    }   
  

    /**
     * @expectedException PHPUnit_Framework_Error
    * DOMDocument::load(): Attempt to load network entity 
    * http://127.0.0.1:5000/combine.dtdEntity 'all' not defined in 
    * /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_core.xml, 
    * line: 9
    */
	
    public function testParameterEntity_substituteEntities_LIBXML_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	// with property substitute_entities
    	$xml->substituteEntities = true;
    	$this->assertTrue($xml->substituteEntities);
    
    
     	$xml->load("../../xml_files_windows/xxep/parameterEntity_core.xml",
    			$options=LIBXML_NONET);   

    
    }
	
	
	
	


    /**
     * @expectedException PHPUnit_Framework_Error
     * DOMDocument::load():	in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml, line: 3
     */
    public function testParameterEntity_doctype() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("", $content);
    }
    
	public function testParameterEntity_doctype_LIBXML_DTDATTR() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    				$options=LIBXML_DTDATTR);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	
	/**
	*@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined
	*/
	public function testParameterEntity_doctype_LIBXML_DTDATTR_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    			$options=LIBXML_DTDATTR|LIBXML_NONET);

    
    	
    }
	
	
	
    public function testParameterEntity_doctype_LIBXML_DTDLOAD() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    				$options=LIBXML_DTDLOAD);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
    
    
    /**
    *@expectedException PHPUnit_Framework_Error
    *DOMDocument::load(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_doctype.dtdEntity 'all' not defined in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml, line: 3
	*/
    public function testParameterEntity_doctype_LIBXML_DTDLOAD_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    			$options=LIBXML_DTDLOAD|LIBXML_NONET);
    
    //	$data = $xml->getElementsByTagName("data");
    //	$content= $data->item(0)->nodeValue;
    //	$content = preg_replace('/\s+/', '', $content);
    
    	
    }
	
	
	public function testParameterEntity_doctype_LIBXML_DTDVALID() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    				$options=LIBXML_DTDVALID);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	
	
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	public function testParameterEntity_doctype_LIBXML_DTDVALID_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    			$options=LIBXML_DTDVALID|LIBXML_NONET);
    
    
    
    	
    }
	
   
    
    /**
     * @expectedException PHPUnit_Framework_Error
     * DOMDocument::load(): Entity 'all' not defined in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml, line: 3
     */
    public function testParameterEntity_doctype_LIBXML_NOENT() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    			$options=LIBXML_NOENT);
    
    }
    
    
    
    
    public function testParameterEntity_doctype_resolveExternals() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->resolveExternals = true;
    	$this->assertTrue($xml->resolveExternals);
    	
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
    
    /*
    public function testParameterEntity_doctype_resolveExternals_LIBXML_NOENT() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->resolveExternals = true;
    	$this->assertTrue($xml->resolveExternals);
    	 
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    				$options=LIBXML_NOENT);
    
    	$data = $xml->getElementsByTagName("data");
    	$content= $data->item(0)->nodeValue;
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("it_works", $content);
    }
	*/
	
    /**
    * @expectedException PHPUnit_Framework_Error
  	*	  DOMDocument::load(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_doctype.dtdEntity 'all' not defined in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/xxep/parameterEntity_doctype.xml, line: 3
	*/
    public function testParameterEntity_doctype_resolveExternals_LIBXML_NONET() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	$xml->resolveExternals = true;
    	$this->assertTrue($xml->resolveExternals);
    	 
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml",
    				$options=LIBXML_NONET);

    }
    
  
	/**
	@expectedException PHPUnit_Framework_Error
	Entity 'all' not defined 
	*/
	public function testParameterEntity_doctype_substituteEntities() {
    	$xml = new DOMDocument();
    
    	$this->assertFalse($xml->substituteEntities);
    	$this->assertFalse($xml->resolveExternals);
    
    	// with property substitute_entities
    	$xml->substituteEntities = true;
    	$this->assertTrue($xml->substituteEntities);
    
    
    	$xml->load("../../xml_files_windows/xxep/parameterEntity_doctype.xml");
 

    
    }
	
	
    
  
    

	
	
	public function testURLInvocation_doctype() {
		$xml = new DOMDocument();		
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);	
	}
	
	
	public function testURLInvocation_doctype_LIBXML_DTDATTR() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		//enable DTD loading
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
					$options=LIBXML_DTDATTR);
		
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_doctype_LIBXML_DTDATTR_NONET () {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
				$options=LIBXML_DTDATTR|LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	
	public function testURLInvocation_doctype_LIBXML_DTDLOAD() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		//enable DTD loading
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
					$options=LIBXML_DTDLOAD);
		
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	public function testURLInvocation_doctype_LIBXML_DTDLOAD_NONET () {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
				$options=LIBXML_DTDLOAD|LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		//enable DTD loading
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
					$options=LIBXML_DTDVALID);
		
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
	}
	
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID_NONET () {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	public function testURLInvocation_doctype_LIBXML_NOENT () {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
				$options=LIBXML_NOENT);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	
	
	public function testURLInvocation_doctype_resolveExternals() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
				
		$xml = new DOMDocument();
		
		// set resolveExternals
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
		
		
	}

	
	public function testURLInvocation_doctype_resolveExternals_LIBXML_NONET () {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();		

		// set resolveExternals
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
		
		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml",
				$options=LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
				
		
	}
	
	
	public function testURLInvocation_doctype_substituteEntities() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
				
		$xml = new DOMDocument();
		
		// set resolveExternals
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_doctype.xml");
		
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


		$xml = new DOMDocument();		
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);		
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
	
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

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
					
		// works with LIBXML
		
			$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
					$options=LIBXML_DTDATTR);
		
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

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
					
		// works with LIBXML
		
			$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
					$options=LIBXML_DTDLOAD);
		
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

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
					
		// works with LIBXML
		
			$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
					$options=LIBXML_DTDVALID);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	/**
	*@expectedException PHPUnit_Framework_Error
	*Failure to process entity remote
	*/
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID_NONET() {

		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
			
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	

	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
					
		// works with LIBXML
		
			$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
					$options=LIBXML_NOENT);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}

	
	/**
	*@expectedException PHPUnit_Framework_Error
	*DOMDocument::load(): Attempt to load network entity http://127.0.0.1:5000/Failure to process entity remote in /home/phoenix/masterthesis/Christopher_Spaeth/code/xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml, line: 5
	*/
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT_NONET() {

		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
			

		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
				$options=LIBXML_NOENT|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	public function testURLInvocation_externalGeneralEntity_resolveExternals() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();

		//	enable entities
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	
	public function testURLInvocation_externalGeneralEntity_substituteEntities() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();

		//	enable entities
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml");
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	

	public function testURLInvocation_externalGeneralEntity_substituteEntities_LIBXML_NONET() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_externalGeneralEntity.xml",
				$options=LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
	}


	
	
	public function testURLInvocation_noNamespaceSchemaLocation(){
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_noNamespaceSchemaLocation.xml");
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_parameterEntity() {
	
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml");
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);	
		
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR() {

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);	
		
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
					$options=LIBXML_DTDATTR);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR_NONET() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			

		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_DTDATTR|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD() {

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);	
		
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
					$options=LIBXML_DTDLOAD);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD_NONET() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			

		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_DTDLOAD|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID() {

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);	
		
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
					$options=LIBXML_DTDVALID);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID_NONET() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			

		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT() {

		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		// works with LIBXML
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
					$options=LIBXML_NOENT);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
/*
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_disable_entity_loader() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		
		libxml_disable_entity_loader(true);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_NOENT);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
*/
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_NONET() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			

		// set LIBXML_NONET
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_NOENT|LIBXML_NONET);
		
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
	public function testURLInvocation_parameterEntity_resolveExternals() {
		
		$xml = new DOMDocument();

		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);

				// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml" );
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
	public function testURLInvocation_parameterEntity_resolveExternals_LIBXML_NONET() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
	}
	
	public function testURLInvocation_parameterEntity_substituteEntities() {
		
		$xml = new DOMDocument();

		//	enable entities
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);

				// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml" );
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
/*
	public function testURLInvocation_parameterEntity_substituteEntities_disable_entity_loader	() {
	
		$xml = new DOMDocument();
	
		//	enable entities
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);

				// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		libxml_disable_entity_loader(true);
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml" );
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	*/
	
	public function testURLInvocation_parameterEntity_substituteEntities_LIBXML_NONET() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		//	enable entities
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_parameterEntity.xml",
				$options=LIBXML_NONET);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
	}

	public function testURLInvocation_schemaLocation(){
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_schemaLocation.xml");
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}

	
	public function testURLInvocation_XInclude() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		
	}
	
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_xinclude.xml",
					$options=LIBXML_XINCLUDE);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	/*
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE_NONET() {
		
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_xinclude.xml",
				$options=LIBXML_XINCLUDE|LIBXML_NONET);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}	
	*/
	
	public function testURLInvocation_XInclude_xinclude() {
		
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$xml->load("../../xml_files_windows/ssrf/url_invocation_xinclude.xml");
		$xml->xinclude();
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);	
		
		
	}
	
	public function testURLInvocation_XInclude_xinclude_LIBXML_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/ssrf/url_invocation_xinclude.xml",
				$options=LIBXML_NONET);
		$xml->xinclude();
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testXInclude() {
		
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/xinclude.xml");	
			
// 		$data = $xml->getElementsByTagName("data");		
// 		$content= $data->item(0)->nodeValue;
// 		$content = preg_replace('/\s+/', '', $content);
		
		
	foreach ($xml->getElementsByTagNameNS('http://www.w3.org/2001/XInclude', '*') as $element) {
    	$this->assertEquals("include", $element->localName);
    	$this->assertEquals("xi", $element->prefix);
// 		echo 'local name: ', $element->localName, ', prefix: ', $element->prefix, "\n";
	}
	

		

	}
	
	
	public function testXInclude_LIBXML_XINCLUDE() {
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/xinclude.xml",
					$options=LIBXML_XINCLUDE);
		
		foreach ($xml->getElementsByTagNameNS('http://www.w3.org/2001/XInclude', '*') as $element) {
			$this->assertEquals("include", $element->localName);
			$this->assertEquals("xi", $element->prefix);
			// 		echo 'local name: ', $element->localName, ', prefix: ', $element->prefix, "\n";
		}
	}
	
	public function testXInclude_xinclude() {
		$xml = new DOMDocument();
		
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/xinclude.xml");
		$xml->xinclude();
		
		$content_el = $xml->getElementsByTagName("content");
		$content = $content_el->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("it_works", $content);
		
	}
	
	
	public function testXSLT() {
		
		$xml = new DOMDocument();
		
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
		
		$xml->load("../../xml_files_windows/optional/xslt.xsl");	
			
 		$data = $xml->documentElement;				
 		$content= $data->tagName;
		$this->assertEquals("xsl:stylesheet", $content);
// 		$content = preg_replace('/\s+/', '', $content);
		
	/*	
	foreach ($xml->getElementsByTagNameNS('http://www.w3.org/2001/XInclude', '*') as $element) {
    	$this->assertEquals("include", $element->localName);
    	$this->assertEquals("xi", $element->prefix);
// 		echo 'local name: ', $element->localName, ', prefix: ', $element->prefix, "\n";
*/
	}
	
	
	
	

	public function testXXE() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxe/xxe.xml");
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$this->assertEquals("", $content);
	}
	
	public function testXXE_LIBXML_DTDATTR() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
			
		// with libxml:
		$xml->load("../../xml_files_windows/xxe/xxe.xml",
				$options=LIBXML_DTDATTR);
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("", $content);	
	}
	
	public function testXXE_LIBXML_DTDLOAD() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
			
		// with libxml:
		$xml->load("../../xml_files_windows/xxe/xxe.xml",
				$options=LIBXML_DTDLOAD);
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("", $content);	
	}
	
	public function testXXE_LIBXML_DTDVALID() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
			
		// with libxml:
		$xml->load("../../xml_files_windows/xxe/xxe.xml",
				$options=LIBXML_DTDVALID);
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);	
	}
	
	public function testXXE_LIBXML_NOENT() {
	
		$xml = new DOMDocument();
	
		$this->assertFalse($xml->substituteEntities);
		$this->assertFalse($xml->resolveExternals);
	
			
		// with libxml:
		$xml->load("../../xml_files_windows/xxe/xxe.xml",
				$options=LIBXML_NOENT);
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);	
	}
	
	public function testXXE_resolveExternals() {
	
		$xml = new DOMDocument();
	
		//	with property substitute_entities
		$xml->resolveExternals = true;
		$this->assertTrue($xml->resolveExternals);
	
		$xml->load("../../xml_files_windows/xxe/xxe.xml");
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("", $content);
	}
	
	public function testXXE_substituteEntitites() {
	
		$xml = new DOMDocument();
	
		//	with property substitute_entities
		$xml->substituteEntities = true;
		$this->assertTrue($xml->substituteEntities);
	
		$xml->load("../../xml_files_windows/xxe/xxe.xml");
	
		$data = $xml->getElementsByTagName("data");
		$content= $data->item(0)->nodeValue;
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("it_works", $content);
	}
	
	
	
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 **/
	public function testXXE_LIBXML_NOENT_disable_entity_loader() {
	
		$xml = new DOMDocument();
	
		libxml_disable_entity_loader(true);
			
		$xml->load("../../xml_files_windows/xxe/xxe.xml",
				$options=LIBXML_NOENT);

	
	
	}


}

?>
