package com.skyincity.qaa.util.common;

/**
 * Class for catchable QA automation framework exceptions
 * 
 */

public class QaException extends Exception {
	public QaException(){
		super();
	}
	public QaException(String message) {
		super(message);
		}
    
	public QaException(String message, Throwable cause){
		super(message,  cause);
		} 
    
	public QaException(Throwable cause){
		super(cause);
		}
    
}