# 🎵 Spotifake — API REST com Quarkus e PostgreSQL

> Trabalho M3 — Desenvolvimento de API REST  
> Curso de Ciência da Computação — UNIVALI  

---

## 📋 Sobre o Projeto

O **Spotifake** é uma API REST que simula as funcionalidades básicas de uma plataforma de streaming de músicas, inspirada no Spotify. Desenvolvida com o framework **Quarkus** e persistência em **PostgreSQL**, a aplicação implementa operações CRUD completas para as quatro entidades do sistema.

Este projeto dá continuidade ao Trabalho M1 (modelagem UML) e M2 (console Java com JDBC), agora evoluindo para uma API REST com arquitetura em camadas.

---

## 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas, com separação clara de responsabilidades:

```
src/main/java/br/com/spotifake/
├── model/          → Entidades JPA mapeadas para o banco de dados
├── dto/            → Objetos de transferência de dados (Request e Response)
├── repository/     → Acesso ao banco via EntityManager (JPA)
├── service/        → Regras de negócio e conversão DTO ↔ entidade
└── resource/       → Endpoints REST (JAX-RS / controllers)
```

### Fluxo de uma requisição

```
Cliente (Postman)
      │
      ▼
  Resource         ← recebe a requisição HTTP, valida com @Valid
      │
      ▼
  Service          ← aplica regras de negócio, converte DTO → entidade
      │
      ▼
  Repository       ← executa queries via EntityManager
      │
      ▼
  PostgreSQL       ← persiste os dados
      │
      ▼
  Service          ← converte entidade → DTO de resposta
      │
      ▼
  Resource         ← devolve Response com status HTTP correto
```

---

## 🗂️ Entidades e Relacionamentos

```
Artista  ──< Musica
                │
               >─< Playlist >── Usuario
```

| Entidade | Relacionamento |
|----------|----------------|
| `Artista` | Um artista possui muitas músicas (1:N) |
| `Musica` | Pertence a um artista; pode estar em várias playlists (N:N) |
| `Usuario` | Possui muitas playlists (1:N) |
| `Playlist` | Pertence a um usuário; contém muitas músicas (N:N) |

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 17+ | Linguagem principal |
| Quarkus | 3.9.4 | Framework REST |
| JAX-RS | — | Definição dos endpoints HTTP |
| JPA / Hibernate | — | ORM — mapeamento objeto-relacional |
| CDI | — | Injeção de dependência (`@Inject`) |
| Bean Validation | — | Validação de campos (`@NotBlank`, `@Email`...) |
| PostgreSQL | 14+ | Banco de dados relacional |
| Maven | 3.8+ | Gerenciador de dependências e build |

---

## ⚙️ Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior
- PostgreSQL rodando localmente

### 1. Clone o repositório

```bash
git clone https://github.com/lucasbattistipiva/spotifake-quarkus.git
cd spotifake-quarkus
```

### 2. Crie o banco de dados

```bash
psql -U postgres -c "CREATE DATABASE spotifake;"
psql -U postgres -d spotifake -f schema.sql
```

Ou abra o `schema.sql` no pgAdmin e execute manualmente.

### 3. Configure as credenciais

Edite `src/main/resources/application.properties`:

```properties
quarkus.datasource.username=postgres
quarkus.datasource.password=sua_senha
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/spotifake
```

### 4. Compile e execute

```bash
mvn clean package -DskipTests
java -jar target/quarkus-app/quarkus-run.jar
```

A API estará disponível em: **`http://localhost:8080`**

---

## 📌 Endpoints da API

### 🎤 Artistas — `/artistas`

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/artistas` | Cria um novo artista | 201 |
| `GET` | `/artistas` | Lista todos os artistas | 200 |
| `GET` | `/artistas/{id}` | Busca artista por ID | 200 / 404 |
| `PUT` | `/artistas/{id}` | Atualiza um artista | 200 / 404 |
| `DELETE` | `/artistas/{id}` | Remove um artista | 204 / 404 |

**POST /artistas — Requisição:**
```json
{
  "nome": "Coldplay",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1996
}
```
**Resposta `201 Created`:**
```json
{
  "id": 4,
  "nome": "Coldplay",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1996,
  "totalMusicas": 0
}
```

---

### 🎵 Músicas — `/musicas`

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/musicas` | Cria uma nova música | 201 |
| `GET` | `/musicas` | Lista todas as músicas | 200 |
| `GET` | `/musicas/{id}` | Busca música por ID | 200 / 404 |
| `GET` | `/musicas/artista/{artistaId}` | Lista músicas de um artista | 200 / 404 |
| `PUT` | `/musicas/{id}` | Atualiza uma música | 200 / 404 |
| `DELETE` | `/musicas/{id}` | Remove uma música | 204 / 404 |

**POST /musicas — Requisição:**
```json
{
  "titulo": "Yellow",
  "duracaoSegundos": 269,
  "genero": "Pop Rock",
  "anoLancamento": 2000,
  "artistaId": 1
}
```
**Resposta `201 Created`:**
```json
{
  "id": 7,
  "titulo": "Yellow",
  "duracaoSegundos": 269,
  "duracaoFormatada": "4:29",
  "genero": "Pop Rock",
  "anoLancamento": 2000,
  "artistaId": 1,
  "artistaNome": "The Beatles"
}
```

---

### 👤 Usuários — `/usuarios`

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/usuarios` | Cria um novo usuário | 201 |
| `GET` | `/usuarios` | Lista todos os usuários | 200 |
| `GET` | `/usuarios/{id}` | Busca usuário por ID | 200 / 404 |
| `PUT` | `/usuarios/{id}` | Atualiza um usuário | 200 / 404 |
| `DELETE` | `/usuarios/{id}` | Remove um usuário | 204 / 404 |

**POST /usuarios — Requisição:**
```json
{
  "nome": "João Pedro",
  "email": "joao@email.com",
  "senha": "senha123",
  "plano": "FREE"
}
```
**Resposta `201 Created`:**
```json
{
  "id": 3,
  "nome": "João Pedro",
  "email": "joao@email.com",
  "plano": "FREE",
  "totalPlaylists": 0
}
```

> ⚠️ A senha **nunca** é retornada nas respostas da API.

---

### 📂 Playlists — `/playlists`

| Método | Endpoint | Descrição | Status |
|--------|----------|-----------|--------|
| `POST` | `/playlists` | Cria uma nova playlist | 201 |
| `GET` | `/playlists` | Lista todas as playlists | 200 |
| `GET` | `/playlists/{id}` | Busca playlist por ID | 200 / 404 |
| `GET` | `/playlists/usuario/{usuarioId}` | Playlists de um usuário | 200 / 404 |
| `PUT` | `/playlists/{id}` | Atualiza uma playlist completa | 200 / 404 |
| `DELETE` | `/playlists/{id}` | Remove uma playlist | 204 / 404 |
| `PATCH` | `/playlists/{id}/musicas/adicionar` | Adiciona músicas à playlist | 200 / 404 |
| `PATCH` | `/playlists/{id}/musicas/remover` | Remove músicas da playlist | 200 / 404 |

**POST /playlists — Requisição:**
```json
{
  "nome": "Minha Playlist",
  "descricao": "Músicas favoritas",
  "publica": true,
  "usuarioId": 1,
  "musicaIds": [1, 3, 5]
}
```
**Resposta `201 Created`:**
```json
{
  "id": 3,
  "nome": "Minha Playlist",
  "descricao": "Músicas favoritas",
  "publica": true,
  "usuarioId": 1,
  "usuarioNome": "Lucas Silva",
  "totalMusicas": 3,
  "musicas": [
    {
      "id": 1,
      "titulo": "Hey Jude",
      "duracaoFormatada": "7:11",
      "artistaNome": "The Beatles"
    }
  ]
}
```

**PATCH /playlists/1/musicas/adicionar — Requisição:**
```json
{
  "musicaIds": [2, 4]
}
```

**Resposta `200 OK`** — retorna a playlist atualizada com as novas músicas incluídas.

---

## 🗄️ Script SQL

```sql
CREATE TABLE artista (
    id          BIGSERIAL    PRIMARY KEY,
    nome        VARCHAR(200) NOT NULL,
    pais_origem VARCHAR(100),
    ano_inicio  INTEGER
);

CREATE TABLE usuario (
    id    BIGSERIAL    PRIMARY KEY,
    nome  VARCHAR(200) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    senha VARCHAR(200) NOT NULL,
    plano VARCHAR(50)  DEFAULT 'FREE'
);

CREATE TABLE musica (
    id               BIGSERIAL    PRIMARY KEY,
    titulo           VARCHAR(200) NOT NULL,
    duracao_segundos INTEGER      NOT NULL CHECK (duracao_segundos > 0),
    genero           VARCHAR(100),
    ano_lancamento   INTEGER,
    artista_id       BIGINT       NOT NULL REFERENCES artista(id) ON DELETE CASCADE
);

CREATE TABLE playlist (
    id         BIGSERIAL    PRIMARY KEY,
    nome       VARCHAR(200) NOT NULL,
    descricao  VARCHAR(500),
    publica    BOOLEAN      DEFAULT TRUE,
    usuario_id BIGINT       NOT NULL REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE playlist_musica (
    playlist_id BIGINT NOT NULL REFERENCES playlist(id) ON DELETE CASCADE,
    musica_id   BIGINT NOT NULL REFERENCES musica(id)   ON DELETE CASCADE,
    PRIMARY KEY (playlist_id, musica_id)
);
```

---

## 📁 Estrutura de Arquivos

```
spotifake-quarkus/
├── src/
│   └── main/
│       ├── java/br/com/spotifake/
│       │   ├── model/
│       │   │   ├── Artista.java
│       │   │   ├── Musica.java
│       │   │   ├── Playlist.java
│       │   │   └── Usuario.java
│       │   ├── dto/
│       │   │   ├── ArtistaRequestDTO.java / ArtistaResponseDTO.java
│       │   │   ├── MusicaRequestDTO.java / MusicaResponseDTO.java
│       │   │   ├── PlaylistRequestDTO.java / PlaylistResponseDTO.java
│       │   │   ├── PlaylistMusicaRequestDTO.java
│       │   │   └── UsuarioRequestDTO.java / UsuarioResponseDTO.java
│       │   ├── repository/
│       │   │   ├── ArtistaRepository.java
│       │   │   ├── MusicaRepository.java
│       │   │   ├── PlaylistRepository.java
│       │   │   └── UsuarioRepository.java
│       │   ├── service/
│       │   │   ├── ArtistaService.java
│       │   │   ├── MusicaService.java
│       │   │   ├── PlaylistService.java
│       │   │   └── UsuarioService.java
│       │   └── resource/
│       │       ├── ArtistaResource.java
│       │       ├── MusicaResource.java
│       │       ├── PlaylistResource.java
│       │       └── UsuarioResource.java
│       └── resources/
│           └── application.properties
├── schema.sql
├── pom.xml
└── README.md
```

---

## 👥 Integrantes do Grupo

|        Nome         |
|---------------------|
| Lucas Battisti Piva |
|     Arthur Reis     | 
|   Camile Dalmolin   |

---

> Trabalho desenvolvido para a disciplina de Programação Orientada a Objetos — UNIVALI 2025
