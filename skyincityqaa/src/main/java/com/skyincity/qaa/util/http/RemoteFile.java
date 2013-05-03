package com.skyincity.qaa.util.http;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * Class represents remote (accessible by http GET) file that going to be 
 * downloaded into temporary folder with File.deleteOnExit() attribute  
 */
public class RemoteFile {
	private static Logger log = Logger.getLogger(RemoteFile.class);
	private File localFile;
	private URL urlToServerFile;
	/**
	 * need this because URL sometimes does't contains file name 
	 * which could be obtained from external source (e.g. from the note on HTML page)
	 * and in this case URL.getFile()doesn't work
	 */
	private String OriginalfileNameOnServer;
	
	
	
	
	public RemoteFile(File localFile, URL urlToServerFile,
			String originalfileNameOnServer) {
		super();
		this.localFile = localFile;
		this.urlToServerFile = urlToServerFile;
		OriginalfileNameOnServer = originalfileNameOnServer;
	}

	public String getOriginalfileNameOnServer(){
		if (urlToServerFile== null){
			return null;
		}
		return urlToServerFile.getFile();
	}

	public boolean isFileLocallyAvailable(){
		return localFile == null;
	}
	
	
	public File getRemoteFile(){
		 if (isFileLocallyAvailable()) {
			 return localFile;
		 }
		 
		 return null;
		 

	}

	@Override
	public String toString() {
		return "RemoteFile [localFile=" + localFile + ", urlToServerFile="
				+ urlToServerFile + ", OriginalfileNameOnServer="
				+ OriginalfileNameOnServer + "]";
	}
	
	
	

}
