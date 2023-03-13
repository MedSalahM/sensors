package com.mms.sensors.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mms.sensors.dto.ReceivedDataDto;
import com.mms.sensors.dto.SensorDto;
import com.mms.sensors.dto.SensorValueDto;
import com.mms.sensors.dto.SondeDto;
import com.mms.sensors.error.DataProcessingException;
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
		
		
		var records=recordData.stream()
		.map(r->r.getRecord())
		.toList();
		
		        records.stream().forEach(record->{
		        String date=record.getDate();
	    	    String time = record.getTime();
	    	    String timestamp=record.getTimestamp();
	    	    
	    	    var entries=record.getEntry();
	    	    
	    	    entries.forEach(e->{
	    	    	
	    	    	dataValues.add(
	    	    			
	    	    			SensorValueDto.builder()
	    	    			.ip(receivedDataDto.getQuerx().getIp())
	    	    			.date(date)
	    	    			.time(time)
	    	    			.timestamp(timestamp)
	    	    			.sensorid(e.getSensorid())
	    	    			.value(e.getValue())
	    	    			.trend(e.getTrend())
	    	    			.build()
	    	    			
	    	    			);
	    	    });
	    	   
	    	   
		});
		

		
     /* List<SondeDto> sondes = receivedDataDto.getQuerx().getSensors()
    		                 .stream().map(e->e.getSensor())
    		                 .toList();
      
      
      
      
      dataValues.stream().forEach(e->{
    	  
    	  e.setName(getSensorNameFromSensors(sondes,e.getSensorid()));
      });*/
		
		
		
		return dataValues;
	}


	public static String getSensorNameFromSensors(List<SondeDto> sensorDtos , String id) {
		
		String sensorName="sensor_undefined";
		
		
	   Optional<SondeDto> optionalSen =	sensorDtos.stream().filter(e->e.getId().equals(id)).findAny();
	   
	   if (optionalSen.isPresent()) {
		
		   sensorName = optionalSen.get().getName();
	}
		
		return sensorName;
	
		
		
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

	
	
}
