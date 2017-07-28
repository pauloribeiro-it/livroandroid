package hellomaterial.paulo.ribeiro.it.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import hellomaterial.paulo.ribeiro.it.R;

public class ConfiguracoesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);
        setUpToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,new PrefsManager());
            ft.commit();
        }
    }

    public static class PrefsManager extends PreferenceFragmentCompat{
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
