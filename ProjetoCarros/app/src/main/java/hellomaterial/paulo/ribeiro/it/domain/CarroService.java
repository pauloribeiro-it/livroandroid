package hellomaterial.paulo.ribeiro.it.domain;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hellomaterial.paulo.ribeiro.it.R;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;
import livroandroid.lib.utils.XMLUtils;

/**
 * Created by paulo on 03/07/17.
 */

public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";

    public static List<Carro> getCarros(Context context, int tipo,boolean refresh) throws IOException{
        String tipoString = getTipo(tipo);
        String url = URL.replace("{tipo}",tipoString);
        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);
        List<Carro> carros = !refresh?getCarrosFromBanco(context,tipo):null;
        if(carros == null || carros.isEmpty()){
            carros = getCarrosFromWebService(context,tipo);
        }
       // salvaArquivoNaMemoriaInterna(context,url,json);
       // salvaArquivoNaMemoriaExterna(context,url,json);
        return carros;
    }

    private static String readFile(Context context, int tipo) throws IOException{
        if(tipo == R.string.classicos){
            return FileUtils.readRawFileString(context,R.raw.carros_classicos,"UTF-8");
        }else if(tipo == R.string.esportivos){
            return FileUtils.readRawFileString(context,R.raw.carros_esportivos,"UTF-8");
        }
        return FileUtils.readRawFileString(context,R.raw.carros_luxo,"UTF-8");
    }

    private static List<Carro> parserJSON(Context context, String json) throws IOException{
        List<Carro> carros = new ArrayList<>();

        try{
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");

            for(int i=0;i<jsonCarros.length();i++){
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");

                if(LOG_ON){
                    Log.d(TAG,"Carro "+c.nome+" > "+c.urlFoto);
                }
                carros.add(c);
            }
            if(LOG_ON){
                Log.d(TAG,carros.size()+" encontrados.");
            }
        }catch(JSONException e){
           throw new IOException(e.getMessage(),e);
        }
        return carros;
    }

    private static List<Carro> parserXML(Context context, String xml){
        List<Carro> carros = new ArrayList<>();
        Element root  = XMLUtils.getRoot(xml,"UTF-8");
        List<Node> nodeCarros = XMLUtils.getChildren(root,"carro");
        for(Node node:nodeCarros){
            Carro c = new Carro();
            c.nome = XMLUtils.getText(node,"nome");
            c.desc = XMLUtils.getText(node,"desc");
            c.urlFoto = XMLUtils.getText(node,"url_foto");
            c.urlInfo = XMLUtils.getText(node,"url_info");
            c.urlVideo = XMLUtils.getText(node,"url_video");
            c.latitude = XMLUtils.getText(node,"latitude");
            c.longitude = XMLUtils.getText(node,"longitude");

            if(LOG_ON){
                Log.d(TAG,"Carro "+ c.nome+" > "+c.urlFoto);
            }

            carros.add(c);
        }

        if(LOG_ON){
            Log.d(TAG,carros.size() +" encontrados.");
        }
        return carros;
    }

    private static String getTipo(int tipo){
        switch(tipo){
            case R.string.classicos:return "classicos";
            case R.string.esportivos: return "esportivos";
            default: return "luxo";
        }
    }

    private static void salvaArquivoNaMemoriaInterna(Context context, String url,String json){
        String fileName = url.substring(url.lastIndexOf("/")+1);
        File file = FileUtils.getFile(context,fileName);
        IOUtils.writeString(file,json);
        Log.d(TAG,"Arquivo salvo: "+file);
    }

    private static void salvaArquivoNaMemoriaExterna(Context context, String url,String json){
        String fileName = url.substring(url.lastIndexOf("/")+1);
        File f = SDCardUtils.getPrivateFile(context,fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(f,json);
        Log.d(TAG,"1) Arquivo privado salvo na pasta downloads: "+f);
        f = SDCardUtils.getPublicFile(fileName,Environment.DIRECTORY_DOWNLOADS);
        Log.d(TAG,"2) Arquivo público salvo na pasta downloads: "+f);
    }

    public static List<Carro> getCarrosFromArquivo(Context context,int tipo) throws IOException{
        String tipoString = getTipo(tipo);
        String fileName = String.format("carros_%s.json",tipoString);
        Log.d(TAG,"Abrindo arquivo: "+fileName);

        String json = FileUtils.readFile(context,fileName,"UTF-8");
        if(json == null){
            Log.d(TAG,"Arquivo "+fileName+" não encontrado");
            return null;
        }
        List<Carro> carros = parserJSON(context,json);
        Log.d(TAG,"Retornando carros do arquivo "+fileName+".");
        return carros;
    }

    public static List<Carro> getCarrosFromWebService(Context context,int tipo) throws IOException{
        String tipoString = getTipo(tipo);
        String url = URL.replace("{tipo}",tipoString);
        Log.d(TAG,"URL: "+url);
        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);
        List<Carro> carros = parserJSON(context,json);
        salvarCarros(context,tipo,carros);
        return carros;
    }

    public static List<Carro> getCarrosFromBanco(Context context,int tipo) throws IOException{
        CarroDB db = new CarroDB(context);
        try{
            String tipoString = getTipo(tipo);
            List<Carro> carros = db.findAllByTipo(tipoString);
            Log.d(TAG,"Retornando "+carros.size()+" carros["+tipoString+"] do banco");
            return carros;
        }finally{
            db.close();
        }
    }

    private static void salvarCarros(Context context,int tipo,List<Carro> carros){
        CarroDB db = new CarroDB(context);
        try{
            String tipoString = getTipo(tipo);
            db.deleteCarrosByTipo(tipoString);
            for(Carro c:carros){
                c.tipo = tipoString;
                Log.d(TAG,"Salvando o carro: "+c.nome);
                db.save(c);
            }
        }finally {
            db.close();
        }
    }
}
