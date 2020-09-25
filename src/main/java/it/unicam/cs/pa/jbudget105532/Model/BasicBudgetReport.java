package it.unicam.cs.pa.jbudget105532.Model;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Questa classe definisce un BudgetReport e contiene i metodi necessari per gestirlo.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicBudgetReport implements BudgetReport {
    private Budget budget;
    private Ledger ledger;

    /**
     * Costruttore di un BudgetReport
     *
     * @param budget il Budget da cui prendere la Category e l'Ammontare scelto per il budget
     * @param ledger il Ledger da cui prendere le informaioni relative alle attuali Income/Expense per una determinata Category
     */
    public BasicBudgetReport(Budget budget, Ledger ledger) {
        this.budget = budget;
        this.ledger = ledger;
    }


    @Override
    public Budget getBudget() {
        return this.budget;
    }

    @Override
    public Category getCategory() {
        return this.budget.getCategory();
    }

    @Override
    public Predicate<Movement> Predicate() {
        return movement -> movement.getCategory().contains(budget.getCategory());
    }

    @Override
    public double calculateActual() {
        List<Movement> movements = new ArrayList<>();
        for (int i = 0; i < ledger.getAccounts().size(); i++) {
            movements.addAll(ledger.getAccounts().get(i).getMovement(Predicate()));
        }
        double actual = 0;
        for (int i = 0; i < movements.size(); i++) {
            if (movements.get(i).getMovementType() == MovementType.INCOME) {
                actual -= movements.get(i).getMovement();
            } else actual += movements.get(i).getMovement();
        }
        return actual;
    }

    @Override
    public double calculateDeviation() {
        return budget.getAmount() - calculateActual();
    }


    @Override
    public List<Integer> years() {
        List<Integer> years = new ArrayList<>();
        Account account = null;
        for (int i = 0; i < ledger.getAccounts().size(); i++) {
            account = ledger.getAccounts().get(i);
            for (Movement movement : account.getMovement(Predicate())) {
                if (!years.contains(movement.getDate().getYear())) {
                    years.add(movement.getDate().getYear());
                }
            }
        }
        return years;
    }


    @Override
    public double calculateIncomePerMonth(int month, List<Movement> movements){
       double incomePerMonth=0.0;
        for (Movement movement :movements) {
            if (movement.getDate().getMonthValue() <=month&& movement.getMovementType().equals(MovementType.INCOME)) {
               incomePerMonth+=movement.getMovement();
            }
        }
        return incomePerMonth;
    }
    @Override
    public double calculateExpensePerMonth(int month, List<Movement> movements){
        double expensePerMonth=0.0;
        for (Movement movement :movements) {
            if (movement.getDate().getMonthValue() <= month&& movement.getMovementType().equals(MovementType.EXPENSE)) {
                expensePerMonth+=movement.getMovement();
            }
        }
        return expensePerMonth;
    }

    @Override
    public double calculateTotalPerMonth(int month, List<Movement> movements){
        double income = calculateIncomePerMonth(month,movements);
        double expense =calculateExpensePerMonth(month,movements);
        return expense-income;
    }
    @Override
    public List<Movement> selectedMovement(Integer year) {
        List<Movement> movements = new ArrayList<>();
        Account account = null;
        for (int i = 0; i < ledger.getAccounts().size(); i++) {
            account = ledger.getAccounts().get(i);
            for (Movement movement : account.getMovement(Predicate())) {
                if (movement.getDate().getYear() == year) {
                    movements.add(movement);
                }
            }
        }
        Transaction transaction=null;
        for (int i = 0; i < ledger.getScheduledTransactions().size(); i++) {
           transaction=ledger.getScheduledTransactions().get(i).getTransaction();
            for (Movement movement : transaction.getMovements(Predicate())) {
                if (movement.getDate().getYear() == year) {
                    movements.add(movement);
                }
            }
        }
        return movements;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(this.getClass())) {
            BudgetReport a = (BudgetReport) o;
            if (a.getBudget() == this.getBudget()) return true;
        }
        return false;
    }


}
