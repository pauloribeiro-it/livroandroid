package hellomaterial.paulo.ribeiro.it.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulo on 29/07/17.
 */

public class CarroDB extends SQLiteOpenHelper{
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "livro_android.sqlite";
    private static final int VERSAO_BANCO=1;
    private static final String dbCreate="create table if not exists carro(_id integer primary key autoincrement, \n" +
            "nome text, \n" +
            "desc text, \n" +
            "url_foto text, \n" +
            "url_info text, \n" +
            "url_video text,\n" +
            "latitude text,\n" +
            "longitude text,\n" +
            "tipo text)";

    public CarroDB(Context context){
        super(context,NOME_BANCO,null,VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"Criando a Tabela carro...");
        db.execSQL(dbCreate);
        Log.d(TAG,"Tabela carro criada com sucesso.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long save(Carro carro){
        long id = carro.id;
        SQLiteDatabase db = getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put("nome",carro.nome);
            values.put("desc",carro.desc);
            values.put("url_foto",carro.urlFoto);
            values.put("url_info",carro.urlInfo);
            values.put("latitude",carro.latitude);
            values.put("longitude",carro.longitude);
            values.put("tipo",carro.tipo);
            if(id!=0){
                String _id = String.valueOf(carro.id);
                String[] whereArgs = new String []{_id};
                int count = db.update("carro",values,"_id=?",whereArgs);
                return count;
            }else{
                id = db.insert("carro","",values);
                return id;
            }
        }finally{
            db.close();
        }
    }

    public int delete(Carro carro){
        SQLiteDatabase db = getWritableDatabase();
        try{
            int count = db.delete("carro","_id=?",new String[]{String.valueOf(carro.id)});
            Log.i(TAG,"Deletou ["+count+"] registro.");
            return count;
        }finally{
            db.close();
        }
    }

    public List<Carro> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("carro",null,null,null,null,null,null,null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    public List<Carro> findAllByTipo(String tipo){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("carro",null,"tipo = '"+tipo+"'",null,null,null,null);
            return toList(c);
        }finally {
            db.close();
        }
    }

    public Carro findByNome(String nome){
        SQLiteDatabase db = getWritableDatabase();
        try{
            Cursor c = db.query("carro",null,"nome =?",new String[]{nome},null,null,null,null);
            List<Carro> list = toList(c);
            return list.isEmpty()? null:list.get(0);
        }finally{
            db.close();
        }
    }

    private List<Carro> toList(Cursor c){
        List<Carro> carros = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                Carro carro = new Carro();
                carros.add(carro);
                carro.id = c.getLong(c.getColumnIndex("_id"));
                carro.nome = c.getString(c.getColumnIndex("nome"));
                carro.desc = c.getString(c.getColumnIndex("desc"));
                carro.urlInfo = c.getString(c.getColumnIndex("url_info"));
                carro.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                carro.urlVideo = c.getString(c.getColumnIndex("url_video"));
                carro.latitude = c.getString(c.getColumnIndex("latitude"));
                carro.longitude = c.getString(c.getColumnIndex("longitude"));
                carro.tipo = c.getString(c.getColumnIndex("tipo"));
            }while(c.moveToNext());
        }

        return carros;
    }

    public void execSQL(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
        }finally{
            db.close();
        }
    }

    public void execSQL(String sql, Object[] args){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql,args);
        }finally{
            db.close();
        }
    }

    public int deleteCarrosByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            int count = db.delete("carro", "tipo=?", new String[]{tipo});
            Log.i(TAG, "Deletou [" + count + "] registros");
            return count;
        } finally {
            db.close();
        }
    }
    public long insert(ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            long id = db.insert("carro", "", values);
            return id;
        } finally {
            db.close();
        }
    }

    public int update(ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // update carro set values = ... where _id=?
            int count = db.update("carro", values, where, whereArgs);
            return count;
        } finally {
            db.close();
        }
    }

    public int delete(String where, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            int count = db.delete("carro", where, whereArgs);
            return count;
        } finally {
            db.close();
        }
    }
}
