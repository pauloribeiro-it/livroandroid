package hellofragments.paulo.ribeiro.it.hellofragments;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

/**
 * Created by paulo on 09/05/17.
 */

public class TabListener implements ActionBar.TabListener{
    private ViewPager viewPager;
    private int idx;

    public TabListener(ViewPager viewPager, int idx){
        this.viewPager = viewPager;
        this.idx = idx;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(idx);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

}
