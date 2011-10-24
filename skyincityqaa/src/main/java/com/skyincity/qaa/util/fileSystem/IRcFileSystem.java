package com.skyincity.qaa.util.fileSystem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
public interface IRcFileSystem {
	
    /**
     *  is this is File and is this it exists
     * @param fileFullPath
     * @return
     * @throws Exception
     */
	public boolean isFileExist(String fileFullPath) throws Exception;
	
	/**
	 * wrapper for File.exists();
	 * @param fileFullPath
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String fileFullPath) throws Exception;
	
    void cleanDirectory(String path) throws IOException;
    public boolean delete(String path);
    public boolean isDirectory(String path);
    public boolean isFile(String path);
    /**
     *  Calculates list of absolute paths for files in specified folder 
     *  
     * @param path - specified folder 
     * @return
     */
    public String[] listFiles(String path);
    
    /// file URL to RC server
    public void  sendUrlToRcFile(URL fromUrl, String toPath)  throws Exception ;
    
    public InputStream  getInputStreamFromRcFile(String fromPath)  throws Exception ;
    


}
