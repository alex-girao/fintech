package br.com.alexgirao.fintech.pagamento.controller;

import br.com.alexgirao.fintech.pagamento.controller.response.EmprestimoResponse;
import br.com.alexgirao.fintech.pagamento.modelo.Emprestimo;
import br.com.alexgirao.fintech.pagamento.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@Tag(name = "Pagamento", description = "Gerenciamento de Pagamentos")
public class PagamentoController {

    private final EmprestimoService emprestimoService;

    public PagamentoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }

    @PutMapping("/{id}/pagar")
    @Operation(summary = "Atualiza o Status para PAGO", description = "Atualiza os dados de um empréstimo existente para PAGO com base no ID fornecido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmprestimoResponse.class))),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado",
                     content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                     content = @Content)
    })
    public ResponseEntity<EmprestimoResponse> pagar(@PathVariable Long id) {
        Emprestimo emprestimoPago = emprestimoService.pagar(id);
        return new ResponseEntity<>(emprestimoPago.toResponse(), HttpStatus.OK);
    }

}
