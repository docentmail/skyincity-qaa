package com.skyincity.qaa.util.sourcegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GroupProcessor {
	private static Logger log = Logger.getLogger(InputProcessor.class);
//	final String rezFolderPath ="C:\\My\\Tmp\\qc\\Manufacturer\\Rez";
	final String rezFolderPath;
	
	
	public GroupProcessor(String rezFolderPath) {
		super();
		this.rezFolderPath = rezFolderPath;
	}

	public void processGroup( List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{
			
		// prepare description 
		prepareDescriptionClass(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		prepareDescriptionProviderFrafment(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		// prepare test class
		prepareTestClass(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		
		// DataBuilderClass
		prepareDataBuilderClass(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		
		// data dataBuilderManagerFragment
		prepareDataBuilderManagerFragment(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		
		// testNg xml group file
		preparetestNgXmlFileForFolder(loadTestsInfo, loadTemplatesInfo, loadGroupInfo );
		
	}

	// testNgXmlFileForFolder
	public String preparetestNgXmlFileForFolder(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String top= (String) loadTemplatesInfo.get("testngXmlFolderTop_group");
		String bottom  =(String) loadTemplatesInfo.get("testngXmlFolderBottom_group");
		
        StringBuilder sb=new StringBuilder();		

        sb.append(top);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual method
			sb.append(loadTestsInfo.get(i).get(TestProcessor.TestngXmlMethodKey));
		}
		sb.append(bottom);
		String rez = GeneratorUtil.substituteAll(sb.toString(), loadGroupInfo);
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+(String)loadGroupInfo.get("TestngXmlFileNameForFolder")+".xml"), rez, false);
		
		return rez; 
	}

	
	public String prepareDataBuilderManagerFragment(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String all= (String) loadTemplatesInfo.get("dataBuilderManager_group");
		String rez = GeneratorUtil.substituteAll(all, loadGroupInfo);
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+"DataBuilderManager.fragment"), rez, false);
		
		return rez; 
	}

	
	
	public String prepareDataBuilderClass(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String top= (String) loadTemplatesInfo.get("dataBuilderTop_group");
		String middle01= (String) loadTemplatesInfo.get("dataBuilderMiddle01_group");
		String middle02= (String) loadTemplatesInfo.get("dataBuilderMiddle02_group");
		String bottom  =(String) loadTemplatesInfo.get("dataBuilderBottom_group");
		
        StringBuilder sb=new StringBuilder();		

        sb.append(top);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual build method choice
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DataBuilderBuilderChoiseFragmentKey));
		}
		
        sb.append(middle01);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual cleaner method choice
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DataBuilderCleanerChoiseFragmentKey));
		}
			
        sb.append(middle02);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual build/clean methods
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DataBuilderBuildMethodKey));
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DataBuilderCleanMethodKey));
		}

		
		sb.append(bottom);
		String rez = GeneratorUtil.substituteAll(sb.toString(), loadGroupInfo);
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+(String)loadGroupInfo.get("DataBuilderClass")+".java"), rez, false);
		
		return rez; 
	}

	
	
	public String prepareTestClass(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String top= (String) loadTemplatesInfo.get("testClassTop_group");
		String bottom  =(String) loadTemplatesInfo.get("testClassBottom_group");
		
        StringBuilder sb=new StringBuilder();		

        sb.append(top);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual method
			sb.append(loadTestsInfo.get(i).get(TestProcessor.TestMethodKey));
		}
		sb.append(bottom);
		String rez = GeneratorUtil.substituteAll(sb.toString(), loadGroupInfo);
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+(String)loadGroupInfo.get("TestClassName")+".java"), rez, false);
		
		return rez; 
	}

	
	public String prepareDescriptionProviderFrafment(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String top= (String) loadTemplatesInfo.get("descriptionProviderTop_group");
		String bottom  =(String) loadTemplatesInfo.get("descriptionProviderBottom_group");

		
        StringBuilder sb=new StringBuilder();		

        sb.append(top);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual method
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DescriptionProviderPartKey));
		}
		sb.append(bottom);
		String rez = GeneratorUtil.substituteAll(sb.toString(), loadGroupInfo);
		
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+"DescriptionProvider.fragment"), rez, false);
		
		return rez; 
	}

	
	public String prepareDescriptionClass(List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{

		// load templates
		String top= (String) loadTemplatesInfo.get("descriptionTop_group");
		String bottom  =(String) loadTemplatesInfo.get("descriptionBottom_group");


		
        StringBuilder sb=new StringBuilder();		

        sb.append(top);
		for (int i=0; i<loadTestsInfo.size(); i++) {
			// add individual method
			sb.append(loadTestsInfo.get(i).get(TestProcessor.DescriptionMethodBodyKey));
			sb.append("\n\n");
			
		}
		sb.append(bottom);
		String rez = GeneratorUtil.substituteAll(sb.toString(), loadGroupInfo);
		
		
		GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+(String)loadGroupInfo.get("DescriptionClassName")+".java"), rez, false);
		
		return rez; 
	}
	
	

	
}
