package br.com.alexgirao.fintech.emprestimo.exception;

import java.io.Serial;

public class DocumentoIdentificacaoException  extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DocumentoIdentificacaoException(String message) {
        super(message);
    }

}