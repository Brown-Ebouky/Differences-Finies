/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package differencesfinies;

import Interfaces.Fonction;
import Interfaces.Solver;
import java.util.ArrayList;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;

/**
 *
 * @author E_DinaBrown
 */
public class Calculs implements Solver {
    
    private double tol = 1E-2;
    public Calculs() {
        
    }

    @Override
    public  Matrix solveDir(Fonction f, double a, double b, int n) {
        double h = 1.0 / n;
        int order = n - 1;
       
        double[] x = new double[n + 1];
        x[0] = a;
        x[n] = b;
        for(int i = 1; i < n; i++)
            x[i] = i*h;
        System.out.println(x[8]);
        
        // Rempissement de la matrice
        Matrix dense = SparseMatrix.Factory.zeros(order, order);
        for (int i = 0; i < order; i++) {
            dense.setAsDouble(2, i, i);
            if (i < n - 2) {
                dense.setAsDouble(-1, i + 1, i);
                dense.setAsDouble(-1, i, i + 1);
            }
        }

        // Remplissement du second membre
        Matrix secondMembre = DenseMatrix.Factory.zeros(order, 1);
        secondMembre.setAsDouble(Math.pow(h, 2)*f.val(x[1]) + a, 0, 0);
        secondMembre.setAsDouble(Math.pow(h, 2)*f.val(x[n-1]) + b, order - 1, 0);
        //System.out.println(secondMembre.getAsDouble(order-1, 0));
        for(int i = 1; i < order - 1; i++)
            secondMembre.setAsDouble(Math.pow(h, 2) * f.val(x[i+1]), i, 0);
        
        Matrix res0 = dense.solve(secondMembre);
        //System.out.println(dense);
        //System.out.println(res0);
        Matrix res = DenseMatrix.Factory.zeros(order + 2, 1);
        res.setAsDouble(a, 0, 0);
        res.setAsDouble(b, order + 1, 0);
        for(int i = 1; i < order+1; i++)
            res.setAsDouble(res0.getAsDouble(i-1, 0), i, 0);
       return res;
    }

    @Override
    public Matrix solveIt(Fonction f, double a, double b, int n) {
        
        double h = 1.0 / n;
        int order = n - 1;
       
        double[] x = new double[n + 1];
        x[0] = a;
        x[n] = b;
        for(int i = 1; i < n; i++)
            x[i] = i*h;
        //System.out.println(x);
        
        // Rempissement de la matrice
        Matrix dense = SparseMatrix.Factory.zeros(order, order);
        for (int i = 0; i < order; i++) {
            dense.setAsDouble(2, i, i);
            if (i < n - 2) {
                dense.setAsDouble(-1, i + 1, i);
                dense.setAsDouble(-1, i, i + 1);
            }
        }

        // Remplissement du second membre
        Matrix secondMembre = DenseMatrix.Factory.zeros(order, 1);
        secondMembre.setAsDouble(Math.pow(h, 2)*f.val(x[1]) + a, 0, 0);
        secondMembre.setAsDouble(Math.pow(h, 2)*f.val(x[n-1]) + b, order - 1, 0);
        //System.out.println(secondMembre.getAsDouble(order-1, 0));
        for(int i = 1; i < order - 1; i++)
            secondMembre.setAsDouble(Math.pow(h, 2) * f.val(x[i+1]), i, 0);
        
        Matrix res0 = Matrix.Factory.rand(n - 1, 1);
        boolean flag = true;
        
        while(flag) {
            for(int i = 0; i < n-2; i++) {
                double temp1 = 0, temp2 = 0;
                for(int j = i + 1; j < n - 1; j++) 
                    temp1 = temp1 + dense.getAsDouble(i, j)*res0.getAsDouble(j, 0);
                for(int j = 0; j < i - 1; j++)
                    temp2 = temp2 + dense.getAsDouble(i, j)*res0.getAsDouble(j, 0);
                res0.setAsDouble((secondMembre.getAsDouble(i, 0) - temp1 - temp2) / dense.getAsDouble(i, i), i, 0);
            }
            
            System.out.println("Hello guy i'm here");
            // Vecteur Ax_k - b
            Matrix temp = dense.mtimes(res0);
            temp = temp.minus(secondMembre);
            
            // Calcul de la norme du vecteur
            double norTemp = temp.norm2();
            System.out.println(norTemp);
            flag = Math.sqrt(norTemp) > tol;
        }
       
        Matrix res = DenseMatrix.Factory.zeros(order + 2, 1);
        res.setAsDouble(a, 0, 0);
        res.setAsDouble(b, order + 1, 0);
        for(int i = 1; i < order+1; i++)
            res.setAsDouble(res0.getAsDouble(i-1, 0), i, 0);
       return res;
    }
    
    

}
