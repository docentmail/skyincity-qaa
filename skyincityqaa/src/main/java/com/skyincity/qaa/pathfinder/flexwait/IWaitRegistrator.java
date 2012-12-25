package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;

public interface IWaitRegistrator {
	public boolean addTag(StoryTagsEnm tag);
	public void addBeforeSnapshot(IPfSnapshot snapshot);
	public void addLaunchSnapshot(IPfSnapshot snapshot);
	public void addFinalSnapshot(IPfSnapshot snapshot);
	public void saveStory();
}
