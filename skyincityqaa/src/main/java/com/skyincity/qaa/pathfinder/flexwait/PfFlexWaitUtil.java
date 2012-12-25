package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.IPfStoryStorage;
import com.skyincity.qaa.pathfinder.PfStoryStorage;
import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.pathfinder.story.BasePfStory;
import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;
import com.thoughtworks.selenium.Selenium;

public class PfFlexWaitUtil {
	
	
	
	
	public static void flexWaitForPageLoad( Selenium selenium ,IWaitLauncher waitLauncher, 
			IWaitRegistrator waitRegistrator, long okLimitInMilliseconds,long warnLimitInMilliseconds) throws Exception{
		IWaiter waiter=new UiWaiter(okLimitInMilliseconds, warnLimitInMilliseconds, waitRegistrator, selenium);
		// before state

		// create artifact+snapshot and add it to Story
		UiPfArtifact uiArtifact= new UiPfArtifact("UI artifact" , "",selenium); 
		BasePfSnapshot snapshot= new BasePfSnapshot ("[Before] UI snapshot", "This is Ui snapshot before wait launching", uiArtifact);
		waitRegistrator.addBeforeSnapshot(snapshot);
		
		// launch process and add launch snapshot 
		waitLauncher.launch();
		
		// wait for the end of the process and register final result
		waiter.waitProcess();
		
		// storyPicker.getStory().addSnapshot(snapshot);

		
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			String clickLocator=null; 
			Selenium selenium=null;
			PfStoryStorage storyStorage=null;
			boolean isLogOkRequired=false;
			long okLimitInMilliseconds=0;
			long warnLimitInMilliseconds=0;		

			IWaitRegistrator waitRegistrator= new BaseWaitRegistrator("This is wait store description", storyStorage,
					isLogOkRequired);

			flexWaitForPageLoad(selenium, new ClickWaitLauncher(waitRegistrator,clickLocator, selenium), 
					waitRegistrator, okLimitInMilliseconds,warnLimitInMilliseconds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// flexWaitForPageLoad(selenium, new ClickWaitLauncher(waitRegistrator,clickLocator, selenium), waitRegistrator	);
	}
	
	
}
