/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import Interfaces.Fonction;
import differencesfinies.Calculs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

/**
 *
 * @author E_DinaBrown
 */
public class Test extends Application {

    private static final int N_ex = 20;

    public static void plotCourbePredRel(Matrix m, Fonction f, Stage stage) {
        System.out.println(m);
        stage.setTitle("Courbe des erreurs pour le calcul différentiel");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of intervals");

        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Courbes des fonctions prédites et réelles ");
        XYChart.Series seriesPred = new XYChart.Series();
        XYChart.Series seriesRel = new XYChart.Series<>();

        seriesPred.setName("Courbe prédite");
        seriesRel.setName("Courbe réelle");

        for (int i = 0; i < m.getSize()[0]; i++) {
            seriesPred.getData().add(new XYChart.Data(m.getAsDouble(i, 1), m.getAsDouble(i, 0)));
            seriesRel.getData().add(new XYChart.Data(m.getAsDouble(i, 1), -Math.pow(m.getAsDouble(i, 1), 2)));
            //seriesRel.getData().add(new XYChart.Data(m.getAsDouble(i, 1), f.val(m.getAsDouble(i, 1))));
            //Math.pow(m.getAsDouble(j, 0)
        }

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(seriesPred);
        lineChart.getData().add(seriesRel);

        stage.setScene(scene);
        stage.show();
    }

    public static void plotErrofN(Fonction f, double a, double b, int max_N, String s,double tol, Stage stage) {
        Matrix m;
        Calculs cal = new Calculs();

        if (tol < 0) {
            stage.setTitle("Courbe des log des erreurs en fonction du nombre de points");

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Nombre de divisions");

            final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

            lineChart.setTitle("Courbes des erreurs");
            XYChart.Series series = new XYChart.Series();

            series.setName("Erreurs");
            double[] norms = new double[max_N-1];
            
            Matrix H = DenseMatrix.Factory.ones(max_N-1, 2);
            Matrix Err = DenseMatrix.Factory.zeros(max_N-1, 1);
            
            for (int i = 2; i < max_N+1; i++) {
                m = cal.solveDir(f, a, b, i);
                Matrix yex = exact_y(s, a, b, i);
                Matrix temp = DenseMatrix.Factory.zeros(i + 1, 1);
              
                temp = m.minus(yex);
                double norm = temp.norm2();
                System.out.println(norm);
               // norms[i-1] = norm;
                
                double h = 1.0 / i;
                H.setAsDouble(Math.log(h), i-2, 1);
                Err.setAsDouble(Math.log(norm), i-2, 0);
                
                series.getData().add(new XYChart.Data<>(i, (norm)));
            }
            System.out.println(H);
            Matrix beta = (((H.transpose().mtimes(H)).inv()).mtimes(H.transpose())).mtimes(Err);
            System.out.println(beta);
           // double conv_p = (Math.log(norms[2]) - Math.log(norms[1])) / (Math.log((double)1/3) - Math.log((double)1/2));
           // System.out.println("L'ordre de convergence est:     " + conv_p);
            
            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().add(series);

            stage.setScene(scene);
            stage.show();

        } else {
            m = cal.solveDir(f, a, b, N_ex);
            Matrix temp = DenseMatrix.Factory.zeros(N_ex + 1, 1);
            Matrix yex1 = exact_y("MoinsCarre", 0, -1, N_ex);
            temp = m.minus(yex1);
            double norm = temp.norm2() / yex1.norm2();
            System.out.println(norm < tol);
            System.out.println(norm);
        }

    }

    private static Matrix exact_y(String s, double a, double b, int n) {
         if(n == 1) {
            Matrix res = DenseMatrix.Factory.zeros(2, 1);
            res.setAsDouble(a, 0, 0);
            res.setAsDouble(b, 1, 0);
            
            return res;
        }
        Matrix m = DenseMatrix.Factory.zeros(n + 1, 1);
        double[] x = Calculs.giveX(n);
        int taille = n + 1;
        switch (s) {
            case "MoinsCarre": {
                for (int j = 0; j < taille; j++) {
                    m.setAsDouble(-Math.pow(x[j], 2), j, 0);
                }
                break;
            }
            case "Cosinus": {
                for (int j = 0; j < taille; j++) {
                    m.setAsDouble(Math.cos(x[j]), j, 0);
                }
                break;
            }
            case "Sinus": {
                for (int j = 0; j < taille; j++) {
                    m.setAsDouble(Math.sin(x[j]), j, 0);
                }
                break;
            }
            default:
                break;
        }
        return m;
    }

    private Fonction giveFunc(String s) {
        Fonction f = (x) -> {
            return 2;
        };
        switch (s) {
            case "MoinsCarre":
                f = (x) -> {
                    return 2;
                };
                break;

            default:
                break;
        }
        return f;
    }

    @Override
    public void start(Stage stage) {
        //plotCourbePredRel(new Calculs().solveDir((x)->{return 2;}, 0, -1, 20), (x)->{return 2;}, stage);
        plotErrofN((x) -> {
            return 2;
        }, 0, -1, N_ex,"MoinsCarre", 1E-8, stage);
        
        /* plotErrofN((x) -> {
            return Math.cos(x);
        }, 1, Math.cos(1), 50, "Cosinus",-1E-8, stage); */
        
        plotErrofN((x) -> {
            return Math.sin(x);
        }, 0, Math.sin(1), 320, "Sinus",-1E-8, stage);
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
/* 
Test de conformité : avec des f pour lesquels on a une solution exacte
Test de convergence:  avec des f pour lesquels on n'a pas de solutions exactes

*/
