package com.skyincity.qaa.config;

import java.util.concurrent.atomic.AtomicReference;

public class ConfigFactory {
	private static final AtomicReference <IConfigFacade> configFacade=new AtomicReference <IConfigFacade>(null) ;
	
	/**
	 * call to chose browser to open new window
	 * @return
	 */
	public static IConfigFacade getConfigFacade(){
		if (configFacade.get()==null) {
				throw new IllegalStateException("ConfigFactory was not initialized.  Please call initConfigFactory() before. "); 
			
		}
		return configFacade.get();
	}
	
	
	public static void initConfigFactory(IConfigFacade parConfigFacade){
		if (configFacade.get()!=null) {
			throw new IllegalStateException("ConfigFactory was already initialized.  Second initialization is wot allowed. ");
		}
		configFacade.compareAndSet(null,parConfigFacade);
	}
	

}
