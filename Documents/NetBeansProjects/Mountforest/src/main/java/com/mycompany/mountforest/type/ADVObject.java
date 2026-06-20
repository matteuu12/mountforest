/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mountforest.type;

import com.mycompany.mountforest.exceptions.*;
import java.util.*;

/**
 * Rappresenta un oggetto nel mondo di gioco.
 * Può essere un oggetto semplice, un contenitore o un oggetto bloccato.
 * 
 * @author sofy8
 */
public class ADVObject {

    private final String nome;
    private String descrizione;
    private final Set<String> alias = new HashSet<>();
    
    private boolean apribile = false;
    private boolean aperto = false;
    private boolean prendibile = true;
    private final String idSblocco = null;
    
    // NUOVO: Lista di oggetti contenuti (se l'oggetto è un contenitore, es. un baule)
    private final List<ADVObject> contenuto = new ArrayList<>();

    public ADVObject(String nome, String descrizione) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("L'oggetto deve avere un nome!");
        }
        this.nome = nome;
        this.descrizione = descrizione;
    }

    // --- LOGICA ALIAS ---
    
    /**
     * Aggiunge un sinonimo per l'oggetto (es. nome "Spada", alias "arma").
     * @param nomeAlias
     */
    public void addAlias(String nomeAlias) {
        if (nomeAlias != null) {
            this.alias.add(nomeAlias.toLowerCase());
        }
    }

    public boolean matches(String nomeInput) {
        return nome.equalsIgnoreCase(nomeInput) || alias.contains(nomeInput.toLowerCase());
    }

    // --- LOGICA CONTENITORE (NUOVO) ---

    public void aggiungiContenuto(ADVObject obj) {
        this.contenuto.add(obj);
    }

    public List<ADVObject> getContenuto() {
        return contenuto;
    }

    /**
     * Se l'oggetto è aperto, mostra cosa c'è dentro nella descrizione.
     * @return 
     */
    public String getDescrizioneCompleta() {
        if (apribile && aperto && !contenuto.isEmpty()) {
            StringBuilder sb = new StringBuilder(descrizione);
            sb.append("\nDentro vedi: ");
            for (ADVObject obj : contenuto) {
                sb.append("[").append(obj.getNome()).append("] ");
            }
            return sb.toString();
        }
        return descrizione;
    }

    // --- LOGICA APERTURA / CHIUSURA ---
    
    public void sbloccaEApri(String chiaveFornita) throws OggettoBloccatoException {
        if (idSblocco != null && !idSblocco.equals(chiaveFornita)) {
            throw new OggettoBloccatoException("L'oggetto è chiuso a chiave o serve un codice."); // 
        }
        this.aperto = true;
    }
    
    public void apri() throws OggettoNonApribileException, OggettoGiaApertoException {
        if (!apribile) {
            throw new OggettoNonApribileException("Non puoi aprire " + nome + ".");
        }
        if (aperto) {
            throw new OggettoGiaApertoException(nome + " è già aperto.");
        }
        this.aperto = true;
    }

    public void chiudi() throws OggettoNonApribileException, OggettoGiaChiusoException {
        if (!apribile) {
            throw new OggettoNonApribileException("Non puoi chiudere questo.");
        }
        if (!aperto) {
            throw new OggettoGiaChiusoException(nome + " è già chiuso.");
        }
        this.aperto = false;
    }

    // --- GETTER & SETTER ---

    public String getNome() { return nome; }
    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    
    public boolean isApribile() { return apribile; }
    public void setApribile(boolean apribile) { this.apribile = apribile; }
    
    public boolean isAperto() { return aperto; }
    public void setAperto(boolean aperto) { this.aperto = aperto; }
    
    public boolean isPrendibile() { return prendibile; }
    public void setPrendibile(boolean prendibile) { this.prendibile = prendibile; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ADVObject)) return false;
        ADVObject other = (ADVObject) obj;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
