/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage.views_components;

/**
 *
 * @author Utilisateur
 */



import javafx.scene.layout.Region;


public class ViewDimensionner {


    public static void bindSizes(Region child,Region parent, double widthPercentage, double heightPercentage){

        child.minWidthProperty().bind(parent.widthProperty().multiply(widthPercentage));
        child.maxWidthProperty().bind(parent.widthProperty().multiply(widthPercentage));

        child.minHeightProperty().bind(parent.heightProperty().multiply(heightPercentage));
        child.maxHeightProperty().bind(parent.heightProperty().multiply(heightPercentage));
    }


    public static void bindSizes(Region child,Region parent){
        bindSizes(child,parent,1,1);
    }

}



