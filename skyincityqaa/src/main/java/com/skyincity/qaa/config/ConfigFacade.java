package com.skyincity.qaa.config;

import java.util.concurrent.atomic.AtomicReference;

import com.skyincity.qaa.entities.AvailableBrowserOnRc;
import com.skyincity.qaa.entities.Browser;

/**
 * 
 * TODO make array availableBrowsersOnRc final
 */
public class ConfigFacade implements IConfigFacade {
	private final AvailableBrowserOnRc[] availableBrowsersOnRc;

	public ConfigFacade(AvailableBrowserOnRc[] availableBrowsersOnRc) {
		super();
		this.availableBrowsersOnRc=availableBrowsersOnRc;
	}

	public AvailableBrowserOnRc[] getAvailableBrowsersOnRc() {
		return availableBrowsersOnRc;
	}
	
	public AvailableBrowserOnRc getAvailableBrowserOnRc(Browser browser) {
		if (availableBrowsersOnRc==null  || availableBrowsersOnRc.length==0) {
			return null;
		}
		for (AvailableBrowserOnRc availableBrowserOnRc: availableBrowsersOnRc) {
			if (availableBrowserOnRc.getBrowser().equals(browser)){
				return availableBrowserOnRc;
			}
		}
		return null;
	}
	
}
