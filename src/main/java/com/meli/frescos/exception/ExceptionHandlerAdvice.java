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
     * This method handles the OneToOneMappingAlreadyDefinedException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(OneToOneMappingAlreadyDefinedException.class)
    public ResponseEntity<ExceptionDetails> handlerOneToOneMappingAlreadyDefinedException(OneToOneMappingAlreadyDefinedException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Entidade já possui mapeamento um-para-um já existente.")
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

    @ExceptionHandler(SectionByIdNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerSectionByIdNotFoundException(SectionByIdNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Setor não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the RepresentativeNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(RepresentativeNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerRepresentativeNotFoundException(RepresentativeNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Representante não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    /**
     * This method handles the RepresentativeWarehouseNotAssociatedException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(RepresentativeWarehouseNotAssociatedException.class)
    public ResponseEntity<ExceptionDetails> handlerRepresentativeWarehouseNotAssociatedException(RepresentativeWarehouseNotAssociatedException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Representante não associado com Armazém")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SellerByIdNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerSellerByIdNotFoundException(SellerByIdNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Vendedor não encontrado")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotPermittedInSectionException.class)
    public ResponseEntity<ExceptionDetails> handlerProductNotPermittedInSectionException(ProductNotPermittedInSectionException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Produto não permitido na seção!")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughSpaceInSectionException.class)
    public ResponseEntity<ExceptionDetails> handlerNotEnoughSpaceInSectionException(NotEnoughSpaceInSectionException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Espaço insuficiente na(s) seção(ões)!")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ExceptionDetails> handlerNotEnoughStockException(NotEnoughStockException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Estoque insuficiente para atender o pedido!")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullDueDateException.class)
    public ResponseEntity<ExceptionDetails> handlerNullDueDateException(NullDueDateException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Não consta data de validade!")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BuyerAlreadyHasWalletException.class)
    public ResponseEntity<ExceptionDetails> handleBuyerAlreadyHasWalletException(BuyerAlreadyHasWalletException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Já possue uma conta")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BuyerWalletNotExistException.class)
    public ResponseEntity<ExceptionDetails> handleBuyerWalletNotExistException(BuyerWalletNotExistException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Conta inexistente.")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}