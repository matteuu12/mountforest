/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import com.mycompany.mountforest.exceptions.*;
import java.util.*;

/**
 * Versione avanzata di Stanza per Mount Forest.
 * Include gestione passaggi bloccati e logica di esplorazione.
 * 
 * @author sofy8
 */
public class Stanza {

    private final String nome;
    private final String descrizione;
    private boolean visibile;
    private boolean visitata = false;
    private boolean eventoAttivato = false;

    // Usiamo una mappa per le direzioni: più flessibile di 4 variabili singole
    private final Map<String, Stanza> adiacenze = new HashMap<>();
    // Mappa per i blocchi: Direzione -> Messaggio che spiega perché è bloccata
    private final Map<String, String> passaggiBloccati = new HashMap<>();
    
    private final List<ADVObject> oggetti_per_terra;

    public Stanza(String nome, String descrizione, boolean visibile) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome della stanza non può essere nullo o vuoto.");
        }
        if (descrizione == null || descrizione.trim().isEmpty()) {
            throw new IllegalArgumentException("La descrizione della stanza non può essere nulla.");
        }
        
        this.nome = nome;
        this.descrizione = descrizione;
        this.visibile = visibile;
        this.oggetti_per_terra = new ArrayList<>();
    }

    // --- GESTIONE MOVIMENTO ---
    
    public boolean deveAttivareEvento() {
        if (!eventoAttivato) {
            eventoAttivato = true;
            return true;
        }
        return false;
    }
    
    public void impostaAdiacenza(String direzione, Stanza stanza) {
        if (stanza == this) throw new IllegalArgumentException("Una stanza non può essere adiacente a se stessa!");
        adiacenze.put(direzione.toLowerCase(), stanza);
    }

    /**
     * Blocca una direzione (es. "nord") con un messaggio (es. "La porta è chiusa a chiave").
     * @param direzione
     * @param messaggio
     */
    public void bloccaPassaggio(String direzione, String messaggio) {
        passaggiBloccati.put(direzione.toLowerCase(), messaggio);
    }

    public void sbloccaPassaggio(String direzione) {
        passaggiBloccati.remove(direzione.toLowerCase());
    }

    public Stanza getAdiacente(String direzione) {
        return adiacenze.get(direzione.toLowerCase());
    }

    public String getMessaggioBlocco(String direzione) {
        return passaggiBloccati.get(direzione.toLowerCase());
    }

    // --- GESTIONE OGGETTI ---

    public void addObject(ADVObject oggetto) {
        if (oggetto == null) throw new NullPointerException("Oggetto nullo.");
        oggetti_per_terra.add(oggetto);
    }

    /**
     * Cerca un oggetto nella stanza per nome (utile per l'input dell'utente).
     * @param nomeOggetto
     * @return 
     */
    public Optional<ADVObject> cercaOggetto(String nomeOggetto) {
        return oggetti_per_terra.stream()
                .filter(o -> o.getNome().equalsIgnoreCase(nomeOggetto))
                .findFirst();
    }

    public void removeObject(ADVObject oggetto) throws OggettoNonPresenteException {
        if (!oggetti_per_terra.contains(oggetto)) {
            throw new OggettoNonPresenteException(oggetto.getNome());
        }
        oggetti_per_terra.remove(oggetto);
    }

    // --- LOGICA DI GIOCO / NARRAZIONE ---

    public String guarda() {
        if (!visibile) {
            return "È troppo buio per vedere qualcosa.";
        }

        StringBuilder risultato = new StringBuilder();
        
        // Se è la prima volta che entriamo, potremmo voler dare più enfasi
        if (!visitata) {
            risultato.append("[NUOVA AREA]\n");
            visitata = true;
        }

        risultato.append(nome).append("\n");
        risultato.append(descrizione).append("\n");

        if (!oggetti_per_terra.isEmpty()) {
            risultato.append("\nA terra vedi: ");
            for (ADVObject oggetto : oggetti_per_terra) {
                risultato.append("[").append(oggetto.getNome()).append("] ");
            }
        }
        
        // Elenco uscite visibili
        if (!adiacenze.isEmpty()) {
            risultato.append("\nUscite: ").append(String.join(", ", adiacenze.keySet()));
        }

        return risultato.toString();
    }

    // --- GETTER & UTILITY ---

    public String getNome() { return nome; }
    public boolean isVisibile() { return visibile; }
    public void setVisibile(boolean visibile) { this.visibile = visibile; }
    public List<ADVObject> getOggetti() { return oggetti_per_terra; }
    public boolean isVisitata() { return visitata; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Stanza)) return false;
        Stanza altra = (Stanza) obj;
        return nome.equals(altra.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
