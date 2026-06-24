# Spotifake — API REST com Quarkus



## Arquitetura

```
src/main/java/br/com/spotifake/
├── model/        → Entidades JPA (mapeadas para o banco)
├── dto/          → Objetos de transferência (entrada e saída da API)
├── repository/   → Acesso ao banco via EntityManager
├── service/      → Regras de negócio + conversão DTO ↔ entidade
└── resource/     → Endpoints REST (JAX-RS)
```

---

## Endpoints — Música

### POST /musicas — Criar música
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
**Resposta 201 Created:**
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

---

### GET /musicas — Listar todas
**Resposta 200 OK:**
```json
[
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
]
```

---

### GET /musicas/{id} — Buscar por ID
**Resposta 200 OK** → objeto da música  
**Resposta 404 Not Found** → `{ "title": "Música com ID 99 não encontrada" }`

---

### GET /musicas/artista/{artistaId} — Músicas de um artista
**Resposta 200 OK** → lista de músicas do artista

---

### PUT /musicas/{id} — Atualizar música
**Corpo:** mesmo formato do POST  
**Resposta 200 OK** → objeto atualizado

---

### DELETE /musicas/{id} — Remover música
**Resposta 204 No Content**

---

## Endpoints — Artista

### POST /artistas — Criar artista
```json
{
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960
}
```
**Resposta 201 Created:**
```json
{
  "id": 1,
  "nome": "The Beatles",
  "paisOrigem": "Reino Unido",
  "anoInicio": 1960,
  "totalMusicas": 0
}
```

### GET /artistas — Listar todos → 200 OK
### GET /artistas/{id} — Buscar por ID → 200 OK ou 404
### PUT /artistas/{id} — Atualizar → 200 OK
### DELETE /artistas/{id} — Remover → 204 No Content

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Quarkus 3.9 | Framework principal |
| JAX-RS | Endpoints REST |
| JPA / Hibernate | ORM (mapeamento objeto-relacional) |
| CDI | Injeção de dependência (`@Inject`) |
| Bean Validation | Validação de campos (`@NotBlank`, etc.) |
| PostgreSQL | Banco de dados |
