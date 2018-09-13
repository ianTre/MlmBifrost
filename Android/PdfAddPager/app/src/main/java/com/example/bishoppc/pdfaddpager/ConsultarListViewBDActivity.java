package com.example.bishoppc.pdfaddpager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bishoppc.pdfaddpager.entidades.Operacion;
import com.example.bishoppc.pdfaddpager.utilidades.Utilidades;

import java.util.ArrayList;

public class ConsultarListViewBDActivity extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Operacion> listaUsuarios;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_list_view_bd);

        conn = new ConexionSQLiteHelper(getApplicationContext());

        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listaInformacion);

        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Operacion operacion=listaUsuarios.get(pos);

                Intent intent=new Intent(ConsultarListViewBDActivity.this,DetallesUsuariosActivity.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("listaUsuariosObject",  operacion);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Operacion operacion = null;
        listaUsuarios = new ArrayList<Operacion>();

        //select * from usuarios
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_OPERACIONES, null);

        while(cursor.moveToNext()){
            operacion = new Operacion();
            operacion.setId(cursor.getInt(0));
            operacion.setFecha(cursor.getString(1));
            operacion.setLegajoCli(cursor.getString(2));
            operacion.setNomUsu(cursor.getString(3));
            operacion.setLeyenda(cursor.getString(4));
            operacion.setNomYApeCli(cursor.getString(5));
            operacion.setDniCli(cursor.getString(6));
            operacion.setPathArch(cursor.getString(7));
            operacion.setFirmado(cursor.getInt(8));

            listaUsuarios.add(operacion);
        }

        obtenerLista();
    }

    private void obtenerLista() {

        listaInformacion = new ArrayList<String>();
        for (int i = 0; i < listaUsuarios.size(); i++)
            listaInformacion.add(listaUsuarios.get(i).getId() + " - " +
                                 listaUsuarios.get(i).getNomUsu() + " - " +
                                 listaUsuarios.get(i).getNomYApeCli());

    }
}
