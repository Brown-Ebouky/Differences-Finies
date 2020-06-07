/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage.views_components;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Utilisateur
 */
public class SolRepresentation extends VBox{
    
    public SolRepresentation(double [][] matrice , int n, int m){
        Grille grille = new Grille(matrice, n, m);
        LegendComponent legend = new LegendComponent(Grille.minVal, Grille.maxVal);
        grille.minHeight(400);
        
        this.getChildren().add(new LegendComponent(Grille.minVal, Grille.maxVal));
        this.getChildren().add(grille);
        this.setSpacing(40);
    }
    
}
