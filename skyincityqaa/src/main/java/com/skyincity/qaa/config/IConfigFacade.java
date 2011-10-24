package com.skyincity.qaa.config;

import com.skyincity.qaa.entities.AvailableBrowserOnRc;
import com.skyincity.qaa.entities.Browser;

/** for new windows */
public interface IConfigFacade {
	public AvailableBrowserOnRc[] getAvailableBrowsersOnRc();
	public AvailableBrowserOnRc getAvailableBrowserOnRc(Browser browser) ;
}
