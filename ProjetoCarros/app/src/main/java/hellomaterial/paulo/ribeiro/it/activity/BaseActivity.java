package hellomaterial.paulo.ribeiro.it.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hellomaterial.paulo.ribeiro.it.R;

/**
 * Created by paulo on 13/06/17.
 */

public class BaseActivity extends livroandroid.lib.activity.BaseActivity{
    protected DrawerLayout drawerLayout;

    protected void setUpToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void setupNavDrawer(){
        final ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(navigationView != null && drawerLayout !=null){
            setNavViewValues(navigationView,R.string.nav_drawer_username,R.string.nav_drawer_email,R.mipmap.ic_launcher);
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener(){
                        @Override
                        public boolean onNavigationItemSelected(MenuItem item) {
                            item.setChecked(true);
                            drawerLayout.closeDrawers();
                            onNavDrawerItemSelected(item);
                            return true;
                        }
                    }
            );
        }
    }

    //Atualiza os dados do header do Navigation Viewpublic
    static void setNavViewValues(NavigationView navView, int nome, int email, int img){
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tNome);
        TextView tEmail = (TextView) headerView.findViewById(R.id.tEmail);
        ImageView imgView = (ImageView) headerView.findViewById(R.id.img);
        tNome.setText(nome);
        tEmail.setText(email);
        imgView.setImageResource(img);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(drawerLayout != null){
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void openDrawer(){
        if(drawerLayout != null)
            drawerLayout.openDrawer(GravityCompat.START);
    }

    protected void closeDrawer(){
        if(drawerLayout != null)
            drawerLayout.closeDrawer(GravityCompat.START);
    }
    //Trata o evento do menu lateral
    private void onNavDrawerItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case R.id.nav_item_carros_todos:
                toast("Clicou em carros");
                break;
            case R.id.nav_item_carros_classicos:
                toast("Clicou em carros clássicos");
                break;
            case R.id.nav_item_carros_esportivos:
                toast("Clicou em carros esportivos");
                break;
            case R.id.nav_item_carros_luxo:
                toast("Clicou em carros luxo");
                break;
            case R.id.nav_item_site_livro:
                snack(drawerLayout,"Clicou em site do livro");
                break;
            case R.id.nav_item_settings:
                toast("Clicou em configurações");
                break;
        }
    }
}
