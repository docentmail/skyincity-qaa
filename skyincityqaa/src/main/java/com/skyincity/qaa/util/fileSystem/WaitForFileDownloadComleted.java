package com.skyincity.qaa.util.fileSystem;

import com.skyincity.qaa.entities.BrowserFamily;
import com.skyincity.qaa.entities.LaunchedBrowserOnRc;
import com.thoughtworks.selenium.Wait;

/**
 * The class that implements "waiting for file upload" functionality
 * 
 * usage :
 * <code> new WaitForFileDownloading("someFileForUpload","xls").wait(,60000,500);</code>
 *  
 */

public class WaitForFileDownloadComleted  extends Wait {
	
	protected String fileName;
	protected String fileExtention;
	protected String errorMessage;
	protected LaunchedBrowserOnRc launcedBrowserOnRc;

	public WaitForFileDownloadComleted(String fileName, String fileExtention, LaunchedBrowserOnRc launcedBrowserOnRc) {
		super();
		this.fileName = fileName;
		if (fileExtention==null || fileExtention.trim().length()==0) {
			this.fileExtention = null;
		}
		else {
			this.fileExtention = fileExtention;
		}
		this.errorMessage = "Couldn't find file " + getFileNameWithExtention()+ " in download foldder !";
		this.launcedBrowserOnRc = launcedBrowserOnRc;
	}

    public WaitForFileDownloadComleted(String fileNameWithExtension, LaunchedBrowserOnRc launcedBrowserOnRc) throws Exception {
        super();
        if(!fileNameWithExtension.contains(".")){
            this.fileName = fileNameWithExtension;
            this.fileExtention = null;
 //            throw new Exception("File extension is not specified:"+"\""+ fileNameWithExtension +"\"") ;
        	
        } else {
            this.fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
            this.fileExtention = fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf(".") + 1);
        }
        this.errorMessage = "Couldn't find file " + getFileNameWithExtention() + " in download foldder !";
		this.launcedBrowserOnRc = launcedBrowserOnRc;

    }
	
	/**
	 * works for remote and local RC
	 */
	public boolean until() {
		try {
			String fileSeparator=launcedBrowserOnRc.getOsFamily().getFileSeparator();
			String fileFullPath =launcedBrowserOnRc.getBrowserDownloadFolder()+ fileSeparator + getFileNameWithExtention();
			
			boolean rez= launcedBrowserOnRc.getRcFileSystem().isFileExist(fileFullPath);
			if (!rez) return false;
			 
			if (launcedBrowserOnRc.getBrowser().getBrowserFamily()== BrowserFamily.FIREFOX) {
  
				// FIREFOX creates "Vendor Info.xls" size=0  and "Vendor Info.xls.part" 
				// and then move "Vendor Info.xls.part" to "Vendor Info.xls" after download is completed 
				//				Thread.sleep(500); // let browser create *.part file after empty base file
				if (launcedBrowserOnRc.getRcFileSystem().isFileExist(fileFullPath+".part")) {
					return false;
				} 
					
			} else {
				throw new RuntimeException(" Can't check if file downloaded for browser except FIREFOX ");
			}

			 return rez;
//			CommonHelper.isFileExist(ConfigurationFactory
//					.getConfigurationFacade().getBrowserDownloadFolder()
//					+ File.separatorChar + fileName + "." + fileExtention);

		} catch (Exception e) {
		}
		return false;
	}
	
	/**
	 * Attention - this file could be on remote RC box 
	 * @return
	 * @throws Exception
	 */
	public String getFullPathOfDownloadeFile() throws Exception{
		return launcedBrowserOnRc.getBrowserDownloadFolder()+
				launcedBrowserOnRc.getOsFamily().getFileSeparator()	+getFileNameWithExtention();
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileExtention() {
		return fileExtention;
	}

	public String getFileNameWithExtention() {
			if (fileExtention!=null) {
				return fileName+"." + fileExtention;
			} else {
				return fileName;
			}
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}



}
