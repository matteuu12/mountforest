package com.mycompany.mountforest.database;

import com.mycompany.mountforest.type.Protagonista;
import com.mycompany.mountforest.type.Nemico;
import com.mycompany.mountforest.type.Stanza;
import com.mycompany.mountforest.type.ADVObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce il salvataggio e caricamento interfacciandosi con le classi reali del progetto.
 * @author matteuu12
 */
public class ServizioSalvataggio {

    private static void svuotaSalvataggiPrecedenti(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM partita");
        }
    }

    /**
     * SALVATAGGIO: Sincronizzato con il Modello (Stanza, Protagonista, Nemico).
     * @param giocatore
     * @param metzger
     * @param capitolo
     * @param tutteLeStanze
     * @return 
     */
    public static boolean salvaPartita(Protagonista giocatore, Nemico metzger, int capitolo, List<Stanza> tutteLeStanze) {
        String queryPartita = "INSERT INTO partita (nome_giocatore, stanza_corrente, livello_allerta_metzger, capitolo_corrente) VALUES (?, ?, ?, ?);";
        String queryInventario = "INSERT INTO inventario_salvato (id_partita, nome_oggetto) VALUES (?, ?);";
        String queryStanze = "INSERT INTO stato_stanze (id_partita, nome_stanza, is_visibile, is_visitata) VALUES (?, ?, ?, ?);";

        try (Connection conn = DatabaseManager.getConnection()) {
            conn.setAutoCommit(false);

            try {
                svuotaSalvataggiPrecedenti(conn);

                int idPartita = -1;
                try (PreparedStatement pstmt = conn.prepareStatement(queryPartita, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, giocatore.getNome()); 
                    pstmt.setString(2, giocatore.getStanzaCorrente().getNome()); 
                    pstmt.setInt(3, metzger.getLivelloAllerta()); 
                    pstmt.setInt(4, capitolo);
                    pstmt.executeUpdate();

                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            idPartita = rs.getInt(1);
                        }
                    }
                }

                if (idPartita == -1) {
                    throw new SQLException("Impossibile generare l'ID partita.");
                }

                // Salvataggio Inventario (getListaOggetti() e getNome())
                if (giocatore.getInventario() != null && giocatore.getInventario().getListaOggetti() != null) {
                    try (PreparedStatement pstmtInv = conn.prepareStatement(queryInventario)) {
                        for (ADVObject obj : giocatore.getInventario().getListaOggetti()) {
                            pstmtInv.setInt(1, idPartita);
                            pstmtInv.setString(2, obj.getNome()); 
                            pstmtInv.addBatch();
                        }
                        pstmtInv.executeBatch();
                    }
                }

                // Salvataggio stato delle Stanze (isVisibile() e isVisitata())
                if (tutteLeStanze != null) {
                    try (PreparedStatement pstmtStanze = conn.prepareStatement(queryStanze)) {
                        for (Stanza stanza : tutteLeStanze) {
                            pstmtStanze.setInt(1, idPartita);
                            pstmtStanze.setString(2, stanza.getNome());
                            pstmtStanze.setBoolean(3, stanza.isVisibile()); 
                            pstmtStanze.setBoolean(4, stanza.isVisitata()); 
                            pstmtStanze.addBatch();
                        }
                        pstmtStanze.executeBatch();
                    }
                }

                conn.commit();
                System.out.println("[DB] Salvataggio completato con successo!");
                return true;

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("[DB ERRORE] Errore durante il salvataggio: " + e.getMessage());
            return false;
        }
    }

    /**
     * CARICAMENTO: Estrae i dati dal database e riallinea i campi delle stanze.
     * @return 
     */
    public static DatiCaricati caricaPartita() {
        String queryPartita = "SELECT id_partita, nome_giocatore, stanza_corrente, livello_allerta_metzger, capitolo_corrente FROM partita LIMIT 1;";
        String queryInventario = "SELECT nome_oggetto FROM inventario_salvato WHERE id_partita = ?;";
        String queryStanze = "SELECT nome_stanza, is_visibile, is_visitata FROM stato_stanze WHERE id_partita = ?;";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rsPartita = stmt.executeQuery(queryPartita)) {

            if (rsPartita.next()) {
                DatiCaricati dati = new DatiCaricati();
                int idPartita = rsPartita.getInt("id_partita");
                dati.setNomeGiocatore(rsPartita.getString("nome_giocatore"));
                dati.setNomeStanzaCorrente(rsPartita.getString("stanza_corrente"));
                dati.setLivelloAllertaMetzger(rsPartita.getInt("livello_allerta_metzger"));
                dati.setCapitoloCorrente(rsPartita.getInt("capitolo_corrente"));

                try (PreparedStatement pstmtInv = conn.prepareStatement(queryInventario)) {
                    pstmtInv.setInt(1, idPartita);
                    try (ResultSet rsInv = pstmtInv.executeQuery()) {
                        while (rsInv.next()) {
                            dati.getOggettiInventario().add(rsInv.getString("nome_oggetto"));
                        }
                    }
                }

                try (PreparedStatement pstmtStanze = conn.prepareStatement(queryStanze)) {
                    pstmtStanze.setInt(1, idPartita);
                    try (ResultSet rsStanze = pstmtStanze.executeQuery()) {
                        while (rsStanze.next()) {
                            StanzaSalvata ss = new StanzaSalvata(
                                rsStanze.getString("nome_stanza"),
                                rsStanze.getBoolean("is_visibile"),
                                rsStanze.getBoolean("is_visitata")
                            );
                            dati.getStanzeSalvate().add(ss);
                        }
                    }
                }

                return dati;
            }

        } catch (SQLException e) {
            System.err.println("[DB ERRORE] Errore durante il caricamento: " + e.getMessage());
        }
        return null;
    }

    // DTO per il passaggio sicuro dei dati verso il Controller
    public static class DatiCaricati {
        private String nomeGiocatore;
        private String nomeStanzaCorrente;
        private int livelloAllertaMetzger;
        private int capitoloCorrente;
        private final List<String> oggettiInventario = new ArrayList<>();
        private final List<StanzaSalvata> stanzeSalvate = new ArrayList<>();

        public String getNomeGiocatore() { return nomeGiocatore; }
        public void setNomeGiocatore(String nomeGiocatore) { this.nomeGiocatore = nomeGiocatore; }
        public String getNomeStanzaCorrente() { return nomeStanzaCorrente; }
        public void setNomeStanzaCorrente(String nomeStanzaCorrente) { this.nomeStanzaCorrente = nomeStanzaCorrente; }
        public int getLivelloAllertaMetzger() { return livelloAllertaMetzger; }
        public void setLivelloAllertaMetzger(int livelloAllertaMetzger) { this.livelloAllertaMetzger = livelloAllertaMetzger; }
        public int getCapitoloCorrente() { return capitoloCorrente; }
        public void setCapitoloCorrente(int capitoloCorrente) { this.capitoloCorrente = capitoloCorrente; }
        public List<String> getOggettiInventario() { return oggettiInventario; }
        public List<StanzaSalvata> getStanzeSalvate() { return stanzeSalvate; }
    }

    public static class StanzaSalvata {
        private final String nomeStanza;
        private final boolean visibile;
        private final boolean visitata;

        public StanzaSalvata(String nomeStanza, boolean visibile, boolean visitata) {
            this.nomeStanza = nomeStanza;
            this.visibile = visibile;
            this.visitata = visitata;
        }

        public String getNomeStanza() { return nomeStanza; }
        public boolean isVisibile() { return visibile; }
        public boolean isVisitata() { return visitata; }
    }
}