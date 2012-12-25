package com.skyincity.qaa.pathfinder.flexwait;

import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.thoughtworks.selenium.Selenium;

public class ClickWaitLauncher extends BaseUIWaitLauncher {

	private String clickLocator; 
	private final Selenium selenium;
	

	public ClickWaitLauncher(IWaitRegistrator waitRegistrator, String clickLocator, Selenium selenium) {
		super();
		this.clickLocator = clickLocator;
		this.selenium = selenium;
		this.waitRegistrator = waitRegistrator;
	}


	public void launch()  throws Exception{
		storeLaunchSnapshot("Snapshot before click on locator ="+clickLocator,selenium);
		selenium.click(clickLocator);
	}
	

}
