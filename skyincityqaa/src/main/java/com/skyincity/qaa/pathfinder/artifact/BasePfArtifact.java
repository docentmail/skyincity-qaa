package com.skyincity.qaa.pathfinder.artifact;

import java.util.Date;

import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.flexwait.ISnapshot;
import com.skyincity.qaa.util.flexwait.ISnapshot.SnapshotTypeEnum;

public abstract class BasePfArtifact implements IPfArtifact {
    protected String name;
    protected String htmlDescription;
    protected Date creationTime;
    protected boolean isStored=false;
    protected ArtifactTagsEnm tag=null; 

    
	public BasePfArtifact(String name, String htmlDescription) {
		super();
		this.name = name;
		this.htmlDescription = htmlDescription;
		creationTime=new Date();
	}

	public BasePfArtifact(String name) {
		this(name, null);
	}
    
    
	public abstract String toHTML();

	public boolean isStored() {
		return isStored;
	}

	public ArtifactTagsEnm getTag() {
		return tag;
	}

	public void setTag(ArtifactTagsEnm tag) {
		this.tag = tag;
	}

	
	
}
