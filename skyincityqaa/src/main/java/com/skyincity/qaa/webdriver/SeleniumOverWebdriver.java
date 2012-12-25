package com.skyincity.qaa.webdriver;

import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.Selenium;

public interface SeleniumOverWebdriver extends Selenium {
 public WebDriver getWebDriver();
 public void setWebDriver(WebDriver webdriver);
  public Selenium getWrappedSelenium();
 

}
