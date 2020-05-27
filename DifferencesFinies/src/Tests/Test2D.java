/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Interfaces.Fonction;
import Interfaces.Fonction2D;
import differencesfinies.Calculs;
import static differencesfinies.Calculs.giveX;
import differencesfinies.Calculs2D;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

/**
 *
 * @author E_DinaBrown
 */
public class Test2D extends Application {

    // solve2D(Fonction2D f, Matrix conn, int n, int m)
    private static final int N_ex = 20;

    public static void plotError2DfN(int max_N, int max_M, double tol, String fun, String meth, Stage stage) {

        Matrix m;
        Matrix conn = matDiric(fun, max_N, max_M);
        Calculs2D cal = new Calculs2D();
        Fonction2D f = giveFunc2D(fun);

        if (tol < 0) {
            stage.setTitle("Courbe des log des erreurs en fonction des logs des pas");

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Logs du pas h");

            final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

            lineChart.setTitle("Courbe des logs des erreurs");
            XYChart.Series series = new XYChart.Series();

            series.setName("Logs Erreurs");
            double[] norms = new double[max_N - 1];

            Matrix H = DenseMatrix.Factory.ones(max_N - 1, 3);
            Matrix Err = DenseMatrix.Factory.zeros(max_N - 1, 1);

            for (int i = 2; i < max_N + 1; i++) {

                double h1 = 1.0 / i;
                double h2 = 1.0/(i+1);
                double norm;
                m = cal.solve2D(f, conn, i, i+1);
                Matrix yex = exact_y2D(fun, conn, i, i+1);
                norm = m.minus(yex).norm2();
                norms[i - 2] = norm;

                H.setAsDouble(Math.log(h1), i - 2, 1);
                H.setAsDouble(Math.log(h2), i - 2, 2);
                Err.setAsDouble(Math.log(norm), i - 2, 0);

                // series.getData().add(new XYChart.Data<>(Math.log(1.0 / i), Math.log(norm)));
            }
            System.out.println(H);
            System.out.println(Err);

            Matrix beta = (((H.transpose().mtimes(H)).inv()).mtimes(H.transpose())).mtimes(Err);
            System.out.println(beta);
            System.out.println("L'ordre de convergence est p =:  " + beta.getAsDouble(1, 0));

            //Scene scene = new Scene(lineChart, 800, 600);
            //lineChart.getData().add(series);
            // stage.setScene(scene);
            // stage.show();
        } else {
            
            m = cal.solve2D(f, conn, max_N, max_M);
            int order = (max_N - 1) * (max_M - 1);
            Matrix temp = DenseMatrix.Factory.zeros(order, 1);
            Matrix yex = exact_y2D(fun, conn, max_N, max_M);
            temp = m.minus(yex);
            double norm = temp.norm2() / yex.norm2();
            boolean res = norm < tol;

            System.out.println("Test Calcul de U en 2D: U = " + fun + "  ||  Validité de la solution: " + res);

        }
    }

    private static Fonction2D giveFunc2D(String s) {
        Fonction2D f = null;
        switch (s) {
            case "XPlusY":
                f = (x, y) -> {
                    return 0;
                };
                break;
            case "XSinY":
                f = (x, y) -> {
                    return 0;
                };
            default:
                break;
        }
        return f;
    }

    private static Matrix exact_y2D(String s, Matrix conn, int n, int m) {
        int order = (n - 1) * (m - 1);
        double x[] = giveX(n);
        double y[] = giveX(m);
        Matrix yex = DenseMatrix.Factory.zeros(order, 1);
        int tempI = 1, tempJ = 1;
        switch (s) {
            case "XPlusY": {
                for (int i = 0; i < order; i++) {
                    if (i % (n - 1) == 0) {
                        if (i != 0) {
                            tempI++;
                            tempJ = 1;
                        }
                    }
                    yex.setAsDouble(x[tempJ] + y[tempI], i, 0);
                    tempJ++;

                }
                break;
            }
            case "XSinY": {
                for (int i = 0; i < order; i++) {
                    if (i % (n - 1) == 0) {
                        if (i != 0) {
                            tempI++;
                            tempJ = 1;
                        }
                    }
                    yex.setAsDouble(x[tempJ] * Math.sin(y[tempI]), i, 0);
                    tempJ++;

                }
                break;
            }
            default:
                break;

        }

        return yex;

    }

    private static Matrix matDiric(String s, int n, int m) {
        Matrix con = SparseMatrix.Factory.zeros(m + 1, n + 1);
        double x[] = giveX(n);
        double y[] = giveX(m);

        switch (s) {
            case "XPlusY": {
                for (int i = 0; i <= n; i++) {
                    con.setAsDouble(x[i] + y[0], m, i);
                    con.setAsDouble(x[i] + y[m], 0, i);
                }
                for (int j = 0; j <= m; j++) {
                    con.setAsDouble(x[0] + y[j], j, 0);
                    con.setAsDouble(x[n] + y[j], j, n);
                }
                break;
            }
            case "XSinY": {
                for (int i = 0; i <= n; i++) {
                    con.setAsDouble(x[i]*Math.sin(y[0]), m, i);
                    con.setAsDouble(x[i]*Math.sin(y[m]), 0, i);
                }
                for (int j = 0; j <= m; j++) {
                    con.setAsDouble(x[0]*Math.sin(y[j]), j, 0);
                    con.setAsDouble(x[n]*Math.sin(y[j]), j, n);
                }
                break;
            }

        }

        return con;
    }

    
    @Override
    public void start(Stage stage) {
        plotError2DfN(4, 4, 1.0E-8, "XPlusY", "", stage);
        plotError2DfN(4, 4, -1.0E-8, "XSinY", "", stage);
        

    }

    public static void main(String[] args) {
        launch(args);
    }

}
// Effectue les mêmes travaux que le solver en 1 dimension,en calculant l'ordre de convergence, et en s'assurant que l'erreur pour degre <2 est inférieur à une 
// certaine tolérance
