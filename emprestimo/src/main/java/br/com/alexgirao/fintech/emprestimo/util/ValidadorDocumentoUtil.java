package br.com.alexgirao.fintech.emprestimo.util;

import br.com.alexgirao.fintech.emprestimo.enums.TipoIdentificadorEnum;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ValidadorDocumentoUtil {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    public static boolean isDocumentoValido(String documento, TipoIdentificadorEnum tipoIdentificador){
        if(TipoIdentificadorEnum.PESSOA_FISICA.equals(tipoIdentificador) && validarCPF(documento)){
            return true;
        }else if(TipoIdentificadorEnum.PESSOA_JURIDICA.equals(tipoIdentificador) && validarCNPJ(documento)){
            return true;
        }else if(TipoIdentificadorEnum.ESTUDANTE_UNIVERSITARIO.equals(tipoIdentificador) && validarEstudanteUniversitario(documento)){
            return true;
        }else return TipoIdentificadorEnum.APOSENTADO.equals(tipoIdentificador) && validarAposentado(documento);
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Validação básica de CPF
        int[] pesosCPF = {10, 9, 8, 7, 6, 5, 4, 3, 2, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        return validarDocumento(cpf, pesosCPF);
    }

    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            return false;
        }

        // Validação básica de CNPJ
        int[] pesosCNPJ = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2, 6, 5, 4, 3, 2};
        return validarDocumento(cnpj, pesosCNPJ);
    }

    private static boolean validarDocumento(String documento, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < documento.length() - 2; i++) {
            soma += Character.getNumericValue(documento.charAt(i)) * pesos[i];
        }

        int digito1 = calcularDigito(soma);
        soma = 0;
        for (int i = 0; i < documento.length() - 1; i++) {
            soma += Character.getNumericValue(documento.charAt(i)) * pesos[i + 1];
        }

        int digito2 = calcularDigito(soma);
        return documento.endsWith(String.valueOf(digito1) + String.valueOf(digito2));
    }

    private static int calcularDigito(int soma) {
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public static boolean validarEstudanteUniversitario(String identificador) {
        if (identificador == null || identificador.length() != 8) {
            return false;
        }

        int primeiroDigito = Character.getNumericValue(identificador.charAt(0));
        int ultimoDigito = Character.getNumericValue(identificador.charAt(7));

        return (primeiroDigito + ultimoDigito) == 9;
    }

    public static boolean validarAposentado(String identificador) {
        if (identificador == null || identificador.length() != 10) {
            return false;
        }

        char ultimoDigito = identificador.charAt(9);
        Set<Character> outrosDigitos = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            outrosDigitos.add(identificador.charAt(i));
        }

        return !outrosDigitos.contains(ultimoDigito);
    }

}
