package com.scs.send.Detalles;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.scs.send.R;
import com.squareup.picasso.Picasso;

public class Productos extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView textw;
    private View hView;
    private EditText buscar;
    PostIem adata;
    private SearchView buscador;
    private String TAG="Productos";

    private DatabaseReference myRef;
    private TextView txt;
    private String texto;
    private String textItemList;
    private int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productos);




        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("probandoando-7073d");


        hView = navigationView.getHeaderView(0);


        texto = bundle.getString("idLocal");
        //Toast.makeText(getApplicationContext(),"El Local Selecionadao:"+texto, Toast.LENGTH_SHORT).show();


         adata=new PostIem(getApplicationContext(),texto);


        final ListView gView = (ListView) findViewById(R.id.Productos);
        gView.setTextFilterEnabled(true);
        gView.setAdapter(adata);

        usuario();
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textoTitulo = (TextView) view.findViewById(R.id.id);
                textItemList  = textoTitulo.getText().toString();
                Toast.makeText(getApplicationContext(),"El Selecionadao: "+textItemList+" Local: "+texto, Toast.LENGTH_SHORT).show();



                datoscart();
                VistaProducto(textItemList,texto);
            }
        });


        buscador = (SearchView) findViewById(R.id.buscador);
        
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
              //  adata.getFilter().filter(query);
                return false;
            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Log.d(TAG, "Value issssss: " + value);
                TextView txt= (TextView) findViewById(R.id.badge_notification_3);

                //cont++;
                txt.setText(""+cont);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w( TAG,"Failed to read value.", error.toException());
            }
        });







    }

    private void usuario() {
        FirebaseUser currentUser =  FirebaseAuth.getInstance().getCurrentUser();
        //Datos De usuatio :v cometado mmv
        String snombre=currentUser.getDisplayName();
        Uri Ursl = currentUser.getPhotoUrl();


        //nombre de menu
        TextView nombre = (TextView) hView.findViewById(R.id.username);
        nombre.setText(snombre);

        //Imagen de menu
        ImageView imaagen = (ImageView) hView.findViewById(R.id.circle_image);
        imaagen.setImageURI(Ursl);
        Picasso.with(this).load(Ursl).into(imaagen);
    }
    private void datoscart(){
        cont++;
        myRef.setValue(""+cont);
        //myRef.child("users").child("probandoando-7073d").setValue("sss");



    }
    private void VistaProducto(String idproducto,String idlocal){
        Holder holder;

        holder = new ViewHolder(R.layout.conten);


        OnClickListener clickListener = new OnClickListener() {
            @Override
            public void onClick(DialogPlus dialog, View view) {

                switch (view.getId()) {
                    case R.id.header_container:
                        Toast.makeText(Productos.this, "Header clicked", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.footer_confirm_button:
                        Toast.makeText(Productos.this, "Confirm button clicked", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.footer_close_button:
                        Toast.makeText(Productos.this, "Close button clicked", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                        break;
                }
                //dialog.dismiss();
            }
        };



        showOnlyContentDialog(holder, Gravity.CENTER,clickListener,false);


    }

    private void showOnlyContentDialog(Holder holder, int gravity, OnClickListener clickListener, boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setHeader(R.layout.header)
                .setFooter(R.layout.footer)
                .setCancelable(true)
                .setGravity(gravity)
                //.setAdapter(adapter)
                .setOnClickListener(clickListener)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        Log.d("DialogPlus", "onItemClick() called with: " + "item = [" +
                                item + "], position = [" + position + "]");
                        TextView tes=(TextView) view.findViewById(R.id.nombre);
                        tes.setText(textItemList);
                    }
                })
                //              .setOnDismissListener(dismissListener)
                .setExpanded(expanded)
     //   .setContentWidth(800)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setOnCancelListener(cancelListener)
                //                .setOverlayBackgroundResource(android.R.color.transparent)
                .setContentBackgroundResource(R.drawable.corner_background)
                //                .setOutMostMargin(0, 100, 0, 0)
                .create();
        dialog.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_productos) {

        } else if (id == R.id.nav_carrito) {

        } else if (id == R.id.nav_ordenes) {

        } else if (id == R.id.nav_facturas) {

        } else if (id == R.id.configuration_section) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item1 = menu.findItem(R.id.action_search);
        MenuItemCompat.setActionView(item1, R.layout.notificacion_contador);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //datoscart();
        }

        return super.onOptionsItemSelected(item);
    }
}
