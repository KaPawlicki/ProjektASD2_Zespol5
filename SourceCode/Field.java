package SourceCode;

import javax.swing.*;
import java.awt.*;

public class Field extends Node{
    private int barleyAmount; // ilość wyrastanego jęczmienia
    private int currentAmountOfBarleyInTheField; // dostepna ilosc jeczmienia do zebrania

    public Field(String type, Point position, int barleyAmount) {
        super(type, position);
        this.barleyAmount = barleyAmount;
        this.currentAmountOfBarleyInTheField = barleyAmount;
    }

    public int getBarleyAmount() {return barleyAmount;}
    public void setBarleyAmount(int barleyAmount) {this.barleyAmount = barleyAmount;}
    public int getCurrentAmountOfBarleyInTheField() {return currentAmountOfBarleyInTheField;}
    public void setCurrentAmountOfBarleyInTheField(int currentAmountOfBarleyInTheField) {this.currentAmountOfBarleyInTheField = currentAmountOfBarleyInTheField;}

    @Override
    public String toString() {
        String s = "id: " + getId() + ", type: " + getType() + ", position: " + getPosition() + ", barleyAmount: " + barleyAmount + ", currentAmountOfBarley: " + currentAmountOfBarleyInTheField;
        if(listOfEdges.size()>0){
            s += "\n\tlistOfEdges: ";
            for(Edge e:listOfEdges){
                s += ", " + e;
            }
        }
        return s;
    }
}
