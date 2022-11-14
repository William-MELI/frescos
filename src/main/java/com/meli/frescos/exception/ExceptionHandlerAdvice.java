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

    @ExceptionHandler(UsedPrimaryKeyConstraintException.class)
    public ResponseEntity<ExceptionDetails> handlerWarehouseNotFoundException(UsedPrimaryKeyConstraintException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Entidade com chave primária em uso")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
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

    /**
     * This method handles the BuyerNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(BuyerNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerBuyerNotFoundException(BuyerNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Comprador não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CpfDuplicateException.class)
    public ResponseEntity<ExceptionDetails> handlerCpfDuplicateException(CpfDuplicateException ex) {
            return new ResponseEntity<>(
                    ExceptionDetails.builder()
                            .title("CPF duplicado")
                            .message(ex.getMessage())
                            .timestamp(LocalDateTime.now())
                            .build(),
                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderProductIsInvalidException.class)
    public ResponseEntity<ExceptionDetails> handlerOrderProductIsInvalidException(OrderProductIsInvalidException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Pedido de compra inválido")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the BatchStockByIdNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(BatchStockByIdNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerBatchStockByIdNotFoundException(BatchStockByIdNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Estoque não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the ProductByIdNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(ProductByIdNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerProductByIdNotFoundException(ProductByIdNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Produto não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the BatchStockFilterOrderInvalidException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(BatchStockFilterOrderInvalidException.class)
    public ResponseEntity<ExceptionDetails> handlerBatchStockFilterOrderInvalidException(BatchStockFilterOrderInvalidException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Ordenação inválida")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}