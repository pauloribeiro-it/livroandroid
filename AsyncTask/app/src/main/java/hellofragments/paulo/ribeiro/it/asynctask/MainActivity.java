package hellofragments.paulo.ribeiro.it.asynctask;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://s3.amazonaws.com/static.novatec.com.br/capas-ampliadas/capa-ampliada-9788575224687.jpg";
    private ProgressBar progress;
    private ImageView imgView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.img);
        progress = (ProgressBar) findViewById(R.id.progress);
        downloadImagem();
    }

    private void downloadImagem(){
        DownloadTask task = new DownloadTask();
        task.execute();
    }

    public Context getContext(){
        return this;
    }
    private class DownloadTask extends AsyncTask<Void,Void,Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try{
                bitmap = Download.downloadBitmap(URL);
            }catch(Exception e){
                Log.e("livroandroid",e.getMessage(),e);
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap){
            if(bitmap != null){
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
                imgView.setImageBitmap(bitmap);
            }
        }
    }


}
