package mx.unam.computoMovil.battleship;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Stack;

import mx.unam.computoMovil.battleship.R;

public class BTGame extends android.support.v4.app.Fragment {

    ImageView ivAuxiliar;
    ImageView barco4;
    ImageView barco3;
    ImageView barco21;
    ImageView barco22;
    ImageView barco11;
    ImageView barco12;
    ImageView barco13;
    ImageView vistaActuando;
    TextView labelTablero;
    HashMap posiciones = new HashMap();
    HashMap posicionesContrario = new HashMap();
    ArrayList <ImageView> arregloTotalImagenes= new ArrayList<ImageView>();
    ImageView imageView1;
    RelativeLayout metal;
    TableLayout board;
    HashMap <String,String> celdasAtacadas =  new HashMap<String,String>();
    HashMap <String,String> celdasAtacadasAMi =  new HashMap<String,String>();
    HashMap barcosVida = new HashMap();
    HashMap barcosVidaEnemigos = new HashMap();
    TextView scoreLabelHeader;
    TextView scoreLabel;
    boolean comenzoJuego = false;
    boolean esMiTurno=true;
    int anchoCuadro;
    int largoCuadro;
    boolean isHorizontal=true;
    Stack<ImageView> piezas=new Stack<ImageView>();
    boolean yaInicialice=false;
    ActionBar ab ;
    Toast dtaptoast;
    Toast dragToast;

    int numeroDeTiros=0;
    int barcosCaidosTuyos=0;
    int barcosCaidosSuyos=0;
    boolean muestraBotonRotar=true;

    //
    private RelativeLayout.LayoutParams layoutParams;
    String msg;
    private static final String IMAGEVIEW_TAG = "Android Logo";
    GestureDetector gestureDetector;
    boolean tapped=false;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //ab=getSupportActionBar();
        ab=((MyActivity) getActivity()).getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        View iv=inflater.inflate(R.layout.activity_game, container, false);
        setHasOptionsMenu(true);
        return iv;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onPause() {


        super.onPause();
        //  dragToast.cancel();
        //  dtaptoast.cancel();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(!yaInicialice){

            inicializaVariables();


        }

    }


    /*@Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //super.onDraw(canvas);
        canvas.drawBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco),largoCuadro*2,anchoCuadro*4,false),0,0,null) ;

    }*/


    public void llenaPosicionesContrarioIA(){


        Random randomPosicionDedo = new Random();

        Random randomOrientacionPieza = new Random();



        int auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        int auxIntOrientacionPieza=randomPosicionDedo.nextInt(2);


        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }
        //TODO se debe validar qeu no se salga de los limites

        //para la pieza de 4
        if(auxIntOrientacionPieza==0){ //supones que la pieza se pondra horizontal



            if(auxIntPosicionDedo%10<4){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+4;
            }
            else if(auxIntPosicionDedo%10>8){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+8;

            }



            posicionesContrario.put(("celda"+ auxIntPosicionDedo),new Integer(1));
            posicionesContrario.put("celda"+ (auxIntPosicionDedo-1),new Integer(1));
            posicionesContrario.put("celda"+ (auxIntPosicionDedo-2),new Integer(1));
            posicionesContrario.put("celda"+ (auxIntPosicionDedo+1),new Integer(1));
            Log.d("se coloco horizontal",sumarACadena("celda", auxIntPosicionDedo));


            ArrayList auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-1));
            auxiliar.add("celda"+ (auxIntPosicionDedo-2));
            auxiliar.add("celda"+ (auxIntPosicionDedo+1));

            barcosVidaEnemigos.put(new Integer(1), auxiliar);


        }
        else
        {



            if(auxIntPosicionDedo<20){

                auxIntPosicionDedo=+10;
            }
            else if(auxIntPosicionDedo>80){
                int unidades = auxIntPosicionDedo%10;
                auxIntPosicionDedo=unidades+70;

            }


            posicionesContrario.put(("celda"+ (auxIntPosicionDedo)),new Integer(1));
            posicionesContrario.put(("celda"+ (auxIntPosicionDedo-10)),new Integer(1));
            posicionesContrario.put(("celda"+ (auxIntPosicionDedo+10)),new Integer(1));
            posicionesContrario.put(("celda"+ (auxIntPosicionDedo+20)),new Integer(1));
            Log.d("se coloco vertical",new Integer(auxIntPosicionDedo).toString());



            ArrayList auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-10));
            auxiliar.add("celda"+ (auxIntPosicionDedo+10));
            auxiliar.add("celda"+ (auxIntPosicionDedo+20));

            barcosVidaEnemigos.put(new Integer(1), auxiliar);


        }



        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }

        //pieza de 3
        if(randomOrientacionPieza.nextInt(2)==0){ //supones que la pieza se pondra horizontal



            if(auxIntPosicionDedo%10<3){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+3;
            }
            else if(auxIntPosicionDedo%10>8){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+8;
            }

            posicionesContrario.put("celda"+auxIntPosicionDedo,new Integer(2));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-1),new Integer(2));
            posicionesContrario.put("celda"+(auxIntPosicionDedo+1),new Integer(2));
            Log.d("posicionCOntrario",new Integer(auxIntPosicionDedo).toString());



            ArrayList auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-1));
            auxiliar.add("celda"+ (auxIntPosicionDedo+1));


            barcosVidaEnemigos.put(new Integer(2), auxiliar);


        }
        else
        {

            if(auxIntPosicionDedo<20){

                auxIntPosicionDedo=+10;
            }
            else if(auxIntPosicionDedo>80){
                int unidades = auxIntPosicionDedo%10;
                auxIntPosicionDedo=unidades+70;

            }
            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(2));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-10),new Integer(2));
            posicionesContrario.put("celda"+(auxIntPosicionDedo+10),new Integer(2));
            Log.d("se coloco vertical",new Integer(auxIntPosicionDedo).toString());


            ArrayList auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-10));
            auxiliar.add("celda"+ (auxIntPosicionDedo+10));


            barcosVidaEnemigos.put(new Integer(2), auxiliar);


        }

        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }
        posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(3));

        ArrayList auxiliar = new ArrayList();

        auxiliar.add(("celda"+ auxIntPosicionDedo));



        barcosVidaEnemigos.put(new Integer(3), auxiliar);




        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }


        //pieza de 2
        if(randomOrientacionPieza.nextInt(2)==0){ //supones que la pieza se pondra horizontal


            if(auxIntPosicionDedo%10<3){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+3;
            }
            else if(auxIntPosicionDedo%10>10){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+10;
            }

            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(4));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-1),new Integer(4));


            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-1));



            barcosVidaEnemigos.put(new Integer(4), auxiliar);




        }
        else
        {


            if(auxIntPosicionDedo<20){

                auxIntPosicionDedo=+10;
            }
            else if(auxIntPosicionDedo>80){
                int unidades = auxIntPosicionDedo%10;
                auxIntPosicionDedo=80+unidades;
            }
            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(4));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-10),new Integer(4));
            Log.d("se coloco vertical",new Integer(auxIntPosicionDedo).toString());


            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-10));



            barcosVidaEnemigos.put(new Integer(4), auxiliar);



        }


        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }
        //pieza de 3
        if(randomOrientacionPieza.nextInt(2)==0){ //supones que la pieza se pondra horizontal

            if(auxIntPosicionDedo%10<3){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+3;
            }
            else if(auxIntPosicionDedo%10>8){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+8;
            }

            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(5));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-1),new Integer(5));
            posicionesContrario.put("celda"+(auxIntPosicionDedo+1),new Integer(5));

            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-1));
            auxiliar.add("celda"+ (auxIntPosicionDedo+1));



            barcosVidaEnemigos.put(new Integer(5), auxiliar);



        }
        else
        {


            if(auxIntPosicionDedo<20){

                auxIntPosicionDedo=+10;
            }
            else if(auxIntPosicionDedo>80){
                int unidades = auxIntPosicionDedo%10;
                auxIntPosicionDedo=unidades+70;

            }

            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(5));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-10),new Integer(5));
            posicionesContrario.put("celda"+(auxIntPosicionDedo+10),new Integer(5));

            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-10));
            auxiliar.add("celda"+ (auxIntPosicionDedo+10));



            barcosVidaEnemigos.put(new Integer(5), auxiliar);

            Log.d("se coloco vertical",new Integer(auxIntPosicionDedo).toString());


        }


        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }
//pieza de 2
        if(randomOrientacionPieza.nextInt(2)==0){ //supones que la pieza se pondra horizontal


            if(auxIntPosicionDedo%10<3){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+3;
            }
            else if(auxIntPosicionDedo%10>10){
                int decimos = auxIntPosicionDedo/10;
                auxIntPosicionDedo=decimos*10+10;
            }

            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(6));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-1),new Integer(6));


            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-1));




            barcosVidaEnemigos.put(new Integer(6), auxiliar);



        }
        else
        {


            if(auxIntPosicionDedo<20){

                auxIntPosicionDedo=+10;
            }
            else if(auxIntPosicionDedo>80){
                int unidades = auxIntPosicionDedo%10;
                auxIntPosicionDedo=80+unidades;
            }
            posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(6));
            posicionesContrario.put("celda"+(auxIntPosicionDedo-10),new Integer(6));
            Log.d("se coloco vertical",new Integer(auxIntPosicionDedo).toString());


            auxiliar = new ArrayList();

            auxiliar.add(("celda"+ auxIntPosicionDedo));
            auxiliar.add("celda"+ (auxIntPosicionDedo-10));




            barcosVidaEnemigos.put(new Integer(6), auxiliar);


        }

        auxIntPosicionDedo=randomPosicionDedo.nextInt(100);
        auxIntPosicionDedo+=1;
        while(auxIntPosicionDedo<=10 || (auxIntPosicionDedo%10==1) || (auxIntPosicionDedo%10==0) || (auxIntPosicionDedo>90)){
            auxIntPosicionDedo = randomPosicionDedo.nextInt(100);
            auxIntPosicionDedo+=1;
        }
        posicionesContrario.put("celda"+(auxIntPosicionDedo),new Integer(7));


        auxiliar = new ArrayList();

        auxiliar.add(("celda"+ auxIntPosicionDedo));

        barcosVidaEnemigos.put(new Integer(7), auxiliar);

    }

    public void inicializaVariables(){
        ivAuxiliar = (ImageView)getView().findViewById(R.id.celda1);
        barco4 = (ImageView) getView().findViewById(R.id.barco4);
        barco3 = (ImageView) getView().findViewById(R.id.barco3);
        barco21 = (ImageView) getView().findViewById(R.id.barco21);
        barco22 = (ImageView) getView().findViewById(R.id.barco22);
        barco11 = (ImageView) getView().findViewById(R.id.barco11);
        barco12 = (ImageView) getView().findViewById(R.id.barco12);
        barco13 = (ImageView) getView().findViewById(R.id.barco13);
        imageView1 = (ImageView) getView().findViewById(R.id.celda1);
        board = (TableLayout) getView().findViewById(R.id.boardTable);
        metal = (RelativeLayout) getView().findViewById(R.id.metal);
        labelTablero = (TextView) getView().findViewById(R.id.boardLabel);
        scoreLabel = (TextView) getView().findViewById(R.id.scoreLabel);
        scoreLabelHeader = (TextView) getView().findViewById(R.id.scoreHeaderLabel);


        scoreLabelHeader.setVisibility(View.INVISIBLE);
        scoreLabel.setVisibility(View.INVISIBLE);



        getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                if(!yaInicialice){
                    anchoCuadro=ivAuxiliar.getWidth();
                    largoCuadro=ivAuxiliar.getHeight();
                    asignaListenerVistaTablero();
                    llenaPosicionesContrarioIA();

                    insertaPiezasTablero();

                    colocaPiezaEnTablero();
                    yaInicialice=true;


                }


            }
        });




    }


    public String sumarACadena(String cadena,int numero){



        int auxInt = 0;
        try {
            auxInt = Integer.parseInt(cadena.replaceAll("[\\D]", ""));

            auxInt+=numero;

            return cadena.replaceAll("[^A-Za-z]","")+new Integer(auxInt).toString();


        } catch (Exception e) {
            Log.d("sumar a cadena: ", "Exception");
            return "";
        }


    }


    public void asignaListenerVistaTablero(){

        for(int i=1;i<101;i++){


            int idDynamic = getResources().getIdentifier("celda"+i, "id",
                    getActivity().getPackageName());



            if(i<=10 || (i%10==1) || (i%10==0) || (i>90))
                ((ImageView) getView().findViewById(idDynamic)).setOnDragListener(new MyDropListenerMetal());
            else{
                ((ImageView) getView().findViewById(idDynamic)).setOnDragListener(new MyDropListener());
                Log.d("listener: ",new Integer(i).toString());
                ((ImageView) getView().findViewById(idDynamic)).setOnTouchListener(new View.OnTouchListener() {
                    int random;
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {

                        Log.d("Se hizo touch en la vista", getResources().getResourceEntryName(view.getId()));

                        if (comenzoJuego && esMiTurno) {





                            board.setBackgroundResource(R.drawable.tablerocontrincante);

                            //labelTablero.setText("Base enemiga");
                            ab.setTitle("Base enemiga");
                            celdasAtacadas.put(getResources().getResourceEntryName(view.getId()), getResources().getResourceEntryName(view.getId()));

                            if (posicionesContrario.get(getResources().getResourceEntryName(view.getId())) != null)
                                view.setBackgroundColor(Color.RED);

                            else
                                view.setBackgroundColor(Color.BLUE);


                            Integer intAux=(Integer)posicionesContrario.get(getResources().getResourceEntryName(view.getId()));
                            ArrayList auxiliar=(ArrayList)barcosVidaEnemigos.get(intAux);
                            if(auxiliar!=null) {


                                Log.d("Ouch:", "ledio");
                                auxiliar.remove(getResources().getResourceEntryName(view.getId()));
                                barcosVidaEnemigos.remove(intAux);
                                barcosVidaEnemigos.put(intAux, auxiliar);
                                if (auxiliar.isEmpty()) {
                                    Toast.makeText(getActivity(), "Tiraste un barco!", Toast.LENGTH_LONG).show();
                                    scoreLabel.setText(new Integer(new Integer(scoreLabel.getText().toString()) + numeroDeTiros * 200).toString());
                                    barcosCaidosSuyos++;

                                } else {

                                    Log.d("listaBarcos:",auxiliar.toString());
                                    Toast.makeText(getActivity(), "Le diste a un barco", Toast.LENGTH_LONG).show();
                                    scoreLabel.setText(new Integer(new Integer(scoreLabel.getText().toString()) + numeroDeTiros * 50).toString());
                                }

                                if (barcosCaidosSuyos == 7)
                                    Toast.makeText(getActivity(), "Ganaste", Toast.LENGTH_LONG).show();


                            }



                            numeroDeTiros++;
                            esMiTurno = false;

                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    //esMiTurno=false;
                                    board.setBackgroundResource(R.drawable.battleshipboard);
                                    //labelTablero.setText("Mi base");
                                    ab.setTitle("Mi base");

                                    for (ImageView v : arregloTotalImagenes) {
                                        v.setVisibility(View.VISIBLE);

                                    }

                                    for (String v : celdasAtacadas.keySet()) {
                                        ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.TRANSPARENT);

                                        // ((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setVisibility(View.INVISIBLE);
                                    }
                                    for (String v : celdasAtacadasAMi.keySet()) {
                                        if (posiciones.get(getResources().getResourceEntryName(((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).getId())) != null)
                                            ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.RED);

                                        else
                                            ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.BLUE);
                                        // ((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setVisibility(View.VISIBLE);

                                    }

                                    Random randomAtaque = new Random();
                                    random = randomAtaque.nextInt(100);

                                    while (random <= 10 || (random % 10 == 1) || (random % 10 == 0) || (random > 90) || (celdasAtacadasAMi.get("celda"+ new Integer(random)) != null)) {
                                        random = randomAtaque.nextInt(100);
                                        random += 1;
                                    }

                                    Barco barcoAux=(Barco)posiciones.get("celda" + new Integer(random));

                                    ArrayList auxiliar=(ArrayList)barcosVida.get(barcoAux);
                                    if(auxiliar!=null){



                                        Log.d("Ouch:","ledio");
                                        auxiliar.remove("celda" + new Integer(random));
                                        barcosVida.remove(barcoAux);
                                        barcosVida.put(barcoAux,auxiliar);
                                        if(auxiliar.isEmpty()){
                                            Toast.makeText(getActivity(),"Barco caido",Toast.LENGTH_LONG).show();
                                            //scoreLabel.setText(new Integer(new Integer(scoreLabel.getText().toString())+numeroDeTiros*200).toString());
                                            barcosCaidosTuyos++;

                                        }

                                        else{

                                            Toast.makeText(getActivity(),"Le dio a uno",Toast.LENGTH_LONG).show();
                                            //scoreLabel.setText(new Integer(new Integer(scoreLabel.getText().toString())+numeroDeTiros*50).toString());
                                        }

                                        if(barcosCaidosTuyos==7)
                                            Toast.makeText(getActivity(),"Perdiste",Toast.LENGTH_LONG).show();






                                    }





                                    Log.d("Identificador", new Integer(random).toString());
                                    if (posiciones.get("celda" + new Integer(random)) != null)
                                        ((ImageView) getView().findViewById(getResources().getIdentifier("celda" + new Integer(random), "id", getActivity().getPackageName()))).setBackgroundColor(Color.RED);

                                    else
                                        ((ImageView) getView().findViewById(getResources().getIdentifier("celda" + new Integer(random), "id", getActivity().getPackageName()))).setBackgroundColor(Color.BLUE);

                                    celdasAtacadasAMi.put("celda" + new Integer(random), "celda" + new Integer(random));


                                    Runnable runn = new Runnable() {
                                        @Override
                                        public void run() {

                                            for (String v : celdasAtacadasAMi.keySet()) {

                                                ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.TRANSPARENT);
                                                //   ((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setVisibility(View.INVISIBLE);
                                            }
                                            for (String v : celdasAtacadas.keySet()) {
                                                if (posicionesContrario.get(getResources().getResourceEntryName(((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).getId())) != null)
                                                    ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.RED);

                                                else
                                                    ((View) getView().findViewById(getResources().getIdentifier(v, "id", getActivity().getPackageName()))).setBackgroundColor(Color.BLUE);
                                                // ((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setVisibility(View.VISIBLE);

                                            }


                                            for (ImageView v : arregloTotalImagenes) {
                                                v.setVisibility(View.INVISIBLE);

                                            }

                                            board.setBackgroundResource(R.drawable.tablerocontrincante);
                                            //labelTablero.setText("Base enemiga");
                                            ab.setTitle("Base enemiga");
                                            esMiTurno = true;
                                        }
                                    };

                                    Handler h = new Handler();
                                    h.postDelayed(runn, 3000); //

                                }
                            };

                            Handler h = new Handler();
                            h.postDelayed(r, 3000);


                        }

                        ImageView imagenRedundante=((ImageView) getView().findViewById(getResources().getIdentifier("celda" + random, "id",
                                getActivity().getPackageName())));
                        if(imagenRedundante!=null)
                            imagenRedundante.setOnTouchListener(this);
                        /*for (String v : celdasAtacadasAMi) {

                           // ((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setVisibility(View.VISIBLE);
                            //((View) findViewById(getResources().getIdentifier(v, "id", getPackageName()))).setBackgroundColor(Color.TRANSPARENT);

                        }*/

                        return true;
                    }
                });

            }




        }

        metal.setOnDragListener(new MyDropListenerMetal());

    }


    public void insertaPiezasTablero(){


        piezas.push(barco11);
        piezas.push(barco12);
        piezas.push(barco13);
        piezas.push(barco22);
        piezas.push(barco21);
        piezas.push(barco3);
        piezas.push(barco4);

        arregloTotalImagenes.add(barco11);
        arregloTotalImagenes.add(barco12);
        arregloTotalImagenes.add(barco13);
        arregloTotalImagenes.add(barco22);
        arregloTotalImagenes.add(barco21);
        arregloTotalImagenes.add(barco3);
        arregloTotalImagenes.add(barco4);
        // dtaptoast=Toast.makeText(getActivity(),"Manten el dedo sobre el barco para comenzar a arrastrarlo",Toast.LENGTH_LONG);
        // dtaptoast.show();
        // dragToast=Toast.makeText(getActivity(),"Toca dos veces el barco para cambiar su orientación.",Toast.LENGTH_LONG);
        //  dragToast.show();



    }


    public int getConstantePorPieza(){

        String aux = getResources().getResourceEntryName(vistaActuando.getId());
        Log.d("convertir a numero: ", aux);
        int auxSize = 1;
        try {
            auxSize = Integer.parseInt(aux.replaceAll("[\\D]", ""));
            if (auxSize > 10)
                auxSize %= 10;

        } catch (Exception e) {
            Log.d("convertir a numero: ", "Exception");
        }

        return auxSize;
    }


    public void colocaPiezaEnTablero(){


        if(!piezas.empty()) {
            vistaActuando = piezas.pop();

            isHorizontal=true;

            int auxSize= getConstantePorPieza();

            vistaActuando.setImageBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco), largoCuadro * 2, anchoCuadro * auxSize, false));


            vistaActuando.setTag(IMAGEVIEW_TAG);


            vistaActuando.requestLayout();


            Log.d("convertir a numero: ", new Integer(auxSize).toString());

            vistaActuando.getLayoutParams().width = anchoCuadro * auxSize;
            vistaActuando.getLayoutParams().height = largoCuadro * 2;

            //vistaActuando.setX(metal.getWidth()/2-vistaActuando.getWidth());
            //vistaActuando.setY(metal.getHeight()/2-vistaActuando.getWidth());


            gestureDetector = new GestureDetector(getActivity().getApplicationContext(), new GestureListener());


            vistaActuando.setOnDragListener(new GestureListener());

            vistaActuando.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    return gestureDetector.onTouchEvent(event);
                }


            });

        }
        else{

            scoreLabel.setVisibility(View.VISIBLE);
            scoreLabelHeader.setVisibility(View.VISIBLE);

            Runnable espera = new Runnable() {
                @Override
                public void run() {

                    muestraBotonRotar=false;
                    getActivity().invalidateOptionsMenu();

                    Toast.makeText(getActivity(),"Toca una casilla para atacar, posteriormente te atacarán.",Toast.LENGTH_SHORT).show();
                    comenzoJuego=true;
                    board.setBackgroundResource(R.drawable.tablerocontrincante);
                    //labelTablero.setText("Base enemiga");
                    for(ImageView v:arregloTotalImagenes){
                        v.setVisibility(View.INVISIBLE);

                    }

                }
            };

            Handler h = new Handler();
            h.postDelayed(espera, 1500); //




        }

    }
/*
    @Override
    public void onWindowFocusChanged(boolean hasFocus){

if(!yaInicialice){

    inicializaVariables();
    asignaListenerVistaTablero();
    llenaPosicionesContrarioIA();

    insertaPiezasTablero();

    colocaPiezaEnTablero();
    yaInicialice=true;

}

    }

*/










    public class GestureListener  implements
            GestureDetector.OnGestureListener,GestureDetector.OnDoubleTapListener,View.OnDragListener {





        @Override
        public boolean onDrag(View v,  DragEvent event){


            Log.d("Result:",new Boolean(event.getResult()).toString());
            if(!event.getResult())
                v.setVisibility(View.VISIBLE);



            switch(event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("Result:",new Boolean(event.getResult()).toString());

                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED :



                    break;
                case DragEvent.ACTION_DRAG_LOCATION  :

                    break;
                case DragEvent.ACTION_DRAG_ENDED   :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                    if(!event.getResult()){
                        vistaActuando.setVisibility(View.VISIBLE);
                        return false;
                    }
                    break;
                case DragEvent.ACTION_DROP:
                    //  Log.d(msg, "ACTION_DROP eventazo"+((View)event.getLocalState()).getId());










                    break;
                default: break;
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            ClipData.Item item = new ClipData.Item((CharSequence) vistaActuando.getTag());

            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData dragData = new ClipData(vistaActuando.getTag().toString(),
                    mimeTypes, item);

            // Instantiates the drag shadow builder.
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(vistaActuando);

            // Starts the drag
            vistaActuando.startDrag(dragData,  // the data to be dragged
                    myShadow,  // the drag shadow builder
                    null,      // no need to use local data
                    0          // flags (not currently used, set to 0)
            );

            vistaActuando.setVisibility(View.INVISIBLE);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
            return false;
        }



        @Override
        public void onLongPress(MotionEvent e) {



        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {
/*
            int medidaAux=getConstantePorPieza();

            vistaActuando.requestLayout();


            Log.d("double Tap","doble tap");

            if(isHorizontal){
                // vistaActuando.getLayoutParams().width = anchoCuadro*medidaAux;
                // vistaActuando.getLayoutParams().height = largoCuadro*2;

                vistaActuando.setImageBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco),  largoCuadro * 2,anchoCuadro * medidaAux, true));
                vistaActuando.getLayoutParams().width =  largoCuadro*2;
                vistaActuando.getLayoutParams().height =anchoCuadro*medidaAux;

            }
            else{
                // vistaActuando.getLayoutParams().width = largoCuadro*2;
                // vistaActuando.getLayoutParams().height = anchoCuadro*medidaAux;
                vistaActuando.setImageBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco),  largoCuadro * 2,anchoCuadro * medidaAux, false));
                vistaActuando.getLayoutParams().width = anchoCuadro*medidaAux;
                vistaActuando.getLayoutParams().height =largoCuadro*2;

            }



            isHorizontal=!isHorizontal;
*/
            return true;



        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {



            return false;
        }
    }

    class MyDropListener implements View.OnDragListener{
        @Override
        public boolean onDrag(View v,  DragEvent event){


            switch(event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("Result:",new Boolean(event.getResult()).toString());
                    // Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED :

                    break;
                case DragEvent.ACTION_DRAG_LOCATION  :

                    break;
                case DragEvent.ACTION_DRAG_ENDED   :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                    // Do nothing
                    break;
                case DragEvent.ACTION_DROP:

                    Log.d(msg, "ACTION_DROP event"+getResources().getResourceEntryName(v.getId()));
                    Log.d(msg, "ACTION_DROP event"+v.getX());
                    Log.d(msg, "ACTION_DROP event"+v.getY());


                    int medidaAux=getConstantePorPieza();
                    //esto lo posiciona si esta horizontal

                    int[] viewLocation = new int[2];

                    v.getLocationOnScreen(viewLocation);

                    switch (medidaAux){
                        case 4:
                            if(isHorizontal){
                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux%10<4){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+4).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+4).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux%10>8){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+8).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+8).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }

                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +1));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -2));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()), +1),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-1),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()), -2),barcoActual);

                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +1));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -2));

                                barcosVida.put(barcoActual, auxiliar);

                                vistaActuando.setX(viewLocation[0] - anchoCuadro * 2);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight() + largoCuadro- largoCuadro/9 );

                                Log.d("Sumando",sumarACadena(getResources().getResourceEntryName(v.getId()),-2));

                            }
                            else{
                                //esto lo posiciona si esta vertical el barco

                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux<20){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux+10).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(posicionAux+10).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux>80){
                                    int unidades = posicionAux%10;
                                    Log.d("identificador armado","celda"+new Integer(70+unidades).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(70+unidades).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                    v.getLocationOnScreen(viewLocation);


                                    Log.d("etiqueta",getResources().getResourceEntryName(v.getId()));


                                }


                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +10));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()),-10));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +20));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-10),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),+10),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),+20),barcoActual);



                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +10));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -10));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +20));

                                barcosVida.put(barcoActual, auxiliar);




                                vistaActuando.setX(viewLocation[0] - anchoCuadro + largoCuadro / 5);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight() + largoCuadro/3);


                            }
                            break;

                        case 2:
                            if(isHorizontal){



                                //TODO validar todas las vistas ... y la parte de bluetott, hacer los web services de los highscores, y valuar la parte de los games de android.
                                // ver la parte de cuando se entrega y eso...

                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux%10<3){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+1).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+3).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux%10>10){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+8).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+10).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }

                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);
                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-1),barcoActual);


                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));


                                barcosVida.put(barcoActual, auxiliar);



                                vistaActuando.setX(viewLocation[0] - anchoCuadro);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight() + largoCuadro- largoCuadro/9 );



                            }
                            else{

                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux<20){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux+10).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(posicionAux+10).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux>80){
                                    int unidades = posicionAux%10;
                                    Log.d("identificador armado","celda"+new Integer(70+unidades).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(80+unidades).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                    v.getLocationOnScreen(viewLocation);


                                    Log.d("etiqueta",getResources().getResourceEntryName(v.getId()));


                                }



                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -10));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                                //esto lo posiciona si esta vertical el barco
                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-10),barcoActual);


                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -10));


                                barcosVida.put(barcoActual, auxiliar);



                                vistaActuando.setX(viewLocation[0] - anchoCuadro + largoCuadro / 5);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight()+largoCuadro/3);


                            }
                            break;

                        case 3:
                            if(isHorizontal){



                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux%10<3){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+1).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+3).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux%10>8){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux*10+7).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(decimos*10+8).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }



                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +1));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-1),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),+1),barcoActual);


                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +1));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -1));


                                barcosVida.put(barcoActual, auxiliar);


                                vistaActuando.setX(viewLocation[0]-anchoCuadro);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight() + largoCuadro- largoCuadro/9 );


                            }
                            else{



                                int posicionAux=Integer.parseInt(getResources().getResourceEntryName(v.getId()).replaceAll("[\\D]", ""));

                                if(posicionAux<20){
                                    int decimos = posicionAux/10;
                                    Log.d("identificador armado","celda"+new Integer(posicionAux+10).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(posicionAux+10).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                }
                                else if(posicionAux>80){
                                    int unidades = posicionAux%10;
                                    Log.d("identificador armado","celda"+new Integer(70+unidades).toString());
                                    v= (ImageView) getView().findViewById(getResources().getIdentifier("celda"+new Integer(70+unidades).toString(), "id", getActivity().getPackageName()));
                                    v.getLocationOnScreen(viewLocation);
                                    v.getLocationOnScreen(viewLocation);


                                    Log.d("etiqueta",getResources().getResourceEntryName(v.getId()));


                                }

                                ArrayList<String> posicionesBarco = new ArrayList<String>();
                                posicionesBarco.add(getResources().getResourceEntryName(v.getId()));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -10));
                                posicionesBarco.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +10));


                                Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                                //esto lo posiciona si esta vertical el barco
                                posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),-10),barcoActual);
                                posiciones.put(sumarACadena(getResources().getResourceEntryName(v.getId()),+10),barcoActual);

                                ArrayList auxiliar = new ArrayList();

                                auxiliar.add(getResources().getResourceEntryName(v.getId()));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), +10));
                                auxiliar.add(sumarACadena(getResources().getResourceEntryName(v.getId()), -10));


                                barcosVida.put(barcoActual, auxiliar);



                                vistaActuando.setX(viewLocation[0] - anchoCuadro + largoCuadro / 5);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight()+largoCuadro/3);



                            }

                            break;

                        case 1:





                            ArrayList<String> posicionesBarco = new ArrayList<String>();
                            posicionesBarco.add(getResources().getResourceEntryName(v.getId()));


                            Barco barcoActual = new Barco(vistaActuando,posicionesBarco);

                            posiciones.put(getResources().getResourceEntryName(v.getId()),barcoActual);


                            ArrayList auxiliar = new ArrayList();

                            auxiliar.add(getResources().getResourceEntryName(v.getId()));


                            barcosVida.put(barcoActual, auxiliar);


                            if(isHorizontal){
                                vistaActuando.setX(viewLocation[0]);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight() + largoCuadro- largoCuadro/9 );

                            }
                            else{
                                //esto lo posiciona si esta vertical el barco
                                vistaActuando.setX(viewLocation[0] - anchoCuadro + largoCuadro / 5);
                                vistaActuando.setY(viewLocation[1] - metal.getHeight()+largoCuadro+largoCuadro/5);


                            }


                            break;


                    }


                    vistaActuando.setVisibility(View.VISIBLE);
                    vistaActuando.setOnTouchListener(null);



                    colocaPiezaEnTablero();
                    Log.d(msg, "Termine todo de drop");



                    break;


                default: break;

            }

            return true;
        }
    }


    class MyDropListenerMetal implements View.OnDragListener{
        @Override
        public boolean onDrag(View v,  DragEvent event){
            switch(event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                    // Do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:

                    break;
                case DragEvent.ACTION_DRAG_EXITED :

                    break;
                case DragEvent.ACTION_DRAG_LOCATION  :

                    break;
                case DragEvent.ACTION_DRAG_ENDED   :
                    Log.d(msg, "Action is DragEvent.ACTION_DRAG_ENDED");
                    // Do nothing
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d(msg, "ACTION_DROP event"+getResources().getResourceEntryName(v.getId()));
                    Log.d(msg, "ACTION_DROP event"+v.getX());
                    Log.d(msg, "ACTION_DROP event"+v.getY());


                    int[] viewLocation = new int[2];
                    v.getLocationOnScreen(viewLocation);
                    //v.setX(viewLocation[0]);
                    //v.setY(viewLocation[1]);


                    vistaActuando.setVisibility(View.VISIBLE);
                    //vistaActuando.setOnTouchListener(null);



                    //colocaPiezaEnTablero();



                    break;
                default: break;
            }
            return true;
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.rotarBarco) {

            int medidaAux=getConstantePorPieza();

            vistaActuando.requestLayout();


            Log.d("double Tap","doble tap");

            if(isHorizontal){
                // vistaActuando.getLayoutParams().width = anchoCuadro*medidaAux;
                // vistaActuando.getLayoutParams().height = largoCuadro*2;

                vistaActuando.setImageBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco),  largoCuadro * 2,anchoCuadro * medidaAux, true));
                vistaActuando.getLayoutParams().width =  largoCuadro*2;
                vistaActuando.getLayoutParams().height =anchoCuadro*medidaAux;

            }
            else{
                // vistaActuando.getLayoutParams().width = largoCuadro*2;
                // vistaActuando.getLayoutParams().height = anchoCuadro*medidaAux;
                vistaActuando.setImageBitmap(getResizedAndRotateBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barco),  largoCuadro * 2,anchoCuadro * medidaAux, false));
                vistaActuando.getLayoutParams().width = anchoCuadro*medidaAux;
                vistaActuando.getLayoutParams().height =largoCuadro*2;

            }



            isHorizontal=!isHorizontal;


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Bitmap rotateImage(Bitmap src, float degree) {
        // create new matrix object
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        // return new bitmap rotated using matrix
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP

        matrix.postScale(scaleWidth, scaleHeight);

// RECREATE THE NEW BITMAP

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }


    public Bitmap getResizedAndRotateBitmap(Bitmap bm, int newHeight, int newWidth,boolean rotate) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;
        Log.d("anchoBuscado",new Float(scaleHeight).toString());
        Log.d("anchoBuscado",new Float(scaleHeight).toString());

// CREATE A MATRIX FOR THE MANIPULATION

        Matrix matrix = new Matrix();

// RESIZE THE BIT MAP



        if(rotate){


            matrix.setScale(scaleHeight,scaleWidth);


            if(isHorizontal)
                matrix.preRotate(90);


            else
                matrix.preRotate(0);





        }
        else
            matrix.postScale(scaleWidth, scaleHeight);


        Log.d("error:",new Integer(width).toString());
        Log.d("error:",new Integer(height).toString());
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);


// RECREATE THE NEW BITMAP



    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.game,menu);
        if(!muestraBotonRotar)
            menu.findItem(R.id.rotarBarco).setVisible(false);



    }



}
