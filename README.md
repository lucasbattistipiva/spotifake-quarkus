# Spotifake — API REST com Quarkus

API REST desenvolvida com Quarkus e PostgreSQL para gerenciamento de músicas, artistas, playlists e usuários.

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Quarkus | 3.9.4 | Framework principal |
| JAX-RS | — | Endpoints REST |
| JPA / Hibernate ORM | — | Mapeamento objeto-relacional |
| CDI (Quarkus ARC) | — | Injeção de dependência |
| Hibernate Validator | — | Validação de campos |
| PostgreSQL | — | Banco de dados |
| Java | 17+ | Linguagem |

---

## Arquitetura

```
src/main/java/br/com/spotifake/
├── model/       → Entidades JPA (mapeadas para as tabelas do banco)
├── dto/         → Objetos de transferência (Request e Response)
├── repository/  → Acesso ao banco via EntityManager
├── service/     → Regras de negócio e conversão DTO ↔ entidade
└── resource/    → Endpoints REST (JAX-RS)
```

---

## Configuração e execução

### Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL rodando na porta 5432

### 1. Criar o banco de dados

```sql
CREATE DATABASE spotifake;
```

### 2. Executar o script SQL

```bash
psql -U postgres -d spotifake -f schema.sql
```

O `schema.sql` cria todas as tabelas e insere dados de exemplo.

### 3. Configurar credenciais (se necessário)

Edite `src/main/resources/application.properties`:

```properties
quarkus.datasource.username=postgres
quarkus.datasource.password=
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/spotifake
```

### 4. Executar a aplicação

```bash
./mvnw quarkus:dev
```

A API estará disponível em `http://localhost:8080`.

---

## Entidades e relacionamentos

```
Artista (1) ──── (N) Musica
Usuario (1) ──── (N) Playlist
Playlist (N) ──── (N) Musica   [tabela associativa: playlist_musica]
```

---

## Endpoints — Artista

### `POST /artistas` — Criar artista

**Corpo da requisição:**
```json
{
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960
}
```

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960,
  "totalMusicas": 0
}
```

**Validações:** `nome` é obrigatório.

---

### `GET /artistas` — Listar todos os artistas

**Resposta `200 OK`:**
```json
[
  {
    "id": 1,
    "nome": "Daft Punk",
    "paisOrigem": "França",
    "anoInicio": 1993,
    "totalMusicas": 2
  },
  {
    "id": 2,
    "nome": "Legião Urbana",
    "paisOrigem": "Brasil",
    "anoInicio": 1982,
    "totalMusicas": 2
  }
]
```

---

### `GET /artistas/{id}` — Buscar artista por ID

**Resposta `200 OK`:**
```json
{
  "id": 1,
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960,
  "totalMusicas": 2
}
```

**Resposta `404 Not Found`:**
```json
{
  "title": "Artista com ID 99 não encontrado"
}
```

---

### `PUT /artistas/{id}` — Atualizar artista

**Corpo da requisição:**
```json
{
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960
}
```

**Resposta `200 OK`:** objeto atualizado (mesmo formato do GET).

---

### `DELETE /artistas/{id}` — Remover artista

**Resposta `204 No Content`** (sem corpo)

**Resposta `404 Not Found`** se o ID não existir.

---

## Endpoints — Música

### `POST /musicas` — Criar música

**Corpo da requisição:**
```json
{
  "titulo": "Hey Jude",
  "duracaoSegundos": 431,
  "genero": "Rock",
  "anoLancamento": 1968,
  "artistaId": 1
}
```

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "titulo": "Hey Jude",
  "duracaoSegundos": 431,
  "duracaoFormatada": "7:11",
  "genero": "Rock",
  "anoLancamento": 1968,
  "artistaId": 1,
  "artistaNome": "The Beatles"
}
```

**Validações:** `titulo`, `duracaoSegundos` (mínimo 1) e `artistaId` são obrigatórios.

---

### `GET /musicas` — Listar todas as músicas

**Resposta `200 OK`:**
```json
[
  {
    "id": 1,
    "titulo": "Get Lucky",
    "duracaoSegundos": 248,
    "duracaoFormatada": "4:08",
    "genero": "Electronic",
    "anoLancamento": 2013,
    "artistaId": 3,
    "artistaNome": "Daft Punk"
  },
  {
    "id": 2,
    "titulo": "Hey Jude",
    "duracaoSegundos": 431,
    "duracaoFormatada": "7:11",
    "genero": "Rock",
    "anoLancamento": 1968,
    "artistaId": 1,
    "artistaNome": "The Beatles"
  }
]
```

---

### `GET /musicas/{id}` — Buscar música por ID

**Resposta `200 OK`:** objeto da música (mesmo formato acima).

**Resposta `404 Not Found`:**
```json
{
  "title": "Música com ID 99 não encontrada"
}
```

---

### `GET /musicas/artista/{artistaId}` — Listar músicas de um artista

**Resposta `200 OK`:** lista de músicas do artista especificado.

**Resposta `404 Not Found`** se o artista não existir.

---

### `PUT /musicas/{id}` — Atualizar música

**Corpo da requisição:** mesmo formato do POST.

**Resposta `200 OK`:** objeto atualizado.

---

### `DELETE /musicas/{id}` — Remover música

**Resposta `204 No Content`** (sem corpo)

---

## Endpoints — Usuário

### `POST /usuarios` — Criar usuário

**Corpo da requisição:**
```json
{
  "nome": "Lucas Silva",
  "email": "lucas@email.com",
  "senha": "senha123",
  "plano": "premium"
}
```

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "nome": "Lucas Silva",
  "email": "lucas@email.com",
  "plano": "PREMIUM",
  "totalPlaylists": 0
}
```

**Validações:** `nome`, `email` (formato válido) e `senha` (mínimo 6 caracteres) são obrigatórios. E-mail deve ser único no sistema. O campo `plano` é convertido automaticamente para maiúsculas; padrão `FREE`.

**Resposta `400 Bad Request`** se o e-mail já estiver em uso:
```json
{
  "title": "Já existe um usuário com o e-mail: lucas@email.com"
}
```

---

### `GET /usuarios` — Listar todos os usuários

**Resposta `200 OK`:**
```json
[
  {
    "id": 1,
    "nome": "Lucas Silva",
    "email": "lucas@email.com",
    "plano": "PREMIUM",
    "totalPlaylists": 2
  },
  {
    "id": 2,
    "nome": "Maria Souza",
    "email": "maria@email.com",
    "plano": "FREE",
    "totalPlaylists": 1
  }
]
```

---

### `GET /usuarios/{id}` — Buscar usuário por ID

**Resposta `200 OK`:** objeto do usuário (mesmo formato acima).

**Resposta `404 Not Found`:**
```json
{
  "title": "Usuário com ID 99 não encontrado"
}
```

---

### `PUT /usuarios/{id}` — Atualizar usuário

**Corpo da requisição:**
```json
{
  "nome": "Lucas Silva",
  "email": "lucas.novo@email.com",
  "senha": "novaSenha123",
  "plano": "free"
}
```

**Resposta `200 OK`:** objeto atualizado.

**Resposta `400 Bad Request`** se o novo e-mail já pertencer a outro usuário.

---

### `DELETE /usuarios/{id}` — Remover usuário

**Resposta `204 No Content`** (sem corpo)

---

## Endpoints — Playlist

### `POST /playlists` — Criar playlist

**Corpo da requisição:**
```json
{
  "nome": "Clássicos do Rock",
  "descricao": "As melhores músicas do rock de todos os tempos",
  "publica": true,
  "usuarioId": 1,
  "musicaIds": [1, 2, 3]
}
```

O campo `musicaIds` é opcional — a playlist pode ser criada sem músicas.

**Resposta `201 Created`:**
```json
{
  "id": 1,
  "nome": "Clássicos do Rock",
  "descricao": "As melhores músicas do rock de todos os tempos",
  "publica": true,
  "usuarioId": 1,
  "usuarioNome": "Lucas Silva",
  "totalMusicas": 3,
  "musicas": [
    {
      "id": 1,
      "titulo": "Hey Jude",
      "duracaoSegundos": 431,
      "duracaoFormatada": "7:11",
      "genero": "Rock",
      "anoLancamento": 1968,
      "artistaId": 1,
      "artistaNome": "The Beatles"
    },
    {
      "id": 2,
      "titulo": "Let It Be",
      "duracaoSegundos": 243,
      "duracaoFormatada": "4:03",
      "genero": "Rock",
      "anoLancamento": 1970,
      "artistaId": 1,
      "artistaNome": "The Beatles"
    },
    {
      "id": 3,
      "titulo": "Que País É Este",
      "duracaoSegundos": 225,
      "duracaoFormatada": "3:45",
      "genero": "Rock Nacional",
      "anoLancamento": 1987,
      "artistaId": 2,
      "artistaNome": "Legião Urbana"
    }
  ]
}
```

**Validações:** `nome` e `usuarioId` são obrigatórios. Todos os IDs de músicas informados devem existir.

---

### `GET /playlists` — Listar todas as playlists

**Resposta `200 OK`:** lista de playlists (mesmo formato de resposta do POST, com músicas aninhadas).

---

### `GET /playlists/{id}` — Buscar playlist por ID

**Resposta `200 OK`:** objeto da playlist com todas as músicas.

**Resposta `404 Not Found`:**
```json
{
  "title": "Playlist com ID 99 não encontrada"
}
```

---

### `GET /playlists/usuario/{usuarioId}` — Listar playlists de um usuário

**Resposta `200 OK`:** lista de playlists do usuário especificado.

**Resposta `404 Not Found`** se o usuário não existir.

---

### `PUT /playlists/{id}` — Atualizar playlist

**Corpo da requisição:**
```json
{
  "nome": "Clássicos do Rock — Atualizada",
  "descricao": "Versão atualizada da playlist",
  "publica": false,
  "usuarioId": 1,
  "musicaIds": [1, 2]
}
```

A lista `musicaIds` substitui completamente as músicas anteriores da playlist.

**Resposta `200 OK`:** objeto atualizado.

---

### `DELETE /playlists/{id}` — Remover playlist

**Resposta `204 No Content`** (sem corpo)

---

## Resumo dos endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/artistas` | Criar artista |
| GET | `/artistas` | Listar todos os artistas |
| GET | `/artistas/{id}` | Buscar artista por ID |
| PUT | `/artistas/{id}` | Atualizar artista |
| DELETE | `/artistas/{id}` | Remover artista |
| POST | `/musicas` | Criar música |
| GET | `/musicas` | Listar todas as músicas |
| GET | `/musicas/{id}` | Buscar música por ID |
| GET | `/musicas/artista/{artistaId}` | Listar músicas de um artista |
| PUT | `/musicas/{id}` | Atualizar música |
| DELETE | `/musicas/{id}` | Remover música |
| POST | `/usuarios` | Criar usuário |
| GET | `/usuarios` | Listar todos os usuários |
| GET | `/usuarios/{id}` | Buscar usuário por ID |
| PUT | `/usuarios/{id}` | Atualizar usuário |
| DELETE | `/usuarios/{id}` | Remover usuário |
| POST | `/playlists` | Criar playlist |
| GET | `/playlists` | Listar todas as playlists |
| GET | `/playlists/{id}` | Buscar playlist por ID |
| GET | `/playlists/usuario/{usuarioId}` | Listar playlists de um usuário |
| PUT | `/playlists/{id}` | Atualizar playlist |
| DELETE | `/playlists/{id}` | Remover playlist |
