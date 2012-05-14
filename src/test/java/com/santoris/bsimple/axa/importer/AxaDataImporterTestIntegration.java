package com.santoris.bsimple.axa.importer;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.santoris.bsimple.BaseSpringConfiguration;

@Ignore
public class AxaDataImporterTestIntegration extends BaseSpringConfiguration {

	@Autowired
	AxaDataImporter dataImporter;
	
	@Test
	public void testImportAllData() {
		dataImporter.importAllData();
	}
}
