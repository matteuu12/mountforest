/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */
// File: OggettoNonApribileException.java
public class OggettoNonApribileException extends Exception {
    public OggettoNonApribileException(String nomeOggetto) {
        super("Hai provato ad aprire " + nomeOggetto + ", ma non è qualcosa che si può aprire.");
    }
}
