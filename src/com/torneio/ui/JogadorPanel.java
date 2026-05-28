package com.torneio.ui;

import com.torneio.dao.JogadorDAO;
import com.torneio.model.Jogador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class JogadorPanel extends javax.swing.JPanel {

    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private Jogador jogadorSelecionado = null;

    public JogadorPanel() {
        initComponents();
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Online", "Em Partida", "Offline" }));
        configurarTabela();
        if (!java.beans.Beans.isDesignTime()) {
            carregarJogadores();
        }
    }

    private void configurarTabela() {
        tblJogadores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tblJogadores.getSelectedRow();
                if (linha >= 0) {
                    try {
                        String nickname = tblJogadores.getValueAt(linha, 1).toString();
                        List<Jogador> lista = jogadorDAO.listar();
                        for (Jogador j : lista) {
                            if (j.getNickname().equals(nickname)) {
                                jogadorSelecionado = j;
                                txtNickname.setText(j.getNickname());
                                txtElo.setText(String.valueOf(j.getElo()));
                                cmbStatus.setSelectedItem(j.getStatus());

                                btnSalvar.setText("Salvar");
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do jogador: " + ex.getMessage());
                    }
                }
            }
        });
    }

    public void carregarJogadores() {
        DefaultTableModel model = (DefaultTableModel) tblJogadores.getModel();
        model.setRowCount(0);
        try {
            List<Jogador> lista = jogadorDAO.listar();
            int posicao = 1;
            for (Jogador j : lista) {
                model.addRow(new Object[]{
                    posicao++,
                    j.getNickname(),
                    j.getElo(),
                    j.getStatus()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar jogadores: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNickname.setText("");
        txtElo.setText("1000");
        cmbStatus.setSelectedIndex(0);
        jogadorSelecionado = null;
        tblJogadores.clearSelection();

        btnSalvar.setText("Adicionar"); // altera para diferenciação do usuario
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPaneTable = new javax.swing.JScrollPane();
        tblJogadores = new javax.swing.JTable();
        pnlForm = new javax.swing.JPanel();
        lblNickname = new javax.swing.JLabel();
        txtNickname = new javax.swing.JTextField();
        lblElo = new javax.swing.JLabel();
        txtElo = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        tblJogadores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Posição", "Nickname", "ELO", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPaneTable.setViewportView(tblJogadores);

        add(scrollPaneTable, java.awt.BorderLayout.CENTER);

        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados do Jogador"));
        pnlForm.setPreferredSize(new java.awt.Dimension(300, 300));

        lblNickname.setText("Nickname:");

        lblElo.setText("ELO:");

        txtElo.setText("1000");

        lblStatus.setText("Status:");

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Online", "Em Partida", "Offline" }));

        btnSalvar.setText("Adicionar");
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
                    .addComponent(txtNickname)
                    .addComponent(txtElo)
                    .addComponent(cmbStatus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlFormLayout.createSequentialGroup()
                        .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNickname)
                            .addComponent(lblElo)
                            .addComponent(lblStatus))
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
                .addComponent(lblNickname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNickname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblElo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtElo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(lblStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(pnlFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnNovo)
                    .addComponent(btnExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(pnlForm, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {
        limparCampos();
    }

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        String nickname = txtNickname.getText().trim();
        String eloStr = txtElo.getText().trim();
        String status = cmbStatus.getSelectedItem().toString();

        if (nickname.isEmpty() || eloStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
            return;
        }

        int elo;
        try {
            elo = Integer.parseInt(eloStr);
            if (elo < 0) {
                JOptionPane.showMessageDialog(this, "ELO não pode ser negativo.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ELO deve ser um número válido.");
            return;
        }

        try {
            if (jogadorSelecionado == null) {
                Jogador j = new Jogador(0, nickname, elo, status);
                jogadorDAO.inserir(j);
                JOptionPane.showMessageDialog(this, "Jogador cadastrado com sucesso!");
            } else {
                jogadorSelecionado.setNickname(nickname);
                jogadorSelecionado.setElo(elo);
                jogadorSelecionado.setStatus(status);
                jogadorDAO.atualizar(jogadorSelecionado);
                JOptionPane.showMessageDialog(this, "Jogador atualizado com sucesso!");
            }
            limparCampos();
            carregarJogadores();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar jogador: " + ex.getMessage());
        }
    }

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {
        if (jogadorSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador na tabela para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir " + jogadorSelecionado.getNickname() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                jogadorDAO.deletar(jogadorSelecionado.getId());
                JOptionPane.showMessageDialog(this, "Jogador excluído com sucesso!");
                limparCampos();
                carregarJogadores();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir jogador: " + ex.getMessage());
            }
        }
    }

    // declaraçao de variaveis
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel lblElo;
    private javax.swing.JLabel lblNickname;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JScrollPane scrollPaneTable;
    private javax.swing.JTable tblJogadores;
    private javax.swing.JTextField txtElo;
    private javax.swing.JTextField txtNickname;
}
