package com.torneio.dao;

import com.torneio.db.ConnectionFactory;
import com.torneio.model.Partida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO {

    /**
     * Cria partida 5v5: registra equipes e jogadores participantes.
     * idJogadoresE1 e idJogadoresE2 devem ter exatamente 5 IDs cada.
     */
    public void inserir(int idMapa, int idEquipe1, int idEquipe2,
                        List<Integer> idJogadoresE1,
                        List<Integer> idJogadoresE2) throws SQLException {

        String sqlPartida  = "INSERT INTO tb_partida (id_mapa, id_equipe1, id_equipe2) VALUES (?, ?, ?)";
        String sqlJogador  = "INSERT INTO tb_partida_jogador (id_partida, id_jogador, lado) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int idPartida;
                try (PreparedStatement ps = conn.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, idMapa);
                    ps.setInt(2, idEquipe1);
                    ps.setInt(3, idEquipe2);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        idPartida = rs.getInt(1);
                    }
                }
                try (PreparedStatement ps = conn.prepareStatement(sqlJogador)) {
                    for (int idJ : idJogadoresE1) {
                        ps.setInt(1, idPartida); ps.setInt(2, idJ); ps.setInt(3, 1);
                        ps.addBatch();
                    }
                    for (int idJ : idJogadoresE2) {
                        ps.setInt(1, idPartida); ps.setInt(2, idJ); ps.setInt(3, 2);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    /** Lista todas as partidas com JOIN para exibir nomes. */
    public List<Partida> listar() throws SQLException {
        List<Partida> lista = new ArrayList<>();
        String sql =
            "SELECT p.id, p.status, p.data_partida, m.nome_mapa, " +
            "       e1.id AS id_e1, e1.nome AS nome_e1, " +
            "       e2.id AS id_e2, e2.nome AS nome_e2, " +
            "       ev.id AS id_v,  ev.nome AS nome_v  " +
            "FROM tb_partida p " +
            "JOIN tb_mapa   m  ON p.id_mapa    = m.id  " +
            "JOIN tb_equipe e1 ON p.id_equipe1 = e1.id " +
            "JOIN tb_equipe e2 ON p.id_equipe2 = e2.id " +
            "LEFT JOIN tb_equipe ev ON p.id_vencedor = ev.id " +
            "ORDER BY p.data_partida DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Partida p = new Partida();
                p.setId(rs.getInt("id"));
                p.setStatus(rs.getString("status"));
                p.setDataPartida(rs.getString("data_partida"));
                p.setNomeMapa(rs.getString("nome_mapa"));
                p.setIdEquipe1(rs.getInt("id_e1"));
                p.setNomeEquipe1(rs.getString("nome_e1"));
                p.setIdEquipe2(rs.getInt("id_e2"));
                p.setNomeEquipe2(rs.getString("nome_e2"));
                int idV = rs.getInt("id_v");
                if (!rs.wasNull()) {
                    p.setIdVencedor(idV);
                    p.setNomeVencedor(rs.getString("nome_v"));
                } else {
                    p.setNomeVencedor("-");
                }
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * Finaliza partida: define equipe vencedora, muda status,
     * e atualiza ELO de TODOS os jogadores participantes (+25 / -25).
     */
    public void finalizar(int idPartida, int idEquipeVencedora, int idEquipePerdedora) throws SQLException {
        String sqlPartida  = "UPDATE tb_partida SET id_vencedor = ?, status = 'Finalizada' WHERE id = ?";
        String sqlVencedor = "UPDATE tb_jogador SET elo = elo + 25 " +
                             "WHERE id IN (SELECT id_jogador FROM tb_partida_jogador WHERE id_partida = ? AND lado = " +
                             "(SELECT CASE WHEN id_equipe1 = ? THEN 1 ELSE 2 END FROM tb_partida WHERE id = ?))";
        String sqlPerdedor = "UPDATE tb_jogador SET elo = GREATEST(0, elo - 25) " +
                             "WHERE id IN (SELECT id_jogador FROM tb_partida_jogador WHERE id_partida = ? AND lado = " +
                             "(SELECT CASE WHEN id_equipe1 = ? THEN 1 ELSE 2 END FROM tb_partida WHERE id = ?))";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conn.prepareStatement(sqlPartida)) {
                    ps.setInt(1, idEquipeVencedora);
                    ps.setInt(2, idPartida);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(sqlVencedor)) {
                    ps.setInt(1, idPartida);
                    ps.setInt(2, idEquipeVencedora);
                    ps.setInt(3, idPartida);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = conn.prepareStatement(sqlPerdedor)) {
                    ps.setInt(1, idPartida);
                    ps.setInt(2, idEquipePerdedora);
                    ps.setInt(3, idPartida);
                    ps.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tb_partida WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Retorna IDs dos jogadores de uma partida por lado (1 ou 2). */
    public List<Integer> listarJogadoresDaPartida(int idPartida, int lado) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT id_jogador FROM tb_partida_jogador WHERE id_partida = ? AND lado = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            ps.setInt(2, lado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) ids.add(rs.getInt(1));
            }
        }
        return ids;
    }
}
