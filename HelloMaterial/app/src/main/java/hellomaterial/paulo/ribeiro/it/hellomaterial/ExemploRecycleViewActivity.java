package hellomaterial.paulo.ribeiro.it.hellomaterial;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class ExemploRecycleViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplo_recycle_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        List<Planeta> planetas = Planeta.getPlanetas();
        recyclerView.setAdapter(new PlanetaAdapter(this,planetas,onClickPlaneta()));
    }

    private PlanetaAdapter.PlanetaOnClickListener onClickPlaneta(){
       return new PlanetaAdapter.PlanetaOnClickListener() {
           @Override
           public void onClickPlaneta(PlanetaAdapter.PlanetasViewHolder holder, int idx) {
                List<Planeta> planetas = Planeta.getPlanetas();
                Planeta p = planetas.get(idx);
                Toast.makeText(getBaseContext(),"Planeta: "+p.nome,Toast.LENGTH_SHORT).show();
           }
       };
    }

    private Activity getActivity(){return this;}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exemplo_recycler_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_linear_layout){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        }else if(id == R.id.action_grid_layout){
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
