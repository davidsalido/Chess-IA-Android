package com.ajedrez.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ajedrez.Logica.Arbol;
import com.ajedrez.Logica.Movimiento;
import com.ajedrez.Logica.Punto;
import com.ajedrez.Logica.Rey;
import com.ajedrez.Logica.Tablero;
import com.ajedrez.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<ArrayList<ImageView>> iv; //Celdas del tablero
    public ArrayList<ArrayList<Drawable>> dw;  //Fondos de las celdas del tablero cuando empieza el juego
    private ArrayList<ArrayList<Drawable>> dwMarked;  //Fondos de las celdas del tablero cuando empieza el juego
    private ImageView ivTurno;
    private Tablero tablero;
    private String[] colores = {"blanco","negro"};
    private Punto ultimoPuntoMarcado;
    private ImageView atras;
    private boolean jugador1, jugador2;


    private int idFondo(int i, int j){
        if(i % 2 == 0 && j % 2 == 0)
            return R.color.colorAccent;
        if(i % 2 == 0 && j % 2 == 1)
            return R.color.colorPrimaryDark;
        if(i % 2 == 1 && j % 2 == 0)
            return R.color.colorPrimaryDark;
        else
            return R.color.colorAccent;
    }

    private int idFondoMarked(int i, int j){
        if(i % 2 == 0 && j % 2 == 0)
            return R.color.colorAccentMarked;
        if(i % 2 == 0 && j % 2 == 1)
            return R.color.colorPrimary;
        if(i % 2 == 1 && j % 2 == 0)
            return R.color.colorPrimary;
        else
            return R.color.colorAccentMarked;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        jugador1 = intent.getStringExtra("jugadorblanco").equals("Persona");
        jugador2 = intent.getStringExtra("jugadornegro").equals("Persona");

        ivTurno = findViewById(R.id.ivTurno);
        iv = new ArrayList<>(8);
        dw = new ArrayList<>(8);
        dwMarked = new ArrayList<>(8);
        for(int i=0; i<8; i++) {
            iv.add(new ArrayList<ImageView>(8));
            dw.add(new ArrayList<Drawable>(8));
            dwMarked.add(new ArrayList<Drawable>(8));
            for(int j=0; j<8; j++) {
                String imageViewID = "iv" + i + j;
                int resID = getResources().getIdentifier(imageViewID, "id", getPackageName());
                iv.get(i).add((ImageView) findViewById(resID));
                iv.get(i).get(j).setBackgroundResource(idFondo(i,j));
                dw.get(i).add(getResources().getDrawable(idFondo(i,j)));
                dwMarked.get(i).add(getResources().getDrawable(idFondoMarked(i,j)));
            }
        }

        Thread t = new Thread(new Runnable(){

            @Override
            public void run() {
                jugar();
            }

        });
        t.start();

        atras = findViewById(R.id.atras);

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askOption().show();
            }
        });

    }

    private AlertDialog askOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Menu Principal")
                .setMessage("¿Quiere ir al menu principal?")

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        startActivity(new Intent(MainActivity.this,Principal.class));
                        dialog.dismiss();
                        finish();
                    }

                })



                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void jugar(){
        int turno = 0;
        tablero = Tablero.tableroPorDefecto();


        boolean juegoAcabado = false;
        while(!juegoAcabado) {

            final int finalTurno = turno;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   if(finalTurno == 0) ivTurno.setImageResource(R.drawable.peon_blanco);
                   else ivTurno.setImageResource(R.drawable.peon_negro);
                }
            });
            if (turno == 0) {
                if(jugador1)
                    decidirJugada(colores[finalTurno]);
                else{
                    Arbol a = new Arbol(new Tablero(tablero.figuras), colores[turno]);
                    Movimiento m = a.getMejor();
                    this.mover(m.origen, m.destino, colores[turno],true);
                }
            } else {
                if(jugador2)
                    decidirJugada(colores[finalTurno]);
                else{
                    Arbol a = new Arbol(new Tablero(tablero.figuras), colores[turno]);
                    Movimiento m = a.getMejor();
                    this.mover(m.origen, m.destino, colores[turno],true);
                }
            }

            if (tablero.jaque(colorContrario(colores[turno]))) {
                if (tablero.jaqueMate(colorContrario(colores[turno]))) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Jaque mate al rey", Toast.LENGTH_SHORT).show();
                        }
                    });
                    juegoAcabado = true;
                } else runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Jaque al rey", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            turno = ++turno % 2;
        }
    }

    private int selecti, selectj;

    private void decidirJugada(String color){
        for(int i = 0; i <8;i++){
            for(int j = 0; j < 8;j++){
                if(tablero.figuras.get(i).get(j) != null && tablero.figuras.get(i).get(j).getColor().equals(color)){
                    final int finalJ = j;
                    final int finalI = i;
                    final Thread t = Thread.currentThread();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.get(finalI).get(finalJ).setOnClickListener(new ImageListener(finalI, finalJ,MainActivity.this,t));
                        }
                    });
                }
            }
        }

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) { }

        for(int i = 0; i <8;i++){
            for(int j = 0; j < 8;j++){
                if(tablero.figuras.get(i).get(j) != null && tablero.figuras.get(i).get(j).getColor().equals(color)){
                    iv.get(i).get(j).setOnClickListener(null);
                    if(!(tablero.figuras.get(i).get(j) instanceof Rey))
                        iv.get(i).get(j).setOnClickListener(new ImageListener(-1,-1,this,Thread.currentThread()));
                }
            }
        }

        Punto origen = new Punto(selecti, selectj);


        final ArrayList<Punto> movimientos = tablero.figuras.get(selecti).get(selectj).posiblesMovimientos(tablero.figuras);


        if(movimientos.isEmpty()) {
            for(int i = 0; i <8;i++){
                for(int j = 0; j < 8;j++){
                    if(tablero.figuras.get(i).get(j) != null && tablero.figuras.get(i).get(j).getColor().equals(color)){
                        if(!(tablero.figuras.get(i).get(j) instanceof Rey))
                            iv.get(i).get(j).setOnClickListener(null);
                    }
                }
            }
            decidirJugada(color);
            return;
        }



        final Thread t = Thread.currentThread();
        for(final Punto p: movimientos) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    iv.get(p.i).get(p.j).setBackground(dwMarked.get(p.i).get(p.j));
                    iv.get(p.i).get(p.j).setOnClickListener(new ImageListener(p.i, p.j, MainActivity.this, t));
                }
            });

        }


        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) { }

        if(selecti == -1){

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(final Punto p: movimientos) {
                        iv.get(p.i).get(p.j).setBackground(dw.get(p.i).get(p.j));
                        iv.get(p.i).get(p.j).setOnClickListener(null);
                    }
                }
            });


            for(int i = 0; i <8;i++){
                for(int j = 0; j < 8;j++){
                    if(tablero.figuras.get(i).get(j) != null && tablero.figuras.get(i).get(j).getColor().equals(color)){
                        if(!(tablero.figuras.get(i).get(j) instanceof Rey))
                            iv.get(i).get(j).setOnClickListener(null);
                    }
                }
            }
            decidirJugada(color);
            return;
        }

        Punto destino = new Punto(selecti,selectj);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for(Punto p: movimientos) {
                    iv.get(p.i).get(p.j).setBackground(dw.get(p.i).get(p.j));
                    iv.get(p.i).get(p.j).setOnClickListener(null);
                }
            }
        });

        for(int i = 0; i <8;i++){
            for(int j = 0; j < 8;j++){
                if(tablero.figuras.get(i).get(j) != null && tablero.figuras.get(i).get(j).getColor().equals(color)){
                    if(!(tablero.figuras.get(i).get(j) instanceof Rey))
                        iv.get(i).get(j).setOnClickListener(null);
                }
            }
        }
        mover(origen,destino,color, false);
    }

    public void seleccionarCasilla(int i, int j){
        this.selecti = i;
        this.selectj = j;
    }

    public void mover(final Punto origen, final Punto destino,final String color,final boolean ia){
        Tablero taux = new Tablero(tablero.figuras);
        final int r = tablero.mover(new Movimiento(origen,destino));

        if(tablero.jaque(color)) {
            tablero = taux;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Seria jaque", Toast.LENGTH_SHORT).show();
                }
            });
            this.decidirJugada(color);
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(ultimoPuntoMarcado != null){
                    iv.get(ultimoPuntoMarcado.i).get(ultimoPuntoMarcado.j).setBackground(dw.get(ultimoPuntoMarcado.i).get(ultimoPuntoMarcado.j));
                    ultimoPuntoMarcado = null;
                }
                if(r == 0) {
                    Drawable icon = iv.get(origen.i).get(origen.j).getDrawable();
                    iv.get(destino.i).get(destino.j).setImageDrawable(icon);
                    iv.get(origen.i).get(origen.j).setImageDrawable(null);

                }
                if(r == 1) {
                    if(color.equals("blanco")) iv.get(destino.i).get(destino.j).setImageResource(R.drawable.reina_blanco);
                    else iv.get(destino.i).get(destino.j).setImageResource(R.drawable.reina_negro);
                    iv.get(origen.i).get(origen.j).setImageDrawable(null);
                }
                else if(r == 2) {
                    if(origen.j == 0) {
                        Drawable icon = iv.get(origen.i).get(origen.j).getDrawable();
                        iv.get(origen.i).get(origen.j + 3).setImageDrawable(icon);

                        Drawable icon2 = iv.get(destino.i).get(destino.j).getDrawable();
                        iv.get(destino.i).get(destino.j - 2).setImageDrawable(icon2);
                    }
                    else {
                        Drawable icon = iv.get(origen.i).get(origen.j).getDrawable();
                        iv.get(origen.i).get(origen.j - 2).setImageDrawable(icon);

                        Drawable icon2 = iv.get(destino.i).get(destino.j).getDrawable();
                        iv.get(destino.i).get(destino.j + 2).setImageDrawable(icon2);
                    }
                    iv.get(destino.i).get(destino.j).setImageDrawable(null);
                    iv.get(origen.i).get(origen.j).setImageDrawable(null);
                }
                if(ia){
                    iv.get(destino.i).get(destino.j).setBackground(dwMarked.get(destino.i).get(destino.j));
                    ultimoPuntoMarcado = destino;
                }
            }
        });

    }

    private String colorContrario(String color){
        if(color.equals("negro")) return "blanco";
        else return "negro";
    }

    @Override
    public void onBackPressed() {
        //Nothing
    }
}
