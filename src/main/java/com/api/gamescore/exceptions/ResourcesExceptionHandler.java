package com.api.gamescore.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ResourcesExceptionHandler {
	static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@ExceptionHandler({ResourceNotFoundException.class, Exception.class})
	public ResponseEntity<StandardError> exceptionsHandler(Exception e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError standardError = new StandardError(LocalDateTime.now().format(FORMATTER), status.value(),
				"Something went wrong!", e.getMessage(), request.getRequestURI());
		
		return ResponseEntity.status(status).body(standardError);
	}

}
