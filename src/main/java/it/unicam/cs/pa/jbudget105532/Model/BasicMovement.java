package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.CategoryAlreadyAdded;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryNotAdded;

import java.time.LocalDate;
import java.util.List;

/**
 * Questa classe definisce un Movement e contiene i metodi necessari per gestirlo.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicMovement implements Movement {

    private final int ID;
    private static int newID = 0;
    private double movement;
    private String description;
    private MovementType movementType;
    private List<Category> categories;
    private Transaction transaction;
    private Account account;

    /**
     * Costruttore di un Movement
     *
     * @param movement     l'Amontare del Movement
     * @param description  la Descrizione del Movement
     * @param movementType il Type del Movement
     * @param category     la List di Category associate al Movement
     * @param account      l'Account a cui è associato il Movement
     */
    public BasicMovement(double movement, String description, MovementType movementType, List<Category> category, Account account) {
        this.ID = newID;
        newID++;
        this.movement = movement;
        this.description = description;
        this.movementType = movementType;
        this.categories = category;
        this.account = account;
    }

    /**
     * Costruttore di un Movement utilizzato dal sistema di caricamento dei dati
     * in quanto ad ogni Movement aggiunnto al Ledger, aggiorna il campo statico newID.
     * <p>
     * Finche verranno aggiunti Movement con un ID maggiore del newID, quest'ultimo viene aggiornato a ID+1.
     * Se viene aggiunto un Movement con un ID più basso di newId, esso non viene aggiornato.
     * Questo meccanismo consente,una volta ricaricati i dati, alla prossima creazione di un Movement
     * di crearlo con il giusto ID
     *
     * @param movement     l'Amontare del Movement
     * @param description  la Descrizione del Movement
     * @param movementType il Type del Movement
     * @param category     la List di Category associate al Movement
     * @param account      l'Account a cui è associato il Movement
     * @param ID           l'ID associato al Movement
     */
    public BasicMovement(double movement, String description, MovementType movementType, List<Category> category, Account account, int ID) {
        this.ID = ID;
        if (newID <= ID) newID = ++ID;
        this.movement = movement;
        this.description = description;
        this.movementType = movementType;
        this.categories = category;
        this.account = account;
    }



    @Override
    public void initializeTransaction(Transaction transaction) {
        if (this.transaction == null) {
            this.transaction = transaction;
            for (int j = 0; j < transaction.getCategories().size(); j++) {
                if (!this.categories.contains(transaction.getCategories().get(j)))
                    this.categories.add(transaction.getCategories().get(j));
            }
        }
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public MovementType getMovementType() {
        return movementType;
    }

    @Override
    public double getMovement() {
        return movement;
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public Account getAccount() {
        return this.account;
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public LocalDate getDate() {
        return this.transaction.getDate();
    }

    @Override
    public List<Category> getCategory() {
        return categories;
    }

    @Override
    public void addCategory(Category category) throws CategoryAlreadyAdded {
        if (!this.categories.contains(category))
            this.categories.add(category);
        else throw new CategoryAlreadyAdded(category.getName());
    }

    @Override
    public void removeCategory(Category category) throws CategoryNotAdded {
        if (!this.categories.contains(category))
            throw new CategoryNotAdded(category.getName());
        categories.remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Movement a = (Movement) o;
            if (a.getID() == this.getID()) return true;
        }
        return false;
    }

}
