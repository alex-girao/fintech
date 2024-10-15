package br.com.alexgirao.fintech.emprestimo.controller.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaResponse {

    private Long id;
    private String nome;
    private String identificador;
    private String tipoIdentificador;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private Double valorMinimoMensal;
    private Double valorMaximoEmprestimo;

}
