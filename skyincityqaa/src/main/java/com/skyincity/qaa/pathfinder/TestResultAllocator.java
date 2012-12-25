package com.skyincity.qaa.pathfinder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestNGMethod;

/**
 * unnab
	 Patunnhfinder ReportStructure
	= Build
	  =PthFndr
	   = ClassnameTestname_hhmmSSmmm
	     -emailableCopy.html 
	        Link to UI hrono,links to logs,
	     -UI_hrono.html
	     -ByStory.html
	       All Stories with all artifacts
	     =Artifacts
	       -18_37_16_640_final Error snapshot.html
	       -18_37_16_640_final Error snapshot.png
	       -18_37_16_640_final Error snapshot.url
	       -LogName_Server_log

  	"build*" and "build_PthFndr*" folders info initialized by application - resultAllocator in constructor

  
 * 
 */
public class TestResultAllocator implements  IPfResultAllocator{

	protected final Date testStartDate;
	protected final Class theTestClass;
	protected final ITestNGMethod theTestMethod;
	protected final String testFolderName;
    private final IPfResultAllocator resultAllocator;
  
    

	public TestResultAllocator(Date testStartDate, Class theTestClass,
			ITestNGMethod theTestMethod, IPfResultAllocator resultAllocator) {
		super();
		this.testStartDate = testStartDate;
		this.theTestClass = theTestClass;
		this.theTestMethod = theTestMethod;
		this.resultAllocator = resultAllocator;

	    String classSimpleName=theTestClass.getSimpleName();
		String methodSimpleName=theTestMethod.getMethodName();
	    final SimpleDateFormat format2 = new SimpleDateFormat("HHmmssSSS");
		String testTime= format2.format(testStartDate);
		
		testFolderName=classSimpleName+"."+methodSimpleName+"."+testTime;
	}

      
    

	public String getPath(String rezKey){
/*
static String buildBOX ="build(box)";
static String buildWEB ="build(web)";	
static String build_PthFndrBOX ="build_PthFndr(box)";
static String build_PthFndrWEB ="build_PthFndr(web)";	
static String build_PthFndr_theTestBOX ="build_PthFndr_theTest(box)";
static String build_PthFndr_theTestWEB ="build_PthFndr_theTest(web)";	
static String build_PthFndr_theTest_ArtifactsBOX ="build_PthFndr_theTest_Artifacts(box)";
static String build_PthFndr_theTest_ArtifactsWEB ="build_PthFndr_theTest_Artifacts(web)";	
*/

		
		if (rezKey.equals(build_PthFndr_theTestBOX)) {
			return resultAllocator.getPath(build_PthFndrBOX)+getFileSeparator()+testFolderName;
		} else 	if (rezKey.equals(build_PthFndr_theTestWEB)) {
			return resultAllocator.getPath(build_PthFndrWEB)+"/"+testFolderName;
		} else	if (rezKey.equals(build_PthFndr_theTest_ArtifactsBOX)) {
			return resultAllocator.getPath(build_PthFndrBOX)+getFileSeparator()+testFolderName+getFileSeparator()+"Artifacts";
		} else	if (rezKey.equals(build_PthFndr_theTest_ArtifactsWEB)) {
			return resultAllocator.getPath(build_PthFndrWEB)+"/"+testFolderName+"/"+"Artifacts";
		} else {
			return resultAllocator.getPath(rezKey);
		}
		
	}
	public String getFileSeparator(){
		return resultAllocator.getFileSeparator();
	}
}


