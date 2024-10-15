package br.com.alexgirao.fintech.emprestimo.controller.request;

import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoRequest {

    @NotNull(message = "O campo 'número de parcelas' é obrogatório")
    private Integer numeroParcelas;

    @NotNull(message = "O campo 'valor de empréstimo' é obrogatório")
    private Double valorEmprestimo;

    @NotBlank(message = "O campo 'identificador' é obrogatório")
    private String identificador;

    public Emprestimo toModel(){
        return Emprestimo.builder()
                .numeroParcelas(numeroParcelas)
                .valorEmprestimo(valorEmprestimo)
                .pessoa(Pessoa.builder().identificador(identificador).build())
                .build();
    }

}
