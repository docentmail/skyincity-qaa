package com.skyincity.qaa.util.flexwait;

public interface ISnapshot {
	public enum SnapshotTypeEnum {
		BEFORE_STATE,OK_STATE,	WARNING_STATE, ERROR_STATE
	}
	public String toHTML();
	public String toString();
	public SnapshotTypeEnum getType();
	public void storeArtifacts(ILogEventsStorage logEventsStorage) throws Exception;
	public boolean isArtifactsStored();
}
