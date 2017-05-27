package hellofragments.paulo.ribeiro.it.hellohandler;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

public class DownloadImagemActivity extends AppCompatActivity {

    private static final String URL = "https://s3.amazonaws.com/static.novatec.com.br/capas-ampliadas/capa-ampliada-9788575224687.jpg";
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_imagem);
        progress = (ProgressBar) findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);
        downloadImagem(URL);
    }

    private void downloadImagem(final String urlImg){
        new Thread(){
            @Override
            public void run() {
                try{
                    final Bitmap imagem = Download.downloadBitmap(urlImg);
                    atualizarImagem(imagem);
                }catch(IOException e ){
                    Log.e("Erro no download: ",e.getMessage(),e);
                }
            }
        }.start();
    }

    private void atualizarImagem(final Bitmap imagem){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progress.setVisibility(View.INVISIBLE);
                ImageView imgView = (ImageView) findViewById(R.id.img);
                imgView.setImageBitmap(imagem);
            }
        });

    }
}
