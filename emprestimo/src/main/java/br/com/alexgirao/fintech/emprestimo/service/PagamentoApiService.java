package br.com.alexgirao.fintech.emprestimo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PagamentoApiService {

    private final RestTemplate restTemplate;

    @Value("${pagamento.api.url}")
    private String url;

    public PagamentoApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void pagar(Long id) {
        String url = "http://localhost:8081/pagamentos/" + id +"/pagar";
        restTemplate.put(url, null, String.class);
    }

}
