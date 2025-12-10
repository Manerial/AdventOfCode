package aoc.exercises.year2025.day10;

import org.ojalgo.optimisation.*;

import java.math.*;
import java.util.*;

public class ILPNaturalSolver {

    public static int solve(int[][] vectors, int[] b) {
        int nVars = vectors.length; // nombre de variables
        int nEquations = b.length; // nombre d'équations
        int maxValue = Arrays.stream(b).max().orElse(1000);

        // Construire la matrice A à partir des colonnes
        int[][] A = new int[nEquations][nVars];
        for (int i = 0; i < nEquations; i++) {
            for (int j = 0; j < nVars; j++) {
                A[i][j] = vectors[j][i];
            }
        }

        ExpressionsBasedModel model = new ExpressionsBasedModel();

        // Créer les variables x0..xnVars-1 >= 0, entières
        Variable[] x = new Variable[nVars];
        for (int i = 0; i < nVars; i++) {
            x[i] = model.newVariable("x" + i) // Nom de la variable
                    .lower(0) // Minimum = 0
                    .upper(maxValue) // Maximum = Max de b
                    .integer(true);  // variable entière
        }

        // Ajouter les contraintes A*x = b
        for (int i = 0; i < nEquations; i++) {
            Expression expression = model.addExpression("eq" + i).level(b[i]); // littéralement eq1 = " = b[1]"
            for (int j = 0; j < nVars; j++) {
                expression.set(x[j], A[i][j]); // x0 * A[1][0] + x1 * A[1][1] + ... + xn * A[1][n] = b[1]
            }
        }

        // Optionnel : minimiser la somme des variables
        Expression objective = model.addExpression("sum").weight(1.0);
        for (Variable var : x) {
            objective.set(var, 1.0);
        }

        // Résoudre le modèle
        Optimisation.Result result = model.minimise();

        if (result.getState().isFeasible()) {
            return (int) result.toList().stream().mapToDouble(BigDecimal::doubleValue).sum();
        } else {
            return -1;
        }
    }
}
