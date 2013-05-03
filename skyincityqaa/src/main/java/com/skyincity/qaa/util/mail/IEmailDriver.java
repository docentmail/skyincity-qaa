package com.skyincity.qaa.util.mail;

import com.thoughtworks.selenium.Selenium;


/**
 *  Interface represents email driver to perform simple operations with email service
 *
 */
public interface IEmailDriver {
//	public static String DEFAULT_SERVER_TO_SEND="shc-it.shcintra.net" ;
//    public MailFootprint[] getAllEmails(final String eMailPrefix,final Selenium selenium) throws Exception;	
//	public EmailServiceEnum getEmailServiceEnum();	

	
	public int countAllEmails(final String eMailPrefix, final Selenium selenium)  throws Exception;

    
	public int getNamePrefixMaxLength ();
	public int getSubjectMaxLength ();
	public int getMaxNumEmailsOnPage();
	public boolean isPageFull(int numMsgsOnPage);

	public String getDomain();
	/**
	 * how long wait for email delivery. Added because default 40 sec was not enough for MAILINATOR
	 * 
	 * @return
	 */
	public int getTotalWaitingTime();

	
	public String getServerToSendEmail();
	

}
