package mx.unam.computoMovil.battleship;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonParser {

    private String url;


    public JsonParser(){}

    public JsonParser(String url){
        this.url = url;
    }


    private String getJSON() throws Exception{
        String resultado = null;

        try {
            HttpParams params = new BasicHttpParams();  //define parametros para el servidor y la respuesta
            HttpConnectionParams.setConnectionTimeout(params,3);
            HttpConnectionParams.setSoTimeout(params,3);

            HttpClient cliente = new DefaultHttpClient();
            HttpResponse respuesta = cliente.execute(new HttpGet(this.url));
            HttpEntity entidades = respuesta.getEntity();

            if(entidades!=null){
                resultado = EntityUtils.toString(entidades); //entity util es para manejar el http entity
            }

        }catch (Exception e ){
            throw  new Exception(e.getMessage());
        }
        return resultado;
    }


    public List<Score> obtenerScores() throws JSONException, Exception{
        List<Score> scores = new ArrayList<Score>();

        Log.d("JSON",getJSON());
        try {
            JSONArray scoresJSONArray = new JSONObject(getJSON()).getJSONArray("scores");
            Log.d("JSON",scoresJSONArray.toString());
            if(scoresJSONArray!=null){
                scores = new ArrayList<Score>();
                for(int indice=0; indice <scoresJSONArray.length(); indice++){
                    JSONObject contactoJsonObject = scoresJSONArray.getJSONObject(indice);
                    if(contactoJsonObject.length() != 0){
                        //JSONObject contactoJSON = contactoJsonObject.getJSONObject("contacto");
                        Score score = new Score();
                        score.setNombre(contactoJsonObject.getString("nombre"));
                        score.setScore(contactoJsonObject.getString("score"));

                        Log.d("JSON",contactoJsonObject.getString("score"));
                        scores.add(score);
                    }else{
                        throw new Exception("No se encontro el contacto");
                    }
                }
            }else{
                throw new Exception("No se encontro el contacto");
            }

        }catch (JSONException je){
                throw  new JSONException(je.getMessage());

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        return scores;
    }
}
