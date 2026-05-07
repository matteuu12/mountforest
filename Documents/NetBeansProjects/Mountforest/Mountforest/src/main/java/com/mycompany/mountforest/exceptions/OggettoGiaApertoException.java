/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */

// File: OggettoGiaApertoException.java
public class OggettoGiaApertoException extends GameException {
    public OggettoGiaApertoException(String nomeOggetto) {
        super(nomeOggetto + " è già spalancato.");
    }
}

