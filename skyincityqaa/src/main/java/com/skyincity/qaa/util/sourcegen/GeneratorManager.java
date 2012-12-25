package com.skyincity.qaa.util.sourcegen;

import java.util.List;
import java.util.Map;

public class GeneratorManager {

  private final String rezFolderPath;
  private final String testsFolderPath;
  private final String templatesFolderPath;

  private final InputProcessor  inputProcessor;
  private final TestProcessor  testProcessor;
  private final GroupProcessor groupProcessor;
  
  private  List<Map<String,Object>> loadedTestsInfo;
  private  Map<String,Object> loadTemplatesInfo; 
  private  Map<String,Object> loadGroupInfo;
  
  
  
	public GeneratorManager(String rezFolderPath, String testsFolderPath,
		String templatesFolderPath) {
			super();
			this.rezFolderPath = rezFolderPath;
			this.testsFolderPath = testsFolderPath;
			this.templatesFolderPath = templatesFolderPath;
			this.inputProcessor = new InputProcessor(this.testsFolderPath,this.templatesFolderPath);
			this.testProcessor = new TestProcessor(rezFolderPath); 
			this.groupProcessor = new GroupProcessor(this.rezFolderPath);
	}

  
 	public void process() throws Exception{

 		loadedTestsInfo = inputProcessor.loadTestsInfo();
 		loadTemplatesInfo = inputProcessor.loadTemplatesInfo();
 		loadGroupInfo = inputProcessor.loadGroupInfo();
 		
 		testProcessor.processTest( loadedTestsInfo,	loadTemplatesInfo,	loadGroupInfo );
 		
 		groupProcessor.processGroup(loadedTestsInfo, loadTemplatesInfo, loadGroupInfo ); 
 		
 	}
 	
 	
	public static void main(String argv[]) {
	  final String rezFolderPath ="C:\\My\\Tmp\\qc\\Manufacturer\\Rez";
	  final String testsFolderPath ="C:\\My\\Tmp\\qc\\Manufacturer";
	  final String templatesFolderPath ="C:\\My\\com.skyincity.qaa\\code\\skyincityqaa\\src\\main\\java\\com\\skyincity\\qaa\\util\\sourcegen\\templates";

		GeneratorManager generatorManager = new GeneratorManager(
				rezFolderPath,testsFolderPath,templatesFolderPath);
		try {
			generatorManager.process();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}

  
 	
	
}
