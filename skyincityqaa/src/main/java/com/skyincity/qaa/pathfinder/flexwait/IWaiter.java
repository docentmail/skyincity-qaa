package com.skyincity.qaa.pathfinder.flexwait;

public interface IWaiter {
	public void waitIt() throws Exception;
	public void waitProcess() throws Exception;
	public long getOkLimitInMilliseconds();
	public long getWarnLimitInMilliseconds();
}
