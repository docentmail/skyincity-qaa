package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.thoughtworks.selenium.Selenium;

public abstract class BaseWaitLauncher implements IWaitLauncher {

	protected IWaitRegistrator waitRegistrator;

	
	protected BasePfSnapshot storeLaunchSnapshot(String snapshotHtmlDescription){
		BasePfSnapshot snapshot= new BasePfSnapshot ("[LaunchSnapshot] ", snapshotHtmlDescription, null);
		waitRegistrator.addLaunchSnapshot(snapshot);
		return snapshot;
	}

}
