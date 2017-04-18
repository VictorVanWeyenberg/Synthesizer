/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import domein.Synth;
import gui.MainPane;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;

/**
 *
 * @author victor
 */
public class StartUpFXML extends Application {

    @Override
    public void start(Stage stage) {
        Synth dc;
        try {
            dc = new Synth();
            MainPane mp = new MainPane(dc);
            Scene scene = new Scene(mp);
            scene.focusOwnerProperty().addListener((obs, oldV, newV) -> {
                mp.setFocusOwner(newV);
                System.out.println(newV);
                    });
            stage.setScene(scene);
            stage.setFullScreen(true);
            stage.setTitle("Synthesizer");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
