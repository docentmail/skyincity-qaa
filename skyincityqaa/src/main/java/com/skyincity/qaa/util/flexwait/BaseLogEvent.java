package com.skyincity.qaa.util.flexwait;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.flexwait.ISnapshot.SnapshotTypeEnum;

public class BaseLogEvent implements ILogEvent {
	protected List<ISnapshot> registeredBeforeStates;
	protected ISnapshot finalSnapshot;
	protected String eventTitle;
	protected String eventDescription;
	protected String problemDescription;
    protected boolean isArtifactsStored=false;
    protected Throwable throwable;  
	
	public BaseLogEvent(String title, String enevtDescription) {
		super();
		this.eventTitle = title;
		this.eventDescription = enevtDescription;
	}
	
	public String toHTML() {

 		StringBuilder sb= new StringBuilder();
		// prepare head
		sb.append("\n\n========= Event Title =["+eventTitle+"]  Problem Type =[" + getFinalStateType()+"] ============" );
		if (eventDescription!=null){
		   sb.append("\nEvent Description =["+eventDescription+"]  ");
		}
		if (problemDescription!=null){
			   sb.append("\nProblem Description =["+problemDescription+"]  ");
		}
		if (throwable!=null){
			   sb.append("\nThrowable = ["+throwable+"]  ");
			   sb.append("\nStack trace: " +CommonUtil.getStackTrace(throwable));
		}
		String head=    CommonUtil.convertNLtoBR(  CommonUtil.escapeHtml(sb.toString())); 

		// prepare list of BeforeStates
		sb.setLength(0);
		if (registeredBeforeStates!=null && registeredBeforeStates.size()>0) {
			sb.append("<br/>--- registered before states -------");
			for (ISnapshot snapshot: registeredBeforeStates ) {
				sb.append("<br/>").append(snapshot.toHTML());
			}
		}
		String beforeStates = sb.toString();

		// prepare final snapshot
		sb.setLength(0);
		sb.append("<br/>--- final state ------- Problem Type =[" + getFinalStateType()+"]");
		sb.append("<br/>").append(finalSnapshot.toHTML());
		String finalState = sb.toString();
		
		return head+beforeStates+ finalState;
	}


	public String toString() {
		return null;
	}


	public void addBeforeState(ISnapshot snapshot) {
		if (registeredBeforeStates==null) {
			registeredBeforeStates= new ArrayList<ISnapshot>(); 
		}
		registeredBeforeStates.add(snapshot);
	}


	public void addFinalState(ISnapshot snapshot) {
		finalSnapshot=snapshot;
	}


	public SnapshotTypeEnum getFinalStateType() {
		if (finalSnapshot==null) {
			return null;
		} else {
			return finalSnapshot.getType(); 
		}
	}

	@Override
	public void storeArtifacts(ILogEventsStorage logEventsStorage) throws Exception {
		if (isArtifactsStored) {
			throw new IllegalStateException("wrong call of storeArtifacts() when isArtifactsStored=true");
		}
		// store screenshots
		if (registeredBeforeStates!=null && registeredBeforeStates.size()>0) {
			for (ISnapshot snapshot: registeredBeforeStates ) {
				snapshot.storeArtifacts(logEventsStorage);
			}
		}
		finalSnapshot.storeArtifacts(logEventsStorage);
		
		isArtifactsStored=true;
	}

	public boolean isArtifactsStored() {
		return isArtifactsStored;
	}

	@Override
	public void setThrowable(Throwable e) {
		throwable=e;
		
	}

	@Override
	public Throwable getThrowable() {
		return throwable;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	
	
}
