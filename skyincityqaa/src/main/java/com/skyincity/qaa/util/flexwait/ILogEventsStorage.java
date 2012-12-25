package com.skyincity.qaa.util.flexwait;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public interface ILogEventsStorage {
	// depends on OS File.separator
	public String getFileSeparator(); 
	public File getFileStorageDir();
	public void storeLogEvent(ILogEvent logEvent);
	public String getHtmlForAllEvents();
	public List<ILogEvent> getEventsList();


}
