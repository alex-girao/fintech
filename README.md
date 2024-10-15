# Fintech

### 💻 Solução
Sistema de empréstimos.
A Solução é composta por duas aplicações que possuem a mesma arquitetura
- [Empréstimo](https://github.com/alex-girao/fintech/tree/main/emprestimo)
Responsável por realizar o cadastro de clientes e a realização de empréstimos.

- [Pagamento](https://github.com/alex-girao/fintech/tree/main/pagamento)
Responsável pelo pagamento do empréstimo

## Principais Tecnologias
- Spring Boot 3
- Spring Web
- Spring Data
- Spring Test
- Swagger
- Flyway

# Setup da aplicação (local)
## 📋 Pré-requisitos
Antes de rodar a aplicações é preciso garantir que as seguintes dependências estejam corretamente instaladas:
- Java 17
- PostgreSQL 16.1
- Maven 3.1.0

## 🔧 Preparando ambiente
É necessário a criação da base de dados relacional no Postgres para o sistema
```
CREATE DATABASE "db_emprestimo";
```
Para os testes de integração também é necessario criar uma base de dados para os testes não interferirem na base de desenvolvimento.
```
CREATE DATABASE "db_emprestimo_test";
```
A aplicação Empréstimo deve ser a primeira a ser iniciada, pois possui as migrações de banco de dados.

## 🚀 Instalação das aplicações
Primeiramente, faça o clone do repositório:
```
https://github.com/alex-girao/fintech.git
```
### Empréstimo
Acesse a pasta:
```
cd emprestimo
```
É preciso compilar o código e baixar as dependências do projeto:
```
mvn clean package
```
Finalizado esse passo, vamos iniciar a aplicação:
```
mvn spring-boot:run
```
Pronto. A aplicação está disponível em http://localhost:8080
```
Tomcat started on port(s): 8080 (http)
Started AppConfig in xxxx seconds (JVM running for xxxx)
```
### Pagamento
Acesse a pasta:
```
cd pagamento
```
É preciso compilar o código e baixar as dependências do projeto:
```
mvn clean package
```
Finalizado esse passo, vamos iniciar a aplicação:
```
mvn spring-boot:run
```
Pronto. A aplicação está disponível em http://localhost:8081
```
Tomcat started on port(s): 8081 (http)
Started AppConfig in xxxx seconds (JVM running for xxxx)
```

# Endpoints
O projeto disponibiliza endpoints para os recursos: Pessoa e Empréstimo, onde utilizam o padrão Rest de comunicação, produzindo e consumindo dados no formato JSON.
A documentação Swagger para cada API está nas seguintes URL's locais:
- Empréstimo: http://localhost:8080/swagger-ui.html
- Pagamento: http://localhost:8081/swagger-ui.html

## Pessoas
#### Listar Todos
GET /pessoas
```bash
http://localhost:8080/pessoas
```
#### Listar por Id
GET /pessoas/:id
```bash
http://localhost:8080/pessoas/1
```
#### Criar
POST /pessoas
```bash
http://localhost:8080/pessoas
```
Request Body
```bash
{
    "nome": "Marieta Nogueira",
    "identificador": "89425712042",
    "dataNascimento": "11/03/2000"
}
```
#### Atualizar
PUT /pessoas/:id
```bash
http://localhost:8080/pessoas/1
```
Request Body
```bash
{
    "nome": "Marieta Nogueira da Silva",
    "identificador": "89425712042",
    "dataNascimento": "12/09/2001"
}
```
#### Remover por Id
DELETE /pessoas/:id
```bash
http://localhost:8080/pessoas/1
```
## Empréstimo
#### Listar Todos
GET /emprestimos
```bash
http://localhost:8080/emprestimos
```
#### Criar
POST /emprestimos
```bash
http://localhost:8080/emprestimos
```
Request Body
```bash
{
    "identificador": "49450525064",
    "numeroParcelas": "4",
    "valorEmprestimo": "42000"
}
```
#### Pagamento Manual
PUT /emprestimos:id/pagar
```bash
http://localhost:8081/emprestimos/1/pagar
```
```