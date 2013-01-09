package com.skyincity.qaa.pathfinder.artifact;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;


import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.common.FileUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.thoughtworks.selenium.Selenium;

/**
 * Class to store in pathfinder files any (limited by size of HDD) size.
 * Initially stores file in tmp folder (with deleteOnExit option).
 * During store() move file to pathfinder folder. 
 * 
 * Usage sample: 
 *   
 *           IPfStory pfStory = DataStorage.getCurrentPfStory();
 *            if (pfStory!= null) {
 *				FilePfArtifact fileArtifact= new FilePfArtifact("fileArtifact", "xmlUrl=" + xmlUrl, new StringBufferInputStream(xmlBody),".xml");
 *				BasePfSnapshot snapshot= new BasePfSnapshot ("Downloaded file tracking", "xmlUrl=" + xmlUrl, fileArtifact);
 *				pfStory.addSnapshot(snapshot);
 *            }
 *
 * 
 *
 */
public class DbQueryPfArtifact extends FilePfArtifact {
	private static Logger log = Logger.getLogger(DbQueryPfArtifact.class);
	// temporary stored file
	protected File tempFile;

    // local box path to stored files
    protected String fileOsPath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    protected String fileWebPath;   //   to access via file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    
	
	/**
	 * creates artifact for file with default ".csv" extension		
	 * @param name
	 * @param htmlDescription
	 * @param inputStream
	 */
    public DbQueryPfArtifact(String name, String htmlDescription,InputStream inputStream) throws IOException{
		super(name, htmlDescription,inputStream, ".cvs");
	}
	
    /**
     * 
     * @param name
     * @param htmlDescription
     * @param inputStream
     * @param suffix - e.g. ".txt" extension
     */
	public DbQueryPfArtifact(String name, String htmlDescription,InputStream inputStream, String suffix) throws IOException{
		super(name, htmlDescription,inputStream, suffix);
		super.tag=ArtifactTagsEnm.atDbQuery;
	}

	
	
}
