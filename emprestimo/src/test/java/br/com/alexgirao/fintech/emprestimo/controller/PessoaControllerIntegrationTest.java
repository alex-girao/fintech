package br.com.alexgirao.fintech.emprestimo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alexgirao.fintech.emprestimo.controller.request.PessoaRequest;
import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import br.com.alexgirao.fintech.emprestimo.repository.EmprestimoRepository;
import br.com.alexgirao.fintech.emprestimo.repository.PessoaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        emprestimoRepository.deleteAll();
        pessoaRepository.deleteAll();
    }

    @Test
    public void testCriarPessoaComSucesso() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest();
        pessoaRequest.setNome("João Silva");
        pessoaRequest.setIdentificador("36878297079");
        pessoaRequest.setDataNascimento(LocalDate.of(1990, 1, 1));
        

        mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is("João Silva")));
    }

    @Test
    public void testCriarPessoaComFalha() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest();
        pessoaRequest.setNome("");
        pessoaRequest.setIdentificador("87864284035");
        pessoaRequest.setDataNascimento(LocalDate.of(1990, 1, 1));

        mockMvc.perform(post("/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testListarPessoasComSucesso() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Maria Oliveira");
        pessoa.setDataNascimento(LocalDate.of(1985, 5, 15));
        pessoa.setIdentificador("62734776049");
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());
        pessoaRepository.save(pessoa);

        mockMvc.perform(get("/pessoas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Maria Oliveira")));
    }

    @Test
    public void testConsultarPessoaComSucesso() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Carlos Souza");
        pessoa.setDataNascimento(LocalDate.of(1978, 3, 22));
        pessoa.setIdentificador("57310097084");
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());
        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        mockMvc.perform(get("/pessoas/{id}", savedPessoa.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Carlos Souza")));
    }

    @Test
    public void testConsultarPessoaComFalha() throws Exception {
        mockMvc.perform(get("/pessoas/{id}", 999L) // ID inexistente
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAtualizarPessoaComFalha() throws Exception {
        PessoaRequest pessoaRequest = new PessoaRequest();
        pessoaRequest.setNome("Rafael Costa");
        pessoaRequest.setIdentificador("80062900080");
        pessoaRequest.setDataNascimento(LocalDate.of(1988, 11, 30));

        mockMvc.perform(put("/pessoas/{id}", 999L) // ID inexistente
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pessoaRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoverPessoaComSucesso() throws Exception {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Rafael Costa");
        pessoa.setIdentificador("41028769016");
        pessoa.setTipoIdentificador(TipoIdentificadorEnum.PESSOA_FISICA);
        pessoa.setValorMaximoEmprestimo(TipoIdentificadorEnum.PESSOA_FISICA.getValorMaximoEmprestimo());
        pessoa.setValorMinimoMensal(TipoIdentificadorEnum.PESSOA_FISICA.getValorMinimoMensalParcelas());
        
        pessoa.setDataNascimento(LocalDate.of(1988, 11, 30));
        Pessoa savedPessoa = pessoaRepository.save(pessoa);

        mockMvc.perform(delete("/pessoas/{id}", savedPessoa.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pessoas/{id}", savedPessoa.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemoverPessoaComFalha() throws Exception {
        mockMvc.perform(delete("/pessoas/{id}", 999L) // ID inexistente
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
