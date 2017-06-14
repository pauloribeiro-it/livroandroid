package hellomaterial.paulo.ribeiro.it.activity;

import android.os.Bundle;

import hellomaterial.paulo.ribeiro.it.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpToolbar();
    }
}
