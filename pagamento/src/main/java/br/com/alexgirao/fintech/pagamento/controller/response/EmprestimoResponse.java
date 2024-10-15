package br.com.alexgirao.fintech.pagamento.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmprestimoResponse {

    private Long id;
    private Integer numeroParcelas;
    private Double valorEmprestimo;
    private LocalDate dataCriacao;
    private String statusPagamento;

}
