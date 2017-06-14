package hellomaterial.paulo.ribeiro.it.activity;

import android.support.v7.widget.Toolbar;

import hellomaterial.paulo.ribeiro.it.R;

/**
 * Created by paulo on 13/06/17.
 */

public class BaseActivity extends livroandroid.lib.activity.BaseActivity{
    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null)
            setSupportActionBar(toolbar);
    }
}
