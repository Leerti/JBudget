package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Exception.UnknownCommandException;

/**
 * Questa interfaccia definisce un ProcessatoreDiComandi e forza le classi che la implementeranno ad avere tutti i metodi necessari per
 * far eseguire i comandi inseriti dall'utente.
 * Ha al suo interno un Command che contiene i metodi che effettivamente realizzeranno i comandi inseriri.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface CommandProcessor {

    /**
     * Presa unsa stringa, esegue il comando corrispondente alla stringa inserita
     *
     * @param command la stringa contenente il nome del comando che l'utente vuole eseguire
     * @throws UnknownCommandException se il comando inserito non è presente tra i comandi possibili
     */
    void processCommand(String command) throws UnknownCommandException;


}
