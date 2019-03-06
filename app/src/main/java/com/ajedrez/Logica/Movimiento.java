package com.ajedrez.Logica;

public class Movimiento {

    public Punto origen;
    public Punto destino;
    public boolean enroque;
    public Figura forigen;
    public Figura fdestino;

    public Movimiento(Punto o,Punto d){
        origen = o;
        destino = d;
    }

    public Movimiento(Punto o,Punto d,Figura fo, Figura fd, boolean e){
        origen = o;
        destino = d;
        forigen = fo;
        fdestino = fd;
        enroque = e;
    }
}
