package com.ajedrez.Logica;
import java.util.ArrayList;


public final class Reina extends Figura {

	public Reina(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.reina;
	}

	@Override
	public ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero) {
		ArrayList<Punto> r = new ArrayList<>();

		for(int i = position.i + 1, j = position.j; i < 8; i++){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i - 1, j = position.j; i >= 0; i--){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i, j = position.j - 1;j >= 0; j--){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i, j = position.j + 1; j < 8; j++){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i + 1, j = position.j + 1; i < 8 && j < 8; i++,j++){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i - 1, j = position.j - 1; i >= 0 && j >= 0; i--,j--){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i + 1, j = position.j - 1; i < 8 && j >= 0; i++,j--){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		for(int i = position.i - 1, j = position.j + 1; i >= 0 && j < 8; i--,j++){
			if(!hayFigura(tablero,i,j))r.add(new Punto(i,j));
			else if(hayFigura(tablero,i,j) && !mismoColor(tablero,i,j)){
				r.add(new Punto(i,j));
				break;
			}
			else break;
		}
		
		return r;
	}

	@Override
	public int valor(ArrayList<ArrayList<Figura>> tablero) {
		return 90 + this.posiblesMovimientos(tablero).size();
	}

	@Override
	public Figura clonar() {
		return new Reina(new Punto(position.i,position.j),color);
	}

}
