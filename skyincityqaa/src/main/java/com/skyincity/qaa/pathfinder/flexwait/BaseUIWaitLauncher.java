package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.thoughtworks.selenium.Selenium;

public abstract class BaseUIWaitLauncher extends BaseWaitLauncher {

	
	protected BasePfSnapshot storeLaunchSnapshot(String snapshotHtmlDescription, Selenium selenium){
		UiPfArtifact uiArtifact= new UiPfArtifact("UI artifact" , "",selenium);
		BasePfSnapshot snapshot= new BasePfSnapshot ("[LaunchSnapshot] ", snapshotHtmlDescription, uiArtifact);
		waitRegistrator.addLaunchSnapshot(snapshot);
		return snapshot;
	}

}
