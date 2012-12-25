package com.skyincity.qaa.pathfinder.log;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HourlyLogFileNamedManager implements ILogFileNamesManger {

	  // solr-webapp.log / solr-webapp.log.2011-10-10-10
	  // ssss-webapp.log.2011-12-27-17  / ssss-webapp.log
	
	/**
	 * someApp.log.2011-12-27-17  contains records with date 2011-12-27 and hour = 17; it appears at 18:00
	 * someApp.log  contains records with date Current and hour = Current from the start of current our  
	 */
	private String baseFileName=null;
	private SimpleDateFormat postfixFormatter = new SimpleDateFormat(".yyyy-MM-dd-hh");
	private Date curDt=new Date();
	
	public HourlyLogFileNamedManager(String baseFileName,
			SimpleDateFormat postfixFormatter) {
		super();
		this.baseFileName = baseFileName;
		this.postfixFormatter = postfixFormatter;
	}

	
	/**
	 * till - ignored for now 
	 * from - to be sure starts 3 min before
	 * 
	 * @return thomething like String[] {"webapp.log","webapp.log.2011-10-10-10","webapp.log.2011-10-10-09","webapp.log.2011-10-10-08"}
	 */
	public String[] getPotentialFileNames1(Date start, Date end, TimeZone logTZ) {
		postfixFormatter.setTimeZone(logTZ);
		
		
		List <String> rezLst= new ArrayList<String>();  

		
	   
	   Calendar currCl = Calendar.getInstance();
	   currCl.setTime(curDt);
	   currCl.set(Calendar.MINUTE,0);
	   currCl.set(Calendar.SECOND,0);
	   currCl.set(Calendar.MILLISECOND,0);
	   
	   
	   
	   //from=from- 1*60*1000;
	   Calendar startCl = Calendar.getInstance();
	   startCl.setTime(start);
	   
	   Calendar wrkCl = (Calendar) startCl.clone();
//	   wrkCl.add(Calendar.MINUTE, -3);
	   wrkCl.set(Calendar.MINUTE,0);
	   wrkCl.set(Calendar.SECOND,0);
	   wrkCl.set(Calendar.MILLISECOND,0);
	   System.out.println("wrkCl="+ postfixFormatter.format(wrkCl.getTime()));

	   
	   rezLst.add(baseFileName);// baseFileName
	   currCl.add(Calendar.HOUR, -1);
	   System.out.println("currCl="+ postfixFormatter.format(currCl.getTime()));
	   while (currCl.compareTo(wrkCl)>=0) {
		   rezLst.add(baseFileName+postfixFormatter.format(currCl.getTime())); 
		   currCl.add(Calendar.HOUR, -1);
		   System.out.println("currCl="+ postfixFormatter.format(currCl.getTime()));
	   }
			   
	   if (rezLst.size()==0){
		   return null;
	   } else {
		   return rezLst.toArray(new String[1]);
	   }
	}


	/**
	 * till - ignored for now 
	 * from - to be sure starts 3 min before
	 * 
	 * @return thomething like String[] {"webapp.log","webapp.log.2011-10-10-10","webapp.log.2011-10-10-09","webapp.log.2011-10-10-08"}
	 */
	public String[] getPotentialFileNames(Date start, Date end, TimeZone logTZ) {
    	postfixFormatter.setTimeZone(logTZ);
		
		List <String> rezLst= new ArrayList<String>();  
	   
	   Calendar currCl = Calendar.getInstance();
	   currCl.setTime(curDt);
	   currCl.set(Calendar.MINUTE,0);
	   currCl.set(Calendar.SECOND,0);
	   currCl.set(Calendar.MILLISECOND,0);
	   
	   Calendar startCl = Calendar.getInstance();
	   startCl.setTime(start);
	   startCl.set(Calendar.MINUTE,0);
	   startCl.set(Calendar.SECOND,0);
	   startCl.set(Calendar.MILLISECOND,0);
//	   System.out.println("startCl="+ postfixFormatter.format(startCl.getTime()));

	   Calendar endCl = Calendar.getInstance();
	   endCl.setTime(end);
	   endCl.set(Calendar.MINUTE,0);
	   endCl.set(Calendar.SECOND,0);
	   endCl.set(Calendar.MILLISECOND,0); 
//	   System.out.println("endCl="+ postfixFormatter.format(endCl.getTime()));
	   
	   // check do we need base file
	   if ( endCl.compareTo(currCl)>=0){
		   rezLst.add(baseFileName);// baseFileName
		   endCl=currCl;
		   endCl.add(Calendar.HOUR, -1);
	   }
	   
//	   System.out.println("endCl="+ postfixFormatter.format(endCl.getTime()));
	   while (endCl.compareTo(startCl)>=0) {
		   rezLst.add(baseFileName+postfixFormatter.format(endCl.getTime())); 
		   endCl.add(Calendar.HOUR, -1);
//		   System.out.println("endCl="+ postfixFormatter.format(endCl.getTime()));
	   }
			   
	   if (rezLst.size()==0){
		   return null;
	   } else {
		   return rezLst.toArray(new String[1]);
	   }
	}
	
	
	   public static void main(String[] args) {
		     try {
		    	 HourlyLogFileNamedManager hlfnm;
		    	 Date start;
		    	 Date end;
		    	 String[] rez;
		    	 
		    	 
		    	 // step 1   with base file US/Pacific
		    	 System.out.println("  ------  step 1   with base file US/Pacific ----------");
		    	 hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Pacific"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}

		     
		    	 // step 2  with base file US/Central
		    	 System.out.println("  ------  step 2  with base file US/Central ----------");
		    	hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Central"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}

		    	
		    	 // step 3  without base file US/Pacific
		    	 System.out.println("  ------  step 3  without base file US/Pacific ----------");
		    	hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 16:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Pacific"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}

		    	// step 4   without base file US/Central
		    	 System.out.println("  ------  step 4   without base file US/Central ----------");
		    	hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 16:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 15:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Central"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}


		    	 // step 5  - start before current US/Pacific
		    	 System.out.println("  ------  step 5  - start before current US/Pacific ----------");
		    	hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 16:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 17:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Pacific"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}

		    	// step 6 - start before current  US/Central
		    	 System.out.println("  ------  step 6 - start before current  US/Central ----------");
		    	hlfnm = new HourlyLogFileNamedManager("webapp.log",
		    			 new SimpleDateFormat(".yyyy-MM-dd-HH"));
		    	 hlfnm.curDt= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 16:02:37,483");
		    	 start=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 13:50:37,483");
		    	 end=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS").parse("2011-12-29 17:00:37,483");
		    	 rez= hlfnm.getPotentialFileNames(start, end, TimeZone.getTimeZone("US/Central"));
		    	for (String fn: rez) {
		    		System.out.println(fn);
		    	}

		     
		     } catch (Exception e) {
				System.out.println(e);
			 }
       } // mainfn


}
