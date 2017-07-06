package hellomaterial.paulo.ribeiro.it.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.fragments.CarrosFragment;

/**
 * Created by paulo on 05/07/17.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private Context context;

    public TabsAdapter(Context context, FragmentManager fm){
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return context.getString(R.string.classicos);
            case 1: return context.getString(R.string.esportivos);
            default: return context.getString(R.string.luxo);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f;
        switch (position){
            case 0: f = CarrosFragment.newInstance(R.string.classicos);
                break;
            case 1: f = CarrosFragment.newInstance(R.string.esportivos);
                break;
            default: f = CarrosFragment.newInstance(R.string.luxo);
        }
        return f;
    }
}
