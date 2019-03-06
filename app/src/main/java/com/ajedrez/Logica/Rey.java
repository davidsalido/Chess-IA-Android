package com.ajedrez.Logica;
import java.util.ArrayList;


public class Rey extends Figura{

	public Rey(Punto p, String c) {
		super(p, c);
		tipo = TipoFigura.rey;
	}

	@Override
	public ArrayList<Punto> posiblesMovimientos(ArrayList<ArrayList<Figura>> tablero) {
		ArrayList<Punto> r = new ArrayList<>();
		
		int i[] = {1,-1,1,-1,0,-1,1,0};
		int j[] = {1,1,-1,-1,-1,0,0,1};
		
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
		return 5000;
	}

	@Override
	public Figura clonar() {
		Rey r = new Rey(new Punto(position.i,position.j),color);
		r.nuncaMovido = nuncaMovido;
		return r;
	}
	
	@Override
	public void mover(Punto p){
		this.position = p;
		nuncaMovido = false;
	}
	
	public boolean nuncaMovido() {
		return nuncaMovido;
	}


	private boolean nuncaMovido = true;
	
	


}
