package com.skyincity.qaa.pathfinder.artifact;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

public class LogPfArtifact extends BasePfArtifact {
	private static Logger log = Logger.getLogger(LogPfArtifact.class);

	// file info
    protected String fileContent=null;
    protected File file=null;

    // local box path to stored files
    protected String fileBoxPath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    // local box path to stored files
    protected String fileWebPath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG

    
    
	public LogPfArtifact(String name, String htmlDescription,String fileContent) {
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atFile;
        this.fileContent=fileContent; 
	}
	public LogPfArtifact(String name, String htmlDescription,File file) {
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atFile;
        this.file=file; 
	}

	public String toHTML() {
		if (!isStored()) {
			throw new IllegalStateException("could not construct toHTML() before call of storeArtifacts(); isArtifactsStored=false");
		}
		StringBuilder sb= new StringBuilder();
		sb.append("<b><i>** Artifact name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime)+"</i></b>");
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}
		// links to artifacts
		if (fileBoxPath!= null) {
			sb.append("<br/> <a href=\""+fileWebPath+"\"> "+name+" - log file fragment</a> ");
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
		
		
		// Store log
			fileBoxPath=osFileName+".log";
			FileUtils.writeStringToFile(new File(fileBoxPath), fileContent, "utf-8");
			fileWebPath=webFileName+".log";
		isStored=true;
	}
    
    
}
