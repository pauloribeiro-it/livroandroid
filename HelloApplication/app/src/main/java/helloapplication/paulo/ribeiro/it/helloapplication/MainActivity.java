package helloapplication.paulo.ribeiro.it.helloapplication;

import android.content.Intent;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

public class MainActivity extends DebugActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btLogin = (Button) findViewById(R.id.btLogin);
        btLogin.setOnClickListener(onClickLogin());
    }

    private View.OnClickListener onClickLogin(){
        return new Button.OnClickListener(){
            public void onClick(View v) {
                TextView tLogin = (TextView) findViewById(R.id.tLogin);
                TextView tSenha = (TextView) findViewById(R.id.tSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();

                if ("paulo".equals(login) && "123".equals(senha)) {
                    Intent intent = new Intent(getContext(),BemVindoActivity.class);
                    intent.putExtra("nome","Paulo Ribeiro");
                    startActivity(intent);
                    alert("Bem-vindo, login realizado com sucesso.");
                } else {
                    alert("Login e senha incorretos.");
                }
            }
        };
    }

    private Context getContext(){
        return this;
    }

    private void alert(String s){
        Toast.makeText(this,s, Toast.LENGTH_SHORT).show();
    }
}
