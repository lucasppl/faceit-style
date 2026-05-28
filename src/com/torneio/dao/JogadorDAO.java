package com.torneio.dao;

import com.torneio.db.ConnectionFactory;
import com.torneio.model.Jogador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JogadorDAO {

    public void inserir(Jogador j) throws SQLException {
        String sql = "INSERT INTO tb_jogador (nickname, elo, status) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getNickname());
            ps.setInt(2, j.getElo());
            ps.setString(3, j.getStatus());
            ps.executeUpdate();
        }
    }

    public List<Jogador> listar() throws SQLException {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_jogador ORDER BY elo DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public void atualizar(Jogador j) throws SQLException {
        String sql = "UPDATE tb_jogador SET nickname = ?, elo = ?, status = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, j.getNickname());
            ps.setInt(2, j.getElo());
            ps.setString(3, j.getStatus());
            ps.setInt(4, j.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tb_jogador WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Atualiza ELO: +25 para o vencedor, -25 para o perdedor (mínimo 0).
     * Usa transação para garantir consistência.
     */
    public void atualizarElo(int idVencedor, int idPerdedor) throws SQLException {
        String sqlVencedor = "UPDATE tb_jogador SET elo = elo + 25 WHERE id = ?";
        String sqlPerdedor = "UPDATE tb_jogador SET elo = GREATEST(0, elo - 25) WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psV = conn.prepareStatement(sqlVencedor);
                 PreparedStatement psP = conn.prepareStatement(sqlPerdedor)) {
                psV.setInt(1, idVencedor);
                psV.executeUpdate();
                psP.setInt(1, idPerdedor);
                psP.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private Jogador mapear(ResultSet rs) throws SQLException {
        return new Jogador(
            rs.getInt("id"),
            rs.getString("nickname"),
            rs.getInt("elo"),
            rs.getString("status")
        );
    }
}
