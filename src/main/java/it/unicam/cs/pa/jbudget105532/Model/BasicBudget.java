package it.unicam.cs.pa.jbudget105532.Model;

import java.util.function.Predicate;

/**
 * Questa classe definisce un Budget e contiene i metodi necessari per gestirlo.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicBudget implements Budget {
    private static int newID = 0;
    private final int ID;
    private Category category;
    private double amount;
    private Predicate<Movement> predicate;

    /**
     * Costruttore di un Budget
     *
     * @param category la Category a cui associare il Budget
     * @param amount   l'Ammontare dedicato al Budget
     */
    public BasicBudget(Category category, double amount) {
        this.ID = newID++;
        this.category = category;
        this.amount = amount;
    }

    /**
     * Costruttore di un Budget utilizzato dal sistema di caricamento dei dati
     * in quanto ad ogni Budget aggiungto al Ledger, aggiorna il campo statico newID.
     * <p>
     * Finche verranno aggiunti Budget con un ID maggiore del newID, quest'ultimo viene aggiornato a ID+1.
     * Se viene aggiunto un Budget con un ID più basso di newId, esso non viene aggiornato.
     * Questo meccanismo consente,una volta ricaricati i dati, alla prossima creazione di un Budget
     * di crearlo con il giusto ID
     *
     * @param category la Category a cui associare il Budget
     * @param amount   l'Ammontare dedicato al Budget
     * @param ID       l'ID del Budget
     */
    public BasicBudget(Category category, double amount, int ID) {
        this.ID = ID;
        if (newID <= ID) newID = ++ID;
        this.category = category;
        this.amount = amount;
    }


    @Override
    public Category getCategory() {
        return this.category;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Budget a = (Budget) o;
            if (a.getID() == this.getID()) return true;
        }
        return false;
    }
}