package com.beadando.javafxbeadando;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class Szal2 extends Thread{
    private final Label mokaslabel1; // Pass in the label as a parameter

    // Constructor to initialize with a Label reference
    public Szal2(Label mokaslabel1) {
        this.mokaslabel1 = mokaslabel1;
    }
    @Override
    public void run(){
        List<String> vaganylist = List.of(
              "Bajnok","Vagány","Király","Zatykó"
        );
        while(true){
            for(int i = 0; i < vaganylist.size(); i++){
                final int jelenlegi = i;
                Platform.runLater(() -> mokaslabel1.setText(vaganylist.get(jelenlegi)));
                try{
                    Thread.sleep(3000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

        }

    }
}
