package hellomaterial.paulo.ribeiro.it.activity;

import android.os.Bundle;

import org.parceler.Parcels;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.fragments.CarroFragment;

public class CarroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        setUpToolbar();
        Carro c = getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(c.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState == null){
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment,frag).commit();
        }
    }

    @Override
    protected boolean chamaReplace() {
        return false;
    }
}
