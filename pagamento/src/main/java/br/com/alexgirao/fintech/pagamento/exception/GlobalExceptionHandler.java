package br.com.alexgirao.fintech.pagamento.exception;

import br.com.alexgirao.fintech.pagamento.controller.response.ErrorDetailsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

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

}
