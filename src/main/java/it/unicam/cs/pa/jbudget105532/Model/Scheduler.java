package it.unicam.cs.pa.jbudget105532.Model;

/**
 * Questa interfaccia definisce uno Scheudler,
 * ovvero un Oggetto che "decide" se una ScheduledTransaction
 * deve essere resa effettiva o ancora non  il momento.
 *
 */
public interface Scheduler {

    /**
     * Questo metodo prende in input una ScheduledTransaction e un Ledger,
     * grazie al metodo Check controlla se è il momemnto di renderla effettiva e se lo è
     * aggiunge la Transaction che si trova all'interno dellla ScheduledTransaction, nella List
     * delle Transacton del Ledger e rimuove la Transaction dalla ScheduledTransaction
     *
     * @param transaction la ScheduledTransaction da controllare
     * @param ledger il Ledger in cui inserire la Transaction
     * @return true se la Transaction è stata resa effettiva, false altrimenti
     */
    boolean insert(ScheduledTransaction transaction,Ledger ledger);

    /**
     * Presa una Transaction controlla se è arrivato il  giorno di renderla effettiva
     *
     * @param transaction la Transaction contenuta in uno ScheduledTransaction da controlalre
     * @return true se deve essere resa effettiva
     */
    boolean check(Transaction transaction);
}
