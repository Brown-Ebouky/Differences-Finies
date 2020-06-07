/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package differencesfinies;

import Interfaces.Fonction2D;
import Interfaces.Solver2D;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

/**
 *
 * @author E_DinaBrown
 */
public class Calculs2D implements Solver2D {

    private final double tol = 1E-9;

    @Override
    public Matrix solve2D(Fonction2D f, Matrix conn, int n, int m) {
 
        double[] x = Calculs.giveX(n);
        double[] y = Calculs.giveX(m);
   
        int order = (n - 1) * (m - 1);
        double h2 = 1.0 / Math.pow(n, 2);
        double k2 = 1.0 / Math.pow(m, 2);

        Matrix dense = mainMatrix(n, m, h2, k2);
        Matrix secondMembre = secondMembre(n, m, x, y, conn, h2, k2, f);
        
        Matrix res = dense.solve(secondMembre);

        /* int tempJ = 1;
        int tempI = 1;
        for (int i = 0; i < order; i++) {
            if (i % (n - 1) == 0) {
                if (i != 0) {
                    tempJ++;
                    tempI = 1;
                }
            }
            conn.setAsDouble(res.getAsDouble(i, 0), tempI, tempJ);
            
            //secondMembre.setAsDouble(f.val(x[tempI], y[tempJ]), i, 0);
            tempI++;

        } */
        
        return res;
    }

    public Matrix solveGaus(Fonction2D f, Matrix conn, int n, int m) {

        //ArrayList<Double> con, 
        double[] x = Calculs.giveX(n);
        double[] y = Calculs.giveX(m);

        int order = (n - 1) * (m - 1);
        double h2 = 1.0 / Math.pow(n, 2);
        double k2 = 1.0 / Math.pow(m, 2);

        Matrix dense = mainMatrix(n, m, h2, k2);
        Matrix secondMembre = secondMembre(n, m, x, y, conn, h2, k2, f);

        //Implémentation Gauss Seiden
        Matrix res = DenseMatrix.Factory.zeros(order, 1);

        boolean flag = true;
        while (flag) {
            for (int i = 0; i < order; i++) {
                double temp = secondMembre.getAsDouble(i, 0);
                for (int j = 0; j < i; j++) {
                    temp = temp - dense.getAsDouble(i, j) * res.getAsDouble(j, 0);
                }
                for (int j = i + 1; j < order; j++) {
                    temp = temp - dense.getAsDouble(i, j) * res.getAsDouble(j, 0);
                }
                res.setAsDouble(temp / dense.getAsDouble(i, i), i, 0);
            }

            Matrix temp = dense.mtimes(res);
            temp = temp.minus(secondMembre);
            // Calcul de la norme du vecteur
            double norTemp = temp.norm2();
            flag = norTemp > tol;
        }

        return res;
    }

    public Matrix solveSOR(Fonction2D f, Matrix conn, int n, int m) {
        //ArrayList<Double> con, 
        double[] x = Calculs.giveX(n);
        double[] y = Calculs.giveX(m);

        int order = (n - 1) * (m - 1);
        double h2 = 1.0 / Math.pow(n, 2);
        double k2 = 1.0 / Math.pow(m, 2);

        Matrix dense = mainMatrix(n, m, h2, k2);
        Matrix secondMembre = secondMembre(n, m, x, y, conn, h2, k2, f);

        // Implémentation de la relaxation SOR
        //Facteurs SOR
        double w = 1.2;

        Matrix res = DenseMatrix.Factory.zeros(order, 1);

        boolean flag = true;
        while (flag) {
            for (int i = 0; i < order; i++) {
                double temp = secondMembre.getAsDouble(i, 0);
                for (int j = 0; j < i; j++) {
                    temp = temp - dense.getAsDouble(i, j) * res.getAsDouble(j, 0);
                }
                for (int j = i + 1; j < order; j++) {
                    temp = temp - dense.getAsDouble(i, j) * res.getAsDouble(j, 0);
                }
                temp = temp * w / dense.getAsDouble(i, i);
                temp = temp + (1 - w) * res.getAsDouble(i, 0);
                res.setAsDouble(temp, i, 0);
            }

            Matrix temp = dense.mtimes(res);
            temp = temp.minus(secondMembre);
            // Calcul de la norme du vecteur
            double norTemp = temp.norm2();
            flag = norTemp > tol;
        }
        return res;
    }

    private Matrix mainMatrix(int n, int m, double h2, double k2) {
        int order = (n - 1) * (m - 1);

        Matrix dense = SparseMatrix.Factory.zeros(order, order);

        //Remplissage de la matrice principale
        for (int i = 0; i < order; i++) {
            dense.setAsDouble(2 / h2 + 2 / k2, i, i);
        }

        int temp = 0;
        for (int i = n-1; i < order; i++) {
            dense.setAsDouble(-1.0 / k2, temp, i);
            dense.setAsDouble(-1.0 / k2, i, temp);
            temp++;                
        }

        temp = 0;
        for (int i = 1; i < order; i++){
            if(i  % (n - 1) != 0) {
                dense.setAsDouble(-1.0 / h2, temp, i);
                dense.setAsDouble(-1.0 / h2, i, temp);
            }
            temp++;
        }
        
        return dense;
    }

    private Matrix secondMembre(int n, int m, double[] x, double[] y, Matrix conn, double h2, double k2, Fonction2D f) {
        int order = (n - 1) * (m - 1);
        int temp;
        // Remplissage du second membre
        Matrix secondMembre = DenseMatrix.Factory.zeros(order, 1);

        int tempI = 1, tempJ = 1;
        for (int i = 0; i < order; i++) {
            if (i % (n - 1) == 0) {
                if (i != 0) {
                    tempJ++;
                    tempI = 1;
                }
            }
            secondMembre.setAsDouble(f.val(x[tempI], y[tempJ]), i, 0);
            // System.out.println(tempI + " " + tempJ + " " + x[tempI] + " " + x[tempJ] + "  " + f.val(x[tempI], y[tempJ]));
            tempI++;

        }

        // System.out.println(secondMembre);
        // secondMembre.setAsDouble(f.val(x[2], y[2]), order / 2, 0);
        temp = (n - 1) * (m - 2);
        int temp2 = n + m + 1;
        for (int i = 0; i <= n - 2; i++) {
            secondMembre.setAsDouble(secondMembre.getAsDouble(i, 0) + conn.getAsDouble(i+1, m) / k2, i, 0);
            secondMembre.setAsDouble(secondMembre.getAsDouble(temp + i, 0) + conn.getAsDouble(i+1, 0) / k2, temp + i, 0);
            //con.get(i+1) * k2 + 
            //+ con.get(temp2 + i) * k2 
        }
        temp = 0;
        temp2 = 2 * n + m + 1;
        for (int i = 0; i <= m - 2; i++) {
            secondMembre.setAsDouble(secondMembre.getAsDouble(temp, 0) + conn.getAsDouble(0, i+1) / h2, temp, 0);
            secondMembre.setAsDouble(secondMembre.getAsDouble((n - 2) + temp, 0) + conn.getAsDouble(n, i+1) / h2, (n - 2) + temp, 0);
            //con.get(n+1+i) * h2 + 
            //con.get(temp2 + i) * h2 + 
            temp = temp + (n - 1);
        }
        return secondMembre;
    }
}
// 
