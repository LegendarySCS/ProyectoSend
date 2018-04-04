package com.scs.send;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scs.send.Fragmentos.TabFarmaFragamen;
import com.scs.send.Fragmentos.TabFavorFragamen;
import com.scs.send.Fragmentos.TabLicoFragamen;
import com.scs.send.Fragmentos.TabMercadoFragamen;
import com.scs.send.Fragmentos.TabPromoFragamen;
import com.scs.send.Fragmentos.TabRestaFragamen;

/**
 * Created by Ac-Ad on 25/3/2018.
 */
public class PagerFragmento extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerFragmento(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabPromoFragamen tab0 = new TabPromoFragamen();
                return tab0;
            case 1:
                TabRestaFragamen tab1 = new TabRestaFragamen();
                return tab1;
            case 2:
                TabFarmaFragamen tab2 = new TabFarmaFragamen();
                return tab2;
            case 3:
                TabLicoFragamen tab3 = new TabLicoFragamen();
                return tab3;
            case 4:
                TabMercadoFragamen tab4 = new TabMercadoFragamen();
                return tab4;
            case 5:
                TabFavorFragamen tab5 = new TabFavorFragamen();
                return tab5;
            default:
                throw new RuntimeException("Tab position invalid " + position);

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}