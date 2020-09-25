package it.unicam.cs.pa.jbudget105532.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa classe definisce un Account e contiene i metodi necessari per gestirlo.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicAccount implements Account {
    private final int ID;
    private static int newId = 0;
    private String name;
    private String description;
    private double balance;
    private double initialBalance;
    private List<Movement> movements;
    private AccountType accountType;

    /**
     * Costruttore di un Account
     *
     * @param name           il Nome dell'Account
     * @param description    la Descrizione dell'Account
     * @param accountType    il Type dell'Account
     * @param initialBalance il Saldo iniziale dell'Account
     */
    public BasicAccount(String name, String description, AccountType accountType, double initialBalance) {
        this.ID = newId++;
        this.name = name;
        this.description = description;
        this.movements = new ArrayList<>();
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
    }

    /**
     * Costruttore di un'Account utilizzato dal sistema di caricamento dei dati
     * in quanto ad ogni Account aggiunnto al Ledger, aggiorna il campo statico newID.
     * <p>
     * Finche verranno aggiunti Account con un ID maggiore del newID, quest'ultimo viene aggiornato a ID+1.
     * Se viene aggiunto un Account con un ID più basso di newId, esso non viene aggiornato.
     * Questo meccanismo consente,una volta ricaricati i dati, alla prossima creazione di un Account
     * di crearlo con il giusto ID
     *
     * @param name           il Nome dell'Account
     * @param description    la Descrizione dell'Account
     * @param accountType    il Type dell'Account
     * @param initialBalance il Saldo iniziale dell'Account
     * @param ID             l'ID dell'Account
     */
    public BasicAccount(String name, String description, AccountType accountType, double initialBalance, int ID) {
        this.name = name;
        this.description = description;
        this.movements = new ArrayList<>();
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
        this.ID = ID;
        if (newId <= ID) newId = ++ID;
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public void setInitialBalance(double initialBalance) {
        this.balance += initialBalance - this.initialBalance;
        this.initialBalance = initialBalance;
    }

    @Override
    public double getInitialBalance() {
        return this.initialBalance;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public List<Movement> getMovement() {
        return this.movements;
    }

    @Override
    public List<Movement> getMovement(Predicate<Movement> p) {
        List<Movement> movements = new ArrayList<>();
        for (int i = 0; i < this.movements.size(); i++) {
            if (p.test(this.movements.get(i))) {
                movements.add(this.movements.get(i));
            }
        }
        return movements;
    }

    @Override
    public AccountType getAccountType() {
        return this.accountType;
    }

    @Override
    public void addMovement(Movement movement) {
        this.movements.add(movement);
        if (movement.getMovementType() == MovementType.INCOME) {
            if (this.accountType == AccountType.ASSET) {
                this.balance += movement.getMovement();
            } else this.balance -= movement.getMovement();
        } else {
            if (this.accountType == AccountType.ASSET) {
                this.balance -= movement.getMovement();
            } else this.balance += movement.getMovement();
        }
    }

    @Override
    public void deleteMovement(Movement movement) {
        this.movements.remove(movement);
        if (movement.getMovementType() == MovementType.INCOME) {
            if (this.accountType == AccountType.ASSET) {
                this.balance -= movement.getMovement();
            } else this.balance += movement.getMovement();
        } else {
            if (this.accountType == AccountType.ASSET) {
                this.balance += movement.getMovement();
            } else this.balance -= movement.getMovement();
        }
    }
@Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Account a = (Account) o;
            if (a.getID() == this.getID()) return true;
        }
        return false;
    }
}
