package com.skyincity.qaa.util.flexwait;

import com.thoughtworks.selenium.Wait;
import com.thoughtworks.selenium.Wait.WaitTimedOutException;

public  abstract class WaitFlex extends Wait {
	protected ICallBack callBack;
	
//    /** The amount of time to wait before giving up; the default is 30 seconds */
//    public static final long DEFAULT_OK_LIMIT = 30000l;
//    public static final long DEFAULT_WARN_LIMIT = 180000l;
//    /** The interval to pause between checking; the default is 500 milliseconds */ 
//    public static final long DEFAULT_INTERVAL = 500l;

	public WaitFlex() {
    }

	public WaitFlex(ICallBack callBack) {
		this.callBack= callBack;
    }

	/** Returns true when it's time to stop waiting */
    public abstract boolean until();
    
    public void wait(String message, long okLimitInMilliseconds,long warnLimitInMilliseconds, long intervalInMilliseconds) {
        long start = System.currentTimeMillis();
        long end = start + warnLimitInMilliseconds;
        while (System.currentTimeMillis() < end) {
            if (until()) {
            	if (callBack!= null) {
                	long deltaMs= System.currentTimeMillis() - start;
            		if (deltaMs <= okLimitInMilliseconds ) {
                		callBack.logWaitOK(new Long(deltaMs));
                	} else {
                		callBack.logWaitWarning(new Long(deltaMs));
                	}
            	}
            	return;
            }
            try {
                Thread.sleep(intervalInMilliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (callBack!= null) {
        		long deltaMs= System.currentTimeMillis() - start;
        		callBack.logError(new Long(deltaMs));
       	} 
        throw new WaitTimedOutException();
    }

}
