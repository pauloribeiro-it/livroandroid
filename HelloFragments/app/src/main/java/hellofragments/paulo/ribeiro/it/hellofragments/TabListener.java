package hellofragments.paulo.ribeiro.it.hellofragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

/**
 * Created by paulo on 09/05/17.
 */

public class TabListener implements ActionBar.TabListener{
    private Context context;
    private Fragment frag;

    public TabListener(Context context, Fragment frag){
        this.context = context;
        this.frag = frag;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.replace(R.id.layoutFrag,this.frag,null);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

}
