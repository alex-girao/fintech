package br.com.alexgirao.fintech.emprestimo.exception;

import java.io.Serial;

public class EntidadeNaoEncontradaException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

    public EntidadeNaoEncontradaException(String entidade, Long id) {
        super(String.format("%s não encontrada com o ID: %d", entidade, id));
    }

    public EntidadeNaoEncontradaException(String entidade, String campo, String valor) {
        super(String.format("%s não encontrada com %s: %s", entidade, campo, valor));
    }
}
