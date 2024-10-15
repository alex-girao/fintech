package br.com.alexgirao.fintech.emprestimo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class FieldMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;


}