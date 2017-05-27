package hellofragments.paulo.ribeiro.it.hellohandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * Created by paulo on 26/05/17.
 */

public class Download {
    public static Bitmap downloadBitmap(String url) throws IOException{
        Bitmap bitmap = null;
        try(InputStream in = new URL(url).openStream()){
            bitmap = BitmapFactory.decodeStream(in);
        }
        return bitmap;
    }
}
