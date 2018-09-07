package com.example.bishoppc.pdfaddpager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class Splash extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    private Spinner mySpinner;
    private ImageView usuarioLogo;

    Global userName = Global.getInstance();
    private static int WELCOME_TIMEOUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageview);
        mySpinner = (Spinner) findViewById(R.id.spinner);
        usuarioLogo = (ImageView) findViewById(R.id.usuario_logo);


        //El objeto myanim es lo que le da el efecto Fade a los diferentes controles
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fade);

        textView.startAnimation(myanim);
        imageView.startAnimation(myanim);
        mySpinner.startAnimation(myanim);
        usuarioLogo.startAnimation(myanim);

        //Esto es para que este seleccionado por defecto la opcion "Seleccione un Usuario"
        mySpinner.setSelection(0);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Splash.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.spinnerText));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        /*// Creamos una instancia de Java Calendar
        Calendar calendar = Calendar.getInstance();

        // Obtenemos la fecha (java.util.Date) de la instancia Calendar creada.
        // Esta fecha java representará el día actual
        java.util.Date currentDate = calendar.getTime();

        // Ahora creamos una java.sql.Date a partir de la java.util.Date
        java.sql.Date hoy = new java.sql.Date(currentDate.getTime());

        userName.setFecha(hoy);*/

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {


                if(pos > 0)
                {
                    Intent welcome = new Intent(Splash.this, MainActivity.class);

                    userName.setuserName(mySpinner.getSelectedItem().toString());
                    usuarioLogo.setImageResource(R.drawable.usuario_1);
                    startActivity(welcome);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        /*new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
                Intent welcome = new Intent(Splash.this,MainActivity.class);

                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);*/

        /*textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageview);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fade);

        textView.startAnimation(myanim);
        imageView.startAnimation(myanim);*/

        /*final Intent i = new Intent(this, MainActivity.class);

        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    startActivity(i);
                    finish();
                }
            }
        };

        timer.start();*/



    }

    @Override
    public void onResume(){
        super.onResume();
        mySpinner.setSelection(0);

        textView = (TextView) findViewById(R.id.textview);
        imageView = (ImageView) findViewById(R.id.imageview);
        mySpinner = (Spinner) findViewById(R.id.spinner);
        usuarioLogo = (ImageView) findViewById(R.id.usuario_logo);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.fade);

        textView.startAnimation(myanim);
        imageView.startAnimation(myanim);
        mySpinner.startAnimation(myanim);
        usuarioLogo.startAnimation(myanim);
        usuarioLogo.setImageResource(R.drawable.usuario_0);

        mySpinner.setSelection(0);
    }
}