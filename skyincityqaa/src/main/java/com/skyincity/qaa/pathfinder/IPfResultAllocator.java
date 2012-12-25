package com.skyincity.qaa.pathfinder;



/**
Pathfinder ReportStructure: 
= [Build]  folder : key = "build"  
 = [PthFndr]  folder : key = "build_PthFndr"
  = [ClassnameTestname_hhmmSSmmm]  folder : key = "build_PthFndr_theTest"
    -[emailableCopy.html] file 
       Link to UI hrono,links to logs,
    -"UI_hrono.html] file
    -[ByStory.html] file
      All Stories with all artifacts
    =[Artifacts]  folder : key = "build_PthFndr_theTest_Artifacts"
      -[18_37_16_640_final Error snapshot.html] file
      -[18_37_16_640_final Error snapshot.png] file
      -[18_37_16_640_final Error snapshot.url] file
      -[LogName_Server_log" file
*/

public interface IPfResultAllocator {
	static String buildBOX ="build(box)";
	static String buildWEB ="build(web)";	
	static String build_PthFndrBOX ="build_PthFndr(box)";
	static String build_PthFndrWEB ="build_PthFndr(web)";	
	static String build_PthFndr_theTestBOX ="build_PthFndr_theTest(box)";
	static String build_PthFndr_theTestWEB ="build_PthFndr_theTest(web)";	
	static String build_PthFndr_theTest_ArtifactsBOX ="build_PthFndr_theTest_Artifacts(box)";
	static String build_PthFndr_theTest_ArtifactsWEB ="build_PthFndr_theTest_Artifacts(web)";	
	/**
	 * 
	 * @param key
	 * @return path without final file separator 
	 */
	String getPath(String key);
    String getFileSeparator();
}
