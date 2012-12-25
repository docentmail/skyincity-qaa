package com.skyincity.qaa.pathfinder.log;

import java.util.Date;

public interface ILogContentExtractor {

	String extract(Date start,Date end, String logContent) throws Exception;
	
}
