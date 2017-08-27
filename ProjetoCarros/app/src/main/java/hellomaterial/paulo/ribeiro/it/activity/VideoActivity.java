package hellomaterial.paulo.ribeiro.it.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import org.parceler.Parcels;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.domain.Carro;
import hellomaterial.paulo.ribeiro.it.fragments.VideoFragment;

public class VideoActivity extends BaseActivity {
    private Carro carro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setUpToolbar();
        carro = getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(carro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout,videoFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(getActivity());
                intent.putExtra("carro",Parcels.wrap(carro));
                NavUtils.navigateUpTo(getActivity(),intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
