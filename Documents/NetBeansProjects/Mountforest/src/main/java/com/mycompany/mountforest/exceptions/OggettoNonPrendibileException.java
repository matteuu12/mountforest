/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */
// File: OggettoNonPrendibileException.java
public class OggettoNonPrendibileException extends GameException {
    public OggettoNonPrendibileException(String nomeOggetto) {
        super("Non puoi prendere " + nomeOggetto + ". Sembra inamovibile.");
    }
}
