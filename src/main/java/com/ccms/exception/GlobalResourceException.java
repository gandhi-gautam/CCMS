package com.ccms.exception;

import com.ccms.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalResourceException {

    @ExceptionHandler
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Response errorResponse = new Response();
        errorResponse.setMessage(resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleEmptyFieldException(EmptyFieldException emptyFieldException) {
        Response errorResponse = new Response();
        errorResponse.setMessage(emptyFieldException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleResourceAlreadyExistsException(ResourceAlreadyExistsException resourceAlreadyExistsException) {
        Response errorResponse = new Response();
        errorResponse.setMessage(resourceAlreadyExistsException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleCardNotActivatedException(CardNotActivatedException cardNotActivatedException) {
        Response errorResponse = new Response();
        errorResponse.setMessage(cardNotActivatedException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Response> handleLimitExceedingException(LimitExceedingException limitExceedingException) {
        Response errorResponse = new Response();
        errorResponse.setMessage(limitExceedingException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
