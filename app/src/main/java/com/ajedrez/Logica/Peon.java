package com.ajedrez.Logica;

import java.util.ArrayList;


public class Peon extends Figura{

	boolean movido = false;
	
	public Peon(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.peon;
	}

	
	@Override
	public ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero) {
		ArrayList<Punto> r = new ArrayList<>();
		
		int fin = 2;
		if(!movido) ++fin;
		
		if(color.equals("negro")){
			for(int i = position.i + 1; i < position.i + fin && i < 8;i++){
				if(tablero.get(i).get(position.j) == null)r.add(new Punto(i,position.j));
				else break;
			}
			
			if(position.i != 7){
				if(position.j != 7 && hayFigura(tablero,position.i + 1,position.j + 1) && !mismoColor(tablero,position.i + 1,position.j + 1)){
					r.add(new Punto(position.i + 1,position.j + 1));
				}
				if(position.j != 0 && hayFigura(tablero,position.i + 1,position.j - 1) && !mismoColor(tablero,position.i + 1,position.j - 1)){
					r.add(new Punto(position.i + 1,position.j - 1));
				}
			}
		}
		else{
			for(int i = position.i - 1; i > position.i - fin && i >= 0;i--){
				if(tablero.get(i).get(position.j) == null) r.add(new Punto(i,position.j));
				else break;
			}
			if(position.i != 0){
				if(position.j != 7 && hayFigura(tablero,position.i - 1,position.j + 1) && !mismoColor(tablero,position.i - 1,position.j + 1)){
					r.add(new Punto(position.i - 1,position.j + 1));
				}
				if(position.j != 0 && hayFigura(tablero,position.i - 1,position.j - 1) && !mismoColor(tablero,position.i - 1,position.j - 1)){
					r.add(new Punto(position.i - 1,position.j - 1));
				}
			}
			
		}
		
			
		return r;
	}

	@Override
	public void mover(Punto p){
		this.position = p;
		movido = true;
	}


	@Override
	public int valor(ArrayList<ArrayList<Figura>> tablero) {
		return 10 + this.posiblesMovimientos(tablero).size();
	}


	@Override
	public Figura clonar() {
		Peon p = new Peon(new Punto(position.i,position.j),color);
		p.movido = movido;
		return p;
	}
}
