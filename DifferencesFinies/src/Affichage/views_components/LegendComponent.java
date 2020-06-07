/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage.views_components;

import java.text.DecimalFormat;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author Utilisateur
 */
public final class LegendComponent extends VBox{
    
    public LegendComponent(double minVal, double maxVal){
        super();
        double spaceScale = (maxVal - minVal)/4;
        this.getChildren().add(this.rowForLegend("#000000", minVal, minVal + spaceScale));
        this.getChildren().add(this.rowForLegend("rgb(80, 80, 80)", minVal + spaceScale, minVal + 2*spaceScale));
        this.getChildren().add(this.rowForLegend("rgb(160, 160, 160)", minVal + 2*spaceScale, minVal + 3*spaceScale));
        this.getChildren().add(this.rowForLegend("#ffffff", minVal + 3*spaceScale, maxVal));
        this.minHeight(200);
    }
    
    public HBox rowForLegend(String color, double min, double max){
        HBox rowLegend = new HBox();
        rowLegend.setSpacing(20);
        Pane colorPane = new Pane();
        colorPane.setPrefSize(40, 40);
        colorPane.setStyle("-fx-background-color: "+color+";");
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(8);
        rowLegend.getChildren().add(colorPane);
        Label labelLegend = new Label(f.format(min) + "   à   "+ f.format(max));
        labelLegend.setTextAlignment(TextAlignment.CENTER);
        
        rowLegend.getChildren().add(labelLegend);
        return rowLegend;
    }

}
