/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */

public class OggettoNonPresenteException extends Exception {

    // Costruttore standard: passi solo il nome dell'oggetto
    public OggettoNonPresenteException(String nomeOggetto) {
        super("Non vedo nessun " + nomeOggetto + " qui nei paraggi.");
    }

    // Costruttore personalizzato: se vuoi scrivere un messaggio totalmente diverso
    public OggettoNonPresenteException(String messaggio, boolean personalizzato) {
        super(messaggio);
    }
}

