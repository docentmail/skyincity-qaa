package com.skyincity.qaa.pathfinder;

public interface IFileStorage {
	/**
	 * file:///C:/QAAuto/jenkins/jobs/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 * http://qa-win-auto:8080/details/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/18_36_22_679_final%20Warning%20snapshot.png
	 * 
	 */
/**
 * 	
 * @return something like C:\\QAAuto\\jenkins\\jobs\\SSSS_PROD\\builds\\2011-12-28_18-16-19\\_LogArtifacts/
 */
   String getBoxPathToFileFolder();
   /**
    * 
    * @return something like http://qa-win-auto:8080/details/SSSS_PROD/builds/2011-12-28_18-16-19/_LogArtifacts/
    */
   String getWebPathToFileFolder();
}
