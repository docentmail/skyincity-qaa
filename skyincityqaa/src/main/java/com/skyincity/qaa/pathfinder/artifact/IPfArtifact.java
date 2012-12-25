package com.skyincity.qaa.pathfinder.artifact;

import com.skyincity.qaa.pathfinder.IFileStorage;

public interface IPfArtifact {
	public String toHTML();
	public void store(IFileStorage fileStorge) throws Exception;
	public boolean isStored();
	public ArtifactTagsEnm getTag();
	public void setTag(ArtifactTagsEnm tag);
}
