package com.scs.send;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout tabLayout;
    private TextView nombre;
    private FirebaseAuth mAuth;
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;
    private View hView;
    private static final String TAG = "Home";
    private RelativeLayout notificationCount1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Bundle bundle = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationCount1 = (RelativeLayout) findViewById(R.id.relative_layout_item_count2);

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
         hView = navigationView.getHeaderView(0);


       // TextView nom = (TextView) hView.findViewById(R.id.add);
        //TextView Texttnombre =(TextView) findViewById(R.id.nombre);
        //TextView Texttcorreo =(TextView)hView.findViewById(R.id.correo);
        //extView Texttid =(TextView) hView.findViewById(R.id.ids);




        tabLayout = (TabLayout) findViewById(R.id.tabs);
        menus();
        usuario();



        final ViewPager viewPager = (ViewPager) findViewById(R.id.pagermain);

        final PagerFragmento adapter = new PagerFragmento(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Toast.makeText(Home.this, "Tab selected " +  tab.getPosition(), Toast.LENGTH_SHORT).show();
                // tabLayout.getTabAt(tab.getPosition()).getCustomView()
                //       .setBackgroundColor(Color.parseColor("#00796B"));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // tabLayout.getTabAt(tab.getPosition()).getCustomView()
                //       .setBackgroundColor(Color.parseColor("#009688"));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

        //nombre principal
        TextView nombreprincipal = (TextView) findViewById(R.id.nombre);
        nombreprincipal.setText(snombre);

        //Imagen de menu
        ImageView imaagen = (ImageView) hView.findViewById(R.id.circle_image);


        //imagen Principal
        ImageView Userprincipal  = (ImageView) findViewById(R.id.Userprincipal);
        Userprincipal.setImageURI(Ursl);

        Picasso.with(this).load(Ursl).into(Userprincipal);

        Picasso.with(this).load(Ursl).into(imaagen);

        //Logo cargardo del server
        ImageView logo= (ImageView) findViewById(R.id.LogoApp);
        Picasso.with(this).load(Ursl).into(logo);


        //EditText te= (EditText) findViewById(R.id.url);
        //te.setText(" "+Ursl);

    }


    private void menus() {

        tabLayout.addTab(tabLayout.newTab().setText("Tab 0"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 5"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        TextView tab0 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab0.setText("Promo");
        tabLayout.getTabAt(0).setCustomView(tab0);

        TextView tab1 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab1.setText("Restaurantes");
        tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.restaurante, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tab1);

        TextView tab2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab2.setText("Farmacias");
        tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.farmacia, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tab2);

        TextView tab3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab3.setText("Licores");
        tab3.setGravity(Gravity.CENTER);

        tab3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.licores, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tab3);

        TextView tab4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab4.setText("Mercado");
        tab4.setGravity(Gravity.CENTER);
        tab4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.licores, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tab4);

        TextView tab5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tab5.setText("Un Favor");
        tab5.setGravity(Gravity.CENTER);
        tab5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.licores, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tab5);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.finish();
            return;
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
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


            TextView txt = (TextView) findViewById(R.id.badge_notification_3);
            txt.setText("aaa");


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_productos) {

        } else if (id == R.id.nav_carrito) {

        } else if (id == R.id.nav_ordenes) {

        } else if (id == R.id.nav_facturas) {

        } else if (id == R.id.configuration_section) {
            FirebaseAuth.getInstance().signOut();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("probandoando-7073d");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                TextView txt= (TextView) findViewById(R.id.badge_notification_3);
                txt.setText(value);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                TextView txt= (TextView) findViewById(R.id.badge_notification_3);
                txt.setText("0");
                Log.w( TAG,"Failed to read value.", error.toException());
            }
        });
    }
}
