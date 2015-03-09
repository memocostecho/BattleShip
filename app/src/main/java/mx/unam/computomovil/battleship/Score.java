package mx.unam.computomovil.battleship;

import java.io.Serializable;

/**
 * Created by yasminegutierrez on 1/6/15.
 */
public class Score implements Serializable {


    private String nombre;
    private String score;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}

