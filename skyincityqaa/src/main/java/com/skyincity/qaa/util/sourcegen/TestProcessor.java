package com.skyincity.qaa.util.sourcegen;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * prepares separate tests code pieces 
				prepareDescriptionMethodBody(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				prepareDescriptionProviderPart(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				prepareTestMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo); 
				// dataBuilderMethod 
				prepareDataBuilderMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// dataBuilderPart
				prepareDataBuilderFragment(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// testNg xml method file fragment
				prepareTestngXmlMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// testNg xml for separate method
				prepareTestngXmlForSeparateMethod
 *
 */
public class TestProcessor {
	public static final String DescriptionMethodBodyKey="DescriptionMethodBodyKey";
	public static final String DescriptionProviderPartKey="DescriptionProviderPartKey";
	public static final String TestMethodKey="TestMethodKey";

	public static final String DataBuilderBuildMethodKey="DataBuilderBuildMethodKey";
	public static final String DataBuilderBuilderChoiseFragmentKey="DataBuilderBuilderChoiseFragmentKey";

	public static final String DataBuilderCleanMethodKey="DataBuilderCleanMethodKey";
	public static final String DataBuilderCleanerChoiseFragmentKey="DataBuilderCleanerChoiseFragmentKey";
	
	public static final String TestngXmlMethodKey="TestngXmlMethodKey";	
	
	 private final String rezFolderPath;	
		
	 
	
	public TestProcessor(String rezFolderPath) {
		super();
		this.rezFolderPath = rezFolderPath;
	}
	public void processTest( List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo ) throws Exception{
			
			for (int i=0; i<loadTestsInfo.size(); i++) {
				// prepare description method body
				prepareDescriptionMethodBody(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				prepareDescriptionProviderPart(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				prepareTestMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo); 

				// dataBuilder Build Method 
				prepareDataBuilderBuildMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// dataBuilder Builder Choise Part
				prepareDataBuilderBuilderChoiseFragment(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// dataBuilder   Cleaner Choise Fragment
				prepareDataBuilderCleanerChoiseFragment(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// dataBuilder CLeaner Method
				prepareDataBuilderCleanerMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				
				// testNg xml method file fragment
				prepareTestngXmlMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
				// testNg xml for separate method
				prepareTestngXmlForSeparateMethod(i,loadTestsInfo,	loadTemplatesInfo,loadGroupInfo);
			}
	}
 	// TestngXmlForSeparateMethod
	private String 	prepareTestngXmlForSeparateMethod(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo) throws Exception{

		// templates
		String dataBuilderFragment= (String) loadTemplatesInfo.get("testngXmlSeparateMethod_test");
        String rez = GeneratorUtil.substituteAll(dataBuilderFragment, loadTestsInfo.get(loadTestsInfoPosition));
        rez = GeneratorUtil.substituteAll(rez, loadGroupInfo);
        
        System.out.println(rez);
        // store
        GeneratorUtil.writeToFile(new File(rezFolderPath+File.separatorChar+(String)loadTestsInfo.get(loadTestsInfoPosition).get("TestngSeparateTestXmlFileName")+".xml"), rez, false);
        
        return rez;
	
	}
	
	// testNg xml for separate method
	private String 	prepareTestngXmlMethod(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String dataBuilderFragment= (String) loadTemplatesInfo.get("testngXmlFolderMethod_test");
        String rez = GeneratorUtil.substituteAll(dataBuilderFragment, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(TestngXmlMethodKey, rez);
        
        return rez;
	
	}

	
	
	// dataBuilderFragment
	private String 	prepareDataBuilderBuilderChoiseFragment(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String dataBuilderFragment= (String) loadTemplatesInfo.get("dataBuilderBuilderChoiseFragment_test"); 
        String rez = GeneratorUtil.substituteAll(dataBuilderFragment, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(DataBuilderBuilderChoiseFragmentKey, rez);
        
        return rez;
	
	}

	
	// dataBuilderMethod
	private String 	prepareDataBuilderBuildMethod(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String dataBuilderMethod= (String) loadTemplatesInfo.get("dataBuilderBuildMethod_test"); 
        String rez = GeneratorUtil.substituteAll(dataBuilderMethod, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(DataBuilderBuildMethodKey, rez);
        
        return rez;
	
	}

	
	// dataBuilder   Cleaner Fragment
	private String 	prepareDataBuilderCleanerChoiseFragment(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String dataBuilderFragment= (String) loadTemplatesInfo.get("dataBuilderCleanerChoiseFragment_test"); 
        String rez = GeneratorUtil.substituteAll(dataBuilderFragment, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(DataBuilderCleanerChoiseFragmentKey, rez);
        
        return rez;
	
	}

	
	// dataBuilder CLeaner Method
	private String 	prepareDataBuilderCleanerMethod(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String dataBuilderMethod= (String) loadTemplatesInfo.get("dataBuilderCleanMethod_test"); 
        String rez = GeneratorUtil.substituteAll(dataBuilderMethod, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(DataBuilderCleanMethodKey, rez);
        
        return rez;
	
	}

	
	
	
	// prepare description method body	
	private String prepareTestMethod(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String descriptionProviderPart= (String) loadTemplatesInfo.get("testMethod_test");
        String rez = GeneratorUtil.substituteAll(descriptionProviderPart, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(TestMethodKey, rez);
        
        return rez;

	
	
	}

	
	// prepare description method body	
	private String prepareDescriptionMethodBody(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String top= (String) loadTemplatesInfo.get("descriptionTop_test");
		String bottom  =(String) loadTemplatesInfo.get("descriptionBottom_test");
		String middle= (String) loadTemplatesInfo.get("descriptionMiddle_test");
		
        StringBuilder sb=new StringBuilder();		
        Map<String,Object>[] ts= (Map<String,Object>[]) loadTestsInfo.get(loadTestsInfoPosition).get(InputProcessor.TEST_STEPS_KEY);   
        if (ts.length>0) {
            sb.append(top);
        }

        for(Map<String,Object> sa: ts) {
            // replaceAll relace /" with /
        	//"Step_Name" ; "Description" ;"Expected_Result"
            sb.append(GeneratorUtil.substituteAll(middle, sa));
        }

        sb.append(bottom);
        String rez = GeneratorUtil.substituteAll(sb.toString(), loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // save description implementation  
        loadTestsInfo.get(loadTestsInfoPosition).put(DescriptionMethodBodyKey, rez);
        
        return rez;
	}
	
	// prepare description provider fragment	
	private String prepareDescriptionProviderPart(int loadTestsInfoPosition,
			List<Map<String,Object>> loadTestsInfo,
			Map<String,Object> loadTemplatesInfo, 
			Map<String,Object> loadGroupInfo){

		// templates
		String descriptionProviderPart= (String) loadTemplatesInfo.get("descriptionProvider_test");
        String rez = GeneratorUtil.substituteAll(descriptionProviderPart, loadTestsInfo.get(loadTestsInfoPosition));
        
        System.out.println(rez);
        // store  
        loadTestsInfo.get(loadTestsInfoPosition).put(DescriptionProviderPartKey, rez);
        
        return rez;
	}


}
