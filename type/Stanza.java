/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import java.util.*;

/**
 *
 * @author sofy8
 */
public class Stanza {

    private String nome;
    private String descrizione;
    private boolean visibile;

    private Stanza nord, sud, est, ovest;
    private final List<ADVObject> oggetti_per_terra;

    // COSTRUTTORE
    public Stanza(String nome, String descrizione, boolean visibile) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.visibile = visibile;
        this.oggetti_per_terra = new ArrayList<>();
    }

    // GETTER
    public String getNome() {
        return nome;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public Stanza getNord() { return nord; }
    public Stanza getSud() { return sud; }
    public Stanza getEst() { return est; }
    public Stanza getOvest() { return ovest; }

    public List<ADVObject> getOggetti() {
        return oggetti_per_terra;
    }

    // DESCRIZIONE (CON LOGICA GIOCO)
    public String getDescrizione() {

        if (!visibile) {
            return "È troppo buio per vedere qualcosa. Forse dovresti accendere una torcia.";
        }

        String risultato = descrizione;

        if (!oggetti_per_terra.isEmpty()) {
            risultato += "\nA terra vedi: ";
            for (ADVObject oggetto : oggetti_per_terra) {
                risultato += oggetto.getNome() + " ";
            }
        }

        return risultato;
    }

    // SETTER
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setVisibile(boolean visibile) {
        this.visibile = visibile;
    }

    public void setNord(Stanza stanza) {
        this.nord = stanza;
    }

    public void setSud(Stanza stanza) {
        this.sud = stanza;
    }

    public void setEst(Stanza stanza) {
        this.est = stanza;
    }

    public void setOvest(Stanza stanza) {
        this.ovest = stanza;
    }

    // OGGETTI
    public void addObject(ADVObject oggetto) {
        oggetti_per_terra.add(oggetto);
    }

    public void removeObject(ADVObject oggetto) {
        oggetti_per_terra.remove(oggetto);
    }

    // EQUALS CORRETTO
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stanza)) return false;

        Stanza altra = (Stanza) obj;
        return nome.equals(altra.nome);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.nome);
        hash = 17 * hash + Objects.hashCode(this.descrizione);
        hash = 17 * hash + (this.visibile ? 1 : 0);
        hash = 17 * hash + Objects.hashCode(this.sud);
        hash = 17 * hash + Objects.hashCode(this.est);
        hash = 17 * hash + Objects.hashCode(this.ovest);
        hash = 17 * hash + Objects.hashCode(this.oggetti_per_terra);
        return hash;
    }
}
