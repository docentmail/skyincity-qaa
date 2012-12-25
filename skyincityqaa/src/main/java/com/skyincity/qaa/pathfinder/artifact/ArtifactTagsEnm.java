package com.skyincity.qaa.pathfinder.artifact;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * thanks http://www.javapractices.com/topic/TopicAction.do?Id=1
 */
public class ArtifactTagsEnm  {

	public static final ArtifactTagsEnm atUi = new ArtifactTagsEnm("atUi");
	public static final ArtifactTagsEnm atFile = new ArtifactTagsEnm("atFile");
	public static final ArtifactTagsEnm atEMail = new ArtifactTagsEnm("atEMail");
	public static final ArtifactTagsEnm atThrowable = new ArtifactTagsEnm("atThrowable");

	

	
	private final String name;

	public ArtifactTagsEnm(String name) {
		super();
		this.name = name;
	}

	/**
	 * Parse text into an element of this enumeration.
	 * 
	 * @param takes
	 *            one of the values atUi, atFile, atEMail, atThrowable
	 */
	public static ArtifactTagsEnm valueOf(String aText) {
		Iterator iter = VALUES.iterator();
		while (iter.hasNext()) {
			ArtifactTagsEnm browserFamily = (ArtifactTagsEnm) iter.next();
			if (aText.equals(browserFamily.toString())) {
				return browserFamily;
			}
		}
		throw new IllegalArgumentException(
				"Text cannot be parsed into an BrowserFamily : '" + aText + "'");
	}

	private static final ArtifactTagsEnm[] fValues = { atUi, atFile, atEMail, atThrowable };
	
	

	public static final List<ArtifactTagsEnm> VALUES = Collections
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
		ArtifactTagsEnm other = (ArtifactTagsEnm) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
	
}
