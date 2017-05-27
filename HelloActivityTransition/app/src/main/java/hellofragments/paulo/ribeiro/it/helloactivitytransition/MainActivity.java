package hellofragments.paulo.ribeiro.it.helloactivitytransition;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickPlaneta(View view){
        Intent intent = new Intent(getBaseContext(), PlanetaActivity.class);
        ActivityOptionsCompat opts = ActivityOptionsCompat.makeCustomAnimation(this,R.anim.slide_in_left,R.anim.slide_out_left);
        ActivityCompat.startActivity(this,intent,opts.toBundle());
    }
}
