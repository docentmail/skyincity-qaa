package com.skyincity.qaa.pathfinder.log;

import java.util.Date;
import java.util.TimeZone;

public interface ILogFileNamesManger {

	  // webapp.log.2011-12-27-17  / webapp.log
	public String[] getPotentialFileNames(Date start, Date end, TimeZone logTZ);

}
