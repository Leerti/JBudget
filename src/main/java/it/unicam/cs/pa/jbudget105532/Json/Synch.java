package it.unicam.cs.pa.jbudget105532.Json;

import it.unicam.cs.pa.jbudget105532.Model.Ledger;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Questa interfaccia ha la responsabilita di gestire la persistenza dei dati.
 * <p>
 * Forza le classi che la implementeranno ad avere tutti i metodi necessari per
 * salvare i dati dell'applicazione su di un file e i metodi necessari per
 * caricare i dati dell'applicazione da un file.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Synch {

    /**
     * Questo metodo, preso un oggetto Ledger e un path di un File salva il Ledger
     * e tutto il suo contenuto ( Transaction, Account... ) sul File
     *
     * @param ledger Ledger da salvare
     * @param path   percorso del File dove salvare il Ledger
     * @throws IOException viene lanciata se non viene trovato il File in cui salvare
     *                     il Ledger
     */
    void writeLedger(Ledger ledger, String path) throws IOException;

    /**
     * Questo metodo, preso un File, ritorna un oggetto Ledger
     *
     * @param path percorso del File da cui "estrarre" il Ledger
     * @return un oggetto Ledger
     * @throws FileNotFoundException viene lanciata se non viene trovato il File da
     *                               cui andare a "estrarre" il Ledger
     */
    Ledger readLedger(String path) throws FileNotFoundException;
}
