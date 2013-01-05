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
public class FilePfArtifact extends BasePfArtifact {
	private static Logger log = Logger.getLogger(FilePfArtifact.class);
	// temporary stored file
	protected File tempFile;

    // local box path to stored files
    protected String fileOsPath;   //   file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    protected String fileWebPath;   //   to access via file:///C:/My/Artifacts/15_00_07_804_OpenLogin.PNG
    
	
	/**
	 * creates artifact for file with default ".tmp" extension		
	 * @param name
	 * @param htmlDescription
	 * @param inputStream
	 */
    public FilePfArtifact(String name, String htmlDescription,InputStream inputStream) throws IOException{
		this(name, htmlDescription,inputStream, ".tmp");
	}
	
    /**
     * 
     * @param name
     * @param htmlDescription
     * @param inputStream
     * @param suffix - e.g. ".xml" extension
     */
	public FilePfArtifact(String name, String htmlDescription,InputStream inputStream, String suffix) throws IOException{
		super(name, htmlDescription);
        super.tag=ArtifactTagsEnm.atFile;
        // save input into temporary file
		tempFile = File.createTempFile("fileArtifact", suffix);
        tempFile.deleteOnExit();
        FileUtil.copy(inputStream, new FileOutputStream(tempFile));
	}

	

	public void store(IFileStorage fileStorage) throws Exception{
		if (isStored()) {
			throw new IllegalStateException("wrong call of storeArtifacts() when isArtifactsStored=true");
		}

		// try to save extension of original file
		String extension =FilenameUtils.getExtension(tempFile.getName());
		
		if (extension==null || extension.trim().length()==0) {
			extension="";
		} else {
			extension="."+extension.trim();
		}
		
		String preffix = CommonUtil.getTimeAsHrMsString(creationTime)+"_";
		fileOsPath=fileStorage.getBoxPathToFileFolder()+preffix+name+extension;
		fileWebPath=fileStorage.getWebPathToFileFolder()+preffix+name+extension;
		
		FileUtils.moveFile(tempFile, new File(fileOsPath));

		isStored=true;
	}

	
	/**
	 * file:///C:/QAAuto/jenkins/jobs/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 *
	 * http://qa-win-auto:8080/details/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 * 
	 */
	public String toHTML() {
		if (!isStored()) {
			throw new IllegalStateException("could not construct toHTML() before call of storeArtifacts(); isArtifactsStored=false");
		}

		StringBuilder sb= new StringBuilder();
		sb.append("<b><i>** Artifact name=["+name+"], time="+CommonUtil.getTimeAsString(creationTime) +"</i></b>");
		if (htmlDescription!= null) {
			sb.append("<br/> description=["+htmlDescription+"]");
		}
		// links to file
		if (fileWebPath!= null) {
			sb.append("<br/> <a href=\""+fileWebPath+"\"  target=\"_blank\">open file in new window</a> ");
		}
		
		return sb.toString();
	}
    
}
