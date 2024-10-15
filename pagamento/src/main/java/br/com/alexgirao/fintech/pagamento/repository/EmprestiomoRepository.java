package br.com.alexgirao.fintech.pagamento.repository;

import br.com.alexgirao.fintech.pagamento.modelo.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestiomoRepository extends JpaRepository<Emprestimo, Long> {
}
