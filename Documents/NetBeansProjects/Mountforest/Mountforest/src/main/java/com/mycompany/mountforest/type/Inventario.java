/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import com.mycompany.mountforest.exceptions.*;
import java.util.*;

/**
 * Gestisce gli oggetti trasportati dal giocatore.
 * 
 * @author sofy8
 */
public class Inventario {

    private final List<ADVObject> oggetti;
    private final int MAX_SPAZIO;

    public Inventario() {
        this.oggetti = new ArrayList<>();
        this.MAX_SPAZIO = 8;
    }

    // --- METODI CORE CON ECCEZIONI ---

    /**
     * Aggiunge un oggetto all'inventario.
     * @throws InventarioPienoException se superi il limite di 8 oggetti.
     */
    public void aggiungi(ADVObject oggetto) throws InventarioPienoException {
        if (oggetti.size() >= MAX_SPAZIO) {
            throw new InventarioPienoException("L'inventario è pieno! Non puoi portare altro.");
        }
        this.oggetti.add(oggetto);
    }

    /**
     * Rimuove un oggetto (es. quando lo lasci a terra o lo usi).
     * @throws OggettoNonPresenteException se provi a rimuovere qualcosa che non hai.
     */
    public void rimuovi(ADVObject oggetto) throws OggettoNonPresenteException {
        if (!oggetti.contains(oggetto)) {
            throw new OggettoNonPresenteException(oggetto.getNome() + " non è nel tuo inventario.");
        }
        this.oggetti.remove(oggetto);
    }

    // --- LOGICA DI RICERCA POTENZIATA ---

    /**
     * Controlla se un oggetto è presente cercando sia per nome che per alias.
     */
    public boolean contiene(String nomeInput) {
        return oggetti.stream()
                .anyMatch(obj -> obj.matches(nomeInput));
    }

    /**
     * Recupera l'oggetto fisico dall'inventario tramite stringa.
     * Utile per i comandi tipo "usa [nome]".
     */
    public Optional<ADVObject> getOggetto(String nomeInput) {
        return oggetti.stream()
                .filter(obj -> obj.matches(nomeInput))
                .findFirst();
    }

    // --- METODI AGGIUNTIVI CONSIGLIATI ---

    /**
     * Svuota l'inventario (es. se il giocatore viene derubato nella foresta).
     */
    public void svuota() {
        this.oggetti.clear();
    }

    /**
     * Restituisce una stringa leggibile di cosa possiede il giocatore.
     */
    public String visualizza() {
        if (oggetti.isEmpty()) {
            return "Il tuo inventario è vuoto.";
        }
        StringBuilder sb = new StringBuilder("Nel tuo zaino hai:\n");
        for (ADVObject obj : oggetti) {
            sb.append("- ").append(obj.getNome()).append("\n");
        }
        return sb.toString();
    }

    // --- GETTER STANDARD ---

    public int getNumeroOggetti() { return oggetti.size(); }
    public int getMaxSpazio() { return MAX_SPAZIO; }
    public List<ADVObject> getListaOggetti() { return oggetti; }
}
