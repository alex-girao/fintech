package br.com.alexgirao.fintech.pagamento.service;

import br.com.alexgirao.fintech.pagamento.enums.StatusPagamentoEnum;
import br.com.alexgirao.fintech.pagamento.exception.EntidadeNaoEncontradaException;
import br.com.alexgirao.fintech.pagamento.exception.RegraNegocioException;
import br.com.alexgirao.fintech.pagamento.modelo.Emprestimo;
import br.com.alexgirao.fintech.pagamento.repository.EmprestiomoRepository;
import org.springframework.stereotype.Service;

@Service
public class EmprestimoService {

    private final EmprestiomoRepository emprestiomoRepository;

    public EmprestimoService(EmprestiomoRepository emprestiomoRepository){
        this.emprestiomoRepository = emprestiomoRepository;
    }

    public Emprestimo pagar(Long id){
        Emprestimo emprestimo = emprestiomoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Empréstimo", id));

        if(emprestimo.getStatusPagamento() == StatusPagamentoEnum.PAGO){
            throw new RegraNegocioException("O Empréstimo " + id + " já está pago");
        }

        emprestimo.setStatusPagamento(StatusPagamentoEnum.PAGO);
        emprestiomoRepository.save(emprestimo);
        return emprestimo;
    }

}
