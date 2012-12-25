package com.skyincity.qaa.pathfinder.log;

/**
 * interface for classes that access files (local, sftp)
 * Usually that is log file - all log files are in the same folder
 *
 */
public interface IFiler {

	public String getFileContent(String fileName) throws Exception;
	public String getLogFolderLocationInfo();
	
}
