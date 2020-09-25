package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Account;
import it.unicam.cs.pa.jbudget105532.View.Printer.AccountPrinter;

/**
 *  Questa classe  definisce un printer per un Account
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicAccountPrinter implements AccountPrinter {

    @Override
    public String stringOf(Account account) {
        return    "  ID Account :     " + account.getID() +
                "\n  Name :           " + account.getName() +
                "\n  Description :    " + account.getDescription() +
                "\n  InitialBalance : " + account.getInitialBalance() +
                "\n  Balance :        " +account.getBalance() +
                "\n  Type :           " + account.getAccountType().toString();
    }
}
