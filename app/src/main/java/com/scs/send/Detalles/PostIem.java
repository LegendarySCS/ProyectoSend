package com.scs.send.Detalles;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class PostIem extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;


    private static final String URL_BASE = "http://192.168.43.182/ServerSend/Welcome/Productos/";
    private static final String URL_IMG = "http://192.168.43.182/ServerSend/assets/Productos/";
    private static final String TAG = "PostRestaAdapter";
    JsonObjectRequest jsArrayRequest;

    ArrayList<PostI> items;
    ArrayList<PostI> item2;
    Context context;
    LayoutInflater layoutInflater;
    CustomFilter filtro;
    ArrayList<PostI> filtroitems;
    String id=null;
    String a="";

    // Atributos
    private RequestQueue requestQueue;

    public PostIem(Context context,String texto) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);

        // Nueva petición JSONObject

        jsArrayRequest = new JsonObjectRequest(Request.Method.GET,URL_BASE+"96749",(String) null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                items = (ArrayList<PostI>) parseJson(response);
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

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.snippet_item1, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    holder.textView2 = (TextView) convertView.findViewById(R.id.text2);


                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.snippet_item2, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mData.get(position));
        Log.e(TAG,mData.get(position));

        return convertView;
    }

    public List<PostI> parseJson(JSONObject jsonObject) {
        // Variables locales
        List<PostI> posts = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    PostI post = new PostI(
                            objeto.getString("node_title"),
                            objeto.getString("Descripcion"),
                            objeto.getString("Foto"),
                            objeto.getString("Categoria"),
                            objeto.getInt("id"));


                    posts.add(post);


                    if(!a.equals(objeto.getString("Categoria"))) {
                        a=objeto.getString("Categoria");
                        addSectionHeaderItem(objeto.optString("Categoria"));
                    }
                    addItem(objeto.getString("node_title"));
//                    Log.e(TAG, "Er"+objeto.getString("node_title"));


                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        item2= (ArrayList<PostI>) posts;
        return posts;
    }

    public static class ViewHolder {
        public TextView textView;
        public TextView textView2;
    }
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

                ArrayList<PostI> filtro = new ArrayList<PostI>();

                for(Integer i=0;i<filtroitems.size();i++){
                    if(filtroitems.get(i).getTitulo().toUpperCase().contains(constraint)){
                        PostI d= new PostI(filtroitems.get(i).getTitulo(),filtroitems.get(i).getDescripcion(),filtroitems.get(i).getImagen(),filtroitems.get(i).getCategoria(),filtroitems.get(i).getId());

                        filtro.add(d);
                    }
                }
                resulst.count= filtro.size();
                resulst.values = filtro;
            }else{
                Log.d(TAG, "Eaaaaaaa "+item2);
                resulst.count= item2.size();
                resulst.values = item2;
            }

            return resulst;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            items = (ArrayList<PostI>) results.values;
            notifyDataSetChanged();


        }
    }
}
