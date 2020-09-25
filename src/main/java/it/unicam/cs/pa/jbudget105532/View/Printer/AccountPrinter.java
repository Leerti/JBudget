package it.unicam.cs.pa.jbudget105532.View.Printer;


import it.unicam.cs.pa.jbudget105532.Model.Account;

/**
 * Questa interfaccia definisce un printer per un Account.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public interface AccountPrinter {

    /**
     * Fornisce una String  che rappresenta un Account
     *
     * @param account l'Account da rappresentare
     * @return la String che descrive l'Account passato come parametro
     */
    String stringOf(Account account);
}
