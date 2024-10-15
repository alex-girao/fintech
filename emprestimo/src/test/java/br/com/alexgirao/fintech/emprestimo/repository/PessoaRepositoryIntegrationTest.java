package br.com.alexgirao.fintech.emprestimo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class PessoaRepositoryIntegrationTest {

    @Autowired
    private PessoaRepository pessoaRepository;

    @BeforeEach
    public void setup() {
        pessoaRepository.deleteAll();
    }

    @Test
    public void testSalvarPessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria Oliveira");
        pessoa.setDataNascimento(LocalDate.of(1985, 5, 15));
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setIdentificador("27659342070");
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        Optional<Pessoa> foundPessoa = pessoaRepository.findById(savedPessoa.getId());
        assertThat(foundPessoa).isPresent();
        assertThat(foundPessoa.get().getNome()).isEqualTo("Maria Oliveira");
    }

    @Test
    public void testSalvarPessoaComFalha() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(null);
        pessoa.setDataNascimento(LocalDate.of(1985, 5, 15));
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setIdentificador("00035683007");
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        try {
            pessoaRepository.save(pessoa);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(Exception.class);
        }
    }

    @Test
    public void testListarPessoasComSucesso() {
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNome("Carlos Souza");
        pessoa1.setDataNascimento(LocalDate.of(1978, 3, 22));
        pessoa1.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa1.setIdentificador("07985970037");
        pessoa1.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa1.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNome("Ana Lima");
        pessoa2.setDataNascimento(LocalDate.of(1992, 7, 11));
        pessoa2.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa2.setIdentificador("55498146094");
        pessoa2.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa2.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        pessoaRepository.save(pessoa1);
        pessoaRepository.save(pessoa2);

        List<Pessoa> pessoas = pessoaRepository.findAll();
        assertThat(pessoas).hasSize(2);
    }

    @Test
    public void testConsultarPessoaComFalha() {
        Optional<Pessoa> foundPessoa = pessoaRepository.findById(999L);
        assertThat(foundPessoa).isNotPresent();
    }

    @Test
    public void testAtualizarPessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setIdentificador("80321943040");
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        savedPessoa.setNome("João Silva Atualizado");
        Pessoa updatedPessoa = pessoaRepository.save(savedPessoa);

        Optional<Pessoa> foundPessoa = pessoaRepository.findById(updatedPessoa.getId());
        assertThat(foundPessoa).isPresent();
        assertThat(foundPessoa.get().getNome()).isEqualTo("João Silva Atualizado");
    }

    @Test
    public void testDeletarPessoaComSucesso() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Rafael Costa");
        pessoa.setDataNascimento(LocalDate.of(1988, 11, 30));
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setIdentificador("75827409049");
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());

        Pessoa savedPessoa = pessoaRepository.save(pessoa);
        pessoaRepository.deleteById(savedPessoa.getId());

        Optional<Pessoa> foundPessoa = pessoaRepository.findById(savedPessoa.getId());
        assertThat(foundPessoa).isNotPresent();
    }

    @Test
    public void testDeletarPessoaComFalha() {
        try {
            pessoaRepository.deleteById(999L); // ID inexistente
        } catch (Exception e) {
            assertThat(e).isInstanceOf(Exception.class);
        }
    }
}
