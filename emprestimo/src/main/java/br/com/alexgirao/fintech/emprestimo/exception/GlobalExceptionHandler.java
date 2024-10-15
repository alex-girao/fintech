package br.com.alexgirao.fintech.emprestimo.exception;

import br.com.alexgirao.fintech.emprestimo.controller.response.ErrorDetailsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request) {
        ErrorDetailsResponse errorDetails =
            ErrorDetailsResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .message(ex.getMessage())
                    .details(request.getDescription(false))
            .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<?> handleRegraNegocioException(RegraNegocioException ex, WebRequest request) {
        ErrorDetailsResponse errorDetails =
                ErrorDetailsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DocumentoIdentificacaoException.class)
    public ResponseEntity<?> handleDocumentoIdentificacaoException(DocumentoIdentificacaoException ex, WebRequest request) {
        ErrorDetailsResponse errorDetails =
                ErrorDetailsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .details(request.getDescription(false))
                        .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest request){

        List<FieldMessage> fields = new ArrayList<>();
        for(FieldError x : ex.getBindingResult().getFieldErrors()){
            fields.add(new FieldMessage(x.getField(), x.getDefaultMessage()));
        }

        ErrorDetailsResponse errorDetails =
                ErrorDetailsResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .message("Erro na validação dos campos")
                        .details("Validation error")
                        .errorFields(fields)
                        .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
