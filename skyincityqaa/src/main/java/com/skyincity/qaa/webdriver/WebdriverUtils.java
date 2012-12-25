package com.skyincity.qaa.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;

import com.skyincity.qaa.pathfinder.SeleniumWithTracking;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 *  = new SeleniumWithTracking(new SeleniumWithJoystick(new SeleniumWithTimer(new DefaultSelenium(
 *  = new SeleniumWithTracking(new SeleniumWithJoystick(new SeleniumWithTimer(new WebDriverBackedSelenium
 *  
 *  addScript, getEval - 
 *
 */
public class WebdriverUtils {

	public static WebDriver getWrappedWebDriver(Selenium selenium){
		WebDriver wd =null;
		if (selenium instanceof WebDriverBackedSelenium) {
			 wd= ((WebDriverBackedSelenium)selenium).getWrappedDriver();
		} else if (selenium instanceof SeleniumOverWebdriver) {
			wd= ((SeleniumOverWebdriver)selenium).getWebDriver();
		} else if (selenium instanceof DefaultSelenium) {
			wd= null;
		} else {
			throw new IllegalStateException("unknown instance of selenium. " +
					"We don't know how to get WebDriver from it. Selenium="+selenium.getClass());
		}	
		
		return wd;
		
	}

	
	/**
	 * useful to know does this selenium have wrapped selenium
	 * if not - 
	 * @param selenium
	 * @return
	 */
	public static Selenium getWrappedSelenium(Selenium selenium){
		Selenium sl =null;
		if (selenium instanceof WebDriverBackedSelenium) {
			sl=null;
		} else if (selenium instanceof SeleniumOverWebdriver) {
			sl= ((SeleniumOverWebdriver)selenium).getWrappedSelenium();
		} else if (selenium instanceof DefaultSelenium) {
			sl=null;
		} else {
			throw new IllegalStateException("unknown instance of selenium. " +
					"We don't know how to get Wrapped Selenium from it. Selenium="+selenium.getClass());
		}	
		return sl;
	}
}
