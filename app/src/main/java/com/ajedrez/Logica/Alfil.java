package com.ajedrez.Logica;
import java.util.ArrayList;


public class Alfil extends Figura{

	public Alfil(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.alfil;
	}

	@Override
	public ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero) {
		ArrayList<Punto> r = new ArrayList<>();

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
		return 30 + this.posiblesMovimientos(tablero).size();
	}

	@Override
	public Figura clonar() {
		return new Alfil(new Punto(position.i,position.j),color);
	}

}
