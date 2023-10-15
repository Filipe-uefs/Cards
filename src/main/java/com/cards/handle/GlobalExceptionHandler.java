package com.cards.handle;

import com.cards.dto.error.ErrorResponseDTO;
import com.cards.exception.BusinessException;
import com.cards.exception.DataNotFoundException;
import com.cards.exception.InvalidInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        String messageError = "Fields errors";
        responseDTO.setMessage(messageError);
        responseDTO.setErrors(errors);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFoundException(DataNotFoundException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        String messageError = "It was not possible to perform the operation with the parameters sent";
        List<String> messageErrors = new ArrayList<>();;
        messageErrors.add(ex.getMessage());

        responseDTO.setErrors(messageErrors);
        responseDTO.setMessage(messageError);
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        String messageError = "Generic error, contact your system administrator";
        List<String> messageErrors = new ArrayList<>();;
        messageErrors.add(ex.getMessage());

        responseDTO.setErrors(messageErrors);
        responseDTO.setMessage(messageError);
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException ex) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        String messageError = "Inputs invalid";
        List<String> messageErrors = new ArrayList<>();;
        messageErrors.add(ex.getMessage());

        responseDTO.setErrors(messageErrors);
        responseDTO.setMessage(messageError);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

}
