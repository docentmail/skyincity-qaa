package com.skyincity.qaa.entities;

import com.skyincity.qaa.util.fileSystem.IRcFileSystem;
import com.thoughtworks.selenium.Selenium;

/**
 * used to pass already running browser into framework
 * 
 * TODO what about passing Selenium to avoid annoying parameter in the Selenium relaed method?
 * It's possible to store Selenium in the Thread Local variable  
 */
public class LaunchedBrowserOnRc {

	/** what we get after launching */
	private final Browser browser;
	private final String browserDownloadFolder;
	private final String browserUploadFolder;
	private final OsFamily osFamily; // temporary solution. Os should be here
	private final IRcFileSystem rcFileSystem; // RC file system implementation 
	private final String browserURL; // outdated I believe. JS Same domain origen; 
	// Could be important if we plan to open some new  url in existing window and JS policy is on 

	private final Selenium selenium;
	
	
	public LaunchedBrowserOnRc(Browser browser, String browserDownloadFolder,
			String browserUploadFolder, OsFamily osFamily, String browserURL, IRcFileSystem rcFileSystem,
			Selenium selenium) {
		super();
		this.browser = browser;
		this.browserDownloadFolder = browserDownloadFolder;
		this.browserUploadFolder = browserUploadFolder;
		this.osFamily = osFamily;
		this.browserURL = browserURL;
		this.rcFileSystem =rcFileSystem;
		this.selenium = selenium;
	}
	
	public LaunchedBrowserOnRc(AvailableBrowserOnRc availableBrowserOnRc,Selenium selenium, String browserURL ) {
		super();
		this.browser = availableBrowserOnRc.getBrowser();
		this.browserDownloadFolder = availableBrowserOnRc.getBrowserDownloadFolder();
		this.browserUploadFolder = availableBrowserOnRc.getBrowserUploadFolder();
		this.osFamily = availableBrowserOnRc.getOsFamily();
		this.browserURL = browserURL;
		this.rcFileSystem =availableBrowserOnRc.getRcFileSystem();
		this.selenium = selenium;
	}
	
	
	
	public Browser getBrowser() {
		return browser;
	}
	public String getBrowserDownloadFolder() {
		return browserDownloadFolder;
	}
	public String getBrowserUploadFolder() {
		return browserUploadFolder;
	}
	public OsFamily getOsFamily() {
		return osFamily;
	}
	public String getBrowserURL() {
		return browserURL;
	}
	public Selenium getSelenium() {
		return selenium;
	}
	public IRcFileSystem getRcFileSystem() {
		return rcFileSystem;
	}
}
