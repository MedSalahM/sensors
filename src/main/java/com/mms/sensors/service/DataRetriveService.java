package com.mms.sensors.service;

import java.util.List;

import com.mms.sensors.dto.ReceivedDataDto;
import com.mms.sensors.dto.SensorValueDto;

public interface DataRetriveService {

	
	public ReceivedDataDto renderReceivedData(String url);
	public ReceivedDataDto renderReceivedData(String ip , String url);
	public ReceivedDataDto renderReceivedDataFromFile(String url);
	public ReceivedDataDto renderReceivedDataByIp(String ip);

	List<SensorValueDto> getDataFromReceivedJson(ReceivedDataDto receivedDataDto);
	List<SensorValueDto> getAllDataFromReceivedJson();
	
	
	

}
