package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.IPfStoryStorage;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.pathfinder.story.BasePfStory;
import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;

public class BaseWaitRegistrator implements IWaitRegistrator {
	private final IPfStoryStorage storyStorage; 
	private IPfStory story;
	private final boolean isLogOkRequired;

	
	
	public BaseWaitRegistrator(String storyDescription, IPfStoryStorage storyStorage,
			boolean isLogOkRequired) {
		super();
		this.storyStorage = storyStorage;
		this.isLogOkRequired = isLogOkRequired;
		this.story= new BasePfStory("Wait story", storyDescription);
	}

	@Override
	public boolean addTag(StoryTagsEnm tag) {
		return story.addTag(tag);
	}

	@Override
	public void addBeforeSnapshot(IPfSnapshot snapshot) {
		story.addSnapshot(snapshot);
	}

	@Override
	public void addLaunchSnapshot(IPfSnapshot snapshot) {
		story.addSnapshot(snapshot);
		
	}

	@Override
	public void addFinalSnapshot(IPfSnapshot snapshot) {
		story.addSnapshot(snapshot);
	}

	public void saveStory() {
		if (story.contansTags(new StoryTagsEnm[] {StoryTagsEnm.Failure}) || 
				story.contansTags(new StoryTagsEnm[] {StoryTagsEnm.Warning})){
			storyStorage.add(story);	
		} else if (isLogOkRequired){
			storyStorage.add(story);
		}
	}

}



