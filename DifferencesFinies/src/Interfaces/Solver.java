/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.util.ArrayList;
import org.ujmp.core.Matrix;

/**
 *
 * @author E_DinaBrown
 */
public interface Solver {
    Matrix solveDir(Fonction f, double a, double b, int n);
    /* 
    Cette interface permet de déterminer la méthode qui sera utilisée pour résoudre l'équation différentielle
    -u''=f sachant que u(0)=a, u(1)=b et en considérant un maillage sur n+1 points.
    */
    // Matrix solveIt(Fonction f, double a, double b, int n);
    
    // n: nombre de subdivisions
   
}
