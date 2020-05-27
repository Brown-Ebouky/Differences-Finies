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
       Calculs2D cal2 = new Calculs2D();
       Matrix conn = DenseMatrix.Factory.zeros(6, 6);
       
       Matrix res = cal2.solve2D((x, y)->{return x*y;}, conn, 5, 5);
       System.out.println(res);
       // Matrix el = SparseMatrix.Factory.fill(9E-2, 6,6);
       // System.out.println(el.compareTo(conn));
      // System.out.println(SparseMatrix.Factory.fill(2.0E-8, 5,5));
    }

}
