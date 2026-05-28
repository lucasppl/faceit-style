package com.torneio.ui;

import com.torneio.dao.MapaDAO;
import com.torneio.model.Mapa;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class MapaPanel extends javax.swing.JPanel {

    private final MapaDAO mapaDAO = new MapaDAO();
    private Mapa mapaSelecionado = null;

    public MapaPanel() {
        initComponents();
        configurarTabela();
        if (!java.beans.Beans.isDesignTime()) {
            carregarMapas();
        }
    }

    private void configurarTabela() {
        tblMapas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tblMapas.getSelectedRow();
                if (linha >= 0) {
                    try {
                        int id = (int) tblMapas.getValueAt(linha, 0);
                        List<Mapa> lista = mapaDAO.listar();
                        for (Mapa m : lista) {
                            if (m.getId() == id) {
                                mapaSelecionado = m;
                                txtNome.setText(m.getNomeMapa());
                                cmbTipo.setSelectedItem(m.getTipo());
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do mapa: " + ex.getMessage());
                    }
                }
            }
        });
    }

    public void carregarMapas() {
        DefaultTableModel model = (DefaultTableModel) tblMapas.getModel();
        model.setRowCount(0);
        try {
            List<Mapa> lista = mapaDAO.listar();
            for (Mapa m : lista) {
                model.addRow(new Object[]{
                    m.getId(),
                    m.getNomeMapa(),
                    m.getTipo()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar mapas: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        cmbTipo.setSelectedIndex(0);
        mapaSelecionado = null;
        tblMapas.clearSelection();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneTable = new javax.swing.JScrollPane();
        tblMapas = new javax.swing.JTable();
        pnlForm = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        tblMapas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome do Mapa", "Tipo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPaneTable.setViewportView(tblMapas);

        add(scrollPaneTable, java.awt.BorderLayout.CENTER);

        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Mapa"));
        pnlForm.setPreferredSize(new java.awt.Dimension(300, 300));

        lblNome.setText("Nome do Mapa:");

        lblTipo.setText("Tipo:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Defuse", "Resgate" }));

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnNovo.setText("Novo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFormLayout = new javax.swing.GroupLayout(pnlForm);
        pnlForm.setLayout(pnlFormLayout);
        pnlFormLayout.setHorizontalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome)
                    .addComponent(cmbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(lblTipo))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlFormLayout.setVerticalGroup(
            pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblTipo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pnlForm, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String nome = txtNome.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome do mapa.");
            return;
        }

        try {
            if (mapaSelecionado == null) {
                Mapa m = new Mapa(0, nome, tipo);
                mapaDAO.inserir(m);
                JOptionPane.showMessageDialog(this, "Mapa cadastrado com sucesso!");
            } else {
                mapaSelecionado.setNomeMapa(nome);
                mapaSelecionado.setTipo(tipo);
                mapaDAO.atualizar(mapaSelecionado);
                JOptionPane.showMessageDialog(this, "Mapa atualizado com sucesso!");
            }
            limparCampos();
            carregarMapas();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar mapa: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (mapaSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um mapa na tabela para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir " + mapaSelecionado.getNomeMapa() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                mapaDAO.deletar(mapaSelecionado.getId());
                JOptionPane.showMessageDialog(this, "Mapa excluído com sucesso!");
                limparCampos();
                carregarMapas();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir mapa: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JScrollPane scrollPaneTable;
    private javax.swing.JTable tblMapas;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
