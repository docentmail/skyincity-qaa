package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.thoughtworks.selenium.Selenium;

public class UiWaiter extends BaseWaiter {
	private final Selenium selenium;
	
	
	public UiWaiter(long okLimitInMilliseconds,
			long warnLimitInMilliseconds, IWaitRegistrator waitRegistrator,
			Selenium selenium) {
		super(okLimitInMilliseconds, warnLimitInMilliseconds, waitRegistrator);
		this.selenium = selenium;
	}

	public void waitIt() throws Exception{
			selenium.waitForPageToLoad(String.valueOf(getWarnLimitInMilliseconds()));
	}
	
	protected IPfSnapshot addFinalWarningSnapshot(long deltaMs){
		IPfSnapshot snapshot=super.addFinalWarningSnapshot(deltaMs);
		UiPfArtifact uiArtifact= new UiPfArtifact("UI artifact" , "",selenium);
		snapshot.addArtifact(uiArtifact);
		return snapshot;
	}

	protected IPfSnapshot addFinalOkaySnapshot(long deltaMs){
		IPfSnapshot snapshot=super.addFinalOkaySnapshot(deltaMs);
		UiPfArtifact uiArtifact= new UiPfArtifact("UI artifact" , "",selenium);
		snapshot.addArtifact(uiArtifact);
		return snapshot;
	}
	
}
