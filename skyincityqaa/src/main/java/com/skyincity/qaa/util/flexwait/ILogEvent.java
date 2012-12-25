package com.skyincity.qaa.util.flexwait;

/**
 * 
 * TBD replace addBeforeState+addFinalState with 
 */
public interface ILogEvent {
	public String toHTML();
	public String toString();
	public void addBeforeState(ISnapshot snapshot );
	public void addFinalState(ISnapshot snapshot );
	public ISnapshot.SnapshotTypeEnum getFinalStateType();
	/**
	 * Stores all "big" artifacts (files, screenshots ..)
	 * Call this in case of Warning or failure before/during storing this WaitEvent in IWaitEventStorage
	 * to save RAM for test execution.
	 * @throws Exception 
	 */
	public void storeArtifacts(ILogEventsStorage logEventsStorage) throws Exception;
	public void setThrowable(Throwable e);
	public Throwable getThrowable();
	
	public String getEventTitle();
	public void setEventTitle(String eventTitle);

	public String getEventDescription();
	public void setEventDescription(String eventDescription);

}
