# Client Support

Sistema web full stack para gerenciamento de chamados de suporte técnico, com backend em Java (Spring Boot) e frontend simples em JavaScript e HTML.

## Tecnologias utilizadas

- Backend: Java 17, Spring Boot (Web, Data JPA, Security)  
- Banco de dados: H2 (em memória)  
- Frontend: JavaScript, HTML, CSS (sem frameworks)  
- Validação: Jakarta Validation (`@Valid`, `@NotNull`, etc)  
- Testes manuais realizados via interface web para validar funcionalidades principais 

## Funcionalidades principais

- Cadastro, listagem e edição de chamados via interface web com controle de acesso
- Usuários clientes podem criar chamados, além de visualizar seus próprios chamados e respostas 
- Usuários administradores podem visualizar todos os chamados, seus status e responder a eles 
- Autenticação e controle de acesso com Spring Security  
- Tratamento centralizado de exceções e validação no backend  
- Comunicação frontend-backend por meio de API REST consumida via fetch API  

## Como rodar o projeto

1. Clone o repositório  
2. No terminal, execute o backend:  
   - Linux/macOS: `./mvnw spring-boot:run`  
   - Windows: `mvnw.cmd spring-boot:run`  
3. Abra o arquivo HTML do frontend no navegador  
4. Use a interface para criar, listar, editar e responder chamados  

---

## Endpoints usados pelo frontend

### 1. Obter dados do usuário logado

- **GET** `/usuarios/me`  
- Requer header `Authorization` com token válido  
- Retorna os dados do usuário autenticado  

### 2. Criar novo usuário

- **POST** `/usuarios/post`  
- Requer header `Content-Type: application/json`  
- Recebe JSON com os campos `role`, `nome`, `email` e `senha`  
- Retorna o usuário criado com status 201  

### 3. Listar chamados do usuário logado

- **GET** `/chamados/meuschamados`  
- Requer header `Authorization`  
- Retorna a lista de chamados do usuário autenticado  

### 4. Criar novo chamado

- **POST** `/chamados`  
- Requer headers `Content-Type: application/json` e `Authorization`  
- Recebe JSON com os campos `titulo` e `descricao`  
- Retorna o chamado criado com status 201  

### 5. Listar todos os chamados (admin)

- **GET** `/chamados/todoschamados`  
- Requer header `Authorization`  
- Retorna todos os chamados cadastrados no sistema  

### 6. Responder um chamado

- **PUT** `/chamados/{id}/responde`  
- Requer headers `Content-Type: application/json` e `Authorization`  
- Recebe JSON com os campos `resposta` e `status`  
- Atualiza o chamado e retorna status 200  