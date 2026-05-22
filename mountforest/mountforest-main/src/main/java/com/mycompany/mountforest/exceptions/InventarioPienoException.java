/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */
public class InventarioPienoException extends GameException {
    public InventarioPienoException() {
        super("L'inventario è pieno! Non puoi portare altro.");
    }
    
    public InventarioPienoException(String messaggio) {
        super(messaggio);
    }
}
