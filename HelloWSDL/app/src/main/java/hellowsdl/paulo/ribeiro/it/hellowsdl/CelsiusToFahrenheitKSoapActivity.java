package hellowsdl.paulo.ribeiro.it.hellowsdl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CelsiusToFahrenheitKSoapActivity extends AppCompatActivity {
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
        try {
            String celsius = tCelcius.getText().toString();
            final String fahrenheit = CelsiusToFahrenheit(URL,celsius);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tFahrenheit.setText(fahrenheit);
                }
            });
        } catch (Exception e) {
            Log.e("livroandroid","Erro: "+e.getMessage(),e);
        }
    }

    public String CelsiusToFahrenheit(String url, String celsius) throws Exception{
        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.implicitTypes = true;
        soapEnvelope.dotNet = true;

        SoapObject soapReq = new SoapObject("http://www.w3schools.com/xml/","CelsiusToFahrenheit");
        soapReq.addProperty("Celsius",celsius);
        int timeOut = 60000;

        HttpTransportSE httpTransport = new HttpTransportSE(url,timeOut);
        try{
            httpTransport.call("https://www.w3schools.com/xml/CelsiusToFahrenheit",soapEnvelope);
            Object retObject = soapEnvelope.bodyIn;
            if(retObject instanceof SoapFault){
                SoapFault fault = (SoapFault) retObject;
                Exception ex = new Exception(fault.faultstring);
                throw ex;
            }else{
                SoapObject result = (SoapObject) retObject;
                if(result.getPropertyCount() > 0){
                    Object obj = result.getProperty(0);
                    if(obj != null && obj.getClass().equals(SoapPrimitive.class)){
                        SoapPrimitive j = (SoapPrimitive) obj;
                        String resultVariable = j.toString();
                        return resultVariable;
                    }else if(obj != null && obj instanceof String){
                        String resultVarible = (String) obj;
                        return resultVarible;
                    }
                }
            }
        }catch(Exception e){
            throw e;
        }

        return "";
    }
}
