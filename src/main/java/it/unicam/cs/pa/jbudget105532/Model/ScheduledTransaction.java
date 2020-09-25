package it.unicam.cs.pa.jbudget105532.Model;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa interfaccia definisce una ScheduledTransaction.
 * Una ScheduledTransaction è un oggetto che contiene una Transaction che ancora non deve essere resa "effettiva"
 * Contiene anche i metodi per controllare quando una ScheudledTransaction è completata,
 * ovvero non ha più la Transaction al suo interno perche è stata spostata nella List di Transaction del  Ledger
 */
public interface ScheduledTransaction {
    /**
     * Get della Transaction all'interno della ScheduledTransaction
     * @return la Transaction Schedulata
     */
    Transaction getTransaction ();

    /**
     * Con questo metodo viene rimossa la Transaction all'interno della ScheduledTransaction
     */
    void removeTransaction();

    /**
     * Controlla se la ScheudledTransaction è completata, ovvero se la Transaction al suo interno
     * è stat resa effettiva e quindi spostata nella List di Transaction del Ledger
     *
     * @return true s la ScheduledTransaction è completata, false altrimenti
     */
    boolean isCompleted();
}
