package hellofragments.paulo.ribeiro.it.hellofragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.Fragment1;
import fragments.Fragment2;
import fragments.Fragment3;

/**
 * Created by paulo on 10/05/17.
 */

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new Fragment1();
            case 1: return new Fragment2();
            default: return new Fragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
