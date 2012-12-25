package com.skyincity.qaa.pathfinder.story;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * thanks http://www.javapractices.com/topic/TopicAction.do?Id=1
 */
public class StoryTagsEnm  {

	public static final StoryTagsEnm UiTrace = new StoryTagsEnm("UiTrace");
	public static final StoryTagsEnm TestMethod = new StoryTagsEnm("TestMethod");
	public static final StoryTagsEnm DataBuilder = new StoryTagsEnm("DataBuilder");
	public static final StoryTagsEnm DataCleaner = new StoryTagsEnm("DataCleaner");
	public static final StoryTagsEnm Failure = new StoryTagsEnm("Failure");
	public static final StoryTagsEnm Warning = new StoryTagsEnm("FileDownload");
	public static final StoryTagsEnm FlexWait = new StoryTagsEnm("FlexWait");
	public static final StoryTagsEnm EmailReceiving = new StoryTagsEnm("EmailReceiving");
	public static final StoryTagsEnm UiFlexWait = new StoryTagsEnm("UiFlexWait");
	public static final StoryTagsEnm FileUpload = new StoryTagsEnm("FileUpload");
	public static final StoryTagsEnm FileDownload = new StoryTagsEnm("FileDownload");
	public static final StoryTagsEnm LogCollection = new StoryTagsEnm("LogCollection");
	public static final StoryTagsEnm TestOutput = new StoryTagsEnm("TestOutput");

	

	
	private final String name;

	public StoryTagsEnm(String name) {
		super();
		this.name = name;
	}

	/**
	 * Parse text into an element of this enumeration.
	 * 
	 * @param takes
	 *            one of the values UiTrace,TestMethod, DataBuilder,DataCleaner,Failure,FileDownload, EmailReceiving, UiFlexWait, FileUpload,FileDownload
	 */
	public static StoryTagsEnm valueOf(String aText) {
		Iterator iter = VALUES.iterator();
		while (iter.hasNext()) {
			StoryTagsEnm browserFamily = (StoryTagsEnm) iter.next();
			if (aText.equals(browserFamily.toString())) {
				return browserFamily;
			}
		}
		throw new IllegalArgumentException(
				"Text cannot be parsed into an BrowserFamily : '" + aText + "'");
	}

	private static final StoryTagsEnm[] fValues = { UiTrace,TestMethod, DataBuilder,DataCleaner,Failure,FileDownload, EmailReceiving, UiFlexWait, FileUpload,FileDownload,LogCollection,TestOutput };
	
	

	public static final List<StoryTagsEnm> VALUES = Collections
			.unmodifiableList(Arrays.asList(fValues));

	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoryTagsEnm other = (StoryTagsEnm) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
