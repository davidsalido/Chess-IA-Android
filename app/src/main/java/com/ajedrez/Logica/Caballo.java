package com.ajedrez.Logica;
import java.util.ArrayList;


public class Caballo extends Figura {

	public Caballo(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.caballo;
	}

	
	
	@Override
	public ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero) {
		ArrayList<Punto> r = new ArrayList<>();
		
		int i[] = {1,-2,1,2,2,-1,-2,-1};
		int j[] = {-2,1,2,1,-1,2,-1,-2};
		
		for(int k = 0; k < 8;k++){
			if(super.posValida(position.i + i[k], position.j + j[k])){
				if(!hayFigura(tablero,position.i + i[k],position.j + j[k])) r.add(new Punto(position.i + i[k],position.j + j[k]));
				else if (!mismoColor(tablero,position.i + i[k],position.j + j[k])) r.add(new Punto(position.i + i[k],position.j + j[k]));
			}
		}
		
		
		return r;
	}



	@Override
	public int valor(ArrayList<ArrayList<Figura>> tablero) {
		return 30 + this.posiblesMovimientos(tablero).size();
	} 



	@Override
	public Figura clonar() {
		return new Caballo(new Punto(position.i,position.j),color);
	}

}
