package it.unicam.cs.pa.jbudget105532.Model;
/**
 * Questa classe definisce una Category e contiene i metodi necessari per gestirla.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicCategory implements Category {
    private String name;

    /**
     * Costruttore di una Category
     *
     * @param name il Nome della Category
     */
    public BasicCategory(String name) {
        this.name = name.toUpperCase();
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object category) {
        Category newcategory = null;
        if (category.getClass().equals(this.getClass())) {
            newcategory = (Category) category;
            return this.name.equals(newcategory.getName());
        }
        return false;
    }
}