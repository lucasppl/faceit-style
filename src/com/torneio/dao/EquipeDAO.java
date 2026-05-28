package com.torneio.dao;

import com.torneio.db.ConnectionFactory;
import com.torneio.model.Equipe;
import com.torneio.model.Jogador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {

    public void inserir(Equipe e) throws SQLException {
        String sql = "INSERT INTO tb_equipe (nome, tag) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getTag());
            ps.executeUpdate();
        }
    }

    public List<Equipe> listar() throws SQLException {
        List<Equipe> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_equipe ORDER BY nome";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    public void atualizar(Equipe e) throws SQLException {
        String sql = "UPDATE tb_equipe SET nome = ?, tag = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getNome());
            ps.setString(2, e.getTag());
            ps.setInt(3, e.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tb_equipe WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void adicionarJogador(int idEquipe, int idJogador) throws SQLException {
        String sql = "INSERT IGNORE INTO tb_equipe_jogador (id_equipe, id_jogador) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipe); ps.setInt(2, idJogador);
            ps.executeUpdate();
        }
    }

    public void removerJogador(int idEquipe, int idJogador) throws SQLException {
        String sql = "DELETE FROM tb_equipe_jogador WHERE id_equipe = ? AND id_jogador = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipe); ps.setInt(2, idJogador);
            ps.executeUpdate();
        }
    }

    public List<Jogador> listarJogadores(int idEquipe) throws SQLException {
        List<Jogador> lista = new ArrayList<>();
        String sql = "SELECT j.* FROM tb_jogador j " +
                     "JOIN tb_equipe_jogador ej ON j.id = ej.id_jogador " +
                     "WHERE ej.id_equipe = ? ORDER BY j.elo DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEquipe);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Jogador(
                        rs.getInt("id"), rs.getString("nickname"),
                        rs.getInt("elo"), rs.getString("status")));
                }
            }
        }
        return lista;
    }

    private Equipe mapear(ResultSet rs) throws SQLException {
        return new Equipe(rs.getInt("id"), rs.getString("nome"), rs.getString("tag"));
    }
}
