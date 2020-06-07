/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage.views_components;

import java.text.DecimalFormat;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Utilisateur
 */
public class CarreSolutions extends Pane{
    private int positionX;
    private int positionY;
    private double [][] points;
    private final double moy;
    public Text moyenne;

    public CarreSolutions(int positionX, int positionY, double[][] points) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.points = points;
        this.setPrefSize(2000, 2000);
              
        double moy = 0;
        for(int m = 0; m<2; m++)
            for (int n=0; n<2; n++){
                moy += points[m][n];
            }
        
        moy = moy/4;
        this.moy = moy;
        double spaceScale = (Grille.maxVal - Grille.minVal)/4;
        double minVal = Grille.minVal;
        double maxVal = Grille.maxVal;
        
        if(moy >= minVal && moy < minVal + spaceScale){
            this.setStyle("-fx-background-color: #000000;");
        }
        else if(moy >= minVal + spaceScale && moy < minVal + 2*spaceScale){
            this.setStyle("-fx-background-color: rgb(80, 80, 80);");
        }
        else if(moy >= minVal + 2*spaceScale && moy < minVal + 3*spaceScale){
            this.setStyle("-fx-background-color: rgb(160, 160, 160);");
        }
        else {
            this.setStyle("-fx-background-color: #ffffff;");
        }
        
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(8);

        this.moyenne = new Text(f.format(this.moy));
        this.moyenne.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        this.moyenne.setFill(Color.WHITESMOKE);
        this.moyenne.wrappingWidthProperty().bind(this.widthProperty());
        this.moyenne.setX(0);
        this.moyenne.setY(25);
           
        this.setOnMouseEntered(((event) -> {
            //fixMoyenne();
        }));
        this.setOnMouseExited(((event) -> {
            //removeMoyenne();
        }));
        
    }
    
    public void fixMoyenne(){
        this.getChildren().add(moyenne);
    }
    
    public void removeMoyenne(){
        this.getChildren().remove(moyenne);    
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    
    
    
    
}
