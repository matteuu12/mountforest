/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author sofy8
 */
package com.mycompany.mountforest;

import com.mycompany.mountforest.gui.FinestraGioco;
import javax.swing.SwingUtilities;

public class MainApp {

    public static void main(String[] args) {
        // Garantisce che l'interfaccia grafica Swing venga avviata nel thread corretto
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Crea un'istanza della finestra di gioco
                    FinestraGioco gioco = new FinestraGioco();
                    
                    // La rende visibile sullo schermo
                    gioco.setVisible(true);
                    
                } catch (Exception e) {
                    System.err.println("Errore durante l'avvio del gioco: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}