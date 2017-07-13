package hellomaterial.paulo.ribeiro.it.activity;

import android.os.Bundle;
import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.fragments.SiteLivroFragment;

public class SiteLivroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_livro);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Título
        getSupportActionBar().setTitle("WebView");
        // Adiciona o fragment com o mesmo Bundle (args) da intent
        if (savedInstanceState == null) {
            SiteLivroFragment frag = new SiteLivroFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
        }
    }
}
