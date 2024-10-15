package br.com.alexgirao.fintech.pagamento.controller;

import br.com.alexgirao.fintech.pagamento.controller.response.EmprestimoResponse;
import br.com.alexgirao.fintech.pagamento.modelo.Emprestimo;
import br.com.alexgirao.fintech.pagamento.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final EmprestimoService emprestimoService;

    public PagamentoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<EmprestimoResponse> pagar(@PathVariable Long id) {
        Emprestimo emprestimoPago = emprestimoService.pagar(id);
        return new ResponseEntity<>(emprestimoPago.toResponse(), HttpStatus.OK);
    }

}
