package br.com.alexgirao.fintech.pagamento.enums;

import lombok.Getter;

@Getter
public enum StatusPagamentoEnum {

    AGUARDANDO_PAGAMENTO(1,"Aguardando Pagamento"),
    PAGO(2, "Pago");

    private final Integer codigo;
    private final String descricao;

    private StatusPagamentoEnum(Integer codigo, String descricao){
        this.codigo = codigo;
        this.descricao = descricao;
    }

}

