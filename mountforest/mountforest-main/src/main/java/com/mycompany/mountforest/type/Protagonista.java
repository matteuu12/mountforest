/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;


/**
 * Rappresenta il protagonista (ricercatore/giornalista) comandato dal giocatore.
 * Estende correttamente la classe astratta Personaggio.
 * @author sofy8
 */
public class Protagonista extends Personaggio {

    private final Inventario inventario;

    public Protagonista(String nome, Stanza stanzaIniziale) {
        // La chiamata a super() DEVE essere la prima istruzione del costruttore
        super(nome, stanzaIniziale); 
        this.inventario = new Inventario(); 
    }

    public Inventario getInventario() {
        return inventario;
    }
    
    @Override
    public String getNome(){
        return nome;
    }
}