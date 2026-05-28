-- ============================================================
--  Torneio de Games (estilo FACEIT) — schema.sql  5v5
--  Disciplina: Laboratório de Banco de Dados
--  UCB — Análise e Desenvolvimento de Sistemas
-- ============================================================

CREATE DATABASE IF NOT EXISTS torneio_games
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE torneio_games;

-- ------------------------------------------------------------
-- 1. tb_mapa
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_mapa (
    id        INT          AUTO_INCREMENT PRIMARY KEY,
    nome_mapa VARCHAR(100) NOT NULL,
    tipo      ENUM('Defuse','Resgate') NOT NULL
);

-- ------------------------------------------------------------
-- 2. tb_jogador
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_jogador (
    id       INT         AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(50) NOT NULL UNIQUE,
    elo      INT         NOT NULL DEFAULT 1000,
    status   ENUM('Online','Em Partida','Offline') NOT NULL DEFAULT 'Online',
    CONSTRAINT chk_elo CHECK (elo >= 0)
);

-- ------------------------------------------------------------
-- 3. tb_equipe
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_equipe (
    id   INT         AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tag  VARCHAR(10)  NOT NULL UNIQUE
);

-- ------------------------------------------------------------
-- 4. tb_equipe_jogador  (N:N — Equipes x Jogadores)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_equipe_jogador (
    id_equipe  INT NOT NULL,
    id_jogador INT NOT NULL,
    PRIMARY KEY (id_equipe, id_jogador),
    CONSTRAINT fk_ej_equipe  FOREIGN KEY (id_equipe)  REFERENCES tb_equipe(id)  ON DELETE CASCADE,
    CONSTRAINT fk_ej_jogador FOREIGN KEY (id_jogador) REFERENCES tb_jogador(id) ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- 5. tb_partida  — agora 5v5: equipe1 vs equipe2
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_partida (
    id           INT  AUTO_INCREMENT PRIMARY KEY,
    id_mapa      INT  NOT NULL,
    id_equipe1   INT  NOT NULL,
    id_equipe2   INT  NOT NULL,
    id_vencedor  INT  NULL,
    status       ENUM('Agendada','Finalizada') NOT NULL DEFAULT 'Agendada',
    data_partida DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_p_mapa    FOREIGN KEY (id_mapa)    REFERENCES tb_mapa(id),
    CONSTRAINT fk_p_equipe1 FOREIGN KEY (id_equipe1) REFERENCES tb_equipe(id),
    CONSTRAINT fk_p_equipe2 FOREIGN KEY (id_equipe2) REFERENCES tb_equipe(id),
    CONSTRAINT fk_p_venc    FOREIGN KEY (id_vencedor) REFERENCES tb_equipe(id),
    CONSTRAINT chk_equipes_diferentes CHECK (id_equipe1 != id_equipe2)
);

-- ------------------------------------------------------------
-- 6. tb_partida_jogador  — quais jogadores participaram de cada partida
--    (N:N — Partidas x Jogadores, com lado: 1 ou 2)
-- ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS tb_partida_jogador (
    id_partida INT NOT NULL,
    id_jogador INT NOT NULL,
    lado       TINYINT NOT NULL COMMENT '1 = equipe1, 2 = equipe2',
    PRIMARY KEY (id_partida, id_jogador),
    CONSTRAINT fk_pj_partida FOREIGN KEY (id_partida) REFERENCES tb_partida(id) ON DELETE CASCADE,
    CONSTRAINT fk_pj_jogador FOREIGN KEY (id_jogador) REFERENCES tb_jogador(id) ON DELETE CASCADE
);

-- ============================================================
-- VIEWS
-- ============================================================

CREATE OR REPLACE VIEW vw_ranking AS
SELECT
    ROW_NUMBER() OVER (ORDER BY elo DESC) AS posicao,
    nickname,
    elo,
    status
FROM tb_jogador;

CREATE OR REPLACE VIEW vw_partidas AS
SELECT
    p.id,
    e1.nome        AS equipe1,
    e2.nome        AS equipe2,
    m.nome_mapa    AS mapa,
    ev.nome        AS vencedor,
    p.status,
    p.data_partida
FROM tb_partida p
JOIN tb_equipe e1 ON p.id_equipe1  = e1.id
JOIN tb_equipe e2 ON p.id_equipe2  = e2.id
JOIN tb_mapa   m  ON p.id_mapa     = m.id
LEFT JOIN tb_equipe ev ON p.id_vencedor = ev.id
ORDER BY p.data_partida DESC;

-- ============================================================
-- DADOS DE TESTE
-- ============================================================

INSERT IGNORE INTO tb_mapa (nome_mapa, tipo) VALUES
    ('Dust II',  'Defuse'),
    ('Mirage',   'Defuse'),
    ('Inferno',  'Defuse'),
    ('Nuke',     'Defuse'),
    ('Vertigo',  'Defuse'),
    ('Breeze',   'Resgate'),
    ('Bind',     'Resgate');

INSERT IGNORE INTO tb_jogador (nickname, elo, status) VALUES
    ('s1mple',  3200, 'Online'),
    ('ZywOo',   3150, 'Online'),
    ('NiKo',    2900, 'Offline'),
    ('device',  2850, 'Online'),
    ('sh1ro',   2700, 'Em Partida'),
    ('electroNic', 2600, 'Online'),
    ('b1t',     2550, 'Online'),
    ('Hobbit',  2500, 'Online'),
    ('nafany',  2450, 'Online'),
    ('Perfecto',2400, 'Online');

INSERT IGNORE INTO tb_equipe (nome, tag) VALUES
    ('Natus Vincere', 'NaVi'),
    ('Team Vitality',  'VIT'),
    ('FaZe Clan',      'FaZe');

-- NaVi: s1mple, electroNic, b1t, Hobbit, Perfecto
INSERT IGNORE INTO tb_equipe_jogador VALUES (1,1),(1,6),(1,7),(1,8),(1,10);
-- VIT: ZywOo, device
INSERT IGNORE INTO tb_equipe_jogador VALUES (2,2),(2,4);
-- FaZe: NiKo, sh1ro, nafany
INSERT IGNORE INTO tb_equipe_jogador VALUES (3,3),(3,5),(3,9);
