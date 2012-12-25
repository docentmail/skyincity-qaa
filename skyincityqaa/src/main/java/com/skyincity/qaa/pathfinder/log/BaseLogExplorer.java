package com.skyincity.qaa.pathfinder.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class BaseLogExplorer implements ILogExplorer {

   protected  ILogFileNamesManger  logFileNamesManger;
   protected  IFiler  filer;
   protected ILogContentExtractor logContentExtractor;
   protected  TimeZone logTimeZone;
   
   protected String logFileInfo="explored files: ";
   protected String logReadableName;
   
   String getUnitedLoogContent(Date start,Date end) throws Exception{
	   String[] files=logFileNamesManger.getPotentialFileNames(start, end,logTimeZone);
	   // insert list of files here and curret date
	   StringBuilder sb= new StringBuilder();
	   for (String fn: files){
		   String fileContent =filer.getFileContent(fn);
		   if (fileContent!= null && fileContent.trim().length()>0) {
			   sb.insert(0, fileContent);
		   } 
		   logFileInfo=logFileInfo+ fn+ ((fileContent== null)? "(not exists)" : "(size="+fileContent.length()+")")+"; ";
	   }
	   return sb.toString();
   }
   
	
	public BaseLogExplorer(String logReadableName, ILogFileNamesManger logFileNamesManger, IFiler filer,
		ILogContentExtractor logContentExtractor, TimeZone logTimeZone) {
	super();
	this.logFileNamesManger = logFileNamesManger;
	this.filer = filer;
	this.logContentExtractor = logContentExtractor;
	this.logTimeZone=logTimeZone;
	this.logReadableName=logReadableName;
}


	public String getLogFragment(Date start, Date end) throws Exception {
		String logContent= getUnitedLoogContent(start,end);
		String finalLogContent= logContentExtractor.extract(start, end, logContent);
		return "Log folder location: "+filer.getLogFolderLocationInfo()+"\n"+
				logFileInfo+"\n"+
				finalLogContent;
	}

	
	public String getLogReadableName(){
	return logReadableName;
	
	}	
	
	public static void main(String[] arg) {
	   try {
		   
		TimeZone logTimeZone=TimeZone.getTimeZone("US/Pacific");   
		BaseLogExplorer ble = new BaseLogExplorer("web application log", 
				   			 new HourlyLogFileNamedManager("ssss-webapp.log",  new SimpleDateFormat(".yyyy-MM-dd-HH")), 
			    			 new SftpFiler("patch-app1","sssspatch","sssss123","/home/sssspatch/logs/","UTF-8"), 
			    			 new SimpleLogContentExtractor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS"),logTimeZone),
			    			 TimeZone.getTimeZone("US/Pacific"));
		String rez=ble.getLogFragment(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-30 00:45:37,483"), new Date());
		System.out.println(rez);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   
	   
	}
	
}
