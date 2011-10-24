package com.skyincity.qaa.util.common;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class UniqueUtil {

	private static Logger log = Logger.getLogger(UniqueUtil.class);
	/**
	 * 
	 * @return something like "ff79e5b7-cb60-4de5-8dff-799ae337c75c"
	 */
	public static String getUniqueString() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	public static String getUniqueCharString(int stringLen) {
		// <34
		String sUuid = getUniqueString();
		// log.debug("\""+sUuid+"\"");
		
		sUuid=sUuid.replaceAll("-", "");
		// log.debug("\""+sUuid+"\"");
		StringBuilder sb= new StringBuilder();
		
		for (int i=0; i< sUuid.length(); i++) {
			if (Character.isDigit(sUuid.charAt(i))) {
				int j=Integer.valueOf(sUuid.substring(i, i+1));
				Character c= Character.valueOf((char) ('h'+j));
				sb.append(c.toString());
			} else {
				sb.append(sUuid.charAt(i));
			}
		}
		if (sb.length()>= stringLen) {
			return sb.substring(0, stringLen).toString(); 
		} else {
			return sb.toString()+ StringUtils.repeat("a", stringLen-sb.length() );
		}
	}
	public static String getUniqueCharString(int stringLen, String[] usedStrings) {
		for (int i=0; i<20; i++) {
			String rez= getUniqueCharString(stringLen);
			if (usedStrings==null || usedStrings.length==0) {
				return rez;
			} else {
				for (String s: usedStrings) {
					if (rez.equalsIgnoreCase(s)) {
						continue;
					} 
				}
				return rez;
			}
		}	
		
		throw new IllegalStateException("Could not find UniqueCharString for stringLen="+stringLen +" and  usedStrings array="+ Arrays.toString(usedStrings));
	}
	
    /**
     * Method appends unique string to existing @text through particular @separator or updates it.
     *
     * For example:
     * appendUniqueStringToExistText("some text", "#") return  "some text #qcmhkilllh"
     * appendUniqueStringToExistText("some text #qcmhkilllh", "#") return  "some text #ddmmsllsjjl"
     * appendUniqueStringToExistText("some text #qcmhkilllh #qcmhkilllh #qcmhkilllh", "#") return "some text #odfqhcbdfb"
     *
     * Warning:
     * apendUniqueString("I don't know java", "'") return "I don'dmkdkaabcm"
     *
     * @param text
     * @param separator
     * @return
     * @throws Exception
     */
    public static String appendUniqueStringToExistText(String text, String separator) {
        if (text.contains(separator)) {
            return text.substring(0, text.indexOf(separator)) + separator + UniqueUtil.getUniqueCharString(10);
        } else {
            if(text.isEmpty()){
                return separator + UniqueUtil.getUniqueCharString(10);
            }else{
                return text + " " + separator + UniqueUtil.getUniqueCharString(10);
            }
        }
    }
	
	
	public static void main(String[] arg) {
		for (int i=0; i<10; i++) {
			log.debug("\""+getUniqueString()+"\"");
		}
 		for (int i=0; i<10; i++) {
 			log.debug("\""+getUniqueCharString(5)+"\"");
		}
        System.out.println(appendUniqueStringToExistText("some text #qcmhkilllh #qcmhkilllh #qcmhkilllh", "#"));
        System.out.println(appendUniqueStringToExistText("I don't know java", "'"));
        String emptyText = "";
        System.out.println(emptyText = appendUniqueStringToExistText(emptyText, "#"));
        System.out.println(emptyText = appendUniqueStringToExistText(emptyText, "#"));

	}

}
