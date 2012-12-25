package com.skyincity.qaa.util.flexwait;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

public class BaseSnapshot implements ISnapshot {
    protected String name;
    protected String htmlDescription;
    protected String Stacktrace;
    protected Date creationTime;
    protected ISnapshot.SnapshotTypeEnum snaphotType;
    protected boolean isArtifactsStored=false;
    
    // UI storage info
    protected String screenshot;
    protected String htmlSource;
    protected String pageUrl;    
    // storage info created during artifact is stored
    protected String screenshotFilePath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    protected String htmlSourceFilePath; 
    protected String pageUrlFilePath;  

    
    
	public BaseSnapshot(String name, String htmlDescription, SnapshotTypeEnum snaphotType) {
		super();
		this.name = name;
		this.htmlDescription = htmlDescription;
		this.snaphotType = snaphotType;
		Stacktrace = CommonUtil.getStackTrace(new Throwable());
		creationTime=new Date();
	}

	public BaseSnapshot(String name, SnapshotTypeEnum snaphotType) {
		this(name, null,snaphotType);
	}
	

	public String toHTML() {
		if (!isArtifactsStored) {
			throw new IllegalStateException("could not construct toHTML() before call of storeArtifacts(); isArtifactsStored=false");
		}

		StringBuilder sb= new StringBuilder();
		sb.append("** Snapshot name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime));
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}
		// links to artifacts
		if (screenshotFilePath!= null) {
			sb.append("<br/> <a href=\""+(new File(screenshotFilePath)).toURI()+"\"> screenshot file</a> ");
		}
		if (htmlSourceFilePath!= null) {
			sb.append("<br/> <a href=\""+(new File(htmlSourceFilePath)).toURI()+"\"> html source file</a> ");
		}
		if (pageUrl!= null) {
			sb.append("<br/> page URL="+pageUrl+" ");
		}
		
		return sb.toString();
	}


	@Override
	public SnapshotTypeEnum getType() {
		return snaphotType;
	}


	@Override
	public void storeArtifacts(ILogEventsStorage logEventsStorage) throws Exception{
		if (isArtifactsStored) {
			throw new IllegalStateException("wrong call of storeArtifacts() when isArtifactsStored=true");
		}
		String preffix = CommonUtil.getTimeAsHrMsString(creationTime)+"_";
		// store image
		if (screenshot!= null) {
			String absoluteFilePath=logEventsStorage.getFileStorageDir().getAbsolutePath()+logEventsStorage.getFileSeparator()+preffix+name+".png";
			SeleniumUtil.saveScreenshotToPngFile(screenshot, absoluteFilePath);
			screenshotFilePath= absoluteFilePath;
		}
		
		// Store HTML
		if (htmlSource!= null) {
			String absoluteFilePath=logEventsStorage.getFileStorageDir().getAbsolutePath()+logEventsStorage.getFileSeparator()+preffix+name+".html";
			FileUtils.writeStringToFile(new File(absoluteFilePath), htmlSource, "utf-8");
			htmlSourceFilePath= absoluteFilePath;
		}
		
		// Store URL 
		if (pageUrl!= null) {
			String absoluteFilePath=logEventsStorage.getFileStorageDir().getAbsolutePath()+logEventsStorage.getFileSeparator()+preffix+name+".url";
			FileUtils.writeStringToFile(new File(absoluteFilePath), pageUrl, "cp1252");
			pageUrlFilePath= absoluteFilePath;
		}

		isArtifactsStored=true;
	}
	
	public void setUiInfo(Selenium selenium){
		if (selenium == null) return;
		screenshot="";//selenium.captureEntirePageScreenshotToString("");
		htmlSource="";//selenium.getHtmlSource();
		pageUrl=selenium.getLocation();
	}

	public boolean isArtifactsStored() {
		return isArtifactsStored;
	}

	
}
