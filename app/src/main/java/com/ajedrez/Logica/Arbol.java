package com.ajedrez.Logica;
import java.util.ArrayList;


public class Arbol {

	public static int maximaProfundidad = 4;
	public static final int infinito = 100000000;
	public static final int menosInfinito = -100000000;
	
	private Movimiento mejor;
	
	public Arbol(Tablero t,String color){
		ArrayList<Movimiento> movs = t.movimientosPosibles(color);
		int mejorValor = menosInfinito;
		for(Movimiento m:movs){
			t.mover(m);
			int v = valoraMin(t, 1, mejorValor, infinito,Arbol.colorContrario(color));
			t.deshacerMovimiento();
			if(v > mejorValor){
				mejorValor = v;
				mejor = m;
			}
		}
		
	}
	
	private int valoraMax(Tablero t, int p, int alfa, int beta,String color) {
		if(p == Arbol.maximaProfundidad){ //Hoja
			return t.valor(color);
		}
		else { 
			ArrayList<Movimiento> movs = t.movimientosPosibles(color);
			for(Movimiento m:movs) {
				t.mover(m);
				alfa = Math.max(alfa, valoraMin(t, p+1, alfa, beta,Arbol.colorContrario(color)));
				t.deshacerMovimiento();
				if (alfa >= beta) return alfa;
			}
			return alfa;
		}
	}
	
	private int valoraMin(Tablero t, int p, int alfa, int beta,String color) {
		if(p == Arbol.maximaProfundidad){ //Hoja
			return t.valor(color);
		}
		else { 
			ArrayList<Movimiento> movs = t.movimientosPosibles(color);
			for(Movimiento m:movs) {
				t.mover(m);
				beta = Math.min(valoraMax(t, p+1, alfa, beta,Arbol.colorContrario(color)), beta);
				t.deshacerMovimiento();
				if (alfa >= beta) return beta;
			}
			return beta;
		}
	}
	
	
	public Movimiento getMejor() {
		return mejor;
	}
	
	public static String colorContrario(String color){
		if(color.equals("negro")) return "blanco";
		else return "negro";
	}

}
