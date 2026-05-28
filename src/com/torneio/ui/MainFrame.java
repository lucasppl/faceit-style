package com.torneio.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        initComponents();
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Listener para atualizar os dados sempre que trocar de aba
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                atualizarTodasAsTelas();
            }
        });
    }

    public void atualizarTodasAsTelas() {
        if (jogadorPanel != null) {
            jogadorPanel.carregarJogadores();
        }
        if (equipePanel != null) {
            equipePanel.carregarEquipes();
            equipePanel.carregarJogadoresDisponiveis();
        }
        if (mapaPanel != null) {
            mapaPanel.carregarMapas();
        }
        if (partidaPanel != null) {
            partidaPanel.carregarPartidas();
            partidaPanel.carregarCombos();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        jogadorPanel = new com.torneio.ui.JogadorPanel();
        equipePanel = new com.torneio.ui.EquipePanel();
        mapaPanel = new com.torneio.ui.MapaPanel();
        partidaPanel = new com.torneio.ui.PartidaPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Plataforma de Torneios - Estilo FACEIT");

        tabbedPane.addTab("Jogadores", jogadorPanel);
        tabbedPane.addTab("Equipes", equipePanel);
        tabbedPane.addTab("Mapas", mapaPanel);
        tabbedPane.addTab("Partidas", partidaPanel);

        getContentPane().add(tabbedPane, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // cria e mostra o formulario
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // declaração de variaveis
    private com.torneio.ui.EquipePanel equipePanel;
    private com.torneio.ui.JogadorPanel jogadorPanel;
    private com.torneio.ui.MapaPanel mapaPanel;
    private com.torneio.ui.PartidaPanel partidaPanel;
    private javax.swing.JTabbedPane tabbedPane;
}
