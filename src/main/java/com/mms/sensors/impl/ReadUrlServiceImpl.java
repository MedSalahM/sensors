package com.mms.sensors.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mms.sensors.error.MyCustomMalFormedExeption;
import com.mms.sensors.service.ReadUrlService;



@Service
public class ReadUrlServiceImpl implements ReadUrlService {

    private final String svnUrl;
    
    public ReadUrlServiceImpl (@Value("${SVN_URL}") String svnUrl) {
        this.svnUrl = svnUrl;
    }
	
	@Override
	public String readFromUrl(String url){
	
		String apiUrl = "http://"+svnUrl+url;
		
        String content = null;
		
		try {
			
		
			URL myUrl = new URL(apiUrl);
			
			
		
			try {
				
				
				InputStream stream = 	myUrl.openStream();
				
				
				 var list =
					      new BufferedReader(new InputStreamReader(stream,
					          StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
				 
				 
				 StringBuilder stringBuilder = new StringBuilder("");
				 
				 if(list!=null && list.size()>0) {
					 
					 list.forEach(e->stringBuilder.append(e).append("\n"));
				
					 
				 }
				 
				 content = stringBuilder.toString();
				 
				
				
			
				 
			}
			
			 catch (IOException e) {

				 
				 throw new MyCustomMalFormedExeption("erreur est survenue ... : "+e.getMessage());
		
				 
			 }
			
			
			
		 }
		
		
		catch (MalformedURLException e) {
			

			 throw new MyCustomMalFormedExeption("Adresse est mal formé .... : "+e.getMessage());
			
		}

		
		return content;
		
		
	}

	@Override
	public String readFromFile(String url) {
		
          
		Path path = Paths.get(url);
        String content = null;

		try {
			 var list = Files.readAllLines(path);
			 StringBuilder stringBuilder = new StringBuilder("");
			 
			 
			 if(list!=null && list.size()>0) {
				 
				 list.forEach(e->stringBuilder.append(e).append("\n"));
						
				 content = stringBuilder.toString();

				 
			 }
			 
			 
			
		} 
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
		return content;
		
	}

	@Override
	public String readFromGivenUrl(String ip , String url) {
		
	String apiUrl = "http://"+ip+url;
		
        String content = null;
		
		try {
			
		
			URL myUrl = new URL(apiUrl);
			
			
		
			try {
				
				
				InputStream stream = 	myUrl.openStream();
				
				
				 var list =
					      new BufferedReader(new InputStreamReader(stream,
					          StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
				 
				 
				 StringBuilder stringBuilder = new StringBuilder("");
				 
				 if(list!=null && list.size()>0) {
					 
					 list.forEach(e->stringBuilder.append(e).append("\n"));
				
					 
				 }
				 
				 content = stringBuilder.toString();
				 
				
				
			
				 
			}
			
			 catch (IOException e) {

				 
				 throw new MyCustomMalFormedExeption("erreur est survenue ... : "+e.getMessage());
		
				 
			 }
			
			
			
		 }
		
		
		catch (MalformedURLException e) {
			

			 throw new MyCustomMalFormedExeption("Adresse est mal formé .... : "+e.getMessage());
			
		}

		
		return content;
	}
	
	
	
}
