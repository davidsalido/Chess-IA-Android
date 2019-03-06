package com.ajedrez.Logica;

import android.text.style.TtsSpan;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Tablero {

    public ArrayList<ArrayList<Figura>> figuras;
    public Deque<Movimiento> movimientos;

    public Tablero() {
        figuras = new ArrayList<>(8);
        movimientos = new LinkedList<Movimiento>();
    }

    public Tablero(ArrayList<ArrayList<Figura>> f){
        figuras = new ArrayList<>(8);
        for(int i = 0; i < 8;i++){
            figuras.add(new ArrayList<Figura>(8));
            for(int j = 0; j < 8;j++){
                if(f.get(i).get(j) != null) figuras.get(i).add(f.get(i).get(j).clonar());
                else figuras.get(i).add(null);
            }
        }
        movimientos = new LinkedList<Movimiento>();
    }

    public int mover(Movimiento m){
        if(esCoronacion(m)) {
            Figura destino = null;
            if(figuras.get(m.destino.i).get(m.destino.j) != null) destino = figuras.get(m.destino.i).get(m.destino.j).clonar();
            Movimiento mov = new Movimiento(m.origen,m.destino,figuras.get(m.origen.i).get(m.origen.j).clonar(),destino,false);
            movimientos.addLast(mov);
            coronacion(m);
            return 1;
        }
        else if(esEnroque(m)) {
            Movimiento mov = new Movimiento(m.origen,m.destino,figuras.get(m.origen.i).get(m.origen.j).clonar(),figuras.get(m.destino.i).get(m.destino.j).clonar(),true);
            movimientos.addLast(mov);
            enroque(m);
            return 2;
        }
        else {
            Figura destino = null;
            if(figuras.get(m.destino.i).get(m.destino.j) != null) destino = figuras.get(m.destino.i).get(m.destino.j).clonar();
            Movimiento mov = new Movimiento(m.origen,m.destino,figuras.get(m.origen.i).get(m.origen.j).clonar(),destino,false);
            movimientos.addLast(mov);
            figuras.get(m.destino.i).set(m.destino.j, figuras.get(m.origen.i).get(m.origen.j));
            figuras.get(m.destino.i).get(m.destino.j).mover(m.destino);
            figuras.get(m.origen.i).set(m.origen.j, null);
            return 0;
        }

    }

    public void deshacerMovimiento(){
        Movimiento m = movimientos.pollLast();
        if(!m.enroque){
            figuras.get(m.destino.i).set(m.destino.j, m.fdestino);
            figuras.get(m.origen.i).set(m.origen.j, m.forigen);
        }
        else{
            if(m.origen.j == 0) {
                figuras.get(m.origen.i).set(m.origen.j + 3,null);
                figuras.get(m.destino.i).set(m.destino.j - 2,null);
            }
            else {
                figuras.get(m.origen.i).set(m.origen.j - 2,null);
                figuras.get(m.destino.i).set(m.destino.j + 2,null);
            }
            figuras.get(m.destino.i).set(m.destino.j,m.fdestino);
            figuras.get(m.origen.i).set(m.origen.j,m.forigen);
        }
    }

    private boolean esEnroque(Movimiento m) {
        if(figuras.get(m.origen.i).get(m.origen.j) instanceof Torre)
            if(figuras.get(m.destino.i).get(m.destino.j) != null)
                if(figuras.get(m.destino.i).get(m.destino.j) instanceof Rey
                        && figuras.get(m.destino.i).get(m.destino.j).getColor().equals(figuras.get(m.origen.i).get(m.origen.j).getColor()))
                    return true;
        return false;
    }

    private void enroque(Movimiento m) {
        if(m.origen.j == 0) {
            figuras.get(m.origen.i).set(m.origen.j + 3, figuras.get(m.origen.i).get(m.origen.j));
            figuras.get(m.origen.i).get(m.origen.j + 3).mover(new Punto(m.origen.i,m.origen.j+3));

            figuras.get(m.destino.i).set(m.destino.j - 2, figuras.get(m.destino.i).get(m.destino.j));
            figuras.get(m.destino.i).get(m.destino.j - 2).mover(new Punto(m.destino.i,m.destino.j-2));
        }
        else {
            figuras.get(m.origen.i).set(m.origen.j - 2, figuras.get(m.origen.i).get(m.origen.j));
            figuras.get(m.origen.i).get(m.origen.j - 2).mover(new Punto(m.origen.i,m.origen.j-2));

            figuras.get(m.destino.i).set(m.destino.j + 2, figuras.get(m.destino.i).get(m.destino.j));
            figuras.get(m.destino.i).get(m.destino.j + 2).mover(new Punto(m.destino.i,m.destino.j+2));
        }
        figuras.get(m.origen.i).set(m.origen.j, null);
        figuras.get(m.destino.i).set(m.destino.j, null);
    }

    private void coronacion(Movimiento m) {
        Figura f = figuras.get(m.origen.i).get(m.origen.j);

        figuras.get(m.destino.i).set(m.destino.j, new Reina(f.getPunto(),f.getColor()));
        figuras.get(m.destino.i).get(m.destino.j).mover(m.destino);
        figuras.get(m.origen.i).set(m.origen.j, null);
    }

    private boolean esCoronacion(Movimiento m) {
        Figura f = figuras.get(m.origen.i).get(m.origen.j);
        if(f instanceof Peon) {
            if(f.getColor().equals("blanco") && m.destino.i == 0)
                return true;
            if(f.getColor().equals("negro") && m.destino.i == 7)
                return true;
        }
        return false;
    }

    public Tablero hacerMovimiento(Movimiento m){
        Tablero t = new Tablero(figuras);
        t.mover(m);
        return t;
    }

    public int valor(String color){
        int v = 0;
        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8;j++){
                if(figuras.get(i).get(j) != null){
                    if(figuras.get(i).get(j).getColor().equals(color)) v+= figuras.get(i).get(j).valor(figuras);
                    else v-= figuras.get(i).get(j).valor(figuras);
                }
            }
        }
        return v;
    }

    public ArrayList<Movimiento> movimientosPosibles(String color){
        ArrayList<Movimiento> r = new ArrayList<>(40);

        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8;j++){
                if(figuras.get(i).get(j) != null){
                    if(figuras.get(i).get(j).getColor().equals(color)){
                        ArrayList<Punto> destinos = figuras.get(i).get(j).posiblesMovimientos(figuras);
                        for(Punto p:destinos){
                            r.add(new Movimiento(figuras.get(i).get(j).getPunto(),p));
                        }
                    }
                }
            }
        }

        return r;
    }


    public String toString(){
        String r="";

        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8;j++){
                if(figuras.get(i).get(j) != null) r += " " + figuras.get(i).get(j) + " ";
                else  r += " nadaa ";
            }
            r += '\n';
        }

        return r;
    }

    public boolean igual(Tablero t){
        for(int i = 0; i < 8;i++){
            for(int j = 0; j < 8;j++){
                if(figuras.get(i).get(j) == null){
                    if(t.figuras.get(i).get(j) != null){
                        return false;

                    }
                }
                else if(!figuras.get(i).get(j).igual(t.figuras.get(i).get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean jaque(String colorRey) {
        for(int i = 0; i <8;i++){
            for(int j = 0; j < 8;j++){
                if(figuras.get(i).get(j) != null && !figuras.get(i).get(j).getColor().equals(colorRey)) {
                    ArrayList<Punto> movs = figuras.get(i).get(j).posiblesMovimientos(figuras);
                    for(Punto p:movs) {
                        if(figuras.get(p.i).get(p.j) instanceof Rey && figuras.get(p.i).get(p.j).getColor().equals(colorRey))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    //Ya se sabe que hay jaque
    public boolean jaqueMate(String colorRey){
        ArrayList<Movimiento> movis = this.movimientosPosibles(colorRey);
        for(Movimiento m:movis){
            if(!this.hacerMovimiento(m).jaque(colorRey))
                return false;
        }
        return true;
    }

    public static Tablero tableroPorDefecto(){
        Tablero tablero = new Tablero();

        tablero.figuras.add(new ArrayList<Figura>());
        tablero.figuras.get(0).add(new Torre(new Punto(0,0),"negro"));
        tablero.figuras.get(0).add(new Caballo(new Punto(0,1),"negro"));
        tablero.figuras.get(0).add(new Alfil(new Punto(0,2),"negro"));
        tablero.figuras.get(0).add(new Reina(new Punto(0,3),"negro"));
        tablero.figuras.get(0).add(new Rey(new Punto(0,4),"negro"));
        tablero.figuras.get(0).add(new Alfil(new Punto(0,5),"negro"));
        tablero.figuras.get(0).add(new Caballo(new Punto(0,6),"negro"));
        tablero.figuras.get(0).add(new Torre(new Punto(0,7),"negro"));

        tablero.figuras.add(new ArrayList<Figura>());
        for(int j = 0; j < 8;j++){
            tablero.figuras.get(1).add(new Peon(new Punto(1,j),"negro"));
        }

        for(int i = 2; i < 6;i++){
            tablero.figuras.add(new ArrayList<Figura>());
            for(int j = 0; j < 8;j++){
                tablero.figuras.get(i).add(null);
            }
        }

        tablero.figuras.add(new ArrayList<Figura>());
        for(int j = 0; j < 8;j++){
            tablero.figuras.get(6).add(new Peon(new Punto(6,j),"blanco"));
        }

        tablero.figuras.add(new ArrayList<Figura>());
        tablero.figuras.get(7).add(new Torre(new Punto(7,0),"blanco"));
        tablero.figuras.get(7).add(new Caballo(new Punto(7,1),"blanco"));
        tablero.figuras.get(7).add(new Alfil(new Punto(7,2),"blanco"));
        tablero.figuras.get(7).add(new Reina(new Punto(7,3),"blanco"));
        tablero.figuras.get(7).add(new Rey(new Punto(7,4),"blanco"));
        tablero.figuras.get(7).add(new Alfil(new Punto(7,5),"blanco"));
        tablero.figuras.get(7).add(new Caballo(new Punto(7,6),"blanco"));
        tablero.figuras.get(7).add(new Torre(new Punto(7,7),"blanco"));

        return tablero;
    }
}