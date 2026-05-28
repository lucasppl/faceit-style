package com.torneio.dao;

import com.torneio.db.ConnectionFactory;
import com.torneio.model.Mapa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MapaDAO {

    public void inserir(Mapa m) throws SQLException {
        String sql = "INSERT INTO tb_mapa (nome_mapa, tipo) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNomeMapa());
            ps.setString(2, m.getTipo());
            ps.executeUpdate();
        }
    }

    public List<Mapa> listar() throws SQLException {
        List<Mapa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tb_mapa ORDER BY nome_mapa";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public void atualizar(Mapa m) throws SQLException {
        String sql = "UPDATE tb_mapa SET nome_mapa = ?, tipo = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getNomeMapa());
            ps.setString(2, m.getTipo());
            ps.setInt(3, m.getId());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tb_mapa WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Mapa mapear(ResultSet rs) throws SQLException {
        return new Mapa(
            rs.getInt("id"),
            rs.getString("nome_mapa"),
            rs.getString("tipo")
        );
    }
}
