:: Setup the parser - assumed location %ANDROID%

Set ANDROID="/data/data/de.rub.nds.parsers"
adb push dos/dos_core.xml %ANDROID%
adb push dos/dos_entitySize.xml %ANDROID%
adb push dos/dos_indirections.xml %ANDROID%
adb push dos/dos_indirections_parameterEntity.xml %ANDROID%
adb push dos/dos_recursion.xml %ANDROID%

adb push xxe/android/xxe.xml %ANDROID%
adb push xxe/android/xxe.txt %ANDROID%

adb push xxep/android/internalSubset_ExternalPEReferenceInDTD.xml %ANDROID%
adb push xxep/android/internalSubset_PEReferenceInDTD.xml %ANDROID%
adb push xxep/android/parameterEntity_core.xml %ANDROID%
adb push xxep/android/parameterEntity_doctype.xml %ANDROID%


adb push ssrf/url_invocation_doctype.xml %ANDROID%
adb push ssrf/url_invocation_externalGeneralEntity.xml %ANDROID%
adb push ssrf/url_invocation_noNamespaceSchemaLocation.xml %ANDROID%
adb push ssrf/url_invocation_parameterEntity.xml %ANDROID%
adb push ssrf/url_invocation_schemaLocation.xml %ANDROID%
adb push ssrf/url_invocation_xinclude.xml %ANDROID%

adb push xinclude/android/xinclude.xml %ANDROID%
adb push xinclude/android/xinclude_source.xml %ANDROID%

adb push xslt/android/xslt.xsl %ANDROID%
adb push xslt/android/xslt.xml %ANDROID%


::setup the webserver - assumed location /data/data/de.rub.nds.simplewebserver

adb push xxep/android/parameterEntity_core.dtd /data/data/de.rub.nds.simplewebserver
adb push xxep/android/parameterEntity_doctype.dtd /data/data/de.rub.nds.simplewebserver

adb push ssrf/url_invocation_noNamespaceSchemaLocation.xsd /data/data/de.rub.nds.simplewebserver
adb push ssrf/url_invocation_parameterEntity.dtd /data/data/de.rub.nds.simplewebserver
adb push ssrf/url_invocation_schemaLocation.xsd /data/data/de.rub.nds.simplewebserver