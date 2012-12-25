package com.skyincity.qaa.pathfinder.artifact;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;


import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.common.UniqueUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

public class ThrowablePfArtifact extends BasePfArtifact {
	private static Logger log = Logger.getLogger(ThrowablePfArtifact.class);

	Throwable exception;
	
	// file info
    protected String fileContent=null;
    protected File file=null;

	public ThrowablePfArtifact(String name, String htmlDescription,Throwable exception) {
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atThrowable;
        this.exception=exception; 
	}
	

	public String toHTML() {
		StringBuilder sb= new StringBuilder();
		sb.append("<b><i>** Throwable artifact name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime)+"</i></b>");
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}
		
		if (exception!= null){
			sb.append("<br/> Error description: "+exception.getMessage()+"<br/> ");
			
			String stacktrace=CommonUtil.getStackTrace(exception);
			// stacktrace with show/hide javascript
			String divId=((new Date()).getTime())+""+creationTime.getTime()+UniqueUtil.getUniqueCharString(5);
			
			sb.append("<br/> Stacktrace: "+
			"<a href=\"javascript:toggleview(\'"+divId+"\')\">show/hide stacktrace</a>"+
			"<div id=\""+divId+"\" style=\"background-color:green;display:none;\"   display = \"none\">"+		
			"<br/> "+CommonUtil.escapeHtmlConvertNLtoBR(stacktrace)+
			"</div>"
			);

		}
		return sb.toString();
	}



	public void store(IFileStorage fileStorage) throws Exception{
		if (isStored()) {
			throw new IllegalStateException("wrong call of storeArtifacts() when isArtifactsStored=true");
		}
		isStored=true;
	}		
}
