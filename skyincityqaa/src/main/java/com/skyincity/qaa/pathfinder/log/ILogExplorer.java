package com.skyincity.qaa.pathfinder.log;

import java.util.Date;

public interface ILogExplorer {

	public String getLogFragment(Date from, Date till) throws Exception;
	public String getLogReadableName();
	
	
	
}
