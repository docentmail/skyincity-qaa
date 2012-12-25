package com.skyincity.qaa.pathfinder.log;

import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.UserAuthenticator;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

public class SftpFiler implements IFiler {


    private String host = "patch-app1";  // Remote SFTP hostname
    private String user = "sssspatch";      // Remote system login name
    private String password = "sssss123";    // Remote system password
    private String remoteDir = "/home/sssspatch/logs/";
    private String encoding = "UTF-8";
    
    

	
	public SftpFiler(String host, String user, String password,
			String remoteDir, String encoding) {
		super();
		this.host = host;
		this.user = user;
		this.password = password;
		this.remoteDir = remoteDir;
		this.encoding = encoding;
	}

	public String getLogFolderLocationInfo(){
		return "sftp://" + this.host +":22"+ this.remoteDir;
	}

	public String getFileContent(String fileName) throws Exception {
   	 FileSystemManager fsManager = null;
	 FileObject sftpFile=null;
	 FileObject src = null; // used for cleanup in release()
	 	String fileContent=null;


	     try {    	
	         System.out.println("SFTP download");
	         FileSystemOptions opts = null;
	         //app.initialize();
	         try {
	             fsManager = VFS.getManager();
	         } catch (FileSystemException ex) {
	             throw new RuntimeException("failed to get fsManager from VFS", ex);
	         }

	         UserAuthenticator auth = new StaticUserAuthenticator(null, this.user,
	                 this.password);
	         opts = new FileSystemOptions();
	         try {
	             DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts,
	                     auth);
	             SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
	         } catch (FileSystemException ex) {
	             throw new RuntimeException("setUserAuthenticator failed", ex);
	         }


	         //app.process();
	         String startPath = "sftp://" +this.user+":"+this.password+"@"+ this.host +":22"+ this.remoteDir+fileName;

	         // Set starting path on remote SFTP server.
	         try {
	             sftpFile = fsManager.resolveFile(startPath, opts);

	             System.out.println("SFTP connection successfully established to " +
	                     startPath);
	         } catch (FileSystemException ex) {
	             throw new RuntimeException("SFTP error parsing path " +
	                     this.remoteDir,
	                     ex);
	         }
	         
	         if (sftpFile.exists()) {
	         	FileContent fc =sftpFile.getContent();
	         	StringWriter writer = new StringWriter();
	         	IOUtils.copy(fc.getInputStream(), writer, encoding);
	         	String theString = writer.toString();
//	         	System.out.println(theString);
	         	fileContent= theString;
	         } 
	         
	         
	 	 } finally {
	         // app.release();
	         /**
	          * Release system resources, close connection to the filesystem. 
	          */
	 		 try {
	             FileSystem fs = null;
	             if (sftpFile != null) {
	               fs = sftpFile.getFileSystem(); // This works even if the src is closed.
	               fsManager.closeFileSystem(fs);
	             } // TODO if sftpFile != null
		 	 } catch (Exception e) {
			 		System.out.println(e);

		 	 }
	 	 }
         return fileContent;	     

	     
	}

    public static void main(String[] args) {
    	    SftpFiler sftpF =  new SftpFiler("patch-app1","sssspatch","sssss1234","/home/sssspatch/logs/","UTF-8");
    	    try {
				String str=sftpF.getFileContent( "ssss-webapp.log1");
				System.out.println(str);
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}
    }
	
}
