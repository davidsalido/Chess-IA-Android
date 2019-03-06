package com.ajedrez.Logica;
import java.util.ArrayList;


public class Torre extends Figura {

	public Torre(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.torre;
	}
	
	private boolean nuncaMovido = true;

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
		
		if(nuncaMovido){//Comprobar enroque
			int j = position.j;
			
			if(position.j == 0)j++;
			else j--;
			
			int cont = 0;
			
			while(tablero.get(position.i).get(j) == null && cont <= 4) {
				cont++;
				if(position.j == 0)j++;
				else j--;
			}
			
			if(tablero.get(position.i).get(j) instanceof Rey && ((Rey) tablero.get(position.i).get(j)).nuncaMovido()) {
				r.add(new Punto(position.i,j));
			}
		}
		
		
		return r;
	}

	@Override
	public int valor(ArrayList<ArrayList<Figura>> tablero) {
		return 50 + this.posiblesMovimientos(tablero).size();
	}

	@Override
	public Figura clonar() {
		Torre t = new Torre(new Punto(position.i,position.j),color);
		t.nuncaMovido = nuncaMovido;
		return t;
	}

	
	@Override
	public void mover(Punto p){
		this.position = p;
		nuncaMovido = false;
	}
}
