package com.mycompany.mountforest.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestore dell'infrastruttura del Database H2 in modalità embedded.
 * Si occupa dell'apertura delle connessioni e della creazione iniziale delle tabelle.
 * * @author matteuu12
 */
public class DatabaseManager {

    // Il file del database verrà creato nella cartella principale del progetto con il nome "mountforest_db.mv.db"
    private static final String DB_URL = "jdbc:h2:./mountforest_db;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASS = "";

    /**
     * Apre e restituisce una connessione attiva verso il database H2.
     * Chi chiama questo metodo è responsabile della chiusura della connessione (es. tramite try-with-resources).
     * * @return Oggetto Connection attivo
     * @return 
     * @throws SQLException Se la connessione fallisce o il driver non è presente
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Forza il caricamento del driver H2 in memoria
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Impossibile trovare il driver H2. Verifica le dipendenze nel pom.xml.", e);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    /**
     * Crea le tabelle di gioco nel database se non sono già presenti.
     * Questo metodo deve essere richiamato una sola volta nel metodo main all'avvio del programma.
     */
    public static void inizializzaDatabase() {
        // 1. Tabella principale per lo stato del Giocatore e del Capitolo
        String sqlPartita = "CREATE TABLE IF NOT EXISTS partita ("
                + "id_partita INT AUTO_INCREMENT PRIMARY KEY, "
                + "nome_giocatore VARCHAR(50) NOT NULL, "
                + "stanza_corrente VARCHAR(50) NOT NULL, "
                + "livello_allerta_metzger INT DEFAULT 0, "
                + "capitolo_corrente INT DEFAULT 1, "
                + "data_salvataggio TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ");";

        // 2. Tabella per tracciare gli oggetti presenti nell'inventario del giocatore
        // Il vincolo ON DELETE CASCADE elimina in automatico lo zaino se viene cancellata la partita
        String sqlInventario = "CREATE TABLE IF NOT EXISTS inventario_salvato ("
                + "id_partita INT, "
                + "nome_oggetto VARCHAR(50) NOT NULL, "
                + "FOREIGN KEY (id_partita) REFERENCES partita(id_partita) ON DELETE CASCADE"
                + ");";

        // 3. Tabella per memorizzare lo stato dinamico delle stanze (es. se una porta è stata sbloccata)
        String sqlStatoStanze = "CREATE TABLE IF NOT EXISTS stato_stanze ("
                + "id_partita INT, "
                + "nome_stanza VARCHAR(50) NOT NULL, "
                + "is_buia BOOLEAN DEFAULT FALSE, "
                + "is_bloccata BOOLEAN DEFAULT FALSE, "
                + "PRIMARY KEY (id_partita, nome_stanza), "
                + "FOREIGN KEY (id_partita) REFERENCES partita(id_partita) ON DELETE CASCADE"
                + ");";

        // Apertura della connessione e del canale di esecuzione comandi (Statement)
        try (Connection conn = getConnection(); 
             Statement stmt = conn.createStatement()) {
            
            // Esecuzione delle query DDL per strutturare il file DB
            stmt.execute(sqlPartita);
            stmt.execute(sqlInventario);
            stmt.execute(sqlStatoStanze);
            
            System.out.println("[DATABASE] Inizializzazione completata con successo. Struttura tabelle verificata.");
            
        } catch (SQLException e) {
            System.err.println("[DATABASE ERRORE] Errore durante l'inizializzazione delle tabelle: " + e.getMessage());
        }
    }
}