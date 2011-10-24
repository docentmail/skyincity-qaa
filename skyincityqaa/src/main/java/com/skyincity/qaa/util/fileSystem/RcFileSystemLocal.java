package com.skyincity.qaa.util.fileSystem;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import com.skyincity.qaa.util.common.CommonUtil;
import com.skyincity.qaa.util.common.FileUtil;
import org.apache.commons.io.FileUtils;

public class RcFileSystemLocal implements IRcFileSystem{

	
	
	public void cleanDirectory(String path) throws IOException {
		FileUtils.cleanDirectory(new File(path));		
	}

	public boolean delete(String path) {
		File fl=new File(path);
		return fl.delete();
	}

	public InputStream getInputStreamFromRcFile(String fromPath) throws Exception {
		return new FileInputStream(new File(fromPath));
	}


   public void  sendUrlToRcFile(URL fromUrl, String toPath)  throws Exception {
   	URLConnection uc = fromUrl.openConnection();
   	InputStream is= uc.getInputStream();
		 FileOutputStream os =  FileUtils.openOutputStream(new File(toPath));  
		 FileUtil.copy(is, os);
   }
   
	public boolean isDirectory(String path) {
		return new File(path).isDirectory();
	}

	public boolean isFile(String path) {
		return new File(path).isFile();
	}

	public boolean isFileExist(String fileFullPath) throws Exception {
		return CommonUtil.isFileExist(fileFullPath);
	}

	public boolean exists(String fileFullPath) throws Exception{
		return new File(fileFullPath).exists();
	}
	
	public String[] listFiles(String path) {
		File[] fls =new File(path).listFiles();
		List<String> rezL= new ArrayList<String>(); 
		for (File fl: fls){
			rezL.add(fl.getAbsolutePath());
		}
		if (rezL.size()==0) {
			return null;
		} else {
			return rezL.toArray(new String[1]);
		}
	}


}
