package com.skyincity.qaa.pathfinder.story;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.pathfinder.snapshot.IPfSnapshot;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.common.UniqueUtil;

public class BasePfStory implements IPfStory {

	protected List<IPfSnapshot> snapshots;
	protected String storyTitle;
	protected String storyDescription;
    protected boolean isArtifactsStored=false;
    protected Set<StoryTagsEnm> storyTags= new HashSet<StoryTagsEnm>();
     // unique story unique ID - for construct story header <a href="#123123123"><b>Story title</b>Story description</a> 
    // and internal link <a id="123123123"></a>
    protected String storyUID=(new Date()).getTime()+UniqueUtil.getUniqueCharString(4);   
    
	public BasePfStory(String title, String storyDescription) {
		super();
		this.storyTitle = title;
		this.storyDescription = storyDescription;
		if (this.storyTitle!=null){
			this.storyUID=this.storyUID+this.storyTitle.hashCode();
		}
		if (this.storyDescription!=null){
			this.storyUID=this.storyUID+this.storyDescription.hashCode();
		}

	}
	
	public boolean addTag(StoryTagsEnm tag){
		return storyTags.add(tag);
	}
	
	public boolean contansTags(StoryTagsEnm[] tags){
		for (StoryTagsEnm tag: tags) {
			if (!storyTags.contains(tag)) {
				return false;
			}
		}
		return true;
	}


	
	public String getHtmlHeader() {
		return "<h2><a href=\"#"+storyUID+"\">Story title=["+storyTitle+"]</a></h2>";
	}
	
	public String toHTML() {

 		StringBuilder sb= new StringBuilder();
		// prepare head
		sb.append("<br/><br/><span style=\"background-color:yellow;\"><b>========= Story Title =["+storyTitle+"]  ] ============</b></span>" );
		if (storyDescription!=null){
		   sb.append("<br/>Story Description =["+storyDescription+"]  ");
		}
//		String head=    CommonUtil.escapeHtmlConvertNLtoBR(sb.toString()); 
		String head=    sb.toString();
		
		// prepare list of snapshots
		sb.setLength(0);
		if (snapshots!=null && snapshots.size()>0) {
			sb.append("<br/>--- registered before states -------");
			for (IPfSnapshot snapshot: snapshots ) {
				sb.append("<br/>").append(snapshot.toHTML());
			}
		}
		String strSnapshot = sb.toString();
		String htmlStoryAncor="<a id=\""+storyUID+"\"></a>";
		return htmlStoryAncor+ head+strSnapshot;
	}





	@Override
	public String toString() {
		return "BasePfStory [snapshots=" + snapshots + ", storyTitle="
				+ storyTitle + ", storyDescription=" + storyDescription
				+ ", isArtifactsStored=" + isArtifactsStored + "]";
	}

	public void addSnapshot(IPfSnapshot snapshot) {
		if (snapshots==null) {
			snapshots= new ArrayList<IPfSnapshot>(); 
		}
		snapshots.add(snapshot);
	}





	public void store(IFileStorage fileStorage) throws Exception{
		if (isArtifactsStored) {
			throw new IllegalStateException("wrong call of store() when isArtifactsStored=true");
		}
		// store screenshots
		if (snapshots!=null && snapshots.size()>0) {
			for (IPfSnapshot snapshot: snapshots ) {
				snapshot.store(fileStorage);
			}
		}
		isArtifactsStored=true;
	}

	public boolean isArtifactsStored() {
		return isArtifactsStored;
	}


	public String getTitle() {
		return storyTitle;
	}

	public void setTitle(String title) {
		this.storyTitle = title;
	}

	public String getDescription() {
		return storyDescription;
	}

	public void seDescription(String description) {
		this.storyDescription = description;
	}
	
 

}
