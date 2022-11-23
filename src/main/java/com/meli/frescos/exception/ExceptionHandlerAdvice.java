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

    /**
     * This method handles the DataIntegrityViolationException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the WarehouseNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the UsedPrimaryKeyConstraintException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the MethodArgumentNotValid
     *
     * @param ex      The original exception
     * @param headers The headers from exception
     * @param status  The status from exception
     * @param request The request from exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the CpfDuplicateException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the OrderProductIsInvalidException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the SectionByIdNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the SellerByIdNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the ProductNotPermittedInSectionException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the NotEnoughSpaceInSectionException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the NotEnoughSpaceInSectionException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the NullDueDateException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
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

    /**
     * This method handles the BatchStockFilterCategoryInvalidException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(BatchStockFilterCategoryInvalidException.class)
    public ResponseEntity<ExceptionDetails> handlerBatchStockFilterCategoryInvalidException(BatchStockFilterCategoryInvalidException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Filtro inválido")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }


    /**
     * This method handles the InvalidCommentException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(InvalidCommentException.class)
    public ResponseEntity<ExceptionDetails> handlerInvalidCommentException(InvalidCommentException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Comentário inválido")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method handles the CommentNotFoundException
     *
     * @param ex The original exception
     * @return A ResponseEntity to represent the HTTP error
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handlerCommentNotFoundException(CommentNotFoundException ex) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Comentário não existe")
                        .message(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}