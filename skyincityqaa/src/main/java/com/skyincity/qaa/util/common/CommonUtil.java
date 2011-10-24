package com.skyincity.qaa.util.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;


public class CommonUtil {
	private static Logger log = Logger.getLogger(CommonUtil.class);	
	private final static SimpleDateFormat format1 =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

	

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
    
    /**
     * saves string with image produced by selenium.captureEntirePageScreenshotToString("") into file
     *  
     * @param base64Screenshot  - usually result of selenium.captureEntirePageScreenshotToString("")
     * @param imagePath - full path to image file to save (new file)
     * 	
     */
    public static void saveImageToFile(String base64Screenshot, String imagePath) throws Exception{
  	      byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
  	      
  	      FileOutputStream fos=null;
		try {
			  fos = new FileOutputStream(new File(imagePath));
			  fos.write(decodedScreenshot);
		} finally{
			if (fos != null) {
				fos.close();
			}
		}
  	      
    }

    
    
    // 2010-06-03 15:00:07,804
    public static String getCurrentTime() {
    	Date dt=new Date();
    	
    	return format1.format(dt);
    	
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
	  */
	  public static String getStackTrace(Throwable aThrowable) {
	    //add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder(" Stack Trace:");
	    result.append(aThrowable.toString());
	    final String NEW_LINE = System.getProperty("line.separator");
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

    /**
     * returns all descriptions as one string for class like TaxonomyTestDescriptions   
     */
    public static String getDescriptions(String theClass) throws Exception {
		StringBuilder rez = new StringBuilder();
		rez.append("<html><body>");
		Class c = Class.forName(theClass);
		Method m[] = c.getDeclaredMethods();
		for (int i = 0; i < m.length; i++) {
			//System.out.println(m[i].toString());
			Object o = m[i].invoke(null, null);
			rez.append("<br/> <h2>" + m[i].getName() + "</h2><br/> ");
			rez.append(o);
		}
		rez.append("</body></html>");
		return rez.toString();
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
    
}
