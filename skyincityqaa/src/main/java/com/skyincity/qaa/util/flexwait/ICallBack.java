package com.skyincity.qaa.util.flexwait;

/**
 * Interface responsible for 
 * - accumulate ISnapshots into ILogEvent
 * - if required: finally store accumulated ILogEvent into appropriate (Warning/Error) ILogEventStorage
 * - if required: finally output accumulated ILogEvent into TestNG.Report
 *
 */
public interface ICallBack {
	public void registerBeforeState(Object obj);
	public void logWaitOK(Object obj);
	public void logWaitWarning(Object obj);
	public void logError(Object obj);
	
}
