/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage.views_components;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Utilisateur
 */
public class Grille extends GridPane{
    private ArrayList <CarreSolutions> carresSolutions = new ArrayList<>();
    private double [][] u;
    public static double minVal, maxVal;
    public Grille(int n, int m){
        this.u = new double[n+1][m+1];
        this.initSolutionSquareList(n, m);
        this.setStyle("-fx-grid-lines-visible: true;");
        for(CarreSolutions carre : carresSolutions){
            this.add(carre, carre.getPositionX(), m-carre.getPositionY(), 1, 1);
            Tooltip t = new Tooltip("Coordonnées (x, y) : "+ this.coordonnees(carre.getPositionX(), carre.getPositionY(), n, m)+ ", Position : ("+ carre.getPositionX()+ ", "+ carre.getPositionY() +  "). Moyenne des 04 points : " + carre.moyenne.getText());
            Tooltip.install(carre, t);
        }
        
    }
    public String coordonnees(int positionXCarre, int positionYCarre, int maxX, int maxY){
        double x1 = (double)positionXCarre / maxX;
        double y1 = (double)positionYCarre / maxY;
        double x2 = x1 + 1.0 / maxX;
        double y2 = y1;
        double x3 = x1; 
        double y3 = y1 + 1.0 / maxY;
        double x4 = x1 + 1.0 / maxX;
        double y4 = y1 + 1.0 / maxY;
        
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(5);
        
        String coords = "("+f.format(x1)+", "+f.format(y1)+"); " + "("+f.format(x2)+", "+f.format(y2)+"); " + "("+f.format(x3)+", "+f.format(y3)+"); " + "("+f.format(x4)+", "+f.format(y4)+")";
        return coords;
    }
    public Grille (double [][] matrice, int n, int m){
        this.u = matrice;
        this.initSolutionSquareList(n, m);
        this.setStyle("-fx-grid-lines-visible: true;");
        for(CarreSolutions carre : carresSolutions){
            this.add(carre, carre.getPositionX(), m-carre.getPositionY()); 
            Tooltip t = new Tooltip("Coordonnées (x, y) : "+ this.coordonnees(carre.getPositionX(), carre.getPositionY(), n, m)+ ", Position : ("+ carre.getPositionX()+ ", "+ carre.getPositionY() +  "). Moyenne des 04 points : " + carre.moyenne.getText());
            Tooltip.install(carre, t);
        }
    }
    
    public double getMaxValueFromArray(double [][] arr, int n, int m){
        double maxValue = arr[0][0];
        for(int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if(maxValue < arr[i][j])
                    maxValue = arr[i][j];
            }
        }
        return maxValue;
    }
    
    public double getMinValueFromArray(double [][] arr, int n, int m){
        double minValue = arr[0][0];
        for(int i = 0; i < n; i++){
            for (int j = 0; j < m; j++){
                if (minValue > arr[i][j])
                    minValue = arr[i][j];         
            }
        }
        return minValue;
    }
    public void initSolutionSquareList(int n, int m){
        if (this.u != null){
            Grille.maxVal = this.getMaxValueFromArray(u, n, m);
            Grille.minVal = this.getMinValueFromArray(u, n, m);            
        }

        for(int i = 1; i<=n; i++){
            for (int j=1; j<=m; j++){
                if(this.u == null){
                    this.u[i-1][j-1] = Math.random();
                    this.u[i-1][j] = Math.random();
                    this.u[i][j-1] = Math.random();
                    this.u[i][j] = Math.random();
                }
                else{
                    double [][] points = new double[2][2];
                    points[0][0] = u[i-1][j-1];
                    points[0][1] = u[i-1][j];
                    points[1][0] = u[i][j-1];
                    points[1][1] = u[i][j];
                    this.carresSolutions.add(new CarreSolutions(i-1, j-1, points));
                    
                }
            }
        }
        
        
    }
    
}
