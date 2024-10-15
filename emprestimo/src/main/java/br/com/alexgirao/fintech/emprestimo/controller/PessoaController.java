package br.com.alexgirao.fintech.emprestimo.controller;

import br.com.alexgirao.fintech.emprestimo.controller.request.PessoaRequest;
import br.com.alexgirao.fintech.emprestimo.controller.response.PessoaResponse;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import br.com.alexgirao.fintech.emprestimo.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<PessoaResponse> criar(@RequestBody @Valid PessoaRequest request) {
        Pessoa pessoa = pessoaService.criar(request.toModel());
        return new ResponseEntity<>(pessoa.toResponse(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long id, @RequestBody PessoaRequest request) {
        Pessoa pessoaAtualizada = pessoaService.atualizar(id, request.toModel());
        return new ResponseEntity<>(pessoaAtualizada, HttpStatus.OK);
    }

    @GetMapping
    public List<PessoaResponse> listar() {
        return pessoaService.listar().stream().map(Pessoa::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponse> consultar(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.consultarPorId(id);
        return pessoa.map(value -> ResponseEntity.ok(value.toResponse())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pessoaService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
