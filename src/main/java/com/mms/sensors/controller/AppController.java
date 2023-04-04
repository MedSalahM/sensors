package com.mms.sensors.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mms.sensors.dto.EntryDto;
import com.mms.sensors.dto.QuerxDto;
import com.mms.sensors.dto.ReceivedDataDto;
import com.mms.sensors.dto.RecordDetailsDto;
import com.mms.sensors.dto.RecordDto;
import com.mms.sensors.dto.SensorDto;
import com.mms.sensors.dto.SensorValueDto;
import com.mms.sensors.dto.SondeDto;
import com.mms.sensors.service.DataRetriveService;
import com.mms.sensors.service.ReadUrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AppController {
	
	private final ReadUrlService readUrlService;
    private final DataRetriveService dataRetriveService;

    
    @GetMapping("")
	public String home() {
	
		
	
	
	return "Connecté au api sondes";
	
	
	}
    
    
	
	@GetMapping("/test")
	public ReceivedDataDto sondes(){
		
		
		
		List<SensorDto> list=  new ArrayList<>();
		
		var s1 = SondeDto.builder().id("s1").name("Temp").unit("DEG").status("0").uplim("160000.0").lolim("15.0").build();
		var s2 = SondeDto.builder().id("s2").name("HUM").unit("RH").status("0").uplim("25").lolim("10").build();

		list.add(SensorDto.builder().sensor(s1).build());
		list.add(SensorDto.builder().sensor(s2).build());
		
		
		
		
		EntryDto entry1= EntryDto.builder().sensorid("sensor_1").name("value").value(15.6).trend(0).build();
		EntryDto entry2= EntryDto.builder().sensorid("sensor_2").name("value").value(33.0).trend(0).build();
		
		RecordDetailsDto rdd = RecordDetailsDto.builder().timestamp("1461675290")
				               .date("26.04.2016")
				               .time("13:54:50")
				               .entry(List.of(entry1, entry2))
				               .build();
		
		RecordDto recordDto = RecordDto.builder().record(rdd).build();
		
		
		
		
		QuerxDto querx = QuerxDto.builder()
				                 .version(1.0)
				                 .hostname("")
				                 .ip("10.12.0.1")
				                 .date_gmt("eee")
				                 .date_local("qdq")
				                 .location("sdd")
				                  .sensors(list)
				                  .data(List.of(recordDto))
				                 .build();
		
		
		
		
		
		
		
		return ReceivedDataDto.builder().querx(querx).build();
				
		
		
		
		
		
	}
	
	
   	
	
	@GetMapping("/data")
	public ResponseEntity<String> data() {
	
		
	var r =readUrlService.readFromUrl("/tpl/document.cgi?tpl/j/current.tpl&format=json");
	
	return ResponseEntity.ok(r);
	
	
	}
	
	@GetMapping("/value")
	public ResponseEntity<List<SensorValueDto>> values() {
	
		
		var receviedData =  dataRetriveService.renderReceivedData("/tpl/document.cgi?tpl/j/current.tpl&format=json");
	 
		var r =  dataRetriveService.getDataFromReceivedJson(receviedData);

	    return ResponseEntity.ok(r);
	
	
	}
	
	@GetMapping("/valuef")
	public ResponseEntity<List<SensorValueDto>> valuesf() {
		
		
	
		
		var receviedData =  dataRetriveService.renderReceivedDataFromFile("c:\\App\\censor.cgi");
	 
		var r =  dataRetriveService.getDataFromReceivedJson(receviedData);
		

	    return ResponseEntity.ok(r);
	
	
	}
	
	
	@GetMapping("/valuef2")
	public ResponseEntity<List<SensorValueDto>> valuesf2() {
		
		
	
		
		var receviedData =  dataRetriveService.renderReceivedDataFromFile("c:\\App\\censor2.cgi");
	 
		var r =  dataRetriveService.getDataFromReceivedJson(receviedData);
		

	    return ResponseEntity.ok(r);
	
	
	}
	
	@GetMapping("/valuef3")
	public ResponseEntity<List<SensorValueDto>> valuesf3() {
		
		
	
		
		var receviedData =  dataRetriveService.renderReceivedDataFromFile("c:\\App\\censor3.cgi");
	 
		var r =  dataRetriveService.getDataFromReceivedJson(receviedData);
		

	    return ResponseEntity.ok(r);
	
	
	}
	
	
	@GetMapping("/value/all")
	public ResponseEntity<List<SensorValueDto>> all() {
	
		
	
	 
		var r =  dataRetriveService.getAllDataFromReceivedJson();
		
		
		

	    return ResponseEntity.ok(r);
	
	
	}
	
	
	
	 @GetMapping("/sensor")
	 public ResponseEntity<List<SensorValueDto>> valuesByIp(@RequestParam String ip) {
	
		 
		
		var receviedData =  dataRetriveService.renderReceivedDataByIp(ip);
	 
		var r =  dataRetriveService.getDataFromReceivedJson(receviedData);

	    return ResponseEntity.ok(r);
	
	
	 }
	
	 
	
		

	 
	
}


