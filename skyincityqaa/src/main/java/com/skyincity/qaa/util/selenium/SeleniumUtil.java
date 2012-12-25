package com.skyincity.qaa.util.selenium;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class SeleniumUtil {
	
    /**
     * saves string with image produced by selenium.captureEntirePageScreenshotToString("") into file
     * 
     * SeleniumUtil.saveImageToFile(selenium.captureEntirePageScreenshotToString(""), filePathNoExtention+".png");
     *  
     * @param base64Screenshot  - usually result of selenium.captureEntirePageScreenshotToString("")
     * @param imagePath - full path to image file to save (new file). e.g.C:\My\Artifacts\15_00_07_804_OpenLogin.PNG for WIN
     * extension should be png or PNG  
     * 	
     */
    public static void saveScreenshotToPngFile(String base64Screenshot, String imagePath) throws Exception{
    	File fl= new File(imagePath);
    	File folder = fl.getParentFile();
    	if (!folder.exists()){
    		FileUtils.forceMkdir(folder);	
    	}
    	
    	if (! "png".equalsIgnoreCase(FilenameUtils.getExtension(imagePath)) ) {
    		throw new IllegalArgumentException("Extension should be 'png' or 'PNG'. File is ["+imagePath+"].");
    	}
    	if (fl.exists()) {
    		throw new IllegalArgumentException("Could not save image. File ["+imagePath+"] is already exists.");
    	}
    	
    	
  	    byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
  	      
  	      FileOutputStream fos=null;
		try {
			  fos = new FileOutputStream(fl);
			  fos.write(decodedScreenshot);
		} finally{
			if (fos != null) {
				fos.close();
			}
		}

    }

}
