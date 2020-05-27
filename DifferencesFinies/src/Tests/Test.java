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

    public static void plotErrofN(double a, double b, int max_N, String s,double tol, Stage stage) {
        Matrix m;
        Calculs cal = new Calculs();
        Fonction f = giveFunc(s);

        if (tol < 0) {
            stage.setTitle("Courbe des log des erreurs en fonction du log du pas");

            final NumberAxis xAxis = new NumberAxis();
            final NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Logs du pas h");

            final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

            lineChart.setTitle("Courbe des logs des erreurs");
            XYChart.Series series = new XYChart.Series();

            series.setName("Logs Erreurs");
            double[] norms = new double[max_N-1];
            
            Matrix H = DenseMatrix.Factory.ones(max_N-1, 2);
            Matrix Err = DenseMatrix.Factory.zeros(max_N-1, 1);
            
            for (int i = 2; i < max_N+1; i++) {
                
                double h = 1.0 / i;
                double norm;
                m = cal.solveDir(f, a, b, i);
                Matrix yex = exact_y(s, a, b, i);
                norm = m.minus(yex).norm2();
                norms[i-2] = norm;
                
                H.setAsDouble(Math.log(h), i-2, 1);
                Err.setAsDouble(Math.log(norm), i-2, 0);
                
                // series.getData().add(new XYChart.Data<>(1.0/i, (norm)));
                // series.getData().add(new XYChart.Data<>(1.0/i, (norm)));
                series.getData().add(new XYChart.Data<>(Math.log(1.0/i), Math.log(norm)));
            }
            
            Matrix beta = (((H.transpose().mtimes(H)).inv()).mtimes(H.transpose())).mtimes(Err);
           
            // double conv_temp = (Math.log(norms[max_N-2]) - Math.log(norms[max_N-9])) / (Math.log(1.0/max_N) - Math.log(1.0/(max_N-7)));
            // System.out.println(conv_temp);
            
            System.out.println("L'ordre de convergence est p =:  " + beta.getAsDouble(1,0));
            
            Scene scene = new Scene(lineChart, 800, 600);
            lineChart.getData().add(series);
            stage.setScene(scene);
            stage.show();

        } else {
            m = cal.solveDir(f, a, b, N_ex);
            Matrix temp = DenseMatrix.Factory.zeros(N_ex + 1, 1);
            Matrix yex1 = exact_y(s, a, b, N_ex);
            temp = m.minus(yex1);
            double norm = temp.norm2() / yex1.norm2();
            boolean res = norm < tol;
            
            System.out.println( "Test Calcul de U: f = " + s + "  ||  Validité de la solution: " + res);
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
            case "Deux": {
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
            case "-X": {
                for (int j = 0; j < taille; j++) {
                    m.setAsDouble(Math.pow(x[j], 3) / 6 + 5*x[j] / 6, j, 0);
                }
                break;
            }
            case "XSinus": {
                for (int j = 0; j < taille; j++) {
                    m.setAsDouble(x[j]*Math.sin(x[j]), j, 0);
                }
                break;
            }
            default:
                break;
        }
        return m;
    }

    private static Fonction giveFunc(String s) {
        Fonction f = (x) -> {
            return 2;
        };
        switch (s) {
            case "Deux":
                f = (x) -> {
                    return 2;
                };
                break;
            
            case "-X":
                 f = (x) -> {
                    return -x;
                };
                break;
            case "Cosinus":
                f = (x) -> {
                    return Math.cos(x);
                };
                break;
            case "Sinus":
                f = (x) -> {
                  return Math.sin(x);
                };
                break;
            case "XSinus":
                f = (x) -> {
                  return -2*Math.cos(x) + x * Math.sin(x);  
                };
                break;
            default:
                break;
        }
        return f;
    }

    @Override
    public void start(Stage stage) {
        
        plotErrofN(0, -1, N_ex,"Deux", 1E-8, stage);
        
        plotErrofN(0, 1, N_ex,"-X", 1E-8, stage);
        
        plotErrofN(1, Math.cos(1), 52, "Cosinus",-1E-8, stage);
        
        plotErrofN(0, Math.sin(1), 10, "Sinus",-1E-8, stage);
        
        plotErrofN(0, Math.sin(1), 10, "XSinus", -1E-8, stage);
       
    }

    public static void main(String[] args) {
        launch(args);
    }
}

