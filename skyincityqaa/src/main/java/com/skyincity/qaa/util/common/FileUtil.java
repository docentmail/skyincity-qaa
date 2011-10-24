package com.skyincity.qaa.util.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.*;
import java.io.OutputStream;
import org.apache.commons.io.FileUtils;


/**
 * Class that implements some file system related operations
 *
 */
public class FileUtil {
	
	/**
	 * Read Stream into String 
	 * 
	 * @param is
	 * @param charsetName   e.g "UTF-8"
	 * @return
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream is, String charsetName) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, charsetName));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            if (sb.length()>=1) {
            	return sb.delete(sb.length()-1, sb.length()). toString();
            } else {
            	return "";
            }
        } else {        
            return "";
        }
    }
	/**
	 * 
	 * @param pathJar 
	 *  samples:  com/skyincity/qaa/util/common/Desert.jpg
	 *            xsd/seller-error-report-v1
	 * @return
	 * @throws Exception
	 */
	public InputStream resolveJarPath(String pathJar) throws Exception{
		// http://www.google.com/codesearch/p?hl=en#UivOgXeeEz8/debian/pool/main/libm/libmx4j-java/libmx4j-java_2.1.1.orig.tar.gz|HiftxcLClsQ/mx4j-2.1.1/src/tools/mx4j/tools/adaptor/http/XSLTProcessor.java&q=getClassLoader%28%29.getResource%20lang:java
		 File root=null;
		 InputStream inpStream = null;
		 ClassLoader targetClassLoader = ClassLoader.getSystemClassLoader();

		 
        // load from a jar
        String targetFile = pathJar;
        
        if (root != null)
        {
           inpStream = targetClassLoader.getResourceAsStream(targetFile);
        }
        if (inpStream == null)
        {
           ClassLoader cl = getClass().getClassLoader();
           if (cl == null)
           {
              inpStream = ClassLoader.getSystemClassLoader().getResourceAsStream(targetFile);
           }
           else
           {
              inpStream = getClass().getClassLoader().getResourceAsStream(targetFile);
           }
           
           // file = getClass().getClassLoader().getResourceAsStream(targetFile);
        }
	   return inpStream;	
	}


	/**
	 * 
	 * Deletes any files and directories in directory recursively.
	 * 
	 * @param folderPath 
	 * @param isRecursively - 
	 * 			true - delete subfolders recursively; 
	 * 			false - files only
	 * @throws Exception
	 */
	@Deprecated
	public static void deleteAllInTheFolder(String folderPath, boolean isRecursively) throws Exception{
		File fld= new File(folderPath);
		String[] flList=fld.list();
		for (String str: flList) {
			File fl=new File(folderPath+File.separator+str);
			if (fl.isFile()) {
				if (!fl.delete()) {
					throw new Exception("Error during delition file  \""+folderPath+File.separator+str+"\"  ");
				}
			} else if (fl.isDirectory()) {
				if (isRecursively) {
					deleteDir(fl);	
				}
			}
		}
	}

	
	
	 /**
	   * Deletes the specified directory and any files and directories in it
	   * recursively.
	   * 
	   * @param dir The directory to remove.
	   * @throws IOException If the directory could not be removed.
	   */
	@Deprecated
	  public static void deleteDir(File dir)
	    throws IOException
	  {
	    if (!dir.isDirectory()) {
	      throw new IOException("Not a directory " + dir);
	    }
	    
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	      File file = files[i];
	      
	      if (file.isDirectory()) {
	        deleteDir(file);
	      }
	      else {
	        boolean deleted = file.delete();
	        if (!deleted) {
	          throw new IOException("Unable to delete file" + file);
	        }
	      }
	    }
	    
	    dir.delete();
	  }
	  
	  public static void copyFile(File in, File out) throws IOException {
		  copyFileStream(new FileInputStream(in), new FileOutputStream(out));
	   }

		
		public static void copyFileStream(FileInputStream is, FileOutputStream os) throws IOException {
			FileChannel inChannel = is.getChannel();
			FileChannel outChannel = os.getChannel();
			try {
				inChannel.transferTo(0, inChannel.size(), outChannel);
			} catch (IOException e) {
				throw e;
			} finally {
				if (inChannel != null)
					inChannel.close();
				if (outChannel != null)
					outChannel.close();
			}
		}

		  /** 
		  * Copy the content of the input stream into the output stream, using a temporary 
		  * byte array buffer whose size is defined by {@link #IO_BUFFER_SIZE}. 
		  * 
		  * @param in The input stream to copy from. 
		  * @param out The output stream to copy to. 
		  * 
		  * @throws IOException If any error occurs during the copy. 
		  */  
		  private static final int IO_BUFFER_SIZE = 4 * 1024;  
		     
		  public static void copy(InputStream in, OutputStream out) throws IOException {  
			  byte[] b = new byte[IO_BUFFER_SIZE];  
			  int read;  
			  while ((read = in.read(b)) != -1) {  
				  out.write(b, 0, read);  
			  }  
		  }  
		  
		  
			public static int WriteFile(StringBuffer buf, String FileName, String aDir,
					String EncodStr, boolean CanRewrite) throws Exception {
				String oFn = FileName;
				if (null != aDir && 0 < aDir.length())
					oFn = (new StringBuilder()).append(aDir).append("\\").append(
							FileName).toString(); // TODO change to file separator
				return WriteFile(buf.toString(), oFn, EncodStr, CanRewrite);
			}

			public static int WriteFile(String buf, String fullFileName,
					String EncodStr, boolean CanRewrite) throws Exception {
				File oFl = new File(fullFileName);
				if (oFl.exists())
					if (CanRewrite)
						oFl.delete();
					else
						throw new Exception((new StringBuilder())
								.append("Error! File:").append(fullFileName).append(
										" exist!").toString());
				try {
					File outfile = new File(fullFileName);
					
					FileUtils.writeStringToFile(outfile, buf);
					
				} catch (IOException e) {
					throw new Exception((new StringBuilder()).append(
							"Error! IO in file:").append(fullFileName).append(" ")
							.append(e.getMessage()).toString());
				}
				return buf.length();
			}

			/**
			 * for "fileMane.csv" returns "fileMane" 
			 * for "fileMane1.fileMane2.csv" returns "fileMane1.fileMane2"
			 * Full path like "C:\Program Files (x86)\Far2\fileMane.csv" is not allowed
			 * 
			 * @param fileNameWithExtension - just name and extension - no folder info
			 * @return
			 */
			public static String getFileNameOnly(String fileNameWithExtension) {
				return fileNameWithExtension.substring(0, fileNameWithExtension.lastIndexOf("."));
			}
			
			/**
			 * for "fileMane.csv" returns "csv" 
			 * for "fileMane1.fileMane2.csv" returns "csv"
			 * Full path like "C:\Program Files (x86)\Far2\fileMane.csv" is not allowed
			 * 
			 * @param fileNameWithExtension - just name and extension - no folder info
			 * @return
			 */
			public static String getExtensionOnly(String fileNameWithExtension) {
				return fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf(".") + 1);
			}



		
}
