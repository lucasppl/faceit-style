package com.torneio.ui;

import com.torneio.dao.PartidaDAO;
import com.torneio.dao.EquipeDAO;
import com.torneio.dao.MapaDAO;
import com.torneio.model.Partida;
import com.torneio.model.Equipe;
import com.torneio.model.Mapa;
import com.torneio.model.Jogador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidaPanel extends javax.swing.JPanel {

    private final PartidaDAO partidaDAO = new PartidaDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final MapaDAO mapaDAO = new MapaDAO();

    public PartidaPanel() {
        initComponents();
        if (!java.beans.Beans.isDesignTime()) {
            carregarPartidas();
            carregarCombos();
        }
    }

    public void carregarPartidas() {
        DefaultTableModel model = (DefaultTableModel) tblPartidas.getModel();
        model.setRowCount(0);
        try {
            List<Partida> lista = partidaDAO.listar();
            for (Partida p : lista) {
                model.addRow(new Object[]{
                    p.getId(),
                    p.getStatus(),
                    p.getDataPartida(),
                    p.getNomeMapa(),
                    p.getNomeEquipe1(),
                    p.getNomeEquipe2(),
                    p.getNomeVencedor()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar partidas: " + ex.getMessage());
        }
    }

    public void carregarCombos() {
        cmbEquipe1.removeAllItems();
        cmbEquipe2.removeAllItems();
        cmbMapa.removeAllItems();
        try {
            List<Equipe> equipes = equipeDAO.listar();
            for (Equipe e : equipes) {
                cmbEquipe1.addItem(e);
                cmbEquipe2.addItem(e);
            }
            List<Mapa> mapas = mapaDAO.listar();
            for (Mapa m : mapas) {
                cmbMapa.addItem(m);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar equipes/mapas nos combos: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPartidaForm = new javax.swing.JPanel();
        lblEquipe1 = new javax.swing.JLabel();
        cmbEquipe1 = new javax.swing.JComboBox<>();
        lblEquipe2 = new javax.swing.JLabel();
        cmbEquipe2 = new javax.swing.JComboBox<>();
        lblMapa = new javax.swing.JLabel();
        cmbMapa = new javax.swing.JComboBox<>();
        btnCriarPartida = new javax.swing.JButton();
        scrollPaneTable = new javax.swing.JScrollPane();
        tblPartidas = new javax.swing.JTable();
        pnlPartidaControls = new javax.swing.JPanel();
        btnFinalizarPartida = new javax.swing.JButton();
        btnExcluirPartida = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        pnlPartidaForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendar Nova Partida (5v5)"));
        pnlPartidaForm.setPreferredSize(new java.awt.Dimension(800, 70));

        lblEquipe1.setText("Equipe 1:");
        pnlPartidaForm.add(lblEquipe1);

        cmbEquipe1.setPreferredSize(new java.awt.Dimension(150, 25));
        pnlPartidaForm.add(cmbEquipe1);

        lblEquipe2.setText("Equipe 2:");
        pnlPartidaForm.add(lblEquipe2);

        cmbEquipe2.setPreferredSize(new java.awt.Dimension(150, 25));
        pnlPartidaForm.add(cmbEquipe2);

        lblMapa.setText("Mapa:");
        pnlPartidaForm.add(lblMapa);

        cmbMapa.setPreferredSize(new java.awt.Dimension(150, 25));
        pnlPartidaForm.add(cmbMapa);

        btnCriarPartida.setText("Criar Partida");
        btnCriarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarPartidaActionPerformed(evt);
            }
        });
        pnlPartidaForm.add(btnCriarPartida);

        add(pnlPartidaForm, java.awt.BorderLayout.NORTH);

        tblPartidas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status", "Data", "Mapa", "Equipe 1", "Equipe 2", "Vencedor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPaneTable.setViewportView(tblPartidas);

        add(scrollPaneTable, java.awt.BorderLayout.CENTER);

        pnlPartidaControls.setPreferredSize(new java.awt.Dimension(800, 50));

        btnFinalizarPartida.setText("🏆 Finalizar Partida");
        btnFinalizarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarPartidaActionPerformed(evt);
            }
        });
        pnlPartidaControls.add(btnFinalizarPartida);

        btnExcluirPartida.setText("Excluir Partida");
        btnExcluirPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirPartidaActionPerformed(evt);
            }
        });
        pnlPartidaControls.add(btnExcluirPartida);

        add(pnlPartidaControls, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCriarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarPartidaActionPerformed
        Equipe e1 = (Equipe) cmbEquipe1.getSelectedItem();
        Equipe e2 = (Equipe) cmbEquipe2.getSelectedItem();
        Mapa mapa = (Mapa) cmbMapa.getSelectedItem();

        if (e1 == null || e2 == null || mapa == null) {
            JOptionPane.showMessageDialog(this, "Selecione as equipes e o mapa.");
            return;
        }

        if (e1.getId() == e2.getId()) {
            JOptionPane.showMessageDialog(this, "Uma equipe não pode jogar contra ela mesma.");
            return;
        }

        try {
            // Verificar jogadores da equipe 1
            List<Jogador> jogE1 = equipeDAO.listarJogadores(e1.getId());
            if (jogE1.size() != 5) {
                JOptionPane.showMessageDialog(this, "A equipe " + e1.getNome() + " possui " + jogE1.size() + " jogador(es).\nCada equipe deve possuir exatamente 5 jogadores para uma partida 5v5.");
                return;
            }

            // Verificar jogadores da equipe 2
            List<Jogador> jogE2 = equipeDAO.listarJogadores(e2.getId());
            if (jogE2.size() != 5) {
                JOptionPane.showMessageDialog(this, "A equipe " + e2.getNome() + " possui " + jogE2.size() + " jogador(es).\nCada equipe deve possuir exatamente 5 jogadores para uma partida 5v5.");
                return;
            }

            List<Integer> idsE1 = new ArrayList<>();
            for (Jogador j : jogE1) idsE1.add(j.getId());

            List<Integer> idsE2 = new ArrayList<>();
            for (Jogador j : jogE2) idsE2.add(j.getId());

            partidaDAO.inserir(mapa.getId(), e1.getId(), e2.getId(), idsE1, idsE2);
            JOptionPane.showMessageDialog(this, "Partida agendada com sucesso!");
            carregarPartidas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao agendar partida: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnCriarPartidaActionPerformed

    private void btnFinalizarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarPartidaActionPerformed
        int linha = tblPartidas.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida agendada na tabela para finalizar.");
            return;
        }

        String status = tblPartidas.getValueAt(linha, 1).toString();
        if ("Finalizada".equals(status)) {
            JOptionPane.showMessageDialog(this, "Esta partida já foi finalizada.");
            return;
        }

        int idPartida = (int) tblPartidas.getValueAt(linha, 0);
        String nomeE1 = tblPartidas.getValueAt(linha, 4).toString();
        String nomeE2 = tblPartidas.getValueAt(linha, 5).toString();

        try {
            // Precisamos descobrir os IDs das equipes
            List<Partida> lista = partidaDAO.listar();
            Partida partida = null;
            for (Partida p : lista) {
                if (p.getId() == idPartida) {
                    partida = p;
                    break;
                }
            }

            if (partida == null) return;

            Object[] options = {nomeE1, nomeE2, "Cancelar"};
            int escolha = JOptionPane.showOptionDialog(this,
                    "Quem venceu a partida?",
                    "Finalizar Partida - Definir Vencedor",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (escolha == 0) { // Equipe 1 venceu
                partidaDAO.finalizar(idPartida, partida.getIdEquipe1(), partida.getIdEquipe2());
                JOptionPane.showMessageDialog(this, "Partida finalizada! Vencedor: " + nomeE1 + "\nELOs dos jogadores atualizados (+25 / -25).");
            } else if (escolha == 1) { // Equipe 2 venceu
                partidaDAO.finalizar(idPartida, partida.getIdEquipe2(), partida.getIdEquipe1());
                JOptionPane.showMessageDialog(this, "Partida finalizada! Vencedor: " + nomeE2 + "\nELOs dos jogadores atualizados (+25 / -25).");
            } else {
                return;
            }

            carregarPartidas();
            
            // Forçar atualização das outras telas se estiverem visíveis
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame instanceof MainFrame) {
                ((MainFrame) parentFrame).atualizarTodasAsTelas();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar partida: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnFinalizarPartidaActionPerformed

    private void btnExcluirPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirPartidaActionPerformed
        int linha = tblPartidas.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida na tabela para excluir.");
            return;
        }
        int idPartida = (int) tblPartidas.getValueAt(linha, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir a partida ID: " + idPartida + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                partidaDAO.deletar(idPartida);
                JOptionPane.showMessageDialog(this, "Partida excluída com sucesso!");
                carregarPartidas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir partida: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnExcluirPartidaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCriarPartida;
    private javax.swing.JButton btnExcluirPartida;
    private javax.swing.JButton btnFinalizarPartida;
    private javax.swing.JComboBox<com.torneio.model.Equipe> cmbEquipe1;
    private javax.swing.JComboBox<com.torneio.model.Equipe> cmbEquipe2;
    private javax.swing.JComboBox<com.torneio.model.Mapa> cmbMapa;
    private javax.swing.JLabel lblEquipe1;
    private javax.swing.JLabel lblEquipe2;
    private javax.swing.JLabel lblMapa;
    private javax.swing.JPanel pnlPartidaControls;
    private javax.swing.JPanel pnlPartidaForm;
    private javax.swing.JScrollPane scrollPaneTable;
    private javax.swing.JTable tblPartidas;
    // End of variables declaration//GEN-END:variables
}
