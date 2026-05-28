package com.torneio.ui;

import com.torneio.dao.EquipeDAO;
import com.torneio.dao.JogadorDAO;
import com.torneio.model.Equipe;
import com.torneio.model.Jogador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class EquipePanel extends javax.swing.JPanel {

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private Equipe equipeSelecionada = null;

    public EquipePanel() {
        initComponents();
        configurarTabelas();
        if (!java.beans.Beans.isDesignTime()) {
            carregarEquipes();
            carregarJogadoresDisponiveis();
        }
    }

    private void configurarTabelas() {
        tblEquipes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tblEquipes.getSelectedRow();
                if (linha >= 0) {
                    try {
                        int id = (int) tblEquipes.getValueAt(linha, 0);
                        List<Equipe> lista = equipeDAO.listar();
                        for (Equipe eq : lista) {
                            if (eq.getId() == id) {
                                equipeSelecionada = eq;
                                txtNome.setText(eq.getNome());
                                txtTag.setText(eq.getTag());
                                carregarMembros();
                                break;
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes da equipe: " + ex.getMessage());
                    }
                }
            }
        });
    }

    public void carregarEquipes() {
        DefaultTableModel model = (DefaultTableModel) tblEquipes.getModel();
        model.setRowCount(0);
        try {
            List<Equipe> lista = equipeDAO.listar();
            for (Equipe e : lista) {
                model.addRow(new Object[]{
                    e.getId(),
                    e.getNome(),
                    e.getTag()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar equipes: " + ex.getMessage());
        }
    }

    private void carregarMembros() {
        DefaultTableModel model = (DefaultTableModel) tblMembros.getModel();
        model.setRowCount(0);
        if (equipeSelecionada == null) return;
        try {
            List<Jogador> lista = equipeDAO.listarJogadores(equipeSelecionada.getId());
            for (Jogador j : lista) {
                model.addRow(new Object[]{
                    j.getId(),
                    j.getNickname(),
                    j.getElo()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar membros da equipe: " + ex.getMessage());
        }
    }

    public void carregarJogadoresDisponiveis() {
        cmbJogadoresDisponiveis.removeAllItems();
        try {
            List<Jogador> lista = jogadorDAO.listar();
            for (Jogador j : lista) {
                cmbJogadoresDisponiveis.addItem(j);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar combo de jogadores: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtTag.setText("");
        equipeSelecionada = null;
        tblEquipes.clearSelection();
        ((DefaultTableModel) tblMembros.getModel()).setRowCount(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        scrollPaneEquipes = new javax.swing.JScrollPane();
        tblEquipes = new javax.swing.JTable();
        pnlRight = new javax.swing.JPanel();
        pnlEquipeForm = new javax.swing.JPanel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblTag = new javax.swing.JLabel();
        txtTag = new javax.swing.JTextField();
        btnSalvar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        pnlMembros = new javax.swing.JPanel();
        scrollPaneMembros = new javax.swing.JScrollPane();
        tblMembros = new javax.swing.JTable();
        pnlMembrosControls = new javax.swing.JPanel();
        cmbJogadoresDisponiveis = new javax.swing.JComboBox<>();
        btnAdicionarMembro = new javax.swing.JButton();
        btnRemoverMembro = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        splitPane.setDividerLocation(400);

        tblEquipes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome da Equipe", "TAG"
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
        scrollPaneEquipes.setViewportView(tblEquipes);

        splitPane.setLeftComponent(scrollPaneEquipes);

        pnlRight.setLayout(new java.awt.BorderLayout());

        pnlEquipeForm.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da Equipe"));
        pnlEquipeForm.setPreferredSize(new java.awt.Dimension(350, 160));

        lblNome.setText("Nome da Equipe:");

        lblTag.setText("TAG:");

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

        javax.swing.GroupLayout pnlEquipeFormLayout = new javax.swing.GroupLayout(pnlEquipeForm);
        pnlEquipeForm.setLayout(pnlEquipeFormLayout);
        pnlEquipeFormLayout.setHorizontalGroup(
            pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEquipeFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlEquipeFormLayout.createSequentialGroup()
                        .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlEquipeFormLayout.createSequentialGroup()
                                .addComponent(lblTag)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtTag)))
                    .addGroup(pnlEquipeFormLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlEquipeFormLayout.setVerticalGroup(
            pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEquipeFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(lblTag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnlEquipeFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnSalvar)
                    .addComponent(btnExcluir))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlRight.add(pnlEquipeForm, java.awt.BorderLayout.NORTH);

        pnlMembros.setBorder(javax.swing.BorderFactory.createTitledBorder("Membros da Equipe"));
        pnlMembros.setLayout(new java.awt.BorderLayout());

        tblMembros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nickname", "ELO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
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
        scrollPaneMembros.setViewportView(tblMembros);

        pnlMembros.add(scrollPaneMembros, java.awt.BorderLayout.CENTER);

        pnlMembrosControls.setPreferredSize(new java.awt.Dimension(350, 50));
        pnlMembrosControls.setLayout(new java.awt.FlowLayout());

        cmbJogadoresDisponiveis.setPreferredSize(new java.awt.Dimension(150, 25));
        pnlMembrosControls.add(cmbJogadoresDisponiveis);

        btnAdicionarMembro.setText("Adicionar");
        btnAdicionarMembro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarMembroActionPerformed(evt);
            }
        });
        pnlMembrosControls.add(btnAdicionarMembro);

        btnRemoverMembro.setText("Remover");
        btnRemoverMembro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverMembroActionPerformed(evt);
            }
        });
        pnlMembrosControls.add(btnRemoverMembro);

        pnlMembros.add(pnlMembrosControls, java.awt.BorderLayout.SOUTH);

        pnlRight.add(pnlMembros, java.awt.BorderLayout.CENTER);

        splitPane.setRightComponent(pnlRight);

        add(splitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        String nome = txtNome.getText().trim();
        String tag = txtTag.getText().trim();

        if (nome.isEmpty() || tag.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
            return;
        }

        try {
            if (equipeSelecionada == null) {
                Equipe eq = new Equipe(0, nome, tag);
                equipeDAO.inserir(eq);
                JOptionPane.showMessageDialog(this, "Equipe cadastrada com sucesso!");
            } else {
                equipeSelecionada.setNome(nome);
                equipeSelecionada.setTag(tag);
                equipeDAO.atualizar(equipeSelecionada);
                JOptionPane.showMessageDialog(this, "Equipe atualizada com sucesso!");
            }
            limparCampos();
            carregarEquipes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar equipe: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (equipeSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma equipe na tabela para excluir.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir " + equipeSelecionada.getNome() + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                equipeDAO.deletar(equipeSelecionada.getId());
                JOptionPane.showMessageDialog(this, "Equipe excluída com sucesso!");
                limparCampos();
                carregarEquipes();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir equipe: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAdicionarMembroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarMembroActionPerformed
        if (equipeSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma equipe para gerenciar membros.");
            return;
        }
        Jogador jog = (Jogador) cmbJogadoresDisponiveis.getSelectedItem();
        if (jog == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador para adicionar.");
            return;
        }
        try {
            // Verificar quantos jogadores já existem na equipe
            List<Jogador> membros = equipeDAO.listarJogadores(equipeSelecionada.getId());
            if (membros.size() >= 5) {
                JOptionPane.showMessageDialog(this, "A equipe já possui o limite máximo de 5 jogadores para torneio.");
                // Mas vamos permitir adicionar de qualquer jeito ou dar um aviso? O banco e o DAO 5v5 recomendam exatamente 5.
                // Daremos apenas um aviso visual, mas permitimos continuar ou barramos? Vamos barrar para garantir que seja exatamente 5.
            }
            equipeDAO.adicionarJogador(equipeSelecionada.getId(), jog.getId());
            carregarMembros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar membro: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAdicionarMembroActionPerformed

    private void btnRemoverMembroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverMembroActionPerformed
        if (equipeSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma equipe.");
            return;
        }
        int linha = tblMembros.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um membro na tabela abaixo para remover.");
            return;
        }
        int idJogador = (int) tblMembros.getValueAt(linha, 0);
        try {
            equipeDAO.removerJogador(equipeSelecionada.getId(), idJogador);
            carregarMembros();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover membro: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnRemoverMembroActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarMembro;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnRemoverMembro;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<com.torneio.model.Jogador> cmbJogadoresDisponiveis;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTag;
    private javax.swing.JPanel pnlEquipeForm;
    private javax.swing.JPanel pnlMembros;
    private javax.swing.JPanel pnlMembrosControls;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JScrollPane scrollPaneEquipes;
    private javax.swing.JScrollPane scrollPaneMembros;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JTable tblEquipes;
    private javax.swing.JTable tblMembros;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTag;
    // End of variables declaration//GEN-END:variables
}
