package com.example.cristian.restaurante;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class ensayisActivity extends ActionBarActivity {
    DataBaseManager Manager = gestionMapa.getManager();
    private Cursor cursor;
    private EditText eNombre;
    private TextView tMapNombre, tMapLatitud, tMapLongitud, tMapEnsayo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensayis);
        eNombre = (EditText) findViewById(R.id.editNombreBuscar);
        tMapNombre = (TextView) findViewById(R.id.tMapNombre);
        tMapLatitud = (TextView) findViewById(R.id.tMapLatitud);
        tMapLongitud = (TextView) findViewById(R.id.tMapLongitud);
        tMapEnsayo = (TextView) findViewById(R.id.tMapId);

        cursor = Manager.cargarCursorContactos();

            if (cursor.moveToFirst()){
                do{

                    String dbnombre = cursor.getString(cursor.getColumnIndex(Manager.CN_NAME)).toString();
                    String dblongitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LONGITUD)).toString();
                    String dblatitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LATITUD)).toString();
                    String dbid = cursor.getString(cursor.getColumnIndex(Manager.CN_ID)).toString();
                    tMapNombre.setText(dbnombre);
                    tMapLongitud.setText(dblongitud);
                    tMapLatitud.setText(dblatitud);
                    tMapEnsayo.setText(dbid);
                }
                while(cursor.moveToNext());
            }
            else Toast.makeText(getApplicationContext(),"No hay datos",Toast.LENGTH_SHORT).show();

/*
        String dbnombre = cursor.getString(cursor.getColumnIndex(Manager.CN_NAME));
        String dblatitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LATITUD));
        String dblongitud = cursor.getString(cursor.getColumnIndex(Manager.CN_LONGITUD));
        TextView nombre = (TextView) findViewById(R.id.tEnsayisNombre);
        nombre.setText(dbnombre);
*/

    }






}
