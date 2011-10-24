package com.skyincity.qaa.entities;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import static com.skyincity.qaa.entities.BrowserFamily.*;

/**
 * TODO - check that framework is under supported browser
 * http://automationtricks.blogspot.com/2010/08/how-to-get-browser-name-version-and.html
 *
 */
public class Browser /* implements Serializable, Comparable*/  {
	private final BrowserFamily browserFamily;
	private final int browserMajorVersion;

	
		// IE
		public static final Browser IE_6 = new Browser(IE,6);
		public static final Browser IE_7 = new Browser(IE,7);
		public static final Browser IE_8 = new Browser(IE,8);
		public static final Browser IE_9 = new Browser(IE,9);
		public static final Browser IE_10 = new Browser(IE,10);
		public static final Browser IE_11 = new Browser(IE,11); // for future

		// FIREFOX
		public static final Browser FIREFOX_3 = new Browser(FIREFOX,4);
		public static final Browser FIREFOX_4 = new Browser(FIREFOX,5);
		public static final Browser FIREFOX_5 = new Browser(FIREFOX,5);
		public static final Browser FIREFOX_6 = new Browser(FIREFOX,6);
		public static final Browser FIREFOX_7 = new Browser(FIREFOX,7);
		public static final Browser FIREFOX_8 = new Browser(FIREFOX,8);
		public static final Browser FIREFOX_9 = new Browser(FIREFOX,9);// for future

		// CHROME
		public static final Browser CHROME_1 = new Browser(CHROME,1);
		public static final Browser CHROME_2 = new Browser(CHROME,2);
		public static final Browser CHROME_3 = new Browser(CHROME,3);
		public static final Browser CHROME_4 = new Browser(CHROME,4);
		public static final Browser CHROME_5 = new Browser(CHROME,5);
		public static final Browser CHROME_6 = new Browser(CHROME,6);
		public static final Browser CHROME_7 = new Browser(CHROME,7);
		public static final Browser CHROME_8 = new Browser(CHROME,8);
		public static final Browser CHROME_9 = new Browser(CHROME,9);
		public static final Browser CHROME_10 = new Browser(CHROME,10);
		public static final Browser CHROME_11 = new Browser(CHROME,11);
		public static final Browser CHROME_12 = new Browser(CHROME,12);
		public static final Browser CHROME_13 = new Browser(CHROME,13);
		public static final Browser CHROME_14 = new Browser(CHROME,14);
		public static final Browser CHROME_15 = new Browser(CHROME,15);// for future

		// OPERA
		public static final Browser OPERA_9 = new Browser(OPERA,9);
		public static final Browser OPERA_10 = new Browser(OPERA,10);
		public static final Browser OPERA_11 = new Browser(OPERA,11);
		public static final Browser OPERA_12 = new Browser(OPERA,12); // for future	

		// SAFARI
		public static final Browser SAFARI_1 = new Browser(SAFARI,1);
		public static final Browser SAFARI_2 = new Browser(SAFARI,2);
		public static final Browser SAFARI_3 = new Browser(SAFARI,3);
		public static final Browser SAFARI_4 = new Browser(SAFARI,4);
		public static final Browser SAFARI_5 = new Browser(SAFARI,5);
		public static final Browser SAFARI_6 = new Browser(SAFARI,6);// for future

		// FUTURE VALUES MUST BE CONSTRUCTED HERE, AFTER ALL THE OTHERS

		public Browser(BrowserFamily browserFamily,int browserMajorVersion) {
			super();
			this.browserFamily = browserFamily;
			this.browserMajorVersion = browserMajorVersion;
		}

		/**
		 * Parse text into an element of this pseudo - enumeration.
		 * @param 	IE_NNNN; FIREFOX_NNN;FIREFOX_NNNN; CHROME_NNNN; OPERA_NNNN; SAFARI_NNNNN  where NNNNN - any integer
		 */
		public static Browser valueOf(String aText) {
			if (aText==null) {
				throw new IllegalArgumentException("Browser object could not be constructed from the text: ["+aText+"]");
			}
			String[] parts = aText.split("_");
			if (parts.length!=2) {
				throw new IllegalArgumentException("Browser object could not be constructed from the text: ["+aText+"]");
			}
			
			// set Browser family
			BrowserFamily bf=BrowserFamily.valueOf(parts[0]); 
			// set magor version
			int version= Integer.parseInt(parts[0]);
			
			return new Browser(bf,version);
		}
		
		

		public BrowserFamily getBrowserFamily() {
			return browserFamily;
		}

		public int getBrowserMajorVersion() {
			return browserMajorVersion;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((browserFamily == null) ? 0 : browserFamily.hashCode());
			result = prime * result + browserMajorVersion;
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
			Browser other = (Browser) obj;
			if (browserFamily == null) {
				if (other.browserFamily != null)
					return false;
			} else if (!browserFamily.equals(other.browserFamily))
				return false;
			if (browserMajorVersion != other.browserMajorVersion)
				return false;
			return true;
		}

		// export VALUES - doesn't implemented
//		private static final Browser[] fValues = { IE, FIREFOX, CHROME, OPERA };
		
//		public static final List<BrowserFamily> VALUES = Collections
//				.unmodifiableList(Arrays.asList(fValues));

	
		
	
}
