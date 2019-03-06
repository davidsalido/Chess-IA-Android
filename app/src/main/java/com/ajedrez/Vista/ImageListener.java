package com.ajedrez.Vista;

import android.view.View;

public class ImageListener implements View.OnClickListener{

    private int i,j;
    private MainActivity m;
    private Thread t;

    public ImageListener(int i,int j, MainActivity m, Thread t){
        this.i = i;
        this.j = j;
        this.m = m;
        this.t = t;
    }


    @Override
    public void onClick(View v) {
        m.seleccionarCasilla(i,j);
        t.interrupt();
    }
}
