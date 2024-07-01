# Mobiauto API

Esta API é parte do sistema Mobiauto, permitindo a gestão de revendas de veículos e oportunidades de negociação.

## Tecnologias Utilizadas

- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Lombok
- Swagger (SpringDoc OpenAPI 3)

## Práticas Adotadas

- RESTful API
- Utilização de DTOs (Data Transfer Objects)
- Validação de entrada com @Valid
- Manipulação de exceções personalizadas
- Documentação automática com Swagger

## Como Executar o Projeto

### Backend

1. **Clonar o repositório Git**

   ```bash
   git clone <URL_do_repositório>

## Configurar o Banco de Dados PostgreSQL

- Instale o PostgreSQL e crie um banco de dados vazio.
- Configure as credenciais do banco de dados no arquivo application.properties.
- Compilar e Executar o Projeto
   
2. **Configurar o Banco de Dados PostgreSQL**

    - Instale o PostgreSQL e crie um banco de dados vazio.
    - Configure as credenciais do banco de dados no arquivo `application.properties`:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/mobiauto
    spring.datasource.username=seu_usuario
    spring.datasource.password=sua_senha
    spring.jpa.hibernate.ddl-auto=update
    ```
## Autenticação

A aplicação utiliza autenticação básica HTTP. Para acessar a API, use as seguintes credenciais:

- **Usuário:** `user`
- **Senha:** `password`

Para interagir com a API, você pode usar o Postman ou qualquer outro cliente de API.

Configurar Autenticação no Postman
- Abra o Postman.
- Vá para a aba "Authorization".
- Selecione "Basic Auth".
- Insira o usuário (user) e a senha (password).
- Agora você pode fazer requisições para a API utilizando os endpoints listados abaixo.

A documentação Swagger também requer autenticação. Acesse-a em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) e insira as credenciais acima.

- A API pode ser acessada em `http://localhost:8080`. 
- A documentação do Swagger pode ser visualizada em `http://localhost:8080/swagger-ui.html`.

# Endpoints da API

## Revendas

### Use este endpoints para listar todas as Revendas

#### Endpoint
```bash
$GET http://localhost:8080/revendas/
```
### Criar uma Nova Revenda
```bash
$POST http://localhost:8080/revendas/
{
  "cnpj": "12345678000195",
  "nomeSocial": "Revenda XYZ"
}
```
### Atualizar uma Revenda Existente

```bash
 PUT /revendas/{id}
{
  "cnpj": "12345678000195",
  "nomeSocial": "Nova Revenda XYZ"
}
 ```
## Oportunidades
### Listar Todas as Oportunidades de uma Revenda
```bash
GET /revendas/{id}/oportunidades/
 ```
### Criar uma Nova Oportunidade
```bash
POST /revendas/{id}/oportunidades/
{
  "codigo": "OPP-001",
  "status": "novo",
  "motivoConclusao": null,
  "clienteNome": "João da Silva",
  "clienteEmail": "joao.silva@example.com",
  "clienteTelefone": "(11) 99999-9999",
  "veiculoMarca": "Chevrolet",
  "veiculoModelo": "Onix",
  "veiculoVersao": "LT",
  "veiculoAnoModelo": 2023
}
 ```
### Atualizar uma Oportunidade Existente
```bash
PUT /revendas/{id}/oportunidades/{idOportunidade}
{
  "codigo": "OPP-001",
  "status": "em_atendimento",
  "motivoConclusao": null,
  "clienteNome": "João da Silva",
  "clienteEmail": "joao.silva@example.com",
  "clienteTelefone": "(11) 99999-9999",
  "veiculoMarca": "Chevrolet",
  "veiculoModelo": "Onix",
  "veiculoVersao": "LT",
  "veiculoAnoModelo": 2023
}
```
### Excluir uma Oportunidade
```bash
 DELETE /revendas/{id}/oportunidades/{idOportunidade}
```

#### Os demais endpoints podem ser encontrados na documentação Acesse-a em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

# Requisitos Implementados

## Gestão de Revendas

- Cada revenda possui um código identificador único.
- O CNPJ da revenda é validado e único.
- A revenda tem um nome social.

## Permissões de Usuários

- A edição e manutenção de perfis só podem ser realizadas por administradores ou proprietários da loja.
- Administradores têm permissão para executar todas as ações em todas as revendas.
- Usuários só podem acessar lojas vinculadas ao seu cargo.

## Gestão de Oportunidades

- Cada loja pode ter uma lista de oportunidades para atendimento/negociação.
- Cada oportunidade possui um código identificador único.
- Cada oportunidade possui status que pode ser novo, em atendimento e concluído, com registro de motivo de conclusão.
- Dados do cliente (nome, e-mail, telefone) são registrados para cada oportunidade.
- Dados do veículo de interesse (marca, modelo, versão, ano modelo) são registrados para cada oportunidade.
- Implementação de serviço para manipulação de oportunidades, incluindo criação, atualização, listagem e exclusão.

## Atendimento e Edição de Oportunidades

- O atendimento de uma oportunidade é realizado por um usuário da revenda.
- Distribuição automática de oportunidades entre assistentes da loja com base na carga de trabalho.
- Proprietários e gerentes podem transferir oportunidades entre assistentes.
- Restrição para edição de oportunidades apenas pelo usuário associado, com exceções para gerentes e proprietários.

# Ações Concluídas

- Configuração de segurança com autenticação HTTP Basic.
- Implementação dos serviços e repositórios necessários para revendas, usuários e oportunidades.
- Desenvolvimento de DTOs para comunicação entre camadas e validação de entrada.
- Implementação de mapeamentos entre DTOs e entidades para persistência e resposta.

# Fluxo do Sistema

## Usuários:

- Usuários podem se registrar no sistema através do endpoint de criação de usuários.
- Administradores podem gerenciar usuários (criar, atualizar, deletar) e atribuir diferentes papéis aos usuários.

## Clientes:

- Clientes são registrados no sistema através do endpoint de criação de clientes.
- Usuários podem listar, atualizar e deletar clientes conforme necessário.

 ## Revendas:

- Revendas são registradas no sistema através do endpoint de criação de revendas.
- Usuários podem listar, atualizar e deletar revendas conforme necessário.

## Veículos:

- Veículos são registrados no sistema através do endpoint de criação de veículos.
- Usuários podem listar, atualizar e deletar veículos conforme necessário.

## Oportunidades:

- Oportunidades são registradas no sistema através do endpoint de criação de oportunidades.
- Cada oportunidade está associada a um cliente, um veículo e um usuário responsável.
- Usuários podem listar, atualizar e deletar oportunidades conforme necessário.
- O status das oportunidades pode ser atualizado para refletir o progresso (por exemplo, aberta, fechada).
