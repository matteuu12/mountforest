/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */
// File: OggettoGiaChiusoException.java
public class OggettoGiaChiusoException extends GameException {
    public OggettoGiaChiusoException(String nomeOggetto) {
        super(nomeOggetto + " è già chiuso.");
    }
}
