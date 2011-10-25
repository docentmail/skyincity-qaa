package com.skyincity.qaa.datatracker;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ArtifactStorage {
	private SortedSet<Artifact> storageSet;  
	
	public boolean addArtifact(Artifact artifact){
		return storageSet.add(artifact);
	}
	public boolean removeArtifact(Artifact artifact){
		return storageSet.remove(artifact);
	}
	
//	public abstract Artifact[] getAllArtifactsForClass(Class cls);
//	public abstract Artifact[] getAllArtifactsForMethod(Class cls);
	
	// by Class.MethodName.ArtifactID
	public String printOutAllArtifacts(){
		StringBuilder sb = new StringBuilder();
		synchronized(storageSet) {
		      Iterator<Artifact> i = storageSet.iterator(); // Must be in the synchronized block
		      while (i.hasNext())

		         sb.append("\n"). append( i.next().toString());
		}
		return sb.toString();
	}
	public ArtifactStorage() {
		super();
		// TODO Auto-generated constructor stub
		storageSet = Collections.synchronizedSortedSet (new TreeSet<Artifact>());
	}
	
	
	
	
}
