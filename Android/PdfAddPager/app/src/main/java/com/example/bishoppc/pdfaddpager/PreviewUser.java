package com.example.bishoppc.pdfaddpager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import java.io.File;

public class PreviewUser extends AppCompatActivity{

    PDFView pdfView;
    Button btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preview);

        Bundle extras = getIntent().getExtras();
        final File pathFile = (File)getIntent().getExtras().get("pathFile");
        final String textoTecnico=(String)getIntent().getExtras().get("textoTecnico");
        //Toast.makeText(this, pathFile.toString(), Toast.LENGTH_LONG).show();

        pdfView = (PDFView)findViewById(R.id.pdfReader);
        //pdfView.fromAsset("manual.pdf").load();
        pdfView.fromFile(pathFile).defaultPage(1).load();
        pdfView.setVerticalScrollBarEnabled(true);

        btnSiguiente = (Button)findViewById(R.id.buttonAff);
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(PreviewUser.this , DatesUser.class);
                intent.putExtra("pathFile",pathFile);
                intent.putExtra("textoTecnico",textoTecnico);
                startActivity(intent);
                }
            });
        }
}
