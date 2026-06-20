package com.mycompany.mountforest.type;

/**
 * Rappresenta le minacce del gioco (Dr. Metzger o Pazienti).
 * @author sofy8
 */
public class Nemico extends Personaggio {

    private boolean inInseguimento;

    public Nemico(String nome, Stanza stanzaIniziale) {
        super(nome, stanzaIniziale);
        this.inInseguimento = false;
    }

    public boolean isInInseguimento() {
        return inInseguimento;
    }

    public void setInInseguimento(boolean inInseguimento) {
        this.inInseguimento = inInseguimento;
    }
    
    /**
     * Logica di intelligenza artificiale per aggiornare lo stato del nemico.
     * Verrà richiamata dal Controller ad ogni turno di gioco.
     * @param stanzaGiocatore
     */
    public void aggiornaStatoIA(Stanza stanzaGiocatore) {
        // Se l'allerta sale troppo (es. rumori), il nemico entra in modalità caccia
        if (this.getLivelloAllerta() > 75) {
            this.inInseguimento = true;
        } else if (this.getLivelloAllerta() == 0) {
            this.inInseguimento = false;
        }
    }
}