package com.skyincity.qaa.entities;

import com.skyincity.qaa.util.fileSystem.IRcFileSystem;

/**
 * used to launch new browser window
 */
public class AvailableBrowserOnRc {
	/** info to launch new DefaultSelenium */
	private final String serverHost;
	private final int serverPort;
	private final String browserStartCommand;


	/** what we get after launching */
	private final Browser browser;
	private final String browserDownloadFolder;
	private final String browserUploadFolder;
	private final OsFamily osFamily; // temporary solution. Os should be here
	private final IRcFileSystem rcFileSystem; // RC file system implementation
	
	public AvailableBrowserOnRc(String serverHost, int serverPort,
			String browserStartCommand, Browser browser,
			String browserDownloadFolder, String browserUploadFolder, IRcFileSystem rcFileSystem,
			OsFamily osFamily) {
		super();
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		this.browserStartCommand = browserStartCommand;
		this.browser = browser;
		this.browserDownloadFolder = browserDownloadFolder;
		this.browserUploadFolder = browserUploadFolder;
		this.osFamily = osFamily;
		this.rcFileSystem =rcFileSystem;
	}
	public String getServerHost() {
		return serverHost;
	}
	public int getServerPort() {
		return serverPort;
	}
	public String getBrowserStartCommand() {
		return browserStartCommand;
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
	public IRcFileSystem getRcFileSystem() {
		return rcFileSystem;
	}
}
