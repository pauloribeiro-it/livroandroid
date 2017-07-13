package hellomaterial.paulo.ribeiro.it.activity;

import android.os.Bundle;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.fragments.CarrosFragment;

public class SiteLivroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_livro);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Título
        getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo", 0)));
        // Adiciona o fragment com o mesmo Bundle (args) da intent
        if (savedInstanceState == null) {
            CarrosFragment frag = new CarrosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
