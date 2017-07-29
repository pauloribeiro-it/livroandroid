package hellowsdl.paulo.ribeiro.it.hellowsdl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.w3c.dom.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import livroandroid.lib.utils.HttpHelper;
import livroandroid.lib.utils.XMLUtils;

public class CelsiusToFahrenheitPostActivity extends AppCompatActivity {
    
    private String URL = "https://www.w3schools.com/xml/tempconvert.asmx/CelsiusToFahrenheit";
    private EditText tCelcius;
    private EditText tFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celsius_to_fahrenheit_post);
        tCelcius = (EditText) findViewById(R.id.tCelcius);
        tFahrenheit = (EditText) findViewById(R.id.tFahrenheit);
    }

    public void onClickConverter(View view){
        new Thread() {
            @Override
            public void run() {
                String celcius = tCelcius.getText().toString();
                Map<String, String> params = new HashMap<String, String>();
                params.put("Celsius",celcius);
                try{
                    String s  = HttpHelper.doPost(URL,params,"UTF-8");
                    Element root = XMLUtils.getRoot(s,"UTF-8");

                    final String fahrenheit = XMLUtils.getText(root);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            tFahrenheit.setText(fahrenheit);
                        }
                    });
                }catch(IOException e){
                    Log.e("livroandroid","Erro: "+e.getMessage(),e);
                }
            }
        }.start();
    }
}
