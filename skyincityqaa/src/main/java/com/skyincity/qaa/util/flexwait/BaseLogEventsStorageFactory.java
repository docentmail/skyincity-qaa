package com.skyincity.qaa.util.flexwait;

public abstract class BaseLogEventsStorageFactory implements ILogEventsStorageFactory {

	@Override
    public long getOkLimitInMilliseconds (ILogEventsStorageFactory.WaitType waitType) {
    	switch(waitType) {
    		case Open: return   getOpenOkLimitMs();
    		case Email: return   getEmailOkLimitMs();
    		case Ajax: return   getAjaxOkLimitMs();
    		case Js: return   getJsOkLimitMs();
    		case FileDownload: return   getFileDownloadOkLimitMs();
    		case FileUpload: return   getFileUploadOkLimitMs();
    		default: throw new IllegalArgumentException("Ok time is not defined for waitType=["+waitType+"]");
    	}
    }
    
	@Override
    public long getWarnLimitInMilliseconds ( ILogEventsStorageFactory.WaitType waitType){ 
    	switch(waitType) {
    		case Open: return   getOpenWarnLimitMs();
    		case Email: return   getEmailWarnLimitMs();
    		case Ajax: return   getAjaxWarnLimitMs();
    		case Js: return   getJsWarnLimitMs();
    		case FileDownload: return   getFileDownloadWarnLimitMs();
    		case FileUpload: return   getFileUploadWarnLimitMs();
    		default: throw new IllegalArgumentException("Warn time is not defined for waitType=["+waitType+"]");
    	}
    }


}
