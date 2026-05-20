/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import java.util.Objects;

/**
 * Classe base che rappresenta un'entità animata all'interno del gioco Mount Forest.
 * Evita conflitti di sistema rinominandosi in Personaggio.
 * * @author sofy8
 */
public abstract class Personaggio {
    private final String nome;
    private Stanza stanzaCorrente;
    private int livelloAllerta;
    
    public Personaggio(String nome, Stanza stanzaIniziale) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il personaggio deve avere un nome valido.");
        }
        if (stanzaIniziale == null) {
            throw new IllegalArgumentException("Il personaggio deve iniziare in una stanza valida.");
        }
        this.nome = nome;
        this.stanzaCorrente = stanzaIniziale;
        this.livelloAllerta = 0;
    }
    
    // --- METODI DI MOVIMENTO ---

    /**
     * Sposta il personaggio in una nuova stanza.
     * @param nuovaStanza La stanza di destinazione.
     */
    public void muovi(Stanza nuovaStanza) {
        if (nuovaStanza == null) {
            throw new IllegalArgumentException("Impossibile muoversi in una stanza nulla.");
        }
        this.stanzaCorrente = nuovaStanza;
    }

    // --- GESTIONE STATO / ALLERTA (STEALTH) ---

    /**
     * Incrementa il livello di allerta del personaggio a causa di un rumore o evento.
     * @param valore L'intensità del rumore da aggiungere.
     */
    public void incrementaAllerta(int valore) {
        this.livelloAllerta += valore;
        if (this.livelloAllerta > 100) {
            this.livelloAllerta = 100; // Limite massimo di allerta (Inseguimento attivo)
        }
    }

    /**
     * Riduce gradualmente l'allerta se non avvengono altri stimoli (es. a fine turno).
     * @param valore Di quanto cala l'allerta.
     */
    public void decrementaAllerta(int valore) {
        this.livelloAllerta -= valore;
        if (this.livelloAllerta < 0) {
            this.livelloAllerta = 0;
        }
    }
    
    // --- GETTER & SETTER ---

    public String getNome() {
        return nome;
    }

    public Stanza getStanzaCorrente() {
        return stanzaCorrente;
    }

    public void setStanzaCorrente(Stanza stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }

    public int getLivelloAllerta() {
        return livelloAllerta;
    }

    public void setLivelloAllerta(int livelloAllerta) {
        if (livelloAllerta >= 0 && livelloAllerta <= 100) {
            this.livelloAllerta = livelloAllerta;
        }
    }

    // --- OVERRIDES STANDARD ---

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Personaggio)) {
            return false;
        }
        final Personaggio other = (Personaggio) obj;
        return Objects.equals(this.nome != null ? this.nome.toLowerCase() : null, 
                              other.nome != null ? other.nome.toLowerCase() : null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome != null ? nome.toLowerCase() : 0);
    }

    @Override
    public String toString() {
        return "Personaggio{" + "nome='" + nome + '\'' + ", stanzaCorrente=" + (stanzaCorrente != null ? stanzaCorrente.getNome() : "null") + ", livelloAllerta=" + livelloAllerta + '}';
    }
}
