package com.skyincity.qaa.util.flexwait;

import java.io.File;
import java.util.Date;

import com.skyincity.qaa.util.common.CommonUtil;
import com.thoughtworks.selenium.Selenium;

public class UICallBack implements ICallBack{
	protected Selenium selenium;
	protected ILogEventsStorageFactory logEventsStorageFactory;
	protected ILogEvent logEvent;
	protected boolean isLogOkRequired;
	
	
	
	public UICallBack(Selenium selenium,
			ILogEventsStorageFactory logEventsStorageFactory, String title, String enevtDescription) {
		this (selenium,logEventsStorageFactory, title, enevtDescription, false);
	}

	public UICallBack(Selenium selenium,
			ILogEventsStorageFactory logEventsStorageFactory, String title, String enevtDescription, boolean isLogOkRequired) {
		super();
		this.selenium = selenium;
		this.logEventsStorageFactory = logEventsStorageFactory;
		this.isLogOkRequired = isLogOkRequired;
		this.logEvent=new BaseLogEvent(title, enevtDescription);
	}

	@Override
	public void registerBeforeState(Object obj) {
		BaseSnapshot baseSnapshot= new BaseSnapshot("BeforeSnapshot", ISnapshot.SnapshotTypeEnum.BEFORE_STATE);
		baseSnapshot.setUiInfo(selenium);
		logEvent.addBeforeState(baseSnapshot);
	}

	@Override
	public void logWaitOK(Object obj) {
		addWaitingTimeToEventDescription(obj);
		if (isLogOkRequired){ // to avoid unnecessary getting screen shot 
		   logSnapshotEndStoreEvent("final OK snapshot", null ,ISnapshot.SnapshotTypeEnum.OK_STATE);
		}
	}

	@Override
	public void logWaitWarning(Object obj) {
		addWaitingTimeToEventDescription(obj);
		if (obj != null && obj instanceof Long) {
			logSnapshotEndStoreEvent("final Warning snapshot", 
					"Actual waiting time is above OK limit ="+ CommonUtil.getHumanRadableTimeFromMiliseconds(((Long)obj).longValue())  ,
					ISnapshot.SnapshotTypeEnum.WARNING_STATE);
		} else {
			throw new IllegalArgumentException("expected delay im milliseconds not null Long but got "+ obj);
		}
	}

	@Override
	public void logError(Object obj) {
		addWaitingTimeToEventDescription(obj);
		if (obj != null && obj instanceof Long) {
			logSnapshotEndStoreEvent("final Error snapshot", 
					"Actual waiting time is above OK and Warning limits ="+ CommonUtil.getHumanRadableTimeFromMiliseconds(((Long)obj).longValue())  ,
					ISnapshot.SnapshotTypeEnum.ERROR_STATE);
		} else {
			throw new IllegalArgumentException("expected delay im milliseconds not null Long but got "+ obj);
		}
	}

	protected void logSnapshotEndStoreEvent(String snapshotName, String snapshotHtmlDescription, ISnapshot.SnapshotTypeEnum snapshotType) {
		// log snapshot
		BaseSnapshot baseSnapshot= new BaseSnapshot(snapshotName, snapshotHtmlDescription,snapshotType);
		baseSnapshot.setUiInfo(selenium);
		logEvent.addFinalState(baseSnapshot);
		Throwable eMarker = new Throwable("==== This is not real exception. This just Stack Trace marker ====");
		logEvent.setThrowable(eMarker);
		// add event to storage
		if (logEventsStorageFactory!=null) {
			ILogEventsStorage les;
			switch (snapshotType) {
			case OK_STATE:  les=logEventsStorageFactory.getOkLogEventsStorage(); break;
			case ERROR_STATE:  les=logEventsStorageFactory.getErrorLogEventsStorage(); break;
			case WARNING_STATE:  les=logEventsStorageFactory.getWarningLogEventsStorage(); break;
			default: 	throw new IllegalStateException("Storage for snapshotType="+snapshotType+" is not defined");
			}
			if (les!=null) {
				les.storeLogEvent(logEvent);
			}	
		}
	}
    
	protected void addWaitingTimeToEventDescription(Object obj){
		if (obj instanceof Long) {
			logEvent.setEventDescription(logEvent.getEventDescription()+" :: Actual wait time was: "+CommonUtil.getHumanRadableTimeFromMiliseconds(((Long)obj).longValue()));
		}
	}
	
}
