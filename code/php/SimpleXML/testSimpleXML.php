<?php

class TestSimplexml extends PHPUnit_Framework_TestCase {


	
	
	public function setUp() {
        libxml_disable_entity_loader(false);
        
	}
	
	public function tearDown() {

	}
	
	public function testDefault_noAttack() {
		
		$data = simplexml_load_file("../../xml_files_windows/standard.xml", $class_name="SimpleXMLElement");
						
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$this->assertEquals("4", $content);
	}
	
    
	public function testDOS_core() {
		$data = simplexml_load_file("../../xml_files_windows/dos_core.xml",
				$class_name="SimpleXMLElement"
				);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}

/**	
	public function testDOS_core_LIBXML_PARSEHUGE() {
		$data = simplexml_load_file("../../xml_files_windows/dos_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_PARSEHUGE
		);
		
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');	
		 
		$this->assertEquals($expectedCount,$realCount);
	}
*/
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * simplexml_load_file(): Entity: line 1: parser error : Detected an entity reference loop
	 */
	public function testDOS_indirections() {
		$data = simplexml_load_file("../../xml_files_windows/dos_indirections.xml",
				$class_name="SimpleXMLElement"				
		);
	
		/*$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 25;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
		*/
	}
	
	public function testDOS_indirections_LIBXML_PARSEHUGE() {
		$data = simplexml_load_file("../../xml_files_windows/dos_indirections.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_PARSEHUGE
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 10000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
	
	/**
	* @expectedException PHPUnit_Framework_Error
	 parser error : PEReferences forbidden in internal subset
	 */
	public function testDOS_indirections_parameterEntity() {
		$data = simplexml_load_file("../../xml_files_windows/optional/dos_indirections_parameterEntity.xml",
				$class_name="SimpleXMLElement"				
		);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 10000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
	
	
	public function testDOS_entitySize() {
		$data = simplexml_load_file("../../xml_files_windows/dos_entitySize.xml",
				$class_name="SimpleXMLElement"
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
	
/*	
	public function testDOS_entitySize_LIBXML_PARSEHUGE() {
		$data = simplexml_load_file("../../xml_files_windows/dos_entitySize.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_PARSEHUGE
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
*/
	/**
	 * @expectedException PHPUnit_Framework_Error
	simplexml_load_file(): Entity: line 1: parser error : Detected an entity reference loop
	**/
	public function testDOS_recursion() {
		$data = simplexml_load_file("../../xml_files_windows/optional/dos_recursion.xml",
				$class_name="SimpleXMLElement"
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$expectedCount = 3400000;
		$realCount = substr_count($content, 'dos');
			
		$this->assertEquals($expectedCount,$realCount);
	}
	
   
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * simplexml_load_file():
	 * ../../xml_files_windows/dtd/externalParameterEntity/internalSubset_ExternalPEReferenceInDTD.xml:6:
	 * parser error : Entity 'intern' not defined
	 */
	public function testInternalSubset_ExternalPEReferenceInDTD() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement"
		);
	}
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDATTR() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options = LIBXML_DTDATTR
		);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDLOAD() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options = LIBXML_DTDLOAD
		);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_DTDVALID() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options = LIBXML_DTDVALID
		);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	
	
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_NOENT() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options = LIBXML_NOENT
		);
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	

	 /*sinnlos
	public function testInternalSubset_ExternalPEReferenceInDTD_LIBXML_NOENT_disable_entity_loader	() {
	
		libxml_disable_entity_loader(true);
	
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_ExternalPEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options = LIBXML_NOENT
		);
	
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	*/
	
	
	public function testInternalSubset_PEReferenceInDTD() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
				$class_name="SimpleXMLElement"
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	
	/* sinnlos
	public function testInternalSubset_PEReferenceInDTD_LIBXML_NOENT() {
		$data = simplexml_load_file("../../xml_files_windows/internalSubset_PEReferenceInDTD.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT
		);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	*/
	
	
    /**
     * @expectedException PHPUnit_Framework_Error
	 simplexml_load_file(): ../../xml_files_windows/parameterEntity_core.xml:10: parser error : Entity 'all' not defined
     */
    public function testParameterEntity_core() {
    
    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
    			$class_name="SimpleXMLElement");
    
    	$content = $data->__toString();
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("data",$data->getName());
    	$this->assertEquals("",$content);
    }
	
	public function testParameterEntity_core_LIBXML_DTDATTR() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}	
	
	/**
	 *  @expectedException PHPUnit_Framework_Error
	parser error : Entity 'all' not defined
	*/
	public function testParameterEntity_core_LIBXML_DTDATTR_NONET() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR|LIBXML_NONET);
	/*
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	*/
	}
	
	
	public function testParameterEntity_core_LIBXML_DTDLOAD() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}	
	
	 /**
	 *  @expectedException PHPUnit_Framework_Error
	 simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_core.dtd../../xml_files_windows/parameterEntity_core.xml:10: parser error : Entity 'all' not defined
	 */
	 
	 public function testParameterEntity_core_LIBXML_DTDLOAD_NONET() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD|LIBXML_NONET);
	/*
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	*/
	}	
	
	

	public function testParameterEntity_core_LIBXML_DTDVALID() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}
	 /**
     *  @expectedException PHPUnit_Framework_Error
	 simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_core.dtd../../xml_files_windows/parameterEntity_core.xml:10: parser error : Entity 'all' not defined
	 */
	public function testParameterEntity_core_LIBXML_DTDVALID_NONET() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID|LIBXML_NONET);
/*	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
		*/
	}
	
	
	public function testParameterEntity_core_LIBXML_NOENT() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}	
    /**
     *  @expectedException PHPUnit_Framework_Error
    * simplexml_load_file(): Attempt to load network entity 
    * http://127.0.0.1:5000/combine.dtd../../xml_files_windows/parameterEntity_core.xml:9: 
    * parser error : Entity 'all' not defined
    */ 
    public function testParameterEntity_core_LIBXML_NOENT_NONET() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT|LIBXML_NONET);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);
	}	
	
	
	 /*sinnlos
	public function testParameterEntity_LIBXML_NOENT_disable_entity_loader() {
		libxml_disable_entity_loader(true);
		
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_core.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT);
	
// 		$content = $data->__toString();
// 		$content = preg_replace('/\s+/', '', $content);
	
// 		$this->assertEquals("data",$data->getName());
// 		$this->assertEquals("it_works",$content);
	}	
	*/
	
	
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error 
	 * simplexml_load_file(): ../../xml_files_windows/parameterEntity_doctype.xml:3: parser error : Entity 'all' not defined 
	 */
	
	public function testParameterEntity_doctype() {
		$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
				$class_name="SimpleXMLElement");
		
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("",$content);
	}
	
	
	 public function testParameterEntity_doctype_LIBXML_DTDATTR() {

    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
            $class_name="SimpleXMLElement",
            $options=LIBXML_DTDATTR
                    );
		
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);

    }
	
	/**
     * @expectedException PHPUnit_Framework_Error 
      parser error : Entity 'all' not defined
     */
    public function testParameterEntity_doctype_LIBXML_DTDATTR_NONET() {
    
    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
    			$class_name="SimpleXMLElement",
    			$options=LIBXML_DTDATTR|LIBXML_NONET
    	);

    }


    public function testParameterEntity_doctype_LIBXML_DTDLOAD() {

    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
            $class_name="SimpleXMLElement",
            $options=LIBXML_DTDLOAD
                    );
		
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);

    }
 

    /**
     * @expectedException PHPUnit_Framework_Error 
     * simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_doctype.dtd../../xml_files_windows/parameterEntity_doctype.xml:3: parser error : Entity 'all' not defined
     */
    public function testParameterEntity_doctype_LIBXML_DTDLOAD_NONET() {
    
    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
    			$class_name="SimpleXMLElement",
    			$options=LIBXML_DTDLOAD|LIBXML_NONET
    	);
    /*
    	$content = $data->__toString();
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("data",$data->getName());
    	$this->assertEquals("it_works",$content);
    */
    }
	
	 public function testParameterEntity_doctype_LIBXML_DTDVALID() {

    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
            $class_name="SimpleXMLElement",
            $options=LIBXML_DTDVALID
                    );
		
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("it_works",$content);

    }
	/**
	* @expectedException PHPUnit_Framework_Error 
	simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/parameterEntity_doctype.dtd../../xml_files_windows/parameterEntity_doctype.xml:3: validity error : Validation failed: no DTD found !
	*/
	public function testParameterEntity_doctype_LIBXML_DTDVALID_NONET() {
    
    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
    			$class_name="SimpleXMLElement",
    			$options=LIBXML_DTDVALID|LIBXML_NONET
    	);
    
    	$content = $data->__toString();
    	$content = preg_replace('/\s+/', '', $content);
    
    	$this->assertEquals("data",$data->getName());
    	$this->assertEquals("it_works",$content);
    
    }
    
    /**
	 * @expectedException PHPUnit_Framework_Error    
     * simplexml_load_file(): ../../xml_files_windows/parameterEntity_doctype.xml:3: parser 
     * error : Entity 'all' not defined
     */
	public function testParameterEntity_doctype_LIBXML_NOENT() {

    	$data = simplexml_load_file("../../xml_files_windows/parameterEntity_doctype.xml",
            $class_name="SimpleXMLElement",
            $options=LIBXML_NOENT                   
        );
		
    }

	
	
	public function testURLInvocation_doctype() {
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement"
				);
		
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
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}
	
	public function testURLInvocation_doctype_LIBXML_DTDATTR_NONET() {

		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_doctype_LIBXML_DTDLOAD() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}	
	
	public function testURLInvocation_doctype_LIBXML_DTDLOAD_NONET() {

		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	
	}
	
	public function testURLInvocation_doctype_LIBXML_DTDVALID_NONET() {

		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}	
	
	public function testURLInvocation_doctype_LIBXML_NOENT() {

		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	
	
	public function testURLInvocation_externalGeneralEntity(){
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement"
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

	
	}
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDATTR() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDATTR
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDLOAD() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDLOAD
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDVALID
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/Attempt to load network entity http://127.0.0.1:5000/Attempt to load network entity http://127.0.0.1:5000/file.xml../../xml_files_windows/url_invocation_externalGeneralEntity.xml:6: parser error : Failure to process entity remote
	*/
	public function testURLInvocation_externalGeneralEntity_LIBXML_DTDVALID_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID|LIBXML_NONET
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}	
	
	
	
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_NOENT
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	

	 /*sinnlos
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT_disable_entity_loader() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		libxml_disable_entity_loader(true);
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_NOENT
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	*/
	/**
	 * @expectedException PHPUnit_Framework_Error
	 * simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/test.xml../../xml_files_windows/url_invocation_externalGeneralEntity.xml:5: parser error : Failure to process entity remote
	 */
	public function testURLInvocation_externalGeneralEntity_LIBXML_NOENT_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_externalGeneralEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT|LIBXML_NONET
		);
	
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
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
						$class_name="SimpleXMLElement"
				);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR() {
		
		
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDATTR
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDATTR_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);


		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD() {
		
		
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDLOAD
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDLOAD_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);


		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID() {
		
		
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_DTDVALID
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_DTDVALID_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);


		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT() {
		
		
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);

		
		// enable entities
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_NOENT
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	
	
	/*sinnlos
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_disable_entity_loader() {
	
	
		//	reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		
		libxml_disable_entity_loader(true);
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options =LIBXML_NOENT
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("1", $r);
	}
	*/
	
	public function testURLInvocation_parameterEntity_LIBXML_NOENT_NONET() {
		//setting LIBXML_NONET
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);


		$data = simplexml_load_file("../../xml_files_windows/url_invocation_parameterEntity.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT|LIBXML_NONET
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	public function testURLInvocation_schemaLocation(){
	
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_schemaLocation.xml",
				$class_name="SimpleXMLElement"
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
			
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml",
				$class_name="SimpleXMLElement"								
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
	}
	
	
	
	public function testURLInvocation_XInclude() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);		
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_xinclude.xml",
				$class_name="SimpleXMLElement"
		);
		
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
		
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_xinclude.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_XINCLUDE
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	
	/*
	public function testURLInvocation_XInclude_LIBXML_XINCLUDE_NONET() {
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_xinclude.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_XINCLUDE|LIBXML_NONET				
		);
	
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
	}
	*/

	
	public function testURLInvocation_noNamespaceSchemaLocation(){
		
		// reset the counter
		$r = file_get_contents("http://127.0.0.1:5000/reset");
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		

		$data = simplexml_load_file("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml",
				$class_name="SimpleXMLElement"
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
					
		$data = simplexml_load_file("../../xml_files_windows/url_invocation_noNamespaceSchemaLocation.xml",
				$class_name="SimpleXMLElement"
		);
		
		$r = file_get_contents("http://127.0.0.1:5000/getCounter");
		$r = preg_replace('/\s+/', '', $r);
		$this->assertEquals("0", $r);
		
	}
	
		
	public function testXInclude() {
		$data = simplexml_load_file("../../xml_files_windows/xinclude.xml",
									$class_name="SimpleXMLElement"
									);
		//$content = $data->__toString();
		//$name = $data->getName();

		//get all children with namespace prefix xi
		$child_nodes = $data->children("xi", True);
		$content = $child_nodes->asXML();
		//$content = $data->asXML(); 						 
		//$content = preg_replace('/\s+/', '', $content);		
		
		$this->assertEquals('<xi:include href="C:/Christopher_Spaeth/code/xml_files_windows/xinclude_source.xml"/>',$content);
		
	}
	
	public function testXInclude_LIBXML_XINCLUDE() {
		$data = simplexml_load_file("../../xml_files_windows/xinclude.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_XINCLUDE);
		
		$content = $data->__toString();		
		$content = preg_replace('/\s+/', '', $content);
		
		$child_nodes = $data->children("xi", True);
		$content = $child_nodes->asXML();
		$this->assertEquals('<xi:include href="C:/Christopher_Spaeth/code/xml_files_windows/xinclude_source.xml"/>',$content);
		
	}
	
	public function testXSLT() {
		$data = simplexml_load_file("../../xml_files_windows/optional/xslt.xsl",
				$class_name="SimpleXMLElement");
		
		$this->assertEquals("stylesheet", $data->getName());
		
	}
	
	
	public function testXXE() {
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement");
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals("",$content);
	}
	
	public function testXXE_LIBXML_DTDATTR() {
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDATTR);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$this->assertEquals('',$content);
	}
	
	public function testXXE_LIBXML_DTDLOAD() {
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDLOAD);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$this->assertEquals('',$content);
	}
	
	public function testXXE_LIBXML_DTDVALID() {
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_DTDVALID);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$this->assertEquals('it_works',$content);
	}
	
	public function testXXE_LIBXML_NOENT() {
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
		$this->assertEquals('it_works',$content);
	}
	
	
	/**
	 * @expectedException PHPUnit_Framework_Error
	 simplexml_load_file(): Attempt to load network entity http://127.0.0.1:5000/url_invocation_parameterEntity.dtdI/O warning : failed to load external entity "../../xml_files_windows/xxe.xml"
	 */
	public function testXXE_LIBXML_NOENT_disable_entity_loader() {
	
	
		libxml_disable_entity_loader(true);
	
		$data = simplexml_load_file("../../xml_files_windows/xxe.xml",
				$class_name="SimpleXMLElement",
				$options=LIBXML_NOENT);
	
		$content = $data->__toString();
		$content = preg_replace('/\s+/', '', $content);
	
		$this->assertEquals("data",$data->getName());
		$this->assertEquals('',$content);
	
	}
	
	

	
// 	//http://php.net/manual/en/function.simplexml-load-string.php
// // 	If you want to use multiple libxml options, separate them with a pipe, like so:
// 	public function testUrlInvocation_setFeature_load_external_dtd() {
		
// 		$data = simplexml_load_file("../../xml_files_windows/url_invocation_doctype.xml",
// 				$class_name="SimpleXMLElement",
// 				$options=LIBXML_DTDLOAD| LIBXML_NOENT
// 		);
		
// // 		$content = $data->__toString();
// // 		$content = preg_replace('/\s+/', '', $content);
		
// // 		$this->assertEquals("data",$data->getName());
// // 		$this->assertEquals("",$content);
// 	}
	
}

?>
