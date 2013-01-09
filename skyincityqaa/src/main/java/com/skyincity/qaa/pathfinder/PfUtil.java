package com.skyincity.qaa.pathfinder;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.skyincity.qaa.pathfinder.artifact.DbQueryPfArtifact;
import com.skyincity.qaa.pathfinder.artifact.FilePfArtifact;
import com.skyincity.qaa.pathfinder.artifact.IPfArtifact;
import com.skyincity.qaa.pathfinder.artifact.ThrowablePfArtifact;
import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.pathfinder.story.BasePfStory;
import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;
import com.thoughtworks.selenium.Selenium;

public class PfUtil {

	
	public static IFileStorage geFileStorageFromResultAllocator(final IPfResultAllocator ResultAllocator,final String boxKey, final String webKey){
		 return new IFileStorage() {
				public String getBoxPathToFileFolder(){
					return ResultAllocator.getPath(boxKey)+ResultAllocator.getFileSeparator();
				};
				public String getWebPathToFileFolder(){
					return ResultAllocator.getPath(webKey)+"/";	 			
				}
			};
	}
	
	public static IPfStory createErrorStory(String storyTitle, String storyDetails, 
			Throwable error,Selenium selenium){
		IPfStory errorStory=   new BasePfStory(storyTitle,storyDetails);
		errorStory.addTag(StoryTagsEnm.Failure);
		
		IPfSnapshot  errorSnapshot = new BasePfSnapshot("Error info", "");
		// Throwable Artifact
		IPfArtifact art =new ThrowablePfArtifact("Exception info",null , error);
		errorSnapshot.addArtifact(art);
		// UI artifact
		if (selenium != null) {
			// create artifact+snapshot and add it to Story
			art = new UiPfArtifact("uiArtifact", "created by during error reporting", selenium);
			errorSnapshot.addArtifact(art);
		}
		errorStory.addSnapshot(errorSnapshot);
		return errorStory;
	}
	
	    
	/**
	 * Logs snapshot with DbQueryPfArtifact with link to csv file.
     *   
     *  Usage		
	 *        IPfStory pfStory = DataStorage.getCurrentPfStory();
	 *        if (pfStory!= null) {
	 *        	PfUtil.logDbSnapshot(rs ,sql, pfStory);
	 *        }
 	 *     
	 * @param rs 
	 * @param sql - SQL sataement executed to get the result set 
	 * @param pfStory - story where snapshot would be saved
	 * @throws Exception
	 */
	public static void logDbSnapshot(ResultSet rs ,String sql, IPfStory pfStory) throws Exception{
		
			StringWriter sw = new StringWriter();
			CSVWriter writer = new CSVWriter(sw, ',');
			rs.first();	
			
			List<String[]> allLines = new ArrayList<String[]>();
			allLines.add(new String[] {sql}  );
			allLines.add(new String[] {""}  );
			writer.writeAll(allLines);
			writer.writeAll(rs, true);
			rs.first();
			
			writer.flush();
			String rezToShow= sw.toString();

	    	
	    	DbQueryPfArtifact fileArtifact= new DbQueryPfArtifact("dbQueryArtifact", "DB query=" + sql, new StringBufferInputStream(rezToShow),".csv");
	        BasePfSnapshot snapshot= new BasePfSnapshot ("DB call results", "DB query", fileArtifact);
	        pfStory.addSnapshot(snapshot);
	    }

	    
	    /**
	     * Logs snapshot with FilePfArtifact with link to exact file.
	     * 
	     * usage sample
	     * 	        IPfStory pfStory = DataStorage.getCurrentPfStory();
	     *      if (pfStory!= null) {
	     *       	PfUtil.logFileSnapshot(pfStory, storyName , bodyInputStream, fileExtenson);
	     *      }
	     * 
	     * 
	     * @param pfStory  story where snapshot would be saved
	     * @param storyName  e.g. "xmlUrl=" + xmlUrl
	     * @param bodyInputStream   e.g. new StringBufferInputStream(fileBody)
	     * @param fileExtenson   e.g. "xml"
	     * @throws Exception
	     */
	    public static void logFileSnapshot(IPfStory pfStory, String storyName , InputStream bodyInputStream, String fileExtenson) throws Exception{
	        
            FilePfArtifact fileArtifact= new FilePfArtifact("fileArtifact", storyName, bodyInputStream , fileExtenson==null ? null : "."+fileExtenson );
            BasePfSnapshot snapshot= new BasePfSnapshot ("Used file tracking", storyName, fileArtifact);
            pfStory.addSnapshot(snapshot);

	        
	    }
}
