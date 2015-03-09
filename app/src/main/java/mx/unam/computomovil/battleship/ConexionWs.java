package mx.unam.computoMovil.battleship;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Created by yasminegutierrez on 11/29/14.
 */
 class ConexionWs extends AsyncTask<Object,Integer,List> {

    Context contextoApp;


    List<Score> scores;
    ProgressDialog mProgressDialog = null;


    ConexionWs(Context contextoApp) {

        this.contextoApp = contextoApp;
        mProgressDialog = new ProgressDialog(contextoApp);
        mProgressDialog.setMessage("Descargando contactos");


    }

    @Override
    protected List doInBackground(Object... params) {
        JsonParser parser = new JsonParser((String)params[0]);

        try {
            scores = parser.obtenerScores();

            Log.d("TAG", String.valueOf(scores.size()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }



    @Override
    protected void onPreExecute() {

        super.onPreExecute();


        mProgressDialog.show();


    }

    @Override
    protected void onPostExecute(List lista) {

        super.onPostExecute(lista);
        mProgressDialog.dismiss();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {


        super.onProgressUpdate(values);
      //  mProgressDialog.setProgress(values[0]);
    }


    @Override
    protected void onCancelled() {
        super.onCancelled();
        mProgressDialog.cancel();
    }
}