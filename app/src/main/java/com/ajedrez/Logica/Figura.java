package com.ajedrez.Logica;

import java.util.ArrayList;

public abstract class Figura {

    protected Punto position;
    protected String color;
    protected TipoFigura tipo;

    public Figura(Punto p, String c){
        this.position = p;
        this.setColor(c);
    }

    public abstract ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero);

    public abstract int valor(ArrayList<ArrayList<Figura>> tablero);

    public abstract Figura clonar();

    public boolean posValida(int i, int j){
        return i >= 0 && i <= 7 && j >= 0 && j <= 7;
    }

    public boolean hayFigura(ArrayList<ArrayList<Figura>> tablero,int i ,int j){
        return tablero.get(i).get(j) != null;
    }

    public boolean mismoColor(ArrayList<ArrayList<Figura>> tablero,int i ,int j){
        return tablero.get(i).get(j).color.equals(color);
    }

    public void mover(Punto p){
        this.position = p;
    }

    public TipoFigura getTipo() {
        return tipo;
    }

    public void setTipo(TipoFigura tipo) {
        this.tipo = tipo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString(){
        return tipo + " " + color + " "+ position;
    }

    public Punto getPunto(){
        return position;
    }

    public boolean igual(Figura f){
        return this.tipo.equals(f.tipo) && this.position.igual(f.position) && this.color.equals(f.color);
    }

}
