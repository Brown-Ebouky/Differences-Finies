/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Affichage;
import static javafx.application.Application.launch;


/**
 *
 * @author E_DinaBrown
 */

import differencesfinies.Calculs2D;
import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.ujmp.core.DenseMatrix;
import org.ujmp.core.Matrix;

import Affichage.views_components.SolRepresentation;


/**
 *
 * @author Utilisateur
 */
public class Affichage2D extends Application {
    
    
    public final int n = 30;
    
    public final int m = 50;
    @Override
    public void start(Stage primaryStage) {
        
        Calculs2D cal2 = new Calculs2D();
        // Matrix conn = DenseMatrix.Factory.zeros(n+1, m+1);
        Matrix conn = Tests.Test2D.matDiric("XSinY", n, m);
        Matrix res = cal2.solve2D((x, y)->{return x+y;}, conn, n, m);

        res = this.getMatrixFromRes(res);

        SolRepresentation view = new SolRepresentation(res.toDoubleArray(), n, m);
                
        StackPane root = new StackPane(view);
       
        root.setPadding(new Insets(40));
        
        
        Scene scene = new Scene(root, 600, 400);
        
        primaryStage.setTitle("Moyennes solutions");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public Matrix getMatrixFromRes(Matrix res){
        Matrix conn = DenseMatrix.Factory.zeros(n+1, m+1);
        int order = (n - 1)  * (m - 1);
        int tempJ = 1;
        int tempI = 1;
        for (int i = 0; i < order; i++) {
            if (i % (n - 1) == 0) {
                if (i != 0) {
                    tempJ++;
                    tempI = 1;
                }
            }
            conn.setAsDouble(res.getAsDouble(i, 0), tempI, tempJ);            
            tempI++;

        }
        return conn;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}