package com.skyincity.qaa.pathfinder.artifact;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

public class UiPfArtifact extends BasePfArtifact {
	private static Logger log = Logger.getLogger(UiPfArtifact.class);
    // UI storage info
    protected String screenshot;
    protected String htmlSource;
    protected String pageUrl;    
    // local box path to stored files
    protected String screenshotFilePath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    protected String htmlSourceFilePath; 
    protected String pageUrlFilePath;  
    // local box path to stored files
    
    protected String screenshotWebPath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    protected String htmlSourceWebPath; 
    protected String pageUrlWebPath;  

    
    
	public UiPfArtifact(String name, String htmlDescription,Selenium selenium) {
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atUi;
			try {
				if (selenium != null) {
					
					try {
						screenshot = selenium
								.captureEntirePageScreenshotToString("");
						htmlSource = selenium.getHtmlSource();
						pageUrl = selenium.getLocation();
					} catch (Throwable e) {
						log.info("problem during taking screenshot"+e);
						log.info(CommonUtil.getStackTrace(e));
						
					}
				}
			} catch (Throwable e) {
				this.htmlDescription=this.htmlDescription+  "<br/>!!!!!!!!! Error during UiPfArtifact creation!!!!!!!!! ";
				log.error("Error during UiPfArtifact creation", e);
			}		
	}

	/**
	 * 
	 * @param name
	 * @param selenium  - may be null
	 */
	
	public UiPfArtifact(String name,Selenium selenium) {
		this(name, null,selenium);
	}
	
	public UiPfArtifact(String name, String htmlDescription, 
			String screenshot, String htmlSource, String pageUrl) {
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atUi;
        this.screenshot=screenshot; 
        this.htmlSource=htmlSource; 
        this.pageUrl=pageUrl;
	}
	
	public UiPfArtifact(String name,  
			String screenshot, String htmlSource, String pageUrl) {
		this (name,  null, screenshot, htmlSource, pageUrl);
 	}

	
	/**
	 * file:///C:/QAAuto/jenkins/jobs/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 * http://qa-win-auto:8080/details/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 * 
	 */
	public String toHTML() {
		if (!isStored()) {
			throw new IllegalStateException("could not construct toHTML() before call of storeArtifacts(); isArtifactsStored=false");
		}

		StringBuilder sb= new StringBuilder();
		sb.append("<b><i>** Artifact name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime) +"</i></b>");
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}
		// links to artifacts
		if (screenshotFilePath!= null) {
			sb.append("<br/> <a href=\""+screenshotWebPath+"\"  target=\"_blank\">open image in new window</a> ");
			sb.append("<img border=\"1\" alt=\""+name+"\" title=\"\" src=\""+screenshotWebPath+"\">");
		

		}
		if (htmlSourceFilePath!= null) {
			sb.append("<br/> <a href=\""+htmlSourceWebPath+"\"> html source file</a> ");
		}
		if (pageUrl!= null) {
			sb.append("<br/> page URL="+pageUrl  /*pageUrlWebPath+" "*/);
		}
		
		return sb.toString();
	}



	public void store(IFileStorage fileStorage) throws Exception{
		if (isStored()) {
			throw new IllegalStateException("wrong call of storeArtifacts() when isArtifactsStored=true");
		}
		String preffix = CommonUtil.getTimeAsHrMsString(creationTime)+"_";
		String osFileName=fileStorage.getBoxPathToFileFolder()+preffix+name;
		String webFileName=fileStorage.getWebPathToFileFolder()+preffix+name;
		
		// store image
		if (screenshot!= null) {
			screenshotFilePath=osFileName+".png";
			SeleniumUtil.saveScreenshotToPngFile(screenshot, screenshotFilePath);
			screenshotWebPath=webFileName+".png";
		}
		
		// Store HTML
		if (htmlSource!= null) {
			htmlSourceFilePath=osFileName+".html";
			FileUtils.writeStringToFile(new File(htmlSourceFilePath), htmlSource, "utf-8");
			htmlSourceWebPath=webFileName+".html";
		}
		
		// Store URL 
		if (pageUrl!= null) {
			pageUrlFilePath=osFileName+".url";
			FileUtils.writeStringToFile(new File(pageUrlFilePath), pageUrl, "cp1252");
			pageUrlWebPath=webFileName+".url";
		}
		isStored=true;
	}
    
    
}
