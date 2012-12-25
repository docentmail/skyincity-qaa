package com.skyincity.qaa.util.flexwait;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class BaseLogEventsStorage implements ILogEventsStorage {
	protected List<ILogEvent> theStorage = Collections.synchronizedList(new ArrayList<ILogEvent>());  
	
	@Override
	public abstract String getFileSeparator();

	@Override
	public abstract File getFileStorageDir();
	

	public void storeLogEvent(ILogEvent logEvent)  {
		try {
			logEvent.storeArtifacts(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		theStorage.add(logEvent);
	}
	
	public String getHtmlForAllEvents() {
		StringBuilder sb= new StringBuilder(); 	
		synchronized(theStorage) {
		      Iterator<ILogEvent> i = theStorage.iterator(); // Must be in synchronized block
		      while (i.hasNext())
		    	  sb.append("<br/>"+i.next().toHTML());
		}
		return sb.toString();
	}
	
	public List<ILogEvent> getEventsList(){
		return theStorage;
	}
}