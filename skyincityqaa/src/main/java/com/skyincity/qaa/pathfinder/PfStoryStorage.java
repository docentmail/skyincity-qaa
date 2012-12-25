package com.skyincity.qaa.pathfinder;


import java.util.ArrayList;
import java.util.List;

import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.pathfinder.story.StoryTagsEnm;

public class PfStoryStorage implements IPfStoryStorage {
	List <IPfStory> storage= new ArrayList<IPfStory>();

	/* (non-Javadoc)
	 * @see com.skyincity.qaa.pathfinder.IPfStoryStorage#add(com.skyincity.qaa.pathfinder.IPfStory)
	 */
	@Override
	public boolean add(IPfStory story){
		if (storage.contains(story)) {
			return false;
		} else {
			storage.add(story);
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.skyincity.qaa.pathfinder.IPfStoryStorage#findStories(com.skyincity.qaa.pathfinder.StoryTagsEnm[])
	 */
	@Override
	public IPfStory[] findStories(StoryTagsEnm[] expectedStoryTags){
		List <IPfStory> tmpStorage= new ArrayList<IPfStory>();
		for (IPfStory story: storage){
			if (story.contansTags(expectedStoryTags)) {
				tmpStorage.add(story);
			}
		}
		
		return tmpStorage.toArray(new IPfStory[1]);
	}

	@Override
	public void clear(){
		storage.clear();
	}
	
	@Override
	public IPfStory[] getAllStories(){
		return storage.toArray(new IPfStory[1]);
	}	
}
