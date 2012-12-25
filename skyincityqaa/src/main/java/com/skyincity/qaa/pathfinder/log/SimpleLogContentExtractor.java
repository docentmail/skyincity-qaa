package com.skyincity.qaa.pathfinder.log;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;

import com.skyincity.qaa.util.common.FileUtil;

public class SimpleLogContentExtractor implements ILogContentExtractor {

	private SimpleDateFormat prefixFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
	
	
	
	
	/**
	 * 
	 * @param prefixFormatter  like  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS")
	 */
	public SimpleLogContentExtractor(SimpleDateFormat prefixFormatter,TimeZone logTimeZone) {
		super();
		this.prefixFormatter = prefixFormatter;
		this.prefixFormatter.setTimeZone(logTimeZone);
	}


//	public SimpleLogContentExtractor(SimpleDateFormat prefixFormatter) {
//		super();
//		this.prefixFormatter = prefixFormatter;
//	}

	
	//2011-12-29 00:00:04,924 
	
	public String extract(Date start, Date end, String logContent) throws Exception {
		String smplPref="2011-12-29 00:00:04,924";
		StringBuilder rez=new StringBuilder();
		String lines[] = logContent.split("\\r?\\n");
		
		boolean collecting=false;
		for (String ln : lines ){
			if (ln.length()>=smplPref.length()) {
					String pref=ln.substring(0,"2011-12-29 00:00:04,924".length());
					
					try {
						 Date date = prefixFormatter.parse(pref);
						 if (date.getTime()<= end.getTime() && date.getTime()>= start.getTime()){
							 collecting=true;
						 } else {
							 collecting=false;
						 }
					} catch (ParseException e) {
						
					}
			}
			if (collecting) {
				rez.append(ln+"\n");
			}
		}
		return rez.toString();
	}

	   public static void main(String[] args) {
		     try {    
		    	 String log; 
		    	 SimpleLogContentExtractor slce;
		    	 String extracted;
		    	 System.out.println("---- timezone US/Pacific----");
		    	log=FileUtils.readFileToString(new File("C:\\My\\com.skyincity.qaa\\wrkFiles\\ssss-webapp.log.2011-12-29-10"), "UTF-8");
		    	slce = new SimpleLogContentExtractor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS"),TimeZone.getTimeZone("US/Pacific"));
		    	extracted = slce.extract(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 10:04:57,587"), 
		    			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 10:05:26,278"), log); 
      		    System.out.println(extracted);

		    	 System.out.println("---- timezone US/Central ----");
		    	log=FileUtils.readFileToString(new File("C:\\My\\com.skyincity.qaa\\wrkFiles\\ssss-webapp.log.2011-12-29-10"), "UTF-8");
		    	slce = new SimpleLogContentExtractor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS"),TimeZone.getTimeZone("US/Central"));
		    	extracted = slce.extract(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 08:04:57,587"), 
		    			new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 08:05:26,278"), log); 
     		    System.out.println(extracted);

		     } catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			 }
     } // mainfn
	
	
}
