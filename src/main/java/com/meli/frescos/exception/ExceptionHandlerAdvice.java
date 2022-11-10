package com.meli.frescos.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Dados inválidos")
                        .message("Um ou mais valores fornecidos ferem as regras de integridade")
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerWarehouseNotFoundException(WarehouseNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Warehouse não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        List<FieldError> erros = ex.getFieldErrors();

        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Parâmetros inválidos")
                        .message("Um ou mais campos com valor inválido")
                        .fields(erros.stream().map(FieldError::getField)
                                .collect(Collectors.joining(";")))
                        .fieldsMessages(erros.stream().map(FieldError::getDefaultMessage)
                                .collect(Collectors.joining(";")))
                        .timestamp(LocalDateTime.now())
                        .build(),
                status
        );

    }


}
