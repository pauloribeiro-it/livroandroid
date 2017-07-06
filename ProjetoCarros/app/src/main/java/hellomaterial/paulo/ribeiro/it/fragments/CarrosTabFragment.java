package hellomaterial.paulo.ribeiro.it.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellomaterial.paulo.ribeiro.it.R;
import hellomaterial.paulo.ribeiro.it.adapter.TabsAdapter;


/**
 * Created by paulo on 05/07/17.
 */

public class CarrosTabFragment extends livroandroid.lib.fragment.BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carros_tab,container,false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new TabsAdapter(getContext(),getChildFragmentManager()));

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(),R.color.white);
        tabLayout.setTabTextColors(cor,cor);

        return view;
    }
}
