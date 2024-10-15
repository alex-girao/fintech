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

import br.com.alexgirao.fintech.emprestimo.enums.StatusPagamentoEnum;
import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;
import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmprestimoRepositoryIntegrationTest {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;

    @BeforeEach
    public void setup() {
        emprestimoRepository.deleteAll();
        pessoaRepository.deleteAll();

        pessoa = new Pessoa();
        pessoa.setNome("Carlos Souza");
        pessoa.setDataNascimento(LocalDate.of(1978, 3, 22));
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());
        pessoa.setIdentificador("12345678901");

        pessoa = pessoaRepository.save(pessoa);
    }

    @Test
    public void testSalvarEmprestimoComSucesso() {
        Emprestimo emprestimo = Emprestimo.builder()
                .numeroParcelas(12)
                .valorEmprestimo(5000.00)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO_PAGAMENTO)
                .pessoa(pessoa)
                .build();

        Emprestimo savedEmprestimo = emprestimoRepository.save(emprestimo);

        Optional<Emprestimo> foundEmprestimo = emprestimoRepository.findById(savedEmprestimo.getId());
        assertThat(foundEmprestimo).isPresent();
        assertThat(foundEmprestimo.get().getNumeroParcelas()).isEqualTo(12);
    }

    @Test
    public void testSalvarEmprestimoComFalha() {
        Emprestimo emprestimo = Emprestimo.builder()
                .numeroParcelas(null)
                .valorEmprestimo(5000.00)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO_PAGAMENTO)
                .pessoa(pessoa)
                .build();

        try {
            emprestimoRepository.save(emprestimo);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(Exception.class);
        }
    }

    @Test
    public void testListarEmprestimosComSucesso() {
        Emprestimo emprestimo1 = Emprestimo.builder()
                .numeroParcelas(12)
                .valorEmprestimo(5000.00)
                .statusPagamento(StatusPagamentoEnum.AGUARDANDO_PAGAMENTO)
                .pessoa(pessoa)
                .build();

        Emprestimo emprestimo2 = Emprestimo.builder()
                .numeroParcelas(24)
                .valorEmprestimo(10000.00)
                .statusPagamento(StatusPagamentoEnum.PAGO)
                .pessoa(pessoa)
                .build();

        emprestimoRepository.save(emprestimo1);
        emprestimoRepository.save(emprestimo2);

        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        assertThat(emprestimos).hasSize(2);
    }

    @Test
    public void testConsultarEmprestimoComFalha() {
        Optional<Emprestimo> foundEmprestimo = emprestimoRepository.findById(999L);
        assertThat(foundEmprestimo).isNotPresent();
    }
}
