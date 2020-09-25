package it.unicam.cs.pa.jbudget105532.Model;

import it.unicam.cs.pa.jbudget105532.Exception.CategoryAlreadyAdded;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryAlreadyExisting;
import it.unicam.cs.pa.jbudget105532.Exception.CategoryNotAdded;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;


/**
 * Questa classe definisce una Transaction e contiene i metodi necessari per gestirla.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicTransaction implements Transaction {

    private final int ID;
    private static int newID = 0;
    private List<Movement> movements;
    private List<Category> categories;
    private LocalDate date;

    /**
     * Costruttore di una transaction
     *
     * @param movements  la List di Movement associati alla Transaction
     * @param categories la List di Category associate alla Transaction
     * @param date       la Data della Transaction
     */
    public BasicTransaction(List<Movement> movements, List<Category> categories, LocalDate date) {
        this.ID = newID;
        newID++;
        this.movements = movements;
        this.categories = categories;
        this.date = date;
        initTransaction();
    }

    /**
     * Costruttore di una Transaction utilizzato dal sistema di caricamento dei dati
     * in quanto ad ogniTransaction aggiunnta al Ledger, aggiorna il campo statico newID.
     * <p>
     * Finche verranno aggiunte Transaction con un ID maggiore del newID, quest'ultimo viene aggiornato a ID+1.
     * Se viene aggiunto una Transaction con un ID più basso di newId, esso non viene aggiornato.
     * Questo meccanismo consente,una volta ricaricati i dati, alla prossima creazione di una Transaction
     * di crearla con il giusto ID
     *
     * @param movements  la List di Movement associati alla Transaction
     * @param categories la List di Category associate alla Transaction
     * @param date       la Data della Transaction
     * @param ID         l'ID della Transaction
     */
    public BasicTransaction(List<Movement> movements, List<Category> categories, LocalDate date, int ID) {
        this.ID = ID;
        if (newID <= ID) newID = ++ID;
        this.movements = movements;
        this.categories = categories;
        this.date = date;
        initTransaction();
    }

    /**
     * una volta creata la transazione, vengono "collegati" tutti i suoi movimenti ad essa
     * per ogni moviemnto della sua lista di movimenti viene richiamato il metodo initializeTransaction del movement
     */
    public void initTransaction() {
        for (int i = 0; i < this.movements.size(); i++) {
            this.movements.get(i).initializeTransaction(this);
        }
    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public List<Movement> getMovements() {
        return movements;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    @Override
    public void addCategory(Category category) throws CategoryAlreadyAdded {
        if (this.categories.contains(category)) {
            throw new CategoryAlreadyAdded(category.getName());
        }
        this.categories.add(category);
        for (int i = 0; i < this.movements.size(); i++) {
            if (!this.movements.get(i).getCategory().contains(category))
                this.movements.get(i).addCategory(category);
        }
    }

    @Override
    public void removeCategory(String categoryName) throws CategoryNotAdded {
        Category category = new BasicCategory(categoryName);
        boolean found=false;
        for (int i = 0; i < this.getCategories().size(); i++) {
            if (this.categories.get(i).equals(category)) {
                found=true;
                for (int j = 0; j < this.movements.size(); j++) {
                    this.movements.get(j).removeCategory(this.categories.get(i));
                }
                this.getCategories().remove(this.categories.get(i));
            }
        }if(found==false)throw new CategoryNotAdded(categoryName);
    }
    @Override
    public void removeAllCategories(){

       while(!categories.isEmpty()){
            removeCategory(categories.get(0).getName());
        }

    }

    @Override
    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public double getTotalAmount() {
        Iterator iter = movements.iterator();
        Movement x;
        double sumIncome = 0.0;
        double sumExpense = 0.0;
        while (iter.hasNext()) {
            x = (Movement) iter.next();
            if (x.getMovementType() == MovementType.INCOME) {
                sumIncome += x.getMovement();
            } else sumExpense += x.getMovement();
        }
        return sumIncome - sumExpense;
    }

    @Override
    public void addMovement(Movement movement) {
        this.movements.add(movement);
        movement.initializeTransaction(this);
    }

    @Override
    public void removeMovement(Movement movement) {
        this.movements.remove(movement);
    }

    @Override
    public List<Movement> getMovements(Predicate<Movement> predicate) {
        List<Movement> movements = new ArrayList<>();
        for (int i = 0; i < this.movements.size(); i++) {
            if (predicate.test(this.movements.get(i))) {
                movements.add(this.movements.get(i));
            }
        }
        return movements;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            Transaction a = (Transaction) o;
            if (a.getID() == this.getID()) return true;
        }
        return false;
    }
}
