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

A documentação Swagger também requer autenticação. Acesse-a em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) e insira as credenciais acima.

- A API pode ser acessada em `http://localhost:8080`. 
- A documentação do Swagger pode ser visualizada em `http://localhost:8080/swagger-ui.html`.

## Configurar o Banco de Dados PostgreSQL

- Instale o PostgreSQL e crie um banco de dados vazio.
- Configure as credenciais do banco de dados no arquivo application.properties.
- Compilar e Executar o Projeto

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

- Configuração básica de segurança com autenticação HTTP Basic.
- Implementação dos serviços e repositórios necessários para revendas, usuários e oportunidades.
- Desenvolvimento de DTOs para comunicação entre camadas e validação de entrada.
- Implementação de mapeamentos entre DTOs e entidades para persistência e resposta.
