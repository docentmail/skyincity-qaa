package com.skyincity.qaa.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * thank you http://www.javapractices.com/topic/TopicAction.do?Id=1
 * 
 * 
 */
public class OsFamily /* implements Serializable, Comparable*/  {
	// LINUX; WINDOWS
	public static final OsFamily LINUX = new OsFamily("LINUX", "/");
	public static final OsFamily WINDOWS = new OsFamily("WINDOWS","\\");
	// FUTURE VALUES MUST BE CONSTRUCTED HERE, AFTER ALL THE OTHERS

	private final String fName;
	private final String fileSeparator;

	public OsFamily(String fName, String fileSeparator) {
		super();
		this.fName = fName;
		this.fileSeparator = fileSeparator;
	}

	/**
	 * Parse text into an element of this enumeration.
	 * @param takes one of the values "LINUX", "WINDOWS".
	 */
	public static OsFamily valueOf(String aText) {
		Iterator iter = VALUES.iterator();
		while (iter.hasNext()) {
			OsFamily osFamily = (OsFamily) iter.next();
			if (aText.equals(osFamily.toString())) {
				return osFamily;
			}
		}
		throw new IllegalArgumentException(
				"Text cannot be parsed into an OsFamily : '" + aText + "'");
	}

	
	public String getFileSeparator() {
		return fileSeparator;
	}


	// export VALUES with these two items
	private static final OsFamily[] fValues = {LINUX, WINDOWS };
	public static final List<OsFamily> VALUES = Collections
			.unmodifiableList(Arrays.asList(fValues));

	public String toString() {
		return fName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fName == null) ? 0 : fName.hashCode());
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
		OsFamily other = (OsFamily) obj;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		return true;
	}
	
}
