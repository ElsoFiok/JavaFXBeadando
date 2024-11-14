package com.beadando.javafxbeadando;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

public class Szal extends Thread{
    private final Label mokaslabel2; // Pass in the label as a parameter

    // Constructor to initialize with a Label reference
    public Szal(Label mokaslabel2) {
        this.mokaslabel2 = mokaslabel2;
    }

    @Override
    public void run(){
        List<String> carBrandsList = List.of(
                "Tesla", "Honda", "Ford", "BMW", "Mercedes", "Audi",
                "Skoda", "Nissan", "Volkswagen", "Hyundai"
        );
        while(true){
            for(int i = 0; i < carBrandsList.size(); i++){
                final int jelenlegi = i;
                Platform.runLater(() -> mokaslabel2.setText(carBrandsList.get(jelenlegi)));
                try{
                    Thread.sleep(8000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }

        }

    }
}
