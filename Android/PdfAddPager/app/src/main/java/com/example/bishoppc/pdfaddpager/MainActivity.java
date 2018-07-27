package com.example.bishoppc.pdfaddpager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre;
    EditText txtDni;

    //Variable para la seleccion Intent
    private static final int PICKER = 1;
    //Declaramos los siguientes obejtos
    Button botonExaminar;
    Button botonAff;
    EditText textExaminar;
    EditText textoTecnico;
    PDFView pdfView;

    private File filePaths;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botonExaminar = (Button) findViewById(R.id.btnExaminar);
        botonAff=(Button) findViewById(R.id.buttonAff);
        textExaminar = (EditText) findViewById(R.id.txtExaminar);
        pdfView = (PDFView) findViewById(R.id.pdfReader);
        textoTecnico=(EditText)findViewById(R.id.txtTecnico);

        //Es necesario una sola vez para no tener que hacer mas de un click
        botonExaminar.setFocusableInTouchMode(true);

        /*La manera en la cual está hecho es que se le da el foco al botonExaminar, luego se tiene
          un Listener el cual está fijandose constantemente el setFocus y LostFocus. Si dicho objeto
          tiene el Foco, se habilita el Scrollview en la pantalla y sino se lo deshabilita.
          Al haber quedado el foco en el PDFView, la App necesita por ejemplo que si se presiona un
          botón, el numero de veces sea 2. Porque? porque un click es para que el foco del PDFView
          se vaya al boton y el segundo es para que se ejecute dicha acción.
          Entonces se agregó un Listener que cuando se hace click en el layout main se le da el foco
          al buttonAff*/

        botonExaminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                PickFile();
            }
        });

        botonAff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , PreviewUser.class);
                intent.putExtra("pathFile",filePaths);
                intent.putExtra("textoTecnico",textoTecnico.getText().toString());
                startActivity(intent);
            }
        });

        final CustomScrollView myScrollView = (CustomScrollView) findViewById(R.id.myScroll);
        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.layout_main);


        botonExaminar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    myScrollView.setEnableScrolling(true); // Scrolling enabled
                    //myLayout.requestFocus();
                }else{
                    myScrollView.setEnableScrolling(false); // Scrolling disabled
                }

            }
        });

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getAction())
                    botonAff.requestFocus();
                return false;
            }
        });

        pdfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                botonExaminar.requestFocus();
            }
        });

    }

    private void PickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Instale un administrador de archivos"), PICKER);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case PICKER:
                if(resultCode == RESULT_OK){

                    textExaminar.setText(data.getData().getPath().toString());
                    filePaths = new File(textExaminar.getText().toString());
                    botonAff.setEnabled(true);

                    pdfView = (PDFView)findViewById(R.id.pdfReader);
                    //pdfView.fromAsset("manual.pdf").load();
                    pdfView.fromFile(filePaths).defaultPage(1).load();
                    pdfView.setVerticalScrollBarEnabled(true);

                }
                break;
        }
    }
}
