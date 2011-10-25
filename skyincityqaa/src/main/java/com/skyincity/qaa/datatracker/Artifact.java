package com.skyincity.qaa.datatracker;

import java.lang.reflect.Method;

/**
 * represents piece of data
 */
public class Artifact implements Comparable<Artifact> {

	public Artifact(Method mthd, String artifactId, Object data, String comment) {
		super();
		this.mthd = mthd;
		this.artifactId = artifactId;
		this.data = data;
		this.comment = comment;
	}

	// test info to gather output for 1 specific test
	// private Class clss; // ITestNGMethod.getRealClass();
	private final Method mthd; // ITestNGMethod.getMethod();

	// Artifact ID
	private final String artifactId;

	// Data - what should be removed in case of cleaning skipped or failed in
	// the middle
	private final Object data;

	// jast text comments
	private final String comment;

	/**
	 * for method "getFullMethodName" of class "Artifact" from package
	 * "com.skyincity.qaa.datatracker"
	 * 
	 * @return getFullMethodName
	 */
	public String getMethodName() {
		return mthd.getName();
	}

	/**
	 * for method "getFullMethodName" of class "Artifact" from package
	 * "com.skyincity.qaa.datatracker"
	 * 
	 * @return com.skyincity.qaa.datatracker.Artifact
	 */
	public String getClassName() {
		return mthd.getDeclaringClass().getCanonicalName();
	}

	/**
	 * for method "getFullMethodName" of class "Artifact" from package
	 * "com.skyincity.qaa.datatracker"
	 * 
	 * @return com.skyincity.qaa.datatracker.Artifact_getFullMethodName
	 */
	public String getFullMethodName() {
		return getClassName() + "_" + getMethodName();
	}

	/**
	 * Class + Method + ArtifactId  
	 * 
	 * @return 
	 */
	public int compareTo(Artifact aThat) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == aThat)
			return EQUAL;

		String thisStr = this.getFullMethodName()+this.getArtifactId();
		String thatStr = aThat.getFullMethodName()+aThat.getArtifactId();
		
		return thisStr.compareTo(thatStr);
	}

	public String getArtifactId() {
		return artifactId;
	}

	public Object getData() {
		return data;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "Artifact [mthd=" + getFullMethodName() + ", artifactId=" + artifactId
				+ "\n data=" + data + "\n comment=" + comment + "]";
	}

	/**
	 * by getFullMethodName() and artifactId
	 * comment & data are ignored 
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((artifactId == null) ? 0 : artifactId.hashCode());
		result = prime * result + ((getFullMethodName() == null) ? 0 : getFullMethodName().hashCode());
		return result;
	}
 
	/**
	 * by getFullMethodName() and artifactId
	 * comment & data are ignored 
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artifact other = (Artifact) obj;
		if (artifactId == null) {
			if (other.artifactId != null)
				return false;
		} else if (!artifactId.equals(other.artifactId)){
			return false;
		} else if (getFullMethodName() == null) {
			if (other.getFullMethodName() != null)
				return false;
		} else if (!getFullMethodName().equals(other.getFullMethodName()))
			return false;
		return true;
	}

	
	
	
	
}
