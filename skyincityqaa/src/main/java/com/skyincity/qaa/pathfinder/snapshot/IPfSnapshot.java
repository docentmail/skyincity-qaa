package com.skyincity.qaa.pathfinder.snapshot;

import com.skyincity.qaa.pathfinder.IFileStorage;
import com.skyincity.qaa.pathfinder.artifact.IPfArtifact;
import com.skyincity.qaa.util.flexwait.ILogEventsStorage;
import com.skyincity.qaa.util.flexwait.ISnapshot.SnapshotTypeEnum;

public interface IPfSnapshot {
	public String toHTML();
	public String toString();
	public void store(IFileStorage fileStorage) throws Exception;
	public boolean isPfArtifactsStored();
    public void addArtifact(IPfArtifact art);
}
