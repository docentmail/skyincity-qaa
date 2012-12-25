package com.skyincity.qaa.pathfinder.snapshot;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.pathfinder.artifact.IPfArtifact;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.common.UniqueUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

public class BasePfSnapshot implements IPfSnapshot {
    protected List<IPfArtifact> pfArtifacts=new ArrayList<IPfArtifact>();  
	protected String name;
    protected String htmlDescription;
    protected String stacktrace;
    protected Date creationTime;
    protected boolean isArtifactsStored=false;
    
//    // UI storage info
//    protected String screenshot;
//    protected String htmlSource;
//    protected String pageUrl;    
//    // storage info created during artifact is stored
//    protected String screenshotFilePath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
//    protected String htmlSourceFilePath; 
//    protected String pageUrlFilePath;  

    
    
	public BasePfSnapshot(String name, String htmlDescription, IPfArtifact art) {
		super();
		this.name = name;
		this.htmlDescription = htmlDescription;
		stacktrace = CommonUtil.getStackTrace(new Throwable());
		creationTime=new Date();
		if (art!=null) {
			pfArtifacts.add(art);
		}
	}
	public BasePfSnapshot(String name, String htmlDescription) {
		this(name, htmlDescription, null);
	}

	
    public void addArtifact(IPfArtifact art){
    	pfArtifacts.add(art);
    }

	public String toHTML() {
		if (!isArtifactsStored) {
			throw new IllegalStateException("could not construct toHTML() before call of storeArtifacts(); isArtifactsStored=false");
		}
		// header
		StringBuilder sb= new StringBuilder();
		sb.append("<b>**** Snapshot name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime)+"</b>");
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}

		// stacktrace with show/hide javascript
		String divId=((new Date()).getTime())+""+creationTime.getTime()+UniqueUtil.getUniqueCharString(5);
		
		sb.append("<br/> Stacktrace: "+
		"<a href=\"javascript:toggleview(\'"+divId+"\')\">show/hide stacktrace</a>"+
		"<div id=\""+divId+"\" style=\"background-color:green;display:none;\"   display = \"none\">"+		
		"<br/> "+CommonUtil.escapeHtmlConvertNLtoBR(stacktrace)+
		"</div>"
		);

		// artifacts
		if (pfArtifacts != null && pfArtifacts.size()>0) {
			sb.append("<br/> Artifacts for the snapshot <br/> ");

			for (IPfArtifact pfa:pfArtifacts) {
				sb.append(pfa.toHTML()+ "<br/>");
			}
		}
		
		return sb.toString();
	}





	public void store(IFileStorage fileStorage) throws Exception{
		if (isArtifactsStored) {
			throw new IllegalStateException("wrong call of store() when isArtifactsStored=true");
		}
//		String preffix = CommonUtil.getTimeAsHrMsString(creationTime)+"_";
		for (IPfArtifact pfa:pfArtifacts) {
			pfa.store(fileStorage);
		}
		isArtifactsStored=true;
	}
	


	@Override
	public boolean isPfArtifactsStored() {
		return isArtifactsStored;
	}
	
}

