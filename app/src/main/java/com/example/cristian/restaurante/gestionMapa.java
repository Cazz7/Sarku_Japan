package com.example.cristian.restaurante;


import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.content.Context;


public class gestionMapa  extends ActionBarActivity implements View.OnClickListener{

    static private DataBaseManager Manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private EditText Ednombre;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_gestion_mapa);

        Manager = new DataBaseManager(getApplicationContext());
        lista = (ListView) findViewById(android.R.id.list);
        Ednombre = (EditText) findViewById(R.id.editNombreBuscar);

        String[] from = new String[]{Manager.CN_NAME,Manager.CN_LATITUD,Manager.CN_LONGITUD};
        int[] to = new int[]{R.id.texto1,R.id.texto2,R.id.texto3};
        cursor = Manager.cargarCursorContactos();
        adapter = new SimpleCursorAdapter(this,R.layout.lista_cursor,cursor,from,to,0);
        lista.setAdapter(adapter);


        //Agregar
        ImageButton btnAdd = (ImageButton) findViewById(R.id.ibAdd);
        btnAdd.setOnClickListener(this);
        //Eliminar
        ImageButton btnDelete = (ImageButton) findViewById(R.id.ibDelete);
        btnDelete.setOnClickListener(this);
        //Editar
        ImageButton btnEdit = (ImageButton) findViewById(R.id.ibEdit);
        btnEdit.setOnClickListener(this);

        //Buscar
        ImageButton btnbuscar = (ImageButton) findViewById(R.id.ibSearch);
        btnbuscar.setOnClickListener(this);
        //Cargar
        ImageButton btnCargar = (ImageButton) findViewById(R.id.ibLoad);
        btnCargar.setOnClickListener(this);


    }

    public static DataBaseManager getManager() {
        return Manager;
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.ibSearch){
            new BuscarTask().execute();
        }
        if(v.getId()==R.id.ibLoad){
            update();
            }


        if (v.getId()==R.id.ibAdd){
            EditText nombre = (EditText) findViewById(R.id.editNombre);
            EditText longitud = (EditText) findViewById(R.id.editLongitud);
            EditText latitud = (EditText) findViewById(R.id.editLatitud);
            Manager.insertar(nombre.getText().toString(),latitud.getText().toString(),longitud.getText().toString());
            nombre.setText("");
            longitud.setText("");
            latitud.setText("");
            Toast.makeText(getApplicationContext(),"Insertado", Toast.LENGTH_SHORT).show();
            update();
        }
        if(v.getId()==R.id.ibDelete){
            EditText nombre = (EditText) findViewById(R.id.editNombre);
            Manager.eliminar(nombre.getText().toString());
            Toast.makeText(getApplicationContext(),"Eliminado", Toast.LENGTH_SHORT).show();
            nombre.setText("");
            update();
        }
        if (v.getId()==R.id.ibEdit){
            EditText nombre = (EditText) findViewById(R.id.editNombre);
            EditText longitud = (EditText) findViewById(R.id.editLongitud);
            EditText latitud = (EditText) findViewById(R.id.editLatitud);
            Manager.Modificardatos(nombre.getText().toString(),latitud.getText().toString(),longitud.getText().toString());
            Toast.makeText(getApplicationContext(),"Actualizado", Toast.LENGTH_SHORT).show();
            nombre.setText("");
            longitud.setText("");
            latitud.setText("");
            update();
        }
    }

public void update(){
    lista = (ListView) findViewById(android.R.id.list);
    Ednombre = (EditText) findViewById(R.id.editNombreBuscar);

    String[] from = new String[]{Manager.CN_NAME,Manager.CN_LATITUD,Manager.CN_LONGITUD};
    int[] to = new int[]{R.id.texto1,R.id.texto2,R.id.texto3};
    cursor = Manager.cargarCursorContactos();
    adapter = new SimpleCursorAdapter(this,R.layout.lista_cursor,cursor,from,to,0);
    lista.setAdapter(adapter);
}

    private class BuscarTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Buscando...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            cursor = Manager.buscarContacto(Ednombre.getText().toString());
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(),"Finalizado", Toast.LENGTH_SHORT).show();
            adapter.changeCursor(cursor);
            obtener();
        }
    }

    public void obtener () {

        TextView Txnombre = (TextView) findViewById(R.id.tNombre);
        TextView Txlongitud = (TextView) findViewById(R.id.tLongitud);
        TextView Txlatitud = (TextView) findViewById(R.id.tLatitud);
        try{

            String dbnombre = cursor.getString(cursor.getColumnIndex(Manager.CN_NAME));
            Txnombre.setText(dbnombre);
            String dblatitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LATITUD));
            Txlatitud.setText(dblatitud);
            String dblongitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LONGITUD));
            Txlongitud.setText(dblongitud);
        }
        catch(CursorIndexOutOfBoundsException e){
            Txnombre.setText("Not Found");
            Txlongitud.setText("Not Found");
            Txlatitud.setText("Not Found");
        }

    }


}
