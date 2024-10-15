package br.com.alexgirao.fintech.emprestimo.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum TipoIdentificadorEnum {

    PESSOA_FISICA("PF", "Pessoa Física", 11,
        300.00, 10000.00),
    PESSOA_JURIDICA("PJ", "Pessoa Jurídica", 14,
        1000.00, 100000.00),
    ESTUDANTE_UNIVERSITARIO("EU", "Estudante Universitário", 8,
        100.00, 10000.00),
    APOSENTADO("AP", "Aposentado", 10,
        400.00, 25000.00);

    private final String sigla;
    private final String descricao;
    private final Integer tamanho;
    private final Double valorMinimoMensalParcelas;
    private final Double valorMaximoEmprestimo;

    private TipoIdentificadorEnum(String sigla, String descricao, Integer tamanho,
        Double valorMinimoMensalParcelas, Double valorMaximoEmprestimo) {
        this.sigla = sigla;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.valorMinimoMensalParcelas = valorMinimoMensalParcelas;
        this.valorMaximoEmprestimo = valorMaximoEmprestimo;
    }

    public static Optional<TipoIdentificadorEnum> buscarPeloTamanho(Integer tamanho){
        return Arrays.stream(TipoIdentificadorEnum.values()).filter(i -> i.tamanho.equals(tamanho)).findFirst();
    }

}
