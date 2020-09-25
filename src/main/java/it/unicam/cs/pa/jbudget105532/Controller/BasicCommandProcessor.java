package it.unicam.cs.pa.jbudget105532.Controller;

import it.unicam.cs.pa.jbudget105532.Exception.UnknownCommandException;


import java.util.Map;
import java.util.function.Consumer;

/**
 * Questa classe definisce  un Controller e ha i metodi necessari per
 * far eseguire i comandi inseriti dall'utente.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicCommandProcessor implements CommandProcessor {
    private Map<String, Consumer<? super Command>> commands;
    private Command state;

    /**
     * Costruttore di un CommandProcess
     *
     * @param commands l'hashMap contenente il nome del comando e l'azione da eseguire legata a un determinato comando
     * @param command  oggetto che contiene i metodi per eseguire un determinato comando
     */
    public BasicCommandProcessor(Map<String, Consumer<? super Command>> commands, Command command) {
        this.commands = commands;
        this.state = command;
    }

    @Override
    public void processCommand(String command) throws UnknownCommandException {

        Consumer<? super Command> action = commands.get(command);
        if (action == null) {
            throw new UnknownCommandException(command);
        } else {
            action.accept(state);
        }
    }


}
