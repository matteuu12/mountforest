package com.mycompany.mountforest.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinestraGioco extends JFrame {

    // Componenti dell'interfaccia grafica
    private final JTextArea areaTesto;
    private JTextField campoInput;
    private final JLabel labelStato;
    // Variabili di stato del gioco
    private final int capitoloCorrente = 1;

    public FinestraGioco() {
        // Configurazione Finestra principale
        setTitle("Mount Forest - Avventura Testuale");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- TOP BAR (Stato Allerta) ---
        JPanel pannelloStato = new JPanel(new BorderLayout());
        pannelloStato.setBackground(new Color(30, 30, 30));
        
        labelStato = new JLabel(" Caricamento in corso... ");
        labelStato.setForeground(Color.RED);
        labelStato.setFont(new Font("Consolas", Font.BOLD, 14));
        pannelloStato.add(labelStato, BorderLayout.WEST);
        
        add(pannelloStato, BorderLayout.NORTH);

        // --- AREA TESTO CENTRALE (Console di gioco) ---
        areaTesto = new JTextArea();
        areaTesto.setEditable(false);
        areaTesto.setBackground(Color.BLACK);
        areaTesto.setForeground(Color.GREEN); // Stile retro/hacker
        areaTesto.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaTesto.setLineWrap(true);
        areaTesto.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(areaTesto);
        add(scrollPane, BorderLayout.CENTER);

        // --- CAMPO INPUT IN BASSO (Comandi giocatore) ---
        campoInput = new JTextField();
        campoInput.setBackground(new Color(20, 20, 20));
        campoInput.setForeground(Color.WHITE);
        campoInput.setFont(new Font("Consolas", Font.PLAIN, 14));
        campoInput.setCaretColor(Color.WHITE);
        
        add(campoInput, BorderLayout.SOUTH);

        // --- INIZIALIZZAZIONE LOGICA DI GIOCO ---
        inizializzaGioco();

        // --- GESTIONE INVIO COMANDI ---
        campoInput.addActionListener(new ActionListener() {
            public void ActionPerformed(ActionEvent e) {
                String comando = campoInput.getText().trim();
                if (!comando.isEmpty()) {
                    elaboraComando(comando);
                    campoInput.setText(""); // Svuota il campo dopo l'invio
                }
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }

    private void inizializzaGioco() {
        
        // Inizializza gli oggetti del gioco (Esempio)    
        
        // Messaggio di Benvenuto/Introduzione basato sulla lore del tuo manicomio
        areaTesto.append("MOUNT FOREST - IL MANICOMIO ABBANDONATO\n");
        areaTesto.append("=========================================\n");
        areaTesto.append("Sei un ricercatore e giornalista. Ti trovi all'interno del manicomio\n");
        areaTesto.append("Mountforest, chiuso da anni dopo un terribile incendio.\n");
        areaTesto.append("Trova informazioni, ma fai attenzione... i corpi sono morti, le coscienze no.\n\n");
        areaTesto.append("Cosa vuoi fare? (Scrivi un comando, es. 'guarda', 'nord')\n\n");
        
        // Aggiorna la barra di stato iniziale
        aggiornaStato("Livello Allerta: Basso | Capitolo: " + capitoloCorrente);
    }

    private void elaboraComando(String comando) {
        // Stampa sul monitor quello che ha scritto il giocatore
        areaTesto.append("> " + comando + "\n");
        
        // Logica di controllo basilare del testo (da espandere con il tuo Parser/Comandi)
        String comandoMinuscolo = comando.toLowerCase();
        
        if (comandoMinuscolo.equals("guarda")) {
            areaTesto.append("Ti guardi intorno. Le pareti della stanza sono annerite dal fuoco e l'aria è pesante.\n");
        } else if (comandoMinuscolo.equals("aiuto")) {
            areaTesto.append("Comandi disponibili: guarda, aiuto, esci\n");
        } else if (comandoMinuscolo.equals("esci")) {
            System.exit(0);
        } else {
            areaTesto.append("Non capisco il comando: '" + comando + "'. Scrivi 'aiuto' per i comandi.\n");
        }
        
        areaTesto.append("\n");
    }

    public void aggiornaStato(String testo) {
        labelStato.setText(" " + testo + " ");
    }
}