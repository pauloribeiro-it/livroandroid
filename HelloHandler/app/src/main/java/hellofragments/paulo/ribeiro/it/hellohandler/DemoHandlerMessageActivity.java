package hellofragments.paulo.ribeiro.it.hellohandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DemoHandlerMessageActivity extends AppCompatActivity {

    protected static final int MENSAGEM_TESTE=1;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_handler_message);
        findViewById(R.id.btEnviar).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(),"A mensagem chegou!", Toast.LENGTH_SHORT).show();
                    }
                },3000);
            }
        });
    }
}

