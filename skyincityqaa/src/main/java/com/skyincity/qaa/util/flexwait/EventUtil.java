package com.skyincity.qaa.util.flexwait;

import org.testng.Reporter;

import com.thoughtworks.selenium.Selenium;

public class EventUtil {
	public static void logErrorEventTestNg(ILogEventsStorageFactory lsfactory,Selenium selenium, 
			String enevtTitle, String enevtDescription,	Throwable throwable){
		if (lsfactory!=null && lsfactory.getErrorLogEventsStorage()!=null) {
			ILogEvent logEvent=new BaseLogEvent(enevtTitle, enevtDescription);
			if (throwable!=null){
				logEvent.setThrowable(throwable);
			}
			
			BaseSnapshot baseSnapshot= new BaseSnapshot("final Error snapshot", "final Error snapshot",ISnapshot.SnapshotTypeEnum.ERROR_STATE);
			baseSnapshot.setUiInfo(selenium);
			logEvent.addFinalState(baseSnapshot);
			lsfactory.getErrorLogEventsStorage().storeLogEvent(logEvent);
			Reporter.log(logEvent.toHTML());
		}
		
	}

}
