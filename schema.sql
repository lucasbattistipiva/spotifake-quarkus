-- =============================================================
-- Spotifake - Script de criação do banco de dados
-- Execute este script no PostgreSQL antes de subir a aplicação
-- =============================================================

-- Tabela de artistas
CREATE TABLE IF NOT EXISTS artista (
    id           BIGSERIAL    PRIMARY KEY,
    nome         VARCHAR(200) NOT NULL,
    pais_origem  VARCHAR(100),
    ano_inicio   INTEGER
);

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuario (
    id     BIGSERIAL    PRIMARY KEY,
    nome   VARCHAR(200) NOT NULL,
    email  VARCHAR(200) NOT NULL UNIQUE,
    senha  VARCHAR(200) NOT NULL,
    plano  VARCHAR(50)  DEFAULT 'FREE'
);

-- Tabela de músicas
CREATE TABLE IF NOT EXISTS musica (
    id                BIGSERIAL    PRIMARY KEY,
    titulo            VARCHAR(200) NOT NULL,
    duracao_segundos  INTEGER      NOT NULL CHECK (duracao_segundos > 0),
    genero            VARCHAR(100),
    ano_lancamento    INTEGER,
    artista_id        BIGINT       NOT NULL REFERENCES artista(id) ON DELETE CASCADE
);

-- Tabela de playlists
CREATE TABLE IF NOT EXISTS playlist (
    id          BIGSERIAL    PRIMARY KEY,
    nome        VARCHAR(200) NOT NULL,
    descricao   VARCHAR(500),
    publica     BOOLEAN      DEFAULT TRUE,
    usuario_id  BIGINT       NOT NULL REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela associativa playlist <-> musica (ManyToMany)
CREATE TABLE IF NOT EXISTS playlist_musica (
    playlist_id  BIGINT NOT NULL REFERENCES playlist(id) ON DELETE CASCADE,
    musica_id    BIGINT NOT NULL REFERENCES musica(id)   ON DELETE CASCADE,
    PRIMARY KEY (playlist_id, musica_id)
);

-- Índices para melhorar performance
CREATE INDEX IF NOT EXISTS idx_musica_artista   ON musica(artista_id);
CREATE INDEX IF NOT EXISTS idx_playlist_usuario ON playlist(usuario_id);

-- =============================================================
-- Dados de exemplo para testes
-- =============================================================
INSERT INTO artista (nome, pais_origem, ano_inicio) VALUES
    ('The Beatles',   'Reino Unido', 1960),
    ('Legião Urbana', 'Brasil',      1982),
    ('Daft Punk',     'França',      1993)
ON CONFLICT DO NOTHING;

INSERT INTO usuario (nome, email, senha, plano) VALUES
    ('Lucas Silva',  'lucas@email.com',  'senha123', 'PREMIUM'),
    ('Maria Souza',  'maria@email.com',  'senha456', 'FREE')
ON CONFLICT DO NOTHING;

INSERT INTO musica (titulo, duracao_segundos, genero, ano_lancamento, artista_id) VALUES
    ('Hey Jude',          431, 'Rock',          1968, 1),
    ('Let It Be',         243, 'Rock',          1970, 1),
    ('Que País É Este',   225, 'Rock Nacional', 1987, 2),
    ('Pais e Filhos',     383, 'Rock Nacional', 1989, 2),
    ('Get Lucky',         248, 'Electronic',    2013, 3),
    ('One More Time',     201, 'Electronic',    2000, 3)
ON CONFLICT DO NOTHING;

INSERT INTO playlist (nome, descricao, publica, usuario_id) VALUES
    ('Clássicos do Rock',  'As melhores do rock', TRUE,  1),
    ('Eletrônico Top',     'Hits eletrônicos',    FALSE, 2)
ON CONFLICT DO NOTHING;

INSERT INTO playlist_musica (playlist_id, musica_id) VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 5), (2, 6)
ON CONFLICT DO NOTHING;
