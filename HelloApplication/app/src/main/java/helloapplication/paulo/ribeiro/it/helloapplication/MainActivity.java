package helloapplication.paulo.ribeiro.it.helloapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(this);
    }
    private void alert(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v) {
        TextView tLogin = (TextView) findViewById(R.id.tLogin);
        TextView tSenha = (TextView) findViewById(R.id.tSenha);
        String login = tLogin.getText().toString();
        String senha = tSenha.getText().toString();

        if ("paulo".equals(login) && "123".equals(senha)) {
            alert("Bem-vindo, login realizado com sucesso.");
        } else {
            alert("Login e senha incorretos.");
        }
    }
}
