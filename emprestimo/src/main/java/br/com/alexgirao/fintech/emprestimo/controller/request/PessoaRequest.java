package br.com.alexgirao.fintech.emprestimo.controller.request;

import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequest {

    @NotBlank(message = "O campo 'nome' é obrogatório")
    private String nome;

    @NotBlank(message = "O campo 'identificador' é obrogatório")
    private String identificador;

    @NotNull(message = "O campo 'data de nascimento' é obrogatório")
    @Past(message = "O campo 'data de nascimento' deve ser no passado")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    public Pessoa toModel() {
        return Pessoa.builder()
                .nome(nome)
                .identificador(identificador)
                .dataNascimento(dataNascimento)
                .build();
    }
}
