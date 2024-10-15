package br.com.alexgirao.fintech.emprestimo.controller.response;

//import br.com.alexgirao.fintech.emprestimo.exception.FieldMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailsResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;
    //private List<FieldMessage> errorFields;

}
