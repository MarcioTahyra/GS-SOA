# Wellbeing Monitoring - Global Solution 2025
Integrantes:
- Vinícius Almeida Bernardino de Souza - RM 97888
- Márcio Hitoshi Tahyra - RM552511
- Sabrina Flores - RM550781

## Aplicação Spring Boot para monitoramento de bem‑estar e saúde mental.
### Este repositório contém uma API REST com:
- Autenticação JWT (stateless) e gerenciamento de roles (ROLE_USER, ROLE_ADMIN)
- Entidades para usuário e entradas de bem‑estar
- Serviços com regras de negócio
- Endpoints administrativos para gestão de usuários
- Migrations Flyway para criação do schema
- Tratamento global de exceções e padronização de respostas

---

## Sumário

- Funcionalidades
- Tecnologias
- Estrutura do projeto
- Requisitos
- Configuração (MySQL, Flyway, JWT)
- Rodando a aplicação
- Endpoints principais
- Testes com Postman
- Admin inicial (desenvolvimento)
- Boas práticas / próximos passos

---

## Funcionalidades

- Registro de usuário (ROLE_USER)
- Login com JWT (Bearer token)
- CRUD para entradas de bem‑estar (criar e listar por usuário)
- Endpoints administrativos protegidos por ROLE_ADMIN:
  - Listar usuários
  - Atualizar roles de um usuário
  - Remover usuário
- Respostas padronizadas com ApiResponse<T>
- Tratamento global de exceções (RestControllerAdvice)
- Validações DTO/VO

---

## Tecnologias

- Java 17
- Spring Boot 3.x
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
- MySQL (connector)
- Flyway (migrations)
- JJWT (io.jsonwebtoken) para JWT
- Lombok (opcional)
- Maven

---

## Requisitos

- Java 17+
- Maven
- MySQL (crie o banco usado na configuração)

---

## Configuração

1. Banco de dados
   - Crie o banco no MySQL:
     CREATE DATABASE wellbeing_db;

2. application.properties (src/main/resources/application.properties)
   - Exemplos de propriedades que devem existir:

     spring.datasource.url=jdbc:mysql://localhost:3306/wellbeing_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
     spring.datasource.username= [Seu Username]
     spring.datasource.password= [Sua Senha]
     spring.jpa.hibernate.ddl-auto=none

     spring.flyway.enabled=true
     spring.flyway.locations=classpath:db/migration

     app.jwt.secret=${JWT_SECRET:ChangeThisSecretToAStrongValueAtLeast32BytesLong!}
     app.jwt.expiration-ms=${JWT_EXPIRATION_MS:3600000}

3. Admin inicial (apenas para dev)
   - O DataInitializer cria automaticamente um usuário admin caso ele não exista.
   - Credenciais padrão (apenas desenvolvimento):
     - Email: admin
     - Senha: admin123

---

## Rodando a aplicação

1. Build:
   mvn clean package

2. Rodar:
   mvn spring-boot:run

---

## Endpoints principais

Auth
- POST /auth/register
  - Body: { "email": "user@example.com", "password": "senha123" }
  - Retorna ApiResponse (usuário criado)

- POST /auth/login
  - Body: { "email": "user@example.com", "password": "senha123" }
  - Retorna token JWT em data.token (use Bearer token para chamadas autenticadas)

User
- GET /users/me
  - Retorna perfil do usuário autenticado

Wellbeing
- POST /wellbeing/entries
  - Body: WellbeingDTO
    { "mood":"GOOD", "sleepHours":7.0, "stressLevel":3, "notes":"..." }
  - Requer ROLE_USER ou ROLE_ADMIN

- GET /wellbeing/entries
  - Lista entradas do usuário autenticado
  - Requer ROLE_USER ou ROLE_ADMIN

Admin (protegido — ROLE_ADMIN)
- GET /admin/users
  - Lista todos os usuários

- PUT /admin/users/{id}/roles
  - Body: { "roles": ["ROLE_USER", "ROLE_ADMIN"] }
  - Atualiza roles do usuário

- DELETE /admin/users/{id}
  - Remove usuário
