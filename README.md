# 🎮 Torneio de Games — FACEIT Style
**Disciplina:** Laboratório de Banco de Dados  
**UCB — Análise e Desenvolvimento de Sistemas**

---

## Tecnologias
- Java 11+
- MySQL 8+
- JDBC (MySQL Connector/J)
- Java Swing

---

## Como configurar e executar

### 1. Banco de Dados
1. Abra o MySQL Workbench ou terminal MySQL.
2. Execute o script:
   ```
   db/schema.sql
   ```
3. O banco `torneio_games` será criado automaticamente com dados de teste.

### 2. Configurar a conexão
Abra o arquivo:
```
src/com/torneio/db/ConnectionFactory.java
```
Altere `PASSWORD` para a senha do seu MySQL:
```java
private static final String PASSWORD = "sua_senha_aqui";
```

### 3. Dependência JDBC
Adicione o driver MySQL ao classpath do projeto:
- Faça download do `mysql-connector-j-8.x.x.jar` em https://dev.mysql.com/downloads/connector/j/
- No IntelliJ: File → Project Structure → Modules → Dependencies → Add JAR

### 4. Executar
Execute a classe principal:
```
src/com/torneio/App.java
```

---

## Estrutura do Projeto
```
torneio-games/
├── db/
│   └── schema.sql              ← DDL completo + dados de teste
├── src/com/torneio/
│   ├── App.java                ← Ponto de entrada
│   ├── db/
│   │   └── ConnectionFactory   ← Conexão JDBC
│   ├── model/
│   │   ├── Jogador.java
│   │   ├── Mapa.java
│   │   ├── Equipe.java
│   │   └── Partida.java
│   ├── dao/
│   │   ├── JogadorDAO.java
│   │   ├── MapaDAO.java
│   │   ├── EquipeDAO.java
│   │   └── PartidaDAO.java
│   └── ui/
│       ├── MainFrame.java      ← Janela principal (JTabbedPane)
│       ├── JogadorPanel.java   ← CRUD de jogadores
│       ├── MapaPanel.java      ← CRUD de mapas
│       └── PartidaPanel.java   ← Matchmaking + histórico
└── README.md
```

---

## Funcionalidades

| Tela | Operações |
|------|-----------|
| Jogadores | Cadastrar, Listar, Editar, Deletar |
| Mapas | Cadastrar, Listar, Editar, Deletar |
| Partidas | Criar partida, Finalizar (define vencedor e atualiza ELO), Deletar |

### Fluxo de uma Partida (estilo FACEIT)
1. Selecione **Jogador 1**, **Jogador 2** e **Mapa** nos combos.
2. Clique em **▶ Criar Partida** — partida entra com status `Agendada`.
3. Selecione a partida na tabela e clique em **🏆 Finalizar Partida**.
4. Um popup pergunta quem venceu.
5. O sistema registra o vencedor e aplica:
   - **+25 ELO** para o vencedor
   - **-25 ELO** para o perdedor (mínimo 0)

---

## Banco de Dados — Tabelas

| Tabela | Descrição |
|--------|-----------|
| `tb_jogador` | Jogadores (nickname, ELO, status) |
| `tb_mapa` | Mapas (nome, tipo: Defuse/Resgate) |
| `tb_equipe` | Equipes (nome, tag) |
| `tb_equipe_jogador` | **N:N** — associação equipes ↔ jogadores |
| `tb_partida` | Partidas com resultado e atualização de ELO |
