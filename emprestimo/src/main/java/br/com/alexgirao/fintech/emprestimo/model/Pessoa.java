package br.com.alexgirao.fintech.emprestimo.model;

import java.time.LocalDate;

import br.com.alexgirao.fintech.emprestimo.controller.response.PessoaResponse;
import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "pessoa")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

    @Id
    @SequenceGenerator(name = "pessoa_id_seq",
        sequenceName = "pessoa_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_id_seq")
    private Long id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "identificador", nullable = false, length = 50, unique = true)
    private String identificador;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identificador", nullable = false)
    private TipoIdentificadorEnum tipoIdentificador;

    @Column(name = "valor_min_mensal", nullable = false)
    private Double valorMinimoMensal;

    @Column(name = "valor_max_emprestimo", nullable = false)
    private Double valorMaximoEmprestimo;

    public PessoaResponse toResponse() {
        return PessoaResponse.builder()
                .id(id)
                .nome(nome)
                .identificador(identificador)
                .dataNascimento(dataNascimento)
                .tipoIdentificador(tipoIdentificador.toString())
                .valorMinimoMensal(valorMinimoMensal)
                .valorMaximoEmprestimo(valorMaximoEmprestimo)
                .build();
    }
}
