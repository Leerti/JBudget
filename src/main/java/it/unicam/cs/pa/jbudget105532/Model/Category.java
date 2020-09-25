package it.unicam.cs.pa.jbudget105532.Model;

/**
 * Questa interfaccia definisce una Category e forza le classi che la implementeranno
 * a fornire i metod necessari alla gestione  di essa.
 * <p>
 * Una Category continene un Nome che la identiica e la differenzia dalle altre.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface Category {
    /**
     * Get del nome della Category
     *
     * @return il nome della Category
     */
    String getName();

}
