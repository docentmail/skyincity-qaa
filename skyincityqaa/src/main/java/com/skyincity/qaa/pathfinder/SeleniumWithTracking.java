package com.skyincity.qaa.pathfinder;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;


import com.skyincity.qaa.pathfinder.artifact.UiPfArtifact;
import com.skyincity.qaa.pathfinder.snapshot.BasePfSnapshot;
import com.skyincity.qaa.pathfinder.story.IPfStory;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.selenium.SeleniumUtil;
import com.skyincity.qaa.webdriver.SeleniumOverWebdriver;
import com.skyincity.qaa.webdriver.WebdriverUtils;
import com.thoughtworks.selenium.Selenium;




public class SeleniumWithTracking implements SeleniumOverWebdriver{
	/** isCookiePresent(SeleniumWithTracking.createSnapshotCmd)  - return true*/
	public static String createSnapshotCmd="create UI snapshot (for SeleniumWithTracking)";

	/** isCookiePresent(SeleniumWithTracking.pauseUiTrackingCmd)  - return true*/
	public static String pauseUiTrackingCmd="pause UI tacking (for SeleniumWithTracking)";

	/** isCookiePresent(SeleniumWithTracking.resumeUiTrackingCmd)  - return true*/
	public static String resumeUiTrackingCmd="resume UI tacking (for SeleniumWithTracking)";
	
	private String strScreen=null;
	private String strHTML=null;
	
	private static Logger log = Logger.getLogger(SeleniumWithTracking.class);
	private Selenium selenium;
	private IPfStoryPicker storyPicker;
    private Boolean isTrackingRequired;
    private Boolean isTrackingPaused=false;
    private WebDriver webdriver;
    
   protected String[] inputCmd = new String[]{
		   "close","doubleClick","contextMenu","clickAt","click","doubleClickAt","contextMenuAt","fireEvent","focus","keyPress","shiftKeyDown",
		   "shiftKeyUp","metaKeyDown","metaKeyUp","altKeyDown","altKeyUp","controlKeyDown","controlKeyUp","keyDown",
		   "keyUp","mouseOver","mouseOut","mouseDown","mouseDownRight","mouseDownAt","mouseDownRightAt","mouseUp",
		   "mouseUpRight","mouseUpAt","mouseUpRightAt","mouseMove","mouseMoveAt","type","typeKeys","check",
		   "uncheck","select","addSelection","removeSelection","removeAllSelections","submit","open",
		   "open","openWindow","selectWindow","selectPopUp","deselectPopUp","selectFrame","goBack",
		   "refresh","windowFocus","keyDownNative","keyUpNative","keyPressNative"
   };

	
	public SeleniumWithTracking(Selenium selenium,IPfStoryPicker storyPicker, Boolean isTrackingRequired){
		this.selenium=selenium;
		this.storyPicker=storyPicker;
		this.isTrackingRequired=isTrackingRequired;
		// set webdriver
		if (selenium instanceof WebDriverBackedSelenium) {
			 this.webdriver= ((WebDriverBackedSelenium)selenium).getWrappedDriver();
		} else if (selenium instanceof SeleniumOverWebdriver) {
			 this.webdriver= ((SeleniumOverWebdriver)selenium).getWebDriver();
		}

	}
	public SeleniumWithTracking(Selenium selenium,IPfStoryPicker storyPicker){
		this(selenium,storyPicker, true);
	}


	 public WebDriver getWebDriver(){
		 return webdriver;
	 }
	 public void setWebDriver(WebDriver webdriver){
		 this.webdriver=webdriver;
	 }
	 
	 public Selenium getWrappedSelenium(){
		 return selenium;
	 }

	 
	private void processSnapshot(String cmd, String params) {
		// log cmd here

		Arrays.sort(inputCmd);
		if (Arrays.binarySearch(inputCmd, cmd) <0) {
			return;
		}
		processSnapshotWithoutCmdCheck(cmd, params);
	}	
		
   
	private void processSnapshotWithoutCmdCheck(String cmd, String params) {
       if (!isTrackingRequired){
    	   return;
       }
       if (isTrackingPaused){
    	   return;
       }
		
		try {
			String atrifactStorage="C:\\_LogArtifacts\\trace\\";
			String preffix = CommonUtil.getTimeAsHrMsString(new Date())+"_";
			String newScreenshot=null;
			String newHtmlSource=null;
			String newPageUrl=null;

			try {
				newScreenshot=selenium.captureEntirePageScreenshotToString("");
				newHtmlSource=selenium.getHtmlSource();
				newPageUrl=selenium.getLocation();
			} catch (Throwable e) {
			}
			
			String name ="";
			
			// check if screenshot/ html was changed and store snapshot (compare with prev screen shot)
			boolean isScreenChanged=false;
			if (newScreenshot!=null &&  (strScreen==null || !newScreenshot.equals(strScreen))){
				isScreenChanged=true;
			}

			boolean isHtmlChanged=false;
			if (newHtmlSource !=null && (strHTML==null || !newHtmlSource.equals(strHTML))){
				isHtmlChanged=true;
			}
			
			
			if (isScreenChanged || isHtmlChanged) {
				// create artifact+snapshot and add it to Story
				UiPfArtifact uiArtifact= new UiPfArtifact("uiArtifact", "created by SeleniumWithTracking",
				newScreenshot,	newHtmlSource,	newPageUrl);
				BasePfSnapshot snapshot= new BasePfSnapshot ("UI Tracking", "Before Selenium cmd: cmd="+cmd+"; params="+params, uiArtifact);
				IPfStory pfStory= storyPicker.getStory();
				if (pfStory==null) {
					log.warn("Story for snapshot was not defined ",new Exception("Store stacktrace excep[tion"));
				} else {
					pfStory.addSnapshot(snapshot);
				}
						
				
				strScreen=newScreenshot;
				strHTML=newHtmlSource;
				
			}
			
			// store Selenium command
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	

	public void addCustomRequestHeader(String key, String value) {
		processSnapshot("addCustomRequestHeader", "key=["+key+"] value=["+ value+"]");
		selenium.addCustomRequestHeader(key, value);
	}




	@Override
	public void addLocationStrategy(String strategyName, String functionDefinition) {
		processSnapshot("addLocationStrategy", "strategyName=["+strategyName+"] functionDefinition=["+functionDefinition+"]");
		selenium.addLocationStrategy(strategyName, functionDefinition);
	}




	@Override
	public void addScript(String scriptContent, String scriptTagId) {
		processSnapshot("addScript", "scriptContent=["+scriptContent+"] scriptTagId=["+scriptTagId+"]");
		selenium.addScript(scriptContent, scriptTagId);
	}




	@Override
	public void addSelection(String locator, String optionLocator) {
		processSnapshot("addSelection", "locator=["+locator+"] optionLocator=["+optionLocator+"]");
		selenium.addSelection( locator, optionLocator);
	}




	@Override
	public void allowNativeXpath(String allow) {
		processSnapshot("allowNativeXpath", "allow=["+allow+"]");
		selenium.allowNativeXpath( allow) ;
	}




	@Override
	public void altKeyDown() {
		processSnapshot("altKeyDown", "");
		selenium.altKeyDown();
	}




	@Override
	public void altKeyUp() {
		processSnapshot("altKeyUp", "");
		selenium. altKeyUp();
	}




	@Override
	public void answerOnNextPrompt(String answer) {
		processSnapshot("answerOnNextPrompt", "answer=["+answer+"]");
		selenium.answerOnNextPrompt( answer);
	}




	@Override
	public void assignId(String locator, String identifier) {
		processSnapshot("assignId", "locator=["+locator+"] identifier=["+identifier+"]");
		selenium.assignId( locator,  identifier);
	}




	@Override
	public void attachFile(String fieldLocator, String fileLocator) {
		processSnapshot("attachFile", "fieldLocator=["+fieldLocator+"] fieldLocator=["+fieldLocator+"]");
		selenium.attachFile( fieldLocator,  fileLocator);
	}




	@Override
	public void captureEntirePageScreenshot(String filename, String kwargs) {
		processSnapshot("captureEntirePageScreenshot", "filename=["+filename+"] kwargs=["+kwargs+"]");
		selenium.captureEntirePageScreenshot( filename,  kwargs);
	}




	@Override
	public String captureEntirePageScreenshotToString(String kwargs) {
		processSnapshot("captureEntirePageScreenshotToString", "kwargs=["+kwargs+"]");
		String rez=null;
		try {
			rez= selenium.captureEntirePageScreenshotToString( kwargs);		
		} catch (UnsupportedOperationException e) {
			WebDriver wd= WebdriverUtils.getWrappedWebDriver(selenium);
			if (wd ==null) {
				throw e;
			} else {
				rez = ((TakesScreenshot)wd).getScreenshotAs(OutputType.BASE64);
			}
		}	
		return rez;
	}




	@Override
	public String captureNetworkTraffic(String type) {
		processSnapshot("captureNetworkTraffic", "type=["+type+"]");
		String rez=  selenium.captureNetworkTraffic( type);
		return rez;
	}




	@Override
	public void captureScreenshot(String filename) {
		processSnapshot("captureScreenshot", "filename=["+filename+"] ");
		selenium.captureScreenshot( filename);
	}




	@Override
	public String captureScreenshotToString() {
		processSnapshot("captureScreenshotToString", "");
		String rez=  selenium.captureScreenshotToString();
		return rez;
	}




	@Override
	public void check(String locator) {
		processSnapshot("check", "locator=["+locator+"]");
		selenium.check(locator);
	}




	@Override
	public void chooseCancelOnNextConfirmation() {
		processSnapshot("chooseCancelOnNextConfirmation", "");
		selenium.chooseCancelOnNextConfirmation();
	}




	@Override
	public void chooseOkOnNextConfirmation() {
		processSnapshot("chooseOkOnNextConfirmation", "");
		selenium.chooseOkOnNextConfirmation();
	}




	@Override
	public void click(String locator) {
		processSnapshot("click", "locator=["+locator+"]");
		selenium.click(locator);
	}




	@Override
	public void clickAt(String locator, String coordString) {
		processSnapshot("clickAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.clickAt( locator,  coordString);
	}




	@Override
	public void close() {
		processSnapshot("close", "");
		selenium.close();
	}




	@Override
	public void contextMenu(String locator) {
		processSnapshot("contextMenu", "locator=["+locator+"]");
		selenium.contextMenu( locator);
	}




	@Override
	public void contextMenuAt(String locator, String coordString) {
		processSnapshot("contextMenuAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.contextMenuAt( locator,  coordString);
	}




	@Override
	public void controlKeyDown() {
		processSnapshot("controlKeyDown", "");
		selenium.controlKeyDown();
	}




	@Override
	public void controlKeyUp() {
		processSnapshot("controlKeyUp", "");
		selenium.controlKeyUp();
	}




	@Override
	public void createCookie(String nameValuePair, String optionsString) {
		processSnapshot("createCookie", "nameValuePair=["+nameValuePair+"] optionsString=["+optionsString+"]");
		selenium.createCookie( nameValuePair,  optionsString);
	}




	@Override
	public void deleteAllVisibleCookies() {
		processSnapshot("deleteAllVisibleCookies", "");
		selenium.deleteAllVisibleCookies();
	}




	@Override
	public void deleteCookie(String name, String optionsString) {
		processSnapshot("deleteCookie", "name=["+name+"] optionsString=["+optionsString+"]");
		selenium.deleteCookie( name,  optionsString);
	}




	@Override
	public void deselectPopUp() {
		processSnapshot("deselectPopUp", "");
		selenium.deselectPopUp();
	}




	@Override
	public void doubleClick(String locator) {
		processSnapshot("doubleClick", "locator=["+locator+"]");
		selenium. doubleClick( locator);
	}




	@Override
	public void doubleClickAt(String locator, String coordString) {
		processSnapshot("doubleClickAt", "");
		selenium.doubleClickAt( locator,  coordString);
	}




	@Override
	public void dragAndDrop(String locator, String movementsString) {
		processSnapshot("dragAndDrop", "locator=["+locator+"] movementsString=["+movementsString+"]");
		selenium.dragAndDrop( locator,  movementsString);
	}




	@Override
   public void dragAndDropToObject(String locatorOfObjectToBeDragged,	String locatorOfDragDestinationObject) {
		processSnapshot("dragAndDropToObject", "locatorOfObjectToBeDragged=["+locatorOfObjectToBeDragged+"] locatorOfObjectToBeDragged=["+locatorOfObjectToBeDragged+"]");
		selenium.dragAndDropToObject( locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
	}




	@Override
	public void dragdrop(String locator, String movementsString) {
		processSnapshot("dragdrop", "movementsString=["+movementsString+"]");
		selenium.dragdrop( locator,  movementsString);
	}




	@Override
	public void fireEvent(String locator, String eventName) {
		processSnapshot("fireEvent", "locator=["+locator+"] eventName=["+eventName+"] ");
		selenium.fireEvent( locator,  eventName);
	}




	@Override
	public void focus(String locator) {
		processSnapshot("focus", "locator=["+locator+"]");
		selenium.focus( locator);
	}




	@Override
	public String getAlert() {
		processSnapshot("getAlert", "");
		String rez=  selenium.getAlert();
		return rez;
	}




	@Override
	public String[] getAllButtons() {
		processSnapshot("getAllButtons", "");
		String[] rez=  selenium.getAllButtons();
		return rez;
	}




	@Override
	public String[] getAllFields() {
		processSnapshot("getAllFields", "");
		String[] rez=  selenium.getAllFields();
		return rez;
	}




	@Override
	public String[] getAllLinks() {
		processSnapshot("getAllLinks", "");
		String[] rez=  selenium.getAllLinks();
		return rez;
	}




	@Override
	public String[] getAllWindowIds() {
		processSnapshot("getAllWindowIds", "");
		String[] rez=  selenium.getAllWindowIds();
		return rez;
	}




	@Override
	public String[] getAllWindowNames() {
		processSnapshot("getAllWindowNames", "");
		String[] rez=  selenium.getAllWindowNames() ;
		return rez;
	}




	@Override
	public String[] getAllWindowTitles() {
		processSnapshot("getAllWindowTitles", "");
		String[] rez=  selenium.getAllWindowTitles();
		return rez;
	}




	@Override
	public String getAttribute(String attributeLocator) {
		processSnapshot("getAttribute", "attributeLocator=["+attributeLocator+"]");
		String rez=  selenium.getAttribute( attributeLocator);
		return rez;
	}




	@Override
	public String[] getAttributeFromAllWindows(String attributeName) {
		processSnapshot("getAttributeFromAllWindows", "attributeName=["+attributeName+"]");
		String[] rez=  selenium.getAttributeFromAllWindows( attributeName);
		return rez;
	}




	@Override
	public String getBodyText() {
		processSnapshot("getBodyText", "");
		String rez=  selenium.getBodyText();
		return rez;
	}




	@Override
	public String getConfirmation() {
		processSnapshot("getConfirmation", "");
		String rez=  selenium.getConfirmation();
		return rez;
	}




	@Override
	public String getCookie() {
		processSnapshot("getCookie", "");
		String rez= selenium.getCookie();
		return rez;
	}




	@Override
	public String getCookieByName(String name) {
		processSnapshot("getCookieByName", "name=["+name+"]");
		String rez=  selenium.getCookieByName(name);
		return rez;
	}




	@Override
	public Number getCursorPosition(String locator) {
		processSnapshot("getCursorPosition", "locator=["+locator+"]");
		Number rez=  selenium.getCursorPosition( locator);
		return rez;
	}




	@Override
	public Number getElementHeight(String locator) {
		processSnapshot("getElementHeight", "locator=["+locator+"]");
		Number rez=  selenium.getElementHeight( locator);
		return rez;
	}




	@Override
	public Number getElementIndex(String locator) {
		processSnapshot("getElementIndex", "locator=["+locator+"]");
		Number rez=  selenium.getElementIndex( locator);
		return rez;

	}




	@Override
	public Number getElementPositionLeft(String locator) {
		processSnapshot("getElementPositionLeft",  "locator=["+locator+"]");
		Number rez=  selenium.getElementPositionLeft( locator);
		return rez;
	}




	@Override
	public Number getElementPositionTop(String locator) {
		processSnapshot("getElementPositionTop",  "locator=["+locator+"]");
		Number rez=  selenium.getElementPositionTop( locator);
		return rez;
	}


	@Override
	public String[] getSelectOptions(String selectLocator) {
		processSnapshot("getSelectOptions", "selectLocator=["+selectLocator+"]");
		String[] rez=  selenium.getSelectOptions(selectLocator);
		return rez;
	}




	@Override
	public String getSelectedLabel(String selectLocator) {
		processSnapshot("getSelectedLabel", "selectLocator=["+selectLocator+"]");
		String rez=  selenium.getSelectedLabel(selectLocator) ;
		return rez;
	}





	@Override
	public String getText(String locator) {
		processSnapshot("getText",  "locator=["+locator+"]");
		String rez=  selenium.getText(locator);
		return rez;
	}





	@Override
	public String getValue(String locator) {
		processSnapshot("getValue",  "locator=["+locator+"]");
		String rez=  selenium.getValue(locator);
		return rez;
	}




	@Override
	public boolean isChecked(String locator) {
		processSnapshot("isChecked",  "locator=["+locator+"]");
		boolean rez=  selenium.isChecked(locator);
		return rez;
	}





	@Override
	public boolean isElementPresent(String locator) {
		processSnapshot("isElementPresent",  "locator=["+locator+"]");
		boolean rez=  selenium.isElementPresent(locator);
		return rez;
	}





	@Override
	public boolean isTextPresent(String pattern) {
		processSnapshot("isTextPresent", "pattern=["+pattern+"]");
		boolean rez=  selenium.isTextPresent(pattern);
		return rez;
	}




	@Override
	public void open(String url) {
//		processSnapshot("open", "");
        selenium.open(url);
	}




	@Override
	public void openWindow(String url, String windowID) {
		processSnapshot("openWindow", "url=["+url+"] windowID=["+windowID+"]");
		selenium.openWindow(url, windowID);
	}




	@Override
	public void select(String selectLocator, String optionLocator) {
		processSnapshot("select", "selectLocator=["+selectLocator+"] optionLocator=["+optionLocator+"]");
		selenium.select(selectLocator, optionLocator);
	}




	@Override
	public void selectFrame(String locator) {
		processSnapshot("selectFrame", "locator=["+locator+"]");
		selenium.selectFrame(locator);
	}







	@Override
	public void selectWindow(String windowID) {
		processSnapshot("selectWindow", "windowID=["+windowID+"]");
		selenium.selectWindow(windowID);
	}



	@Override
	public void start() {
		processSnapshot("start", "");
		selenium.start();
	}




	@Override
	public void start(String optionsString) {
		processSnapshot("start", "optionsString=["+optionsString+"]");
		selenium.start(optionsString);
	}




	@Override
	public void start(Object optionsObject) {
		processSnapshot("start", "optionsObject=["+optionsObject+"]");
		selenium.start(optionsObject);
	}




	@Override
	public void stop() {
		processSnapshot("stop", "");
		selenium.stop();
	}





	@Override
	public void type(String locator, String value) {
		processSnapshot("type", "value=["+value+"]");
		selenium.type(locator, value);
	}





	@Override
	public void waitForPageToLoad(String timeout) {
		processSnapshot("waitForPageToLoad", "timeout=["+timeout+"]");
		selenium.waitForPageToLoad(timeout);
	}




	@Override
	public void windowMaximize() {
		processSnapshot("windowMaximize", "");
		selenium.windowMaximize();
	}

	
	@Override
	public Number getElementWidth(String locator) {
		processSnapshot("getElementWidth",  "locator=["+locator+"]");
		Number rez=  selenium.getElementWidth(locator);
		return rez;
	}

	@Override
	public String getEval(String script) {
		processSnapshot("getEval", "script=["+script+"]");
		String rez=  selenium.getEval(script);
		return rez;
	}

	@Override
	public String getExpression(String expression) {
		processSnapshot("getExpression", "");
		String rez=  selenium.getExpression(expression);
		return rez;
	}

	@Override
	public String getHtmlSource() {
		processSnapshot("getHtmlSource", "");
		String rez=  selenium.getHtmlSource();
		return rez;
	}

	@Override
	public String getLocation() {
		processSnapshot("getLocation", "");
		String rez=  selenium.getLocation();
		return rez;
	}

	@Override
	public Number getMouseSpeed() {
		processSnapshot("getMouseSpeed", "");
		Number rez=  selenium.getMouseSpeed();
		return rez;
	}

	@Override
	public String getPrompt() {
		processSnapshot("getPrompt", "");
		String rez=  selenium.getPrompt();
		return rez;
	}

	@Override
	public String getSelectedId(String selectLocator) {
		processSnapshot("getSelectedId", "selectLocator=["+selectLocator+"]");
		String rez=  selenium.getSelectedId(selectLocator);
		return rez;
	}

	@Override
	public String[] getSelectedIds(String selectLocator) {
		processSnapshot("getSelectedIds","selectLocator=["+selectLocator+"]");
		String[] rez=  selenium.getSelectedIds(selectLocator);
		return rez;
	}

	@Override
	public String getSelectedIndex(String selectLocator) {
		processSnapshot("getSelectedIndex", "selectLocator=["+selectLocator+"]");
		String rez=  selenium.getSelectedIndex(selectLocator);
		return rez;
	}

	@Override
	public String[] getSelectedIndexes(String selectLocator) {
		processSnapshot("getSelectedIndexes", "selectLocator=["+selectLocator+"]");
		String[] rez=  selenium.getSelectedIndexes(selectLocator);
		return rez;
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		processSnapshot("getSelectedLabels", "selectLocator=["+selectLocator+"]");
		String[] rez=  selenium.getSelectedLabels(selectLocator);
		return rez;
	}

	@Override
	public String getSelectedValue(String selectLocator) {
		processSnapshot("getSelectedValue", "selectLocator=["+selectLocator+"]");
		String rez=  selenium.getSelectedValue(selectLocator);
		return rez;
	}

	@Override
	public String[] getSelectedValues(String selectLocator) {
		processSnapshot("getSelectedValues", "selectLocator=["+selectLocator+"]");
		String[] rez=  selenium.getSelectedValues(selectLocator);
		return rez;
	}


	@Override
	public String getSpeed() {
		processSnapshot("getSpeed", "");
		String rez=  selenium.getSpeed();
		return rez;
	}

	@Override
	public String getTable(String tableCellAddress) {
		processSnapshot("getTable", "tableCellAddress=["+tableCellAddress+"]");
		String rez=  selenium.getTable(tableCellAddress);
		return rez;
	}


	@Override
	public String getTitle() {
		processSnapshot("getTitle", "");
		String rez=  selenium.getTitle();
		return rez;
	}


	@Override
	public boolean getWhetherThisFrameMatchFrameExpression(	String currentFrameString, String target) {
		processSnapshot("getWhetherThisFrameMatchFrameExpression", "currentFrameString=["+currentFrameString+"] target=["+target+"]");
		boolean rez=  selenium.getWhetherThisFrameMatchFrameExpression(currentFrameString, target);
		return rez;
	}

	@Override
	public boolean getWhetherThisWindowMatchWindowExpression(String currentWindowString, String target) {
		processSnapshot("getWhetherThisWindowMatchWindowExpression", "currentWindowString=["+currentWindowString+"] target=["+target+"]");

		boolean rez=  selenium.getWhetherThisWindowMatchWindowExpression(currentWindowString,
				target);
		return rez;
	}

	@Override
	public Number getXpathCount(String xpath) {
		processSnapshot("getXpathCount", "xpath=["+xpath+"]");
		Number rez=  selenium.getXpathCount(xpath);
		return rez;
	}

    @Override
    public Number getCssCount(String css) {
		processSnapshot("getCssCoun", "css=[+css+]");
		Number rez=  selenium.getCssCount(css);
		return rez;
    }

    @Override
	public void goBack() {
		processSnapshot("goBack", "");
		selenium.goBack();
	}

	@Override
	public void highlight(String locator) {
		processSnapshot("highlight",  "locator=["+locator+"]");
		selenium.highlight(locator);
	}

	@Override
	public void ignoreAttributesWithoutValue(String ignore) {
		processSnapshot("ignoreAttributesWithoutValue", "ignore=["+ignore+"]");
		selenium.ignoreAttributesWithoutValue(ignore);
	}

	@Override
	public boolean isAlertPresent() {
		processSnapshot("isAlertPresent", "");
		boolean rez=  selenium.isAlertPresent();
		return rez;
	}

	@Override
	public boolean isConfirmationPresent() {
		processSnapshot("isConfirmationPresent", "");
		boolean rez=  selenium.isConfirmationPresent();
		return rez;
	}

	/**
	 * used for passing specific commands into SeleniumWith tracker
	 * pauseUiTrackingCmd & resumeUiTrackingCmd
	 */
	@Override
	public boolean isCookiePresent(String name) {
		if (name.equals(pauseUiTrackingCmd) ){ 
			isTrackingPaused=true;
			return true;
		}
		if (name.equals(resumeUiTrackingCmd) ){ 
			isTrackingPaused=false;
			return true;
		}

		processSnapshot("isCookiePresent", "name=["+name+"]");
		// very specific command to ask SeleniumWithTracking create snapshot (usually last snapshot at the test)
		if (name.equals(createSnapshotCmd) ){ 
			processSnapshotWithoutCmdCheck("direct UI snapshor request", "");
			return true;
		}

		boolean rez=  selenium.isCookiePresent(name);
		return rez;
	}

	@Override
	public boolean isEditable(String locator) {
		processSnapshot("isEditable",  "locator=["+locator+"]");
		boolean rez=  selenium.isEditable(locator);
		return rez;
	}

	@Override
	public boolean isOrdered(String locator1, String locator2) {
		processSnapshot("isOrdered", "locator1=["+locator1+"] locator2=["+locator2+"]");
		boolean rez=  selenium.isOrdered(locator1, locator2);
		return rez;
	}

	@Override
	public boolean isPromptPresent() {
		processSnapshot("isPromptPresent", "");
		boolean rez=  selenium.isPromptPresent();
		return rez;
	}

	@Override
	public boolean isSomethingSelected(String selectLocator) {
		processSnapshot("isSomethingSelected", "selectLocator=["+selectLocator+"]");
		boolean rez=  selenium.isSomethingSelected(selectLocator);
		return rez;
	}

	@Override
	public boolean isVisible(String locator) {
		processSnapshot("isVisible",  "locator=["+locator+"]");
		boolean rez=  selenium.isVisible(locator);
		return rez;
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		processSnapshot("keyDown", "locator=["+locator+"] keySequence=["+keySequence+"]");
		selenium.keyDown(locator, keySequence);
	}

	@Override
	public void keyDownNative(String keycode) {
		processSnapshot("keyDownNative", "keycode=["+keycode+"]");
		selenium.keyDownNative(keycode);
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		processSnapshot("keyPress", "locator=["+locator+"] keySequence=["+keySequence+"]");
		selenium.keyPress(locator, keySequence);
	}

	@Override
	public void keyPressNative(String keycode) {
		processSnapshot("keyPressNative", "keycode=["+keycode+"]");
		selenium.keyPressNative(keycode);
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		processSnapshot("keyUp", "locator=["+locator+"] keySequence=["+keySequence+"]");
		selenium.keyUp(locator, keySequence);
	}

	@Override
	public void keyUpNative(String keycode) {
		processSnapshot("keyUpNative", "keycode=["+keycode+"]");
		selenium.keyUpNative(keycode);
	}

	@Override
	public void metaKeyDown() {
		processSnapshot("metaKeyDown", "");
		selenium.metaKeyDown();
	}

	@Override
	public void metaKeyUp() {
		processSnapshot("metaKeyUp", "");
		selenium.metaKeyUp();
	}

	@Override
	public void mouseDown(String locator) {
		processSnapshot("mouseDown",  "locator=["+locator+"]");
		selenium.mouseDown(locator);
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		processSnapshot("mouseDownAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.mouseDownAt(locator, coordString);
	}

	@Override
	public void mouseDownRight(String locator) {
		processSnapshot("mouseDownRight", "locator=["+locator+"]");
		selenium.mouseDownRight(locator);
	}

	@Override
	public void mouseDownRightAt(String locator, String coordString) {
		processSnapshot("mouseDownRightAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.mouseDownRightAt(locator, coordString);
	}

	@Override
	public void mouseMove(String locator) {
		processSnapshot("mouseMove", "locator=["+locator+"]");
		selenium.mouseMove(locator);
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		processSnapshot("mouseMoveAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.mouseMoveAt(locator, coordString);
	}

	@Override
	public void mouseOut(String locator) {
		processSnapshot("mouseOut",  "locator=["+locator+"]");
		selenium.mouseOut(locator);
	}

	@Override
	public void mouseOver(String locator) {
		processSnapshot("mouseOver",  "locator=["+locator+"]");
		selenium.mouseOver(locator);
	}

	@Override
	public void mouseUp(String locator) {
		processSnapshot("mouseUp",  "locator=["+locator+"]");
		selenium.mouseUp(locator);
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		processSnapshot("mouseUpAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.mouseUpAt(locator, coordString);
	}

	@Override
	public void mouseUpRight(String locator) {
		processSnapshot("mouseUpRight",  "locator=["+locator+"]");
		selenium.mouseUpRight(locator);
	}

	@Override
	public void mouseUpRightAt(String locator, String coordString) {
		processSnapshot("mouseUpRightAt", "locator=["+locator+"] coordString=["+coordString+"]");
		selenium.mouseUpRightAt(locator, coordString);
	}


	@Override
	public void refresh() {
		processSnapshot("refresh", "");
		selenium.refresh();
	}

	@Override
	public void removeAllSelections(String locator) {
		processSnapshot("removeAllSelections",  "locator=["+locator+"]");
		selenium.removeAllSelections(locator);
	}

	@Override
	public void removeScript(String scriptTagId) {
		processSnapshot("removeScript", "scriptTagId=["+scriptTagId+"]");
		selenium.removeScript(scriptTagId);
	}

	@Override
	public void removeSelection(String locator, String optionLocator) {
		processSnapshot("removeSelection", "locator=["+locator+"] optionLocator=["+optionLocator+"]");
		selenium.removeSelection(locator, optionLocator);
	}

	@Override
	public String retrieveLastRemoteControlLogs() {
		processSnapshot("retrieveLastRemoteControlLogs", "");
		String rez=  selenium.retrieveLastRemoteControlLogs();
		return rez;
	}

	@Override
	public void rollup(String rollupName, String kwargs) {
		processSnapshot("rollup", "rollupName=["+rollupName+"] kwargs=["+kwargs+"]");
		selenium.rollup(rollupName, kwargs);
	}

	@Override
	public void runScript(String script) {
		processSnapshot("runScript", "script=["+script+"]");
		selenium.runScript(script);
	}


	@Override
	public void selectPopUp(String windowID) {
		processSnapshot("selectPopUp", "windowID=["+windowID+"]");
		selenium.selectPopUp(windowID);
	}


	@Override
	public void setBrowserLogLevel(String logLevel) {
		processSnapshot("setBrowserLogLevel", "logLevel=["+logLevel+"]");
		selenium.setBrowserLogLevel(logLevel);
	}

	@Override
	public void setContext(String context) {
		processSnapshot("setContext", "context=["+context+"]");
		selenium.setContext(context);
	}

	@Override
	public void setCursorPosition(String locator, String position) {
		processSnapshot("setCursorPosition", "locator=["+locator+"] position=["+position+"]");
		selenium.setCursorPosition(locator, position);
	}

	@Override
	public void setExtensionJs(String extensionJs) {
		processSnapshot("setExtensionJs", "extensionJs=["+extensionJs+"]");
		selenium.setExtensionJs(extensionJs);
	}

	@Override
	public void setMouseSpeed(String pixels) {
		processSnapshot("setMouseSpeed", "pixels=["+pixels+"]");
		selenium.setMouseSpeed(pixels);
	}

	@Override
	public void setSpeed(String value) {
		processSnapshot("setSpeed", "value=["+value+"]");
		selenium.setSpeed(value);
	}

	@Override
	public void setTimeout(String timeout) {
		processSnapshot("setTimeout", "timeout=["+timeout+"]");
		selenium.setTimeout(timeout);
	}

	@Override
	public void shiftKeyDown() {
		processSnapshot("shiftKeyDown", "");
		selenium.shiftKeyDown();
	}

	@Override
	public void shiftKeyUp() {
		processSnapshot("shiftKeyUp", "");
		selenium.shiftKeyUp();
	}

	@Override
	public void showContextualBanner() {
		processSnapshot("showContextualBanner", "");
		selenium.showContextualBanner();
	}

	@Override
	public void showContextualBanner(String className, String methodName) {
		processSnapshot("showContextualBanner", "className=["+className+"] methodName=["+methodName+"]");
		selenium.showContextualBanner(className, methodName);
	}

	@Override
	public void shutDownSeleniumServer() {
		processSnapshot("shutDownSeleniumServer", "");
		selenium.shutDownSeleniumServer();
	}



	@Override
	public void submit(String formLocator) {
		processSnapshot("submit", "");
		selenium.submit(formLocator);
	}



	@Override
	public void typeKeys(String locator, String value) {
		processSnapshot("typeKeys", "locator=["+locator+"] value=["+value+"]");
		selenium.typeKeys(locator, value);
	}

	@Override
	public void uncheck(String locator) {
		processSnapshot("uncheck", "locator=["+locator+"]");
		selenium.uncheck(locator);
	}

	@Override
	public void useXpathLibrary(String libraryName) {
		processSnapshot("useXpathLibrary", "libraryjName=["+libraryName+"]");
		selenium.useXpathLibrary(libraryName);
	}

	@Override
	public void waitForCondition(String script, String timeout) {
		processSnapshot("waitForCondition", "script=["+script+"]  timeout=["+timeout+"]");
		selenium.waitForCondition(script, timeout);
	}

	@Override
	public void waitForFrameToLoad(String frameAddress, String timeout) {
		processSnapshot("waitForFrameToLoad", "frameAddress=["+frameAddress+"] timeout=["+timeout+"]");
		selenium.waitForFrameToLoad(frameAddress, timeout);
	}



	@Override
	public void waitForPopUp(String windowID, String timeout) {
		processSnapshot("waitForPopUp", "windowID=["+windowID+"] timeout=["+timeout+"]");
		selenium.waitForPopUp(windowID, timeout);
	}

	@Override
	public void windowFocus() {
		processSnapshot("windowFocus", "");
		selenium.windowFocus();
	}


	@Override
	public String getLog() {
		processSnapshot("getLog", "");
		String rez=  selenium.getLog();
		return rez;
	}


	@Override
	public void open(String arg0, String arg1) {
		processSnapshot("open", "arg0=["+arg0+"] arg1=["+arg1+"]");
		selenium.open(arg0, arg1);
	}

}
