package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class BasicCategoriesPrinter {


    public List<String> stringOf(List<Category> categories){
        List<String> categoryRepresentation = new ArrayList<>();
        for (Category category : categories) {
            categoryRepresentation.add(category.getName());
        }
        return categoryRepresentation;
    }
}
