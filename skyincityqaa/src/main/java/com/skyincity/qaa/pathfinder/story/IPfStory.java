package com.skyincity.qaa.pathfinder.story;

import java.util.ArrayList;

import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.util.common.CommonUtil;

public interface IPfStory {

	public String toHTML();
	public String getHtmlHeader();
	public String toString();
	public void addSnapshot(IPfSnapshot snapshot);
	public void store(IFileStorage fileStorage) throws Exception;
	public boolean isArtifactsStored();
	public String getTitle();
	public void setTitle(String title);
	public String getDescription();
	public void seDescription(String description);
	public boolean addTag(StoryTagsEnm tag);
	public boolean contansTags(StoryTagsEnm[] tags);
	
}
