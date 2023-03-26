package com.mms.sensors.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.sensors.dto.ReceivedDataDto;
import com.mms.sensors.dto.SensorValueDto;
import com.mms.sensors.dto.SondeDto;
import com.mms.sensors.error.DataProcessingException;
import com.mms.sensors.error.MyCustomMalFormedExeption;
import com.mms.sensors.service.DataRetriveService;
import com.mms.sensors.service.ReadUrlService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DataRetriveImpl implements DataRetriveService {

	private final ReadUrlService readUrlService;
	
	@Value("${ips}")
	private String[] arrayOfStrings;
	
	@Override
	public ReceivedDataDto renderReceivedData(String url) {
		
		ReceivedDataDto dto = null;

		String result = readUrlService.readFromUrl(url);


		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(result, ReceivedDataDto.class);
		} catch (JsonProcessingException e) {
		
			throw new DataProcessingException("impossible de convertir les donneés vers json");
		}
		
		
		
		return dto;
	}

	
	
	@Override
	public ReceivedDataDto renderReceivedData(String ip, String url) {
		
		
		ReceivedDataDto dto = null;

		String result = readUrlService.readFromGivenUrl(ip,url);


		
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(result, ReceivedDataDto.class);
		} catch (JsonProcessingException e) {
		
			throw new DataProcessingException("impossible de convertir les donneés vers json");
		}
		
		
		
		return dto;
	}
	
	
	
	
	
	
	
	@Override
	public List<SensorValueDto> getDataFromReceivedJson(ReceivedDataDto receivedDataDto) {
	
		
		List<SensorValueDto> dataValues = new ArrayList<>();
		
		var recordData = receivedDataDto.getQuerx().getData();
		var sensorsData = receivedDataDto.getQuerx().getSensors();

		
		var records=recordData.stream()
		.map(r->r.getRecord())
		.toList();
		
		
		        records.stream().forEach(record->{      
		        String date=record.getDate();
	    	    String time = record.getTime();
	    	    String timestamp=record.getTimestamp();
	    	    
	    	    String dt = getDateFromTimeStamp(timestamp);
	    	    String tm= getTimeFromTimeStamp(timestamp);
	    	    
	    	    
	    	    
	    	    
	    	    
	    	    var entries=record.getEntry();
	    	    
	    	    entries.forEach(e->{
	    	    	
	    	    	dataValues.add(
	    	    			
	    	    			 SensorValueDto.builder()
	    	    			.ip(receivedDataDto.getQuerx().getIp())
	    	    			.hostname(receivedDataDto.getQuerx().getHostname())
	    	    			.date(dt)
	    	    			//.time(tm)
	    	    			//.timestamp(timestamp)
	    	    			.sensorid(e.getSensorid())
	    	    			.value(e.getValue())
	    	    			.trend(e.getTrend())
	    	    			.build()
	    	    			
	    	    			);
	    	    });
	    	   
	    	   
		});
		

		
        List<SondeDto> sondes = sensorsData
    		                    .stream().map(e->e.getSensor())
    		                    .toList();
      
      
      
      
      dataValues.stream().forEach(e->{
    	  
    
    	  e.setUnit(getSensorDetailsFromSensors(sondes, e.getSensorid()).getUnit());
    	  e.setLolim(getSensorDetailsFromSensors(sondes, e.getSensorid()).getLolim());
    	  e.setUplim(getSensorDetailsFromSensors(sondes, e.getSensorid()).getUplim());
    	  e.setName(getSensorDetailsFromSensors(sondes, e.getSensorid()).getName());
    	 
    	  
      });
		
		
		
		return dataValues;
	}


	
    public static SondeDto getSensorDetailsFromSensors(List<SondeDto> sensorDtos , String id) {
		
    	
    	SondeDto dto = new SondeDto(id, "", "", "", "", "");
    	
		
		
		
	    Optional<SondeDto> optionalSen = sensorDtos.stream().filter(e->e.getId().equals(id)).findAny();
	   
	    if (optionalSen.isPresent()) {
		
		  
	    	return optionalSen.get();
		   
		   
	    }
		
		return dto;
	
	}
	
	
	

	@Override
	public ReceivedDataDto renderReceivedDataFromFile(String url) {
	
		
		ReceivedDataDto dto = null;

		String result = readUrlService.readFromFile(url);


		
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			dto = mapper.readValue(result, ReceivedDataDto.class);
		} catch (JsonProcessingException e) {
		
			throw new DataProcessingException("impossible de convertir les donneés vers json");
		}
		
		
		
		return dto;
	}

	@Override
	public List<SensorValueDto> getAllDataFromReceivedJson() {
		
	   
	   List<String> ips =	Arrays.asList(arrayOfStrings);
	   
	
	   List<ReceivedDataDto> recevied = new ArrayList<>();
	   
		List<SensorValueDto> dataValues = new ArrayList<>();

		
       if(ips!=null && ips.size()>0) {
    	   
    	  var adresses =  ips.stream().map(String::trim)
    	      .toList();

    	  if(adresses.size()>0) {
    		  
    		  adresses.stream().map(ip->renderReceivedData(ip , "/tpl/document.cgi?tpl/j/current.tpl&format=json"))
    		  .forEach(recevied::add);
    	  }
    	   
       }
       
       
      
       if(recevied.size()>0) {
    	   
    	 
    	      recevied.forEach(dto->{
    	    	  
    	    	List<SensorValueDto> dtos = getDataFromReceivedJson(dto);
    	    	
    	    	dtos.forEach(dataValues::add);
    	      });
  			                           
       }
       
       
       
 	   
	   
	   
		return dataValues;
	}



	@Override
	public ReceivedDataDto renderReceivedDataByIp(String ip) {
		
		 
		if(ip==null) ip="127.0.0.1";
		
		ip = ip.replaceAll("\\s+","");

		
		 InetAddressValidator validator
         = InetAddressValidator.getInstance();

         //validate IPv4
         
		var isValidIp =  validator.isValidInet4Address(ip);
		
		if(!isValidIp) {
			
			throw new MyCustomMalFormedExeption("mauvaise adresse ip");
		  
			
		}
		
		
     	var dto  = 	renderReceivedData(ip , "/tpl/document.cgi?tpl/j/current.tpl&format=json");
     	
		
		return dto;
	}

	
	public static String getDateFromTimeStamp(String timestam) {
		
		long timestamp;
		String date="";

		try {
			
			
			timestamp = Long.valueOf(timestam);

		}

		catch(NumberFormatException e) {
			
			return "";
		}
		
		Instant instant = Instant.now(); //can be LocalDateTime
		ZoneId systemZone = ZoneId.systemDefault(); // my timezone
		ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
		
		LocalDateTime triggerTime =
		        LocalDateTime.ofEpochSecond(timestamp, 0, currentOffsetForMyZone);
		LocalDate localDate
		= triggerTime.toLocalDate();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
	    date=localDate.format(dtf);
		return date;
		
		
	}
	
	
	public static String getTimeFromTimeStamp(String timestam) {
		
		long timestamp;
		String date="";

		try {
			
			
			timestamp = Long.valueOf(timestam);

		}

		catch(NumberFormatException e) {
			
			return "";
		}
		
		
		
		
		Instant instant = Instant.now(); //can be LocalDateTime
		ZoneId systemZone = ZoneId.systemDefault(); // my timezone
		ZoneOffset currentOffsetForMyZone = systemZone.getRules().getOffset(instant);
		
		LocalDateTime triggerTime =
		        LocalDateTime.ofEpochSecond(timestamp, 0, currentOffsetForMyZone);
		LocalTime localDate
		= triggerTime.toLocalTime();
		
		
		
		date = localDate.toString();
		
		return date;
		
		
		
	}



	
}
