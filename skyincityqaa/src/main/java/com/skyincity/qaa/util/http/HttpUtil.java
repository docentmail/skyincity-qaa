package com.skyincity.qaa.util.http;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.params.CookiePolicy;
import org.apache.log4j.Logger;


public class HttpUtil {
	private static Logger log = Logger.getLogger(HttpUtil.class);

	public static RemoteFile getFileFromUrl(String authCookies, String escapedUrl) throws Exception {
		String fileName="uspecified"; 		
		return getFileFromUrl(authCookies, escapedUrl, fileName);
	}

	public static RemoteFile getFileFromUrl(String authCookies, String escapedUrl, String fileName) throws Exception {
		return getFileFromUrl(authCookies, escapedUrl, fileName,new Integer(5000));
		
	}

	public static RemoteFile getFileFromUrl(String authCookies, String escapedUrl, String fileName, Integer timeOutMilliseconds) throws Exception {
        HttpClient client = new HttpClient();
        client.getParams().setParameter("http.connection.timeout",timeOutMilliseconds);
        HttpMethod method = new GetMethod();
    
        if (authCookies!=null){
	        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
	        method.setRequestHeader("Cookie", authCookies);
        }
        method.setURI(new URI(escapedUrl, true));
        int returnCode = client.executeMethod(method);
        if(returnCode != HttpStatus.SC_OK) {
            throw new  Exception("Unable to fetch file, status code: " + returnCode);
        }
        
        String type = method.getResponseHeader("Content-type").getValue();
        log.debug("Downloaded file of type " + type + " from URL " + escapedUrl);
        String extension = StringUtils.substringBetween(type, "/", ";");
        if(StringUtils.isBlank(extension)){
            extension = StringUtils.substringAfter(type, "/");
        }
        extension = "." + extension;
        byte[] imageData = method.getResponseBody();
        File temp = File.createTempFile("temp-http-get", StringUtils.isNotBlank(extension) ? extension : ".autoget");
        temp.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(temp);
        fos.write(imageData);
        fos.flush();
        fos.close();
        method.releaseConnection();
        
        //.getFile()
        RemoteFile remoteFile =new RemoteFile(temp, new URL(escapedUrl),  fileName);
        
        return remoteFile;
    }

	
	

}
