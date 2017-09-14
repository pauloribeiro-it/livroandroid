package hellomaterial.paulo.ribeiro.it.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.HashMap;

import hellomaterial.paulo.ribeiro.it.domain.CarroDB;

/**
 * Created by paulo on 13/09/17.
 */

public class CarroProvider extends ContentProvider{

    private CarroDB db;
    private static HashMap<String,String> colunas;
    private static final int CARROS = 1;
    private static final int CARROS_ID = 2;
    private UriMatcher uriCarro;

    private static String getAuthority(){
        return "hellomaterial.paulo.ribeiro.it";
    }

    public static final class Carros implements BaseColumns{
        private Carros(){

        }

        public static final Uri CONTENT_URI = Uri.parse("content://"+getAuthority()+"/carros");

        public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.google.carros";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.carros";

        public static final String DEFAULT_SORT_ORDER = "_id ASC";

        public static final String NOME = "nome";

        public static final String DESC = "desc";

        public static final String URL_INFO = "url_info";

        public static final String URL_FOTO = "url_foto";

        public static final String URL_VIDEO = "url_video";

        public static final String LATITUDE = "latitude";

        public static final String LONGITUDE = "longitude";

        public static final String TIPO = "tipo";

        public static Uri getUriId(long id){
            Uri uriCarro = ContentUris.withAppendedId(Carros.CONTENT_URI,id);
            return uriCarro;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(uriCarro.match(uri)){
            case CARROS: return Carros.CONTENT_TYPE;
            case CARROS_ID: return Carros.CONTENT_ITEM_TYPE;
            default: throw new IllegalArgumentException("Uri desconhecida: "+uri);
        }
    }

    @Override
    public boolean onCreate() {
        uriCarro = new UriMatcher(UriMatcher.NO_MATCH);
        uriCarro.addURI(getAuthority(),"carros",CARROS);
        uriCarro.addURI(getAuthority(),"carros/#",CARROS_ID);

        colunas = new HashMap<>();
        colunas.put(Carros._ID,Carros._ID);
        colunas.put(Carros.NOME,Carros.NOME);
        colunas.put(Carros.DESC,Carros.DESC);
        colunas.put(Carros.URL_INFO,Carros.URL_INFO);
        colunas.put(Carros.URL_FOTO,Carros.URL_FOTO);
        colunas.put(Carros.URL_VIDEO,Carros.URL_VIDEO);
        colunas.put(Carros.LATITUDE,Carros.LATITUDE);
        colunas.put(Carros.LONGITUDE,Carros.LONGITUDE);
        colunas.put(Carros.TIPO,Carros.TIPO);
        db = new CarroDB(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        if(projection == null){
            projection = new String[]{"_id",Carros.NOME,Carros.DESC,Carros.URL_INFO,
            Carros.URL_FOTO,Carros.URL_VIDEO,Carros.LATITUDE,Carros.LONGITUDE,Carros.TIPO};
        }
        switch(uriCarro.match(uri)){
            case CARROS:
                builder.setTables("carros");
                builder.setProjectionMap(colunas);
                break;
            case CARROS_ID:
                builder.setTables("carros");
                builder.setProjectionMap(colunas);
                builder.appendWhere(Carros._ID+" = "+uri.getPathSegments().get(1));
                break;
            default: throw new IllegalArgumentException("Uri desconhecida: "+uri);
        }
        String orderBy;
        if(TextUtils.isEmpty(sortOrder)){
            orderBy = Carros.DEFAULT_SORT_ORDER;
        }else{
            orderBy = sortOrder;
        }
        SQLiteDatabase db = this.db.getReadableDatabase();
        Cursor c = builder.query(db,projection,selection,selectionArgs,null,null,orderBy);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if(uriCarro.match(uri) != CARROS){
            throw new IllegalArgumentException("Uri desconhecida: "+uri);
        }
        ContentValues values;
        if(initialValues != null){
            values = new ContentValues(initialValues);
        }else{
            values = new ContentValues();
        }
        long id = db.insert(values);
        if(id > 0){
            Uri uriCarro = Carros.getUriId(id);
            getContext().getContentResolver().notifyChange(uriCarro,null);
            return uriCarro;
        }
        throw new SQLException("Falhou ao inserir"+uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        int count;
        switch(uriCarro.match(uri)){
            case CARROS:
                count = db.update(values,where,whereArgs);
                break;
            case CARROS_ID:
                String id = uri.getPathSegments().get(1);
                String whereFinal = Carros._ID + "="+id+(!TextUtils.isEmpty(where)? "AND ("+where+')':"");
                count = db.update(values,whereFinal,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public int delete(Uri uri, String where,String[] whereArgs) {
        int count;
        switch (uriCarro.match(uri)){
            case CARROS:
                count = db.delete(where,whereArgs);
                break;
            case CARROS_ID:
                String id = uri.getPathSegments().get(1);
                String whereFinal = Carros._ID + "=" + id+(!TextUtils.isEmpty(where) ? " AND (" + where +')' :"");
                count = db.delete(where,whereArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconhecida: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }
}
