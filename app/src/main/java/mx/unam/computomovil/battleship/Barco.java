package mx.unam.computoMovil.battleship;

import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by yasminegutierrez on 11/27/14.
 */
public class Barco {

    public ImageView imagenBarco;
    public ArrayList<String> posicionesBarco;

    public Barco(ImageView imagenBarco, ArrayList<String> posicionesBarco) {
        this.imagenBarco = imagenBarco;
        this.posicionesBarco = posicionesBarco;
    }


    public ImageView getImagenBarco() {
        return imagenBarco;
    }

    public void setImagenBarco(ImageView imagenBarco) {
        this.imagenBarco = imagenBarco;
    }

    public ArrayList<String> getPosicionesBarco() {
        return posicionesBarco;
    }

    public void setPosicionesBarco(ArrayList<String> posicionesBarco) {
        this.posicionesBarco = posicionesBarco;
    }
}
