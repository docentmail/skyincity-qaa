package com.skyincity.qaa.util.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;


public class CommonUtil {
	private static Logger log = Logger.getLogger(CommonUtil.class);	
	private final static SimpleDateFormat format1 =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	private final static SimpleDateFormat format2 =
	        new SimpleDateFormat("HH_mm_ss_SSS");

	
	// between 
	public static Date convertBetweenTimezones(Date dateFromTZ, TimeZone fromTZ, TimeZone toTZ){
		 Date  curr= new Date();
		 SimpleDateFormat sdfHere= new  SimpleDateFormat();
		 System.out.println("Here:"+ sdfHere.format(curr));

		 
		 SimpleDateFormat sdfFrom= new  SimpleDateFormat();
		 sdfFrom.setTimeZone(fromTZ);
		 System.out.println("fromTZ:"+ sdfFrom.format(curr));
		 
		 
		 SimpleDateFormat sdfTo= new  SimpleDateFormat();
		 sdfTo.setTimeZone(toTZ);
		 System.out.println("toTZ:"+ sdfTo.format(curr));
		 
		 
		 
//	 System.out.println("sdf.getTimeZone()="+ sdfTo.getTimeZone());
//		
//		 Calendar toCalendar = new GregorianCalendar(toTZ);
//		 
//		 japanCal.setTimeInMillis(dateThisTZ.getTime());
		 
		 
		 
		return null;
	}
	
	public static void main (String [] arg) {
		convertBetweenTimezones(new Date(), TimeZone.getTimeZone("US/Pacific"), TimeZone.getTimeZone( "US/Central"));
	}
	

    public static String substitute(String s1, String s2, String origSt ) {
    	if (origSt==null) {
    		return null; 
    	}
    	StringBuilder sb=new StringBuilder();
    	for (int i=0; i<origSt.length(); i++) {
    		
    		int j=s1.indexOf(	origSt.charAt(i));
    		if (j<0) {
    			sb.append(origSt.charAt(i));
    		} else {
    			sb.append(s2.charAt(j));
    		}
    	
    	}
    	return sb.toString();
    }	
    
    // 2010-06-03 15:00:07,804
    public static String getCurrentTime() {
    	Date dt=new Date();
    	
    	return format1.format(dt);
    	
    }

    // 2010-06-03 15:00:07,804 
    public static String getTimeAsString(Date dt) {
    	return format1.format(dt);
    	
    }

 
    /**
     * convert 2010-06-03 15:00:07,804 to 15_00_07_804
     *  
     * @param dt
     * @return 15_00_07_804
     */
    public static String getTimeAsHrMsString(Date dt) {
    	return format2.format(dt);
    	
    }

    public static boolean isFileExist(String fileFullPath) throws Exception {
    	File fl=new File(fileFullPath);
    		if (fl.exists() && fl.isFile() ){
    			return true;
    		}
    		return false;
		
	}
    
	 /**
	  * Store stack trace of Throwable into as String.
	  * Todo - add aThrowable.getCause() - done!
	  */
	  public static String getStackTrace(Throwable aThrowable) {
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder(" Stack Trace:");
	    result.append(getStackTraceWithoutCauses(aThrowable));
	    
	    Throwable cause =aThrowable.getCause();
	    while (cause!= null){
	    	 result.append("\n -- causes by:\n ");
	    	 result.append(getStackTraceWithoutCauses(cause));
	    	cause =cause.getCause();
	    }
	    return result.toString();
	  }

	  private static String getStackTraceWithoutCauses(Throwable aThrowable) {
		    //add the class name and any message passed to constructor
		    final StringBuilder result = new StringBuilder();
		    result.append(aThrowable.toString());
		    final String NEW_LINE = "\n";//System.getProperty("line.separator");
		    result.append(NEW_LINE);

		    //add each element of the stack trace
		    for (StackTraceElement element : aThrowable.getStackTrace() ){
		      result.append( element );
		      result.append( NEW_LINE );
		    }
		    return result.toString();
		  }

	  
	  /**
	   * converts calendar.get(Calendar.MONTH) into readable string like "January", "February" 
	   * 
	   * @param pMonth
	   * @return
	   */
	  public static String getMonthForInt(int pMonth) {
		    String month = "invalid";
		    DateFormatSymbols dfs = new DateFormatSymbols();
		    String[] months = dfs.getMonths();
		    if (pMonth >= 0 && pMonth <= 11 ) {
		        month = months[pMonth];
		    }
		    return month;
		}

	  /**
	   * calculates difference between to java.util.Date and return delta in human readable form  
	   * 
	   * @param timeStart
	   * @param timeStop
	   * @return string like "4h:7m:23s"
	   */
	  public static String getHumanRadableTimeDelta(long timeStart, long timeStop) {
		  int delta = (int)((timeStop-timeStart)/1000);
		  return CommonUtil.getHoursFromSec(delta)+"h:"+CommonUtil.getMinsFromSec(delta)+"m:"+CommonUtil.getSecsFromSec(delta)+"s";
	  }

	  /**
	   * return delta in human readable form  
	   * 
	   * @param timeDeltaMilliseconds
	   * @return string like "4h:7m:23s"
	   */
	  public static String getHumanRadableTimeFromMiliseconds(long timeDeltaMilliseconds) {
		  int delta = (int)((timeDeltaMilliseconds)/1000);
		  return CommonUtil.getHoursFromSec(delta)+"h:"+CommonUtil.getMinsFromSec(delta)+"m:"+CommonUtil.getSecsFromSec(delta)+"s";
	  }

    public static int getSecsFromSec(int pSec)
    {
        return pSec % 60;
    }

    public static int getMinsFromSec(int pSec)
    {
        return ((pSec - getSecsFromSec(pSec)) / 60) % 60;
    }

    public static int getHoursFromSec(int pSec)
    {
        return (pSec - getMinsFromSec(pSec) * 60 - getSecsFromSec(pSec)) / 3600;
    }
	    
    

    /**
     * Outputs Map<String, String> as HTML.
     * @param map to output
     * @return HTML String
     */
    public static String getUIParametersString(Map<String, String> map) {
        if (map == null || map.isEmpty())
            return "";
        StringBuffer str = new StringBuffer();
        for (String paramName : map.keySet()) {
            str.append("<br/>").append(paramName).append(" : ").append(map.get(paramName));
        }
        return str.toString();
    }

    public static String getCollectionParametersString(Collection collection) {
        if (collection == null || collection.isEmpty())
            return "";
        StringBuffer str = new StringBuffer();
        for (Object paramName : collection) {
            str.append("<br/>").append(paramName.toString());
        }
        return str.toString();
    }
    
    /**
     * sleep with replacement of checked InterruptedException with unchecked RuntimeException   
     * 
     * @param milliseconds
     */
    public static void sleepWithNoException(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e); 
        }		
    }


	public static String normalizeWhitespaces(String s) {
		if (s==null) {
			return null;
		}
		 s= s.trim();
		StringBuffer res = new StringBuffer();
		int prevIndex = 0;
		int currIndex = -1;
		int stringLength = s.length();
		String searchString = "  ";
		while ((currIndex = s.indexOf(searchString, currIndex + 1)) >= 0) {
			res.append(s.substring(prevIndex, currIndex + 1));
			while (currIndex < stringLength && s.charAt(currIndex) == ' ') {
				currIndex++;
			}
			prevIndex = currIndex;
		}
		res.append(s.substring(prevIndex));
		return res.toString();
	}
	
	/**
	 * Escapes special characters (no '\n') for HTML
	 * @param str
	 * @return
	 */
	public static String escapeHtml(String str) {
		return StringEscapeUtils.escapeHtml(str);
	}
	

	/**
	 * replace '/n' new line into HTML style <br/>
	 * @param str
	 * @return
	 */
	public static String convertNLtoBR(String str) {
		return StringUtils.replace(str, "\n", "<br/>");
	}

	/**
	 * Escapes special characters (no '\n') for HTML
	 * @param str
	 * @return
	 */
	public static String escapeHtmlConvertNLtoBR(String str) {
		return convertNLtoBR(escapeHtml(str));
	}

	public static String bodyToHtml(String body, String title){
		String page= "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n\t\t" +
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n\t\t" +
				"<head>\n\t\t<title>"+title+"</title>\n\t\t" +
				// show/hide element by Id JS
				"<script type=\"text/javascript\">  \nfunction toggleview(element1) {  \n  \n   element1 = document.getElementById(element1);  \n  \n   if (element1.style.display == \'block\' || element1.style.display == \'\')  \n      element1.style.display = \'none\';  \n   else  \n      element1.style.display = \'block\';  \n  \n   return;  \n}  \n \n</script> "+		
				
				"</head>\n\t\t" +
				"<body>\n\t\t" +
				body+
				"\n</body></html>\n";
		return page;
	}
	
	
}


