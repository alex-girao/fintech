package br.com.alexgirao.fintech.emprestimo.service;

import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;
import br.com.alexgirao.fintech.emprestimo.exception.DocumentoIdentificacaoException;
import br.com.alexgirao.fintech.emprestimo.exception.EntidadeNaoEncontradaException;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import br.com.alexgirao.fintech.emprestimo.repository.PessoaRepository;
import br.com.alexgirao.fintech.emprestimo.util.ValidadorDocumentoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa criar(Pessoa pessoa) {
        definirIdentificacao(pessoa);
        return pessoaRepository.save(pessoa);
    }

    private void definirIdentificacao(Pessoa pessoa) {
        TipoIdentificadorEnum tipoIdentificador =
            TipoIdentificadorEnum.buscarPeloTamanho(pessoa.getIdentificador().length()).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Identificador inválido")
            );

        if(!ValidadorDocumentoUtil.isDocumentoValido(pessoa.getIdentificador(), tipoIdentificador)){
            throw new DocumentoIdentificacaoException("Identificador inválido");
        }

        pessoa.setTipoIdentificador(tipoIdentificador);
        pessoa.setValorMaximoEmprestimo(tipoIdentificador.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(tipoIdentificador.getValorMinimoMensalParcelas());
    }

    public Pessoa atualizar(Long id, Pessoa pessoa) {
        pessoaRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa", id));
        definirIdentificacao(pessoa);
        pessoa.setId(id);
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> consultarPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public void remover(Long id) {
        pessoaRepository.findById(id)
            .orElseThrow(() -> new EntidadeNaoEncontradaException("Pessoa", id));

        pessoaRepository.deleteById(id);
    }

    public Optional<Pessoa> consultarPorIdentificador(String identificador) {
        return pessoaRepository.findByIdentificador(identificador);
    }

}
