package br.com.alexgirao.fintech.emprestimo.service;

import br.com.alexgirao.fintech.emprestimo.enums.StatusPagamentoEnum;
import br.com.alexgirao.fintech.emprestimo.exception.EntidadeNaoEncontradaException;
import br.com.alexgirao.fintech.emprestimo.exception.RegraNegocioException;
import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import br.com.alexgirao.fintech.emprestimo.repository.EmprestimoRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmprestimoService {

    private static final Logger logger = LoggerFactory.getLogger(EmprestimoService.class);

    private final EmprestimoRepository emprestimoRepository;

    private final PessoaService pessoaService;

    private final PagamentoApiService pagamentoService;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, PessoaService pessoaService,
        PagamentoApiService pagamentoService){
        this.emprestimoRepository = emprestimoRepository;
        this.pessoaService = pessoaService;
        this.pagamentoService = pagamentoService;
    }

    public Emprestimo criar(Emprestimo emprestimo){

        Pessoa pessoa = pessoaService.consultarPorIdentificador(emprestimo.getPessoa().getIdentificador())
            .orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                    "Pessoa com identificador "+emprestimo.getPessoa().getIdentificador()+" não cadastrado."
                )
            );

        validarValores(emprestimo, pessoa);

        criarEmprestimo(emprestimo, pessoa);

        efetivarPagamento(emprestimo);

        return emprestimo;
    }

    @Transactional
    private void criarEmprestimo(Emprestimo emprestimo, Pessoa pessoa){
        emprestimo.setPessoa(pessoa);
        emprestimo.setStatusPagamento(StatusPagamentoEnum.AGUARDANDO_PAGAMENTO);
        emprestimoRepository.save(emprestimo);
        logger.info("Empréstimo {} criado com sucesso", emprestimo.getId());
    }

    private void efetivarPagamento(Emprestimo emprestimo) {
        try{
            pagamentoService.pagar(emprestimo.getId());
            logger.info("Pagamento do empréstimo {} realizado com sucesso", emprestimo.getId());
        }catch (Exception e){
            logger.error("Erro ao realizar pagamento do empréstimo {}: {}", emprestimo.getId(), e.getMessage(), e);
        }
    }

    private void validarValores(Emprestimo emprestimo, Pessoa pessoa) {
        if(emprestimo.getValorEmprestimo() > pessoa.getValorMaximoEmprestimo()){
            throw new RegraNegocioException("O valor solicitado é superior ao valor permitido de " + pessoa.getValorMaximoEmprestimo());
        }else if((emprestimo.getValorEmprestimo() / emprestimo.getNumeroParcelas()) < pessoa.getValorMinimoMensal()){
            throw new RegraNegocioException("O valor por parcela é inferior ao mínimo de " + pessoa.getValorMinimoMensal());
        }else if(emprestimo.getNumeroParcelas() > 24){
            throw new RegraNegocioException("A quantidade de parcelas não deve ultrapassar 24");
        }
    }

    public List<Emprestimo> listar() {
        return emprestimoRepository.findAll();
    }
}
