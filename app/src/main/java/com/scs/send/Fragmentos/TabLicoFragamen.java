package com.scs.send.Fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scs.send.R;

/**
 * Created by Ac-Ad on 25/3/2018.
 */

public class TabLicoFragamen extends Fragment {
    public TabLicoFragamen() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.tabrestafragamen, container, false);

        return rootView;
    }

}
