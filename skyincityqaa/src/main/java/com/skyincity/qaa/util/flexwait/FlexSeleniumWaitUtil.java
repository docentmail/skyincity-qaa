package com.skyincity.qaa.util.flexwait;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class FlexSeleniumWaitUtil {

	public static void clickAndWaitForPageToLoad( Selenium selenium, String clickLocator,ILogEventsStorageFactory ilcf, 
			long okLimitInMilliseconds,long warnLimitInMilliseconds , String eventDescription ){
		ICallBack callBack1 =new UICallBack(selenium,ilcf, "ClickAndWaitForPageToLoad", eventDescription, ilcf.isLogOkRequired());
		callBack1.registerBeforeState(null);
	    selenium.click(clickLocator);

		waitForPageToLoad( selenium, ilcf.getOpenOkLimitMs(), ilcf.getOpenWarnLimitMs(),callBack1);
		
	}
	
	public static void clickAndWaitForPageToLoad( Selenium selenium, String clickLocator, ILogEventsStorageFactory ilcf, String eventDescription ){
		long okLimitInMilliseconds =ilcf.getOpenOkLimitMs();
		long warnLimitInMilliseconds=ilcf.getOpenWarnLimitMs();
		clickAndWaitForPageToLoad(  selenium,  clickLocator, ilcf, okLimitInMilliseconds, warnLimitInMilliseconds,eventDescription );
	}
	
	
	/**
	 * 
	 * 
	 * @param url
	 * @param selenium
	 * @param okLimitInMilliseconds
	 * @param warnLimitInMilliseconds
	 * @param callBack
	 */
	public static void open(String url, Selenium selenium, long okLimitInMilliseconds,long warnLimitInMilliseconds,ICallBack callBack){
        long start = System.currentTimeMillis();
		try {
			selenium.setTimeout(String.valueOf(warnLimitInMilliseconds));
			selenium.open(url);
		} catch (SeleniumException e) {
			long deltaMs= System.currentTimeMillis()- start;
    		callBack.logError(new Long(deltaMs));
    		throw e;
		}

		long deltaMs= System.currentTimeMillis()- start;
		if (deltaMs <= okLimitInMilliseconds ) {
    		callBack.logWaitOK(new Long(deltaMs));
    	} else {
    		callBack.logWaitWarning(new Long(deltaMs));
    	}
	}


	public static void open(String url, Selenium selenium, ILogEventsStorageFactory ilcf, String eventDescription){
		ICallBack callBack =new UICallBack(selenium,ilcf, "Open Page", eventDescription, ilcf.isLogOkRequired());
		long okLimitInMilliseconds =ilcf.getOpenOkLimitMs();
		long warnLimitInMilliseconds =ilcf.getOpenWarnLimitMs();
		
		open(url, selenium, okLimitInMilliseconds,warnLimitInMilliseconds,callBack);
	}


	
	/**
	 * useful callBack.registerBeforeState(obj) before 
	 * @param selenium
	 * @param okLimitInMilliseconds
	 * @param warnLimitInMilliseconds
	 * @param callBack
	 */
	public static void waitForPageToLoad( Selenium selenium, long okLimitInMilliseconds,long warnLimitInMilliseconds,ICallBack callBack ){
        long start = System.currentTimeMillis();
		try {
			selenium.waitForPageToLoad(String.valueOf(warnLimitInMilliseconds));
		} catch (SeleniumException e) {
			long deltaMs= System.currentTimeMillis()- start;
    		callBack.logError(new Long(deltaMs));
    		throw e;
		}

		long deltaMs= System.currentTimeMillis()- start;
		if (deltaMs <= okLimitInMilliseconds ) {
    		callBack.logWaitOK(new Long(deltaMs));
    	} else {
    		callBack.logWaitWarning(new Long(deltaMs));
    	}
	}
		
	
	
//	o	selenium.waitPageLoad – SeleniumHelper. waitPageLoad
//	o	selenium.waitForPopUp – SeleniumHelper. waitForPopUp
//	o	selenium.waitForCondition - SeleniumHelper. waitForCondition
//	o	selenium.waitForFrameToLoad -SeleniumHelper.waitForFrameToLoad
// void waitForPopUp(String windowID,String timeout);	

	
}
