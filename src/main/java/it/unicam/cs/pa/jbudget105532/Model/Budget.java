package it.unicam.cs.pa.jbudget105532.Model;


/**
 * Questa interfaccia definisce un Budget e forza le classi che la implementeranno
 * a fornire tutti i metodi necessari alla gestione di esso.
 * Un Budget è composto di una Category e un Ammontare deciso per il Budget.
 * E' possibile modificate l'Ammontare predisposto per il Budget attraverso il metodo setAmount.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Budget {

    /**
     * Get della Category a cui è associato il Budget
     *
     * @return la Category a cui è associato il Budget
     */
    Category getCategory();

    /**
     * Get del Totale definito per il Budget
     *
     * @return il Totale definito per il Budget
     */
    double getAmount();

    /**
     * Cambia il Totale definito per il Budget
     *
     * @param amount il nuovo Totale definito per il Budget
     */
    void setAmount(double amount);

    /**
     * Get dell'ID del Budget
     *
     * @return l'ID del Budget
     */
    int getID();
}
