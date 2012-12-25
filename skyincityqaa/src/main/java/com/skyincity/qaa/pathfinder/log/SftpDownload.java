package com.skyincity.qaa.pathfinder.log;




import java.io.File;
import java.io.StringWriter;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.vfs.AllFileSelector;
import org.apache.commons.vfs.FileContent;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystem;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemManager;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.FileType;
import org.apache.commons.vfs.UserAuthenticator;
import org.apache.commons.vfs.VFS;
import org.apache.commons.vfs.auth.StaticUserAuthenticator;
import org.apache.commons.vfs.impl.DefaultFileSystemConfigBuilder;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.local.LocalFile;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;


/**
 * Example use of VFS sftp
 *
 */
public class SftpDownload {

    // Set these variables for your testing environment:
    private String host = "patch-app1";  // Remote SFTP hostname
    private String user = "sssspatch";      // Remote system login name
    private String password = "sssss123";    // Remote system password
    private String remoteDir = "/home/sssspatch/logs";
    private String filename = "ssss-webapp.log";
    // Look for a file path like "smoke20070128_wkt.txt"
//    private String filePatternString = ".*/smoke\\d{8}_wkt\\.txt";
    private String filePatternString = ".*/ssss-webapp\\.log";
    // Local directory to receive file
    private String localDir = "c:\\TMP";
    
    
    private File localDirFile;
    private Pattern filePattern;
    private FileSystemManager fsManager = null;
    private FileSystemOptions opts = null;
    private FileObject sftpFile;

    private FileObject src = null; // used for cleanup in release()

    public static void main(String[] args) {
     try {    	
        System.out.println("SFTP download");
        SftpDownload app = new SftpDownload();

        app.initialize();

        app.process();

        app.release();
	 } catch (Exception e) {
		System.out.println(e);
	 }
    } // main( String[] args )


    public static void main1(String[] args) {
        
         
          //OutputStream os = session.setOutputStream();
    
          try {
			JSch jsch = new JSch();
			int port = 22;
			//jsch.setKnownHosts("10.10.16.10");
			Session session = jsch.getSession("sssspatch", "patch-app1", port);
			session.setPassword("sssss123");
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();
			ChannelSftp c = (ChannelSftp) channel;
			c.put("/home/sssspatch/logs/ssss-webapp.log",
					"c:\\TMP\\ssss-webapp.log");
     	 } catch (Exception e) {
     		System.out.println(e);
     	 }

          
    } // main( String[] args )
    
    
    
    /**
     * Creates the download directory localDir if it
     * does not exist and makes a connection to the remote SFTP server.
     * 
     */
    public void initialize() {
        if (localDirFile == null) {
            localDirFile = new File(localDir);
        }
        if (!this.localDirFile.exists()) {
            localDirFile.mkdirs();
        }

        try {
            this.fsManager = VFS.getManager();
        } catch (FileSystemException ex) {
            throw new RuntimeException("failed to get fsManager from VFS", ex);
        }

        UserAuthenticator auth = new StaticUserAuthenticator(null, this.user,
                this.password);
        this.opts = new FileSystemOptions();
        try {
            DefaultFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts,
                    auth);
            SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(opts, "no");
        } catch (FileSystemException ex) {
            throw new RuntimeException("setUserAuthenticator failed", ex);
        }

        this.filePattern = Pattern.compile(filePatternString);
    } // initialize()


    public void process() throws Exception{
//    	java.util.Properties config = new java.util.Properties(); 
//    	config.put("StrictHostKeyChecking", "no");
//    	session.setConfig(config);
    	
        String startPath = "sftp://" +this.user+":"+this.password+"@"+ this.host +":22"+ this.remoteDir+this.filename;
        FileObject[] children;

        // Set starting path on remote SFTP server.
        try {
            this.sftpFile = this.fsManager.resolveFile(startPath, opts);

            System.out.println("SFTP connection successfully established to " +
                    startPath);
        } catch (FileSystemException ex) {
            throw new RuntimeException("SFTP error parsing path " +
                    this.remoteDir,
                    ex);
        }
        
        if (this.sftpFile.exists()) {
        	FileContent fc =this.sftpFile.getContent();
        	StringWriter writer = new StringWriter();
        	IOUtils.copy(fc.getInputStream(), writer, "UTF-8");
        	String theString = writer.toString();
        	System.out.println(theString);
        }

        // Get a directory listing
        try {
            children = this.sftpFile.getChildren();
        } catch (FileSystemException ex) {
            throw new RuntimeException("Error collecting directory listing of " +
                    startPath, ex);
        }

        search:
        for (FileObject f : children) {
            try {
                String relativePath =
                        File.separatorChar + f.getName().getBaseName();

                if (f.getType() == FileType.FILE) {
                    System.out.println("Examining remote file " + f.getName());

                    if (!this.filePattern.matcher(f.getName().getPath()).matches()) {
                        System.out.println("  Filename does not match, skipping file ." +
                                relativePath);
                        continue search;
                    }

                    String localUrl = "file://" + this.localDir + relativePath;
                    String standardPath = this.localDir + relativePath;
                    System.out.println("  Standard local path is " + standardPath);
                    LocalFile localFile =
                            (LocalFile) this.fsManager.resolveFile(localUrl);
                    System.out.println("    Resolved local file name: " +
                            localFile.getName());

                    if (!localFile.getParent().exists()) {
                        localFile.getParent().createFolder();
                    }

                    System.out.println("  ### Retrieving file ###");
                    localFile.copyFrom(f,
                            new AllFileSelector());
                } else {
                    System.out.println("Ignoring non-file " + f.getName());
                }
            } catch (FileSystemException ex) {
                throw new RuntimeException("Error getting file type for " +
                        f.getName(), ex);
            }
        } // for (FileObject f : children)

        // Set src for cleanup in release()
        src = children[0];
    } // process(Object obj)
    
    
    /**
     * Retrieves files that match the specified FileSpec from the SFTP server
     * and stores them in the local directory.
     */
    public void process1() {
//    	java.util.Properties config = new java.util.Properties(); 
//    	config.put("StrictHostKeyChecking", "no");
//    	session.setConfig(config);

        String startPath = "sftp://" +this.user+":"+this.password+"@"+ this.host +":22"+ this.remoteDir;
        FileObject[] children;

        // Set starting path on remote SFTP server.
        try {
            this.sftpFile = this.fsManager.resolveFile(startPath, opts);

            System.out.println("SFTP connection successfully established to " +
                    startPath);
        } catch (FileSystemException ex) {
            throw new RuntimeException("SFTP error parsing path " +
                    this.remoteDir,
                    ex);
        }


        // Get a directory listing
        try {
            children = this.sftpFile.getChildren();
        } catch (FileSystemException ex) {
            throw new RuntimeException("Error collecting directory listing of " +
                    startPath, ex);
        }

        search:
        for (FileObject f : children) {
            try {
                String relativePath =
                        File.separatorChar + f.getName().getBaseName();

                if (f.getType() == FileType.FILE) {
                    System.out.println("Examining remote file " + f.getName());

                    if (!this.filePattern.matcher(f.getName().getPath()).matches()) {
                        System.out.println("  Filename does not match, skipping file ." +
                                relativePath);
                        continue search;
                    }

                    String localUrl = "file://" + this.localDir + relativePath;
                    String standardPath = this.localDir + relativePath;
                    System.out.println("  Standard local path is " + standardPath);
                    LocalFile localFile =
                            (LocalFile) this.fsManager.resolveFile(localUrl);
                    System.out.println("    Resolved local file name: " +
                            localFile.getName());

                    if (!localFile.getParent().exists()) {
                        localFile.getParent().createFolder();
                    }

                    System.out.println("  ### Retrieving file ###");
                    localFile.copyFrom(f,
                            new AllFileSelector());
                } else {
                    System.out.println("Ignoring non-file " + f.getName());
                }
            } catch (FileSystemException ex) {
                throw new RuntimeException("Error getting file type for " +
                        f.getName(), ex);
            }
        } // for (FileObject f : children)

        // Set src for cleanup in release()
        src = children[0];
    } // process(Object obj)


    /**
     * Release system resources, close connection to the filesystem. 
     */
    public void release() {
        FileSystem fs = null;

        fs = this.src.getFileSystem(); // This works even if the src is closed.
        this.fsManager.closeFileSystem(fs);
    } // release()
} // class App