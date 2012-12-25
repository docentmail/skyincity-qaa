package com.skyincity.qaa.pathfinder;

import com.skyincity.qaa.pathfinder.artifact.IPfArtifact;
import com.skyincity.qaa.pathfinder.artifact.LogPfArtifact;
import com.skyincity.qaa.pathfinder.artifact.ThrowablePfArtifact;
import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.log.ILogExplorer;
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
	
}
