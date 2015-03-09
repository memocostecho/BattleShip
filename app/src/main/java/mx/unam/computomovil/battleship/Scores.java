package mx.unam.computomovil.battleship;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutionException;



public class Scores extends android.support.v4.app.Fragment {


    private ArrayAdapter<String> scoresArrayAdapter;
    private ListView scoresListView;
    String url = "https://dgticbattle.appspot.com/_ah/api/scores/v1/listaScores";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View iv=inflater.inflate(R.layout.scoreslayout, container, false);
        scoresListView=(ListView)iv.findViewById(R.id.scoresListView);
        ConexionWs mContactosTask = null;


        mContactosTask = new ConexionWs(getActivity());
        try {
            mostrarScores((ArrayList)mContactosTask.execute(url).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return iv;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



    }




    public  void mostrarScores(ArrayList<Score> listaScores){
        ScoreCustomAdapter mArrayAdapter = new ScoreCustomAdapter(getActivity(),listaScores);

        scoresListView.setAdapter(mArrayAdapter);

        scoresListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(),"hola",Toast.LENGTH_LONG);

            }
        });



    }





}
