package br.com.alexgirao.fintech.emprestimo.controller;

import br.com.alexgirao.fintech.emprestimo.controller.request.PessoaRequest;
import br.com.alexgirao.fintech.emprestimo.controller.response.PessoaResponse;
import br.com.alexgirao.fintech.emprestimo.model.Pessoa;
import br.com.alexgirao.fintech.emprestimo.service.PessoaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "Pessoa", description = "Gerenciamento de Pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar uma nova pessoa", description = "Cria um novo registro de pessoa no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pessoa criada com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                     content = @Content)
    })
    public ResponseEntity<PessoaResponse> criar(@RequestBody @Valid PessoaRequest request) {
        Pessoa pessoa = pessoaService.criar(request.toModel());
        return new ResponseEntity<>(pessoa.toResponse(), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todas as pessoas", description = "Retorna uma lista de todas as pessoas cadastradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pessoas retornada com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaResponse.class)))
    })
    @GetMapping
    public List<PessoaResponse> listar() {
        return pessoaService.listar().stream().map(Pessoa::toResponse).collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Consultar uma pessoa", description = "Retorna os dados de uma pessoa com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa encontrada",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                     content = @Content)
    })
    public ResponseEntity<PessoaResponse> consultar(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaService.consultarPorId(id);
        return pessoa.map(value -> ResponseEntity.ok(value.toResponse())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma pessoa", description = "Atualiza os dados de uma pessoa existente com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pessoa atualizada com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = PessoaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                     content = @Content)
    })
    public ResponseEntity<PessoaResponse> atualizar(@PathVariable Long id, @RequestBody PessoaRequest request) {
        Pessoa pessoaAtualizada = pessoaService.atualizar(id, request.toModel());
        return new ResponseEntity<>(pessoaAtualizada.toResponse(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover uma pessoa", description = "Remove uma pessoa do sistema com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pessoa removida com sucesso",
                     content = @Content),
        @ApiResponse(responseCode = "404", description = "Pessoa não encontrada",
                     content = @Content)
    })
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        pessoaService.remover(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
