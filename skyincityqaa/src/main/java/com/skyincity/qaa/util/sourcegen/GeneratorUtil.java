package com.skyincity.qaa.util.sourcegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.Map;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class GeneratorUtil {
    public final static String StartPlaseholderMarker="@#$_";
    public final static String EndPlaseholderMarker="_$#@";    

	
	private static Logger log = Logger.getLogger(GeneratorUtil.class);
	
	public static String escapeString(String origStr) {
		String rez=origStr;
		if (rez==null || rez.length()==0) {
			return "";
		}

		// "
		// rez=rez.replaceAll("\"","'"); - doesn't work
		StringBuilder sb=new StringBuilder();
		for (int i=0; i<rez.length(); i++) {
			if (rez.charAt(i)=='\"'  ) {
				sb.append("\\\"");
			} else {
				sb.append(rez.charAt(i));
			}

		}


		// $ to UDS
		// rez=rez.replaceAll("\"","'"); - doesn't work
		rez=sb.toString();
		sb=new StringBuilder();
		for (int i=0; i<rez.length(); i++) {
			if (rez.charAt(i)=='$'  ) {
				sb.append("USD");
			} else {
				sb.append(rez.charAt(i));
			}
		}

		return sb.toString();
	}
	
    public static String substituteAll(String template, Map<String,Object> actualInfo){
    	if (actualInfo == null || actualInfo.size()==0)  {
    		return template;
    	}

    	if (template == null || template.length()==0 || !template.contains(StartPlaseholderMarker))  {
    		return template;
    	}

    	
    	Set<String> keys= actualInfo.keySet();
    	
    	for (String key:keys) {
    		if (actualInfo.get(key) instanceof String) {
    			template=template.replace(StartPlaseholderMarker+key+EndPlaseholderMarker, (String)actualInfo.get(key));
    		}	
    	}
    	
    	if (template.contains(StartPlaseholderMarker) && template.contains(EndPlaseholderMarker) ) {
    		log.warn("some plaseholders were not replaced: Final text is:\n"+template);
    		
    	}
    	
    	return template;
    	
    }
	
    public static String getTextByXpath(String xml, String xpath) throws Exception{
    	XPathFactory factory = null;
    	XPath xPath = null;
		factory = XPathFactory.newInstance();
		xPath = factory.newXPath();


    	Node node;
		node = (Node) xPath.evaluate(xpath, new InputSource( new StringReader(xml)), XPathConstants.NODE);
		if (node!=null || node.getTextContent()!=null) {
			return node.getTextContent();		
		} else {
			return null;
		}

    }

	static public void writeToFile(File aFile, String aContents, boolean appendToFile) throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}

		// use buffering
		Writer output = new BufferedWriter(new FileWriter(aFile, appendToFile));
		try {
			// FileWriter always assumes default encoding is OK!
			output.write(aContents);
		} finally {
			output.close();
		}
	}

    
}
