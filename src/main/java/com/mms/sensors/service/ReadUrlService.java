package com.mms.sensors.service;

public interface ReadUrlService {

	String readFromUrl(String url);
	String readFromFile(String url);

	String readFromGivenUrl(String ip , String url);

	
	
	
}
