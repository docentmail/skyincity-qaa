package com.skyincity.qaa.util.http;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HttpUtilTest {
	
	@BeforeClass
	 public void setUp() {
	   // code that will be invoked when this test is instantiated
	 }
	 
	 @Test(groups = { "util" })
	 public void aFastTest() throws Exception {
		 RemoteFile rf= HttpUtil.getFileFromUrl(null, "http://www.sears.com/ue/home/trendad-0213-lightlayers.jpg", "trendad-0213-lightlayers.jpg",  5000);
		 System.out.println("rf="+rf);
	   System.out.println("aFastTest");
	 }
	 
	 
	 @Test(groups = { "util" })
	 public void isStringInFileTest() throws Exception {
		   System.out.println("isStringInFileTest");
		 
	 }
	
	 
	 @Test(groups = { "slow" })
	 public void aSlowTest() {
	    System.out.println("Slow test");
	 }


}
