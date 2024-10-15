package br.com.alexgirao.fintech.emprestimo.controller;

import br.com.alexgirao.fintech.emprestimo.controller.request.EmprestimoRequest;
import br.com.alexgirao.fintech.emprestimo.controller.response.EmprestimoResponse;
import br.com.alexgirao.fintech.emprestimo.model.Emprestimo;
import br.com.alexgirao.fintech.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoResponse> criar(@RequestBody @Valid EmprestimoRequest request) {
        Emprestimo emprestimo = emprestimoService.criar(request.toModel());
        return new ResponseEntity<>(emprestimo.toResponse(), HttpStatus.CREATED);
    }

    @GetMapping
    public List<EmprestimoResponse> listar() {
        return emprestimoService.listar().stream().map(Emprestimo::toResponse).collect(Collectors.toList());
    }

}
