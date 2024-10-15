package br.com.alexgirao.fintech.emprestimo.repository;

import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByIdentificador(String identificador);

}
