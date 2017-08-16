package hellomaterial.paulo.ribeiro.it.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.parceler.Parcels;

import java.util.List;

import hellomaterial.paulo.ribeiro.it.adapter.CarroAdapter;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.CarroDB;

public class CarrosIntentActivity extends BaseActivity {

    private List<Carro> carros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros_intent);
        Intent intent = getIntent();
        Uri uri = intent.getData();
        Log.d("livroandroid","Action: "+intent.getAction());
        Log.d("livroandroid","Scheme: "+uri.getScheme());
        Log.d("livroandroid","Host: "+uri.getHost());
        Log.d("livroandroid","Path: "+uri.getPath());
        Log.d("livroandroid","PathSegments: " + uri.getPathSegments());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        setUpToolbar();
        CarroDB db = new CarroDB(this);
        try{
            if("/carros".equals(uri.getPath())){
                this.carros = db.findAll();
                recyclerView.setAdapter(new CarroAdapter(this,carros,onClickCarro()));
            }else{
                List<String> paths = uri.getPathSegments();
                String nome = paths.get(1);
                Carro carro = db.findByNome(nome);
                if(carro != null){
                    Intent carroIntent = new Intent(this,CarroActivity.class);
                    carroIntent.putExtra("carro", Parcels.wrap(carro));
                    startActivity(carroIntent);
                    finish();
                }
            }
        }finally{
            db.close();
        }
    }

    private CarroAdapter.CarrosOnClickListener onClickCarro(){
        return new CarroAdapter.CarrosOnClickListener(){
            @Override
            public void onClickCarro(View view, int idx) {
                Carro c = carros.get(idx);
                Intent intent = new Intent();
                intent.putExtra("nome",c.nome);
                intent.putExtra("url_foto",c.urlFoto);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }

            @Override
            public void onLongClickCarro(View view, int idx) {

            }
        };
    }
}
