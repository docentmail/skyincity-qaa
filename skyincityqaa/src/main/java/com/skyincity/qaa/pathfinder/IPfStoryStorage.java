package com.skyincity.qaa.pathfinder;

import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;

public interface IPfStoryStorage {
	boolean add(IPfStory story);
	IPfStory[] findStories(StoryTagsEnm[] expectedStoryTags);
	public IPfStory[] getAllStories();
	void clear();
}