package com.skyincity.qaa.util.flexwait;

public interface ILogEventsStorageFactory {
	public ILogEventsStorage getOkLogEventsStorage();
	public ILogEventsStorage getWarningLogEventsStorage();
	public ILogEventsStorage getErrorLogEventsStorage();
	
	public long getOpenOkLimitMs();
	public long getOpenWarnLimitMs();

	public long getEmailOkLimitMs();
	public long getEmailWarnLimitMs();
	
	public long getAjaxOkLimitMs();
	public long getAjaxWarnLimitMs();
	
	public long getJsOkLimitMs();
	public long getJsWarnLimitMs();

	public long getFileDownloadOkLimitMs();
	public long getFileDownloadWarnLimitMs();
	
	public long getFileUploadOkLimitMs();
	public long getFileUploadWarnLimitMs();
	
	public boolean isLogOkRequired();
	

    public static enum WaitType{
    	Open, Email, Ajax, Js, FileDownload, FileUpload
    }
    
    public long getOkLimitInMilliseconds (ILogEventsStorageFactory.WaitType waitType);
    public long getWarnLimitInMilliseconds (ILogEventsStorageFactory.WaitType waitType); 


}
