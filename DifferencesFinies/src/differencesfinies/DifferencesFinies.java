/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package differencesfinies;

import java.util.ArrayList;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.util.MathUtil;

/**
 *
 * @author E_DinaBrown
 */
public class DifferencesFinies {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

     /*  Calculs cal = new Calculs();
       Matrix res = cal.solveDir((x) -> {return x;}, 0, 1, 10);
       //Matrix res2 = cal.solveIt((x) -> {return 2*x;}, 0, 1, 10);
       //Matrix res3 = cal.solveDir((x) -> {return x*Math.cos(x)+2*Math.sin(x);}, 0, Math.cos(1), 40);
       System.out.println(res);
       System.out.println(res.getSize()[0]);
       //System.out.println(res2); */
       Calculs2D cal2 = new Calculs2D();
       ArrayList<Double> con = new ArrayList<>(16);
       for(int i=0; i<=15; i++)
           con.add(i / 15.0);
       
       
       Matrix conn = DenseMatrix.Factory.zeros(6, 6);
       
       Matrix res = cal2.solveSOR((x, y)->{return x*y;}, conn, 5, 5);
       System.out.println(res);
       // Matrix el = SparseMatrix.Factory.fill(9E-2, 6,6);
       // System.out.println(el.compareTo(conn));
      // System.out.println(SparseMatrix.Factory.fill(2.0E-8, 5,5));
    }

}
