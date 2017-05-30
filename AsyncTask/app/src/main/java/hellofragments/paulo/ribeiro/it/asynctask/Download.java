package hellofragments.paulo.ribeiro.it.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by paulo on 29/05/17.
 */

public class Download {
    public static Bitmap downloadBitmap(String url) throws IOException {
        try {
            // Só para mostrar o ProgressBar girando
            // Mas não faça isso em produção.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e("download",e.getMessage());
        }

        // Faz o download da imagem
        Bitmap bitmap = null;
        InputStream in = new URL(url).openStream();
        // Converte a InputStream do Java para Bitmap
        bitmap = BitmapFactory.decodeStream(in);
        in.close();
        return bitmap;
    }
}
