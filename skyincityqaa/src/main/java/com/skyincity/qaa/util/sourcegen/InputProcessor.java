package com.skyincity.qaa.util.sourcegen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.skyincity.qaa.util.common.FileUtil;
import org.apache.commons.io.FileUtils;

import javax.xml.xpath.*;

/**
 * loads templates, group info, tests info
 * 
 * 
 *
 */
public class InputProcessor {
	public static final String TEST_STEPS_KEY ="STEPS";
	
	private static Logger log = Logger.getLogger(InputProcessor.class);
	private final String testsFolderPath;
	private final String templatesFolderPath;
	
	private XPathFactory factory = null;
	private XPath xPath = null;

	public InputProcessor(String testsFolderPath, String templatesFolderPath) {
		super();
		this.testsFolderPath = testsFolderPath;
		this.templatesFolderPath = templatesFolderPath;

		factory = XPathFactory.newInstance();
		xPath = factory.newXPath();
	}

	// read templates  *.template
	public Map<String,Object> loadTemplatesInfo() throws Exception{
		Map<String,Object> templateMap=new HashMap<String,Object>();
		
		File[] files = new File(templatesFolderPath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".template");
            }
        });

		// loop by templates 
        for (File file : files) {
        	templateMap.put(FileUtil.getFileNameOnly(file.getName()),
        			FileUtils.readFileToString(file) );
        }
        return templateMap;

	}

	// 
	/**
	 * read group info from group.xml and parses it into separate values
	 * 
	 * Map keys:
	 *  DescriptionPackage
	 *  DescriptionClassName
	 *  TestPackage
	 *  TestClassName
	 *  TestngGroupName
	 *  TestngXmlFileNameForFolder
	 *  DataBuilderPackage
	 *  DataBuilderClass
	 * 
	 * @return Map<String,Object> with strings
	 * @throws Exception
	 */
	public Map<String,Object> loadGroupInfo() throws Exception{
			Map<String,Object> groupInfo= new HashMap<String,Object>();

			File groupSourceFile = new File(testsFolderPath+File.separator+"group.xml");
			String testSrc=FileUtils.readFileToString(groupSourceFile);
			
			String text;

//			<!-- description --->
//			<DescriptionPackage>com.sssss.atest.description</DescriptionPackage>
//			<DescriptionClassName>AccountSwitchSmokeTestDescriptions</DescriptionClassName>
			text=GeneratorUtil.getTextByXpath(testSrc,"/group/DescriptionPackage");
			if (text!=null) {	groupInfo.put("DescriptionPackage", text);	}

			text=GeneratorUtil.getTextByXpath(testSrc,"/group/DescriptionClassName");
			if (text!=null) {	groupInfo.put("DescriptionClassName", text);	}

			
//			<!-- test -->
//			<TestPackage>com.ssssss.atest.test</TestPackage>
//			<TestClassName>AccountSwitchSmokeTestDescriptions</TestClassName>
//			<TestngGroupName>TheTestngGroupName</TestngGroupName>


			text=GeneratorUtil.getTextByXpath(testSrc,"/group/TestPackage");
			if (text!=null) {	groupInfo.put("TestPackage", text);	}

			text=GeneratorUtil.getTextByXpath(testSrc,"/group/TestClassName");
			if (text!=null) {	groupInfo.put("TestClassName", text);	}

			text=GeneratorUtil.getTextByXpath(testSrc,"/group/TestngGroupName");
			if (text!=null) {	groupInfo.put("TestngGroupName", text);	}

			text=GeneratorUtil.getTextByXpath(testSrc,"/group/TestngXmlFileNameForFolder");
			if (text!=null) {	groupInfo.put("TestngXmlFileNameForFolder", text);	}

			

//			<!-- dataBuilder -->
//			<DataBuilderPackage>com.qaautomation.ssss.databuilders.production</DataBuilderPackage>
//			<DataBuilderClass>ProductionVDItemMgmt_BulkUploadDataBuilder</DataBuilderClass>
			
			text=GeneratorUtil.getTextByXpath(testSrc,"/group/DataBuilderPackage");
			if (text!=null) {	groupInfo.put("DataBuilderPackage", text);	}

			text=GeneratorUtil.getTextByXpath(testSrc,"/group/DataBuilderClass");
			if (text!=null) {	groupInfo.put("DataBuilderClass", text);	}
			
			
			
			return groupInfo;		
	}

	/**
	 * reads tests info from ***test.xml  files
	 *   parses each xml and create Map<String,Object> for each test 
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> loadTestsInfo() throws Exception{
		List<Map<String,Object>> testInfo= new ArrayList<Map<String,Object>>(); 

		// prepare tests info files list
        File[] files = new File(testsFolderPath).listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith("test.xml");
            }
        });

		// loop by tests
        for (File file : files) {
        	Map<String,Object> testMap =loadSinglTest(file);
        	if (testMap!= null && !testMap.isEmpty()) {
        		testInfo.add(testMap);	
        	}
        }

		return testInfo;
	}
	

	
	private Map<String,Object> loadSinglTest(File testSourceFile) throws Exception{
		Map<String,Object> testMap= new HashMap<String,Object>();  
		
		// collect steps info	
		Map<String, String>[] rez;
		
		String testSrc=FileUtils.readFileToString(testSourceFile);
//		InputSource inputSource = new InputSource( new StringReader(testSrc));
		
		// load test test description
		String expression = "/defects/defect"; // each this node is one step
		NodeList nodeList = (NodeList) xPath.evaluate(expression, new InputSource( new StringReader(testSrc)),
				XPathConstants.NODESET);
		if (nodeList==null || nodeList.getLength()==0) {
			return null;
		}

		rez=new Map[nodeList.getLength()];
		
		// collect one step info
		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Map<String, String> stepMap = new HashMap<String, String>(); 
//			"Step_Name" ; "Description" ;"Expected_Result"
			

			Node node=null;
			node = (Node) xPath.evaluate("//defect["+(i+1)+"]/Step_Name", nodeList.item(i),XPathConstants.NODE);
			stepMap.put("Step_Name",formatString(node.getTextContent()));

			node = (Node) xPath.evaluate("//defect["+(i+1)+"]/Description", nodeList.item(i),XPathConstants.NODE);
			stepMap.put("Description",formatString(node.getTextContent()));

			node = (Node) xPath.evaluate("//defect["+(i+1)+"]/Expected_Result", nodeList.item(i),XPathConstants.NODE);
			stepMap.put("Expected_Result",formatString(node.getTextContent()));

			rez[i]=stepMap;

		}
		testMap.put(TEST_STEPS_KEY, rez);

		// load test test info
//		<MethodJavaName>testVD003DownloadBlankTemplate</MethodJavaName>
//		<QcCaseId>4446</QcCaseId>
//		<QcCasePath>SSSS =&gt; Production Test =&gt;  VD Item Mgmt =&gt; Bulk upload =&gt; Item Management. Vendor direct 003. Download blank template</QcCasePath>
		
		String text;
		text=GeneratorUtil.getTextByXpath(testSrc,"/defects/MethodJavaName");
		if (text!=null) {
			testMap.put("MethodJavaName", text);
		}

		text=GeneratorUtil.getTextByXpath(testSrc,"/defects/QcCaseId");
		if (text!=null) {
			testMap.put("QcCaseId", text);
		}

		text=GeneratorUtil.getTextByXpath(testSrc,"/defects/QcCasePath");
		if (text!=null) {
			testMap.put("QcCasePath", text);
		}


		text=GeneratorUtil.getTextByXpath(testSrc,"/defects/TestngSeparateTestXmlFileName");
		if (text!=null) {
			testMap.put("TestngSeparateTestXmlFileName", text);
		}


		
		return testMap;
	}
	
	private static String formatString(String origStr){
		// escape
		String rez=GeneratorUtil.escapeString(origStr);
		// replace \n with "+<br/>+"
		rez=rez.replaceAll("\\n", "<br/>\"+\n\t\t\t\t\t\t\" " );
		// add  frb+""+fre+    at the end
		return rez+ "\"+frb+\"\"+fre+\"";
	}

	public static void main(String argv[]) {
		try {
			final String testsFolderPath ="C:\\My\\Tmp\\qc\\Manufacturer";
			final String templatesFolderPath ="C:\\My\\com.skyincity.qaa\\code\\skyincityqaa\\src\\main\\java\\com\\skyincity\\qaa\\util\\sourcegen\\templates";

			InputProcessor ip = new InputProcessor(testsFolderPath,templatesFolderPath);
			List<Map<String, Object>> loadTestsInfo = ip.loadTestsInfo();
			System.out.println(loadTestsInfo);
		} catch (Exception e) {
            e.printStackTrace();
		} 
	}

	
}
