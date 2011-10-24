package com.skyincity.qaa.util.fileSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.skyincity.qaa.entities.BrowserFamily;
import com.skyincity.qaa.entities.LaunchedBrowserOnRc;
import com.thoughtworks.selenium.Wait;
/**
 * Class contains helper methods to interact with RC file system (remote or local)
 * 
 */

public class RcFileSystemHelper {
	/**
	 * Deletes downloaded files related to one file form the specified (browser download) folder
	 * e.g. for FireFox:
	 *  if specify fileName="Tag Library Admin";  fileExtention="xls"    
	 *  files would be deleted: "Tag Library Admin.xls", "Tag Library Admin(2).xls", "Tag Library Admin(3).xls"
	 * @param browser
	 * @param downloadFolderPath
	 * @param fileName
	 * @param fileExtention
	 * @throws Exception
	 */

		public static void deleteDownloadedFiles(LaunchedBrowserOnRc launchedBrowserOnRc, String fileName, String fileExtention) throws Exception{
			final IRcFileSystem rcFS =launchedBrowserOnRc.getRcFileSystem();
			String filePattern;
			if (fileName==null || fileName.length()==0 ) {
				throw new Exception("fileName is not specified:"+"\""+ fileName +"\"") ;
			}
			
			if (fileExtention==null || fileExtention.trim().length()==0 ) {
				fileExtention=null;
			}
			
			BrowserFamily browserFamily =launchedBrowserOnRc.getBrowser().getBrowserFamily();
			
			if (browserFamily.equals(BrowserFamily.FIREFOX)) {
					if (null!=fileExtention) {
						filePattern="^"+fileName+"(\\(\\d*\\))?\\."+fileExtention+"$";// ^Tag Library Admin(\(\d*\))?\.xls$	
					} else {
						filePattern="^"+fileName+"(\\(\\d*\\))?$";// ^Tag Library Admin(\(\d*\))?$					}
					}	
			} else {
					throw new Exception("The browser family is not emplimented yet" + browserFamily);
				
			}
		
			
//			File downLoadFolder = new File(downloadFolderPath);
//			if (!downLoadFolder.exists()) {
//				throw new Exception("Download folder doesn't exists:"+"\""+ downloadFolderPath +"\"");
//			}
			
			
			if (!rcFS.exists(launchedBrowserOnRc.getBrowserDownloadFolder())) {
				throw new Exception("Download folder doesn't exists:"+"\""+ launchedBrowserOnRc.getBrowserDownloadFolder() +"\"");
			}

			
//			if (!downLoadFolder.isDirectory()) {
//				throw new Exception("Download folder is not a directory:"+"\""+ downloadFolderPath +"\"");
//			}

			if (!rcFS.isDirectory(launchedBrowserOnRc.getBrowserDownloadFolder())) {
				throw new Exception("Download folder is not a directory:"+"\""+ launchedBrowserOnRc.getBrowserDownloadFolder() +"\"");
			}

			
			String[] flList= rcFS.listFiles(launchedBrowserOnRc.getBrowserDownloadFolder());
//			File[] flList= downLoadFolder.listFiles();
			if (flList==null || flList.length==0) {
				return;
			}
			
			for(String fn:  flList ) {
				 Pattern pattern =    Pattern.compile(filePattern);
				 File file = new File(fn);
				 Matcher matcher =    pattern.matcher(file.getName());
				 if (matcher.matches()) {
					 final String ffn=fn;
							new Wait() {
									public boolean until() {
							
										try {
											 if (rcFS.isFileExist(ffn)) {
												 if (!rcFS.delete(ffn)) {
													 throw new Exception("Error during file delition. File name="+ffn);
												 }
											 }
											 return true;
										} catch (Exception e) {
											return true; // 
										}
								    }
							}.wait("Error during file delition. File name="+ffn,30000, 1000);
						
					 
					 
					 
					 
					 
					 
//					 if (rcFS.isFileExist(fn)) {
////						 if (!fl.delete()) {
//						 if (!rcFS.delete(fn)) {
//							 throw new Exception("Error during file delition. File name="+fn);
//						 }
//					 }
					 
				 }
			}
			
		}


		
		
		
		/**
		 * 
		 * @param browser
		 * @param downloadFolderPath
		 * @param fileNameRegExp e.g for "report-vendor-direct-items-10054290.csv" it would be "report-vendor-direct-items-(\d*)"   
		 * @param fileExtention
		 * @throws Exception
		 */
		public static void deleteDownloadedFilesByRegExp(LaunchedBrowserOnRc launchedBrowserOnRc, String fileNameRegExp, String fileExtention) throws Exception{
			 deleteDownloadedFiles(launchedBrowserOnRc, fileNameRegExp, fileExtention);
		}
	
		public static String[] getDownloadedFilesFromListByRegExp(LaunchedBrowserOnRc launchedBrowserOnRc, String[] flList, String fileNameRegExp, String fileExtention) throws Exception{
			if (flList==null || flList.length==0) {
				return null;
			}

			List rez=new ArrayList<String>();
			
			String filePattern;
			if (fileNameRegExp==null || fileNameRegExp.length()==0 ) {
				throw new Exception("fileName is not specified:"+"\""+ fileNameRegExp +"\"") ;
			}
			
			if (fileExtention==null || fileExtention.trim().length()==0 ) {
				fileExtention=null;
			}

			BrowserFamily browserFamily =launchedBrowserOnRc.getBrowser().getBrowserFamily();
			if (browserFamily.equals(BrowserFamily.FIREFOX)) {
					if (null!=fileExtention) {
						filePattern="^"+fileNameRegExp+"(\\(\\d*\\))?\\."+fileExtention+"$";// ^Tag Library Admin(\(\d*\))?\.xls$	
					} else {
						throw new Exception("Files without extention are not supported yet" );
						//    filePattern="^"+fileName+"(\\(\\d*\\))?$";// ^Tag Library Admin(\(\d*\))?$
					}
			} else {
				throw new Exception("The browser family is not emplimented yet" + browserFamily);
			
			}
		
			
			for(String fn:  flList ) {
				 Pattern pattern =    Pattern.compile(filePattern);
				 File file = new File(fn);
				 Matcher matcher =    pattern.matcher(file.getName());
				 if (matcher.matches()) {
					 rez.add(file.getName());
				 }
			}

		  if (rez.size()==0) {
			  return null;
		  } else {
			  return  (String[]) rez.toArray(new String[0]);
		  }
			
		}	
		
		
    /**
     * Deletes downloaded files related to one file form the specified (browser download) folder
	 * e.g. for FireFox:
	 *  if specify fileNameWithExtension="Tag Library Admin.xls"
	 *  files would be deleted: "Tag Library Admin.xls", "Tag Library Admin(2).xls", "Tag Library Admin(3).xls"
     * @param browser
     * @param downloadFolderPath
     * @param fileNameWithExtension
     * @throws Exception
     */
    public static void deleteDownloadedFiles(LaunchedBrowserOnRc launcedBrowserOnRc, String fileNameWithExtension) throws Exception {
        String fileName = null;
        String fileExtension = null;
        if (fileNameWithExtension != null && fileNameWithExtension.contains(".")) {
            fileName = fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
            fileExtension = fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf(".") + 1);
        } else {
            fileName = fileNameWithExtension;
        }
        deleteDownloadedFiles(launcedBrowserOnRc, fileName, fileExtension);
    }

//		/**
//		 * 	/**
//			 * to resolve not finished upload file reading
//			 * 
//		Unable to read entire header; 0 bytes read; expected 32 bytes
//		 *
//		 * @param fileFullPath
//		 * @param testString
//		 * @return
//		 * @throws Exception
//		 */
//			public static boolean isStringInDownloadedExcelFile(final String fileFullPath, final String testString, 
//					int iTotalWaitingTime, int iRepetitionInterval) throws Exception {
//				if (testString==null || testString.length()==0) {
//					throw new Exception("test string is null or empty");
//				}
//				try {
//					new Wait() {
//							public boolean until() {
//								InputStream inp=null;
//								try {
//									// inp = new FileInputStream(fileFullPath);
//									inp = RcFileSystemFactory.getRcFileSystem().getInputStreamFromRcFile(fileFullPath);
//									boolean rez = ExcelHelper.isStringInFile(inp,testString);
//									return rez;
//								} catch (IOException ioe) {
//									return false;
//								} catch (Exception e) {
//									return true; // 
//								} finally {
//									if (inp!=null) {
//										try {
//											inp.close();
//										} catch (IOException e) {
//											e.printStackTrace();
//										}
//									}
//								}
//								
//						    }
//					}.wait("",iTotalWaitingTime, iRepetitionInterval);
//				} catch (Exception e) {
//					return false; 
//				}
//				
//
//				InputStream inp=null;
//				try {
//					inp = RcFileSystemFactory.getRcFileSystem().getInputStreamFromRcFile(fileFullPath);
//					boolean rez= ExcelHelper.isStringInFile(inp,testString);
//					return rez;
//				} finally {
//					if (inp!=null) {
//						inp.close();
//					}	
//				}
//
//				
//			}
			
//			/**
//			 * Prepares files in upload folder available for browser launched by Selenium Remoute Control
//			 * Copies files from jar classpath to real disk folder. It makes possible to call something like
//			 *   selenium.type("file",fileAbsolutePath ); 
//			 *   selenium.click("//a//*[text()='Upload Template']");
//			 *  
//			 * @param ftua 
//			 * @throws Exception
//			 */
//			public static void prepareBrowserUploadFolder(FileToUpload[] ftua) throws Exception{
//				 IConfigurationFacade cf=ConfigurationFactory.getConfigurationFacade();
//				 IRcFileSystem rcFS= RcFileSystemFactory.getRcFileSystem();
//				 
//				 rcFS.cleanDirectory(cf.getBrowserUploadFolder());
////				 FileUtils.cleanDirectory(new File (icf.getBrowserUploadFolder()));
//				 
//				 assert ftua!=null: "FileToUpload[] is null"; 
//				 assert ftua.length>0: "FileToUpload[] length =0";
//				 for (FileToUpload ftu: ftua) {
//					 URL fromUrl = String.class.getResource(ftu.getClasspath()); // ?????
//					 String toPath = cf.getBrowserUploadFolder()+cf.getRemoteControlOs().getFileSeparator()+ ftu.getRelativePathInUploadFolder();
//					 rcFS.sendUrlToRcFile(fromUrl, toPath);           
//				 }	 
//			}
//			
//			
//			/**
//			 * searchs file to upload in array by its allias
//			 * @param alias
//			 * @param fileToUploadArr
//			 * @return
//			 */
//			public static FileToUpload findFileToUploadIArrayByAlias(String alias, FileToUpload[] fileToUploadArr) {
//				if (alias==null || alias.trim().length()==0) {
//					throw new IllegalArgumentException("alias is null or empty  [" +alias+"]" );
//				}
//				if (fileToUploadArr == null|| fileToUploadArr.length == 0) {
//					return null;
//				}
//				for (FileToUpload fu:fileToUploadArr) {
//					if (alias.equals(fu.getAlias())){
//						return fu;
//					}
//				}
//				
//				return null;
//			}

}
