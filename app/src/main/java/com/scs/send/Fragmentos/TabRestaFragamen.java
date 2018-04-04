package com.scs.send.Fragmentos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.scs.send.Detalles.Productos;
import com.scs.send.R;
import com.scs.send.Services.Post;
import com.scs.send.Services.PostRestaAdapter;

import java.util.Locale;

/**
 * Created by Ac-Ad on 25/3/2018.
 */

public class TabRestaFragamen extends Fragment {
    private EditText editsearch;
    private SearchView buscador;
    PostRestaAdapter adapter2;
    public TabRestaFragamen() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tabrestafragamen, container, false);


        ListView gView = (ListView) rootView.findViewById(R.id.listView);
        //SearchView sv= (SearchView) rootView.findViewById(R.id.sv);
        gView.setTextFilterEnabled(true);

        // Crear adaptador y setear
        adapter2 = new PostRestaAdapter(getActivity());
        gView.setAdapter(adapter2);

        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                EditText textView = (EditText) view.findViewById(R.id.idLocal);
                //Obtiene el texto dentro del TextView.
                String textItemList  = textView.getText().toString();


                //Toast.makeText(TabRestaFragamen.super.getContext(), textItemList+" " + position, Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                Intent intent = new Intent(view.getContext(), Productos.class);
                String texto = ""+textItemList;
                bundle.putString("idLocal", texto);
                intent.putExtras(bundle);


                startActivity(intent);
            }
        });

        buscador = (SearchView) rootView.findViewById(R.id.buscador2);
        buscador.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter2.getFilter().filter(query);
                return false;
            }
        });


        return rootView;
    }



}
