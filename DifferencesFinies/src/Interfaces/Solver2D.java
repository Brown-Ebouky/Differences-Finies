/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import org.ujmp.core.Matrix;

/**
 *
 * @author E_DinaBrown
 */
public interface Solver2D {
    
    public Matrix solve2D(Fonction f, Matrix con, int n, int m);
}
