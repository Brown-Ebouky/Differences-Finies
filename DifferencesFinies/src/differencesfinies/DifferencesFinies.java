/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package differencesfinies;

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

       Calculs cal = new Calculs();
       Matrix res = cal.solveDir((x) -> {return x;}, 0, 1, 10);
       //Matrix res2 = cal.solveIt((x) -> {return 2*x;}, 0, 1, 10);
       //Matrix res3 = cal.solveDir((x) -> {return x*Math.cos(x)+2*Math.sin(x);}, 0, Math.cos(1), 40);
       System.out.println(res);
       System.out.println(res.getSize()[0]);
       //System.out.println(res2);
    }

}
