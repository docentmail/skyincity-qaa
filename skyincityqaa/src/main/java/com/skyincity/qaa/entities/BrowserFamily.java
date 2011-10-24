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
public class BrowserFamily /* implements Serializable, Comparable*/  {
	// IE, FIREFOX, CHROME, OPERA
	public static final BrowserFamily IE = new BrowserFamily("IE");
	public static final BrowserFamily FIREFOX = new BrowserFamily("FIREFOX");
	public static final BrowserFamily CHROME = new BrowserFamily("CHROME");
	public static final BrowserFamily OPERA = new BrowserFamily("OPERA");
	public static final BrowserFamily SAFARI = new BrowserFamily("SAFARI");
	// FUTURE VALUES MUST BE CONSTRUCTED HERE, AFTER ALL THE OTHERS

	private final String fName;

	public BrowserFamily(String fName) {
		super();
		this.fName = fName;
	}

	/**
	 * Parse text into an element of this enumeration.
	 * 
	 * @param takes
	 *            one of the values "IE", "FIREFOX", "CHROME", "OPERA", "SAFARI".
	 */
	public static BrowserFamily valueOf(String aText) {
		Iterator iter = VALUES.iterator();
		while (iter.hasNext()) {
			BrowserFamily browserFamily = (BrowserFamily) iter.next();
			if (aText.equals(browserFamily.toString())) {
				return browserFamily;
			}
		}
		throw new IllegalArgumentException(
				"Text cannot be parsed into an BrowserFamily : '" + aText + "'");
	}

	// export VALUES with these two items
	private static final BrowserFamily[] fValues = { IE, FIREFOX, CHROME, OPERA, SAFARI };
	public static final List<BrowserFamily> VALUES = Collections
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
		BrowserFamily other = (BrowserFamily) obj;
		if (fName == null) {
			if (other.fName != null)
				return false;
		} else if (!fName.equals(other.fName))
			return false;
		return true;
	}
	
	
	
	
}
