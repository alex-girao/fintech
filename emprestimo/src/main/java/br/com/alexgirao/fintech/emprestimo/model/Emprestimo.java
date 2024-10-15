package br.com.alexgirao.fintech.emprestimo.model;

import java.time.LocalDate;

import br.com.alexgirao.fintech.emprestimo.controller.response.EmprestimoResponse;
import br.com.alexgirao.fintech.emprestimo.controller.response.PessoaResponse;
import br.com.alexgirao.fintech.emprestimo.enums.StatusPagamentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "emprestimo")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @SequenceGenerator(name = "emprestimo_id_seq",
            sequenceName = "emprestimo_id_seq", allocationSize = 1, schema = "public")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emprestimo_id_seq")
    private Long id;

    @Column(name = "numero_parcelas", length = 50, nullable = false)
    private Integer numeroParcelas;

    @Column(name = "valor_emprestimo", nullable = false)
    private Double valorEmprestimo;

    @Column(name = "data_criacao", nullable = false)
    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamentoEnum statusPagamento;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", nullable = false)
    private Pessoa pessoa;

    @PrePersist
    public void prePersist() {
        dataCriacao = LocalDate.now();
    }

    public EmprestimoResponse toResponse() {
        return EmprestimoResponse.builder()
                .id(id)
                .numeroParcelas(numeroParcelas)
                .valorEmprestimo(valorEmprestimo)
                .dataCriacao(dataCriacao)
                .statusPagamento(statusPagamento.getDescricao())
                .pessoa(PessoaResponse.builder()
                        .id(pessoa.getId())
                        .nome(pessoa.getNome())
                        .identificador(getPessoa().getIdentificador())
                    .build())
                .build();
    }
}
