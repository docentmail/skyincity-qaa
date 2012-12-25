package com.skyincity.qaa.util.sourcegen;

public class SampleTestSourcesGenerator {
	/** 
	 * folder where would be stored generated tests fragments
	 */
	final static String rezFolderPath ="C:\\My\\Temp\\qc\\Rez";
	/**
	 * path to this java project folder  
	 */
	private static String pathToJavaProj ="C:\\My\\com.skyincity.qaa\\gh_repo\\skyincityqaa\\src\\main\\java\\";
	
	public static void main(String argv[]) {
		  final String testsSourcesFolderPath =pathToJavaProj+ "com\\skyincity\\qaa\\util\\sourcegen\\sample\\sources";
		  final String templatesFolderPath =
				  pathToJavaProj+"com\\skyincity\\qaa\\util\\sourcegen\\sample\\templates";
			GeneratorManager generatorManager = new GeneratorManager(
					rezFolderPath,testsSourcesFolderPath,templatesFolderPath);
			try {
				generatorManager.process();
			} catch (Exception e) {

				e.printStackTrace();
			}
			
		}
}
	