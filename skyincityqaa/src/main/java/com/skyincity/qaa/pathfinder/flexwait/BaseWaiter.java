package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.artifact.ThrowablePfArtifact;
import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;
import com.thoughtworks.selenium.Selenium;

public abstract class BaseWaiter implements IWaiter {
	private long okLimitInMilliseconds;
	private long warnLimitInMilliseconds;
	private final IWaitRegistrator waitRegistrator;		
	
	
	public BaseWaiter(long okLimitInMilliseconds,
			long warnLimitInMilliseconds, IWaitRegistrator waitRegistrator) {
		super();
		this.okLimitInMilliseconds = okLimitInMilliseconds;
		this.warnLimitInMilliseconds = warnLimitInMilliseconds;
		this.waitRegistrator = waitRegistrator;
	}

	
	
	public long getOkLimitInMilliseconds() {
		return okLimitInMilliseconds;
	}



	public long getWarnLimitInMilliseconds() {
		return warnLimitInMilliseconds;
	}



	public void waitProcess() throws Exception{
        long start = System.currentTimeMillis();
		try {
			waitIt();
		} catch (Exception e) {
			// problem during wait or timout
			addFinalErrorSnapshot(e);
			waitRegistrator.saveStory();
    		throw e;
		}

		// 
		long deltaMs= System.currentTimeMillis()- start;
		if (deltaMs <= okLimitInMilliseconds ) {
			addFinalOkaySnapshot(deltaMs);
			waitRegistrator.saveStory();
		} else {
			addFinalWarningSnapshot(deltaMs);
			waitRegistrator.saveStory();
		}
	}
	
	protected IPfSnapshot addFinalErrorSnapshot(Exception e){
		ThrowablePfArtifact throwableArtifact=new ThrowablePfArtifact("Problem with wait", "" ,e); 
		BasePfSnapshot snapshot= new BasePfSnapshot ("[Final] UI snapshot", "This is Ui snapshot after wait is finishing. "+
		"There is problem with wait. details in Error artifact of the snapshot ", throwableArtifact);
		waitRegistrator.addFinalSnapshot(snapshot);
		waitRegistrator.addTag(StoryTagsEnm.Failure);
		return snapshot;
	}

	protected IPfSnapshot addFinalWarningSnapshot(long deltaMs){
		return addFinalSnapshot(true,deltaMs);
	}

	protected IPfSnapshot addFinalOkaySnapshot(long deltaMs){
		return addFinalSnapshot(false,deltaMs);
	}
	
	private IPfSnapshot addFinalSnapshot(boolean isWarning, long deltaMs){
		String warningOrOkay;
		if (isWarning) {
			warningOrOkay=" Warning";
		} else {
			warningOrOkay=" OKay";
		}
		BasePfSnapshot snapshot= new BasePfSnapshot ("[Final] UI snapshot", "This is Ui snapshot after wait is finishing. " +
				"Waiting time="+deltaMs +warningOrOkay);
		waitRegistrator.addFinalSnapshot(snapshot);
		if (isWarning){
			waitRegistrator.addTag(StoryTagsEnm.Warning);
		}
		return snapshot;
	}

	
}
