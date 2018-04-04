package com.scs.send.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.scs.send.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ac-Ad on 25/3/2018.
 */


public class PostRestaAdapter extends ArrayAdapter  {

    private static final String URL_BASE = "http://192.168.43.182/ServerSend/Welcome/Locales/";
    private static final String URL_IMG = "http://192.168.43.182/ServerSend/assets/";
    private static final String TAG = "PostRestaAdapter";
    JsonObjectRequest jsArrayRequest;


    ArrayList<Post> items;
    ArrayList<Post> item2;
    Context context;
    LayoutInflater layoutInflater;
    CustomFilter filtro;
    ArrayList<Post> filtroitems;


    // Atributos
    private RequestQueue requestQueue;

    public PostRestaAdapter(Context context) {
        super(context, 0);

        // Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);

        // Nueva petición JSONObject

        jsArrayRequest = new JsonObjectRequest(Request.Method.GET,URL_BASE,(String) null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                items = (ArrayList<Post>) parseJson(response);
                notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }



    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        View listItemView;

        //Comprobando si el View no existe
                listItemView = null == convertView ? layoutInflater.inflate(R.layout.principal,parent,false) : convertView;


                Post item = items.get(position);

                TextView textoTitulo = (TextView) listItemView.findViewById(R.id.textoTitulo);
                TextView textoDescripcion = (TextView) listItemView.findViewById(R.id.dirreccion);
                TextView Estado = (TextView) listItemView.findViewById(R.id.Estado);
        final   ImageView imagenPost = (ImageView) listItemView.findViewById(R.id.imagenPost);
                EditText idlocal = (EditText) listItemView.findViewById(R.id.idLocal);


                textoTitulo.setText(item.getTitulo());
                idlocal.setText(""+item.getId());
                textoDescripcion.setText(item.getDescripcion());
                Estado.setText(item.getEstado());


                listItemView.findViewById(R.id.idLocal).setVisibility(View.GONE);

                ImageRequest request = new ImageRequest(
                    URL_IMG+item.getImagen(),
                    new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                            imagenPost.setImageBitmap(bitmap);
                            }
                        }, 0, 0, null, null,
                        new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                            imagenPost.setImageResource(R.drawable.logoapp);
                                Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                                }
                             });
        requestQueue.add(request);
        this.filtroitems = items;
        return listItemView;
    }

    public List<Post> parseJson(JSONObject jsonObject) {
        // Variables locales
        List<Post> posts = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Post post = new Post(
                            objeto.getString("node_title"),
                            objeto.getString("Direccion"),
                            objeto.getString("Logo"),
                            objeto.getInt("nid"),
                            objeto.getString("Estado"));


                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        item2= (ArrayList<Post>) posts;
        return posts;
    }


    @Override
    public Filter getFilter() {
        if(filtro == null){
            filtro = new CustomFilter();
        }

        return filtro;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults resulst = new FilterResults();
            if(constraint != null && constraint.length()>0){
                //pasamos a mayusculas
                constraint = constraint.toString().toUpperCase();

                ArrayList<Post> filtro = new ArrayList<Post>();

                for(Integer i=0;i<filtroitems.size();i++){
                    if(filtroitems.get(i).getTitulo().toUpperCase().contains(constraint) || filtroitems.get(i).getEstado().toUpperCase().contains(constraint)){
                        Post d= new Post(filtroitems.get(i).getTitulo(),filtroitems.get(i).getDescripcion(),filtroitems.get(i).getImagen(),filtroitems.get(i).getId(),filtroitems.get(i).getEstado());

                        filtro.add(d);
                    }
                }
                resulst.count= filtro.size();
                resulst.values = filtro;
            }else{
                resulst.count= item2.size();
                resulst.values = item2;
            }

            return resulst;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<Post>) results.values;
            notifyDataSetChanged();

        }
    }
}
