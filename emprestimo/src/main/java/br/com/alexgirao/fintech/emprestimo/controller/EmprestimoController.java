package br.com.alexgirao.fintech.emprestimo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexgirao.fintech.emprestimo.controller.request.EmprestimoRequest;
import br.com.alexgirao.fintech.emprestimo.controller.response.EmprestimoResponse;
import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import br.com.alexgirao.fintech.emprestimo.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    @Operation(summary = "Cadastrar um novo empréstimo", description = "Cria um novo registro de empréstimo no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Empréstimo criado com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmprestimoResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                     content = @Content)
    })
    public ResponseEntity<EmprestimoResponse> criar(@RequestBody @Valid EmprestimoRequest request) {
        Emprestimo emprestimo = emprestimoService.criar(request.toModel());
        return new ResponseEntity<>(emprestimo.toResponse(), HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos os empréstimos", description = "Retorna uma lista de todos os empréstimos cadastrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmprestimoResponse.class)))
    })
    @GetMapping
    public List<EmprestimoResponse> listar() {
        return emprestimoService.listar().stream().map(Emprestimo::toResponse).collect(Collectors.toList());
    }

}
