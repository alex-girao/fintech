package br.com.alexgirao.fintech.emprestimo.repository;

import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

}
