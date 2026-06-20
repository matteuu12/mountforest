/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.mycompany.mountforest.exceptions;

/**
 *
 * @author sofy8
 */
public class GameException extends Exception {
    public GameException(String messaggio) {
        super(messaggio);
    }
    public GameException(String messaggio, Throwable cause) {
        super(messaggio, cause);
    }
}