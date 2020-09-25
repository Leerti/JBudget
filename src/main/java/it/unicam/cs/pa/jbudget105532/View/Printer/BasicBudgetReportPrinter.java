package it.unicam.cs.pa.jbudget105532.View.Printer;

import it.unicam.cs.pa.jbudget105532.Model.BudgetReport;
import it.unicam.cs.pa.jbudget105532.View.Printer.BudgetPrinter;
import it.unicam.cs.pa.jbudget105532.View.Printer.BudgetReportPrinter;

/**
 *  Questa classe definisce un printer per un BudgetReport.
 *
 * @author Alessandra Lerteri Caroletta  ale.lertericaroletta@studenti.unicam.it
 */
public class BasicBudgetReportPrinter implements BudgetReportPrinter {
    private BudgetPrinter bprinter;


    public BasicBudgetReportPrinter(BudgetPrinter bprinter) {
        this.bprinter = bprinter;
    }

    @Override
    public String stringOf(BudgetReport budgetReport) {
        return "Budget :" + "\n" + bprinter.stringOf(budgetReport.getBudget()) +
                "\nCurrent spending :" + budgetReport.calculateActual() +
                "\n" + resourceStillAvailable(budgetReport);
    }

    /**
     *  In base allo scostamento attuale dal Budget prefissato, viene fornita
     *  la String "Resources still available :" se le spese attuali sono minori del Budget fissato,
     *  altrimenti, se il Budget viene superato viene fornita la String "Budget exceeded by : "
     *
     * @param budgetReport il BudgetReport che calcola lo scostamento dal Budget attuale
     * @return la String  corretta per lo scostamento
     */
    private String resourceStillAvailable(BudgetReport budgetReport) {
        if (budgetReport.calculateDeviation() > 0) {
            return "Resources still available :" + budgetReport.calculateDeviation();
        } else return "Budget exceeded by : " + -budgetReport.calculateDeviation();
    }
}
