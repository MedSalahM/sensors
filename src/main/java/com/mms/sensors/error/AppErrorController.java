package com.mms.sensors.error;

import java.net.UnknownHostException;

import javax.naming.NamingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppErrorController {

	
	
	@ExceptionHandler(MyCustomMalFormedExeption.class)
	public ResponseEntity<ProblemDetail> handlleNamingException(MyCustomMalFormedExeption ex) {
		
		 var a =	HttpStatus.INTERNAL_SERVER_ERROR;
			
			ProblemDetail apiError = ProblemDetail.forStatusAndDetail(a, ex.getMessage()) ;
			
			return new ResponseEntity<ProblemDetail>(apiError ,a );

		
		
	}
	
	@ExceptionHandler(MyCustomIOExeption.class)
	public ResponseEntity<ProblemDetail> handlUnknownhost(MyCustomIOExeption ex) {
		
		 var a =	HttpStatus.INTERNAL_SERVER_ERROR;
			
			ProblemDetail apiError = ProblemDetail.forStatusAndDetail(a, ex.getMessage()) ;
			
			return new ResponseEntity<ProblemDetail>(apiError ,a );

		
		
	}
	
	@ExceptionHandler(DataProcessingException.class)
	public ResponseEntity<ProblemDetail> handlDataProcessing(DataProcessingException ex) {
		
		 var a =	HttpStatus.INTERNAL_SERVER_ERROR;
			
			ProblemDetail apiError = ProblemDetail.forStatusAndDetail(a, ex.getMessage()) ;
			
			return new ResponseEntity<ProblemDetail>(apiError ,a );

		
		
	}
	
}
