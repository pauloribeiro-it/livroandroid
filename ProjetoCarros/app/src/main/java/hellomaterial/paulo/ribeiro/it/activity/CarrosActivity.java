package hellomaterial.paulo.ribeiro.it.activity;

import android.os.Bundle;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.fragments.CarrosFragment;;

public class CarrosActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo",0)));
        if(savedInstanceState == null){
            CarrosFragment frag = new CarrosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container,frag).commit();
        }
    }

    @Override
    protected boolean chamaReplace() {
        return false;
    }
}
