package com.ajedrez.Logica;

public class Punto {
    public int i,j;

    public Punto(int i, int j){
        this.i = i;
        this.j = j;
    }

    public String toString(){
        return i + " "+ j;
    }

    public boolean igual(Punto p){
        return i == p.i && j == p.j;
    }
}

