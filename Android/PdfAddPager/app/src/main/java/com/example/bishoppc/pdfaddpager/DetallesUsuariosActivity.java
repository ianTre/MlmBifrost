package com.example.bishoppc.pdfaddpager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bishoppc.pdfaddpager.entidades.Operacion;

public class DetallesUsuariosActivity extends AppCompatActivity {

    TextView campoId, campoFecha, campoLegajo, campoNomUsu, campoLeyenda;
    TextView campoNomYApe, campoDni, campoPath, campoFirmado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuarios);

        campoId = (TextView)findViewById(R.id.idTxt);
        campoFecha = (TextView)findViewById(R.id.fechaTxt);
        campoLegajo = (TextView)findViewById(R.id.legajoTxt);
        campoNomUsu = (TextView)findViewById(R.id.nomUserTxt);
        campoLeyenda = (TextView)findViewById(R.id.leyendaTxt);
        campoNomYApe = (TextView)findViewById(R.id.nomYApeTxt);
        campoDni = (TextView)findViewById(R.id.dniTxt);
        campoPath = (TextView)findViewById(R.id.pathTxt);
        campoFirmado = (TextView)findViewById(R.id.firmadoTxt);

        Bundle objetoEnviado = getIntent().getExtras();
        Operacion operacion = null;

        try{
            if(objetoEnviado != null){
                operacion = (Operacion) objetoEnviado.getSerializable("listaUsuariosObject");
                campoId.setText(operacion.getId().toString());
                campoFecha.setText(operacion.getFecha().toString());
                campoLegajo.setText(operacion.getLegajoCli().toString());
                campoNomUsu.setText(operacion.getNomUsu().toString());
                campoLeyenda.setText(operacion.getLeyenda().toString());
                campoNomYApe.setText(operacion.getNomYApeCli().toString());
                campoDni.setText(operacion.getDniCli().toString());
                campoPath.setText(operacion.getPathArch().toString());

                if (operacion.getFirmado()== 1)
                    campoFirmado.setText("Si");
                else
                    campoFirmado.setText("No");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
