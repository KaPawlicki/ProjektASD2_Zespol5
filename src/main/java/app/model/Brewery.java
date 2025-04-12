package app.model;

import java.awt.*;

public class Brewery extends Node{
    private static final int BEER_PER_TON_OF_BARLEY = 10; // stały przelicznik ile z tony jęczmienia można wytworzyć piwa
    private static int amountOfBarleyProcessed = 10; // ile dany browar może przetworzyć jęczmienia w tonach
    private int currentAmountOfBeerProduced; // aktualana ilość wyprodukowanego piwa

    public Brewery(String type, Point position) {
        super(type, position);
        this.currentAmountOfBeerProduced = 0;
    }

    public static int getAmountOfBarleyProcessed() {return amountOfBarleyProcessed;}
    public static void setAmountOfBarleyProcessed(int amount) {amountOfBarleyProcessed = amount;}
    public int getCurrentAmountOfBeerProduced() {return currentAmountOfBeerProduced;}
    public void setCurrentAmountOfBeerProduced(int currentAmountOfBeerProduced) {this.currentAmountOfBeerProduced = Math.max(0, currentAmountOfBeerProduced);}

    // produkowanie piwa w danym browarze
    public int processBarley(int availableBarley) {
        // Oblicz, ile możemy przetworzyć (do limitu mocy przerobowej)
        int barleyToProcess = Math.min(availableBarley, amountOfBarleyProcessed);
        // Przelicz na piwo
        int producedBeer = barleyToProcess * BEER_PER_TON_OF_BARLEY;
        currentAmountOfBeerProduced += producedBeer;
        return producedBeer;
    }


    @Override
    public String toString() {
        String s = "id: " + getId() + ", type: " + getType() + ", position: " + getPosition() + "amountOfBarleyProcessed: " + amountOfBarleyProcessed + ", currentAmountOfBeerProduced: " + currentAmountOfBeerProduced;
        if(listOfEdges.size()>0){
            s += "\n\tlistOfEdges: ";
            for(Edge e:listOfEdges){
                s += ", " + e;
            }
        }
        return s;
    }
}

